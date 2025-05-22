package com.university.nuri.repository.adminrepository;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ADashboardDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	

    public int getStudentCount() {
        return sqlSessionTemplate.selectOne("adashboard.getStudentCount");
    }

    public int getTeacherCount() {
        return sqlSessionTemplate.selectOne("adashboard.getTeacherCount");
    }

    public int getDeptCount() {
        return sqlSessionTemplate.selectOne("adashboard.getDeptCount");
    }

    public int getCurrentLectureCount() {
        return sqlSessionTemplate.selectOne("adashboard.getCurrentLectureCount");
    }

    public int getPendingRequestCount() {
        return sqlSessionTemplate.selectOne("adashboard.getPendingRequestCount");
    }

    public Map<String, Object> getEnrollApplyInfo() {
        return sqlSessionTemplate.selectOne("adashboard.getEnrollApplyInfo");
    }
    public Map<String, Object> getEnrollApplyDashBoard() {
    	return sqlSessionTemplate.selectOne("adashboard.getEnrollApplyDashBoard");
    }
}
