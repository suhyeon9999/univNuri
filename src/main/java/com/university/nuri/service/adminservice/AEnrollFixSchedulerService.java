package com.university.nuri.service.adminservice;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

public interface AEnrollFixSchedulerService {
	
    public void scheduleEnrollFix(String enroll_apply_idx, LocalDateTime endTime);
    
    public void cancelSchedule(String enrollApplyIdx);


}
