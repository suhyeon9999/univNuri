package com.university.nuri.service.studentservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.studentrepository.SEnrollApplyDAO;
import com.university.nuri.vo.commonvo.EnrollApplyVO;

@Service
public class SEnrollApplyImpl implements SEnrollApplyService{
	@Autowired
	private SEnrollApplyDAO sEnrollApplyDAO;
	
	// start, end 시간 가져오기
	@Override
	public List<EnrollApplyVO> GetCurrentEnrollPeriod() {
		return sEnrollApplyDAO.GetCurrentEnrollPeriod();  // 가장 최근 1건만 가져오도록
	}
	// 수강신청 리스트 상단
	@Override
	public List<Map<String, Object>> SearchLectureList(Map<String, String> params) {
		return sEnrollApplyDAO.SearchLectureList(params);
	}
	// 수강 신청 리스트 하단
	@Override
	public List<Map<String, Object>> GetEnrolledLectureList(String s_idx) {
		return sEnrollApplyDAO.getEnrolledLectureList(s_idx);
	}
	// 수강 신청 OK
	@Override
	public int InsertEnroll(Map<String, Object> params) {
		return sEnrollApplyDAO.InsertEnroll(params);
	}
	// 수강 신청 cancel
	@Override
	public int CancelEnroll(Map<String, Object> params) {
		return sEnrollApplyDAO.CancelEnroll(params);
	}
	// 수강 신청 상태 조회
	@Override
	public Integer getEnrollStatus(Map<String, Object> params) {
		return sEnrollApplyDAO.getEnrollStatus(params);
	}
	// 수강 신청 쉬소한거 재신청
	@Override
	public int ReApplyEnroll(Map<String, Object> params) {
		return sEnrollApplyDAO.ReApplyEnroll(params);
	}
	// 수강신청 가능 학점
	public int getMaxCredit(String s_idx) {
	    return sEnrollApplyDAO.getMaxCredit(s_idx);
	}
}
