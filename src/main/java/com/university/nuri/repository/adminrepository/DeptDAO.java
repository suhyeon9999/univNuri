package com.university.nuri.repository.adminrepository;

import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.university.nuri.vo.adminvo.DepartMentVO;

@Repository
@Transactional
public class DeptDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	// 전체 학과 리스트 불러오기
	public List<Map<String, Object>> getAllDeptList() {
		try {
			return sqlSessionTemplate.selectList("department.getAllDeptList");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// dept_id 중복체크
	public int chkDeptId(String deptId) {
		try {
			return sqlSessionTemplate.selectOne("department.chkDeptId",deptId);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// 학과등록
	public int insertDept(DepartMentVO deptVO) {
		try {
			return sqlSessionTemplate.insert("department.insertDept",deptVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// 학과 검색
	public List<Map<String, Object>> searchDept(String searchType, String keyword) {
		try {
			if ("dept_name".equals(searchType)) {
				return sqlSessionTemplate.selectList("department.searchDeptName",keyword);
			}else if ("t_name".equals(searchType)) {
				return sqlSessionTemplate.selectList("department.searchTName",keyword);				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	// 학과장 미배정 학과 보기
	public List<Map<String, Object>> searchNullTIdx() {
		try {
			return sqlSessionTemplate.selectList("department.searchNullTIdx");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 학과 상세보기
	public Map<String, Object> detailDept(String dept_idx) {
		try {
			return sqlSessionTemplate.selectOne("department.detailDept", dept_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 해당 학과 교수 조회
	public List<Map<String, Object>> teacherList(String dept_idx) {
		try {
			return sqlSessionTemplate.selectList("department.teacherList", dept_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 학과 정보 수정
	@Transactional
	public int updateDept(DepartMentVO deptVO) {
		try {						
			int result = sqlSessionTemplate.update("department.updateDept",deptVO);
			if (result == 1) {								
				String t_idx = sqlSessionTemplate.selectOne("department.selectTIdx",deptVO);
				if (t_idx != null) {
					// 학과수정 성공-> 교수 학과장여부 수정					
					sqlSessionTemplate.update("department.changeChair",deptVO);
					sqlSessionTemplate.update("department.updateTeacher", t_idx);	
					return 1;
				}
				return 1;
			}
			return 0;			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("등록 실패");
		}
	}
	// 학과삭제
	@Transactional
	public int deleteDept(DepartMentVO deptVO) {
		try {						
			int result =  sqlSessionTemplate.update("department.deleteDept",deptVO);
			
			if (result == 1) {								
				sqlSessionTemplate.update("department.changeChair",deptVO);
				return 1;
			}
			return 0;			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("삭제 실패");
		}
	}



}
