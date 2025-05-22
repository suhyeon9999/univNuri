package com.university.nuri.controller.admincontroller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.service.adminservice.AEnrollFixSchedulerService;
import com.university.nuri.service.adminservice.AEnrollService;
import com.university.nuri.service.adminservice.DeptService;
import com.university.nuri.service.adminservice.LectService;

@Controller
public class EnrollController {

	@Autowired
	private DeptService deptService;

	@Autowired
	private AEnrollService aEnrollService;

	@Autowired
	private LectService lectService;
	
	
    @Autowired
    private AEnrollFixSchedulerService scheduler;

	// 수강관리
	// admin 네비 -> 수강 관리 연결
    // 수강관리 조회 페이지 
	@GetMapping("/enrollList")
	public ModelAndView enrollList() {
		ModelAndView mv = new ModelAndView();
		List<Map<String, Object>> deptList = deptService.getAllDeptList();
		mv.addObject("deptList", deptList);
		System.out.println(deptList);
		mv.addObject("deptListNum", deptList.size());
		mv.setViewName("admin/enroll/enrollList");
		Map<String, String> params = new HashMap<>();
		List<Map<String, Object>> lectList = aEnrollService.searchLectures(params);
		//List<Map<String, Object>> lectList = aEnrollService.getOpenLecture(); 지우기
		mv.addObject("lectList", lectList);
		mv.addObject("lectListNum", lectList.size()); 
		System.out.println("검색 결과: " + lectList);

		return mv;
	}
	// 수강관리 조회 페이지 처음 눌렀을때
	@GetMapping("/lecture-list")
	public ModelAndView lectureList() {
		ModelAndView mv = new ModelAndView();
		// List<Map<String, Object>> lectList = aEnrollService.getOpenLecture();지우기
		Map<String, String> params = new HashMap<>();
		List<Map<String, Object>> lectList = aEnrollService.searchLectures(params);
		mv.addObject("lectList", lectList);
		System.out.println("검색 결과: " + lectList);

		System.out.println("lectList" + lectList.get(0));
		mv.setViewName("admin/enroll/lectureListOnly"); // <-- 변경된 부분
		return mv;
	}

	// 수강관리 조회 페이지 필터링 했을때
	@GetMapping("/searchLectEnroll")
	public ModelAndView searchLectEnroll(@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView("admin/enroll/lectureListOnly");
		System.out.println("semester" + params.get("semester"));
		System.out.println("semester" + params.get("dept_name"));
		String semester = params.get("semester");
		String dept_name = params.get("dept_idx_select");
		System.out.println("오" + dept_name);
		if (semester != null && semester.contains("-")) {
			String[] parts = semester.split("-");
			params.put("year", parts[0]);
			params.put("month", parts[1].equals("1") ? "3" : "9"); // 예: 1학기 → 3월, 2학기 → 9월
		}

		List<Map<String, Object>> lectList = aEnrollService.searchLectures(params);
		mv.addObject("lectList", lectList);
		return mv;
	}

