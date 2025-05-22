package com.university.nuri.repository.adminrepository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.university.nuri.vo.commonvo.AccessVO;

@Repository
public class AccessDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	//권한 저장
	public int updateAccess(AccessVO accessVO) {
		try {
			return sqlSessionTemplate.update("access.updateAccess",accessVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// 관리자 권한 불러오기
	public AccessVO getAccessByGrade(String a_grade) {
		return sqlSessionTemplate.selectOne("access.getAccessByGrade",a_grade);
	}
}
