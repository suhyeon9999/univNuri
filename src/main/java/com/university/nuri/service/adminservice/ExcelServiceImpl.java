package com.university.nuri.service.adminservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.nuri.repository.adminrepository.ExcelDAO;
import com.university.nuri.vo.adminvo.StudentExcelDTO;
import com.university.nuri.vo.commonvo.UserVO;
import com.university.nuri.vo.studentvo.StudentVO;

@Service
public class ExcelServiceImpl implements ExcelService{

	@Autowired
	private ExcelDAO excelDAO;


	@Override
	public int getDeptIdxByName(String dept_name) {
		return excelDAO.getDeptIdxByName(dept_name);
	}

	@Override
	public int countStudentsThisYear(String yearstr, String dept_idx) {
		return excelDAO.countStudentsThisYear(yearstr, dept_idx);
	}
	
	@Transactional
	@Override
	public void studentManyInsert(UserVO userVO, StudentVO studentVO) {
		excelDAO.insertUserVO(userVO);
		studentVO.setUser_idx(userVO.getUser_idx()); // user_idx는 insert 후에 채워짐
		excelDAO.insertStudentVO(studentVO);
	}


}
