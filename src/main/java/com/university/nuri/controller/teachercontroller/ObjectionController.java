package com.university.nuri.controller.teachercontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.service.teacherservice.ObjectionService;
import com.university.nuri.service.teacherservice.ScoreService;

@Controller
public class ObjectionController {

	@Autowired
	private ObjectionService objectionService;
	@Autowired
	private HttpSession session;
	
	@Autowired
	private ScoreService scoreService;

	// 이의제기 관리 눌렀을때의 화면
	@GetMapping("/objection-main")
	public ModelAndView getObjectionMain() {
		Map<String, Object> tInfo = (Map<String, Object>) session.getAttribute("tInfo");
		int t_idx = (Integer) tInfo.get("t_idx");  // 또는 필요 시 String으로 꺼내서 parseInt

		int objection_status = 0;// 대기 상태 defalut이므로 
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("t_idx", t_idx);
		paramMap.put("objection_status", objection_status);
		List<Map<String, Object>> objectionList = objectionService.getObjectionList(paramMap);
		
		ModelAndView mv = new ModelAndView("teacher/objection/objectionMain");

		mv.addObject("objectionList", objectionList);

		return mv;
	}

	// 이의제기 메인화면에서 필터링 적용한 경우
	@PostMapping("/get-wait-permit-reject-objection")
	@ResponseBody
	public List<Map<String, Object>> getWaitPermitRejectObjection(@RequestBody Map<String, Object> params) {
	
		
		Map<String, Object> tInfo = (Map<String, Object>) session.getAttribute("tInfo");
		int t_idx = (Integer) tInfo.get("t_idx"); // 하드코딩 되어있었어서 바꿔야
		String objectionStatus = (String) params.get("objectionStatus");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("t_idx", t_idx);
		paramMap.put("objection_status", objectionStatus);
		System.out.println("왕" + objectionStatus);
		List<Map<String, Object>> objectionList = objectionService.getObjectionList(paramMap);
		return objectionList;

	}
	
	


	// 이의제기 메인에서 각 행 클릭시 나오는 이의 제기 상세 화면
	@GetMapping("/objection-main-detail")
	public ModelAndView getObjectionMainDetail(@RequestParam("objection_idx") int objection_idx) {

		Map<String, Object> objectionMap = objectionService.getObjectionDetail(objection_idx);

		ModelAndView mv = new ModelAndView("teacher/objection/objectionDetail");

		mv.addObject("objectionMap", objectionMap);

		return mv;
	}

// 해당 이의제기 관련 성적 수정 페이지로 이동
	@PostMapping("/objection-update")
	public ModelAndView getObjectionUpdate(@RequestParam("enroll_idx") int enroll_idx,
			@RequestParam("objection_idx") int objection_idx) {
		Map<String, Object> resultMap = objectionService.getSearchScore(enroll_idx);
		ModelAndView mv = new ModelAndView("teacher/objection/objectionUpdate");
		mv.addObject("objection_idx", objection_idx);
		mv.addObject("scoreMap", resultMap);
		mv.addObject("enroll_idx",enroll_idx);
	
		
		return mv;

	}


	
	
	  @PostMapping("/objection_update_ok")
	  public String updateScores(
		  @RequestParam("objection_idx") int objection_idx,
	      @RequestParam("enroll_idx[]") List<Integer> enrollIdxList,
	      @RequestParam("score_mid[]") List<Integer> midScores,
	      @RequestParam("score_final[]") List<Integer> finalScores,
	      @RequestParam("score_assign[]") List<Integer> assignScores,
	      @RequestParam("score_attend[]") List<Integer> attendScores,
	      @RequestParam("result") String result
	  ) {
		  
		  List<String> totalScores = new ArrayList<>();
		  Map<String, Object> paramMapUpdate = new HashMap<>();
		  int objection_status=2;
		  
		  if("permit".equals(result)){
			  for (int i = 0; i < enrollIdxList.size(); i++) {
				  String totalGrade = null;
				  if ( (midScores.get(i)) != null && finalScores.get(i) != null && assignScores.get(i) != null && attendScores.get(i) != null) {
			            int total = midScores.get(i) + finalScores.get(i) + assignScores.get(i) + attendScores.get(i);
			            totalGrade = (total >= 90) ? "A" : (total >= 80) ? "B" : (total >= 70) ? "C" : "F";
			        }
				  totalScores.add(totalGrade);
			  }
		      for (int i = 0; i < enrollIdxList.size(); i++) {
		    	  
		  		Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("enroll_idx", enrollIdxList.get(i));
				paramMap.put("score_mid", midScores.get(i));
				paramMap.put("score_final", finalScores.get(i));
				paramMap.put("score_assign", assignScores.get(i));
				paramMap.put("score_attend", attendScores.get(i));
				paramMap.put("score_total", totalScores.get(i));
		    	  
				scoreService.getScoreUpdateOk(paramMap);
		      	}
		      objection_status=1;
	      
		  }
		  paramMapUpdate.put("objection_status", objection_status);
		  paramMapUpdate.put("objection_idx", objection_idx);
		  objectionService.getUpdateObjectionStatus(paramMapUpdate);
		  
		  

	      return "redirect:/get-objection-update-ok-jsp?result="+ result+"&objection_idx=" + objection_idx + "&enroll_idx=" + enrollIdxList.get(0);
	      
	  }
	  
	  
	@GetMapping("/get-objection-update-ok-jsp")
	public ModelAndView getObjectionUpdate(@RequestParam("result") String result,
			@RequestParam("enroll_idx") int enroll_idx, @RequestParam("objection_idx") int objection_idx) {
		Map<String, Object> resultMap = objectionService.getSearchScore(enroll_idx);
		ModelAndView mv = new ModelAndView("teacher/objection/objectionUpdateOk");
		mv.addObject("scoreMap", resultMap);
		mv.addObject("result", result);
		System.out.println("Ri"+result);
		mv.addObject("objection_idx", objection_idx);
		return mv;

	}

}