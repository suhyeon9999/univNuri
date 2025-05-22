package com.university.nuri.service.adminservice;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.adminrepository.AMyPageDAO;

@Service
public class AMyPageServiceImpl implements AMyPageService{
	@Autowired
	private AMyPageDAO aMyPageDAO;
	
	@Override
	public Map<String, String> getMyPage(int admin_idx) {
		return aMyPageDAO.getMyPage(admin_idx);
	}

	@Override
	public int updateApassword(int admin_idx, String newPassword) {
		return aMyPageDAO.updateApassword(admin_idx, newPassword);
	}

	@Override
	public boolean insertFileFirst(Map<String, Object> fileParam) {
		return aMyPageDAO.insertFileFirst(fileParam) > 0;
	}

	@Override
	public boolean updateUserFileIdx(int user_idx, int f_idx) {
		return aMyPageDAO.updateUserFileIdx(user_idx, f_idx) > 0;
	}

	@Override
	public int updateFname(int f_idx, String f_name, int f_size, int f_type) {
		return aMyPageDAO.updateFname(f_idx, f_name, f_size, f_type);
	}

	@Override
	public int insertFoldName(int f_idx, String f_old_name) {
		return aMyPageDAO.insertFoldName(f_idx, f_old_name);
	}

	@Override
	public int updateFnameFoldName(int f_idx, String f_name, int f_size, int f_type, String f_old_name) {
		return aMyPageDAO.updateFnameFoldName(f_idx, f_name, f_size, f_type, f_old_name);
	}

	@Override
	public int getMyPageUpdateOK(int admin_idx, String phone, String email, String birth) {
		return aMyPageDAO.getMyPageUpdateOK(admin_idx, phone, email, birth);
	}

	@Override
	public String checkApassword(int admin_idx) {
		return aMyPageDAO.checkApassword(admin_idx);
	}

}
