package com.university.nuri.service.adminservice;

import java.util.List;
import java.util.Map;

import com.university.nuri.vo.adminvo.DepartMentVO;

public interface DeptService {
	// 학과 전체 리스트 출력
	public List<Map<String, Object>> getAllDeptList();
	// dept_id 중복검사
	public int chkDeptId(String deptId);
	// 학과등록
	public int insertDept(DepartMentVO deptVO);
	// 학과 검색
	public List<Map<String, Object>> searchDept(String searchType, String keyword);
	// 학과장 미배정 학과 보기
	public List<Map<String, Object>> searchNullTIdx();
	// 관리자 상세보기
	public Map<String, Object> detailDept(String dept_idx);
	// 해당 학과 교수 조회
	public List<Map<String, Object>> teacherList(String dept_idx);
	// 학과 수정
	public int updateDept(DepartMentVO deptVO);
	// 학과 삭제
	public int deleteDept(DepartMentVO deptVO);
	

}
