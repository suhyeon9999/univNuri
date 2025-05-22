package com.university.nuri.service.adminservice;

import com.university.nuri.vo.commonvo.AccessVO;

public interface AccessService {
	// 권한 저장
	public int updateAccess(AccessVO accessVO);
	// 관리자 권한 불러오기
	public  AccessVO getAccessByGrade(String a_grade);
	// 특정 권한 불러오기
	
}
