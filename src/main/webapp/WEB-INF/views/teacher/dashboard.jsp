<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 아래 import는 테스트일 때만 쓸 것 -->
<%@ page
	import="java.util.List, java.util.Map, java.util.ArrayList, java.util.HashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
	<%@include file="../headerNavi.jsp"%>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>대시보드</h1>
		</div>
		<!-- 카드 형태 대시보드 요소 -->
		<div id="dashboard-container">
			<div class="card-container">
				<div class="card" id="card1">
					<h3>교수</h3>
					<p>${tInfo.name}</p>
					<p>${tInfo.user_id}</p>
					<p>${tInfo.dept_name}</p>
				</div>
				<div class="card" id="card2">
					<h3>이의제기(대기)</h3>
					<h1>${objectCount}</h1>
				</div>
				<div class="card" id="card3">
					<h3>전체 강의</h3>
					<h1>${allLecCount}</h1>
				</div>
				<div class="card" id="card4">
					<h3>금학기 강의</h3>
					<h1>${listCount}</h1>
				</div>
			</div>
			<!-- 수업 일정 영역 -->
			<div class="schedule-section">
				<div class="small-title-container">
					<div class="highlight"></div>
					<div class="schedule-txt">
						<h2>오늘 수업 일정</h2>
						<span>${todayDate}</span>
					</div>
				</div>
				<c:choose>
					<c:when test="${not empty ttodayLectList}">
						<c:forEach items="${ttodayLectList}" var="k">
							<div class="schedule-item">
								<span class="time">${fn:substring(k.lect_start_time, 0, 5)}
									~ ${fn:substring(k.lect_end_time, 0, 5)}</span> <span
									class="lecture-name">${k.lect_name}</span> <span
									class="location">${k.class_building} ${k.class_room}</span>
							</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="schedule-item">
							<span>오늘 수업이 없습니다.</span>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<!-- 강의 리스트 -->
		<div class="small-title-container">
			<div class="highlight"></div>
			<h2>금학기 수업 강의</h2>
		</div>
		<div class="block">
			<p>
				강의 : <span class="span-red">${listCount}</span>
			</p>
			<div class="white-block-container">
				<c:forEach var="lecture" items="${lectureList}" varStatus="v">
					<ul class="white-block">
						<li><h2>${v.count}.${lecture.lect_name}</h2></li>
						<ul class="lecture-info">
							<li><span>강의실</span> <span class="span-red">${lecture.class_building}
									${lecture.class_room}</span></li>
							<li><span>강의요일</span> <span class="span-red">${lecture.lect_day}</span></li>
							<li><span>강의시간</span> <span class="span-red">${fn:substring(lecture.lect_start_time, 0, 5)}
									- ${fn:substring(lecture.lect_end_time, 0, 5)}</span></li>
							<li><span>학생 수</span> <span class="span-red">${lecture.student_count}</span></li>
						</ul>
					</ul>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>