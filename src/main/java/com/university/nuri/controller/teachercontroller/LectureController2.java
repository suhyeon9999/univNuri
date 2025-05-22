package com.university.nuri.controller.teachercontroller;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.service.teacherservice.LectureService2;

@Controller
public class LectureController2 {

	@Autowired
	private LectureService2 lectureService2;
	
	@Autowired HttpSession session;

	// 수업 관리 nav 링크 /main?scoreLectureIs=lecture
	// 성적 관리 nav 링크 /main?scoreLectureIs=score
	
	
	private String formatTime(Object timeObj) {
	    if (timeObj == null) return null;

	    if (timeObj instanceof Time) {
	        return new SimpleDateFormat("HH:mm").format((Time) timeObj); // 시:분
	    } else if (timeObj instanceof LocalTime) {
	        return ((LocalTime) timeObj).format(DateTimeFormatter.ofPattern("HH:mm")); // 시:분
	    } else {
	        return timeObj.toString(); // fallback
	    }
	}


	// 강의관리, 성적관리 눌렀을때의 메인화면
	// @RequestParam("type") String type은 성적 관리메인 인지 강의 관리 메인인지 체크하기 위함- 그래야 과목명
	// 눌렀을때 강의 관리의 학생목록으로 갈지, 성적 조회 페이지로 갈지 조정할 수 있기 때문
	@GetMapping("/main")
	public ModelAndView getMain(@RequestParam("scoreLectureIs") String scoreLectureIs, 
			@RequestParam(value = "t_idx", required = false) Integer t_idx, 
			@RequestParam(value = "user_level", required = false) Integer user_level) {
		if (t_idx == null) {
	        Map<String, Object> tInfo = (Map<String, Object>) session.getAttribute("tInfo");
	        if (tInfo != null) {
	            t_idx = (Integer) tInfo.get("t_idx");
	        } else {
	            // t_idx가 없으면 적절한 처리 (예: 에러 메시지 반환)
	            throw new IllegalStateException("t_idx is missing");
	        }
	    }
		Map<String, Object> currentParams = new HashMap<>(); // 금학기 강좌 찾기
		currentParams.put("currentPastIs", "current");
		currentParams.put("t_idx", t_idx);
		System.out.println("t_idx" + t_idx);

		Map<String, Object> pastParams = new HashMap<>(); // 과거학기 강좌 찾기
		pastParams.put("currentPastIs", "past");
		pastParams.put("t_idx", t_idx);

		List<Map<String, Object>> mainThisSemester = lectureService2.getMainSemesterLectureList(currentParams); // 금학기
																												// 강좌
																												// 리스트
		List<Map<String, Object>> mainPastSemester = lectureService2.getMainSemesterLectureList(pastParams); // 과거학기 강좌
																												// 리스트
		for (Map<String, Object> row : mainThisSemester) {
		    row.put("lect_start_time", formatTime(row.get("lect_start_time")));
		    row.put("lect_end_time", formatTime(row.get("lect_end_time")));
		}
		for (Map<String, Object> row : mainPastSemester) {
		    row.put("lect_start_time", formatTime(row.get("lect_start_time")));
		    row.put("lect_end_time", formatTime(row.get("lect_end_time")));
		}

		ModelAndView mv = new ModelAndView("teacher/lecture/lectureMain"); // 금학기, 과거 강의 리스트 확인
		mv.addObject("mainThisSemester", mainThisSemester);// 금학기 강좌 리스트
		mv.addObject("mainPastSemester", mainPastSemester);// 과거학기 강좌 리스트

		
		//관리자
		//user_level이 있는 경우 1인 경우 
		if(user_level!=null) {
			mv.addObject("name", lectureService2.getTName(t_idx));
			System.out.println("user_level:" + user_level+"    name:" +lectureService2.getTName(t_idx));
			mv.addObject("user_level", user_level);
		}
		
		
		mv.addObject("t_idx", t_idx);// 과거학기 강좌 리스트

		mv.addObject("scoreLectureIs", scoreLectureIs); // 과목 눌렀을때 성적 관리로 가는지 강의 관리로 가는지 확인하기 위해 필요
		mv.addObject("mainThisSemesterLectureCount", mainThisSemester.size());// 금학기 강좌 리스트 수
		mv.addObject("mainPastSemesterLectureCount", mainPastSemester.size());// 과거학기 강좌 리스트 수
		return mv;
	}

