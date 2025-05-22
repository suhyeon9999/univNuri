<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 사용자들 화면의 헤더와 사이드바.jsp -->
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>NURI UNIVERSITY 공통 레이아웃</title>
<!-- 1. header_navi.css -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/headerNavi.css">
<!-- 2. jQuery CDN -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- 3. Font Awesome CDN (v6.x) -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
	<!-- [헤더] -->
	<header>
		<div id="logo"><a href="/index">NURI UNIVERSITY</a></div>
		<div class="user-info">
			<span class="user-name">${uvo.name}(${uvo.user_id })님</span> | <a
				href="/logout"><i class="fa-solid fa-power-off"></i> LOGOUT</a>
		</div>
	</header>

	<!-- [사이드바] -->
	<aside>
		<nav>
			<ul class="navi-menu">
				<c:choose>
					<c:when test="${uvo.user_level == 0}">
						<!-- 학생용 메뉴 -->
						<li><a href="/sDashboard">대시보드</a></li>
						<li><a href="/sEnroll">수강 관리</a></li>
						<li><a href="/sScoreSearch">성적 관리</a></li>
						<c:if test="${uvo.status != '2' && uvo.status != '3' }">
						<li><a href="/senrollapply">수강신청</a></li>
						</c:if>
						<c:if test="${uvo.status != '3' }">
						<li><a href="/sApplyforchange">학적 변경 신청</a></li>
						</c:if>
						<li><a href="/sMyPageInfoDetail">개인정보 관리</a></li>
						<li><a href="/noticeList">공지사항 바로가기</a></li>
						<li><a href="/sBoardList">1:1 문의</a></li>
					</c:when>
					<c:when test="${uvo.user_level == 1}">
						<!-- 교사용 메뉴 -->
						<li><a href="/t-dashboard">대시보드</a></li>
						<li><a href="/main?scoreLectureIs=lecture">수업 관리</a></li>
						<li><a href="/main?scoreLectureIs=score">성적 관리</a></li>
						<li><a href="/tMyPageInfoDetail">개인정보 관리</a></li> 
						<li><a href="/objection-main">이의제기 관리</a></li> 
						<li><a href="/noticeList">공지사항 바로가기</a></li>
					</c:when>
					<c:when test="${uvo.user_level == 2}">
						<!-- 관리자용 메뉴 -->
						<li><a href="/aDashboard">대시보드</a></li>
						<li><a href="/studentList">학생 관리</a></li>
						<li><a href="/ateacherList">교수 관리</a></li>
						<li><a href="/adminList">관리자 관리</a></li>
						<li><a href="/deptList">학과 관리</a></li>
						<li><a href="/lectList">강의 관리</a></li>
						<li><a href="/enrollList">수강 관리</a></li>
						<c:if test="${sessionScope.aInfo.a_grade=='0'}">
						<li><a href="/access">사용자 권한 관리</a></li>
						</c:if>
						<li><a href="/requestList">휴학 및 복학 처리</a></li>
						<li><a href="/aBoardList">1:1 문의 관리</a></li>
						<li><a href="/aMyPageDetail">개인정보 관리</a></li>
						<li><a href="/noticeList">공지사항 바로가기</a></li>
					</c:when>
				</c:choose>
			</ul>
		</nav>
	</aside>
</body>
</html>