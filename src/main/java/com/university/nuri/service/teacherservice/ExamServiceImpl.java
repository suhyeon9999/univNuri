package com.university.nuri.service.teacherservice;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.teacherrepository.ExamDAO;
import com.university.nuri.vo.commonvo.FileVO;
import com.university.nuri.vo.teachervo.TestMakeVO;

@Service
public class ExamServiceImpl implements ExamService{
	
	@Autowired
	private ExamDAO examDAO;

	@Override
	public Map<String, Object> getExamDetail(String test_make_idx) {
	    Map<String, Object> examDetail = examDAO.getExamDetail(test_make_idx);

	    if (examDetail == null || examDetail.get("mid_final") == null) {
	        throw new IllegalArgumentException("시험 정보를 찾을 수 없습니다. test_make_idx: " + test_make_idx);
	    }

	    int midFinal = (int) examDetail.get("mid_final");
	    String midFinalName = (midFinal == 0) ? "중간고사" : "기말고사";
	    examDetail.put("midFinalName", midFinalName);
	    return examDetail;
	}

	@Override
	public int examUpdateOK(TestMakeVO testMakeVO) {
		return examDAO.examUpdateOK(testMakeVO);
	}


	@Override
	public Map<String, Object> getTLecName(String lect_idx) {
		return examDAO.getTLecName(lect_idx);
	}


	@Override
	public int examMakeOK(TestMakeVO testMakeVO) {
		return examDAO.examMakeOK(testMakeVO);
	}


	@Override
	public int getExamDelete(String test_make_idx) {
		return examDAO.getExamDelete(test_make_idx);
	}

	@Override
	public String getPwd(String t_idx) {
		return examDAO.getPwd(t_idx);
	}

	@Override
	public void insertFile(FileVO fileVO) {
		examDAO.insertFile(fileVO);
	}

	@Override
	public int updateFileToTestMake(String test_make_idx, String f_idx) {
		return examDAO.updateFileToTestMake(test_make_idx, f_idx);
	}

	@Override
	public int updateFile(FileVO fileVO, String testMakeIdx) {
		return examDAO.updateFile(fileVO, testMakeIdx);
	} 
}
