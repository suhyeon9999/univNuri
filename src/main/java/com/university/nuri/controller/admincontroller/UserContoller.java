package com.university.nuri.controller.admincontroller;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.university.nuri.service.adminservice.UserService;
import com.university.nuri.vo.adminvo.AdminVO;
import com.university.nuri.vo.commonvo.UserVO;
import com.university.nuri.vo.studentvo.StudentVO;
import com.university.nuri.vo.teachervo.TeacherVO;


@Controller
public class UserContoller {

	@Autowired 
	private UserService userService;

	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;



	// admin 네비 -> [관리자 관리] 연결
		// admin 네비 -> 관리자 관리 연결(전체 리스트 불러오기)
		@GetMapping("/adminList")
		public ModelAndView adminList() {
			try {
				ModelAndView mv = new ModelAndView();
				List<Map<String, Object>> adminList = userService.getAllAdminList();


				mv.addObject("adminList", adminList);
				mv.setViewName("admin/userpage/adminList");

				return mv;
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("/error");
			}
		}


		// 관리자 관리-> 관리자 등록페이지 이동
		@GetMapping("/moveInsertAdmin")
		public ModelAndView moveInsertAdmin() {
			return new ModelAndView("admin/userpage/insertAdmin");
		}




	// 관리자 등록
		@PostMapping("/adminInsert")
		public ModelAndView adminInsert(@ModelAttribute UserVO userVO, AdminVO adminVO) {
			try {
				ModelAndView mv = new ModelAndView();
				// 입력받은 정보에서 생년월일6자리 + 핸드폰 뒷 4자리 조합 -> 비밀번호 저장		
				// 생년월일 처리
				String birth6 = userVO.getBirth().replaceAll("-", "").substring(2, 8);
				// 휴대폰번호 처리
				String phoneDigits = userVO.getPhone().replaceAll("[^0-9]", "");
				String phoneLast4 = phoneDigits.substring(phoneDigits.length() - 4);
				// 비밀번호 생성
				String rawPw = birth6 + phoneLast4;
				// 암호화
				userVO.setUser_pw(passwordEncoder.encode(rawPw));			

				// 유저레벨, 재직상태 
				userVO.setUser_level("2");
				userVO.setStatus("4");

				// id지정 (현재 년도2자리+랜덤3자리)
				int currentYear = LocalDate.now().getYear();
				int shortYear = currentYear % 100;
				String adminId;
				do {
					adminId = shortYear+String.format("%03d", (int)(Math.random() * 100));

				} while (userService.chkAdminId(adminId) > 0);
				userVO.setUser_id(adminId);			



				int result = userService.adminInsert(userVO,adminVO);
				if (result > 0) {
					mv.setViewName("redirect:/detailAdmin?user_idx=" + adminVO.getUser_idx());
					System.out.println(adminVO.getUser_idx());
					return mv;				
				}else {
					mv.setViewName("redirect:/moveInsertAdmin");
					System.out.println("3번");
				}

				System.out.println("4번");
				return mv;				

			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("redirect:/adminList");
			}
		}

	// 선생 등록
	@PostMapping("/teacherInsert")
	public ModelAndView teacherInsert(@ModelAttribute UserVO userVO, TeacherVO teacherVO) {
		try {
			ModelAndView mv = new ModelAndView();
			// 입력받은 정보에서 생년월일6자리 + 핸드폰 뒷 4자리 조합 -> 비밀번호 저장		
			// 생년월일 처리
			String birth6 = userVO.getBirth().replaceAll("-", "").substring(2, 8);

			// 휴대폰번호 처리
			String phoneDigits = userVO.getPhone().replaceAll("[^0-9]", "");
			String phoneLast4 = phoneDigits.substring(phoneDigits.length() - 4);

			// 비밀번호 생성
			String rawPw = birth6 + phoneLast4;

			// 암호화
			userVO.setUser_pw(passwordEncoder.encode(rawPw));
			
			// 유저레벨, id지정
			userVO.setUser_level("1");
			userVO.setStatus("4");
			teacherVO.setDept_idx("2");
			int currentYear = LocalDate.now().getYear();
			// 사번 생성
			String yearstr = String.valueOf(currentYear); //현재년도
			int count = userService.countTeachersThisYear(yearstr, teacherVO.getDept_idx()); // 맴퍼를 통해 해당 연도 인원 조회
			String serial = String.format("%03d", count + 1); // 일련번호 001 최대값 +1
			String teacherNumber = "T" + yearstr + "00" + teacherVO.getDept_idx() + serial;
			userVO.setUser_id(teacherNumber); // 조합한 UserID 를 저장
			int result = userService.teacherInsert(userVO,teacherVO);
			if (result > 0) {
				mv.setViewName("redirect:/ateacherList");
				return mv;				
			}else {
				mv.setViewName("redirect:/amoveInsertTeacher");
			}
			return mv;				
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/ateacherList");
		}
	}
	// 관리자 관리 -> 검색
	@GetMapping("/searchAdmin")
	public ModelAndView searchAdmin(
	        @RequestParam(value = "name", required = false) String name,
	        @RequestParam(value = "a_grade", required = false) String a_grade) {
	    try {
	        ModelAndView mv = new ModelAndView();
	        System.out.println("파라미터: " + a_grade);
	        Map<String, Object> searchAdmin = new HashMap<>();
	        if (name != null && !name.isEmpty()) {
	            searchAdmin.put("name", name);
	        }
	        if (a_grade != null && !a_grade.isEmpty()) {
	            searchAdmin.put("a_grade", a_grade); 
	        }
	        List<Map<String, Object>> adminList = userService.searchAdmin(searchAdmin);

	        mv.addObject("adminList",adminList);
	        mv.setViewName("admin/userpage/adminList");
	        return mv;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ModelAndView("redirect:/moveInsertAdmin");
	    }
	}


