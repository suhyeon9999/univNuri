package com.university.nuri.service.adminservice;

import java.util.List;
import java.util.Map;

import com.university.nuri.vo.commonvo.UserVO;
import com.university.nuri.vo.studentvo.StudentVO;

public interface AStudentService {

	public List<Map<String, Object>> getStudentList();

	public List<Map<String, Object>> getFilteredStudentList(String name, String user_id);

	public Map<String, Object> getDetailStudent(String user_id);

	public List<Map<String, Object>> getDeptList();

	public int getUpdateUserTable(String user_id, String name, String birth, String email, String phone);

	public int getUpdateStudentTable(String user_id, int s_grade, int dept_idx, String s_address, String s_address2);

	public String getAStudentCheckPassword(String user_id);

	public int getDeleteSUserTable(String user_id);

	public int getDeleteSStudentTable(int s_idx);

	// 학생 등록
	public int studentInsert(UserVO userVO, StudentVO studentVO);

	public int countStudentsThisYear(String yearstr , String deptIdx);


}
