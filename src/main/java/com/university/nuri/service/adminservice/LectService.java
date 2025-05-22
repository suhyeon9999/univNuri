package com.university.nuri.service.adminservice;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

public interface LectService {
	// 강의 리스트
	List<Map<String, Object>> getAllLectList();
	// 강의 상세보기
	Map<String, Object> detailLect(String lect_idx);
	// 강의 상세보기 -과목,과목군 불러오기
	List<Map<String, Object>> lestSubSetList(String lect_idx);
	// 정보 미입력 강의 보기
	List<Map<String, Object>> searchNullLect();
	// 강의검색
	// 강의코드로 검색
	List<Map<String, Object>> searchLectId(String lect_id);
	// 강의명으로 검색
	List<Map<String, Object>> searchLectName(String lect_name);	
	// 개설학과로 검색
	List<Map<String, Object>> searchDeptName(String dept_name);	
	// 강의요일로 검색
	List<Map<String, Object>> searchLectDay(String lect_day);	
	// 담당교수로 검색
	List<Map<String, Object>> searchName(String name);
	// 강의등록 전 과목군 검색
		List<Map<String, Object>> getGroupListByDept(Map<String, String> paramMap);
		// 강의등록 학과 불러오기
		List<Map<String, Object>> getAllActiveDepts();
		// 강의등록 선생 불러오기
		List<Map<String, Object>> getAllActiveTeachers();
		// 강의 등록할때 학과 선택하면 선생 불러오기
		List<Map<String, Object>> getTeachersByDept(@RequestParam("dept_idx") String deptIdx);
		// 강의건물 선택 시 해당 강의실 목록 조회
		List<Map<String, Object>> getRoomsByBuilding(String building);
		// 강의실 건물,호실 선택하면 시간 나오게
		List<String> getAvailableStartTimes(@RequestParam Map<String, String> paramMap);
		// 강의등록 서비스 위임
		 int insertLecture(Map<String, String> param);
		 String generateLectureId(String deptIdx);
		 List<Map<String, Object>> getSubjectGroupListByLecture(String lectIdx);
		 boolean deleteLecture(String lect_idx);
		 List<String> getSubjectGroupsByLectureIdx(String dept_idx); 
		 List<Map<String, Object>> getProfessorListByDept(String dept_idx);
		 List<String> classRoomListByBuilding(String building);
		 boolean updateLecture(Map<String, Object> param); // 강의 정보 수정
}
