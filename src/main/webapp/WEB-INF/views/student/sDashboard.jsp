<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../headerNavi.jsp"%>
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
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>대시보드</h1>
		</div>

		<!-- 카드 형태 대시보드 요소 -->

		<div id="dashboard-container">
			<div class="card-container">
				<div class="card" id="card1">
					<h3>${sessionScope.uvo.name}학생</h3>
				</div>
				<div class="card" id="card2">
					<h3>이수학점</h3>
					<h1>${completedCredit}/130</h1>
				</div>
				<div class="card" id="card3">
					<h3>과제</h3>
					<c:choose>
						<c:when test="${sessionScope.uvo.status == 3}">
							<p>(졸업)</p>
						</c:when>
						<c:otherwise>
							<p>
								제출 <span class="span-blue">${empty submitted ? 0 : submitted}</span>
							</p>
							<p>
								미제출 <span class="span-red">${empty notSubmitted ? 0 : notSubmitted}</span>
							</p>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="card" id="card4">
					<h3>수강 중인 강의</h3>
					<c:choose>
						<c:when test="${sessionScope.uvo.status == 3}">
							<p>(졸업)</p>
						</c:when>
						<c:otherwise>
							<h1>${listCount}</h1>
						</c:otherwise>
					</c:choose>
				</div>
			</div>

			<!-- 오늘 수업 일정 -->
			<div class="schedule-section">
				<div class="small-title-container">
					<div class="highlight"></div>
					<div class="schedule-txt">
						<h2>오늘 수업 일정</h2>
						<span>${todayDate}</span>
					</div>
				</div>

				<c:choose>
					<c:when
						test="${not empty sTodayLectList && sessionScope.uvo.status != 3}">
						<c:forEach var="lect" items="${sTodayLectList}">
							<div class="schedule-item">
								<span class="time">${lect.lect_start_time} ~
									${lect.lect_end_time}</span> <span class="lecture-name">${lect.lect_name}</span>
								<span class="location">${lect.class_building}${lect.class_room}</span>
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

		<!-- 금학기 수강 강의 리스트 -->
		<c:choose>
			<c:when test="${sessionScope.uvo.status == 3}">
				<div class="small-title-container">
					<div class="highlight"></div>
					<h2>수강 내역</h2>
				</div>
				<div class="block">
					<p>
						강의 : <span class="span-red">${fn:length(allLectList)}</span>
					</p>
					<div class="white-block-container">
						<c:forEach var="lect" items="${allLectList}" varStatus="v">
							<ul class="white-block">
								<li><h2>${v.index + 1}.${lect.lect_name}</h2></li>
								<ul class="lecture-info">
									<li><span>강의시간</span> <span class="span-red"> <fmt:formatDate
												value="${lect.lect_start_time}" pattern="HH:mm" /> ~ <fmt:formatDate
												value="${lect.lect_end_time}" pattern="HH:mm" />
									</span></li>
									<li><span>강의요일</span> <span class="span-red">${lect.lect_day}</span></li>
									<li><span>강의실</span> <span class="span-red">${lect.class_building}
											${lect.class_room}</span></li>
								</ul>
							</ul>
						</c:forEach>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="small-title-container">
					<div class="highlight"></div>
					<h2>금학기 수강 강의</h2>
				</div>
				<div class="block">
					<p>
						강의 : <span class="span-red">${fn:length(lectureList)}</span>
					</p>
					<div class="white-block-container">
						<c:forEach var="lect" items="${lectureList}" varStatus="v">
							<ul class="white-block">
								<li><h2>${v.index + 1}.${lect.lect_name}</h2></li>
								<ul class="lecture-info">
									<li><span>강의시간</span> <span class="span-red"> <fmt:formatDate
												value="${lect.lect_start_time}" pattern="HH:mm" /> ~ <fmt:formatDate
												value="${lect.lect_end_time}" pattern="HH:mm" />
									</span></li>
									<li><span>강의요일</span> <span class="span-red">${lect.lect_day}</span></li>
									<li><span>강의실</span> <span class="span-red">${lect.class_building}
											${lect.class_room}</span></li>
								</ul>
							</ul>
						</c:forEach>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>
