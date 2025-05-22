package com.university.nuri.service.teacherservice;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.teacherrepository.TMyPageDAO;



@Service
public class TMyPageServiceImpl implements TMyPageService{
	@Autowired
	private TMyPageDAO tMyPageDAO;
	
	@Override
	public Map<String, String> getMyPageInfo(int t_idx) {
		return tMyPageDAO.getMyPageInfo(t_idx);
	}
	
	@Override
	public String checkSpassword(int t_idx) {
		return tMyPageDAO.checkSpassword(t_idx);
	}
	
	@Override
	public int updateSpassword(int t_idx, String newPassword) {
		return tMyPageDAO.updateSpassword(t_idx, newPassword);
	}
	
	@Override
	public int getMyPageInfoUpdateOK(int t_idx, String phone, String email, String birth) {
		return tMyPageDAO.getMyPageInfoUpdateOK(t_idx, phone, email, birth);
	}

	    @Override
	    public boolean insertFileFirst(Map<String, Object> fileParam) {
	        return tMyPageDAO.insertFileFirst(fileParam) > 0;
	    }

	    @Override
	    public boolean updateUserFileIdx(int user_idx, int f_idx) {
	        return tMyPageDAO.updateUserFileIdx(user_idx, f_idx) > 0;
	    }

	    @Override
	    public int updateFname(int f_idx, String f_name, int f_size, int f_type) {
	        return tMyPageDAO.updateFname(f_idx, f_name, f_size, f_type);
	    }

	    @Override
	    public int insertFoldName(int f_idx, String f_old_name) {
	        return tMyPageDAO.insertFoldName(f_idx, f_old_name);
	    }

	    @Override
	    public int updateFnameFoldName(int f_idx, String f_name, int f_size, int f_type, String f_old_name) {
	        return tMyPageDAO.updateFnameFoldName(f_idx, f_name, f_size, f_type, f_old_name);
	    }

	
}