package com.university.nuri.service.adminservice;

import java.util.List;
import java.util.Map;

import com.university.nuri.vo.adminvo.SubjectVO;

public interface SubjectService {
	// 과목리스트
	List<Map<String, Object>> getAllSubjectList();
	// 과목등록
	int insertSubject(SubjectVO subjectVO);
	// 과목 상세보기
	Map<String, Object> detailSubject(String subject_idx);
	// 과목삭제
	int deleteSubject(String subject_idx);
	// 과목 조건 검색
	List<Map<String, Object>> searchSubject(String searchType, String keyword);

	

}
