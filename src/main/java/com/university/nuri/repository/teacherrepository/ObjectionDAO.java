package com.university.nuri.repository.teacherrepository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ObjectionDAO {
	
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	

	public List<Map<String, Object>> getObjectionList(Map<String, Object> paramMap) {
		try {

			return sqlSessionTemplate.selectList("objection.objectionList", paramMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}

	public Map<String, Object> getObjectionDetail(int objection_idx) {
		try {
			
			return sqlSessionTemplate.selectOne("objection.objectionDetail", objection_idx);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public Map<String, Object> getSearchScore(int enroll_idx) {
		try {
			
			return sqlSessionTemplate.selectOne("objection.searchScore",enroll_idx);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public int getScoreUpdateOk(Map<String, Object> paramMap) {
		try {
			
			return sqlSessionTemplate.update("objection.scoreUpdateOk", paramMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	public int getUpdateObjectionStatus(Map<String, Object> paramMap) {
		try {
			
			return sqlSessionTemplate.update("objection.updateObjectionStatus", paramMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

}
