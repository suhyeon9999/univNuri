package com.university.nuri.service.adminservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.adminrepository.ATeacherDAO;
import com.university.nuri.vo.commonvo.UserVO;
import com.university.nuri.vo.teachervo.TeacherVO;

@Service
public class ATeacherServiceImpl implements ATeacherService{
	@Autowired
	private ATeacherDAO aTeacherDAO;

	@Override
	public List<Map<String, Object>> getTeacherList() {
		List<Map<String, Object>> list = aTeacherDAO.getTeacherList();
		
		for (Map<String, Object> item : list) {

	        int sta = ((Number) item.get("status")).intValue();
					String statusName=null;
					switch (sta) {
					case 4: statusName="재직"; break;
					case 5: statusName="퇴직"; break;
					default: statusName = "미정"; break;
					}
			      item.put("status", statusName);
			      
			int t_dept_chair=((Number) item.get("t_dept_chair")).intValue();
			String deptName=null;
			switch(t_dept_chair) {
			case 0: deptName="X";break;
			case 1: deptName="O"; break;
			}
			item.put("t_dept_chair", deptName);
	    }

	    return list;
	}

	@Override
	public List<Map<String, Object>> getFilteredTeacherList(String name, String user_id) {
		return aTeacherDAO.getFilteredTeacherList(name, user_id);
	}

	@Override
	public Map<String, Object> getDetailTeacher(String user_id) {
		Map<String, Object> list = aTeacherDAO.getDetailTeacher(user_id);
		


	        int sta = ((Number) list.get("status")).intValue();
					String statusName=null;
					switch (sta) {
					case 4: statusName="재직"; break;
					case 5: statusName="퇴직"; break;
					default: statusName = "미정"; break;
					}
					list.put("status", statusName);
					int t_dept_chair = ((Number) list.get("t_dept_chair")).intValue();
					String deptName=null;
					switch(t_dept_chair) {
					case 0: deptName="교수";break;
					case 1: deptName="학과장";break;
					}
					list.put("t_dept_chair", deptName);
	    return list;
	}

	@Override
	public List<Map<String, Object>> getDeptList() {
		return aTeacherDAO.getDeptList();
	}

	@Override
	public int getUpdateUserTable(String user_id, String name, String birth, String email, String phone, int status) {
		return aTeacherDAO.getUpdateUserTable(user_id, name, birth, email, phone, status);
	}

	@Override
	public int getUpdateTeacherTable(String user_id, int dept_idx, int t_dept_chair) {
		return aTeacherDAO.getUpdateStudentTable(user_id, dept_idx, t_dept_chair);
	}

	@Override
	public String getATeacherCheckPassword(String user_id) {
		return aTeacherDAO.getATeacherCheckPassword(user_id);
	}

	@Override
	public int getDeleteTUserTable(String user_id) {
		return aTeacherDAO.getDeleteTUserTable(user_id);
	}

	@Override
	public int getDeleteTeacherTable(int t_idx) {
		return aTeacherDAO.getDeleteTeacherTable(t_idx);
	}

	@Override
	public int getDeptIdxByName(String dept_name) {
		return aTeacherDAO.getDeptIdxByName(dept_name);
	}

	@Override
	public int countTeachersThisYear(String yearstr, String dept_idx) {
		return aTeacherDAO.countStudentsThisYear(yearstr, dept_idx);
	}

	@Override
	public void teacherManyInsert(UserVO userVO, TeacherVO teacherVO) {
		aTeacherDAO.insertUserVO(userVO);
		teacherVO.setUser_idx(userVO.getUser_idx()); // user_idx는 insert 후에 채워짐
		aTeacherDAO.insertTeacherVO(teacherVO);
	}

	@Override
	public String getTIdx(String user_id) {
		return aTeacherDAO.getTIdx(user_id);
	}

	@Override
	public String getUserLevel(String user_id) {
		return aTeacherDAO.getUserLevel(user_id);
	}

}