	// 관리자 관리 -> 관리자 상세보기
	@GetMapping("/detailAdmin")
	public ModelAndView detailAdmin(@RequestParam("user_idx") String user_idx) {
		try {
			ModelAndView mv = new ModelAndView();
			Map<String, Object> detailAdmin = userService.getAdminDetail(user_idx);

			if (detailAdmin != null) {
				mv.addObject("detailAdmin",detailAdmin);
				mv.setViewName("admin/userpage/detailAdmin");
				return mv;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/moveInsertAdmin");
		}
		return new ModelAndView("/error");
	}



	// 관리자 수정페이지 이동
		@GetMapping("/moveUpdateAdmin")
		public ModelAndView moveUpdateAdmin(@RequestParam("user_idx") String user_idx) {
		    ModelAndView mv = new ModelAndView();
		    try {
		        Map<String, Object> detailAdmin = userService.getAdminDetail(user_idx);
		        if (detailAdmin != null) {
		            mv.addObject("detailAdmin", detailAdmin);
		            mv.setViewName("admin/userpage/updateAdmin");
		            return mv;
		        } else {
		            mv.setViewName("redirect:/adminList");
		            return mv;
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        mv.setViewName("redirect:/adminList");
		        mv.addObject("errorMessage", "관리자 정보를 불러오지 못했습니다.");
		        return mv;
		    }
		}
		// 관리자 수정
		@PostMapping("/updateAdmin")
		public ModelAndView updateAdmin(@ModelAttribute UserVO userVO, @ModelAttribute AdminVO adminVO) {
		    ModelAndView mv = new ModelAndView();
		    try {
		    	System.out.println("이메일"+userVO.getEmail());
		    	
		        if (userVO.getUser_idx() == null || userVO.getUser_idx().isEmpty()) {
		            mv.setViewName("redirect:/moveUpdateAdmin?user_idx=" + userVO.getUser_idx());
		            mv.addObject("errorMessage", "사용자 ID가 누락되었습니다.");
		            return mv;
		        }
		        int result = userService.updateAdmin(userVO, adminVO);
		        if (result > 0) {
		            mv.setViewName("redirect:/detailAdmin?user_idx=" + userVO.getUser_idx());
		        } else {
		            mv.setViewName("redirect:/moveUpdateAdmin?user_idx=" + userVO.getUser_idx());
		            mv.addObject("errorMessage", "관리자 정보 수정에 실패했습니다.");
		        }
		        return mv;
		    } catch (Exception e) {
		        e.printStackTrace();
		        mv.setViewName("redirect:/moveUpdateAdmin?user_idx=" + userVO.getUser_idx());
		        mv.addObject("errorMessage", "오류가 발생했습니다: " + e.getMessage());
		        return mv;
		    }
		}

		// 관리자 삭제
		@GetMapping("/deleteAdmin")
		public ModelAndView deleteAdmin(@RequestParam("user_idx") String user_idx) {
			try {
				ModelAndView mv = new ModelAndView();
				int result = userService.deleteAdmin(user_idx);

				if (result > 0) {
					mv.setViewName("redirect:/detailAdmin?user_idx=" + user_idx);
					return mv;
				} 
				mv.setViewName("redirect:/adminList");
				
				return mv;
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("/error");
			}
		}

	
		





	// admin 네비 -> [선생님 관리] 연결
	@GetMapping("/teacherList")
	public ModelAndView teacherList() {
		ModelAndView mv = new ModelAndView();
		// 페이징기법 들어가야함

		/* List<UserVO> userList = userService.getUserList(); */

		return new ModelAndView("admin/userpage/teacherList");
	}

	// 선생님 관리-> 선생님 등록페이지 이동
	@GetMapping("/moveInsertTeacher")
	public ModelAndView moveInsertTeacher() {
		return new ModelAndView("admin/userpage/insertTeacher");
	}

	// 선생님 관리 -> 선생님 수정 페이지
	@GetMapping("/updateTeacher")
	public ModelAndView updateTeacher() {
		return new ModelAndView("admin/userpage/updateTeacher");
	}

	// 선생님 관리 -> 학생 상세보기
	@GetMapping("/detailTeacher")
	public ModelAndView detailTeacher() {
		return new ModelAndView("admin/userpage/detailTeacher");
	}

	// 일괄등록 페이지
	@GetMapping("/moveInsertManyStudent")
	public ModelAndView moveInsertManyStudent() {
		return new ModelAndView("admin/userpage/insertManyStudent");
	}

}	

