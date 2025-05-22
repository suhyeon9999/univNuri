package com.university.nuri.repository.studentrepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.university.nuri.vo.studentvo.SubmitFileVO;
import com.university.nuri.vo.studentvo.SubmitVO;

@Repository
public class SEnrollDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	//수강관리 강의목록 불러오기
	public List<Map<String, Object>> getsEnrollcurrent(String s_idx) {
		try {
			return sqlSessionTemplate.selectList("senroll.senrollcurrent", s_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 수강 강의 조건 검색
	public List<Map<String, Object>> searchEnrollPast(Map<String, String> searchParams) {
		try {
			return sqlSessionTemplate.selectList("senroll.senrollpast", searchParams);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 연도 리스트
	public List<Integer> getLectureYears() {
		try {
			return sqlSessionTemplate.selectList("senroll.getLectureYears");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 학기 리스트
	public List<Integer> getLectureSemesters() {
		try {
			return sqlSessionTemplate.selectList("senroll.getLectureSemesters");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 수강중인 강의 디테일에 대한 정보
	public List<Map<String, Object>> getTaskSubmitStatus(Map<String, String> params) {
		try {
			return sqlSessionTemplate.selectList("senroll.getTaskSubmitStatus", params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//디테일에 쓸 강의 이름가져오기
	public String getLectureName(String lect_idx) {
		try {
			return sqlSessionTemplate.selectOne("senroll.getlectureName", lect_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 특정 과목에 과제 제출하기전 교수가 적은 정보 불러오기
	public List<Map<String, Object>> getassignInfo(String assign_idx, String enroll_idx) {
	    Map<String, String> param = new HashMap<>();
	    param.put("assign_idx", assign_idx);
	    param.put("enroll_idx", enroll_idx);
	    return sqlSessionTemplate.selectList("senroll.getassignInfo", param);
	}
	// 과목에 과제 제출 하기위한 인덱스
	public String getEnrollIdx(String assign_idx, String s_idx) {
		Map<String, String> param = new HashMap<>();
		param.put("assign_idx", assign_idx);
		param.put("s_idx", s_idx);
		return sqlSessionTemplate.selectOne("senroll.getEnrollIdx", param);
	}
	// 실제 과목에 과제 제출 로직
	@Transactional
	public void insertSubmit(SubmitVO submitVO) {
		 sqlSessionTemplate.insert("senroll.insertSubmit", submitVO);
	}
	// 실제 과목에 과제 제출 로직
	@Transactional
	public void insertSubmitFile(SubmitFileVO fileVO) {
		 sqlSessionTemplate.insert("senroll.insertSubmitFile", fileVO);
	}
	// 과제 수정
	//제출 과제 수정
	public void sEnrollDetailSubjectDetailUpdateOK(SubmitVO submitVO) {
		sqlSessionTemplate.update("senroll.submit",submitVO);
		
	}
	public void sEnrollDetailSubjectDetailUpdateFile(SubmitFileVO sfvo) {
		sqlSessionTemplate.update("senroll.submitfil",sfvo);
	}
	public String getsEnrollcountTotal(String s_idx, String lect_Idx) {
		Map<String, Object> param = new HashedMap<String, Object>();
		param.put("s_idx", s_idx);
		param.put("lectIdx", lect_Idx);
		return sqlSessionTemplate.selectOne("senroll.counttotal", param);
	}
	public String getsEnrollcountPresent(String s_idx, String lect_Idx) {
		Map<String, Object> param = new HashedMap<String, Object>();
		param.put("s_idx", s_idx);
		param.put("lect_Idx", lect_Idx);
		return sqlSessionTemplate.selectOne("senroll.present", param);
	}
	//출결 조회를 위한 enroll index
	public String getEnrollAttend(String lect_idx, String s_idx) {
		Map<String, String> map = new HashedMap<>();
		map.put("lect_idx", lect_idx);
		map.put("s_idx", s_idx);
		return sqlSessionTemplate.selectOne("senroll.getEnrollAttend",map);
	}
	// 출결 리스트
	public List<Map<String, Object>> getAttendanceList(Map<String, String> params) {
		return sqlSessionTemplate.selectList("senroll.getAttendanceList",params);
	}
	// 차시 구하기위한 카운트
	public int getAttendanceCount(String enroll_idx) {
		return sqlSessionTemplate.selectOne("senroll.getAttendanceCount",enroll_idx);
	}
	//강의 정보 불러오기
	public Map<String, Object> getLectInfo(String lect_idx) {
		return sqlSessionTemplate.selectOne("senroll.getLectInfo",lect_idx);
	}
	//Submit Idx 구하기
	public String getSubmitIdx(String assign_idx, String enroll_idx) {
		try {
			Map<String, String> map = new HashedMap<>();
			map.put("assign_idx", assign_idx);
			map.put("enroll_idx", enroll_idx);
			return sqlSessionTemplate.selectOne("senroll.getSubmitIdx",map);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 기존파일 삭제
	public void deleteSubmitFiles(String submit_idx) {
		try {
			sqlSessionTemplate.selectOne("senroll.deleteSubmitFiles",submit_idx);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 전체파일 가져오기
	public List<Map<String, Object>> getAllSubmitFiles(String assign_idx, String enroll_idx) {
		try {
			Map<String, String> map = new HashedMap<>();
			map.put("assign_idx", assign_idx);
			map.put("enroll_idx", enroll_idx);
			return sqlSessionTemplate.selectList("senroll.getAllSubmitFiles", map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
