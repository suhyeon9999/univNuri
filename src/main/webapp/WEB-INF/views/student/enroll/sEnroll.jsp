<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/student/sEnroll.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/assignList.css">
<meta charset="UTF-8">
<title>수강 관리</title>
</head>
<body>
	<%@include file="../../headerNavi.jsp"%>
	<div id="wrap">
		<c:choose>
			<c:when test="${sessionScope.uvo.status != 3}">
				<div class="title-container">
					<div class="highlight"></div>
					<h1>
						<span style="color: orange;">금학기</span> 수강 강의
					</h1>
				</div>
				<div class="main-container">
					<c:choose>
						<c:when test="${not empty senrollcurrent[0]}">
							<p class="count">
								전체 : <span style="color: orange;">
									${senrollcurrent[0].assignTotal}</span>
							</p>
						</c:when>
						<c:otherwise>
							<p class="count">
								전체:<span style="color: orange;">0</span>
							</p>
						</c:otherwise>
					</c:choose>
					<div class="assign-list-wrapper">
						<c:choose>
							<c:when test="${empty senrollcurrent}">
								<p>수강중인 강의가 없습니다.</p>
							</c:when>
							<c:otherwise>
								<div class="white-block-container">
									<c:forEach var="k" items="${senrollcurrent}" varStatus="v">
										<div class="assign-list">
											<h2>
												<a class="link-black"
													href="/sEnrollDetail?lect_idx=${k.lect_idx}">${v.index + 1}.
													${k.lect_name}</a>
											</h2>
											<div class="teacher">
												<p class="top">교수</p>
												<p class="bottom">${k.teacher_name}</p>
											</div>
											<div class="class">
												<p class="top">강의실</p>
												<p class="bottom">${k.classroom}</p>
											</div>
											<div class="day">
												<p class="top">강의요일</p>
												<p class="bottom">${k.day_of_week}</p>
											</div>
											<div class="time">
												<p class="top">강의시간</p>
												<p class="bottom">${k.lecture_time}</p>
											</div>
											<div class="attendance">
												<p class="top">출석율</p>
												<p class="bottom">${k.attendance_rate}</p>
											</div>
											<div class="assign">
												<p class="top">과제</p>
												<p class="bottom">${k.assign_count}</p>
											</div>
										</div>
									</c:forEach>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</c:when>
			<c:otherwise>

			</c:otherwise>
		</c:choose>

		<div class="title-credit-wrapper">
			<div class="small-title-container">
				<div class="highlight" style="vertical-align: bottom;"></div>
				<h2 style="vertical-align: bottom;">지난 수강 강의</h2>
			</div>
			<form action="/sEnroll" method="post">
				<ul class="selector">
					<!-- 연도 필터 -->
					<li><label for="year">연도</label> <select name="year" id="year">
							<option value="">전체</option>
							<c:forEach var="y" items="${yearList}">
								<option value="${y}"
									<c:if test="${param.year == y}">selected</c:if>>${y}학년도</option>
							</c:forEach>
					</select></li>
					<!-- 학기 필터 -->
					<li><label for="semester">학기</label> <select name="semester"
						id="semester">
							<option value="">전체</option>
							<c:forEach var="s" items="${semesterList}">
								<option value="${s}"
									<c:if test="${param.semester == s}">selected</c:if>>${s}학기</option>
							</c:forEach>
					</select></li>
					<!-- 과목명 필터 -->
					<li><label for="lect_name" style="margin-right: 15px;">과목명</label>
						<input type="text" name="lect_name" id="lect_name"
						value="${param.lect_name}" /></li>
					<li>
						<button type="submit" style="color: white;" class="btn-orange">검색</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="main-container">
			<p class="count">
				전체 : <span style="color: orange;"> ${fn:length(senrollpast)}</span>
			</p>
			<div class="assign-list-wrapper">
				<c:choose>
					<c:when test="${empty senrollpast}">
						<p>지난 수강 강의가 없습니다.</p>
					</c:when>
					<c:otherwise>
						<div class="white-block-container">
							<c:forEach var="k" items="${senrollpast}" varStatus="v">
								<div class="assign-list">
									<h2>
										<a class="link-black"
											href="/sEnrollDetail?lect_idx=${k.lect_idx}"> ${v.index + 1}.
											${k.lect_name} </a>
									</h2>
									<div class="teacher">
										<p class="top">교수</p>
										<p class="bottom">${k.teacher_name}</p>
									</div>
									<div class="class">
										<p class="top">강의실</p>
										<p class="bottom">${k.classroom}</p>
									</div>
									<div class="day">
										<p class="top">강의요일</p>
										<p class="bottom">${k.day_of_week}</p>
									</div>
									<div class="time">
										<p class="top">강의시간</p>
										<p class="bottom">${k.lecture_time}</p>
									</div>
									<div class="attendance">
										<p class="top">출석율</p>
										<p class="bottom">${k.attendance_rate}</p>
									</div>
									<div class="assign">
										<p class="top">과제</p>
										<p class="bottom">${k.assign_count}</p>
									</div>
								</div>
							</c:forEach>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</body>
</html>