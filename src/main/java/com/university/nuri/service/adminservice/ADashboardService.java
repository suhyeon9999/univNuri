package com.university.nuri.service.adminservice;

import java.util.Map;


public interface ADashboardService {
    int getStudentCount();
    int getTeacherCount();
    int getDeptCount();
    int getCurrentLectureCount();
    int getPendingRequestCount();
    Map<String, Object> getEnrollApplyInfo();
    Map<String, Object> getEnrollApplyDashBoard();
}
