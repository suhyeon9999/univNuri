package com.university.nuri.controller.admincontroller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.service.adminservice.DeptService;
import com.university.nuri.service.adminservice.SubjectService;
import com.university.nuri.vo.adminvo.SubjectVO;

@Controller
public class SubjectController {
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private DeptService deptService;

	// 강의관리 -> 과목 리스트탭
	@GetMapping("/subjectList")
	public ModelAndView subjectList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			// 데이터 조회 로그
			List<Map<String, Object>> subjectList = subjectService.getAllSubjectList();
		
			mv.addObject("subjectList", subjectList);

			// AJAX 헤더 로그
			String requestedWith = request.getHeader("X-Requested-With");
		

			// 수정: AJAX 요청 여부에 상관없이 동일한 JSP 페이지를 반환
			// JSP는 프래그먼트를 직접 지원하지 않으므로 클라이언트에서 #subjectList 추출
			mv.setViewName("admin/lect/lectList");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("error");
			mv.addObject("errorMessage", "과목 리스트 조회 중 오류 발생: " + e.getMessage());
			return mv;
		}
	}

	// 강의관리 -> 과목 등록 패이지 이동
	@GetMapping("/moveInsertSubject")
	public ModelAndView moveInsertSubject() {
		try {
			ModelAndView mv = new ModelAndView();
			// 학과 정보 가지고 이동
			List<Map<String, Object>> deptList = deptService.getAllDeptList();
			mv.addObject("deptList", deptList);
			mv.setViewName("admin/lect/insertSubject");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error");
		}
	}

	// 과목 등록
	@PostMapping("/insertSubject")
	public ModelAndView insertSubject(SubjectVO subjectVO) {
		try {
			ModelAndView mv = new ModelAndView();
			int result = subjectService.insertSubject(subjectVO);
			if (result > 0) {
				mv.setViewName("redirect:/detailSubject?subject_idx=" + subjectVO.getSubject_idx());
				return mv;

			} else {
				mv.setViewName("redirect:/moveInsertSubject");
				return mv;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error");
		}
	}

	// 과목 상세보기
	@GetMapping("/detailSubject")
	public ModelAndView detailSubject(@RequestParam String subject_idx) {
		try {
			ModelAndView mv = new ModelAndView();
			Map<String, Object> detailSubject = subjectService.detailSubject(subject_idx);

			mv.addObject("detailSubject", detailSubject);
			mv.setViewName("admin/lect/detailSubject");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error");
		}
	}

	// 과목 삭제
	@GetMapping("/deleteSubject")
	public ModelAndView deleteSubject(@RequestParam String subject_idx) {
		try {
			ModelAndView mv = new ModelAndView();
			int result = subjectService.deleteSubject(subject_idx);

			if (result > 0) {
				mv.setViewName("redirect:/subjectList");
				return mv;
			} else {
				mv.setViewName("redirect:/detailSubject?subject_idx=" + subject_idx);
			}
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("/error");
		}
	}

	// 과목 조건 검색
	@GetMapping("/searchSubject")
	public ModelAndView searchSubject(@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "keyword", required = false) String keyword,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
            
            List<Map<String, Object>> subjectList = subjectService.searchSubject(searchType, keyword);
            if (subjectList == null) {
                System.out.println("Warning: subjectList is null, returning empty list");
                subjectList = Collections.emptyList();
            }
           

            mv.addObject("subjectList", subjectList);

            // AJAX 헤더 로그
            String requestedWith = request.getHeader("X-Requested-With");
            System.out.println("X-Requested-With: " + requestedWith);

            // JSP 반환
            mv.setViewName("admin/lect/lectList");
            return mv;
        } catch (Exception e) {
            System.err.println("Error in searchSubject: " + e.getMessage());
            e.printStackTrace();
            mv.setViewName("error");
            mv.addObject("errorMessage", "과목 검색 중 오류 발생: " + e.getMessage());
            return mv;
        }
    }
	
	}