	// 년도, 학기, 과목 이름 검색해서 과거 강좌 조회
	@PostMapping("/get-year-semester-lecture")
	@ResponseBody
	public List<Map<String, Object>> getYearSemesterLecture(@RequestBody Map<String, String> param) {

		String year = (String)param.get("year");
		String semester = (String)param.get("semester");
		String subject = (String) param.get("subject");
		String t_idx = (String) param.get("t_idx");
		Map<String, Object> paramMap = new HashMap<>();
		
		String currentPastIs = "past";
		paramMap.put("currentPastIs", currentPastIs);
		paramMap.put("year", year);
		paramMap.put("t_idx", t_idx);

		if (!"all".equals(year)) {
			paramMap.put("year", year);
		}else {
			year = null;
			paramMap.put("year", year);
		}		
		
		
		String month=null;
		// 학기 → month로 변환
		if (!"all".equals(semester)) {
			month = "1".equals(semester) ? "3" : "9";
			paramMap.put("month", month);
		}else {
			
			paramMap.put("month", month);
		}
		

		
		System.out.println("년도" + year);
		System.out.println("월" + month);
		System.out.println("과목명" + subject);
		System.out.println("t_idx" + t_idx);
		System.out.println("currentPastIs" + currentPastIs);

		paramMap.put("subject", subject);
		
		// 공통 메서드 호출
		List<Map<String, Object>> mainPastSemester = lectureService2.getMainSemesterLectureList(paramMap);
		
		for (Map<String, Object> row : mainPastSemester) {
		    row.put("lect_start_time", formatTime(row.get("lect_start_time")));
		    row.put("lect_end_time", formatTime(row.get("lect_end_time")));
		}



		return mainPastSemester;

	}

	// -------------------------------------------------------------------------------------------------------
	// 학생 목록

	// 강의 관리에서 해당 강의 클릭시 수강하는 학생 목록 조회
	@GetMapping("/get-lecture-student-list")
	public ModelAndView getLectureStudentList(@RequestParam("lect_idx") Integer lect_idx,
			@RequestParam(value = "t_idx", required = false) Integer t_idx, 
			@RequestParam(value = "user_level", required = false) Integer user_level) {
		Map<String, Object> paramMap = Map.of("lect_idx", lect_idx);

		List<Map<String, Object>> resultList = lectureService2.getLectureStudentList(paramMap);

		ModelAndView mv = new ModelAndView("teacher/lecture/studentList");
		mv.addObject("studentList", resultList);
		mv.addObject("lect_idx", lect_idx);
		mv.addObject("studentNum", resultList.size());
		Map<String, Object> lectureInfo = lectureService2.getLectureInfo(lect_idx);
		mv.addObject("lectureInfo", lectureInfo);
		return mv;
	}

	// 이름, 학번 검색해서 학생 정보 조회

	
	@PostMapping("/get-name-idx-student")
	@ResponseBody
	public Map<String, Object> getFilteredStudentListAjax(@RequestBody Map<String, Object> params) {
	    String name = (String) params.get("name");
	    String user_id = (String) params.get("user_id");
	    String lect_idx = (String) params.get("lect_idx"); // 필요하다면 사용
	    
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("lect_idx", lect_idx);
		paramMap.put("name", name);
		paramMap.put("user_id", user_id);

	    List<Map<String, Object>> studentList = lectureService2.getLectureStudentList(paramMap);
	    Map<String, Object> result = new HashMap<>();
	    result.put("studentList", studentList);
	    result.put("studentNum", studentList != null ? studentList.size() : 0);
	    return result;
	}


