package com.university.nuri.service.teacherservice;

import java.util.Map;

import com.university.nuri.vo.commonvo.FileVO;
import com.university.nuri.vo.teachervo.TestMakeVO;

public interface ExamService {
	Map<String, Object> getExamDetail(String test_make_idx);
	int examUpdateOK(TestMakeVO testMakeVO);
	Map<String, Object> getTLecName(String lect_idx);
	int examMakeOK(TestMakeVO testMakeVO);
	int getExamDelete(String test_make_idx);
	String getPwd(String t_idx);
	void insertFile(FileVO fileVO);
	int updateFileToTestMake(String test_make_idx, String f_idx);
	int updateFile(FileVO fileVO, String testMakeIdx);
}