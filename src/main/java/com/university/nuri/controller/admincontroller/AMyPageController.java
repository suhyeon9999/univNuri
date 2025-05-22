package com.university.nuri.controller.admincontroller;

import java.io.File;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.service.adminservice.AMyPageService;
import com.university.nuri.vo.commonvo.UserVO;

@Controller
public class AMyPageController {
	@Autowired
	private AMyPageService aMyPageService;
	@Autowired
	private HttpSession session;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	// 개인정보 관리 페이지
	@RequestMapping("/aMyPage")
	public ModelAndView getMyPage(String view_name) {
		try {
			Map<String, Object> aInfo = (Map<String, Object>) session.getAttribute("aInfo");
			int admin_idx = (Integer) aInfo.get("admin_idx");  // 또는 필요 시 String으로 꺼내서 parseInt
			Map<String, String> myPage = aMyPageService.getMyPage(admin_idx);

			ModelAndView mv = new ModelAndView();
			mv.addObject("myPage", myPage);
			mv.setViewName(view_name);
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 개인정보 관리 - 조회 페이지
	@RequestMapping("/aMyPageDetail")
	public ModelAndView getMyPageDetail() {
		return getMyPage("admin/mypage/aMyPage");
	}

	// 개인정보 관리 - 수정 페이지
	@RequestMapping("/aMyPageUpdate")
	public ModelAndView getMyPageUpdate() {
		return getMyPage("admin/mypage/aMyPageUpdate");
	}
	
	// 비밀번호 체크
	@PostMapping("/checkApassword")
	@ResponseBody
	public String checkSpassword(@RequestParam("inputPwd") String inputPwd) {
		Map<String, Object> aInfo = (Map<String, Object>) session.getAttribute("aInfo");
		int admin_idx = (Integer) aInfo.get("admin_idx");  // 또는 필요 시 String으로 꺼내서 parseInt

		// DB에서 비밀번호를 가져옴
		String dbpw = aMyPageService.checkApassword(admin_idx);  // 평문 비밀번호

		// DB에 저장된 비밀번호가 평문으로 저장되어 있으면 직접 비교
		if (inputPwd != null && passwordEncoder.matches(inputPwd, dbpw)) {
			return "OK";
		} else {
			return "FAIL";
		}
	}
	
	// 개인정보 관리 - 수정 페이지 - 비밀번호 변경
	@RequestMapping("/updateApassword")
	@ResponseBody
	public String updateSpassword(@RequestParam("newPassword") String newPassword) {
		Map<String, Object> aInfo = (Map<String, Object>) session.getAttribute("aInfo");
		int admin_idx = (Integer) aInfo.get("admin_idx");  // 또는 필요 시 String으로 꺼내서 parseInt
		String pws = passwordEncoder.encode(newPassword);
		int result = aMyPageService.updateApassword(admin_idx, pws);

		if(result > 0) {
		     // 세션의 비밀번호 정보도 업데이트
		     aInfo.put("user_pw", pws); 
		     session.setAttribute("aInfo", aInfo); 
			return "OK";
		} else {
			return "FAIL";
		}
	}
	
	// 개인정보 관리 - 수정 확정
	@RequestMapping("/aMyPageUpdateOK")
	@ResponseBody
	public String aMyPageUpdateOK(
			@RequestParam Map<String, String> params,
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request) {


		    try {
		        UserVO uvo = (UserVO) session.getAttribute("uvo");
		        Map<String, Object> aInfo = (Map<String, Object>) session.getAttribute("aInfo");
		        int admin_idx = (Integer) aInfo.get("admin_idx");

		        // 파라미터 처리
		        String phone = params.get("phone");
		        String email = params.get("email");
		        String birth = params.get("birth");

		        String f_old_name = params.get("f_old_name");
		        String f_curr_name = params.get("f_curr_name");

		        String f_size_str = params.get("f_size");
		        String f_type_str = params.get("f_type");

		        int f_size = 0;  // 기본값
		        int f_type = -1; // 기본값

		        if (f_size_str != null && f_type_str != null) {
		            f_size = Integer.parseInt(f_size_str);
		            f_type = Integer.parseInt(f_type_str);
		        }

		        boolean fileProcessSuccess = true;  // 기본 true (파일 없으면 무시)

		        // === 파일 선택된 경우만 파일 처리 ===
		        if (file != null && !file.isEmpty()) {
		            String user_id = uvo.getUser_id();
		            String path = request.getSession().getServletContext().getRealPath("/resources/upload/" + user_id);
		            UUID uuid = UUID.randomUUID();
		            String f_name = uuid.toString() + "_" + file.getOriginalFilename();

		            if ("null_data".equals(f_old_name) && "null_data".equals(f_curr_name)) {
		                // 0번: 한 번도 올린 적 없음
		                Map<String, Object> fileParam = new HashMap<>();
		                fileParam.put("f_name", f_name);
		                fileParam.put("f_size", f_size);
		                fileParam.put("f_type", f_type);

		                boolean fileInserted = aMyPageService.insertFileFirst(fileParam);
		                Object f_idx_obj = fileParam.get("f_idx");
		                int f_idx = ((BigInteger) f_idx_obj).intValue();
		                int user_idx = Integer.parseInt(uvo.getUser_idx());
		                boolean userFileUpdated = aMyPageService.updateUserFileIdx(user_idx, f_idx);
		                fileProcessSuccess = fileInserted && userFileUpdated;

		            } else if ("null_data".equals(f_old_name) && !"null_data".equals(f_curr_name)) {
		                // 1번: 딱 한 번 올린 사람
		                f_old_name = f_curr_name;
		                int f_idx = Integer.parseInt(uvo.getF_idx());
		                boolean fnameUpdated = aMyPageService.updateFname(f_idx, f_name, f_size, f_type) > 0;
		                boolean foldInserted = aMyPageService.insertFoldName(f_idx, f_old_name) > 0;
		                fileProcessSuccess = fnameUpdated && foldInserted;

		            } else {
		                // 2번 이상: 두 번 이상 올린 사람
		                f_old_name = f_curr_name;
		                int f_idx = Integer.parseInt(uvo.getF_idx());
		                fileProcessSuccess = aMyPageService.updateFnameFoldName(f_idx, f_name, f_size, f_type, f_old_name) > 0;
		            }

		            if (!fileProcessSuccess) {
		                return "FAIL";
		            }

		            // 최종적으로 파일 업로드
		            file.transferTo(new File(path, f_name));
		        }

		        // 개인정보 업데이트 (파일 처리 성공했거나, 파일 없을 때 실행)
		        int userInfoResult = aMyPageService.getMyPageUpdateOK(
		                admin_idx, phone, email, birth);

		        if (userInfoResult > 0) {
		            return "OK";
		        } else {
		            return "FAIL";
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		        return "FAIL";
		    }
		}
	
	
}
