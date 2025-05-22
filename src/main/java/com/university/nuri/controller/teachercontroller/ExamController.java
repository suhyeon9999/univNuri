package com.university.nuri.controller.teachercontroller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.service.teacherservice.ExamService;
import com.university.nuri.vo.commonvo.FileVO;
import com.university.nuri.vo.teachervo.TestMakeVO;

@Controller
public class ExamController {
	
	@Autowired
	private ExamService examService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 날짜 포맷 설정
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

        // 시간 포맷 설정 (HH:mm)
        binder.registerCustomEditor(Time.class, new CustomTimeEditor("HH:mm"));
    }
    
    //시험 출제 > 상세 페이지
	@GetMapping("exam-detail")
	public ModelAndView getExamDetail(@RequestParam("test_make_idx") String test_make_idx,
			HttpSession session, @RequestParam("lect_idx") String lect_idx) {
		Map<String, Object> tInfo = (Map<String, Object>) session.getAttribute("tInfo");
		String t_idx = String.valueOf(tInfo.get("t_idx")); 
		ModelAndView mv = new ModelAndView();
		Map<String, Object> getExamDetail= examService.getExamDetail(test_make_idx);
		mv.addObject("examDetail",getExamDetail);
		mv.addObject("test_make_idx", test_make_idx);
		mv.addObject("lect_idx", lect_idx);
		mv.setViewName("teacher/exam/examDetail");
		return mv;
	}
    
	@GetMapping("exam-down")
	public void examDown(HttpServletRequest request, HttpServletResponse response) {
			try {
				String f_name = request.getParameter("f_name");
				String path =request.getSession().getServletContext().getRealPath("/resources/upload/"+f_name);
				String r_path=URLEncoder.encode(f_name,"UTF-8");
				System.out.println("Received file name: " + f_name);
				System.out.println("File path: " + path);

				//브라우저 설정
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition", "attachment; filename = "+r_path);
				//실제 입출력
				File file = new File(new String(path.getBytes(), "utf-8"));
				FileInputStream in =new FileInputStream(file);
				OutputStream out = response.getOutputStream();
				FileCopyUtils.copy(in, out);
				response.getOutputStream().flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	
	//상세 페이지 > 수정 페이지 
	@GetMapping("/exam-update")
	public ModelAndView getExamUpdate(TestMakeVO testMakeVO,@RequestParam("test_make_idx") String test_make_idx,
			@RequestParam("lect_idx") String lect_idx) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("testMakeVO", examService.getExamDetail(test_make_idx));
		mv.addObject("lect_idx", lect_idx);
		mv.addObject("test_make_idx", test_make_idx);
		mv.setViewName("teacher/exam/examUpdate");
		return mv;
	}
	
	//수정하기
	@PostMapping("/exam-update-ok")
	public ModelAndView examUpdateOK(TestMakeVO testMakeVO, FileVO fileVO, HttpServletRequest request) throws IllegalStateException, IOException{
		ModelAndView mv = new ModelAndView();
		
	    String path = request.getSession().getServletContext().getRealPath("/resources/upload");
	    MultipartFile file = fileVO.getFile_name();
	    String old_f_name = fileVO.getF_old_name();


	    if (file != null && !file.isEmpty()) {
	        UUID uuid = UUID.randomUUID();
	        String originName = file.getOriginalFilename();
	        String f_name = uuid.toString() + "_" + originName;
	        fileVO.setF_name(f_name);

	        file.transferTo(new File(path, f_name));

	        // 파일 확장자 추출
	        String fileExtension = originName.substring(originName.lastIndexOf(".") + 1).toLowerCase();
	        String f_type = "0";  // 기본값 (예: 이미지)

	        switch (fileExtension) {
	            case "jpg": f_type = "0"; break;
	            case "txt": f_type = "1"; break;
	            case "ppt":
	            case "pptx": f_type = "2"; break;
	            case "pdf": f_type = "3"; break;
	            case "doc":
	            case "docx": f_type = "4"; break;
	            default: f_type = "9"; break; // 기타
	        }

	        fileVO.setF_size(Long.toString(file.getSize()));
	        fileVO.setF_type(f_type);

	        String testMakeIdx = testMakeVO.getTest_make_idx();
	        int result = examService.updateFile(fileVO, testMakeIdx);

	        fileVO.setF_name(old_f_name);
	    }

	    // 시험 자체 업데이트
	    int result = examService.examUpdateOK(testMakeVO);
	    mv.setViewName("redirect:/exam-detail?test_make_idx=" + testMakeVO.getTest_make_idx()+"&lect_idx="+testMakeVO.getLect_idx());
	    return mv;
	}
	
	//시험 출제 > 출제 페이지
	@GetMapping("/exam-make")
	public ModelAndView getExamMake(@RequestParam("lect_idx") String lect_idx) {
		ModelAndView mv = new ModelAndView();

        Map<String, Object> getTLecName= examService.getTLecName(lect_idx);
        mv.addObject("getTLecName",getTLecName);
        mv.addObject("lect_idx", lect_idx);
		mv.setViewName("teacher/exam/examMake");
		return mv;
	}

	//출제 + 파일
	@PostMapping("/exam-make-ok")
	public ModelAndView examMakeOK(@RequestParam("lect_idx") String lect_idx, TestMakeVO testMakeVO,
	        @RequestParam("file_name") MultipartFile file_name, HttpServletRequest request){

	    ModelAndView mv = new ModelAndView();
	    testMakeVO.setLect_idx(lect_idx);  
	    System.out.println("lect_idx"+lect_idx);
	    
	    System.out.println(testMakeVO.getTest_subject());
	    //Test_make 데이터 추가 
	    int result = examService.examMakeOK(testMakeVO); 
	    System.out.println("데이터 추가"+result);
	    String test_make_idx=testMakeVO.getTest_make_idx();
	    
	    // 파일 업로드
	    if (file_name != null && !file_name.isEmpty()) {
	        String path = request.getSession().getServletContext().getRealPath("/resources/upload/");
	        UUID uuid = UUID.randomUUID();
	        String originalName = file_name.getOriginalFilename();
	        String savedName = uuid.toString() + "_" + originalName;

	        String fileExtension = originalName.substring(originalName.lastIndexOf(".") + 1).toLowerCase();
	        String f_type = "0";  

	        if (fileExtension.equals("jpg")) {
	            f_type = "0"; 
	        } else if (fileExtension.equals("txt")) {
	            f_type = "1"; 
	        } else if (fileExtension.equals("ppt") || fileExtension.equals("pptx")) {
	            f_type = "2"; 
	        } else if (fileExtension.equals("pdf")) {
	            f_type = "3"; 
	        } else if (fileExtension.equals("doc") || fileExtension.equals("docx")) {
	            f_type = "4"; 
	        }

	        // 파일 정보 세팅
	        FileVO fileVO = new FileVO();
	        fileVO.setF_name(savedName);
	        fileVO.setF_old_name(originalName);
	        fileVO.setF_size(Long.toString(file_name.getSize()));
	        fileVO.setF_type(f_type);  // 파일 타입 설정
	        
	        examService.insertFile(fileVO); 
	        String f_idx = fileVO.getF_idx(); 

	        result= examService.updateFileToTestMake(test_make_idx, f_idx);
	        System.out.println("updateFileToTestMake"+result);

	        // 실제 파일 저장
	        try {
	            file_name.transferTo(new File(path, savedName)); 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    mv.setViewName("redirect:/exam-list?lect_idx="+lect_idx);
	    return mv;
	}
	
	
	//시험 삭제 
	@GetMapping("/exam-delete-ok")
	public ModelAndView examDeleteOK(@RequestParam("user_pw") String user_pw, 
	        HttpSession session, @RequestParam("test_make_idx") String test_make_idx, 
	        @RequestParam("lect_idx") String lect_idx) {
	    Map<String, Object> tInfo = (Map<String, Object>) session.getAttribute("tInfo");
	    String t_idx = String.valueOf(tInfo.get("t_idx"));
	    ModelAndView mv = new ModelAndView();
	    
	    String originPwd = examService.getPwd(t_idx);
	    
	    if (passwordEncoder.matches(user_pw, originPwd)) {
	        int result = examService.getExamDelete(test_make_idx);
	        
	        if (result > 0) {
	            mv.setViewName("redirect:/exam-list?lect_idx="+lect_idx);
	        } else {
	            mv.addObject("deleteError", "시험 삭제 실패");
	            mv.setViewName("redirect:/exam-detail?test_make_idx=" + test_make_idx);
	        }
	    } else {
	        mv.addObject("pwdchk", "fail"); // 비밀번호 실패
	        mv.setViewName("redirect:/exam-detail?test_make_idx=" + test_make_idx);
	    }
	    
	    return mv;
	}

}
