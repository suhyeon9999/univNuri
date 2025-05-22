package com.university.nuri.service.adminservice;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class AEnrollFixSchedulerServiceImpl implements AEnrollFixSchedulerService{
	
	
	
    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private AEnrollFixService aEnrollFixService;

  /*  public void scheduleEnrollFix(String enroll_apply_idx, LocalDateTime endTime) {
    	System.out.println("endTime" + endTime);
       // LocalDateTime endTime = LocalDateTime.parse(end_time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    	//LocalDateTime endTime = LocalDateTime.now().plusSeconds(10);
    	
    	endTime = LocalDateTime.now().plusSeconds(10);
        Date triggerTime = Date.from(endTime.plusSeconds(1).atZone(ZoneId.systemDefault()).toInstant());

        taskScheduler.schedule(() -> {
            try {
                System.out.println("[예약작업] 수강신청 마감: " + enroll_apply_idx);
                aEnrollFixService.runEnrollFix(enroll_apply_idx);
            } catch (Exception e) {
                e.printStackTrace(); // 예외 확인
            }
        }, triggerTime);
    }*/
    
    
    private final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public AEnrollFixSchedulerServiceImpl() {
        scheduler.setPoolSize(5);
        scheduler.initialize();
    }

    public void scheduleEnrollFix(String enrollApplyIdx, LocalDateTime endTime) {
    	
    	//endTime = LocalDateTime.now().plusSeconds(10);
        // 취소 후 새 예약 (중복 방지)
        cancelSchedule(enrollApplyIdx);

        Runnable task = () -> {
            System.out.println("자동 확정 실행: " + enrollApplyIdx);
            try {
                aEnrollFixService.runEnrollFix(enrollApplyIdx); // ✅ 실제 확정 처리 로직 호출
                System.out.println("자동 확정 완료: " + enrollApplyIdx);
            } catch (Exception e) {
                System.err.println("자동 확정 중 오류 발생: " + e.getMessage());
                e.printStackTrace();
            }
        };

        ScheduledFuture<?> future = scheduler.schedule(task, Timestamp.valueOf(endTime));
        scheduledTasks.put(enrollApplyIdx, future);
    }

    public void cancelSchedule(String enrollApplyIdx) {
        ScheduledFuture<?> future = scheduledTasks.remove(enrollApplyIdx);
        if (future != null) {
            future.cancel(false); // 예약된 작업 취소
            System.out.println("예약 취소 완료: " + enrollApplyIdx);
        }
    }

}
