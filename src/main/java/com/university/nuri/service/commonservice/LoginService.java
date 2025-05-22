package com.university.nuri.service.commonservice;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.commonrepository.LoginRepository;
import com.university.nuri.vo.commonvo.UserVO;

@Service
public class LoginService {
	@Autowired
	private LoginRepository loginRepository;
	
	public UserVO getLoginOK(String user_id) {
		return loginRepository.getLoginOK(user_id);
	}
	public Map<String, Object> getByOccupationIdx(String user_idx, String user_level) {
		return loginRepository.getByOccupationIdx(user_idx, user_level);
	}
}
