package com.university.nuri.service.studentservice;

import java.util.List;
import java.util.Map;

public interface SDashboardService {

    // 학생 정보 (이름, 학번, 학과 등)
    Map<String, Object> getSInfo(String s_idx);

    // 이번 학기 수강 강의 리스트
    List<Map<String, Object>> sThisLectList(String s_idx);

    // 오늘 수업 강의 리스트
    List<Map<String, Object>> sTodayLectList(Map<String, Object> dayParam);

	Map<String, Integer> getAssignmentStatus(String s_idx);

	int getCompletedCredit(String s_idx);

	List<Map<String, Object>> allLectList(String s_idx);
}