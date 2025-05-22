package com.university.nuri.repository.adminrepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.university.nuri.vo.commonvo.UserVO;
import com.university.nuri.vo.studentvo.StudentVO;

@Repository
public class AStudentDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public List<Map<String, Object>> getStudentList() {
		try {
			return sqlSessionTemplate.selectList("astudent.getStudentList");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Map<String, Object>> getFilteredStudentList(String name, String user_id) {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("name", name);
			params.put("user_id", user_id);

			return sqlSessionTemplate.selectList("astudent.getFilteredStudentList", params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Map<String, Object> getDetailStudent(String user_id) {
		try {
			return sqlSessionTemplate.selectOne("astudent.getDetailStudent", user_id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// DB에 있는 학과들 가져오기
	public List<Map<String, Object>> getDeptList() {
		try {
			return sqlSessionTemplate.selectList("astudent.getDeptList");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getUpdateUserTable(String user_id, String name, String birth, String email, String phone) {
		try {
			Map<String, Object> updateMap = new HashMap<>();
			updateMap.put("user_id", user_id);
			updateMap.put("name", name);
			updateMap.put("birth", birth);
			updateMap.put("email", email);
			updateMap.put("phone", phone);
			return sqlSessionTemplate.update("astudent.getUpdateUserTable", updateMap);

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getUpdateStudentTable(String user_id, int s_grade, int dept_idx, String s_address, String s_address2) {
		try {
			Map<String, Object> updateMap = new HashMap<>();
			updateMap.put("user_id", user_id);
			updateMap.put("s_grade", s_grade);
			updateMap.put("dept_idx", dept_idx);
			updateMap.put("s_address", s_address);
			updateMap.put("s_address2", s_address2);
			return sqlSessionTemplate.update("astudent.getUpdateStudentTable", updateMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public String getAStudentCheckPassword(String user_id) {
		try {
			return sqlSessionTemplate.selectOne("astudent.getAStudentCheckPassword", user_id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getDeleteSUserTable(String user_id) {
		try {
			return sqlSessionTemplate.update("astudent.getDeleteSUserTable", user_id);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getDeleteSStudentTable(int s_idx) {
		try {
			return sqlSessionTemplate.update("astudent.getDeleteSStudentTable", s_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 학생 등록
	@Transactional
	public int studentInsert(UserVO userVO,StudentVO studentVO) {
		try {
			int result = sqlSessionTemplate.insert("astudent.insertUser",userVO);
			if (result == 1) {
				String idx = sqlSessionTemplate.selectOne("astudent.selectUserIdx", userVO);
				studentVO.setUser_idx(idx);
				return sqlSessionTemplate.insert("astudent.insertStudent",studentVO);
			}else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("User 등록 실패");
		}
	}

	// 학생 입학년도 조회 및 학생 수
	public int countStudentsThisYear(@Param("yearstr")String yearstr , @Param("deptIdx")String deptIdx) {
		try {
			return sqlSessionTemplate.selectOne("astudent.yearcount", Map.of("yearstr",yearstr,"deptIdx",deptIdx));
		} catch (Exception e) {
				e.printStackTrace();
		       throw new RuntimeException("찾기 실패");
		}
	}

}
