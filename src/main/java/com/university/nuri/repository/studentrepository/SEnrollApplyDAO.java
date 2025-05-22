package com.university.nuri.repository.studentrepository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.university.nuri.vo.commonvo.EnrollApplyVO;

@Repository
public class SEnrollApplyDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	// start, end 시간 가져오기
	public List<EnrollApplyVO> GetCurrentEnrollPeriod() {
		try {
			return sqlSessionTemplate.selectList("senrollapply.getCurrentEnrollPeriod");  // 가장 최근 1건만 가져오도록
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 수강신청 리스트 상단
	public List<Map<String, Object>> SearchLectureList(Map<String, String> params) {
		try {
			return sqlSessionTemplate.selectList("senrollapply.searchLectureList",params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 수강 신청 리스트 하단
	public List<Map<String, Object>> getEnrolledLectureList(String s_idx) {
		return sqlSessionTemplate.selectList("senrollapply.getEnrolledLectureList",s_idx);
	}
	// 수강 신청 OK
	public int InsertEnroll(Map<String, Object> params) {
		try {
			return sqlSessionTemplate.insert("senrollapply.insertEnroll",params);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// 수강 신청 cancel
	public int CancelEnroll(Map<String, Object> params) {
		try {
			return sqlSessionTemplate.update("senrollapply.cancelEnroll",params);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// 수강 신청 상태 조회
	public Integer getEnrollStatus(Map<String, Object> params) {
		try {
			return sqlSessionTemplate.selectOne("senrollapply.getEnrollStatus",params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 수강 신청 쉬소한거 재신청
	public int ReApplyEnroll(Map<String, Object> params) {
		try {
			return sqlSessionTemplate.update("senrollapply.ReApplyEnroll",params);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// 수강신청 가능 학점
	public int getMaxCredit(String s_idx) {
		try {
			return sqlSessionTemplate.selectOne("senrollapply.getMaxCredit", s_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
