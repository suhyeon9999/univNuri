package com.university.nuri.controller.studentcontroller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.university.nuri.service.studentservice.SEnrollService;
import com.university.nuri.vo.studentvo.SubmitFileVO;
import com.university.nuri.vo.studentvo.SubmitVO;

@Controller
public class SEnrollController {
	@Autowired
	private SEnrollService sEnrollService;
	
	//수강 관리 첫 페이지
	@RequestMapping(value = "/sEnroll", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView getsEnrollInfo(@RequestParam Map<String, String> params, HttpSession session) {
		session.removeAttribute("lect_idx");
		ModelAndView mv = new ModelAndView();
		Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
	    String s_idx = String.valueOf(sInfo.get("s_idx"));
	    //금학기 수강중인 과목 및 지난 강의 조회 (같이 들어있음)
	    Map<String, List<Map<String, Object>>> senrolldata = sEnrollService.getsEnrollInfo(s_idx);
	    // 검색 결과
	    List<Map<String, Object>> senrollpast = sEnrollService.searchEnrollPast(s_idx, params);
	    System.out.println("💡 넘어온 params = " + params);
	    System.out.println("🔍 검색된 pastList.size = " + senrollpast.size());
	    System.out.println("넘어온 학기 값: [" + params.get("semester")  + "]");
	    // 연도/학기 필터용 select
	    mv.addObject("yearList", sEnrollService.getLectureYears());
	    mv.addObject("semesterList", sEnrollService.getLectureSemesters());
		mv.addObject("senrollcurrent", senrolldata.get("current"));
		mv.addObject("senrollpast", senrollpast);
		mv.setViewName("student/enroll/sEnroll");
		return mv;
	}
	
	// 강의 디테일
	@RequestMapping("/sEnrollDetail")
	public ModelAndView getsEnrollDetail(@RequestParam Map<String, String> params, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
	    String s_idx = String.valueOf(sInfo.get("s_idx"));
	    String lect_idx = params.get("lect_idx");
	    String lectName = sEnrollService.getLectureName(lect_idx);
		params.put("s_idx", s_idx);
		params.put("lect_idx", lect_idx);
	    session.setAttribute("lect_idx", lect_idx);
	    // 과제 + 제출 상태 리스트 가져오기
	    List<Map<String, Object>> taskList = sEnrollService.getTaskSubmitStatus(params);
	    mv.addObject("lectName", lectName);
		mv.addObject("taskList", taskList);           // 과제명, 시작일, 마감일, 제출상태
		mv.setViewName("student/enroll/sEnrollDetail");
		return mv;
	}
	
	// 각 과목 과제 제출폼
	@RequestMapping("/sEnrollDetailSubjectSubmit")
	public ModelAndView getsEnrollDetailSubjectSubmit(@RequestParam("assign_idx")String assign_idx, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
	    String s_idx = String.valueOf(sInfo.get("s_idx"));
		// 1. enroll_idx 조회
		String enroll_idx = sEnrollService.getEnrollIdx(assign_idx, s_idx);
		  List<Map<String, Object>> assignInfoList = sEnrollService.getassignInfo(assign_idx, enroll_idx);
		    List<Map<String, Object>> fileList = sEnrollService.getAllSubmitFiles(assign_idx, enroll_idx);
		    // ✅ 핵심 수정: assignInfoList에서 첫 번째 Map만 꺼내기
		    Map<String, Object> assignInfo = assignInfoList != null && !assignInfoList.isEmpty() 
		                                     ? assignInfoList.get(0) 
		                                     : new HashMap<>();
		mv.addObject("fileList", fileList);
	    mv.addObject("assigninfo", assignInfo);
		mv.setViewName("student/enroll/sEnrollDetailSubjectSubmit");
		return mv;
	}
	// 과제 제출 OK
	@PostMapping("/sEnrollDetailSubjectSubmitOK")
	public ModelAndView sEnrollDetailSubjectSubmitOK(
	        @RequestParam("assign_idx") String assign_idx,
	        @RequestParam("submit_content") String submit_content,
	        @RequestParam("submit_file_name") MultipartFile[] submit_file_name,
	        HttpSession session,
	        RedirectAttributes redirectAttributes,
	        HttpServletRequest request
	) {
		ModelAndView mv = new ModelAndView();

		try {
		    // ✅ 1. 세션 정보
		    Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
		    String s_idx = String.valueOf(sInfo.get("s_idx"));
		    String lect_idx = String.valueOf(session.getAttribute("lect_idx"));

		    // ✅ 2. 수강 등록번호 조회
		    String enroll_idx = sEnrollService.getEnrollIdx(assign_idx, s_idx);

		    // ✅ 3. 제출 정보 생성 및 저장
		    SubmitVO submitVO = new SubmitVO();
		    submitVO.setAssign_idx(assign_idx);
		    submitVO.setEnroll_idx(enroll_idx);
		    submitVO.setSubmit_title("과제파일 제출");
		    submitVO.setSubmit_content(submit_content);
		    sEnrollService.insertSubmit(submitVO);

		    String submit_idx = submitVO.getSubmit_idx();
		    if (submit_idx == null) {
		        throw new IllegalStateException("submit_idx가 생성되지 않았습니다.");
		    }

		    // ✅ 4. 파일 업로드 경로
		    String uploadPath = request.getSession().getServletContext().getRealPath("/resources/upload/");
		    int order = 1;

		    for (MultipartFile file : submit_file_name) {
		        if (file == null || file.isEmpty()) continue;

		        String originalName = file.getOriginalFilename();
		        if (originalName == null || originalName.trim().isEmpty()) continue;

		        String ext = getFileExtension(originalName);
		        int fileType = getFileTypeCode(ext);

		        String uuid = UUID.randomUUID().toString();
		        String savedName = uuid + "_" + originalName;

		        file.transferTo(new File(uploadPath, savedName));

		        SubmitFileVO fileVO = new SubmitFileVO();
		        fileVO.setSubmit_idx(submit_idx);
		        fileVO.setSubmit_f_name(savedName);
		        fileVO.setSubmit_f_old_name(originalName);
		        fileVO.setSubmit_f_size(String.valueOf(file.getSize()));
		        fileVO.setSubmit_f_type(String.valueOf(fileType));
		        fileVO.setSubmit_f_order(String.valueOf(order++));

		        sEnrollService.insertSubmitFile(fileVO);
		    }

	        // 5. 완료 후 리다이렉트
	        mv.setViewName("redirect:/sEnrollDetailSubjectDetail?lect_idx=" + lect_idx + "&assign_idx=" + assign_idx);
	        return mv;

	    } catch (Exception e) {
	        e.printStackTrace();
	        mv.setViewName("error/submitError");
	        return mv;
	    }
	}
	// 제출한 과제 디테일
	@RequestMapping("/sEnrollDetailSubjectDetail")
	public ModelAndView sEnrollDetailSubjectDetail(@RequestParam("assign_idx")String assign_idx, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
	    String s_idx = String.valueOf(sInfo.get("s_idx"));
	    String enroll_idx = sEnrollService.getEnrollIdx(assign_idx, s_idx);

	    
	    List<Map<String, Object>> assignInfoList = sEnrollService.getassignInfo(assign_idx, enroll_idx);
	    List<Map<String, Object>> fileList = sEnrollService.getAllSubmitFiles(assign_idx, enroll_idx);
	    // ✅ 핵심 수정: assignInfoList에서 첫 번째 Map만 꺼내기
	    Map<String, Object> assignInfo = assignInfoList != null && !assignInfoList.isEmpty() 
	                                     ? assignInfoList.get(0) 
	                                     : new HashMap<>();
	    
	    mv.addObject("fileList", fileList);
	    mv.addObject("assigninfo", assignInfo);
		mv.setViewName("student/enroll/sEnrollDetailSubjectDetail");
		return mv;

	}
	//파일 다운로드
	@GetMapping("/download")
	public void downloadFile(@RequestParam("fileName") String fileName,
	                         @RequestParam("originalName") String originalName,
	                         HttpServletRequest request,
	                         HttpServletResponse response,HttpSession session) throws IOException {

	    // 1. 파일 저장 경로 지정
	    String uploadPath = request.getSession().getServletContext().getRealPath("/resources/upload/");
	    File file = new File(uploadPath, fileName);

	    // 2. 파일 존재 여부 확인
	    if (!file.exists()) {
	        response.sendError(HttpServletResponse.SC_NOT_FOUND, "파일이 존재하지 않습니다.");
	        return;
	    }

	    // 3. 파일 다운로드 응답 헤더 설정
	    response.setContentType("application/octet-stream");
	    response.setContentLength((int) file.length());

	    // 한글 파일명 다운로드 대응
	    String encodedName = URLEncoder.encode(originalName, "UTF-8").replaceAll("\\+", "%20");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedName + "\";");
	    response.setHeader("Content-Transfer-Encoding", "binary");

	    // 4. 파일 스트림 전송
	    try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
	         BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {

	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = bis.read(buffer)) > 0) {
	            bos.write(buffer, 0, length);
	        }
	        bos.flush();
	    }
	}
	// 제출한 과제 디테일 수정하기 누른후 페이지
	@RequestMapping("/sEnrollDetailSubjectUpdate")
	public ModelAndView sEnrollDetailSubjectUpdate(@RequestParam("assign_idx")String assign_idx, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
		String s_idx = String.valueOf(sInfo.get("s_idx"));
		String enroll_idx = sEnrollService.getEnrollIdx(assign_idx, s_idx);
		  List<Map<String, Object>> assignInfoList = sEnrollService.getassignInfo(assign_idx, enroll_idx);
		    List<Map<String, Object>> fileList = sEnrollService.getAllSubmitFiles(assign_idx, enroll_idx);
		    // ✅ 핵심 수정: assignInfoList에서 첫 번째 Map만 꺼내기
		    Map<String, Object> assignInfo = assignInfoList != null && !assignInfoList.isEmpty() 
		                                     ? assignInfoList.get(0) 
		                                     : new HashMap<>();
		mv.addObject("fileList", fileList);
		mv.addObject("assigninfo", assignInfo);
		mv.setViewName("student/enroll/sEnrollDetailSubjectUpdate");
		return mv;
		
	}
	// 제출한 과제 업데이트 OK
	@RequestMapping("/sEnrollDetailSubjectDetailUpdateOK")
	public ModelAndView sEnrollDetailSubjectDetailUpdateOK(
					@RequestParam("assign_idx") String assign_idx,
					@ModelAttribute("submit_content") String submit_content,
					@ModelAttribute("submit_file_name") MultipartFile[] submit_file_name,
					HttpSession session,
					RedirectAttributes redirectAttributes, HttpServletRequest request
		){
		ModelAndView mv = new ModelAndView();
		try {
		    Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
		    String s_idx = String.valueOf(sInfo.get("s_idx"));
		    String lect_idx = (String) session.getAttribute("lect_idx");

		    // 1. enroll_idx 조회
		    String enroll_idx = sEnrollService.getEnrollIdx(assign_idx, s_idx);

		    // 2. 기존 제출 여부 확인
		    String submit_idx = sEnrollService.getSubmitIdx(assign_idx, enroll_idx);

		    // 3. 제출 VO 설정
		    SubmitVO submitVO = new SubmitVO();
		    submitVO.setAssign_idx(assign_idx);
		    submitVO.setEnroll_idx(enroll_idx);
		    submitVO.setSubmit_content(submit_content);
		    submitVO.setSubmit_title("과제파일 수정");

		    if (submit_idx != null) {
		        // ✅ 기존 제출이 있으면 update + 파일 삭제
		        submitVO.setSubmit_idx(submit_idx);
		        sEnrollService.sEnrollDetailSubjectDetailUpdateOK(submitVO);
		        sEnrollService.deleteSubmitFiles(submit_idx);
		    } else {
		        // ✅ 제출 없으면 insert
		        sEnrollService.insertSubmit(submitVO);
		        submit_idx = submitVO.getSubmit_idx(); // auto-generated key
		    }

		    // 4. 파일 저장
		    int order = 1;
		    String path = request.getSession().getServletContext().getRealPath("/resources/upload/");

		    for (MultipartFile file : submit_file_name) {
		        if (file == null || file.isEmpty()) continue;

		        String originalName = file.getOriginalFilename();
		        if (originalName == null || originalName.trim().isEmpty()) continue;

		        String uuid = UUID.randomUUID().toString();
		        String savedName = uuid + "_" + originalName;
		        String fileSize = String.valueOf(file.getSize());

		        // 저장
		        file.transferTo(new File(path, savedName));

		        // 파일 VO
		        SubmitFileVO fileVO = new SubmitFileVO();
		        fileVO.setSubmit_idx(submit_idx);
		        fileVO.setSubmit_f_name(savedName);
		        fileVO.setSubmit_f_old_name(originalName);
		        fileVO.setSubmit_f_size(fileSize);
		        fileVO.setSubmit_f_type(String.valueOf(getFileTypeCode(getFileExtension(originalName))));
		        fileVO.setSubmit_f_order(String.valueOf(order++));

		        sEnrollService.insertSubmitFile(fileVO);
		    }

		    // 리다이렉트
		    mv.setViewName("redirect:/sEnrollDetailSubjectDetail?lect_idx=" + lect_idx + "&assign_idx=" + assign_idx);
		    return mv;

		} catch (Exception e) {
		    e.printStackTrace();
		    mv.setViewName("error/submitError");
		    return mv;
		}
		
	}
	@GetMapping("/sAttendance")
	public ModelAndView getsAttendance(HttpSession session, @RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		// 세션에서 학생정보 강의 정보 가져오기
		Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
		String s_idx = String.valueOf(sInfo.get("s_idx"));
		String lect_idx = String.valueOf(session.getAttribute("lect_idx"));
		// 출결계산을 하기위해서 enroll Idx 값 구하기 
		String enroll_idx = sEnrollService.getEnrollAttend(lect_idx, s_idx);
		// Map 으로 파라미터 묶기
		Map<String, String> list = new HashedMap<>();
		params.put("s_idx", s_idx);
		params.put("lect_idx", lect_idx);
		params.put("enroll_idx", enroll_idx);
		// 전체 출결 개수 조회 (필터 X)
		int totalCount = sEnrollService.getAttendanceCount(enroll_idx);
		// 출결 리스트 가져오기
		List<Map<String, Object>> attendanceList = sEnrollService.getAttendanceList(params);
		Collections.reverse(attendanceList);
		// 전체 출결 개수 기준으로 차시번호 계산
		for (int i = 0; i < attendanceList.size(); i++) {
		    Map<String, Object> row = attendanceList.get(i);
		    row.put("class_num", totalCount - i + " 회차");  // 최신 출결이 가장 높은 차시
		}
		int countPresent = 0; // 출석
		int countAbsent = 0; // 결석
		int countLate = 0; // 지각
		int countLeave = 0; // 조퇴
		int countLateAndLeave = 0; // 지각/조퇴

		for (Map<String, Object> row : attendanceList) {
		    int status = Integer.parseInt(String.valueOf(row.get("attend_status")));
		    if (status == 1) countPresent++;
		    else if (status == 5) countAbsent++;
		    else if (status == 2) countLate++;
		    else if (status == 3) countLeave++;
		    else if (status == 4) countLateAndLeave++;
		}
		String lectName = sEnrollService.getLectureName(lect_idx);
		mv.addObject("lectName", lectName);

		mv.addObject("countLateAndLeave", countLateAndLeave);
		mv.addObject("countLeave", countLeave);
		mv.addObject("countPresent", countPresent);
		mv.addObject("countAbsent", countAbsent);
		mv.addObject("countLate", countLate);
		mv.addObject("enroll_idx", enroll_idx);
		mv.addObject("attend_date", params.get("attend_date"));     // 검색 필터 유지용
		mv.addObject("attend_status", params.get("attend_status")); // 검색 필터 유지용
		mv.addObject("attendanceList", attendanceList);
		mv.setViewName("student/enroll/sAttendance");
		return mv;
	}
	@GetMapping("/LectureInfo")
	public ModelAndView getLectInfo(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		String lect_idx = (String) session.getAttribute("lect_idx");
		Map<String, Object> lectInfo = sEnrollService.getLectInfo(lect_idx);
		mv.addObject("lectInfo", lectInfo);
		mv.setViewName("student/enroll/sLectureInfo");
		return mv;
	}
	// 파일 확장자 추출 메서드
	private String getFileExtension(String fileName) {
	    int dotIndex = fileName.lastIndexOf('.');
	    if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
	        throw new IllegalArgumentException("파일 확장자가 없습니다: " + fileName);
	    }
	    return fileName.substring(dotIndex + 1).toLowerCase();
	}

	// 확장자에 따른 타입 코드
	private int getFileTypeCode(String ext) {
	    switch (ext) {
	        case "jpg":
	        case "jpeg":
	        case "png":
	            return 0;
	        case "txt":
	            return 1;
	        case "ppt":
	        case "pptx":
	            return 2;
	        case "pdf":
	            return 3;
	        case "doc":
	        case "docx":
	            return 4;
	        default:
	            throw new IllegalArgumentException("지원하지 않는 파일 형식입니다: " + ext);
	    }
	}
}