package com.university.nuri.repository.studentrepository;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SMyPageDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public Map<String, String> getMyPageInfo(int s_idx) {
		try {
			return sqlSessionTemplate.selectOne("smypage.getMyPageInfo", s_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String checkSpassword(int s_idx) {
		try {
			return sqlSessionTemplate.selectOne("smypage.checkSpassword", s_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int updateSpassword(int s_idx, String newPassword) {
		try {
			//s_idx, newPassword를 Map으로 묶어서 전달
			Map<String, Object> updateMap = new HashMap<>();
			updateMap.put("s_idx", s_idx);
			updateMap.put("newPassword", newPassword);
			return sqlSessionTemplate.update("smypage.updateSpassword", updateMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getMyPageInfoUpdateOK(int s_idx, String phone, String email, String birth, String address1,
			String address2) {
		try {
		        Map<String, Object> updateMap = new HashMap<>();
		        updateMap.put("s_idx", s_idx);
		        updateMap.put("phone", phone);
		        updateMap.put("email", email);
		        updateMap.put("birth", birth);
		        updateMap.put("address1", address1);
		        updateMap.put("address2", address2);
		        return sqlSessionTemplate.update("smypage.getMyPageInfoUpdateOK", updateMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	    public int insertFileFirst(Map<String, Object> fileParam) {
		        return sqlSessionTemplate.insert("smypage.insertFileFirst", fileParam);
		    }

		    public int updateUserFileIdx(int user_idx, int f_idx) {
		        Map<String, Object> map = new HashMap<>();
		        map.put("user_idx", user_idx);
		        map.put("f_idx", f_idx);
		        return sqlSessionTemplate.update("smypage.updateUserFileIdx", map);
		    }

		    public int updateFname(int f_idx, String f_name, int f_size, int f_type) {
		        Map<String, Object> map = new HashMap<>();
		        map.put("f_idx", f_idx);
		        map.put("f_name", f_name);
		        map.put("f_size", f_size);
		        map.put("f_type", f_type);
		        return sqlSessionTemplate.update("smypage.updateFname", map);
		    }

		    public int insertFoldName(int f_idx, String f_old_name) {
		        Map<String, Object> map = new HashMap<>();
		        map.put("f_idx", f_idx);
		        map.put("f_old_name", f_old_name);
		        return sqlSessionTemplate.update("smypage.insertFoldName", map);
		    }

		    public int updateFnameFoldName(int f_idx, String f_name, int f_size, int f_type, String f_old_name) {
		        Map<String, Object> map = new HashMap<>();
		        map.put("f_idx", f_idx);
		        map.put("f_name", f_name);
		        map.put("f_size", f_size);
		        map.put("f_type", f_type);
		        map.put("f_old_name", f_old_name);
		        return sqlSessionTemplate.update("smypage.updateFnameFoldName", map);
		    }

}
