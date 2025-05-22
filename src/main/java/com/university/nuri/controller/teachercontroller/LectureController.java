package com.university.nuri.controller.teachercontroller;

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

import com.university.nuri.service.teacherservice.LectureService;
import com.university.nuri.vo.commonvo.AssignVO;
import com.university.nuri.vo.teachervo.TestMakeVO;

@Controller
public class LectureController {
	
	@Autowired
	private LectureService lectureService;
	
	@Autowired 
	HttpSession session;
	
	//강의관리 > 강의 정보 
	@GetMapping("/lecture-info")
	public ModelAndView getLectureInfo(@RequestParam("lect_idx") String lect_idx) {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> lectureInfo = lectureService.getLectureInfo(lect_idx);

		mv.addObject("lectureInfo", lectureInfo);
		mv.addObject("lect_idx", lect_idx);
		mv.setViewName("teacher/lecture/lectureInfo");
		return mv;
	}
	
	
	/*
	 * //강의관리 > 과제 현황
	 * 
	 * @GetMapping("/assign-list") public ModelAndView
	 * getAssignList(@RequestParam(value = "date", defaultValue = "total") String
	 * filter,
	 * 
	 * @RequestParam("lect_idx") String lect_idx) { System.out.println(lect_idx);
	 * ModelAndView mv = new ModelAndView(); List<Map<String,Object>> assignList =
	 * lectureService.getAssignList(lect_idx, filter); mv.addObject("lect_idx",
	 * lect_idx); mv.addObject("assignList", assignList); mv.addObject("ListSize",
	 * assignList.size()); mv.setViewName("teacher/lecture/assignList"); return mv;
	 * }
	 */
	
	//강의관리 > 과제 현황
	@GetMapping("/assign-list")
	public ModelAndView getAssignList(@RequestParam("lect_idx") String lect_idx) {
		System.out.println(lect_idx);
		ModelAndView mv = new ModelAndView();
		String end = "all";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("lect_idx", lect_idx);
		paramMap.put("end", end);
		List<Map<String,Object>> assignList = lectureService.getAssignList(paramMap);
		mv.addObject("lect_idx", lect_idx);
		mv.addObject("assignList", assignList);
		for (Map<String, Object> assign : assignList) {
		    System.out.println("과제 정보: " + assign);
		}
		mv.addObject("ListSize", assignList.size());
		mv.setViewName("teacher/lecture/assignList");
		return mv;
	} 

	
	
	// 이의제기 메인화면에서 필터링 적용한 경우
	@PostMapping("/get-assign")
	@ResponseBody
	public List<Map<String, Object>> getWaitPermitRejectObjection(@RequestBody Map<String, Object> params) {
	
		

		String end = (String) params.get("end");
		String lect_idx = (String) params.get("lect_idx");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("lect_idx", lect_idx);
		paramMap.put("end", end);
		System.out.println("왕" + end);
		List<Map<String,Object>> assignList = lectureService.getAssignList(paramMap);
		for (Map<String, Object> assign : assignList) {
		    System.out.println("과제 정보: " + assign);
		}
		return assignList;

	}
	
	

	//강의 관리 > 시험 출제
	@GetMapping("/exam-list")
	public ModelAndView getExamList(@RequestParam("lect_idx") String lect_idx){
		ModelAndView mv = new ModelAndView();
		
		//중간고사, 기말고사 출제 여부 
		int resultMid =0;
		int resultFinal =0;
		resultMid = lectureService.midExist(lect_idx);
		resultFinal = lectureService.finalExist(lect_idx);
 		mv.addObject("resultMid", resultMid);
		mv.addObject("retusltFinal", resultFinal);
		
		
		//시험 리스트 
		List<TestMakeVO> getExamList = lectureService.getExamList(lect_idx);
		mv.addObject("examList", getExamList); 
		
		mv.addObject("lect_idx", lect_idx);
		mv.setViewName("teacher/lecture/examList");
		return mv;
	}

}
