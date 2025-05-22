package com.university.nuri.service.studentservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.studentrepository.SDashboardDAO;

@Service
public class SDashboardServiceImpl implements SDashboardService {

	@Autowired
	private SDashboardDAO sDashboardDAO;

	@Override
	public Map<String, Object> getSInfo(String s_idx) {
		return sDashboardDAO.getSInfo(s_idx);
	}

	@Override
	public List<Map<String, Object>> sThisLectList(String s_idx) {
		List<Map<String, Object>> list = sDashboardDAO.sThisLectList(s_idx);
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
			String className = null;
			switch (classNum) {
			case 0: className = "미래관"; break;
			case 1: className = "현재관"; break;
			case 2: className = "과거관"; break;
			default: className = "미정"; break;
			}
			item.put("class_building", className);
		}

		System.out.println("📌 수강 강의 개수: " + list.size());
		for (Map<String, Object> map : list) {
			System.out.println("🔍 강의명: " + map.get("lect_name"));
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> sTodayLectList(Map<String, Object> dayParam) {
		List<Map<String, Object>> list = sDashboardDAO.sTodayLectList(dayParam);
		for (Map<String, Object> item : list) {
			int classNum = ((Number) item.get("class_building")).intValue();
			String className = null;
			switch (classNum) {
			case 0: className = "미래관"; break;
			case 1: className = "현재관"; break;
			case 2: className = "과거관"; break;
			default: className = "미정"; break;
			}
			item.put("class_building", className);
		}
		return list;
	}

	@Override
	public Map<String, Integer> getAssignmentStatus(String s_idx) {
		return sDashboardDAO.getAssignmentStatus(s_idx);
	}

	@Override
	public int getCompletedCredit(String s_idx) {
		return sDashboardDAO.getCompletedCredit(s_idx);
	}

	@Override
	public List<Map<String, Object>> allLectList(String s_idx) {
		List<Map<String, Object>> list = sDashboardDAO.allLectList(s_idx);
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
			String className = null;
			switch (classNum) {
			case 0: className = "미래관"; break;
			case 1: className = "현재관"; break;
			case 2: className = "과거관"; break;
			default: className = "미정"; break;
			}
			item.put("class_building", className);
		}

		System.out.println("📌 수강 강의 개수: " + list.size());
		for (Map<String, Object> map : list) {
			System.out.println("🔍 강의명: " + map.get("lect_name"));
		}
		return list;
	}
}

