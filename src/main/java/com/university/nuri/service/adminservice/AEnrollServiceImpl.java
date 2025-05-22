package com.university.nuri.service.adminservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.adminrepository.AEnrollDAO;

@Service
public class AEnrollServiceImpl implements AEnrollService{
	
	@Autowired
	private AEnrollDAO aEnrollDAO;


    //수강신청 관리 리스트

	@Override
	public List<Map<String, Object>> getOpenLecture() {
		return aEnrollDAO.getOpenLecture();
	}
	@Override
	public List<Map<String, Object>> searchDeptNameEnroll(String dept_name) {
		return aEnrollDAO.searchDeptNameEnroll(dept_name);
	}
	@Override
	public List<Map<String, Object>> searchBySemester(Map<String, Object> parmMap) {
		return aEnrollDAO.searchBySemester(parmMap);
	}
	@Override
	public List<Map<String, Object>> searchLectures(Map<String, String> paramMap) {
	    return aEnrollDAO.searchLectures(paramMap);
	}
	@Override
	public List<Map<String, Object>> getEnrollApplyReservationIs() {
		return aEnrollDAO.getEnrollApplyReservationIs();
	}
	@Override
	public List<Map<String, Object>> searchEnrollApplyLecturesReservationLook() {
		
		
	    List<Map<String, Object>> list = aEnrollDAO.searchEnrollApplyLecturesReservationLook();

	    for (Map<String, Object> item : list) {
	        // 요일 변환
	        String dayNumStr = String.valueOf(item.get("lect_day"));
	        StringBuilder dayNames = new StringBuilder();
	        for (int i = 0; i < dayNumStr.length(); i++) {
	            switch (dayNumStr.charAt(i)) {
	                case '1': dayNames.append("일"); break;
	                case '2': dayNames.append("월"); break;
	                case '3': dayNames.append("화"); break;
	                case '4': dayNames.append("수"); break;
	                case '5': dayNames.append("목"); break;
	                case '6': dayNames.append("금"); break;
	                case '7': dayNames.append("토"); break;
	            }
	            if (i != dayNumStr.length() - 1) {
	                dayNames.append(",");
	            }
	        }
	        item.put("lect_day", dayNames.toString());

	        // 건물명 변환
	        int classNum = ((Number) item.get("class_building")).intValue();
					String className=null;
					switch (classNum) {
					case 0: className="미래관"; break;
					case 1: className="현재관"; break;
					case 2: className="과거관"; break;
					default: className = "미정"; break;
					}
			      item.put("class_building", className);
	    }

	    return list;
		
	}
	@Override
	public List<Map<String, Object>> searchEnrollApplyReservationDateTime(Integer enroll_apply_idx) {
		return aEnrollDAO.searchEnrollApplyReservationDateTime(enroll_apply_idx);
	}
	
	@Override
	public List<Map<String, Object>> searchEnrollApplyLectures() {
		
	    List<Map<String, Object>> list = aEnrollDAO.searchEnrollApplyLectures();

	    for (Map<String, Object> item : list) {
	        // 요일 변환
	        String dayNumStr = String.valueOf(item.get("lect_day"));
	        StringBuilder dayNames = new StringBuilder();
	        for (int i = 0; i < dayNumStr.length(); i++) {
	            switch (dayNumStr.charAt(i)) {
	                case '1': dayNames.append("일"); break;
	                case '2': dayNames.append("월"); break;
	                case '3': dayNames.append("화"); break;
	                case '4': dayNames.append("수"); break;
	                case '5': dayNames.append("목"); break;
	                case '6': dayNames.append("금"); break;
	                case '7': dayNames.append("토"); break;
	            }
	            if (i != dayNumStr.length() - 1) {
	                dayNames.append(",");
	            }
	        }
	        item.put("lect_day", dayNames.toString());

	        // 건물명 변환
	        int classNum = ((Number) item.get("class_building")).intValue();
					String className=null;
					switch (classNum) {
					case 0: className="미래관"; break;
					case 1: className="현재관"; break;
					case 2: className="과거관"; break;
					default: className = "미정"; break;
					}
			      item.put("class_building", className);
	    }

	    return list;
		
		
	}
	@Override
	public int insertEnrollApplyFirstReservation(Map<String, Object> paramMap) {
		return aEnrollDAO.insertEnrollApplyFirstReservation(paramMap);
	}
	@Override
	public int updateEnrollApplyLectActiveZero(String lect_idx) {
		return aEnrollDAO.updateEnrollApplyLectActiveZero(lect_idx);
	}
	@Override
	public int updateEnrollApplyActiveOne(String enroll_apply_idx) {
		return aEnrollDAO.updateEnrollApplyActiveOne(enroll_apply_idx);
	}
	@Override
	public int updateEnrollApplyLectActiveTwo(String lect_idx) {
		return aEnrollDAO.updateEnrollApplyLectActiveTwo(lect_idx);
	}
	@Override
	public int updateEnrollApplyFirstReservation(Map<String, Object> paramMap) {
		return aEnrollDAO.updateEnrollApplyFirstReservation(paramMap);
	}
	@Override
	public int updateEnrollApplyLectActiveOne(String lect_idx) {
		return aEnrollDAO.updateEnrollApplyLectActiveOne(lect_idx);
	}
	
	
	

}
