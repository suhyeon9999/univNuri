
 package com.university.nuri.controller.studentcontroller;
  
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import
  org.springframework.web.bind.annotation.RequestMapping;
import
  org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import
  org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.university.nuri.service.studentservice.SEnrollService;
import com.university.nuri.service.studentservice.SScoreSearchService;
import com.university.nuri.vo.commonvo.UserVO;
  
  
  @Controller 
  public class SScoreSearchController {
  @Autowired
  private SScoreSearchService sScoreSearchService;
  @Autowired
  private SEnrollService sEnrollService;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  
  @RequestMapping("/sScoreSearch") 
  public ModelAndView getSScoreSearch(@RequestParam Map<String, String> params, HttpSession session) { 
	  ModelAndView mv = new ModelAndView(); 
	  //세션에서 s_idx 가져오기 
	  Map<String, Object> sInfo = (Map<String,  Object>) session.getAttribute("sInfo"); 
	  String s_idx =  String.valueOf(sInfo.get("s_idx"));
	  //검색 조건 
	  params.put("s_idx", s_idx); 
	  List<Map<String, Object>> scoreList = sScoreSearchService.getSScoreSearch(params);
	  
	  // 전체 평점, 금학기 평점 계산
	  Map<String, Object> gpaInfo = sScoreSearchService.getGPAInfo(s_idx);
	  mv.addObject("yearList", sEnrollService.getLectureYears());
	  mv.addObject("semesterList", sEnrollService.getLectureSemesters());
	  mv.addObject("scoreList", scoreList);
	  mv.addObject("gpaInfo", gpaInfo);
	  // 검색버튼 초기화 방지
	  mv.addObject("search", params);
	  mv.setViewName("student/scoresearch/sScoreSearch");
	  return mv;
	  } 
  
	  @RequestMapping("/sScoreSearchObjectionManagement")
	  public ModelAndView getsScoreSearchObjectionManagement(HttpSession session) {
		  ModelAndView mv = new ModelAndView(); 
		  //세션에서 s_idx 가져오기 
		  Map<String, Object> sInfo = (Map<String,  Object>) session.getAttribute("sInfo"); 
		  String s_idx =  String.valueOf(sInfo.get("s_idx"));
	      List<Map<String, Object>> objectionList = sScoreSearchService.getObjectionList(s_idx);
	      
	      mv.addObject("objectionList", objectionList);
	      mv.setViewName("student/scoresearch/sScoreSearchObjectionManagement");
	      return mv;
	  }
	  @RequestMapping("/sScoreSearchObjectionDetail")
	  public ModelAndView sScoreSearchObjectionDetail(@RequestParam("objection_idx")String objection_idx, HttpSession session, @RequestParam("lect_idx")String lect_idx) {
		  ModelAndView mv = new ModelAndView(); 
		  Map<String, Object> sInfo = (Map<String,  Object>) session.getAttribute("sInfo"); 
		  String s_idx =  String.valueOf(sInfo.get("s_idx"));
		  Map<String, Object> objection = sScoreSearchService.getObjectionByIdx(objection_idx, lect_idx, s_idx);
		  System.out.println("s_idx" + s_idx);
		  System.out.println("lect_idx" + lect_idx);
		  System.out.println("📌 objection = " + objection);
		  mv.addObject("objectionList", objection);
		  mv.setViewName("student/scoresearch/sScoreSearchObjectionDetail");
		  return mv;
	  }
	  @GetMapping("/sScoreSearchObjectionDetailUpdate")
	  public ModelAndView sScoreSearchObjectionDetailUpdate(HttpSession session, @ModelAttribute("objection_idx")String objection_idx, @ModelAttribute("objection_content") String objection_content) {
		  ModelAndView mv = new ModelAndView(); 
		  //세션에서 s_idx 가져오기 
		  Map<String, Object> sInfo = (Map<String,  Object>) session.getAttribute("sInfo"); 
		  String s_idx =  String.valueOf(sInfo.get("s_idx"));
		  
		  List<Map<String, Object>> objectionList = sScoreSearchService.getObjectionList(s_idx);
		  
		  mv.addObject("objectionList", objectionList);
		  mv.setViewName("student/scoresearch/sScoreSearchObjectionDetailUpdate");
		  return mv;
	  }
	  // 이의 제기 업데이트 OK
	  @PostMapping("/sScoreSearchObjectionDetailUpdateOK")
	  public ModelAndView sScoreSearchObjectionDetailUpdateOK( @RequestParam("objection_idx") String objection_idx,
			    @RequestParam("objection_content") String objection_content,
			    RedirectAttributes redirectAttributes, @RequestParam("lect_idx")String lect_idx) {
		  try {
			  ModelAndView mv = new ModelAndView(); 
			  Map<String, Object> params = new HashedMap<>();
			  params.put("objection_idx", objection_idx);
			  params.put("objection_content", objection_content);
			  
			  int result = sScoreSearchService.sScoreSearchObjectionDetailUpdateOK(params);
			  if(result > 0) {
				  redirectAttributes.addFlashAttribute("msg", "수정되었습니다.");
			  }else{
				  redirectAttributes.addFlashAttribute("msg", "수정 실패 했습니다.");
			  }
			  mv.setViewName("redirect:/sScoreSearchObjectionDetail?objection_idx="+objection_idx+"&lect_idx="+lect_idx);
			  return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	  }
	  // 이의 제기 신청
	  @GetMapping("/sScoreSearchObjectionInsert")
	  public ModelAndView sScoreSearchObjectionInsert(HttpSession session, @RequestParam("lect_idx") String lect_idx) {
	      try {
	          // 세션에서 s_idx 가져오기
	          Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
	          String s_idx = String.valueOf(sInfo.get("s_idx"));

	          // Service 호출로 해당 강의/교수/수강정보 등 조회
	          Map<String, Object> objectionInfo = sScoreSearchService.getObjectionInfoForInsert(s_idx, lect_idx);

	          ModelAndView mv = new ModelAndView();
	          mv.addObject("objectionInfo", objectionInfo);
	          mv.setViewName("student/scoresearch/sScoreSearchObjectionInsert");
	          return mv;

	      } catch (Exception e) {
	          e.printStackTrace();
	          return new ModelAndView("error");
	      }
	  }



	  // 이의 제기 신청OK
	  @PostMapping("/sScoreSearchObjectionDetailInsertOK")
	  public ModelAndView sScoreSearchObjectionDetailInsertOK(
		        @RequestParam("objection_content") String objection_Content,
		        RedirectAttributes redirectAttributes, HttpSession session,
		        @RequestParam("lect_idx") String lect_idx) {
		    try {
		        ModelAndView mv = new ModelAndView();
		        // 세션에서 s_idx, lect_idx 가져오기
		        Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
		        String s_idx = String.valueOf(sInfo.get("s_idx"));		        
		        // enroll_idx 조회
		        String enroll_idx = sScoreSearchService.getEnrollIdx(s_idx, lect_idx);
		        System.out.println("s_idx: " + s_idx);
		        System.out.println("lect_idx: " + lect_idx);
		        System.out.println("enroll_idx: " + enroll_idx);

		        // 파라미터 맵 구성
		        Map<String, Object> params = new HashMap<>();
		        mv.addObject("lect_idx", lect_idx);
		        params.put("enroll_idx", enroll_idx);
		        params.put("objection_content", objection_Content);
		        params.put("objection_active", 0);  // 기본 미처리 상태

		        // INSERT 실행
		        sScoreSearchService.sScoreSearchObjectionDetailInsertOK(params);
		        
		        mv.setViewName("redirect:/sScoreSearchObjectionManagement");
		        return mv;
		    } catch (Exception e) {
		        e.printStackTrace();
		        return null;
		    }
	  }
	  // 이의 제기 삭제
	  @PostMapping("/sScoreSearchObjectionDetailDeleteOK")
	  public ModelAndView sScoreSearchObjectionDetailDeleteOK(HttpSession session,  @RequestParam Map<String, String> paramMap) {
		  try {
			  ModelAndView mv = new ModelAndView();
			  
			  // 세션에서 s_idx, lect_idx 가져오기
			  Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
			  String s_idx = String.valueOf(sInfo.get("s_idx"));		        
			  paramMap.put("s_idx", s_idx);
			  
			  
			  // Delete 실행
			  sScoreSearchService.sScoreSearchObjectionDetailDeleteOK(paramMap);
			  
			  mv.setViewName("redirect:/sScoreSearchObjectionManagement");
			  return mv;
		  } catch (Exception e) {
			  e.printStackTrace();
			  return null;
		  }
	  }
		@PostMapping("/objectionPwCheck")
		@ResponseBody
		public String lectureDeletePwCheck(@RequestParam("inputPwd") String inputPwd, HttpSession session) {
		    // 로그인된 관리자 세션에서 user_id, user_pw 가져오기
		    UserVO user = (UserVO) session.getAttribute("uvo");
		    if (user == null) return "SESSION_FAIL";
		    String savedPw = user.getUser_pw();  // 세션에서 저장된 암호화된 비밀번호
		    // bcrypt 체크
		    if (passwordEncoder.matches(inputPwd, savedPw)) {
		        return "OK";
		    } else {
		        return "FAIL";
		    }
		}
		
  }
 