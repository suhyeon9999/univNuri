package com.university.nuri.repository.commonrepository;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.university.nuri.vo.commonvo.UserVO;

@Repository
public class LoginRepository {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public UserVO getLoginOK(String user_id) {
		try {
			return sqlSessionTemplate.selectOne("login.loginchk", user_id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public Map<String, Object> getByOccupationIdx(String user_idx, String user_level) {
		try {	
			 switch (user_level) {
	            case "0":  return sqlSessionTemplate.selectOne("login.getStudentByUserIdx", user_idx);
	            case "1":  return sqlSessionTemplate.selectOne("login.getTeacherByUserIdx", user_idx);
	            case "2":  return sqlSessionTemplate.selectOne("login.getAdminByUserIdx", user_idx);
	            default: System.out.println("DAO ⚠ Unknown user_level: " + user_level);  return null;
			 	}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 유저별 권한 가져오기
	public Map<String, Object> GetAccess(String admin_idx){
		try {
			return sqlSessionTemplate.selectOne("login.access",admin_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
