package com.university.nuri.controller.studentcontroller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.service.studentservice.SApplyForChangeService;
import com.university.nuri.vo.commonvo.RequestFileVO;
import com.university.nuri.vo.commonvo.RequestVO;
import com.university.nuri.vo.commonvo.UserVO;

@Controller
public class SApplyForChangeController {
	@Autowired SApplyForChangeService sApplyForChangeService;
	@Autowired HttpSession session;
	@Autowired BCryptPasswordEncoder passwordEncoder;
	// 학적 변경 신청 - 전체 조회
	@RequestMapping("/sApplyforchange")
	public ModelAndView getApplyForChange() {
		try {
			ModelAndView mv = new ModelAndView();
			Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
			int s_idx = (Integer) sInfo.get("s_idx");  // 또는 필요 시 String으로 꺼내서 parseInt

			// service 호출
			List<Map<String, Object>> applyList = sApplyForChangeService.getApplyList(s_idx);

			// jsp에서 사용할 데이터 담기
			mv.addObject("applyList", applyList);
			mv.setViewName("student/applyforchange/sApplyForChange");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 학적 변경 신청 - 조건 검색
	@RequestMapping("/filteredSApplyList")
	public ModelAndView filterSApplyList(@RequestParam(required = false) Integer req_type,
			@RequestParam(required = false) Integer req_response) {
		try {
			ModelAndView mv = new ModelAndView();
			Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
			int s_idx = (Integer) sInfo.get("s_idx");  // 또는 필요 시 String으로 꺼내서 parseInt

			List<Map<String, Object>> applyList = sApplyForChangeService.filteredSApplyList(s_idx, req_type, req_response);

			if (applyList == null) applyList = new ArrayList<>();

			mv.addObject("applyList", applyList);
			mv.setViewName("student/applyforchange/sApplyForChange");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	// 학적 변경 신청 수정폼
	@PostMapping("/applyforchangeupdate")
	public ModelAndView getapplyforchangeUpdate() {
		return new ModelAndView("student/applyforchange/sApplyForChangeUpdate");
	}


	// 학적 변경 신청 폼으로 이동
	@PostMapping("/sApplyForChangeInsert")
	public ModelAndView getApplyForChangeInsert(){
		try {
			ModelAndView mv = new ModelAndView();
			// 세션에서 학생 정보 꺼내기
			UserVO uvo = (UserVO) session.getAttribute("uvo");  

			// 화면에 전달할 정보 추가
			mv.addObject("uvo", uvo);  // JSP에서 ${userVO.user_name}, ${userVO.user_id} 사용 가능
			mv.setViewName("student/applyforchange/sApplyForChangeInsert");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 학생 학적 변경 신청 - 비밀번호 체크
	@PostMapping("/sApplyForChangeCheckPw")
	@ResponseBody
	public String checkPw(@RequestParam("inputPwd") String inputPwd) {
		try {
			// 1. 세션에서 로그인 정보 불러오기
			UserVO uvo = (UserVO) session.getAttribute("uvo");
			// 세션에 비밀번호(복호화)되어있는게 저장되어있음
			String user_pw = uvo.getUser_pw();
			// (추 후에 아래에 matches 코드 필요)


			// 비밀번호 일치 여부 판단
			if (inputPwd != null && passwordEncoder.matches(inputPwd, user_pw)) {
				return "OK";
			} else {
				return "FAIL";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
	// 파일 타입 분별 메소드
	private int getFileTypeCode(String ext) {
		switch (ext.toLowerCase()) {
		case "jpg": return 0;
		case "jpeg": return 1;
		case "txt": return 2;
		case "ppt": return 3;
		case "pptx": return 4;
		case "pdf": return 5;
		case "doc": return 6;
		case "docx": return 7;
		default: return -1;
		}
	}

	// 학적 변경 신청
	@PostMapping("/sApplyForChangeInsertOk")
	@ResponseBody
	public String sApplyForChangeInsert(
			@RequestParam(value = "fileUpload[]", required = false) List<MultipartFile> files,
			@RequestParam("req_reason") String req_reason,
			@RequestParam("req_type") String req_type,
			HttpServletRequest request) {

		try {
			// 세션 정보
			UserVO uvo = (UserVO) request.getSession().getAttribute("uvo");
			String user_id = uvo.getUser_id();
			Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
			String s_idx = String.valueOf(sInfo.get("s_idx"));
			// 요청 정보 객체 생성
			RequestVO rvo = new RequestVO();
			rvo.setReq_reason(req_reason);
			rvo.setReq_type(req_type);
			rvo.setS_idx(s_idx);

			// request 테이블 insert
			int result = sApplyForChangeService.insertRequestTable(rvo); // req_idx 세팅되어야 함

			if (result > 0) {
				// 파일 있을 때만 request_file 처리
				if (files != null && !files.isEmpty()) {
					int order = 1;
					for (MultipartFile file : files) {
						if (!file.isEmpty()) {
							String originalName = file.getOriginalFilename();
							String ext = originalName.substring(originalName.lastIndexOf(".") + 1);
							int typeCode = getFileTypeCode(ext);

							if (typeCode == -1) {
								// 허용되지 않은 확장자일 경우 무시 또는 return
								return "INVALID_FILE_TYPE";
							}

							// 저장 경로 설정
							String path = request.getSession().getServletContext().getRealPath("/resources/upload/" + user_id);
							File dir = new File(path);
							if (!dir.exists()) dir.mkdirs();

							// 파일 저장
							String uuid = UUID.randomUUID().toString();
							String f_name = uuid + "_" + originalName;
							file.transferTo(new File(path, f_name));

							// VO 설정
							RequestFileVO rfvo = new RequestFileVO();
							rfvo.setReq_idx(rvo.getReq_idx());
							rfvo.setReq_f_name(f_name);
							rfvo.setReq_f_order(String.valueOf(order));
							rfvo.setReq_f_size(String.valueOf(file.getSize()));
							rfvo.setReq_f_type(String.valueOf(typeCode));
							order++;

							sApplyForChangeService.insertRequestFileTable(rfvo);
						}
					}
				}
				return "OK";
			} else {
				return "FAIL_REQ";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
	}

	// 학적 변경 신청 내역 상세보기
	@GetMapping("/sApplyForChangeDetail")
	public ModelAndView sApplyForChangeDetail(@RequestParam("req_idx") int req_idx,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		UserVO uvo = (UserVO) request.getSession().getAttribute("uvo");

		RequestVO rvo = sApplyForChangeService.getRequestDetail(req_idx);
		List<RequestFileVO> fileList = sApplyForChangeService.getRequestFileList(req_idx);

		mv.addObject("uvo", uvo);

		mv.addObject("rvo", rvo);
		mv.addObject("fileList", fileList);
		mv.setViewName("student/applyforchange/sApplyForChangeDetail");
		return mv;
	}

	// 파일 다운로드
	@GetMapping("/sRequestFileDown")
	public void sRequestFileDown(HttpServletRequest request, HttpServletResponse response) {
		try {
			UserVO uvo = (UserVO) request.getSession().getAttribute("uvo");
			String user_id = uvo.getUser_id();
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

	// 학적 변경 신청 수정
	@GetMapping("/sApplyForChangeUpdate")
	public ModelAndView sApplyForChangeUpdate(@RequestParam("req_idx") int req_idx,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		UserVO uvo = (UserVO) request.getSession().getAttribute("uvo");

		RequestVO rvo = sApplyForChangeService.getRequestDetail(req_idx);
		List<RequestFileVO> fileList = sApplyForChangeService.getRequestFileList(req_idx);

		mv.addObject("uvo", uvo);

		mv.addObject("rvo", rvo);
		mv.addObject("fileList", fileList);
		mv.setViewName("student/applyforchange/sApplyForChangeUpdate");
		return mv;
	}	

	// 학적 변경 신청 수정
	@PostMapping("/sApplyForChangeUpdateOk")
	public String sApplyForChangeUpdateOk(
			@RequestParam("req_idx") String req_idx,
			@RequestParam("req_type") String req_type,
			@RequestParam("req_reason") String req_reason,
			@RequestParam(value = "files", required = false) List<MultipartFile> files,
			HttpServletRequest request) {
		try {

			ModelAndView mv = new ModelAndView();
			// 1. 기본 정보 수정
			RequestVO rvo = new RequestVO();
			rvo.setReq_idx(req_idx);
			rvo.setReq_type(req_type);
			rvo.setReq_reason(req_reason);

			int result = sApplyForChangeService.updateRequest(rvo);
			if (result <= 0) return "errorPage";

			// 2. 기존 파일 전체 삭제
			sApplyForChangeService.deleteRequestFilesByReqIdx(req_idx);

			// 3. 기존 파일 다시 등록
			String[] existingNames = request.getParameterValues("existing_file");
			String[] existingOldNames = request.getParameterValues("existing_file_old_name");

			int order = 0;

			if (existingNames != null && existingOldNames != null) {
				for (int i = 0; i < existingNames.length; i++) {
					RequestFileVO rfvo = new RequestFileVO();
					rfvo.setReq_idx(req_idx);
					rfvo.setReq_f_name(existingNames[i]);
					rfvo.setReq_f_old_name(existingOldNames[i]);
					rfvo.setReq_f_order(String.valueOf(order++));
					rfvo.setReq_f_size("0");
					String ext = existingNames[i].substring(existingNames[i].lastIndexOf('.') + 1);
					rfvo.setReq_f_type(String.valueOf(getFileTypeCode(ext)));
					sApplyForChangeService.insertRequestFile(rfvo);
				}
			}

			if (files != null) {
				// 4. 새로 업로드된 파일 저장
				UserVO uvo = (UserVO) request.getSession().getAttribute("uvo");
				String user_id = uvo.getUser_id();
				String path = request.getSession().getServletContext().getRealPath("/resources/upload/" + user_id);


				for (MultipartFile file : files) {
					if (!file.isEmpty()) {
						String originName = file.getOriginalFilename();
						String newName = UUID.randomUUID().toString() + "_" + originName;
						file.transferTo(new File(path, newName));

						RequestFileVO new_rfvo = new RequestFileVO();
						new_rfvo.setReq_idx(req_idx);
						new_rfvo.setReq_f_name(newName);
						new_rfvo.setReq_f_old_name(originName);
						new_rfvo.setReq_f_size(String.valueOf(file.getSize()));
						new_rfvo.setReq_f_type(String.valueOf(getFileTypeCode(originName)));
						new_rfvo.setReq_f_order(String.valueOf(order++));

						sApplyForChangeService.insertRequestFile(new_rfvo);
					}
				}
			}

			return "redirect:/sApplyForChangeDetail?req_idx=" + req_idx;

		} catch (Exception e) {
			e.printStackTrace();
			return "errorPage";
		}
	}

	// 학적 변경 신청 삭제
	@PostMapping("/sApplyForChangeDeleteOk")
	@ResponseBody
	public String sApplyForChangeDeleteOk(@RequestParam String req_idx) {
		try {
			// 1. 파일 먼저 삭제 (0건이어도 OK)
			sApplyForChangeService.requestFileDeleteOk(Integer.parseInt(req_idx));

			// 2. 본문 삭제
			int result = sApplyForChangeService.requestDeleteOk(Integer.parseInt(req_idx));

			return (result > 0) ? "OK" : "FAIL";

		} catch (Exception e) {
			e.printStackTrace();
			return "FAIL";
		}
	}
}
