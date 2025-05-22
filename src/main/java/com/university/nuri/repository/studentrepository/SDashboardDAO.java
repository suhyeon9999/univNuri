package com.university.nuri.repository.studentrepository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SDashboardDAO {

	@Autowired
	private SqlSessionTemplate sessionTemplate;

	public Map<String, Object> getSInfo(String s_idx) {
		try {
			return sessionTemplate.selectOne("sdashboard.getSInfo", s_idx);
		} catch (Exception e) {
			return null;
		}
	}

	public List<Map<String, Object>> sThisLectList(String s_idx) {
		try {
			return sessionTemplate.selectList("sdashboard.sThisLectList", s_idx);
		} catch (Exception e) {
			return null;
		}
	}

	public List<Map<String, Object>> sTodayLectList(Map<String, Object> dayParam) {
		try {
			return sessionTemplate.selectList("sdashboard.sTodayLectList", dayParam);
		} catch (Exception e) {
			e.printStackTrace(); 
			return null;
		}
	}

	public Map<String, Integer> getAssignmentStatus(String s_idx) {
		try {
			return sessionTemplate.selectOne("sdashboard.getAssignmentStatus", s_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getCompletedCredit(String s_idx) {
		try {
			return sessionTemplate.selectOne("sdashboard.getCompletedCredit", s_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public List<Map<String, Object>> allLectList(String s_idx) {
		try {
			return sessionTemplate.selectList("sdashboard.allLectList", s_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
