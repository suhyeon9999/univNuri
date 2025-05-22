package com.university.nuri.repository.teacherrepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.university.nuri.vo.commonvo.AssignVO;
import com.university.nuri.vo.teachervo.TestMakeVO;

@Repository
public class LectureDAO {
    @Autowired
    private SqlSessionTemplate sessionTemplate;

    public Map<String, Object> getLectureInfo(String lect_idx) {
        try {
            return sessionTemplate.selectOne("lecture.lectureInfo", lect_idx);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


	public List<Map<String, Object>> getAssignList(Map<String, Object> paramMap) {
		try {
			return sessionTemplate.selectList("lecture.assignList", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    public List<TestMakeVO> getExamList(String lect_idx) {
        try {
            return sessionTemplate.selectList("lecture.getExamList",lect_idx);
        } catch (Exception e) {
            return null;
        }
    }

    public int midExist(String lect_idx) {
        try {
            return sessionTemplate.selectOne("lecture.midExist",lect_idx);
        } catch (Exception e) {
            return 0;
        }
    }

    public int finalExist(String lect_idx) {
        try {
            return sessionTemplate.selectOne("lecture.finalExist",lect_idx);
        } catch (Exception e) {
            return 0;
        }
    }
}