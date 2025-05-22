package com.university.nuri.repository.adminrepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class AEnrollFixDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	
	public int GetEnrollStatusOneUpdate() {
		try {
			return sqlSessionTemplate.update("aenrollfix.getEnrollStatusOneUpdate");
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		
	}
	
	 	    // enroll_apply_idx에 따라 enroll_apply에서 날짜 구해오기
	public Timestamp  GetEnrollApplyYear(String enroll_apply_idx) {
	    try {
	        return sqlSessionTemplate.selectOne("aenrollfix.getEnrollApplyYear", enroll_apply_idx);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	
	public List<String> GetEnrollStatusFIxList(Timestamp start_time){
		
		try {
			return sqlSessionTemplate.selectList("aenrollfix.getEnrollStatusFIxList", start_time);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public int GetLectIdx(String enroll_idx) {
		
		try {
			return sqlSessionTemplate.selectOne("aenrollfix.getLectIdx", enroll_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		
	}

		// 해당하는 enroll_idx에 따른 score 테이블에 score 데이터 넣기
		public int InsertScoreByEnrollIdx(String enroll_idx) {
			
			
			try {
				return sqlSessionTemplate.insert("aenrollfix.insertScoreByEnrollIdx", enroll_idx);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	

		public int InsertAttendanceByEnrollIdx(Map<String, Object> paramMap) {
			
			try {
				return sqlSessionTemplate.insert("aenrollfix.insertAttendanceByEnrollIdx", paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}

	}


