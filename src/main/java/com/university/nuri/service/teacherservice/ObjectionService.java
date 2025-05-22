package com.university.nuri.service.teacherservice;

import java.util.List;
import java.util.Map;

public interface ObjectionService {
	
	public List<Map<String, Object>> getObjectionList(Map<String, Object> paramMap);

	public Map<String, Object> getObjectionDetail(int objection_idx);

	public Map<String, Object> getSearchScore(int enroll_idx);
	public int getScoreUpdateOk(Map<String, Object> paramMap);

	public int getUpdateObjectionStatus(Map<String, Object> paramMap);
}
