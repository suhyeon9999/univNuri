package com.university.nuri.service.studentservice;

import java.util.List;
import java.util.Map;

import com.university.nuri.vo.studentvo.SubmitFileVO;
import com.university.nuri.vo.studentvo.SubmitVO;

public interface SEnrollService {
	public Map<String, List<Map<String, Object>>> getsEnrollInfo(String s_idx);
	public List<Map<String, Object>> searchEnrollPast(String s_idx , Map<String, String> params);
	public List<Integer> getLectureYears();
	public List<Integer> getLectureSemesters();
	public List<Map<String, Object>> getTaskSubmitStatus(Map<String, String> params);
	public String getLectureName(String lect_idx);
	public List<Map<String, Object>> getassignInfo(String assign_idx, String enroll_idx);
	public String getEnrollIdx(String assign_idx, String s_idx);
	public void insertSubmit(SubmitVO submitVO);
	public void insertSubmitFile(SubmitFileVO fileVO);
	public void sEnrollDetailSubjectDetailUpdateOK(SubmitVO submitVO);      // submit 테이블용
	public void sEnrollDetailSubjectDetailUpdateFile(SubmitFileVO sfvo);     // submit_file 테이블용
	public String getEnrollAttend(String lect_idx, String s_idx);
	public List<Map<String, Object>> getAttendanceList(Map<String, String> params);
	public int getAttendanceCount(String enroll_idx);
	public Map<String, Object> getLectInfo(String lect_idx);
	public String getSubmitIdx(String assign_idx, String enroll_idx);
	public void deleteSubmitFiles(String submit_idx);
	public List<Map<String, Object>> getAllSubmitFiles(String assign_idx, String enroll_idx);
}