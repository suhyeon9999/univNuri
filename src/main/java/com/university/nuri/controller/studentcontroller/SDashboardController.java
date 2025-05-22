package com.university.nuri.controller.studentcontroller;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.service.studentservice.SDashboardService;

@Controller
public class SDashboardController {

    @Autowired
    private SDashboardService sDashboardService;

    @GetMapping("/sDashboard")
    public ModelAndView sDashboard(HttpSession session) {
        Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
        String s_idx = String.valueOf(sInfo.get("s_idx"));
        ModelAndView mv = new ModelAndView();

        // 학생정보
        Map<String, Object> getSInfo = sDashboardService.getSInfo(s_idx);
        mv.addObject("sInfo", getSInfo);

        // 오늘 날짜
        LocalDate today = LocalDate.now();
        String formattedDate = today.getMonthValue() + "월 " + today.getDayOfMonth() + "일 "
                + today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        mv.addObject("todayDate", formattedDate);

        // 이번 학기 수강 강의
        List<Map<String, Object>> sThisLectList = sDashboardService.sThisLectList(s_idx);
        mv.addObject("lectureList", sThisLectList);
        mv.addObject("listCount", sThisLectList.size());

        // 졸업 한 사람만 - 수강 내역
        List<Map<String, Object>> allLectList = sDashboardService.allLectList(s_idx);
        mv.addObject("allLectList", allLectList);
        
        
        // 오늘 수업 일정
        int todayDay = today.getDayOfWeek().getValue() + 1; // 1(월) ~ 7(일)
        String todayDayStr = String.valueOf(todayDay); // "5" ← 문자열로 변환

        Map<String, Object> dayParam = Map.of(
            "s_idx", s_idx,
            "lect_day", todayDayStr
        );
        List<Map<String, Object>> sTodayLectList = sDashboardService.sTodayLectList(dayParam);
        mv.addObject("sTodayLectList", sTodayLectList);

        System.out.println("📆 오늘 요일: " + todayDayStr); // 예: 2 (월요일)

        System.out.println("🧪 오늘 수업 개수: " + sTodayLectList.size());
        for (Map<String, Object> lect : sTodayLectList) {
            System.out.println("🧾 수업명: " + lect.get("lect_name") + ", 요일: " + lect.get("lect_day"));
        }
        
        // 과제 제출/미제출 수
        Map<String, Integer> assignmentStatus = sDashboardService.getAssignmentStatus(s_idx);
        System.out.println("🧾 과제 제출: " + assignmentStatus.get("submitted"));
        System.out.println("🧾 과제 미제출: " + assignmentStatus.get("notSubmitted"));
        mv.addObject("submitted", assignmentStatus.get("submitted"));
        mv.addObject("notSubmitted", assignmentStatus.get("notSubmitted"));

        // 이수학점
        int completedCredit = sDashboardService.getCompletedCredit(s_idx);
        mv.addObject("completedCredit", completedCredit);

        mv.setViewName("student/sDashboard");
        return mv;
    }
}