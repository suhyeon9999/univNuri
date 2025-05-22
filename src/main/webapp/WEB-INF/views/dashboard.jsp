<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 아래 import는 테스트일 때만 쓸 것 -->
<%@ page
	import="java.util.List, java.util.Map, java.util.ArrayList, java.util.HashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>누리대학교 학습관리시스템</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/student/dashboard.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/headerNavi.css">
</head>
<body>
	<%@include file="headerNavi.jsp"%>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>대시보드</h1>
		</div>
		<!-- 카드 형태 대시보드 요소 -->
		<c:choose>
			<c:when test="${uvo.user_level == 0}">
				<div id="dashboard-container">
					<div class="card-container">
						<div class="card" id="card1">
							<h3>과제</h3>
							<p>
								제출<span class="span-blue"> 3</span>
							</p>
							<p>
								미제출<span class="span-red"> 1</span>
							</p>
						</div>
						<div class="card" id="card2">
							<h3>이수학점</h3>
							<h1>102/130</h1>
						</div>
						<div class="card" id="card3">
							<h3>전체학점</h3>
							<h1>3.8/4.5</h1>
						</div>
						<div class="card" id="card4">
							<h3>수강 중인 강의</h3>
							<h1>6</h1>
						</div>
					</div>
					<!-- 수업 일정 영역 -->
					<div class="schedule-section">
						<div class="small-title-container">
							<div class="highlight"></div>
							<div class="schedule-txt">
								<h2>오늘 수업 일정</h2>
								<span>4월 30일 수요일</span>
							</div>
						</div>
						<div class="schedule-item">
							<span class="time">09:00 ~ 10:30</span> <span
								class="lecture-name">자바 프로그래밍</span> <span class="location">청101</span>
						</div>

						<div class="schedule-item">
							<span class="time">09:00 ~ 10:30</span> <span
								class="lecture-name">자바 프로그래밍</span> <span class="location">청101</span>
						</div>

						<div class="schedule-item">
							<span class="time">09:00 ~ 10:30</span> <span
								class="lecture-name">자바 프로그래밍</span> <span class="location">청101</span>
						</div>
					</div>


				</div>
				<!-- 강의 리스트 -->
				<div class="small-title-container">
					<div class="highlight"></div>
					<h2>금학기 수강 강의</h2>
				</div>
				<div class="block">
					<p>
						강의 : <span class="span-red">${fn:length(lectureList)}</span>
					</p>
					<c:forEach var="k" items="${senrollcurrent}" varStatus="v">
						<ul class="white-block">
							<li><h2>${v.index + 1 }.${lect_name.title}</h2></li>
							<ul class="lecture-info">
								<li><span>교수명</span> <span class="span-red">${k.teacher_name}</span></li>
								<li><span>출석률</span> <span class="span-red">${lecture.attend}</span></li>
								<li><span>과제</span> <span class="span-red">${lecture.assignment}</span></li>
							</ul>
						</ul>
					</c:forEach>
				</div>
			</c:when>
			<c:when test="${uvo.user_level == 1}">
				<div id="dashboard-container">
					<div class="card-container">
						<div class="card" id="card1">
							<h3>교수</h3>
							<p>김현우</p>
							<p>P20250121</p>
							<p>컴퓨터공학과</p>
						</div>
						<div class="card" id="card2">
							<h3>이의제기(대기)</h3>
							<h1>3</h1>
						</div>
						<div class="card" id="card3">
							<h3>전체 강의</h3>
							<h1>35</h1>
						</div>
						<div class="card" id="card4">
							<h3>금학기 강의</h3>
							<h1>6</h1>
						</div>
					</div>
					<!-- 수업 일정 영역 -->
					<div class="schedule-section">
						<div class="small-title-container">
							<div class="highlight"></div>
							<div class="schedule-txt">
								<h2>오늘 수업 일정</h2>
								<span>4월 30일 수요일</span>
							</div>
						</div>

						<div class="schedule-item">
							<span class="time">09:00 ~ 10:30</span> <span
								class="lecture-name">자바 프로그래밍</span> <span class="location">청101</span>
						</div>

						<div class="schedule-item">
							<span class="time">09:00 ~ 10:30</span> <span
								class="lecture-name">자바 프로그래밍</span> <span class="location">청101</span>
						</div>

						<div class="schedule-item">
							<span class="time">09:00 ~ 10:30</span> <span
								class="lecture-name">자바 프로그래밍</span> <span class="location">청101</span>
						</div>
					</div>
				</div>
				<!-- 강의 리스트 -->
				<div class="small-title-container">
					<div class="highlight"></div>
					<h2>금학기 수업 강의</h2>
				</div>
				<div class="block">
					<p>
						강의 : <span class="span-red">${fn:length(lectureList)}</span>
					</p>
					<c:forEach var="lecture" items="${lectureList}" varStatus="v">
						<ul class="white-block">
							<li><h2>${v.count }.${lecture.title}</h2></li>
							<ul class="lecture-info">
								<li><span>강의실</span> <span class="span-red">${lecture.building}
										${lecture.lectclass}</span></li>
								<li><span>강의요일</span> <span class="span-red">${lecture.weekday}</span></li>
								<li><span>강의시간</span> <span class="span-red">${lecture.starttime}
										- ${lecture.endtime}</span></li>
								<li><span>학생 수</span> <span class="span-red">${lecture.student}</span></li>
							</ul>
						</ul>
					</c:forEach>
				</div>
			</c:when>
			<c:when test="${uvo.user_level == 2}">
				<div id="dashboard-container">
					<div id="admin-card-container">
						<div class="admin-card" id="card1">
							<h3>학생</h3>
							<h1>650</h1>
						</div>
						<div class="admin-card" id="card2">
							<h3>교직원</h3>
							<h1>95</h1>
						</div>
						<div class="admin-card" id="card3">
							<h3>학과</h3>
							<h1>35</h1>
						</div>
						<div class="admin-card" id="card4">
							<h3>금학기 강의</h3>
							<h1>156</h1>
						</div>
					</div>
				</div>
				<!-- 강의 리스트 -->
				<div class="block">
					<div class="white-block">
						<div class="menu-container">
							<div class="small-title-container">
								<div class="highlight"></div>
								<h2>
									학적 변경 신청<a class="move" href="#"> ➔</a>
								</h2>
								<h3>
									처리 대기 중인 신청건 : <span class="span-red">4</span>
								</h3>
							</div>
						</div>
					</div>
					<div class="white-block">
						<div class="menu-container">
							<div class="small-title-container">
								<div class="highlight"></div>
								<h2>
									수강신청 오픈<a class="move" href="#"> ➔</a>
								</h2>
								<h3>
									개강 예정 강의 : <span class="span-red">156</span>
								</h3>
								<h4>
									<span>시작날짜 <i class="fa-regular fa-calendar"></i> ) <span
										class="span-blue"> 2025.05.10</span></span> <span>시작시간 <i
										class="fa-regular fa-clock"></i> ) <span class="span-blue">
											9:00</span>
									</span>
								</h4>
								<h4>
									<span>종료날짜 <i class="fa-regular fa-calendar"></i> ) <span
										class="span-blue"> 2025.05.13</span></span> <span>종료시간 <i
										class="fa-regular fa-clock"></i> ) <span class="span-blue">
											17:00</span>
									</span>
								</h4>
							</div>
						</div>
					</div>
				</div>
			</c:when>
		</c:choose>
	</div>
</body>
</html>