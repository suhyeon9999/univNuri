package com.university.nuri.service.studentservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.studentrepository.SScoreSearchDAO;

@Service
public class SScoreSearchImpl implements SScoreSearchService{
	@Autowired
	private SScoreSearchDAO sScoreSearchDAO;
    //검색 조건 
	@Override
	public List<Map<String, Object>> getSScoreSearch(Map<String, String> params) {
		return sScoreSearchDAO.getSScoreSearch(params);
	}
	// 전체 평점, 금학기 평점 계산
	@Override
	public Map<String, Object> getGPAInfo(String s_idx) {
		return sScoreSearchDAO.getGPAInfo(s_idx);
	}
	// 이의 제기 리스트
	@Override
	public List<Map<String, Object>> getObjectionList(String s_idx) {
		return sScoreSearchDAO.getObjectionList(s_idx);
	}
	// 이의 제기 수정
	@Override
	public int sScoreSearchObjectionDetailUpdateOK(Map<String, Object> params) {
		return sScoreSearchDAO.sScoreSearchObjectionDetailUpdateOK(params);
	}
	// 이의 제기 신청
	@Override
	public void sScoreSearchObjectionDetailInsertOK(Map<String, Object> params) {
		sScoreSearchDAO.sScoreSearchObjectionDetailInsertOK(params);
	}
	// 이의 제기 디테일
	@Override
	public Map<String, Object> getObjectionByIdx(String objection_idx) {
		return sScoreSearchDAO.getObjectionByIdx(objection_idx);
	}
	// 이의 제기 삭제
	@Override
	public void sScoreSearchObjectionDetailDeleteOK(Map<String, String> paramMap) {
		sScoreSearchDAO.sScoreSearchObjectionDetailDeleteOK(paramMap);
	}
	// 전체 이의 제기 검색
	@Override
	public List<Map<String, Object>> getAllObjectionList(String s_idx) {
		 return sScoreSearchDAO.getAllObjectionList(s_idx);
	}
}