	// 지우기
	// 수강신청 예약 페이지 !!!!! 
	// 수강관리에서 수강신청 관리 누르면 수강 신청 페이지 이동
	/*@GetMapping("/moveEnrollApplyList")
	public ModelAndView moveEnrollApplyList() {
		// 수강신청 예약 날짜 2, 8월로 고정 
		// 현재 1-6월이면 이면 해당 년도 2월의 수강신청 예약 데이터가 있는지 찾으러 감 이때 active=0이여야 함
		List<Map<String, Object>> resultList = aEnrollService.getEnrollApplyReservationIs();
		ModelAndView mv = new ModelAndView();
		System.out.println("오오" +resultList.size());
		// 예약 해놓은게 있어서 조회
		if (resultList.size() > 0) {
			
			// 강의 시작날짜가 해당 년도 1~6이면 3월로 조건 걸고, active = 0인값 조건 (2는 미정, 0은 강의 확정, 1은 강의 확정 x) 예약 확정된 강의만 목록으로 불러오기 
			List<Map<String, Object>> lectList = aEnrollService.searchEnrollApplyLecturesReservationLook();
			
			// 신청한 날짜랑 시간 불러오기 
			// 교수 과제 제추 활용해서 데이터 가져오기 **
			Integer enroll_apply_idx = (Integer) resultList.get(0).get("enroll_apply_idx");
			List<Map<String, Object>> reservationDateTimeList = aEnrollService.searchEnrollApplyReservationDateTime(enroll_apply_idx);
			
			
			mv.addObject("lectListCount", aEnrollService.searchEnrollApplyLectures().size()); // 전체 강의 수 (lect_start_date가 금학기인 경우만)
			mv.addObject("lectListCheckCount", lectList.size()); // 선택된 강의 수 
			mv.addObject("lectList", lectList);
			mv.addObject("enroll_apply_idx", enroll_apply_idx);
			mv.addObject("reservationDateTimeList", reservationDateTimeList);
			mv.setViewName("admin/enroll/enrollApplyReservationLook"); // 선택된 강의만 나오게 active = 0인 것만 조건
		} else { // 수강신청 예약 이번학기 처음

			// 강의 불러오기
			//전체 강의 불러오기
			List<Map<String, Object>> lectList = aEnrollService.searchEnrollApplyLectures(); // 이거는 오로지  전체 강의 수 (lect_start_date가 금학기인 경우만)
																									// 이유 전체 강의 수 구할때 // 계속 사용하기 위함
			mv.addObject("lectListCount", lectList.size()); // 전체 강의 수 																		
			
			
			mv.addObject("lectList", lectList);
			mv.setViewName("admin/enroll/enrollApplyReservation");
		}
		return mv;
	}*/
	
	
	@GetMapping("/moveEnrollApplyList")
	public ModelAndView moveEnrollApplyList() {
	    ModelAndView mv = new ModelAndView();
	    //LocalDate today = LocalDate.now();
	    
	 // 실제 날짜 대신 테스트 날짜로 강제 설정
	   LocalDate today = LocalDate.of(2025, 8, 2); // 예: 2학기 종료 테스트용
	    LocalDateTime now = LocalDateTime.of(2025, 8, 2, 12, 0);
	    int currentYear = today.getYear();
	    int currentMonth = today.getMonthValue();

	    // 현재 예약 여부 확인
	    List<Map<String, Object>> resultList = aEnrollService.getEnrollApplyReservationIs();

	    // 예약 데이터 있는 경우
	    if (!resultList.isEmpty()) {
	        Map<String, Object> reservation = resultList.get(0);
	        Integer enroll_apply_idx = (Integer) reservation.get("enroll_apply_idx");
	        Timestamp endTime = (Timestamp) reservation.get("end_time"); // 예약 종료 시간
	        LocalDate endDate = endTime.toLocalDateTime().toLocalDate();

	        int semester = (int) reservation.get("semester"); // 1: 1학기, 2: 2학기

	        // 1학기 예약이었고, 오늘이 7월 1일 이후면 → 새 예약 가능
	        if (semester == 1 && today.isAfter(LocalDate.of(currentYear, 6, 30))) {
	            return moveToNewReservationPage(mv);
	        }

	        // 2학기 예약이었고, 오늘이 내년 1월 1일 이후면 → 새 예약 가능
	        if (semester == 2 && today.isAfter(LocalDate.of(currentYear, 12, 31))) {
	            return moveToNewReservationPage(mv);
	        }

	        // 조회 페이지로 이동
	        List<Map<String, Object>> lectList = aEnrollService.searchEnrollApplyLecturesReservationLook();
	        List<Map<String, Object>> reservationDateTimeList = aEnrollService.searchEnrollApplyReservationDateTime(enroll_apply_idx);

	        mv.addObject("lectList", lectList);
	        mv.addObject("lectListCount", aEnrollService.searchEnrollApplyLectures().size());
	        mv.addObject("lectListCheckCount", lectList.size());
	        mv.addObject("enroll_apply_idx", enroll_apply_idx);
	        mv.addObject("reservationDateTimeList", reservationDateTimeList);

	        // end_time 지난 경우에는 수정/취소 버튼 비활성화 플래그 추가
	        //LocalDateTime now = LocalDateTime.now(); // 현재 시각 포함
	       
	        LocalDateTime endDateTime = endTime.toLocalDateTime();

	        boolean isEnded = now.isAfter(endDateTime);
	        System.out.println("boolean값"+isEnded);
	        mv.addObject("reservationEnded", isEnded); // JSP에서 이 값으로 버튼 보여줄지 결정

	        mv.setViewName("admin/enroll/enrollApplyReservationLook");
	        return mv;

	    } else {
	        // 예약 내역 없음 → 처음 예약 페이지로
	        return moveToNewReservationPage(mv);
	    }
	}

