package com.university.nuri.repository.adminrepository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.university.nuri.vo.adminvo.AdminVO;
@Repository
public class AdminDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	


}
