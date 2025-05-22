package com.university.nuri.service.teacherservice;

import java.util.List;
import java.util.Map;

import com.university.nuri.vo.commonvo.AssignFileVO;
import com.university.nuri.vo.commonvo.AssignVO;
import com.university.nuri.vo.commonvo.FileVO;
import com.university.nuri.vo.studentvo.SubmitFileVO;
import com.university.nuri.vo.studentvo.SubmitVO;

public interface AssignService {

	List<Map<String, Object>> getSubmitList(String assign_idx);

	int countSubmit(String assign_idx);

	int countCourseS(String assign_idx);

	AssignVO assignDetail(String assign_idx);

	Map<String, Object> getTLecName(String lect_idx);

	SubmitVO getSubmitDetail(String submit_idx);

	int assignListMakeInsert(AssignVO assignVO);

	int assignUpdateOK(AssignVO assignVO);

	String getPwd(String t_idx);

	int getAssignDelete(String assign_idx);

	Map<String, Object> getStuName(String submit_idx);

	String getFileType(String originName);

	int insertAssignFile(AssignFileVO fileVO);

	List<AssignFileVO> getAssignFiles(String assign_idx);

	List<AssignVO> getDueAssignments();

	int updateAssignEnd(String assign_idx);

	List<SubmitFileVO> getSubmitFiles(String submit_idx);

	int deleteAssignFilesByAssignIdx(String assign_idx);

	String isEnd(String assign_idx);

}
