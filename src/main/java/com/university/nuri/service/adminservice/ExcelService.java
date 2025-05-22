package com.university.nuri.service.adminservice;

import java.util.List;

import com.university.nuri.vo.adminvo.StudentExcelDTO;
import com.university.nuri.vo.commonvo.UserVO;
import com.university.nuri.vo.studentvo.StudentVO;

public interface ExcelService {

	public int getDeptIdxByName(String dept_name);

	public int countStudentsThisYear(String yearstr, String dept_idx);

	public void studentManyInsert(UserVO userVO, StudentVO studentVO);

}
