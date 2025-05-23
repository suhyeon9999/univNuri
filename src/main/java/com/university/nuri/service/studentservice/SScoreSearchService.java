package com.university.nuri.service.studentservice;

import java.util.List;
import java.util.Map;

public interface SScoreSearchService {
	// 검색 조건 
    public List<Map<String, Object>> getSScoreSearch(Map<String, String>params);
	// 전체 평점, 금학기 평점 계산
    public Map<String, Object> getGPAInfo(String s_idx);
    // 이의 제기 리스트
    public List<Map<String, Object>> getObjectionList(String s_idx);
    // 이의 제기 수정
    public int sScoreSearchObjectionDetailUpdateOK(Map<String, Object> params);
    // 이의 제기 신청
    public void sScoreSearchObjectionDetailInsertOK(Map<String, Object> params);
    // 이의 제기 디테일
    public Map<String, Object> getObjectionByIdx(String objection_idx);
    // 이의 제기 삭제
    public void sScoreSearchObjectionDetailDeleteOK(Map<String, String> paramMap);
    // 이의제기 신청 페이지
    Map<String, Object> getObjectionInfoForInsert(String s_idx, String lect_idx);
}