	// -------------------------------------------------------------------------------------------------------
	// 출결 관리
	
	
	// 예: LocalDate를 받아서 "2025년 5월 7일 수요일" 형식으로 반환하는 메서드
	public String formatDateWithDayOfWeek(LocalDate date) {
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일");
	    String formattedDate = date.format(dateFormatter);

	    String dayOfWeekKorean = date.getDayOfWeek()
	                                  .getDisplayName(TextStyle.FULL, Locale.KOREAN); // ex: 수요일

	    return formattedDate + " " + dayOfWeekKorean;
	}	


	// 강의관리에서 해당 강의 누르고 그 강의의 출결관리 누른 화면(날짜별로 집계된 학생들의 출결 현황 확인)
	@GetMapping("/get-lecture-attendance-list")
	public ModelAndView getLectureAttendanceList(@RequestParam("lect_idx") Integer lect_idx) {
		// 1. 강의 정보 가져오기
		Map<String, Object> lectureInfo = lectureService2.getLectureInfo(lect_idx);

		// 강의 시작 날짜 LocalDate로 사용하기 위함
		java.sql.Date sqlDate = (java.sql.Date) lectureInfo.get("lect_start_date");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sqlDate);
		LocalDate lectStartDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, // 월은 0부터
																												// 시작이므로
																												// +1
				calendar.get(Calendar.DAY_OF_MONTH));

		// 강의 요일 사용하기 위함
		int lectDayInt = (int) lectureInfo.get("lect_day"); // 예: "13"
		String lectDayStr = String.valueOf(lectDayInt); // int → String 변환 후 한 글자씩 자름

		// 강의 요일 파싱
		List<DayOfWeek> lectDayList = new ArrayList<>(); // lectDayList :수업하는 강의 요일 담은 리스트
		for (char lectDayChar : lectDayStr.toCharArray()) {
			int lectDay = Character.getNumericValue(lectDayChar); // '1' → 1
			DayOfWeek dayOfWeek = DayOfWeek.of(lectDay == 1 ? 7 : lectDay - 1); // 0이면 일요일(7), 나머지는 그대로
			lectDayList.add(dayOfWeek);
		}

		// 2.수업 날짜와 몇주차 수업인지 담은 리스트 생성
		List<Object> lectDateList = new ArrayList<>(); // lectDateList:수업 날짜만 모아놓은 리스트
		List<Map<String, Object>> lectDateWeekList = new ArrayList<>(); // lectDateWeekList:(수업날짜와 몇주차 수업인지 까지 정보)map들
																		// 담은 리스트

		int totalWeek = 16; // 총 9주차 수업, 일주일에 두번 수업일경우 18주차
		int countWeek = 1; // 몇주차인지 표현하는 변수
		LocalDate lectStartDateTemp = lectStartDate; // 바뀌는 수업날짜 표현할 용도(강의 시작날짜부터 시작해서 수업하는 날만 뽑기 위함)

		while (countWeek <= totalWeek * lectDayList.size()) {
			if (lectDayList.contains(lectStartDateTemp.getDayOfWeek())) {
				Map<String, Object> lectDateWeekInfo = new HashMap<>();// 수업날짜와 몇주차 수업인지 담을 map
				lectDateWeekInfo.put("lectDate", lectStartDateTemp);
				lectDateWeekInfo.put("countWeek", countWeek);
				lectDateWeekList.add(lectDateWeekInfo); // lectDateWeekList:(수업날짜와 몇주차 수업인지 까지 정보)map들 담은 리스트

				lectDateList.add(lectStartDateTemp); // lectDateList:수업 날짜만 모아놓은 리스트
				countWeek++;

			}
			lectStartDateTemp = lectStartDateTemp.plusDays(1);
		}

		// 3. 날짜별 출석 집계
		List<Map<String, Object>> attendanceSummaryListByDateList = new ArrayList<>(); // attendanceSummaryListByDateList:
																						// 출결 관리 집계 현황을 담은 map을 각 날짜별로
																						// 담은 리스트

		// 수업날짜와 주차 정보 담은 map을 list에서 하나씩 꺼낸다
		for (int i = 0; i < lectDateWeekList.size(); i++) {
			Map<String, Object> LectDatetWeekMap = lectDateWeekList.get(i); // 수업날짜와 주차 정보 담은 map을 list에서 하나씩 꺼낸다
			LocalDate lectDate = (LocalDate) LectDatetWeekMap.get("lectDate");
			int countWeek2 = (int) LectDatetWeekMap.get("countWeek");

			Map<String, Object> attendanceSummaryMap; // attendanceSummaryMap: 출결 관리 집계 현황을 담은 map

			Map<String, Object> paramMap = new HashMap<>(); // mapper갈때 lect_idx와 강의 날짜있어야 해당 날짜와 해당 수업으로 날짜별 출석 집계가
															// 가능하기 때문
			paramMap.put("lect_idx", lect_idx);
			paramMap.put("lectDate", lectDate);
			System.out.println(lectDate);

			// 강의관리메인에서 강의 선택해서 수업하는 '모든' 날짜의 날짜별 출결 관리 집계 현황
			attendanceSummaryMap = lectureService2.getAttendanceSummaryListByDateMap(paramMap); // 출석 상태별 숫자 리턴
			
		    if (attendanceSummaryMap == null) {
		        ModelAndView mv = new ModelAndView("teacher/lecture/alert-redirect");
		        mv.addObject("message", "강의 출석 정보 준비 중입니다.");
		        mv.addObject("redirectUrl", "/main?scoreLectureIs=lecture"); // 돌아갈 페이지 적절히 수정
		        return mv;
		    }
			// 지금 for문으로 날짜별로 하고 있으므로 해당 날짜의 출결 관리 집계 현황을 넣은 곳에 해당 날짜와 해당 주차까지 넣기
			attendanceSummaryMap.put("countWeek", countWeek2); // 해당 수업 날짜의 주차 정보
			attendanceSummaryMap.put("lectDate", lectDate); // 해당 수업 날짜
			attendanceSummaryListByDateList.add(attendanceSummaryMap);// attendanceSummaryMap가 한행,
																		// attendanceSummaryListByDateList가 테이블
		}

		ModelAndView mv = new ModelAndView("teacher/lecture/attendanceList"); // 날짜별 출결 집계 현황 조회 페이지
		mv.addObject("attendanceSummaryList", attendanceSummaryListByDateList); // 날짜별로 map(수업날짜, 수업 차수, 해당 날짜 출석 집계)담은
																				// 리스트 보냄

		int studentNum = lectureService2.getStudentNum(lect_idx); // 해당 강의 듣는 전체 학생 수
		mv.addObject("studentNum", studentNum);
		mv.addObject("lect_idx", lect_idx);
		mv.addObject("lectDateList", lectDateList);
		mv.addObject("lectDateWeekList", lectDateWeekList);
		mv.addObject("lectureInfo", lectureInfo);

		return mv;

	}

	// 강의관리에서 해당 강의 누르고 그 강의의 출결관리 누른 화면(날짜별로 집계된 학생들의 출결 현황 확인)인데 + 날짜 별로 검색했을 경우

	@PostMapping("/get-attendance-by-date-ajax")
	@ResponseBody
	public List<Map<String, Object>> getLectureAttendanceList(@RequestBody Map<String, Object> param) {
		String searchLectDate = (String) param.get("date"); // 검색해야하는 강의날짜

		String lect_idx_str = (String) param.get("lect_idx");

		int lect_idx = Integer.parseInt(lect_idx_str);// 해당 강의 idx

		List<Map<String, Object>> lectDateWeekList = (List<Map<String, Object>>) param.get("lectDateWeekList"); // 강의날짜-주차
																												// 들어있는)map으로
																												// 이루어진
																												// list

		// attendanceSummaryListByDateList: 출결 관리 집계 현황을 담은 map을 각 날짜별로 담은 리스트
		List<Map<String, Object>> attendanceSummaryListByDateList = new ArrayList<>();

		if ("all".equals(searchLectDate)) { // 전체 선택시

			// 3.날짜별 출석 집계 - 처음 출석 관리 화면 불러올때 전체 불러오기때문에 이때와 똑같

			for (int i = 0; i < lectDateWeekList.size(); i++) {
				Map<String, Object> LectDatetWeekMap = lectDateWeekList.get(i); // 수업날짜와 주차 정보 담은 map을 list에서 하나씩 꺼낸다

				// LocalDate로 사용하기 위함
				String lectDateStr = (String) LectDatetWeekMap.get("lectDate");

				// String을 java.sql.Date로 변환
				java.sql.Date sqlDate = java.sql.Date.valueOf(lectDateStr);

				// java.sql.Date를 LocalDate로 변환
				LocalDate lectDate = sqlDate.toLocalDate();

				System.out.println("성공2");

				String countWeek_str = (String) LectDatetWeekMap.get("countWeek");

				int countWeek = Integer.parseInt(countWeek_str);// 해당 강의 idx

				Map<String, Object> attendanceSummaryMap; // attendanceSummaryMap: 출결 관리 집계 현황을 담은 map

				Map<String, Object> paramMap = new HashMap<>(); // mapper갈때 lect_idx와 강의 날짜있어야 해당 날짜와 해당 수업으로 날짜별 출석 집계가
																// 가능하기 때문
				paramMap.put("lect_idx", lect_idx);
				paramMap.put("lectDate", lectDate);

				// 강의관리메인에서 강의 선택해서 수업하는 '모든' 날짜의 날짜별 출결 관리 집계 현황
				attendanceSummaryMap = lectureService2.getAttendanceSummaryListByDateMap(paramMap); // 출석 상태별 숫자 리턴

				// 지금 for문으로 날짜별로 하고 있으므로 해당 날짜의 출결 관리 집계 현황을 넣은 곳에 해당 날짜와 해당 주차까지 넣기
				attendanceSummaryMap.put("countWeek", countWeek); // 해당 수업 날짜의 주차 정보
				attendanceSummaryMap.put("lectDate", lectDate); // 해당 수업 날짜
				attendanceSummaryListByDateList.add(attendanceSummaryMap);// attendanceSummaryMap가 한행,
																			// attendanceSummaryListByDateList가 테이블

			}

		} else { // 전체 외의 날짜 선택했을 경우

			// 특정 날짜의 출석 집계
			// 수업날짜와 주차 정보 담은 map을 list에서 하나씩 꺼낸다
			for (int i = 0; i < lectDateWeekList.size(); i++) {
				Map<String, Object> LectDatetWeekMap = lectDateWeekList.get(i); // 수업날짜와 주차 정보 담은 map을 list에서 하나씩 꺼낸다

				// LocalDate로 사용하기 위함
				String lectDateStr = (String) LectDatetWeekMap.get("lectDate");

				// String을 java.sql.Date로 변환
				java.sql.Date sqlDate = java.sql.Date.valueOf(lectDateStr);

				// java.sql.Date를 LocalDate로 변환
				LocalDate lectDate = sqlDate.toLocalDate();

				System.out.println("성공");

				if (lectDate.equals(LocalDate.parse(searchLectDate))) {// LocalDate 비교하는 법 equlals? ==?

					String countWeekStr = (String) LectDatetWeekMap.get("countWeek");
					int countWeek = Integer.parseInt(countWeekStr);

					Map<String, Object> attendanceSummaryMap; // attendanceSummaryMap: 출결 관리 집계 현황을 담은 map

					Map<String, Object> paramMap = new HashMap<>(); // mapper갈때 lect_idx와 강의 날짜있어야 해당 날짜와 해당 수업으로 날짜별 출석
																	// 집계가 가능하기 때문
					paramMap.put("lect_idx", lect_idx);
					paramMap.put("lectDate", lectDate);

					// 강의관리메인에서 강의 선택해서 '검색'한 날짜의 출결 관리 집계 현황
					attendanceSummaryMap = lectureService2.getAttendanceSummaryListByDateMap(paramMap); // 출석 상태별 숫자 리턴

					// 지금 for문으로 날짜별로 하고 있으므로 해당 날짜의 출결 관리 집계 현황을 넣은 곳에 해당 날짜와 해당 주차까지 넣기
					attendanceSummaryMap.put("countWeek", countWeek); // 해당 수업 날짜의 주차 정보
					attendanceSummaryMap.put("lectDate", lectDate); // 해당 수업 날짜
					System.out.println(searchLectDate);
					attendanceSummaryListByDateList.add(attendanceSummaryMap);// attendanceSummaryMap가 한행,
																				// attendanceSummaryListByDateList가 테이블

					break;
				} else {
					continue;
				}
			}

		}

		return attendanceSummaryListByDateList;

	}

	// 출석 체크하기 버튼 눌렀을때
	@PostMapping("/get-lecture-attendance-today")
	public ModelAndView getLectureAttendanceToday(@RequestParam("lect_idx") int lect_idx) {
		Map<String, Object> lectureInfo = lectureService2.getLectureInfo(lect_idx);

		// 강의 시작 날짜 LocalDate로 사용하기 위함
		java.sql.Date sqlDate = (java.sql.Date) lectureInfo.get("lect_start_date");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sqlDate);
		LocalDate lectStartDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, // 월은 0부터
																												// 시작이므로
																												// +1
				calendar.get(Calendar.DAY_OF_MONTH));

		// 강의 요일 사용하기 위함
		int lectDayInt = (int) lectureInfo.get("lect_day"); // 예: "13"
		String lectDayStr = String.valueOf(lectDayInt); // int → String 변환 후 한 글자씩 자름

		// 강의 요일 파싱
		List<DayOfWeek> lectDayList = new ArrayList<>(); // lectDayList :수업하는 강의 요일 담은 리스트
		for (char lectDayChar : lectDayStr.toCharArray()) {
			int lectDay = Character.getNumericValue(lectDayChar); // '1' → 1
			DayOfWeek dayOfWeek = DayOfWeek.of(lectDay == 1 ? 7 : lectDay - 1); // 0이면 일요일(7), 나머지는 그대로
			lectDayList.add(dayOfWeek);
		}
		

		// 2.수업 날짜와 몇주차 수업인지 담은 리스트 생성
		List<Object> lectDateList = new ArrayList<>(); // lectDateList:수업 날짜만 모아놓은 리스트
		List<Map<String, Object>> lectDateWeekList = new ArrayList<>(); // lectDateWeekList:(수업날짜와 몇주차 수업인지 까지 정보)map들
																		// 담은 리스트

		int totalWeek = 15; // 총 9주차 수업, 일주일에 두번 수업일경우 18주차
		int countWeek = 1; // 몇주차인지 표현하는 변수
		LocalDate lectStartDateTemp = lectStartDate; // 바뀌는 수업날짜 표현할 용도(강의 시작날짜부터 시작해서 수업하는 날만 뽑기 위함)

		while (countWeek <= totalWeek * lectDayList.size()) {
			if (lectDayList.contains(lectStartDateTemp.getDayOfWeek())) {
				Map<String, Object> lectDateWeekInfo = new HashMap<>();// 수업날짜와 몇주차 수업인지 담을 map
				lectDateWeekInfo.put("lectDate", lectStartDateTemp);
				lectDateWeekInfo.put("countWeek", countWeek);
				lectDateWeekList.add(lectDateWeekInfo); // lectDateWeekList:(수업날짜와 몇주차 수업인지 까지 정보)map들 담은 리스트

				lectDateList.add(lectStartDateTemp); // lectDateList:수업 날짜만 모아놓은 리스트
				countWeek++;

			}
			lectStartDateTemp = lectStartDateTemp.plusDays(1);
		}

		LocalDate today = LocalDate.now();
		boolean isTodayLectureDay = lectDateList.stream()
			    .anyMatch(date -> ((LocalDate) date).isEqual(today));
	
		
