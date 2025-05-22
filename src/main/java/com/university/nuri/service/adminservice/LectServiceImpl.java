package com.university.nuri.service.adminservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.adminrepository.LectDAO;
import com.university.nuri.vo.adminvo.LectureVO;

@Service
public class LectServiceImpl implements LectService{
	@Autowired
	private LectDAO lectDAO;
	// 강의 리스트
	@Override
	public List<Map<String, Object>> getAllLectList() {
		return lectDAO.getAllLectList();
	}
	// 강의 상세보기
	@Override
	public Map<String, Object> detailLect(String lect_idx) {
		try {
			Map<String, Object> detailLect = lectDAO.detailLect(lect_idx);
			String[] days = {"", "일", "월", "화", "수", "목", "금", "토"};
			String dayStr = String.valueOf(detailLect.get("lect_day"));
			List<String> result = new ArrayList<>();
			for (char ch : dayStr.toCharArray()) {
			    int idx = Character.getNumericValue(ch);
			    if (idx >= 1 && idx <= 7) result.add(days[idx]);
			}
			detailLect.put("lect_day", String.join(",", result));
			
			int classNum = ((Number) detailLect.get("class_building")).intValue();

			String className=null;
			switch (classNum) {
			case 0: className="미래관"; break;
			case 1: className="현재관"; break;
			case 2: className="과거관"; break;
			default: className = "미정"; break;
			}
			detailLect.put("class_building", className);		
			return detailLect;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
			
	}
	// 강의 상세보기 -과목,과목군 불러오기
	@Override
	public List<Map<String, Object>> lestSubSetList(String lect_idx) {
		return lectDAO.lestSubSetList(lect_idx);
	}
	
	// 정보 미입력 강의보기
	@Override
	public List<Map<String, Object>> searchNullLect() {
		return lectDAO.searchNullLect();
	}
	// 강의검색
		// 강의코드로 검색
	@Override
	public List<Map<String, Object>> searchLectId(String lect_id) {
		return lectDAO.searchLectId(lect_id);
	}
	// 강의명으로 검색
	@Override
	public List<Map<String, Object>> searchLectName(String lect_name) {
		return lectDAO.searchLectName(lect_name);
	}
	// 개설학과로 검색
	@Override
	public List<Map<String, Object>> searchDeptName(String dept_name) {
		return lectDAO.searchDeptName(dept_name);
	}
	// 강의요일로 검색
	@Override
	public List<Map<String, Object>> searchLectDay(String lect_day) {
		try {
			 List<Map<String, Object>> lectureList = lectDAO.searchLectDay(lect_day);

			    for ( Map<String, Object> lectureDay : lectureList) {
			    	String dayNumStr = String.valueOf(lectureDay.get("lect_day")); // 예: "25"
					 StringBuilder dayNames = new StringBuilder();

					 for (int i = 0; i < dayNumStr.length(); i++) {
					     char ch = dayNumStr.charAt(i);

					     switch (ch) {
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

					 lectureDay.put("lect_day", dayNames.toString());
			    }
			    System.out.println(lectureList);
			    return lectureList;
			 
		} catch (Exception e) {
			 e.printStackTrace();
				return null;
		}
		
	}
	// 담당교수로 검색
	@Override
	public List<Map<String, Object>> searchName(String name) {
		return lectDAO.searchName(name);
	}
		
		
		
		
}
