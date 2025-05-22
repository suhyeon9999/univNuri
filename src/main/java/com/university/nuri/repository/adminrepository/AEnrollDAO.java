package com.university.nuri.repository.adminrepository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AEnrollDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	// 수강관리- 개설된 강의 리스트 출력
	public List<Map<String, Object>> getOpenLecture() {
		try {
			return sqlSessionTemplate.selectList("aenroll.getOpenLecture");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// select value 학과 이름으로 찾기
	public List<Map<String, Object>> searchDeptNameEnroll(String dept_name) {
		try {
			return sqlSessionTemplate.selectList("aenroll.searchDeptNameEnroll", dept_name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Map<String, Object>> searchBySemester(Map<String, Object> parpMap) {
		try {
			return sqlSessionTemplate.selectList("aenroll.searchBySemester", parpMap);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Map<String, Object>> searchLectures(Map<String, String> paramMap) {
		try {
			return sqlSessionTemplate.selectList("aenroll.searchLectures", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	
	
	public List<Map<String, Object>> getEnrollApplyReservationIs() {
		try {
			return sqlSessionTemplate.selectList("aenroll.getEnrollApplyReservationIs");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Map<String, Object>> searchEnrollApplyLecturesReservationLook() {
		try {
			return sqlSessionTemplate.selectList("aenroll.searchEnrollApplyLecturesReservationLook");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Map<String, Object>> searchEnrollApplyReservationDateTime(Integer enroll_apply_idx) {
		try {
			return sqlSessionTemplate.selectList("aenroll.searchEnrollApplyReservationDateTime", enroll_apply_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Map<String, Object>> searchEnrollApplyLectures() {
		try {
			return sqlSessionTemplate.selectList("aenroll.searchEnrollApplyLectures");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public int insertEnrollApplyFirstReservation(Map<String, Object> paramMap) {
		try {
			return sqlSessionTemplate.insert("aenroll.insertEnrollApplyFirstReservation", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int updateEnrollApplyLectActiveZero(String lect_idx) {
		try {
			return sqlSessionTemplate.update("aenroll.updateEnrollApplyLectActiveZero", lect_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	
	public int updateEnrollApplyActiveOne(String enroll_apply_idx) {
		try {
			return sqlSessionTemplate.update("aenroll.updateEnrollApplyActiveOne", enroll_apply_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	
	public int updateEnrollApplyLectActiveTwo(String lect_idx) {
		try {
			return sqlSessionTemplate.update("aenroll.updateEnrollApplyLectActiveTwo", lect_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	
	public int updateEnrollApplyFirstReservation(Map<String, Object> paramMap) {
		try {
			return sqlSessionTemplate.update("aenroll.updateEnrollApplyFirstReservation", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int updateEnrollApplyLectActiveOne(String lect_idx) {
		try {
			return sqlSessionTemplate.update("aenroll.updateEnrollApplyLectActiveOne", lect_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	

}
