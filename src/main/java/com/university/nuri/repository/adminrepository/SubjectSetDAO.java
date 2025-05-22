package com.university.nuri.repository.adminrepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.university.nuri.vo.adminvo.SubjectSetVO;

@Repository
public class SubjectSetDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	// 과목군리스트
	public List<Map<String, Object>> getAllSubjectSetList() {		
		try {
			return sqlSessionTemplate.selectList("subject_set.getAllSubjectSetList");			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	// 과목 조건 검색
	public List<Map<String, Object>> searchSubjectSet(String searchType, String keyword) {
		try {
			if ("dept_name".equals(searchType)) {
				return sqlSessionTemplate.selectList("subject_set.searchDeptName",keyword);
			}else if ("sub_set_name".equals(searchType)) {
				return sqlSessionTemplate.selectList("subject_set.searchSubjectName",keyword);				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	// 과목군 번호 확인
	public String getNextSubSetNum() {
		try {
			return sqlSessionTemplate.selectOne("subject_set.getNextSubSetNum");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 과목군명 중복검사
	public boolean chkSubSetName(String sub_set_name) {
	    try {
	        Integer count = sqlSessionTemplate.selectOne("subject_set.chkSubSetName", sub_set_name);
	        System.out.println("쿼리로 조회된 count: " + count); // 디버깅용 로그

	        return count != null && count > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// 과목군 등록
	public int insertSubjectSet(List<SubjectSetVO> subjectSetList) {
		try {
			return sqlSessionTemplate.insert("subject_set.insertSubjectSet",subjectSetList);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// 과목 상세보기
	public List<Map<String, Object>> detailSubjectSet(int sub_set_num) {
		try {
			return sqlSessionTemplate.selectList("subject_set.detailSubjectSet", sub_set_num);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 과목군 수정 active
	public int updateSubjectSet(int subSetNumInt) {
		try {
			return sqlSessionTemplate.update("subject_set.updateSubjectSet", subSetNumInt);			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
