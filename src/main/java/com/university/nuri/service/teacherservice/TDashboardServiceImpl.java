package com.university.nuri.service.teacherservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.teacherrepository.TDashboardDAO;

@Service
public class TDashboardServiceImpl implements TDashboardService {
	@Autowired
	private TDashboardDAO dashboardDAO;

	@Override
	public Map<String, Object> getTInfo(String t_idx) {
		return dashboardDAO.getTInfo(t_idx);
	}

	@Override
	public int getobjectCount(String t_idx) {
		return dashboardDAO.getobjectCount(t_idx);
	}

	@Override
	public int getAllLecCount(String t_idx) {
		return dashboardDAO.getAllLecCount(t_idx);
	}

	@Override
	public List<Map<String, Object>> tthisLectList(String t_idx) {
		List<Map<String, Object>> list=  dashboardDAO.tthisLectList(t_idx);
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
	public List<Map<String, Object>> ttodayLectList(String t_idx, int todayDay) {
		List<Map<String, Object>> ttodayLectList= dashboardDAO.ttodayLectList(t_idx, todayDay);
	    for (Map<String, Object> item : ttodayLectList) {
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
	return ttodayLectList;
	}
}
