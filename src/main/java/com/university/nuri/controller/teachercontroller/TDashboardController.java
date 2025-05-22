package com.university.nuri.controller.teachercontroller;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.service.teacherservice.TDashboardService;

@Controller
public class TDashboardController {
	@Autowired
	private TDashboardService dashboardService;

	@GetMapping("t-dashboard")
	public ModelAndView tDashboard(HttpSession session) {
		Map<String, Object> tInfo = (Map<String, Object>) session.getAttribute("tInfo");
		String t_idx = String.valueOf(tInfo.get("t_idx"));
		System.out.println(t_idx);
		ModelAndView mv = new ModelAndView();

		// 교수정보 => 이름, 학과, 학번
		Map<String, Object> getTInfo = dashboardService.getTInfo(t_idx);
		mv.addObject("tInfo", getTInfo);

		// 이의신청 개수
		int getobjectCount = dashboardService.getobjectCount(t_idx);
		mv.addObject("objectCount", getobjectCount);

		// 총 진행 강의 개수
		int getAllLecCount = dashboardService.getAllLecCount(t_idx);
		mv.addObject("allLecCount", getAllLecCount);

		//오늘 날짜
		LocalDate today = LocalDate.now();
		String formattedDate = today.getMonthValue() + "월 " + today.getDayOfMonth() + "일 "
				+ today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
		mv.addObject("todayDate", formattedDate);

		
		//금학기 강의 리스트 
		List<Map<String, Object>> tthisLectList = dashboardService.tthisLectList(t_idx);
		mv.addObject("lectureList", tthisLectList);
		mv.addObject("listCount", tthisLectList.size());
		
		//오늘 진행 강의
		int rawDayOfWeek = LocalDate.now().getDayOfWeek().getValue(); 
		int todayDay = (rawDayOfWeek % 7) + 1;
		System.out.println(rawDayOfWeek+"/"+todayDay);
		List<Map<String,Object>> ttodayLectList= dashboardService.ttodayLectList(t_idx, todayDay);
		mv.addObject("ttodayLectList", ttodayLectList);
		
		mv.setViewName("teacher/dashboard");
		return mv;
	}
}
