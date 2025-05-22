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
import com.university.nuri.service.adminservice.SubjectSetService;
import com.university.nuri.vo.adminvo.SubjectSetVO;


@Controller
public class SubjectSetController {
	@Autowired
	private SubjectSetService subjectSetService;
	@Autowired
	private DeptService deptService;
	@Autowired
	private SubjectService subjectService;
	
	// 강의관리 -> 과목군 리스트 탭
		@GetMapping("/subjectSetList")
		public ModelAndView subjectSetList(HttpServletRequest request) {
	        ModelAndView mv = new ModelAndView();
	        try {
	            List<Map<String, Object>> subjectSetList = subjectSetService.getAllSubjectSetList();
	          
	            mv.addObject("subjectSetList", subjectSetList);	           
	            mv.setViewName("admin/lect/lectList");
	            return mv;
	        } catch (Exception e) {
	           
	            e.printStackTrace();
	            mv.setViewName("admin/lect/lectList");
	            mv.addObject("subjectSetList", null);
	            mv.addObject("errorMessage", "과목군 리스트 조회 중 오류 발생: " + e.getMessage());
	            return mv;
	        }
	    }
	// 과목군 조건 검색
		@GetMapping("/searchSubjectSet")
		public ModelAndView searchSubjectSet(@RequestParam(value = "searchType", required = false) String searchType,
				@RequestParam(value = "keyword", required = false) String keyword,
				HttpServletRequest request) {
			ModelAndView mv = new ModelAndView();
			try {
	            
	            List<Map<String, Object>> subjectSetList = subjectSetService.searchSubjectSet(searchType, keyword);
	            if (subjectSetList == null) {
	                System.out.println("Warning: subjectSetList is null, returning empty list");
	                subjectSetList = Collections.emptyList();
	            }
	           

	            mv.addObject("subjectSetList", subjectSetList);

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
		// 강의관리 -> 과목군 등록 패이지 이동
		@GetMapping("/moveInsertSubjectSet")
		public ModelAndView moveInsertSubjectSet() {
			try {
				ModelAndView mv = new ModelAndView();
				// 학과 정보 가지고 이동
				List<Map<String, Object>> deptList = deptService.getAllDeptList();
				// 모달 해당 학과 과목 리스트
				List<Map<String, Object>> subjectList = subjectService.getAllSubjectList();
				
				mv.addObject("deptList", deptList);
				mv.addObject("subjectList", subjectList);
				mv.setViewName("admin/lect/insertSubjectSet");
				return mv;
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("error");
			}			
		}
		
		// 과목군 등록
		@PostMapping("/insertSubjectSet")
		public ModelAndView insertSubjectSet(
		        SubjectSetVO subjectSetVO,
		        @RequestParam(required = false) String selectedSubjectsIdx) {
		    try {
		        ModelAndView mv = new ModelAndView();
		        // 중복 검사
		        if (subjectSetService.chkSubSetName(subjectSetVO.getSub_set_name())) {
		            mv = new ModelAndView("admin/lect/insertSubjectSet");
		            mv.addObject("errorMessage", "이미 존재하는 과목군명입니다.");
		            mv.addObject("sub_set_name", subjectSetVO.getSub_set_name());
		            mv.addObject("dept_idx", subjectSetVO.getDept_idx());
		            mv.addObject("selectedSubjectsIdx", selectedSubjectsIdx);
		            mv.addObject("deptList", deptService.getAllDeptList());
		            mv.addObject("subjectList", subjectService.getAllSubjectList());
		            return mv;
		        }

		        // subjectIdxArr 생성
		        String[] subjectIdxArr = selectedSubjectsIdx != null ? selectedSubjectsIdx.split(",") : new String[0];

		        if (subjectIdxArr.length == 0) {
		            mv.setViewName("redirect:/moveInsertSubjectSet");
		            return mv;
		        }

		        // sub_set_num 생성 (서비스에서 자동 생성하도록 수정)
		        String result = subjectSetService.insertSubjectSet(subjectSetVO, subjectIdxArr);
		        subjectSetVO.setSub_set_num(result);
		        if (! result.isEmpty()) {
		            mv.setViewName("redirect:/detailSubjectSet?sub_set_num=" + subjectSetVO.getSub_set_num());
		            return mv;
		        }
		        mv.setViewName("redirect:/subjectSetList");
		        return mv;

		    } catch (Exception e) {
		        e.printStackTrace();
		        return new ModelAndView("error");
		    }
		}
		
		// 과목군 자세히 보기
		@GetMapping("/detailSubjectSet")
		public ModelAndView detailSubjectSet(@RequestParam int sub_set_num) {
			try {
				ModelAndView mv = new ModelAndView();
				List<Map<String, Object>> detailSubjectSet = subjectSetService.detailSubjectSet(sub_set_num);
				if (detailSubjectSet.isEmpty()) {					
					mv.setViewName("redirect:/subjectSetList");
				    return mv;
				}
				mv.addObject("detailSubjectSet", detailSubjectSet);
				mv.setViewName("admin/lect/detailSubjectSet");
				return mv;
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("error");
			}
		}
		// 과목군 수정페이지 이동
		@GetMapping("/moveUpdateSubjectSet")
		public ModelAndView moveUpdateSubjectSet(@RequestParam("sub_set_num") int sub_set_num) {
		    try {
		        ModelAndView mv = new ModelAndView();
		        System.out.println("Received sub_set_num: " + sub_set_num);		       
		        List<Map<String, Object>> detailSubjectSet = subjectSetService.detailSubjectSet(sub_set_num);
		        if (detailSubjectSet.isEmpty()) {
		            mv.setViewName("redirect:/subjectSetList");
		            return mv;
		        }
		        List<Map<String, Object>> deptList = deptService.getAllDeptList();
		        List<Map<String, Object>> subjectList = subjectService.getAllSubjectList();
		        mv.addObject("deptList", deptList);
		        mv.addObject("subjectList", subjectList);
		        mv.addObject("detailSubjectSet", detailSubjectSet);
		        mv.setViewName("admin/lect/updateSubjectSet");		       
		        return mv;
		    } catch (Exception e) {
		        e.printStackTrace();
		        return new ModelAndView("error");
		    }
		}
		
		// 과목군 수정
		@PostMapping("/updateSubjectSet")
		public ModelAndView updateSubjectSet(
		        SubjectSetVO subjectSetVO,
		        @RequestParam(required = false) String selectedSubjectsIdx) {
		    try {
		        ModelAndView mv = new ModelAndView();

		     // subjectIdxArr 준비
		        String[] subjectIdxArr = (selectedSubjectsIdx != null)
		            ? selectedSubjectsIdx.split(",")
		            : new String[0];
		        if (subjectIdxArr.length == 0) {
		            throw new IllegalArgumentException("최소 한 개 이상의 과목을 선택해야 합니다.");
		        }


		      

		        // sub_set_num 유효성 체크
		        String subSetNum = subjectSetVO.getSub_set_num();
		        if (subSetNum == null || subSetNum.isEmpty()) {
		            throw new IllegalArgumentException("과목군 번호가 없습니다.");
		        }

		        int subSetNumInt = Integer.parseInt(subSetNum.trim());
		        // 서비스 호출 후 새 sub_set_num 반환
		        int newSubSetNum = subjectSetService.updateSubjectSet(subjectSetVO, subSetNumInt, subjectIdxArr);
		        if (newSubSetNum > 0) {
		            mv.setViewName("redirect:/detailSubjectSet?sub_set_num=" + newSubSetNum);
		            return mv;
		        }

		        mv.setViewName("redirect:/subjectSetList");
		        mv.addObject("errorMessage", "과목군 수정에 실패했습니다.");
		        return mv;

		    } catch (Exception e) {
		        e.printStackTrace();
		        ModelAndView mv = new ModelAndView("error");
		        mv.addObject("errorMessage", "과목군 수정 중 오류 발생: " + e.getMessage());
		        return mv;
		    }
		}
		// 과목군 삭제
		@GetMapping("/deleteSubjectSet")
		public ModelAndView deleteSubjectSet(@RequestParam int sub_set_num) {
			try {
				ModelAndView mv = new ModelAndView();
				int result = subjectSetService.deleteSubjectSet(sub_set_num);

				if (result > 0) {
					mv.setViewName("redirect:/subjectSetList");
					return mv;
				} else {
					mv.setViewName("redirect:/detailSubjectSet?sub_set_num=" + sub_set_num);
				}
				return mv;
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("/error");
			}
		}
		 
}
