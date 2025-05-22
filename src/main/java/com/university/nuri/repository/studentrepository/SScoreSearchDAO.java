package com.university.nuri.repository.studentrepository;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SScoreSearchDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
    //검색 조건 
	public List<Map<String, Object>> getSScoreSearch(Map<String, String> params) {
		try {
			return sqlSessionTemplate.selectList("sscoresearch.getSScoreSearch",params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 전체 평점, 금학기 평점 계산
	public Map<String, Object> getGPAInfo(String s_idx) {
		try {
			return sqlSessionTemplate.selectOne("sscoresearch.getGPAInfo",s_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 이의 제기 리스트
	public List<Map<String, Object>> getObjectionList(String s_idx) {
		try {
			return sqlSessionTemplate.selectList("sscoresearch.getObjectionList",s_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 이의 제기 수정
	public int sScoreSearchObjectionDetailUpdateOK(Map<String, Object> params) {
		try {
			return sqlSessionTemplate.update("sscoresearch.sScoreSearchObjectionDetailUpdateOK",params);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// 이의 제기 신청
	public void sScoreSearchObjectionDetailInsertOK(Map<String, Object> params) {
		try {
			sqlSessionTemplate.insert("sscoresearch.sScoreSearchObjectionDetailInsertOK",params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Map<String, Object> getObjectionByIdx(String objection_idx) {
		try {
			return sqlSessionTemplate.selectOne("sscoresearch.getObjectionByIdx",objection_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 이의 제기 삭제
	public void sScoreSearchObjectionDetailDeleteOK(Map<String, String> paramMap) {
		try {
			sqlSessionTemplate.update("sscoresearch.sScoreSearchObjectionDetailDeleteOK",paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 전체 이의 제기 검색
	public List<Map<String, Object>> getAllObjectionList(String s_idx) {
		try {
			return sqlSessionTemplate.selectList("sscoresearch.getAllObjectionList",s_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