	// 예약 없거나 다음 학기 예약 가능한 상태 → 예약 시작 페이지로
	private ModelAndView moveToNewReservationPage(ModelAndView mv) {
	    List<Map<String, Object>> lectList = aEnrollService.searchEnrollApplyLectures(); // 금학기 강의 목록
	    mv.addObject("lectList", lectList);
	    mv.addObject("lectListCount", lectList.size());
	    mv.setViewName("admin/enroll/enrollApplyReservation");
	    return mv;
	}

	
	
	
	// insert로 enroll_apply_look.jsp에 넣기 이거 작업하는 now()로해서 날짜랑, 1-6이면 1학기, 7-12이면
	// 2학기로 하기
	// ** 이때 개강한 강의 체크할때 db에 체크할만한 부분 이 있는지 ? ** 우선 active이용해서 삭제하는 걸로
	//수강신청 예약 누르면
	// 처음 예약
	@PostMapping("/enroll-apply-reservation")
	public ModelAndView enrollApplyReservation(
	        @RequestParam("enroll-date-start") String enrollDateStart,
	        @RequestParam("enroll-date-end") String enrollDateEnd,
	        @RequestParam(value = "enrollCheckbox", required = false) List<String> selectedLectures
	) throws Exception {
	    ModelAndView mv = new ModelAndView();

	    // 날짜 파싱
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	    LocalDateTime startDateTime = LocalDateTime.parse(enrollDateStart, formatter);
	    LocalDateTime endDateTime = LocalDateTime.parse(enrollDateEnd, formatter);

	    int semester = (startDateTime.getMonthValue() <= 6) ? 1 : 2;
	    
		Map<String, Object> paramMap = new HashMap<>();
		
		paramMap.put("start_time", Timestamp.valueOf(startDateTime));
		paramMap.put("end_time", Timestamp.valueOf(endDateTime));
		paramMap.put("semester",semester);
	    // insertEnrollApply에 직접 파라미터 전달
	    aEnrollService.insertEnrollApplyFirstReservation(paramMap);

	    
	    
        for (String lect_idx : selectedLectures) {
        	System.out.println("확인"+ lect_idx);
        }

	    
	    List<Map<String, Object>> allLectList = aEnrollService.searchEnrollApplyLectures();
	    
	    // 체크된 강의가 null일 경우 대비
	    if (selectedLectures == null) {
	        selectedLectures = new ArrayList<>();
	    }
	    
	    // 체크된 강의는 lect_active = 0
	    for (String lect_idx : selectedLectures) {
	        aEnrollService.updateEnrollApplyLectActiveZero(lect_idx);
	    }
	    
	    // 체크되지 않은 강의는 lect_active = 1
	    for (Map<String, Object> lecture : allLectList) {
	        String lect_idx = String.valueOf(lecture.get("lect_idx"));
	        if (!selectedLectures.contains(lect_idx)) {
	            aEnrollService.updateEnrollApplyLectActiveOne(lect_idx);
	        }
	    }
	    

	    
	    
    	List<Map<String, Object>> resultList = aEnrollService.getEnrollApplyReservationIs();
    	String enroll_apply_idx = String.valueOf(resultList.get(0).get("enroll_apply_idx"));
	    
	    scheduler.scheduleEnrollFix(
	    	    enroll_apply_idx, 
	    	    endDateTime
	    	);

	    mv.setViewName("redirect:/moveEnrollApplyList");
	    return mv;
	}


 // 히든으로 enroll_idx 받아오기
	// 예약 취소 버튼 누를 경우
	// select로 선택된 강의랑 enroll_apply에서 정보 불러오기
	// ** 예약 취소 누르면 걍 정보 다 날리기 delete ? active가 없는데 ? ** 우선 delete하기
	@GetMapping("/enroll-apply-reservation-cancel")
	public ModelAndView enrollApplyReservationCancel(@RequestParam("enroll_apply_idx") String enroll_apply_idx) {
		// enroll_apply에서 active =1 로 바꾸고
		// lect_idx도 다 lect_active = 2로바꾸기(미정)
	    aEnrollService.updateEnrollApplyActiveOne(enroll_apply_idx); // active = 1

	    List<Map<String, Object>> lectList = aEnrollService.searchEnrollApplyLectures();
	    for (Map<String, Object> lectMap : lectList) {
	        String lect_idx = String.valueOf(lectMap.get("lect_idx"));
	        aEnrollService.updateEnrollApplyLectActiveTwo(lect_idx); // active = 2 (미정)
	    }
	    // 기존 예약 취소
	    scheduler.cancelSchedule(enroll_apply_idx);

	    ModelAndView mv = new ModelAndView();
	    mv.setViewName("redirect:/moveEnrollApplyList");
	    return mv;
	}

