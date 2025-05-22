package com.university.nuri.service.adminservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.adminrepository.AccessDAO;
import com.university.nuri.vo.commonvo.AccessVO;

@Service
public class AccessServiceImpl implements AccessService{
	@Autowired
	private AccessDAO accessDAO;
	
	// 권한 저장
	@Override
	public int updateAccess(AccessVO accessVO) {
		return accessDAO.updateAccess(accessVO);
	}
	// 관리자 권한 불러오기
	@Override
	public AccessVO getAccessByGrade(String a_grade) {
		return accessDAO.getAccessByGrade(a_grade);
	}
	
}
