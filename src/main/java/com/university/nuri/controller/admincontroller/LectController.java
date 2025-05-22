package com.university.nuri.controller.admincontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.service.adminservice.DeptService;
import com.university.nuri.service.adminservice.LectService;
import com.university.nuri.service.adminservice.SubjectSetService;

@Controller
public class LectController {
	@Autowired
	private LectService lectService;
	@Autowired
	private DeptService deptService;
	@Autowired
	private SubjectSetService subjectSetService;
	
	//강의관리
	// admin 네비 -> 강의 관리 연결
	@GetMapping("/lectList")
	public ModelAndView lectList() {
		try {
			ModelAndView mv = new ModelAndView();
			// 강의리스트
			List<Map<String, Object>> lectList = lectService.getAllLectList();
			mv.addObject("lectList",lectList);
			mv.setViewName("admin/lect/lectList");
			return mv;
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error");
		}
		
		
	}
	
	
	// 강의관리 -> 정보 미입력 강의보기
	@GetMapping("/searchNullLect")
	public ModelAndView searchNullLect(){
		try {
			ModelAndView mv = new ModelAndView();
			List<Map<String, Object>> lectList = lectService.searchNullLect();
			mv.addObject("lectList",lectList);
			mv.setViewName("admin/lect/lectList");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error");
		}
	}
	
	// 강의관리 -> 검색
	@GetMapping("/searchLect")
	public ModelAndView searchLect(
			@RequestParam(value = "lect_id", required = false) String lect_id,
		    @RequestParam(value = "lect_name", required = false) String lect_name,
		    @RequestParam(value = "dept_name", required = false) String dept_name,
		    @RequestParam(value = "lect_day", required = false) String lect_day,
		    @RequestParam(value = "name", required = false) String name) {
		try {
			ModelAndView mv = new ModelAndView();
			List<Map<String, Object>> lectList = new ArrayList<>();
			  if (lect_id != null && !lect_id.isEmpty()) {
				  	lectList = lectService.searchLectId(lect_id);
		        } else if (lect_name != null && !lect_name.isEmpty()) {
		        	lectList = lectService.searchLectName(lect_name);
		        } else if (dept_name != null && !dept_name.isEmpty()) {
		        	 lectList = lectService.searchDeptName(dept_name);
		        } else if (lect_day != null && !lect_day.isEmpty()) {
		        	lectList = lectService.searchLectDay(lect_day);
		        } else if (name != null && !name.isEmpty()) {
		        	lectList = lectService.searchName(name);
		        }
			  	mv.addObject("lectList",lectList);
				mv.setViewName("admin/lect/lectList");
				return mv;
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error");
		}
		
		
	}
	
	// 강의관리 -> 강의상세보기
	@GetMapping("/detailLect")
	public ModelAndView detailLect(String lect_idx) {
		try {
			ModelAndView mv = new ModelAndView();
			Map<String, Object> detailLect = lectService.detailLect(lect_idx);
			List<Map<String, Object>> lectSubSet = lectService.lestSubSetList(lect_idx);			
			mv.addObject("lectSubSet",lectSubSet);
			mv.addObject("detailLect",detailLect);
			mv.setViewName("admin/lect/detailLect");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error");
		}
	}
	
	
	
	
	
//	// 강의관리 -> 강의등록 페이지 이동
//	@GetMapping("/moveInsertLect")
//	public ModelAndView moveInsertLect() {
//		ModelAndView mv = new ModelAndView();
//		try {
//			// 학과 정보 가지고 이동
//			List<Map<String, Object>> deptList = deptService.getAllDeptList();			
//			mv.addObject("deptList", deptList);
//			// 과목군 정보 가지고 이동
//			List<Map<String, Object>> subjectSetList = subjectSetService.getAllSubjectSetList();
//			mv.addObject("subjectSetList", subjectSetList);	                
//			// 강의실 정보
//			List<Map<String, Object>> classList = lectService.getAllClassList();
//			mv.addObject("classList",classList);
//			// 교수정보
//			List<Map<String, Object>> teacherList = lectService.getAllTeacherListList();
//			mv.addObject("teacherList",teacherList);
//			mv.setViewName("admin/lect/insertLect");
//			
//	            return mv;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ModelAndView("error");
//		}
//		
//		
//	
//	}
//	

	
	
	
	
	
}
