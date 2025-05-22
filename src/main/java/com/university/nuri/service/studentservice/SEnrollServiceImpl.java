package com.university.nuri.service.studentservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.repository.studentrepository.SEnrollDAO;
import com.university.nuri.vo.studentvo.SubmitFileVO;
import com.university.nuri.vo.studentvo.SubmitVO;

@Service
public class SEnrollServiceImpl implements SEnrollService{
	@Autowired
	private SEnrollDAO sEnrollDAO;

	@Override
	public Map<String, List<Map<String, Object>>> getsEnrollInfo(String s_idx) {
		List<Map<String, Object>> current = sEnrollDAO.getsEnrollcurrent(s_idx);
		int totalAssignCount = 0;
		// 금학기 수강 내용
		for(Map<String, Object> map : current) {
			String lectIdx = String.valueOf(map.get("lect_idx"));
			String total = sEnrollDAO.getsEnrollcountTotal(s_idx, lectIdx);
			String present = sEnrollDAO.getsEnrollcountPresent(s_idx, lectIdx);
			String rate = Integer.parseInt(total) > 0 ? (Integer.parseInt(present) * 100 / Integer.parseInt(total)) + "%" : "0%";
			map.put("attendance_rate", rate);
	        // 과제수 누적
	        Object ac = map.get("assign_count");
	        if (ac != null) {
	        totalAssignCount += Integer.parseInt(ac.toString());
	        }
	        if (!current.isEmpty()) {
	        	current.get(0).put("assignTotal", totalAssignCount);
	        }
		}
		    Map<String, List<Map<String, Object>>> senrolldata = new HashMap<>();
		    senrolldata.put("current", current);;
		    
			return senrolldata;
	}
	// 수강 강의 조건 검색
	@Override
	public List<Map<String, Object>> searchEnrollPast(String s_idx, Map<String, String> params) {
		Map<String, String> searchParams = new HashMap<String, String>();
		searchParams.put("s_idx", s_idx);
	    if (params != null) {
	    	params.forEach((k, v) -> {
	            if (v != null && !v.trim().isEmpty()) {
	                searchParams.put(k, v.trim());  // ✨ trim 해서 넘기기
	            }
	        });
	        searchParams.putAll(params); // ✅ 여기서 year, semester, lect_name을 포함
	    }
		return sEnrollDAO.searchEnrollPast(searchParams);
	}
	// 연도 리스트
	@Override
	public List<Integer> getLectureYears() {
		return sEnrollDAO.getLectureYears();
	}
	// 학기 리스트
	@Override
	public List<Integer> getLectureSemesters() {
		return sEnrollDAO.getLectureSemesters();
	}
	// 강의 디테일에서 과제 및 등 각종 정보 불러오기
	@Override
	public List<Map<String, Object>> getTaskSubmitStatus(Map<String, String> params) {
		return sEnrollDAO.getTaskSubmitStatus(params);
	}
	@Override
	public String getLectureName(String lect_idx) {
		return sEnrollDAO.getLectureName(lect_idx);
	}
	
	// 특정 과목에 과제 제출하기전 교수가 적은 정보 불러오기
	@Override
	public List<Map<String, Object>> getassignInfo(String assign_idx, String enroll_idx) {
		return sEnrollDAO.getassignInfo(assign_idx, enroll_idx);
	}
	// 과제 제출 하기위한 enroll 인덱스 호출
	@Override
	public String getEnrollIdx(String assign_idx, String s_idx) {
		return sEnrollDAO.getEnrollIdx(assign_idx, s_idx);
	}
	// 실제 과목에 과제 제출 로직
	@Override
	public void insertSubmit(SubmitVO submitVO) {
		sEnrollDAO.insertSubmit(submitVO);
	}
	@Override
	public void insertSubmitFile(SubmitFileVO fileVO) {
		
		sEnrollDAO.insertSubmitFile(fileVO);
	}
	//제출 과제 수정
	@Override
	public void sEnrollDetailSubjectDetailUpdateOK(SubmitVO submitVO) {
		sEnrollDAO.sEnrollDetailSubjectDetailUpdateOK(submitVO);
		
	}
	@Override
	public void sEnrollDetailSubjectDetailUpdateFile(SubmitFileVO sfvo) {
		sEnrollDAO.sEnrollDetailSubjectDetailUpdateFile(sfvo);
	}
	//출결 조회를 위한 enroll index
	@Override
	public String getEnrollAttend(String lect_idx, String s_idx) {
		return sEnrollDAO.getEnrollAttend(lect_idx, s_idx);
	}
	// 출결 리스트
	@Override
	public List<Map<String, Object>> getAttendanceList(Map<String, String> params) {
		return sEnrollDAO.getAttendanceList(params);
	}
	// 차시 구하기위한 카운트
	@Override
	public int getAttendanceCount(String enroll_idx) {
		return sEnrollDAO.getAttendanceCount(enroll_idx);
	}
	//강의 정보 불러오기
	@Override
	public Map<String, Object> getLectInfo(String lect_idx) {
		return sEnrollDAO.getLectInfo(lect_idx);
	}
	//Submit Idx 구하기
	@Override
	public String getSubmitIdx(String assign_idx, String enroll_idx) {
		return sEnrollDAO.getSubmitIdx(assign_idx, enroll_idx);
	}
	// 기존파일 삭제
	@Override
	public void deleteSubmitFiles(String submit_idx) {
		sEnrollDAO.deleteSubmitFiles(submit_idx);
	}
	// 전체파일 가져오기
	@Override
	public List<Map<String, Object>> getAllSubmitFiles(String assign_idx, String enroll_idx) {
		return sEnrollDAO.getAllSubmitFiles(assign_idx, enroll_idx);
	}
	
}
