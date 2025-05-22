package com.university.nuri.service.adminservice;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.adminrepository.ADashboardDAO;

@Service
public class ADashboardServiceImpl implements ADashboardService {

    @Autowired
    private ADashboardDAO aDashboardDAO;

    @Override
    public int getStudentCount() {
        return aDashboardDAO.getStudentCount();
    }

    @Override
    public int getTeacherCount() {
        return aDashboardDAO.getTeacherCount();
    }

    @Override
    public int getDeptCount() {
        return aDashboardDAO.getDeptCount();
    }

    @Override
    public int getCurrentLectureCount() {
        return aDashboardDAO.getCurrentLectureCount();
    }

    @Override
    public int getPendingRequestCount() {
        return aDashboardDAO.getPendingRequestCount();
    }

    @Override
    public Map<String, Object> getEnrollApplyInfo() {
        return aDashboardDAO.getEnrollApplyInfo();
    }

	@Override
	public Map<String, Object> getEnrollApplyDashBoard() {
		return aDashboardDAO.getEnrollApplyDashBoard();
	}
}
