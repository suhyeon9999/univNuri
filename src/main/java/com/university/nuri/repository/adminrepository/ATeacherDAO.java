package com.university.nuri.repository.adminrepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.university.nuri.vo.commonvo.UserVO;
import com.university.nuri.vo.teachervo.TeacherVO;

@Repository
public class ATeacherDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;

	public List<Map<String, Object>> getTeacherList() {
		try {
			return sessionTemplate.selectList("ateacher.getTeacherList");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Map<String, Object>> getFilteredTeacherList(String name, String user_id) {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("name", name);
			params.put("user_id", user_id);

			return sessionTemplate.selectList("ateacher.getFilteredTeacherList", params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Map<String, Object> getDetailTeacher(String user_id) {
		try {
			return sessionTemplate.selectOne("ateacher.getDetailTeacher", user_id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Map<String, Object>> getDeptList() {
		try {
			return sessionTemplate.selectList("ateacher.getDeptList");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getUpdateUserTable(String user_id, String name, String birth, String email, String phone, int status) {
		try {
			Map<String, Object> updateMap = new HashMap<>();
			updateMap.put("user_id", user_id);
			updateMap.put("name", name);
			updateMap.put("birth", birth);
			updateMap.put("email", email);
			updateMap.put("phone", phone);
			updateMap.put("status", status);
			return sessionTemplate.update("ateacher.getUpdateUserTable", updateMap);

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getUpdateStudentTable(String user_id, int dept_idx, int t_dept_chair) {
		try {
			Map<String, Object> updateMap = new HashMap<>();
			updateMap.put("user_id", user_id);
			updateMap.put("dept_idx", dept_idx);
			updateMap.put("t_dept_chair", t_dept_chair);
			return sessionTemplate.update("ateacher.getUpdateTeacherTable", updateMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public String getATeacherCheckPassword(String user_id) {
		try {
			return sessionTemplate.selectOne("ateacher.getATeacherCheckPassword", user_id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getDeleteTUserTable(String user_id) {
		try {
			return sessionTemplate.update("ateacher.getDeleteTUserTable", user_id);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getDeleteTeacherTable(int t_idx) {
		try {
			return sessionTemplate.update("ateacher.getDeleteTeacherTable", t_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getDeptIdxByName(String dept_name) {
		try {
			return sessionTemplate.selectOne("ateacher.getDeptIdxByName", dept_name);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int countStudentsThisYear(String yearstr, String dept_idx) {
		try {
			return sessionTemplate.selectOne("ateacher.yearcount", Map.of("yearstr",yearstr,"deptIdx",dept_idx));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("찾기 실패");
		}
	}

	public void insertUserVO(UserVO userVO) {
		try {
			// 1. user 테이블 먼저 insert
			sessionTemplate.insert("ateacher.insertUserVO", userVO);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("사용자 등록 중 오류 발생");
		}
	}

	public void insertTeacherVO(TeacherVO teacherVO) {
		try {
			// 1. user 테이블 먼저 insert
			sessionTemplate.insert("ateacher.insertTeacherVO", teacherVO);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("사용자 등록 중 오류 발생");
		}
	}

	public String getTIdx(String user_id) {
		try {
			return sessionTemplate.selectOne("ateacher.getTIdx", user_id);
		} catch (Exception e) {
			return null;
		}
	}

	public String getUserLevel(String user_id) {
		try {
			return sessionTemplate.selectOne("ateacher.getUserLevel", user_id);
		} catch (Exception e) {
			return null;
		}
	}
}
