package com.university.nuri.repository.adminrepository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LectDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	// 강의 리스트
	public List<Map<String, Object>> getAllLectList() {
		try {
			return sqlSessionTemplate.selectList("lecture.getAllLectList");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 강의 상세보기
	public Map<String, Object> detailLect(String lect_idx) {
		try {
			return sqlSessionTemplate.selectOne("lecture.detailLect",lect_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 강의 상세보기 -과목,과목군 불러오기
	public List<Map<String, Object>> lestSubSetList(String lect_idx) {
		try {
			return sqlSessionTemplate.selectList("lecture.lestSubSetList",lect_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 정보 미입력 강의보기
	public List<Map<String, Object>> searchNullLect() {
		try {
			return sqlSessionTemplate.selectList("lecture.searchNullLect");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 강의검색
	// 강의코드로 검색		
	public List<Map<String, Object>> searchLectId(String lect_id) {
		try {
			return sqlSessionTemplate.selectList("lecture.searchLectId", lect_id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 강의명으로 검색
	public List<Map<String, Object>> searchLectName(String lect_name) {
		try {
			return sqlSessionTemplate.selectList("lecture.searchLectName", lect_name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 개설학과로 검색
	public List<Map<String, Object>> searchDeptName(String dept_name) {
		try {
			return sqlSessionTemplate.selectList("lecture.searchDeptName", dept_name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 강의요일로 검색
	public List<Map<String, Object>> searchLectDay(String lect_day) {
		try {
			return sqlSessionTemplate.selectList("lecture.searchLectDay", lect_day);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 담당교수로 검색
	public List<Map<String, Object>> searchName(String name) {
		try {
			return sqlSessionTemplate.selectList("lecture.searchName", name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
