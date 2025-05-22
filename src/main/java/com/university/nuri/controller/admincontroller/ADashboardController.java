package com.university.nuri.controller.admincontroller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.service.adminservice.ADashboardService;
import com.university.nuri.service.adminservice.AEnrollService;

@Controller
public class ADashboardController {
	@Autowired ADashboardService aDashboardService;
	@Autowired AEnrollService aEnrollService;
	
	@GetMapping("/aDashboard")
	public ModelAndView getDashboard() {
		try {
		        ModelAndView mv = new ModelAndView("admin/aDashboard");

		        mv.addObject("studentCount", aDashboardService.getStudentCount());
		        mv.addObject("teacherCount", aDashboardService.getTeacherCount());
		        mv.addObject("deptCount", aDashboardService.getDeptCount());
		        mv.addObject("currentLectCount", aDashboardService.getCurrentLectureCount());
		        mv.addObject("pendingRequestCount", aDashboardService.getPendingRequestCount());

		        Map<String, Object> enrollApplyInfo = aDashboardService.getEnrollApplyInfo();
		        
		        
		    
		        Map<String, Object> EnrollApplyDashBoard = aDashboardService.getEnrollApplyDashBoard();
		        
		        
		        List<Map<String, Object>> lectList = aEnrollService.searchEnrollApplyLecturesReservationLook();
		        
		        mv.addObject("lectListCheckCount", lectList.size());
		        mv.addObject("EnrollApplyDashBoard", EnrollApplyDashBoard);
		        return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
