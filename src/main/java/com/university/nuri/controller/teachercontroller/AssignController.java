package com.university.nuri.controller.teachercontroller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.service.teacherservice.AssignService;
import com.university.nuri.service.teacherservice.SchedulerService;
import com.university.nuri.vo.commonvo.AssignFileVO;
import com.university.nuri.vo.commonvo.AssignVO;
import com.university.nuri.vo.commonvo.FileVO;
import com.university.nuri.vo.studentvo.SubmitFileVO;
import com.university.nuri.vo.studentvo.SubmitVO;

@Controller
public class AssignController {
	
	@Autowired
	private AssignService assignService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Autowired
    private SchedulerService schedulerService;
	
	//출제 페이지 이동
	@GetMapping("/assign-make")
	public ModelAndView getAssignMake(@RequestParam("lect_idx") String lect_idx) {
		ModelAndView mv =new ModelAndView();
        Map<String, Object> getTLecName= assignService.getTLecName(lect_idx);
        mv.addObject("getTLecName",getTLecName);
        mv.addObject("lect_idx", lect_idx);
        mv.setViewName("teacher/assign/assignMake");
		return mv;
	}
	
	//리스트 > 상세 페이지 이동
    @GetMapping("/assign-detail")
    public ModelAndView getAssignDetail(@RequestParam("assign_idx") String assign_idx,
    		@RequestParam("lect_idx") String lect_idx) {
        ModelAndView mv = new ModelAndView();
        //과제 상세(제목, 내용, 시작시간마감기한)
        AssignVO assignDetail = assignService.assignDetail(assign_idx);
        mv.addObject("assignDetail", assignDetail);
        
        //교수명, 강의명 
        Map<String, Object> getTLecName= assignService.getTLecName(lect_idx);
        mv.addObject("getTLecName",getTLecName);
        
        //과제 제출 명수 
        int countSubmit = assignService.countSubmit(assign_idx);
        mv.addObject("countSubmit", countSubmit);
        
        //과제 마감 여부 
        String isEnd= assignService.isEnd(assign_idx);
        mv.addObject("isEnd", isEnd);
        System.out.println("isEnd:"+isEnd);
        
        //과제 미제출 명수
        int countCourseS = assignService.countCourseS(assign_idx);
        int countNotSubmit =countCourseS - countSubmit;
        mv.addObject("countNotSubmit", countNotSubmit);
        
        //제출 목록
        List<Map<String, Object>> lectureInfo = assignService.getSubmitList(assign_idx);
        
        //파일 목록 출력 
        List<AssignFileVO> getAssignFiles = assignService.getAssignFiles(assign_idx);
        mv.addObject("assignFiles", getAssignFiles);

        mv.addObject("submitList", lectureInfo); 
        mv.addObject("assign_idx",assign_idx);  

        mv.addObject("lect_idx", lect_idx);
        mv.setViewName("teacher/assign/assignDetail");
        return mv;
    }
    
    //과제 상세 > 제출(학생) 상세
    @GetMapping("/submit-detail")
    public ModelAndView getSubmitDetail(@RequestParam("submit_idx") String submit_idx, 
    		@RequestParam("assign_idx") String assign_idx, 
    		@RequestParam("lect_idx") String lect_idx) {
    	ModelAndView mv = new ModelAndView();
    	
    	//과제 제목, 과제 내용
    	AssignVO assignDetail = assignService.assignDetail(assign_idx);
    	mv.addObject("assignDetail", assignDetail);
    	
    	//학생명 
    	Map<String, Object> getStuName=assignService.getStuName(submit_idx);
    	mv.addObject("getName", getStuName);
    	
    	//강의명 
        Map<String, Object> getTLecName= assignService.getTLecName(lect_idx);
        mv.addObject("getTLecName",getTLecName);
    	
        //제출일/제출내용
        SubmitVO getSubmitDetail = assignService.getSubmitDetail(submit_idx);
        mv.addObject("getSubmitDetail", getSubmitDetail);
        
        //파일 목록 출력 
        List<SubmitFileVO> getSubmitFiles = assignService.getSubmitFiles(submit_idx);
        mv.addObject("submitFiles", getSubmitFiles);

        mv.addObject("assign_idx",assign_idx); 
        mv.addObject("lect_idx", lect_idx);
        
    	mv.setViewName("teacher/assign/submitDetail");
    	return mv;
    }
    
