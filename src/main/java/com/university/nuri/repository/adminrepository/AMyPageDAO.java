package com.university.nuri.repository.adminrepository;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AMyPageDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public Map<String, String> getMyPage(int admin_idx) {
		try {
			return sqlSessionTemplate.selectOne("amypage.getMyPage", admin_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int updateApassword(int admin_idx, String newPassword) {
		try {
			//s_idx, newPassword를 Map으로 묶어서 전달
			Map<String, Object> updateMap = new HashMap<>();
			updateMap.put("admin_idx", admin_idx);
			updateMap.put("newPassword", newPassword);
			return sqlSessionTemplate.update("amypage.updateApassword", updateMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int insertFileFirst(Map<String, Object> fileParam) {
	        return sqlSessionTemplate.insert("amypage.insertFileFirst", fileParam);
	}

	public int updateUserFileIdx(int user_idx, int f_idx) {
	        Map<String, Object> map = new HashMap<>();
	        map.put("user_idx", user_idx);
	        map.put("f_idx", f_idx);
	        return sqlSessionTemplate.update("amypage.updateUserFileIdx", map);
	}

	public int updateFname(int f_idx, String f_name, int f_size, int f_type) {
	        Map<String, Object> map = new HashMap<>();
	        map.put("f_idx", f_idx);
	        map.put("f_name", f_name);
	        map.put("f_size", f_size);
	        map.put("f_type", f_type);
	        return sqlSessionTemplate.update("amypage.updateFname", map);
	    }

	public int insertFoldName(int f_idx, String f_old_name) {
	        Map<String, Object> map = new HashMap<>();
	        map.put("f_idx", f_idx);
	        map.put("f_old_name", f_old_name);
	        return sqlSessionTemplate.update("amypage.insertFoldName", map);
	    }

	public int updateFnameFoldName(int f_idx, String f_name, int f_size, int f_type, String f_old_name) {
	        Map<String, Object> map = new HashMap<>();
	        map.put("f_idx", f_idx);
	        map.put("f_name", f_name);
	        map.put("f_size", f_size);
	        map.put("f_type", f_type);
	        map.put("f_old_name", f_old_name);
	        return sqlSessionTemplate.update("amypage.updateFnameFoldName", map);
	    }

	public int getMyPageUpdateOK(int admin_idx, String phone, String email, String birth) {
		try {
		        Map<String, Object> updateMap = new HashMap<>();
		        updateMap.put("admin_idx", admin_idx);
		        updateMap.put("phone", phone);
		        updateMap.put("email", email);
		        updateMap.put("birth", birth);
		        return sqlSessionTemplate.update("amypage.getMyPageUpdateOK", updateMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public String checkApassword(int admin_idx) {
		try {
			return sqlSessionTemplate.selectOne("amypage.checkApassword", admin_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
