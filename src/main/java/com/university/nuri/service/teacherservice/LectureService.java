package com.university.nuri.service.teacherservice;

import java.util.List;
import java.util.Map;

import com.university.nuri.vo.commonvo.AssignVO;
import com.university.nuri.vo.teachervo.TestMakeVO;


public interface LectureService {

	Map<String, Object> getLectureInfo(String lect_idx);
	List<Map<String, Object>> getAssignList(Map<String, Object> paramMap);
	List<TestMakeVO> getExamList(String lect_idx);
	int midExist(String lect_idx);
	int finalExist(String lect_idx);
}
