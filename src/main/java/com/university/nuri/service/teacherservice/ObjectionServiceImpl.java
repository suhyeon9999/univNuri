package com.university.nuri.service.teacherservice;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.teacherrepository.ObjectionDAO;

@Service
public class ObjectionServiceImpl implements ObjectionService{
	
	@Autowired
	private ObjectionDAO objectionDAO;
	
	public List<Map<String, Object>> getObjectionList(Map<String, Object> paramMap) {

		List<Map<String, Object>> objectionList = objectionDAO.getObjectionList(paramMap);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

		for (Map<String, Object> map : objectionList) {
		    Object rawDate = map.get("apply_date");

		    if (rawDate instanceof java.util.Date) {
		        String formatted = sdf.format((java.util.Date) rawDate);
		        map.put("formattedDate", formatted); 
		        
		    } 
		}

		return objectionList;
	}

	public Map<String, Object> getObjectionDetail(int objection_idx) {
		return objectionDAO.getObjectionDetail( objection_idx);
	}

	public Map<String, Object> getSearchScore(int enroll_idx) {
		return objectionDAO.getSearchScore(enroll_idx);
		
	}

	public int getScoreUpdateOk(Map<String, Object> paramMap) {
		return objectionDAO.getScoreUpdateOk(paramMap);
		
	}

	public int getUpdateObjectionStatus(Map<String, Object> paramMap) {
		return objectionDAO.getUpdateObjectionStatus(paramMap);
		
	}

}