// LocalDate 형태여서 비교 잘 안되면  String today = LocalDate.now().toString(); 이걸로 해보기
		// 수업 날짜 리스트에 오늘이 포함되어 있는지 확인
		if (isTodayLectureDay) {
			// 해당 날짜의 출석 체크 페이지로 리다이렉트
			return new ModelAndView("redirect:/get-lecture-attendance-by-date-student?lectDate=" + today + "&lect_idx=" + lect_idx);
		} else {
			// 오늘이 수업 날짜가 아니라면, 강의 관리 눌렀을때 전체 수업 날짜들의 날짜별 출석 집계 볼 수 있는 페이지로 감
//// 오늘 수업 없을 경우, alert 띄우고 출석 집계 페이지로 이동
			ModelAndView mv = new ModelAndView("teacher/lecture/alert-redirect");
			mv.addObject("message", "오늘은 수업이 없습니다.");
			mv.addObject("redirectUrl", "/get-lecture-attendance-list?lect_idx=" + lect_idx);
			return mv;

		}

	}

	// 날짜를 누르고 해당 날짜의 학생별 출결 상황
	@GetMapping("/get-lecture-attendance-by-date-student")
	public ModelAndView getLectureAttendanceByDateStudent(@RequestParam("lect_idx") int lect_idx,
			@RequestParam("lectDate") String dateStr) {
		ModelAndView mv = new ModelAndView("teacher/lecture/attendanceDetail");

		// 출결 날짜 파싱 // 문제 생길 수 있음 주의 깊게 보기
		LocalDate attendDate = LocalDate.parse(dateStr);

		// jsp에 화면단대로 날짜 형태 출력하기

		// 해당 날짜에 출결 상태와 함께 수강중인 학생 리스트 조회
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("lect_idx", lect_idx);
		paramMap.put("date", attendDate);
		// 수업 듣는 학생의 이름, 학번, 학년, 학과, 그리고 해당 날짜의 출석, 결석, 지각 등 체크 여부,해당 학생의 그 수업에서 총 출석 집계
		// 현황 map
		List<Map<String, Object>> getLectureAttendanceByDateStudentList = lectureService2
				.getLectureAttendanceByDateStudentList(paramMap);

		mv.addObject("getLectureAttendanceByDateStudentList", getLectureAttendanceByDateStudentList);
 // 또는 서비스 로직에서 얻은 날짜
		String theDateFormatted = formatDateWithDayOfWeek(attendDate);

		mv.addObject("theDateFormat", theDateFormatted );
		mv.addObject("theDate", attendDate );
		mv.addObject("lect_idx", lect_idx);
		System.out.println("몇이야" +lect_idx);
		// int studentNum = lectureService2.getStudentNum(lect_idx); // 해당 강의 듣는 전체 학생 수
		mv.addObject("studentNum", getLectureAttendanceByDateStudentList.size());
		Map<String, Object> lectureInfo = lectureService2.getLectureInfo(lect_idx);
		mv.addObject("lectureInfo", lectureInfo);
		return mv;
	}


	
	@PostMapping("/go_attendance_update_ok")
	public String updateAttendance(
	    @RequestParam("lect_idx") int lectIdx,
	    @RequestParam("theDate") String theDate,
	    @RequestParam("enroll_idx[]") List<Integer> enrollIdxList,
	    HttpServletRequest request
	) {
	    for (Integer enrollIdx : enrollIdxList) {
	        String statusStr = request.getParameter("attend_status_" + enrollIdx);
	        
	        System.out.println("값은" +statusStr);
	        System.out.println("어"+theDate);
	        Integer attendStatus = (statusStr != null) ? Integer.parseInt(statusStr) : null;

	        if (attendStatus != null) {
	            Map<String, Object> paramMap = new HashMap<>();
	            paramMap.put("attend_status", attendStatus);
	            paramMap.put("enroll_idx", enrollIdx);
	            System.out.println("dP" + enrollIdx);
	            paramMap.put("attend_date", theDate);
	            System.out.println("attend_date1:" + theDate);

	            lectureService2.getAttendanceUpdate(paramMap);
	        }
	    }

	    return "redirect:/get-lecture-attendance-list?lect_idx=" + lectIdx;
	}


}