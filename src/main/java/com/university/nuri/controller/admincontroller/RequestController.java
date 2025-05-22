package com.university.nuri.controller.admincontroller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.service.adminservice.RequestService;
import com.university.nuri.vo.commonvo.RequestFileVO;
import com.university.nuri.vo.commonvo.RequestVO;
import com.university.nuri.vo.commonvo.UserVO;

@Controller
public class RequestController {
	@Autowired
	private RequestService requestService;
	@Autowired
	private HttpSession session;

	@GetMapping("/requestList")
	public ModelAndView getRequestList() {
		try {
			ModelAndView mv = new ModelAndView();
			List<Map<String, Object>> requestList = requestService.getRequestList();
			mv.addObject("requestList", requestList); 
			mv.setViewName("admin/request/requestList");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping("/requestDetail")
	public ModelAndView getRequestDetail(@RequestParam("req_idx") int req_idx,
			HttpServletRequest request) {
		try {
			ModelAndView mv = new ModelAndView();
			Map<String, Object> requestDetail = requestService.getRequestDetail(req_idx);
			List<RequestFileVO> fileList = requestService.getRequestFileList(req_idx);
			mv.addObject("requestDetail", requestDetail);
			mv.addObject("fileList", fileList);
			mv.setViewName("admin/request/requestDetail");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping("/requestResponse")
	public ModelAndView requestResponse(@RequestParam("req_idx") int req_idx,
			@RequestParam("req_type") int req_type,
			@RequestParam(value = "reject", required = false) String reject,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			// 관리자 번호
			Map<String, Object> aInfo = (Map<String, Object>) request.getSession().getAttribute("aInfo");
			int admin_idx = Integer.parseInt(String.valueOf(aInfo.get("admin_idx")));

			// 승인/반려 여부 결정
			boolean isReject = (reject != null);

			// 서비스 호출
			requestService.requestResponse(req_idx, admin_idx, isReject, req_type); // ✅ 파라미터 추가됨

			mv.setViewName("redirect:/requestList");
			return mv;

		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:/error");
			return mv;
		}
	}
	
	
	// 파일 다운로드
	@GetMapping("/aRequestFileDown")
	public void sRequestFileDown(@RequestParam("req_idx") int req_idx,  HttpServletRequest request, 
			HttpServletResponse response) {
		try {
			Map<String, Object> detail = requestService.getRequestDetail(req_idx);
			String user_id = String.valueOf(detail.get("user_id")); // 학생 user_id
			String req_f_name = request.getParameter("req_f_name");
			String path = request.getSession().getServletContext().getRealPath("/resources/upload/" + user_id);
			String r_path = URLEncoder.encode(req_f_name, "UTF-8");
			// 확인
			System.out.println("Received file name: " + req_f_name);
			System.out.println("File path: " + path);			

			// 브라우저 설정
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename = "+r_path);

			// 실제 입출력
			File file = new File(path, req_f_name); // 경로 + 파일명 합쳐서 파일 객체 생성
			FileInputStream in =new FileInputStream(file);
			OutputStream out = response.getOutputStream();
			FileCopyUtils.copy(in, out);
			response.getOutputStream().flush();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