    //과제 출제
    @PostMapping("/assign-list-make-ok")
    public ModelAndView assignListMakeOk(
        @ModelAttribute AssignVO assignVO, 
        @RequestParam(value = "files", required = false) List<MultipartFile> files, 
        HttpServletRequest request, 
        @RequestParam("lect_idx") String lect_idx
    ) throws Exception {  
        ModelAndView mv = new ModelAndView();
        
        System.out.println(lect_idx);

        // 과제 내용 저장 
        int result = assignService.assignListMakeInsert(assignVO);
        System.out.println(result);
        String assignIdx = assignVO.getAssign_idx();
        System.out.println(assignIdx);

        // 파일 저장 경로
        String path = request.getSession().getServletContext().getRealPath("/resources/upload");
        int assign_f_order = 0;

        // 파일이 존재할 경우에만 처리
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String originName = file.getOriginalFilename();
                    String f_name = UUID.randomUUID().toString() + "_" + originName;
                    File saveFile = new File(path, f_name);
                    
                    System.out.println(f_name);
                    file.transferTo(saveFile);

                    AssignFileVO fileVO = new AssignFileVO();
                    fileVO.setAssign_idx(assignIdx);
                    fileVO.setAssign_f_name(f_name);
                    fileVO.setF_size(Long.toString(file.getSize()));
                    fileVO.setF_type(assignService.getFileType(originName));
                    fileVO.setAssign_f_order(Integer.toString(assign_f_order));
                    
                    result = assignService.insertAssignFile(fileVO);
                    assign_f_order++;
                }
            }
        }

        mv.setViewName("redirect:/assign-list?lect_idx=" + lect_idx);
        return mv;
    }

    
    
    
    //과제 상세 페이지 > 수정 페이지
    @GetMapping("/assign-update")
    public ModelAndView getAssignUpdate(AssignVO assignVO, @RequestParam("lect_idx") String lect_idx, 
    		@RequestParam("assign_idx") String assign_idx) {
    	ModelAndView mv = new ModelAndView();
    	
    	//디테일 불러서 담아야함 업데이트 페이지에 
    	//교수명 강의명
    	Map<String, Object> getTLecName= assignService.getTLecName(lect_idx);
        mv.addObject("getTLecName",getTLecName);
    	
        //과제 상세(제목, 내용, 시작시간마감기한)
        AssignVO assignDetail = assignService.assignDetail(assign_idx);
        mv.addObject("assignVO", assignDetail);
        
        //파일 불러오기
        List<AssignFileVO> getAssignFiles = assignService.getAssignFiles(assign_idx);
        mv.addObject("assignFiles", getAssignFiles);
    	
        mv.addObject("lect_idx", lect_idx);
    	mv.setViewName("teacher/assign/assignUpdate");
    	return mv;
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 날짜 포맷 설정
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

        // 시간 포맷 설정 (HH:mm)
        binder.registerCustomEditor(Time.class, new CustomTimeEditor("HH:mm"));
    }


    //과제 수정
    @PostMapping("/assign-update-ok")
    public ModelAndView assignUpdateOk(
        AssignVO assignVO,
        @RequestParam("assign_idx") String assign_idx,
        @RequestParam(value = "files", required = false) List<MultipartFile> files,
        HttpServletRequest request
    ) throws Exception {
        ModelAndView mv = new ModelAndView();

        // 과제 내용 수정
        assignService.assignUpdateOK(assignVO);

        // 기존 파일 전체 삭제
        assignService.deleteAssignFilesByAssignIdx(assign_idx);

        // 기존 유지 파일 다시 insert
        String[] existingFiles = request.getParameterValues("existing_file");
        String[] existingOldNames = request.getParameterValues("existing_file_old_name");

        int order = 0;
        if (existingFiles != null && existingOldNames != null) {
            for (int i = 0; i < existingFiles.length; i++) {
                AssignFileVO fileVO = new AssignFileVO();
                fileVO.setAssign_idx(assign_idx);
                fileVO.setAssign_f_name(existingFiles[i]);
                fileVO.setAssign_f_old_name(existingOldNames[i]);
                fileVO.setAssign_f_order(String.valueOf(order++));
                fileVO.setF_size("0");
                fileVO.setF_type(assignService.getFileType(existingFiles[i]));
                assignService.insertAssignFile(fileVO);
            }
        }

        // 새 파일 업로드 처리 (있을 때만)
        if (files != null && !files.isEmpty()) {
            String path = request.getSession().getServletContext().getRealPath("/resources/upload");

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String originName = file.getOriginalFilename();
                    String f_name = UUID.randomUUID().toString() + "_" + originName;
                    File saveFile = new File(path, f_name);

                    file.transferTo(saveFile);

                    AssignFileVO fileVO = new AssignFileVO();
                    fileVO.setAssign_idx(assign_idx);
                    fileVO.setAssign_f_name(f_name);
                    fileVO.setAssign_f_old_name(originName);
                    fileVO.setAssign_f_order(String.valueOf(order++));
                    fileVO.setF_size(Long.toString(file.getSize()));
                    fileVO.setF_type(assignService.getFileType(originName));
                    assignService.insertAssignFile(fileVO);
                }
            }
        }

        mv.setViewName("redirect:/assign-detail?lect_idx=" + assignVO.getLect_idx()+"&assign_idx="+assign_idx);
        return mv;
    }





    
 // 과제 삭제
    @GetMapping("/assign-delete-ok")
    public ModelAndView assignDeleteOK(@RequestParam("assign_idx") String assign_idx, 
                                       @RequestParam("user_pw") String user_pw, 
                                       HttpSession session, @RequestParam("lect_idx") String lect_idx) {
        Map<String, Object> tInfo = (Map<String, Object>) session.getAttribute("tInfo");
        String t_idx = String.valueOf(tInfo.get("t_idx"));  

        ModelAndView mv = new ModelAndView();

        String originPwd = assignService.getPwd(t_idx);

        if (passwordEncoder.matches(user_pw, originPwd)) {  
        	int result= assignService.getAssignDelete(assign_idx);

            if (result > 0) {  
                mv.setViewName("redirect:/assign-list?lect_idx="+lect_idx);  
            } else {  
                mv.addObject("deleteError", "과제 삭제 실패");
                mv.setViewName("redirect:/assign-detail?assign_idx=" + assign_idx);  
            }
        } else {  
            mv.addObject("pwdchk", "fail");  
            mv.setViewName("redirect:/assign-detail?assign_idx=" + assign_idx);  
        }

        return mv;
    }

    
    //과제 상세 파일 다운로드 
    @GetMapping("/assign-down")
    public void assignDown(HttpServletRequest request, HttpServletResponse response) {
    	try {
			String f_name=request.getParameter("f_name");
			String path=request.getSession().getServletContext().getRealPath("/resources/upload/"+f_name);
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
    
}
