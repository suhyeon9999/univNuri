package com.university.nuri.controller.admincontroller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.service.adminservice.AStudentService;
import com.university.nuri.vo.commonvo.UserVO;
import com.university.nuri.vo.studentvo.StudentVO;

@Controller
public class AStudentController {
	@Autowired
	private AStudentService aStudentService;
	@Autowired
	private HttpSession session;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// 학생관리 페이지
	@RequestMapping("/studentList")
	public ModelAndView studentList() {
		ModelAndView mv = new ModelAndView();
		List<Map<String, Object>> studentList = aStudentService.getStudentList();
		mv.addObject("studentList", studentList);
		mv.setViewName("admin/userpage/studentList");
		return mv;
	}
	// 학생관리 페이지 - 조건 검색
	@RequestMapping("/filteredStudentList")
	public ModelAndView filterStudentList(@RequestParam(required = false) String name,
               @RequestParam(required = false) String user_id) {
		ModelAndView mv = new ModelAndView();
		List<Map<String, Object>> studentList = aStudentService.getFilteredStudentList(name, user_id);
		mv.addObject("studentList", studentList);
		mv.setViewName("admin/userpage/studentList");
		return mv;
	}
	// 학생 관리 -> 학생 상세보기
	@GetMapping("/detailStudent")
	public ModelAndView detailStudent(@RequestParam("user_id") String user_id) {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> detailStudent = aStudentService.getDetailStudent(user_id);
		mv.addObject("detailStudent", detailStudent);
		mv.setViewName("admin/userpage/detailStudent");
		return mv;
	}
	
	// 학생 관리 -> 학생 수정 페이지
	@GetMapping("/updateStudent")
	public ModelAndView updateStudent(@RequestParam("user_id") String user_id) {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> detailStudent = aStudentService.getDetailStudent(user_id);
		List<Map<String, Object>> deptList = aStudentService.getDeptList();
		mv.addObject("detailStudent", detailStudent);
		mv.addObject("deptList", deptList);
		mv.setViewName("admin/userpage/updateStudent");
		return mv;
	}	
	
	// 학생 관리 -> 학생 수정 페이지
	@PostMapping("/updateOkStudent")
	@ResponseBody
	public String updateOkStudent(@RequestParam Map<String, String> params) {
		String user_id   = params.get("user_id");
		String name = params.get("name");
		String birth = params.get("birth");
		String email = params.get("email");
		String phone = params.get("phone");
		int s_grade = Integer.parseInt(params.get("s_grade"));
		int dept_idx = Integer.parseInt(params.get("dept_idx"));
		String s_address = params.get("s_address");
		String s_address2 = params.get("s_address2");
		
		int userResult = aStudentService.getUpdateUserTable(user_id, name, birth, email, 
				phone);
		int studentResult = aStudentService.getUpdateStudentTable(user_id, s_grade, dept_idx, 
				s_address, s_address2);
		return (userResult > 0 && studentResult > 0) ? "OK" : "FAIL";
	}	
	
	// 학생 삭제 비밀번호 체크
	@PostMapping("/aStudentCheckPassword")
	@ResponseBody
	public String checkPassword(@RequestParam("inputPwd") String inputPwd) {
		UserVO uvo = (UserVO) session.getAttribute("uvo");
		String user_id = uvo.getUser_id();
		// DB에서 비밀번호를 가져옴
		String dbpw = aStudentService.getAStudentCheckPassword(user_id);  // 평문 비밀번호

		// DB에 저장된 비밀번호가 평문으로 저장되어 있으면 직접 비교
		if (inputPwd != null && passwordEncoder.matches(inputPwd, dbpw)) {
			return "OK";
		} else {
			return "FAIL";
		}
	}
	
	// 학생 삭제 확정
	@PostMapping("/aStudentDeleteOk")
	@ResponseBody
	public String sBoardDeleteOk(@RequestParam String user_id,
			@RequestParam int s_idx) {
		int userResult = aStudentService.getDeleteSUserTable(user_id);
		int studentResult = aStudentService.getDeleteSStudentTable(s_idx);
		return (userResult > 0 && studentResult > 0) ? "OK" : "FAIL";
	}
	
	// 학생 관리-> 학생 등록페이지 이동
	@GetMapping("/moveInsertStudent")
	public ModelAndView moveInsertStudent() {
		ModelAndView mv = new ModelAndView();
		List<Map<String, Object>> deptList = aStudentService.getDeptList();
		mv.addObject("deptList", deptList);
		mv.setViewName("admin/userpage/insertStudent");
		return mv;
	}
	
	// 학생 등록
	@PostMapping("/studentInsert")
	public ModelAndView studentInsert(@ModelAttribute UserVO userVO, StudentVO studentVO) {
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
			userVO.setUser_level("0");
			userVO.setStatus("1");
			int currentYear = LocalDate.now().getYear();
			// 학번 생성
			String yearstr = String.valueOf(currentYear); //현재년도
			int count = aStudentService.countStudentsThisYear(yearstr, studentVO.getDept_idx()); // 맴퍼를 통해 해당 연도 인원 조회
			String serial = String.format("%03d", count + 1); // 일련번호 001 최대값 +1
			String studentNumber = "S" + yearstr + "00" + studentVO.getDept_idx() + serial;
			userVO.setUser_id(studentNumber); // 조합한 UserID 를 저장
			// 확인용
			System.out.println(userVO.getUser_id());
			int result = aStudentService.studentInsert(userVO,studentVO);
			if (result > 0) {
				String newUserId = userVO.getUser_id(); // 생성한 학번
				mv.setViewName("redirect:/detailStudent?user_id=" + newUserId);
				return mv;				
			}else {
				mv.setViewName("redirect:/moveInsertStudent");
			}
			return mv;				
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/moveInsertStudent");
		}
	}




}
