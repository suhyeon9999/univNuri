<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../headerNavi.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>누리대학교 학습관리시스템</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/student/dashboard.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/headerNavi.css">
</head>
<body>
<div id="wrap">
	<div class="title-container">
		<div class="highlight"></div>
		<h1>대시보드</h1>
	</div>

	<c:choose>
		<c:when test="${uvo.user_level == 2}">
			<div id="dashboard-container">
				<div id="admin-card-container">
					<div class="admin-card" id="card1">
						<h3>학생</h3>
						<h1>${studentCount}</h1>
					</div>
					<div class="admin-card" id="card2">
						<h3>교직원</h3>
						<h1>${teacherCount}</h1>
					</div>
					<div class="admin-card" id="card3">
						<h3>학과</h3>
						<h1>${deptCount}</h1>
					</div>
					<div class="admin-card" id="card4">
						<h3>금학기 강의</h3>
						<h1>${currentLectCount}</h1>
					</div>
				</div>
			</div>

			<div class="block">
				<!-- 학적 변경 신청 -->
				<div class="white-block">
					<div class="menu-container">
						<div class="small-title-container">
							<div class="highlight"></div>
							<h2>
								학적 변경 신청<a class="move" href="/requestList"> ➔</a>
							</h2>
							<h3>
								처리 대기 중인 신청건 : <span class="span-red">${pendingRequestCount}</span>
							</h3>
						</div>
					</div>
				</div>

				<!-- 수강신청 오픈 일정 -->
				<div class="white-block">
					<div class="menu-container">
						<div class="small-title-container">
							<div class="highlight"></div>
							<h2>
								수강신청 오픈<a class="move" href="/moveEnrollApplyList"> ➔</a>
							</h2>
							<c:if test="${not empty EnrollApplyDashBoard}">
								<h4>
									<span>시작날짜 <i class="fa-regular fa-calendar"></i> ) <span class="span-blue">${EnrollApplyDashBoard.start_date}</span></span>
									<span>시작시간 <i class="fa-regular fa-clock"></i> ) <span class="span-blue">${EnrollApplyDashBoard.start_time}</span></span>
								</h4>
								<h4>
									<span>종료날짜 <i class="fa-regular fa-calendar"></i> ) <span class="span-blue">${EnrollApplyDashBoard.end_date}</span></span>
									<span>종료시간 <i class="fa-regular fa-clock"></i> ) <span class="span-blue">${EnrollApplyDashBoard.end_time}</span></span>
								</h4>
							</c:if>
							<c:if test="${empty EnrollApplyDashBoard}">
								<h4>등록된 수강신청 일정이 없습니다.</h4>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</c:when>
	</c:choose>
</div>
</body>
</html>
