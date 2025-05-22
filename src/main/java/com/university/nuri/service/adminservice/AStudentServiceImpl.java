package com.university.nuri.service.adminservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.adminrepository.AStudentDAO;
import com.university.nuri.vo.commonvo.UserVO;
import com.university.nuri.vo.studentvo.StudentVO;

@Service
public class AStudentServiceImpl implements AStudentService{
	@Autowired
	private AStudentDAO aStudentDAO;
	
	@Override
	public List<Map<String, Object>> getStudentList() {
		return aStudentDAO.getStudentList();
	}

	@Override
	public List<Map<String, Object>> getFilteredStudentList(String name, String user_id) {
		return aStudentDAO.getFilteredStudentList(name, user_id);
	}
	
	@Override
	public Map<String, Object> getDetailStudent(String user_id) {
		return aStudentDAO.getDetailStudent(user_id);
	}
	
	@Override
	public List<Map<String, Object>> getDeptList() {
		return aStudentDAO.getDeptList();
	}

	@Override
	public int getUpdateUserTable(String user_id, String name, String birth, String email, String phone) {
		return aStudentDAO.getUpdateUserTable(user_id, name, birth, email, 
				phone);
	}
	
	@Override
	public int getUpdateStudentTable(String user_id, int s_grade, int dept_idx, String s_address, String s_address2) {
		return aStudentDAO.getUpdateStudentTable(user_id, s_grade, dept_idx, 
				s_address, s_address2);
	}
	
	@Override
	public String getAStudentCheckPassword(String user_id) {
		return aStudentDAO.getAStudentCheckPassword(user_id);
	}

	@Override
	public int getDeleteSUserTable(String user_id) {
		return aStudentDAO.getDeleteSUserTable(user_id);
	}
	
	@Override
	public int getDeleteSStudentTable(int s_idx) {
		return aStudentDAO.getDeleteSStudentTable(s_idx);
	}
	
	// 학생 등록
	@Override
	public int studentInsert(UserVO userVO, StudentVO studentVO) {
		if(userVO == null && studentVO == null) {
			throw new IllegalArgumentException("VO값들이 다 비었음");
		}
        return aStudentDAO.studentInsert(userVO,studentVO);
        }
	
	// 학생 년도,학과 중복 체크
	@Override
	public int countStudentsThisYear(String yearstr , String deptIdx) {
		return aStudentDAO.countStudentsThisYear(yearstr, deptIdx);
	}
}
