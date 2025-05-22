package com.university.nuri.controller.admincontroller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.university.nuri.service.adminservice.AccessService;
import com.university.nuri.vo.commonvo.AccessVO;

@Controller
public class AccessController {
	@Autowired
	private AccessService accessService;
	
	@GetMapping("/access")
	public ModelAndView getAccess(
	    @RequestParam(value = "a_grade", required = false) String a_grades
	) {
	    ModelAndView mv = new ModelAndView();

	    // 🔐 처음 진입 시 기본값은 중간 관리자("1")
	    String gradeToQuery = (a_grades != null && !a_grades.trim().isEmpty())
	        ? a_grades.trim()
	        : "1";  // ← 여기 중요!

	    // 🔍 등급별 권한 정보 불러오기
	    AccessVO access = accessService.getAccessByGrade(gradeToQuery);

	    mv.addObject("access", access);               // 체크박스 표시용
	    mv.addObject("selectedGrade", gradeToQuery);  // select 유지용
	    mv.setViewName("admin/access/access");

	    return mv;
	}
	// 특정 등급에 대한 권한 처리 OK
	@PostMapping("/accessSave")
	public String saveAccess(@ModelAttribute AccessVO accessVO, RedirectAttributes redirectAttributes, HttpSession session) {

	    accessService.updateAccess(accessVO); // accessVO에 a_grade 포함되어 있어야 함
	    // 세션의 내 등급과 수정한 등급이 같으면 세션 갱신
	    Map<String, Object> aInfo = (Map<String, Object>) session.getAttribute("aInfo");
	    String myGrade = String.valueOf(aInfo.get("a_grade"));
	    String changedGrade = accessVO.getA_grade();
	    if (myGrade.equals(changedGrade)) {
	        AccessVO updatedAccess = accessService.getAccessByGrade(myGrade);
	        session.setAttribute("access", updatedAccess); // 🔁 즉시 세션 갱신!
	    }
	    redirectAttributes.addFlashAttribute("msg", "권한이 저장되었습니다.");
	    
	    
	    
	    return "redirect:/access?a_grade=" + accessVO.getA_grade(); // 다시 불러올 때도 같은 등급 조회
	}
}
