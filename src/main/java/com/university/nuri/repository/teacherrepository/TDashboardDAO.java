package com.university.nuri.repository.teacherrepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TDashboardDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;

	public Map<String, Object> getTInfo(String t_idx) {
		try {
			return sessionTemplate.selectOne("tdashboard.getTInfo",t_idx);
		} catch (Exception e) {
			return null;
		}
	}

	public int getobjectCount(String t_idx) {
		try {
			return sessionTemplate.selectOne("tdashboard.getobjectCount", t_idx);
		} catch (Exception e) {
			return 0;
		}
	}

	public int getAllLecCount(String t_idx) {
		try {
			return sessionTemplate.selectOne("tdashboard.getAllLecCount", t_idx);
		} catch (Exception e) {
			return 0;
		}
	}

	public List<Map<String, Object>> tthisLectList(String t_idx) {
		try {
			return sessionTemplate.selectList("tdashboard.tthisLectList", t_idx);
		} catch (Exception e) {
			return null;
		}
	}

	public List<Map<String, Object>> ttodayLectList(String t_idx, int todayDay) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String today = String.valueOf(todayDay);
			map.put("t_idx", t_idx);
			map.put("lect_day", today);
			return sessionTemplate.selectList("tdashboard.todayLectList", map);
		} catch (Exception e) {
			return null;
		}
	}

}
