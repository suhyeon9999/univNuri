package com.university.nuri.controller.studentcontroller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openxmlformats.schemas.spreadsheetml.x2006.main.STSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.university.nuri.service.studentservice.SEnrollApplyService;
import com.university.nuri.vo.commonvo.EnrollApplyVO;

@Controller
public class SEnrollApplyController {
	@Autowired
	private SEnrollApplyService sEnrollApplyService;
	@RequestMapping("/senrollapply")
	public ModelAndView sEnrollSearchLecture(@RequestParam Map<String, String> params, HttpSession session, RedirectAttributes redirect) {
	    ModelAndView mv = new ModelAndView();

	    // 🔒 수강 신청 가능 기간 조회
	    List<EnrollApplyVO> applyPeriodList = sEnrollApplyService.GetCurrentEnrollPeriod();
	    if (applyPeriodList == null || applyPeriodList.isEmpty()) {
	        redirect.addFlashAttribute("msg", "수강신청 기간이 설정되지 않았습니다.");
	        mv.setViewName("redirect:/dashboard");
	        return mv;
	    }

	    LocalDateTime now = LocalDateTime.now();
	    boolean isInPeriod = false;

	    for (EnrollApplyVO period : applyPeriodList) {
	        LocalDateTime start = LocalDateTime.parse(period.getStart_time(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	        LocalDateTime end = LocalDateTime.parse(period.getEnd_time(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

	        if (!now.isBefore(start) && !now.isAfter(end)) {
	            isInPeriod = true;
	            break; // 유효한 기간 하나라도 있으면 탈출
	        }
	    }

	    if (!isInPeriod) {
	        redirect.addFlashAttribute("msg", "수강신청 가능 기간이 아닙니다.");
	        mv.setViewName("redirect:/dashboard");
	        return mv;
	    }

	    // 👤 세션 정보에서 학생 s_idx 추출
	    Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
	    String s_idx = String.valueOf(sInfo.get("s_idx"));
	    params.put("s_idx", s_idx);

	    // 📋 상단: 신청 가능한 강의 목록
	    List<Map<String, Object>> lectureList = sEnrollApplyService.SearchLectureList(params);
	    mv.addObject("lectureList", lectureList);

	    // 📋 하단: 신청한 강의 목록
	    List<Map<String, Object>> enrolledLectureList = sEnrollApplyService.GetEnrolledLectureList(s_idx);
	    
	    mv.addObject("enrolledLectureList", enrolledLectureList);

	    // 🧮 학점 계산
	    int maxCredit = sEnrollApplyService.getMaxCredit(s_idx);
	    int currentCredit = enrolledLectureList.stream()
	        .mapToInt(e -> Integer.parseInt(String.valueOf(e.get("lect_credit"))))
	        .sum();
	    mv.addObject("max_credit", maxCredit);
	    mv.addObject("current_credit", currentCredit);

	    // 🔍 검색 조건 유지
	    mv.addObject("search", params);

	    mv.setViewName("student/enrollapply/sEnrollApply");
	    return mv;
	}
	//수강 신청OK
	@PostMapping("/sEnrollApplyOK")
	public String sEnrollApplyOK(@RequestParam("lect_idx") int lect_idx, HttpSession session, RedirectAttributes redirectAttributes) {
	    // 세션에서 학생 정보 가져오기
	    Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
	    String s_idx = String.valueOf(sInfo.get("s_idx"));
	    System.out.println(">>> lect_idx = " + lect_idx); // 숫자 잘 찍히는지
	    // 파라미터 묶기
	    Map<String, Object> params = new HashMap<>();
	    params.put("s_idx", s_idx);
	    params.put("lect_idx", lect_idx);
	    //중복방지 로직
	    Integer status = sEnrollApplyService.getEnrollStatus(params);
	    if(status == null) {
	    	//처음 신청
	    	int result = sEnrollApplyService.InsertEnroll(params);
	    	// 수강신청 insert 호출
	    	if (result > 0) {
	    		redirectAttributes.addFlashAttribute("applySuccess", true);
	    	} else {
	    		redirectAttributes.addFlashAttribute("applySuccess", false);
	    	}
	    }else if (status == 2) {
	    	//취소했던 강의 재신청
	    	sEnrollApplyService.ReApplyEnroll(params);
	    	redirectAttributes.addFlashAttribute("applySuccess", true);
	    }else {
	    	//이미 신청 또는 확정
	    	redirectAttributes.addFlashAttribute("applySuccess", false);
	    }


	    return "redirect:/senrollapply"; // 다시 리스트로
	}
	//수강 신청 cancel
	@PostMapping("/sEnrollApplyCancel")
	public String sEnrollApplyCancel(@RequestParam("lect_idx") int lect_idx, HttpSession session, RedirectAttributes redirectAttributes) {
		// 세션에서 학생 정보 가져오기
		Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
		String s_idx = String.valueOf(sInfo.get("s_idx"));
		System.out.println(">>> lect_idx = " + lect_idx); // 숫자 잘 찍히는지
		// 파라미터 묶기
		Map<String, Object> params = new HashMap<>();
		params.put("s_idx", s_idx);
		params.put("lect_idx", lect_idx);
		
		// 수강신청 insert 호출
		int result = sEnrollApplyService.CancelEnroll(params);
		
		if (result > 0) {
			redirectAttributes.addFlashAttribute("cancelSuccess", true);
		} else {
			redirectAttributes.addFlashAttribute("cancelSuccess", false);
		}
		
		return "redirect:/senrollapply"; // 다시 리스트로
	}

}
