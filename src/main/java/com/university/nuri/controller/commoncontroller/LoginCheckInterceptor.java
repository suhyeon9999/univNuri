package com.university.nuri.controller.commoncontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {

 @Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
	 HttpSession session = request.getSession(false); //기존 세션없으면 Null
	 if(session == null || session.getAttribute("loginchk") == null) {
		 response.setContentType("text/html; charset=UTF-8");
		 response.getWriter().write(
					"<script>"
							+"alert('로그인이 필요합니다.');"
							+"location.href='"+request.getContextPath()+"/login';"
							+"</script>"
				 );
		 return false;
	 }
	 return true;
}	
}
