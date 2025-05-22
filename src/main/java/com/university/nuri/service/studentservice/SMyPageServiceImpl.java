package com.university.nuri.service.studentservice;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.studentrepository.SMyPageDAO;

@Service
public class SMyPageServiceImpl implements SMyPageService{
	@Autowired
	private SMyPageDAO sMyPageDAO;
	
	@Override
	public Map<String, String> getMyPageInfo(int s_idx) {
		return sMyPageDAO.getMyPageInfo(s_idx);
	}
	
	@Override
	public String checkSpassword(int s_idx) {
		return sMyPageDAO.checkSpassword(s_idx);
	}
	
	@Override
	public int updateSpassword(int s_idx, String newPassword) {
		return sMyPageDAO.updateSpassword(s_idx, newPassword);
	}
	
	@Override
	public int getMyPageInfoUpdateOK(int s_idx, String phone, String email, String birth, 
			String address1, String address2) {
		return sMyPageDAO.getMyPageInfoUpdateOK(s_idx, phone, email, birth, 
				address1, address2);
	}

	    @Override
	    public boolean insertFileFirst(Map<String, Object> fileParam) {
	        return sMyPageDAO.insertFileFirst(fileParam) > 0;
	    }

	    @Override
	    public boolean updateUserFileIdx(int user_idx, int f_idx) {
	        return sMyPageDAO.updateUserFileIdx(user_idx, f_idx) > 0;
	    }

	    @Override
	    public int updateFname(int f_idx, String f_name, int f_size, int f_type) {
	        return sMyPageDAO.updateFname(f_idx, f_name, f_size, f_type);
	    }

	    @Override
	    public int insertFoldName(int f_idx, String f_old_name) {
	        return sMyPageDAO.insertFoldName(f_idx, f_old_name);
	    }

	    @Override
	    public int updateFnameFoldName(int f_idx, String f_name, int f_size, int f_type, String f_old_name) {
	        return sMyPageDAO.updateFnameFoldName(f_idx, f_name, f_size, f_type, f_old_name);
	    }

	
}
