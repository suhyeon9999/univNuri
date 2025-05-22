package com.university.nuri.service.adminservice;

import java.util.List;
import java.util.Map;

import com.university.nuri.vo.adminvo.SubjectSetVO;

public interface SubjectSetService {
	// 과목군 리스트
	List<Map<String, Object>> getAllSubjectSetList();
	// 과목군 조건 검색
	List<Map<String, Object>> searchSubjectSet(String searchType, String keyword);
	// 과목군명 중복검사
	boolean chkSubSetName(String sub_set_name);
	// 과목군 등록
	String insertSubjectSet(SubjectSetVO subjectSetVO, String[] subjectIdxArr);
	// 과목군 상세보기
	List<Map<String, Object>> detailSubjectSet(int sub_set_num);
	// 과목군 수정
	int updateSubjectSet(SubjectSetVO subjectSetVO, int subSetNumInt, String[] subjectIdxArr);
	// 과목군 삭제
	int deleteSubjectSet(int sub_set_num);
	
	
}
