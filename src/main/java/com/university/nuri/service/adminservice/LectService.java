package com.university.nuri.service.adminservice;

import java.util.List;
import java.util.Map;

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

}
