package com.university.nuri.service.studentservice;

import java.util.List;
import java.util.Map;

import com.university.nuri.vo.commonvo.EnrollApplyVO;

public interface SEnrollApplyService {
	public List<EnrollApplyVO> GetCurrentEnrollPeriod();
	public List<Map<String, Object>> SearchLectureList(Map<String, String> params);
	public int InsertEnroll(Map<String, Object> params);
	public List<Map<String, Object>>  GetEnrolledLectureList(String s_idx);
	public int CancelEnroll(Map<String, Object> params);
	public Integer getEnrollStatus(Map<String, Object> params);
	public int ReApplyEnroll(Map<String, Object> params);
	public int getMaxCredit(String s_idx);
}
