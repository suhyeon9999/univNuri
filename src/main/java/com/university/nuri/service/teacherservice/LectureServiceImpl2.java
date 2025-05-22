package com.university.nuri.service.teacherservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.teacherrepository.LectureDAO2;

@Service
public class LectureServiceImpl2 implements LectureService2{

	@Autowired
	private LectureDAO2 lectureDAO2;
	
	// 강의관리 메인화면
	// 강의관리메인 금학기 정보 불러오기, 강의관리메인 과거학기 정보 전체 or 검색 불러오기
@Override
public List<Map<String, Object>> getMainSemesterLectureList(Map<String, Object> paramMap) {
    List<Map<String, Object>> list = lectureDAO2.getMainSemesterLectureList(paramMap);

    for (Map<String, Object> item : list) {
        // 요일 변환
        String dayNumStr = String.valueOf(item.get("lect_day"));
        StringBuilder dayNames = new StringBuilder();
        for (int i = 0; i < dayNumStr.length(); i++) {
            switch (dayNumStr.charAt(i)) {
                case '1': dayNames.append("일"); break;
                case '2': dayNames.append("월"); break;
                case '3': dayNames.append("화"); break;
                case '4': dayNames.append("수"); break;
                case '5': dayNames.append("목"); break;
                case '6': dayNames.append("금"); break;
                case '7': dayNames.append("토"); break;
            }
            if (i != dayNumStr.length() - 1) {
                dayNames.append(",");
            }
        }
        item.put("lect_day", dayNames.toString());

        // 건물명 변환
        int classNum = ((Number) item.get("class_building")).intValue();
				String className=null;
				switch (classNum) {
				case 0: className="미래관"; break;
				case 1: className="현재관"; break;
				case 2: className="과거관"; break;
				default: className = "미정"; break;
				}
		      item.put("class_building", className);
    }

    return list;
}

	//-------------------------------------------------------------------------------------------------------------------
	// 학생 목록
	
	
	// 강의관리메인에서 강의 선택해서 해당 강의 수강하는 학생 리스트 전체 혹은 이름이나 학번으로 검색 조회
	@Override
	public List<Map<String, Object>> getLectureStudentList(Map<String, Object> paramMap) {
		return lectureDAO2.getLectureStudentList(paramMap);
}

	//-------------------------------------------------------------------------------------------------------------------
	// 출결 관리
	
	
	// 해당 강의 듣는 전체 학생 수 

		@Override
		public int getStudentNum(int lect_idx) {
				return lectureDAO2.getStudentNum(lect_idx);

		}
	
		// 강의 정보 가져오기(강의 시작 날짜 LocalDate로 사용하기 위함, 강의 요일 사용하기 위함)
		@Override
		public Map<String, Object> getLectureInfo(int lect_idx) {
			return lectureDAO2.getLectureInfo(lect_idx);
		}


		// 강의관리메인에서 강의 선택해서 수업하는 '모든' 날짜'중 (for문이므로 ) '특정 날짜의' 출결 관리 집계 현황
		@Override
		public Map<String, Object> getAttendanceSummaryListByDateMap(Map<String, Object> paramMap) {

			return lectureDAO2.getAttendanceSummaryListByDateMap(paramMap);
		}


		@Override
	// 강의관리에서 날짜 눌렀을때 해당 날짜 학생들의 출석 조회, 수정 정보 불러오기 or 출석 체크 클릭시 
	// 그날이 수업날이라면 나오는 정보 불러오기
	public  List<Map<String, Object>> getLectureAttendanceByDateStudentList(Map<String, Object> paramMap) {
			
			
		/*
		 * List<Map<String, Object>> lectureAttendanceByDateStudentList =
		 * lectureDAO2.getLectureAttendanceByDateStudentList( paramMap);
		 * 
		 * for(Map<String, Object> lectureAttendanceByDateStudent :
		 * lectureAttendanceByDateStudentList) {
		 * 
		 * int status = ((Number)
		 * lectureAttendanceByDateStudent.get("status")).intValue();
		 * 
		 * 
		 * String attend_status=null; switch (status) { case 0: attend_status="미정";
		 * break; case 1: attend_status="출석"; break; case 2: attend_status="지각"; break;
		 * case 3: attend_status="조퇴"; break; case 4: attend_status="지각/조퇴"; break; case
		 * 5: attend_status="결석"; break;
		 * 
		 * } lectureAttendanceByDateStudent.put("attend_status", attend_status); }
		 */
			 return lectureDAO2.getLectureAttendanceByDateStudentList( paramMap);

		}

	// 출석 정보 수정하고 저장 버튼 눌렀을때 집계결과와 출결 테이블 결석 등 상태 업데이트

		@Override
		public int getAttendanceUpdate(Map<String, Object> paramMap) {
				return lectureDAO2.getAttendanceUpdate( paramMap);

		}

		@Override
		public String getTName(Integer t_idx) {
			return lectureDAO2.getTName(t_idx);
		}
			
		
	
	
	



}
