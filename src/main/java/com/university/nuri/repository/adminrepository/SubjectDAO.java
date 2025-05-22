package com.university.nuri.repository.adminrepository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.university.nuri.vo.adminvo.SubjectVO;

@Repository
public class SubjectDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	// 과목리스트
	public List<Map<String, Object>> getAllSubjectList() {
		try {
			return sqlSessionTemplate.selectList("subject.getAllSubjectList");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	// 과목등록
	public int insertSubject(SubjectVO subjectVO) {
		try {
			return sqlSessionTemplate.insert("subject.insertSubject",subjectVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// 과목 상세보기
	public Map<String, Object> detailSubject(String subject_idx) {
		try {
			return sqlSessionTemplate.selectOne("subject.detailSubject", subject_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 과목 삭제
	public int deleteSubject(String subject_idx) {
		try {
			return sqlSessionTemplate.update("subject.deleteSubject",subject_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// 학과 조건 검색
	public List<Map<String, Object>> searchSubject(String searchType, String keyword) {
		try {
			if ("dept_name".equals(searchType)) {
				return sqlSessionTemplate.selectList("subject.searchDeptName",keyword);
			}else if ("subject_name".equals(searchType)) {
				return sqlSessionTemplate.selectList("subject.searchSubjectName",keyword);				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	 
}
