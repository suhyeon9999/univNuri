package com.university.nuri.controller.admincontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.service.adminservice.DeptService;
import com.university.nuri.service.adminservice.LectService;
import com.university.nuri.service.adminservice.SubjectSetService;
import com.university.nuri.vo.commonvo.UserVO;

@Controller
public class LectController {
	@Autowired
	private LectService lectService;
	@Autowired
	private DeptService deptService;
	@Autowired
	private SubjectSetService subjectSetService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
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
	
	// 강의관리 -> 강의등록 페이지 이동
		// 강의등록 및 학과 불러오기
		@GetMapping("/moveInsertLect")
		public ModelAndView moveInsertLect() {
		    ModelAndView mv = new ModelAndView();
		    // 학과 불러오기 
		    List<Map<String, Object>> deptList = lectService.getAllActiveDepts();
		    // 선생 불러오기
		    List<Map<String, Object>> teacherList = lectService.getAllActiveTeachers();
		    mv.addObject("teacherList", teacherList);
		    mv.addObject("deptList", deptList);
		    mv.setViewName("admin/lect/insertLect");
		    return mv;
		}
		// 과목군 로드
		@GetMapping("/subjectGroupList")
		@ResponseBody
		public List<Map<String, Object>> getSubjectGroupList(@RequestParam Map<String, String> paramMap) {
		    // paramMap contains: dept_idx, sub_set_name, subject_name
		    return lectService.getGroupListByDept(paramMap);
		}

		// 강의등록 - 학과선택하면 해당 교수 불러오기
		@GetMapping("/teachersByDept")
		@ResponseBody
		public List<Map<String, Object>> getTeachersByDept(@RequestParam("dept_idx") String deptIdx) {
		    return lectService.getTeachersByDept(deptIdx);
		}

		// 강의건물 선택 시 해당 강의실 목록 조회
		@GetMapping("/getRoomsByBuilding")
		@ResponseBody
		public List<Map<String, Object>> getRoomsByBuilding(@RequestParam("building") String building) {
		    return lectService.getRoomsByBuilding(building);
		}
		// 강의실 건물,호실 선택하면 시간 나오게
		@GetMapping("/availableStartTimes")
		@ResponseBody
		public List<String> getAvailableStartTimes(@RequestParam Map<String, String> paramMap) {
		    return lectService.getAvailableStartTimes(paramMap);
		}
		// 강의등록 서비스 위임
		@PostMapping("/insertLecture")
		@ResponseBody
		public String insertLecture(@RequestBody Map<String, String> param) {
		    try {
		        // 1. 강의 코드 자동 생성
		        String generatedLectId = lectService.generateLectureId(param.get("dept_idx"));
		        param.put("lect_id", generatedLectId);

		        // 2. 등록 처리
		        int lectIdx = lectService.insertLecture(param);

		        return lectIdx > 0 ? "ok" : "fail";
		    } catch (Exception e) {
		        e.printStackTrace();
		        return "error";
		    }
		}
		@PostMapping("/lectureDeletePwCheck")
		@ResponseBody
		public String lectureDeletePwCheck(@RequestParam("inputPwd") String inputPwd, HttpSession session) {
		    // 로그인된 관리자 세션에서 user_id, user_pw 가져오기
		    UserVO user = (UserVO) session.getAttribute("uvo");
		    if (user == null) return "SESSION_FAIL";
		    String savedPw = user.getUser_pw();  // 세션에서 저장된 암호화된 비밀번호
		    // bcrypt 체크
		    if (passwordEncoder.matches(inputPwd, savedPw)) {
		        return "OK";
		    } else {
		        return "FAIL";
		    }
		}

		@PostMapping("/lectureDelete")
		@ResponseBody
		public String lectureDelete(@RequestParam("lect_idx") String lect_idx) {
		    boolean result = lectService.deleteLecture(lect_idx);
		    return result ? "OK" : "FAIL";
		}

		// 강의관리 -> 강의상세보기
		@GetMapping("/updateLect")
		public ModelAndView UpdateLect(@RequestParam("lect_idx")String lect_idx) {
			try {
				ModelAndView mv = new ModelAndView();
				Map<String, Object> detailLect = lectService.detailLect(lect_idx);
				String dept_idx = detailLect.get("dept_idx").toString(); // 강의의 개설학과 기준
			    // 과목군 + 과목 리스트 조회
			    List<String> subjectGroupList = lectService.getSubjectGroupsByLectureIdx(dept_idx); // 과목군 목록만
			    List<Map<String, Object>> professorList = lectService.getProfessorListByDept(dept_idx);
			    mv.addObject("subjectGroupList", subjectGroupList);
			    mv.addObject("professorList", professorList);
				mv.addObject("detailLect",detailLect);
				mv.setViewName("admin/lect/updateLect");
				return mv;
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("error");
			}
		}
		@GetMapping("/getAvailableTimes")
		@ResponseBody
		public List<String> getAvailableTimes(
		        @RequestParam("building") String building,
		        @RequestParam("room") String room,
		        @RequestParam("day") String day) {

		    Map<String, String> param = new HashMap<>();
		    param.put("building", building);
		    param.put("room", room);
		    param.put("day", day);

		    return lectService.getAvailableStartTimes(param);
		}
		@GetMapping("/classRoomListByBuilding")
		@ResponseBody
		public List<String> classRoomListByBuilding(@RequestParam("building") String building) {
		    return lectService.classRoomListByBuilding(building);
		}


		@PostMapping("/updateLectureOK")
		@ResponseBody
		public String updateLectureOK(@RequestBody Map<String, Object> param) {
		    try {
		        boolean result = lectService.updateLecture(param);
		        return result ? "OK" : "FAIL";
		    } catch (Exception e) {
		        e.printStackTrace();
		        return "FAIL";
		    }
		}
		@GetMapping("/getDeptListForUpdate")
		@ResponseBody
		public List<Map<String, Object>> getDeptListForUpdate() {
		    return lectService.getAllActiveDepts(); // dept_active = 0 조건
		}


	
	
	
}
