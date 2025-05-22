<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 상세보기</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/commonNoticeList.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/commonNoticeDetail.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/index.css">
</head>
<body>
	<div class="wrap">
	<header>
			<div class="header-inner">
				<h1>
					<a href="/index">NURI UNIVERSITY</a>
				</h1>
				<nav id="gnav">
					<ul class="gnav-list">
						<li><a href="/noticeList">NOTICE</a></li>
						<li><a href="/faqList">FAQ</a></li>
						<li><a href="https://www.kmooc.kr/" target="_blank">K-MOOC</a></li>
					</ul>
				</nav>
				<div id="logInOut">
					<ul class="logInOut-list">
					<c:choose>
						<c:when test="${empty sessionScope.loginchk}">
					<li style="align-content: center;"><a href="/login">LOGIN</a></li> 
					</c:when>
					<c:otherwise>
					<li style="font-size: 27.5px; margin-top: 2.1px;"><a href="/dashboard"><i class="fa-solid fa-school"></i></a></li>
					<li style="align-content: center;"><a href="/logout">LOGOUT</a></li>
					</c:otherwise>
					</c:choose>
					<!-- 아래가 테스트용!!!! -->
<!-- 					<li><a href="/loginTest">LOGIN_TEST</a></li> -->
				</ul>
				</div>
			</div>
		</header>
		  <div class="container">
		  	<p class="necessary">
				<c:choose>
						<c:when test="${detailNotice.necessary == '0' }">일반공지</c:when>
						<c:when test="${detailNotice.necessary == '1' }">중요공지</c:when>						
					</c:choose>
			</p>
		      <p class="meta"> 작성자 :
		      	<span>  
		      		<c:choose>
						<c:when test="${detailNotice.a_grade == '0' }">최고관리자</c:when>
						<c:when test="${detailNotice.a_grade == '1' }">중간관리자</c:when>
						<c:when test="${detailNotice.a_grade == '2' }">일반관리자</c:when>
					</c:choose>
		      	</span> |  작성일 : 
		      	 ${detailNotice.write_date.toString().substring(0, 10) }
		      </p>
		      <h2>${detailNotice.title }</h2>
	      <hr />
	      <pre>	${detailNotice.content }</pre>		   
	      <hr />
		<!-- 이전글, 다음글 -->
		<c:if test="${not empty previousNotice }">
	      <div class="article-wrapper">
	        <i class="fa-solid fa-arrow-up"></i>
	        <a class="article" 
	         href="/detailNotice?notice_idx=${previousNotice.notice_idx }&cPage=${paging.nowPage}">${previousNotice.title }</a>
	      </div>	
	      <hr />
		</c:if>
		<c:if test="${not empty nextNotice }">
	      <div class="article-wrapper">
	        <i class="fa-solid fa-arrow-down"></i>
	        <a class="article" 
	         href="/detailNotice?notice_idx=${nextNotice.notice_idx }&cPage=${paging.nowPage}">${nextNotice.title }</a>
	      </div>	
	      <hr />
		</c:if>		
	
	      <div class="buttons">
	        <button class="btn-gray"
	        	onclick="location.href='${pageContext.request.contextPath}/noticeList'">목록</button>	        	
	            <button class="btn-gray"
	             onclick="location.href='${pageContext.request.contextPath}/moveNoticeUpdate?notice_idx=${detailNotice.notice_idx}'" >수정</button>
		        <button class="btn-gray"
		         onclick="if(confirm('정말 삭제하시겠습니까?')) location.href='${pageContext.request.contextPath}/deleteNotice?notice_idx=${detailNotice.notice_idx}'" >삭제</button>	        
	      </div>
	    </div>
	</div>
	<footer>
		<div class="footer-inner">
			<p>© 2025 NURI UNIVERSITY. All rights reserved.</p>
			<p>문의: 02-1234-5678 | 주소: 서울시 누리구 누리로 123</p>
		</div>
	</footer>
	<!-- 알림창 -->
        <c:if test="${errorMessage == 'error'}">
            <script>
                alert(요청 실패.);
            </script>
        </c:if>
</body>
</html>