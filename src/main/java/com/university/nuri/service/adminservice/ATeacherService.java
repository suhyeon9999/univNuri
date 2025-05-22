package com.university.nuri.service.adminservice;

import java.util.List;
import java.util.Map;

import com.university.nuri.vo.commonvo.UserVO;
import com.university.nuri.vo.teachervo.TeacherVO;

public interface ATeacherService {

	List<Map<String, Object>> getTeacherList();

	List<Map<String, Object>> getFilteredTeacherList(String name, String user_id);

	Map<String, Object> getDetailTeacher(String user_id);

	List<Map<String, Object>> getDeptList();

	int getUpdateUserTable(String user_id, String name, String birth, String email, String phone, int status);

	int getUpdateTeacherTable(String user_id, int dept_idx, int t_dept_chair);

	String getATeacherCheckPassword(String user_id);

	int getDeleteTUserTable(String user_id);

	int getDeleteTeacherTable(int t_idx);

	int getDeptIdxByName(String dept_name);

	int countTeachersThisYear(String yearstr, String dept_idx);

	void teacherManyInsert(UserVO userVO, TeacherVO teacherVO);

	String getTIdx(String user_id);

	String getUserLevel(String user_id);

}
