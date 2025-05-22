package com.university.nuri.service.adminservice;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.adminrepository.AEnrollFixDAO;
import com.university.nuri.service.teacherservice.LectureService2;

@Service
public class AEnrollFixServiceImpl implements AEnrollFixService {

		@Autowired
		private AEnrollFixDAO aEnrollFixDAO;
		@Autowired
		private LectureService2 lectureService2;
		
		
		// 수강신청 기간 예약에서 수강신청 기간 설정 후 해당 enroll_apply_idx 가지고 옴
		// 1)우선 enroll_status값 취소가 아닌 신청(즉 0)인 값들 우선 확정(즉 1)로 바꿈
		// 2)enroll_apply_idx에 해당하는 enroll_apply의 start_time 예약 시작한 날을 가져옴
		// enroll_status값이 확정이고, enroll_time &gt; #{start_time}처럼 수강신청한 날짜가 수강신청 예약한 날짜보다 이후인 enroll_idx 구해서
		//3)해당 enroll_idx에 대해 출결데이터와 성적 데이터 넣기

	@Override
    public void runEnrollFix(String enroll_apply_idx) {
		int result = 0;
    	try {
    	// enroll_status 우선 0(신청이던거) 1(확정)으로 바꿔야 함(update하기)
	    result  =  aEnrollFixDAO.GetEnrollStatusOneUpdate();
	    if (result <= 0) {
	        System.out.println("enroll_status 1 확정 안됨 - 조건에 맞는 행이 없거나 업데이트 실패");
	    } else {
	        System.out.println("enroll_status 1로 업데이트 완료");
	    }
	} catch (Exception e) {
	    System.out.println("예외 발생: " + e.getMessage());
	    e.printStackTrace();
	}
	    
	 // 확정된 enroll_idx리스트(수강신청 시작 시간 기간 이후인지 체크해야 그 이후 enroll_idx만 데이터 추가할 수 있음-year(enroll_apply에서)로비교)
	    
	    
	    // enroll_apply_idx에 따라 enroll_apply에서 날짜 구해오기
    	Timestamp start_time  =  aEnrollFixDAO.GetEnrollApplyYear(enroll_apply_idx);
	    System.out.println("start_time" +start_time);
	    // 해당하는 수강신청 기간 이후에 신청한 enroll_idx 리스트 구해오기
		List<String> enrollIdxList = aEnrollFixDAO.GetEnrollStatusFIxList(start_time);

		// 각enroll_idx돌면서 성적이랑 출결 데이터 넣기
		for(String enroll_idx:enrollIdxList){

			// 해당하는 enroll_idx에 따른 lect_idx구하기
			int lect_idx = aEnrollFixDAO.GetLectIdx(enroll_idx);

			// 해당하는 enroll_idx에 따른 score 테이블에 score 데이터 넣기		
			
	    	try {
	        	// enroll_status 우선 0(신청이던거) 1(확정)으로 바꿔야 함(update하기)
	    	    result  =  aEnrollFixDAO.InsertScoreByEnrollIdx(enroll_idx);
	    	    System.out.println("삽입 시도하는 enroll_idx: " + enroll_idx);
	    	    if (result <= 0) {
	    	        System.out.println("성적 데이터 추가 안됨 - 조건에 맞는 행이 없거나 업데이트 실패");
	    	    } else {
	    	        System.out.println("성적 데이터 추가 업데이트 완료");
	    	    }
	    	} catch (Exception e) {
	    	    System.out.println("예외 발생: " + e.getMessage());
	    	    e.printStackTrace();
	    	}
			// 해당하는 enroll_idx에 따른 attendance 테이블에 attendance 데이터 넣기

			// 1. 강의 정보 가져오기
			Map<String, Object> lectureInfo = lectureService2.getLectureInfo(lect_idx);

			// 강의 시작 날짜 LocalDate로 사용하기 위함
			java.sql.Date sqlDate = (java.sql.Date) lectureInfo.get("lect_start_date");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sqlDate);
			LocalDate lectStartDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, // 월은
																													// 0부터
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

			int totalWeek = 16; // 총 9주차 수업, 일주일에 두번 수업일경우 18주차
			int countWeek = 1; // 몇주차인지 표현하는 변수
			LocalDate lectStartDateTemp = lectStartDate; // 바뀌는 수업날짜 표현할 용도(강의 시작날짜부터 시작해서 수업하는 날만 뽑기 위함)

			while (countWeek <= totalWeek * lectDayList.size()) {
				if (lectDayList.contains(lectStartDateTemp.getDayOfWeek())) {
					lectDateList.add(lectStartDateTemp); // lectDateList:수업 날짜만 모아놓은 리스트
					countWeek++;

				}
				lectStartDateTemp = lectStartDateTemp.plusDays(1);
			}

			for (Object attend_date : lectDateList) {
				
				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("attend_date", attend_date);
				paramMap.put("enroll_idx", enroll_idx);

				
		    	try {
		    		result = aEnrollFixDAO.InsertAttendanceByEnrollIdx(paramMap);
		    	    System.out.println("삽입 시도하는 enroll_idx: " + enroll_idx);
		    	    if (result <= 0) {
		    	        System.out.println("출결 데이터 추가 안됨 - 조건에 맞는 행이 없거나 업데이트 실패");
		    	    } else {
		    	        System.out.println("출결 데이터 추가 업데이트 완료");
		    	    }
		    	} catch (Exception e) {
		    	    System.out.println("예외 발생: " + e.getMessage());
		    	    e.printStackTrace();
		    	}

			}

		}

	}

}
