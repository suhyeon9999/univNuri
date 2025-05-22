package com.university.nuri.controller.commoncontroller;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.university.nuri.service.commonservice.LoginService;
import com.university.nuri.vo.adminvo.AdminVO;
import com.university.nuri.vo.commonvo.UserVO;

@Controller
public class MainController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@GetMapping("/login")
	public ModelAndView getLoginForm() {
		return new ModelAndView("common/login/loginForm");
	}
	// 아래는 테스트용
	@GetMapping("/index")
	public ModelAndView getIndex() {
		return new ModelAndView("index");
	}
	// 아래는 테스트용2
	@GetMapping("/loginTest") 
	public ModelAndView getLoginTest() {
		return new ModelAndView("/dashboard");
	}

	// 대쉬보드
	@RequestMapping("/dashboard")
	public String redirectDashboard(HttpSession session) {
	    UserVO uvo = (UserVO) session.getAttribute("uvo");
	    if (uvo == null) return "redirect:/loginForm";

	    switch (uvo.getUser_level()) {
	        case "0": return "redirect:/sDashboard";     // 학생
	        case "1": return "redirect:/t-dashboard";    // 교사
	        case "2": return "redirect:/aDashboard";     // 관리자
	        default: return "redirect:/loginForm";
	    }
	}
	// login chk
	@RequestMapping("/user_login")
	public ModelAndView getUserLogin(UserVO uservo, HttpSession session) {
		try {
			ModelAndView mv = new ModelAndView();
			UserVO uvo = loginService.getLoginOK(uservo.getUser_id());
			if(uvo == null || "1".equals(uvo.getActive()) || "5".equals(uvo.getStatus())) {
				  mv.addObject("loginchk", "fail"); 
				  mv.setViewName("common/login/loginForm");
				  return mv;
			}else {
				if(passwordEncoder.matches(uservo.getUser_pw(), uvo.getUser_pw())) {
				session.setAttribute("loginchk", "ok");
				session.setAttribute("uvo", uvo);
				System.out.println("🔍 [세션] uvo: " + uvo.getUser_id());
				
				Enumeration<String> attrNames = session.getAttributeNames();
				while (attrNames.hasMoreElements()) {
				    String name = attrNames.nextElement();
				    Object value = session.getAttribute(name);
				    System.out.println("세션 속성 이름: " + name + ", 값: " + value);
				}

				String userLevel = uvo.getUser_level();
				String userIdx = uvo.getUser_idx();
				switch (userLevel) {
				// 학생 idx
			    case "0":
			    	Map<String, Object> sInfo = loginService.getByOccupationIdx(userIdx, userLevel);
			        session.setAttribute("sInfo", sInfo);
			        mv.setViewName("redirect:/sDashboard");
			        break;
			    // 선생 idx
			    case "1":
			    	Map<String, Object> tInfo = loginService.getByOccupationIdx(userIdx, userLevel);
			        session.setAttribute("tInfo", tInfo);
			        mv.setViewName("redirect:/t-dashboard");
			        break;
			    // 관리자 idx
			    case "2":
			    	Map<String, Object>  aInfo = loginService.getByOccupationIdx(userIdx, userLevel);
			        session.setAttribute("aInfo", aInfo);
			        mv.setViewName("redirect:/aDashboard");
			        break;
			    default:
			        // 예외 처리 필요 시
			        System.out.println("Unknown user level: " + userLevel);
			        break;
				}
				return mv;
				}else{
					mv.addObject("loginchk", "fail");
					mv.setViewName("common/login/loginForm");
					return mv;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error");
		}
	}
	// logout
	@GetMapping("/logout")
	public ModelAndView getLogout(HttpSession session) {		
		session.invalidate();
		return new ModelAndView("redirect:/index");
	}

	// 학생, 교수 메뉴얼 다운로드
	@GetMapping("/manual")
	public void getSmanualDown(HttpServletRequest request, HttpServletResponse response) {
		try {
			String f_name = request.getParameter("f_name"); // 파일명
			String path = request.getSession().getServletContext().getRealPath("/resources/upload/" + f_name); // 실제 경로
			String r_path = URLEncoder.encode(f_name, "UTF-8"); // 한글처리

			// 브라우저 설정 (PDF 파일)
			response.setContentType("application/pdf");

			// Content-Disposition 2가지 방식
			// inline : 브라우저로 보여주고 다운받을 수 있도록 선택권 주는 것) 
			// attachment : 바로 다운받게 하는 것
			response.setHeader("Content-Disposition", "inline; filename=" + r_path);  

			// 실제 입출력
			File file = new File(new String(path.getBytes(), "UTF-8")); 
			FileInputStream in = new FileInputStream(file);
			OutputStream out = response.getOutputStream(); // 브라우저에 출력

			FileCopyUtils.copy(in, out);
			response.getOutputStream().flush();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}

