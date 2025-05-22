package com.university.nuri.repository.adminrepository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.university.nuri.vo.adminvo.StudentExcelDTO;
import com.university.nuri.vo.commonvo.UserVO;
import com.university.nuri.vo.studentvo.StudentVO;
@Repository
public class ExcelDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public int getDeptIdxByName(String dept_name) {
		try {
			return sqlSessionTemplate.selectOne("excel.getDeptIdxByName", dept_name);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int countStudentsThisYear(String yearstr, String dept_idx) {
		try {
			return sqlSessionTemplate.selectOne("excel.yearcount", Map.of("yearstr",yearstr,"deptIdx",dept_idx));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("찾기 실패");
		}
	}

	public void insertUserVO(UserVO userVO) {
		try {
			// 1. user 테이블 먼저 insert
			sqlSessionTemplate.insert("excel.insertUserVO", userVO);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("사용자 등록 중 오류 발생");
		}
	}
	public void insertStudentVO(StudentVO studentVO) {
		try {
			// 1. user 테이블 먼저 insert
			sqlSessionTemplate.insert("excel.insertStudentVO", studentVO);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("사용자 등록 중 오류 발생");
		}
	}

}
