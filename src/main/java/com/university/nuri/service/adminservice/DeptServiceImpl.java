package com.university.nuri.service.adminservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.nuri.repository.adminrepository.DeptDAO;
import com.university.nuri.vo.adminvo.DepartMentVO;

@Service
public class DeptServiceImpl implements DeptService{
	@Autowired
	private DeptDAO deptDAO;

	// 전체 학과 리스트 불러오기
	@Override
	public List<Map<String, Object>> getAllDeptList() {		
		return deptDAO.getAllDeptList();
	}
	
	// dept_id 중복검사
	@Override
	public int chkDeptId(String deptId) {
		return deptDAO.chkDeptId(deptId);
	}
	
	// 학과등록
	@Override
	public int insertDept(DepartMentVO deptVO) {
		return deptDAO.insertDept(deptVO);
	}
	// 학과 검색
	@Override
	public List<Map<String, Object>> searchDept(String searchType, String keyword) {
		return deptDAO.searchDept(searchType, keyword);
	}
	// 학과장 미배정 학과 보기
	@Override
	public List<Map<String, Object>> searchNullTIdx() {		
		return deptDAO.searchNullTIdx();
	}
	// 관리자 상세보기
	@Override
	public Map<String, Object> detailDept(String dept_idx) {		
		return deptDAO.detailDept(dept_idx);
	}
	// 해당 학과 교수 조회
	@Override	
	public List<Map<String, Object>> teacherList(String dept_idx) {
		return deptDAO.teacherList(dept_idx);
	}
	// 학과 정보 수정
	@Override
	public int updateDept(DepartMentVO deptVO) {				
		return deptDAO.updateDept(deptVO);
	}
	// 학과 삭제
	@Override
	public int deleteDept(DepartMentVO deptVO) {
		return deptDAO.deleteDept(deptVO);
	}
}
