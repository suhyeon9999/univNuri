package com.university.nuri.repository.teacherrepository;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.university.nuri.vo.commonvo.FileVO;
import com.university.nuri.vo.teachervo.TestMakeVO;

@Repository
public class ExamDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;

	public Map<String, Object> getExamDetail(String test_make_idx) {
		try {
			return sessionTemplate.selectOne("exam.examdetail", test_make_idx);
		} catch (Exception e) {
			return null;
		}
	}

	public int examUpdateOK(TestMakeVO testMakeVO) {
		try {
			return sessionTemplate.update("exam.examupdate", testMakeVO);
		} catch (Exception e) {
			return 0;
		}
	}

	public Map<String, Object> getTLecName(String lect_idx) {
		try {
			return sessionTemplate.selectOne("exam.getTLecName", lect_idx);
		} catch (Exception e) {
			return null;
		}
	}

	public int examMakeOK(TestMakeVO testMakeVO) {
		try {
			return sessionTemplate.insert("exam.examMakeOK", testMakeVO);
		} catch (Exception e) {
			return 0;
		}
	}

	public int getExamDelete(String test_make_idx) {
		try {
			return sessionTemplate.update("exam.examDelete", test_make_idx);
		} catch (Exception e) {
			return 0;
		}
	}

	public String getPwd(String t_idx) {
		try {
			return sessionTemplate.selectOne("exam.getPwd", t_idx);
		} catch (Exception e) {
			return null;
		}
	}

	public Object insertFile(FileVO fileVO) {
		try {
			return sessionTemplate.insert("exam.insertFile", fileVO);
		} catch (Exception e) {
			return null;
		}
	}

	public int updateFileToTestMake(String test_make_idx, String f_idx) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
	        map.put("test_make_idx", test_make_idx);  
	        map.put("f_idx", f_idx);
			return sessionTemplate.update("exam.updateFileToTestMake", map);
		} catch (Exception e) {
			return 0;
		}
	}

	public int updateFile(FileVO fileVO, String testMakeIdx) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
	        map.put("test_make_idx", testMakeIdx);  
	        map.put("fileVO", fileVO);
			return sessionTemplate.update("exam.updateFile", map);
		} catch (Exception e) {
			return 0;
		}
	}
}


