package com.university.nuri.service.teacherservice;

import java.util.List;
import java.util.Map;

public interface TDashboardService {

	Map<String, Object> getTInfo(String t_idx);

	int getobjectCount(String t_idx);

	int getAllLecCount(String t_idx);

	List<Map<String, Object>> tthisLectList(String t_idx);

	List<Map<String, Object>> ttodayLectList(String t_idx, int todayDay);

}
