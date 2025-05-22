package com.university.nuri.repository.teacherrepository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LectureDAO2 {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	//강의 관리 메인 화면
	// 강의관리메인 금학기 정보 불러오기,  강의관리메인 과거학기 정보 전체 혹은 검색 불러오기
	public List<Map<String, Object>> getMainSemesterLectureList(Map<String, Object> paramMap) {
		try {

			return sqlSessionTemplate.selectList("lecture2.mainSemesterLectureList", paramMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}


	
	
	
	//------------------------------------------------------------------------------------------------------------------------------------
	// 학생 목록

	// 강의관리메인에서 강의 선택해서 해당 강의 수강하는 학생 리스트에서 학번or 이름 '검색' 조회
	public List<Map<String, Object>> getLectureStudentList(Map<String, Object> paramMap) {
		try {

			return sqlSessionTemplate.selectList("lecture2.studentListMap", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------
	// 출결 관리
	// 해당 강의 듣는 전체 학생 수 
		public int getStudentNum(int lect_idx) {
			try {

				return sqlSessionTemplate.selectOne("lecture2.studentNum", lect_idx);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	
		// 강의 정보 가져오기(강의 시작 날짜 LocalDate로 사용하기 위함, 강의 요일 사용하기 위함)
		public Map<String, Object> getLectureInfo(int lect_idx) {
			try {

				return sqlSessionTemplate.selectOne("lecture2.lectureInfo", lect_idx);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		
		// 강의관리메인에서 강의 선택해서 수업하는 '모든' 날짜'중 (for문이므로 ) '특정 날짜의' 출결 관리 집계 현황
			public Map<String, Object> getAttendanceSummaryListByDateMap(Map<String, Object> paramMap) {
				try {
					
					for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
					    System.out.println("key: " + entry.getKey() + ", value: " + entry.getValue());
					}

					return sqlSessionTemplate.selectOne("lecture2.attendanceSummaryListByDateMap", paramMap);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}




		// 강의관리에서 날짜 눌렀을때 해당 날짜 학생들의 출석 조회, 수정 정보 불러오기 or 출석 체크 클릭시 
		// 그날이 수업날이라면 나오는 정보 불러오기
		public  List<Map<String, Object>> getLectureAttendanceByDateStudentList(Map<String, Object> paramMap) {
				try {

					return sqlSessionTemplate.selectList("lecture2.lectureAttendanceByDateStudentList", paramMap);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}

			}

		// 출석 정보 수정하고 저장 버튼 눌렀을때 집계결과와 출결 테이블 결석 등 상태 업데이트
			public int getAttendanceUpdate(Map<String, Object> paramMap) {
				try {

					return sqlSessionTemplate.update("lecture2.attendanceUpdate", paramMap);
				} catch (Exception e) {
					e.printStackTrace();
					return 0;
				}

			}





			public String getTName(Integer t_idx) {
				try {
					return sqlSessionTemplate.selectOne("lecture2.getTName", t_idx);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}

}
