package com.university.nuri.service.teacherservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.university.nuri.vo.commonvo.AssignVO;

@Service
public class SchedulerServiceImpl implements SchedulerService{

    @Autowired
    private AssignService assignService;
    
    // 과제 마감 여부 확인 및 처리
    @Scheduled(cron="*/1 * * * * *")
    public void closeAssignment() {
        // 마감일 지난 과제 조회
        List<AssignVO> dueAssignments = assignService.getDueAssignments();
        if(dueAssignments != null) {
        // 과제 마감 처리
        for (AssignVO assign : dueAssignments) {
            int result = assignService.updateAssignEnd(assign.getAssign_idx());
        }
        }
    }
}