	// 수정 버튼 누를 경우
	@GetMapping("/enroll-apply-reservation-update")
	public ModelAndView EnrollApplyReservationUpdate() {
		
		ModelAndView mv = new ModelAndView();
		List<Map<String, Object>> resultList = aEnrollService.getEnrollApplyReservationIs();
		// 선택된 강의 수
		// 수강신청 날짜와 시간
		// 강의 불러오기
		
		// 강의 시작날짜가 해당 년도 1~6이면 3월로 조건 걸고, active = 0인값 조건 (2는 미정, 0은 강의 확정, 1은 강의 확정 x)
		List<Map<String, Object>> lectList = aEnrollService.searchEnrollApplyLectures();
		
		// 신청한 날짜랑 시간 불러오기 
		
		// 교수 과제 제추 활용해서 데이터 가져오기 **
		Integer enroll_apply_idx = (Integer) resultList.get(0).get("enroll_apply_idx");
		List<Map<String, Object>> reservationDateTimeList = aEnrollService.searchEnrollApplyReservationDateTime(enroll_apply_idx);
		
		
		mv.addObject("lectListCount", aEnrollService.searchEnrollApplyLectures().size()); // 전체 강의 수 
		mv.addObject("lectListCheckCount", lectList.size()); // 선택된 강의 수 
		mv.addObject("lectList", lectList);
		mv.addObject("reservationDateTimeList", reservationDateTimeList);
		mv.addObject("lectList", lectList);
		mv.addObject("enroll_apply_idx", enroll_apply_idx);
		System.out.println("확인하려고"+ enroll_apply_idx);
		
		mv.setViewName("admin/enroll/enrollApplyReservationUpdate"); // 이번학기 열리는 모든 과목 나오게

		return mv;
	}

	
	//hidden으로 enroll_apply_idx받기
	//저장 버튼
	//눌렀을 경우 업데이트
	@PostMapping("/enroll-apply-reservation-update-ok")
	public ModelAndView enrollApplyReservationUpdateOk(
	        @RequestParam("enroll-date-start") String enrollDateStart,
	        @RequestParam("enroll-date-end") String enrollDateEnd,
	        @RequestParam(value = "enrollCheckbox", required = false) List<String> selectedLectures,
	        @RequestParam("enroll_apply_idx") Integer enroll_apply_idx
	) throws Exception {
	    ModelAndView mv = new ModelAndView();

	    // 날짜 파싱
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	    LocalDateTime startDateTime = LocalDateTime.parse(enrollDateStart, formatter);
	    LocalDateTime endDateTime = LocalDateTime.parse(enrollDateEnd, formatter);

	    int semester = (startDateTime.getMonthValue() <= 6) ? 1 : 2;
	    
		Map<String, Object> paramMap = new HashMap<>();
		
		paramMap.put("start_time", Timestamp.valueOf(startDateTime));
		paramMap.put("end_time", Timestamp.valueOf(endDateTime));
		paramMap.put("semester",semester);
		paramMap.put("enroll_apply_idx",enroll_apply_idx);
	    // insertEnrollApply에 직접 파라미터 전달
	    aEnrollService.updateEnrollApplyFirstReservation(paramMap);

	    for (String lect : selectedLectures) {
	            System.out.println("선택"+lect);
	      
	    }
	    
	    // 전체 강의 목록
	    List<Map<String, Object>> allLectList = aEnrollService.searchEnrollApplyLectures();
	    for (Map<String, Object> lect : allLectList) {
	        String lect_idx = String.valueOf(lect.get("lect_idx"));
	        if (selectedLectures != null && selectedLectures.contains(lect_idx)) {
	            aEnrollService.updateEnrollApplyLectActiveZero(lect_idx); // 선택됨 → active = 0
	            System.out.println("선택"+lect_idx);
	        } else {
	            aEnrollService.updateEnrollApplyLectActiveOne(lect_idx); // 선택 안됨 → active = 1
	            System.out.println("선택x"+lect_idx);
	        }
	    }
	    
	    // 체크되지 않은 강의 lect_active = 1로 업데이트 
	    
	    // 기존 예약 취소
	    scheduler.cancelSchedule(String.valueOf(enroll_apply_idx));
	    
	    scheduler.scheduleEnrollFix(
	    		String.valueOf(enroll_apply_idx), 
	    	    endDateTime
	    	);
	    mv.setViewName("redirect:/moveEnrollApplyList");
	    return mv;
	}
	
	



}
