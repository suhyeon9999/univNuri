package com.university.nuri.service.adminservice;

import java.util.List;
import java.util.Map;


public interface AEnrollService {
	
	
	public List<Map<String, Object>> getOpenLecture();

	public List<Map<String, Object>> searchDeptNameEnroll(String dept_name);
	
	public List<Map<String, Object>> searchBySemester(Map<String, Object> parmMap);
	
	
	public List<Map<String, Object>> searchLectures(Map<String, String> paramMap);
	
	
	
	// 예약이 있는지 없는지 조회해서 예약이 있다면 해당 리스트 가져오기 
	public List<Map<String, Object>> getEnrollApplyReservationIs();
	
	
	// 체크되었던 예약이 확정된 강의만 목록 불러오기 
	public List<Map<String, Object>> searchEnrollApplyLecturesReservationLook();
	
	
	
	public List<Map<String, Object>> searchEnrollApplyReservationDateTime(Integer enroll_apply_idx);
	
	
	// 금학기 열릴 수도 있는 강의 목록 불러오기 
	public List<Map<String, Object>> searchEnrollApplyLectures();
	
	
    public int insertEnrollApplyFirstReservation(Map<String, Object> paramMap);

    // 체크된 강의 lect_active = 0
 
    public int updateEnrollApplyLectActiveZero(String lect_idx);
	
    
    public int updateEnrollApplyActiveOne(String enroll_apply_idx); // active = 1


    public int updateEnrollApplyLectActiveTwo(String lect_idx); // active = 2 (미정)
   
    public int updateEnrollApplyFirstReservation(Map<String, Object> paramMap);

    
 

    public int updateEnrollApplyLectActiveOne(String lect_idx); // 선택 안됨 → active = 1
    
      
    

}
