package com.university.nuri.controller.admincontroller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.university.nuri.service.adminservice.DeptService;
import com.university.nuri.vo.adminvo.DepartMentVO;
@Controller
public class DeptController {
	@Autowired
	private DeptService deptService;
	
		//학과관리
		// admin 네비 -> 학과 관리 연결(전체 리스트 불러오기)
		@GetMapping("/deptList")
		public ModelAndView deptList() {
			try {
				ModelAndView mv = new ModelAndView();
				List<Map<String, Object>> deptList = deptService.getAllDeptList();
				mv.addObject("deptList", deptList);
				mv.setViewName("admin/dept/deptList");
				return mv;
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("error");
			}			
			
		}
		// 학과관리 -> 학과등록 페이지 이동
		@GetMapping("/moveInsertDept")
		public ModelAndView moveInsertDept() {
			return new ModelAndView("admin/dept/insertDept");
		}
		
		// 학과등록
		@PostMapping("/insertDept")
		public ModelAndView insertDept(DepartMentVO deptVO) {
			try {
				ModelAndView mv = new ModelAndView();
				 String deptId;
			        do {
			            deptId = String.format("%03d", (int)(Math.random() * 100));
			        } while (deptService.chkDeptId(deptId) > 0);  
				
				deptVO.setDept_id(deptId);
				int result = deptService.insertDept(deptVO);
				if (result > 0) {
					mv.setViewName("redirect:/detailDept?dept_idx=" + deptVO.getDept_idx());
					return mv;				
				}else {
					mv.setViewName("redirect:/moveInsertDept");
					return mv;	
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("/error");
			}
		}
		
		// 학과관리 -> 검색
		@GetMapping("/searchDept")
		public ModelAndView searchDept(
				@RequestParam(value = "searchType", required = false) String searchType,
				@RequestParam(value = "keyword" , required = false) String keyword ) {
			try {
				ModelAndView mv = new ModelAndView();
				// 디버깅 로그
	            System.out.println("Search Parameters - searchType: " + searchType + ", keyword: " + keyword);

				List<Map<String, Object>> deptList = deptService.searchDept(searchType, keyword);
				
				mv.addObject("deptList", deptList);
				mv.setViewName("admin/dept/deptList");
				return mv;
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("/error");
			}
		}

		// 학과장 미배정 학과 보기
		@GetMapping("/searchNullTIdx")
		public ModelAndView searchNullTIdx() {
			try {
				ModelAndView mv = new ModelAndView();
				List<Map<String, Object>> deptList = deptService.searchNullTIdx();
				
				mv.addObject("deptList", deptList);
				mv.setViewName("admin/dept/deptList");
				return mv;
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("/error");
			}
		}
		
		// 학과 자세히 보기
		@GetMapping("/detailDept")
		public ModelAndView detailDept(String dept_idx) {
			try {
				ModelAndView mv = new ModelAndView();
				Map<String, Object> detailDept = deptService.detailDept(dept_idx);
				
				mv.addObject("detailDept",detailDept);
				mv.setViewName("admin/dept/detailDept");
				return mv;
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("/error");
			}
		}
		
		// 학과정보 수정페이지 이동
		@GetMapping("/moveUpdateDept")
		public ModelAndView moveUpdateDept(String dept_idx) {
			try {
				ModelAndView mv = new ModelAndView();
				Map<String, Object> detailDept = deptService.detailDept(dept_idx);
				
				// 해당 학과 교수 조회
				List<Map<String, Object>> teacherList = deptService.teacherList(dept_idx);
				
				mv.addObject("detailDept", detailDept);
				mv.addObject("teacherList",teacherList);
				mv.setViewName("admin/dept/updateDept");
				return mv;
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("/error");
			}
		}
		
		// 학과정보 수정
		@GetMapping("/updateDept")
		public ModelAndView updateDept(DepartMentVO deptVO) {
			try {
				ModelAndView mv = new ModelAndView();
				int result = deptService.updateDept(deptVO);
				if (result > 0) {
					String dept_idx = deptVO.getDept_idx();
					mv.addObject("dept_idx",dept_idx);
					mv.setViewName("redirect:/detailDept?dept_idx="+dept_idx);
					return mv;				
				}else {
					mv.setViewName("redirect:/moveUpdateDept");
				}
				    return mv;
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("/error");
			}
			
		}
		// 학과 삭제
		@GetMapping("/deleteDept")
		public ModelAndView deleteDept(@RequestParam String dept_idx) {
			try {
				ModelAndView mv = new ModelAndView();
				DepartMentVO deptVO = new DepartMentVO();
				deptVO.setDept_idx(dept_idx);
				
				int result = deptService.deleteDept(deptVO);

				if (result > 0) {
					mv.setViewName("redirect:/deptList");
					return mv;
				} else {
					mv.setViewName("redirect:/detailDept?dept_idx="+dept_idx);
				}
				return mv;
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("/error");
			}
		}
		
		
}
