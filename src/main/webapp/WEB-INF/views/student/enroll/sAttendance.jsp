<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/ko.js"></script>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/student/sAttendance.css">
<meta charset="UTF-8">
<title>수강 관리</title>
</head>
<body>
<%@include file="../../headerNavi.jsp"%>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>${lectName}</h1>
		</div>
		<div class="nav-container">
			<div class="nav-left">
				<div class="list">
					<a href="/sEnrollDetail?lect_idx=${sessionScope.lect_idx }">과제</a>
				</div>
				<div class="attend">
					<a href="/sAttendance?lect_idx=${sessionScope.lect_idx }&enroll_idx=${enroll_idx}">출결</a>
				</div>
			</div>
			<div class="nav-right">
				<div class="detail">
					<a href="/LectureInfo?lect_idx=${sessionScope.lect_idx }">강의 정보</a>
				</div>
			</div>
		</div>
	<div class="main-container">
			<div class="info-bar" style="font-weight: bold; font-size: 13px;">
			  <div class="info-item">
			    <span class="label">출석 :</span>
			    <span class="value" style="color: orange;">${countPresent}</span>
			  </div>
			  <div class="info-item">
			    <span class="label">결석 :</span>
			    <span class="value" style="color: orange;">${countAbsent}</span>
			  </div>
			  <div class="info-item">
			    <span class="label">지각 :</span>
			    <span class="value deadline" style="color: orange;">${countLate}</span>
			  </div>
			  <div class="info-item">
			    <span class="label">조퇴 :</span>
			    <span class="value deadline" style="color: orange;">${countLeave}</span>
			  </div>
			  <div class="info-item">
			    <span class="label">지각/조퇴 :</span>
			    <span class="value deadline" style="color: orange;">${countLateAndLeave}</span>
			  </div>
		  </div>
        <div class="title-credit-wrapper">
	<form action="/sAttendance" method="get">
		<ul class="selector">
			<li>
			<label for="dept_name">날짜</label>
				<input type="text" id="datepicker" name="attend_date" placeholder="날짜선택" value="${attend_date}"/><span onclick="document.getElementById('datepicker').focus()"></span>
			</li>
			<li>
			<label for="attend_status">출석 여부</label>
				<select name="attend_status">
				  <option value="전체" ${attend_status == '전체' ? 'selected' : ''}>전체</option>
				  <option value="출석" ${attend_status == '출석' ? 'selected' : ''}>출석</option>
				  <option value="결석" ${attend_status == '결석' ? 'selected' : ''}>결석</option>
				  <option value="지각" ${attend_status == '지각' ? 'selected' : ''}>지각</option>
				  <option value="조퇴" ${attend_status == '조퇴' ? 'selected' : ''}>조퇴</option>
				  <option value="지각/조퇴" ${attend_status == '지각/조퇴' ? 'selected' : ''}>지각/조퇴</option>
				</select>
			</li>
			<li>
				<button type="submit" style="color: white;" class="btn-orange">검색</button>
			</li>
		</ul>
	</form>
	</div>
        <div class="assign-list-wrapper">
			<table>
					<thead>
						<tr>
							<th>수업차시</th>
							<th>날짜</th>
							<th>출결</th>
						</tr>
					</thead>
					<tbody>
						<tr>
						<c:choose>
						<c:when test="${empty attendanceList }">
							<tr>
								<td colspan="3" style="font-weight: bold; font-size: 1em;">출결 정보가 없습니다.</td>
							</tr>
						</c:when>
						<c:otherwise>
						<c:forEach var="attend" items="${attendanceList}" varStatus="k">
							  <tr>
							    <td>${attend.class_num}</td>
							    <td>${attend.attend_date}</td>
							    <td>
							      <c:choose>
							        <c:when test="${attend.attend_status == 5}">결석</c:when>
							        <c:when test="${attend.attend_status == 1}">출석</c:when>
							        <c:when test="${attend.attend_status == 2}">지각</c:when>
							        <c:when test="${attend.attend_status == 3}">조퇴</c:when>
							        <c:when test="${attend.attend_status == 4}">지각/조퇴</c:when>
							        <c:otherwise>출결 미입력</c:otherwise>
							      </c:choose>
							    </td>
							  </tr>
							</c:forEach>
							</c:otherwise>
							</c:choose>
						</tr>
					</tbody>	
			</table>
        </div>
    </div>
</div>
<script>
flatpickr("#datepicker", {
	  locale: "ko",  // 한글 로케일 (별도 로딩 필요 시 아래 참고)
	  dateFormat: "Y-m-d",
	  onDayCreate: function(dObj, dStr, fp, dayElem) {
	    const date = dayElem.dateObj;
	    const today = new Date();

	    // 주말 처리
	    if (date.getDay() === 0 || date.getDay() === 6) {
	      dayElem.classList.add("weekend");
	    }

	    // 오늘 날짜 파란색 강조
	    if (
	      date.getFullYear() === today.getFullYear() &&
	      date.getMonth() === today.getMonth() &&
	      date.getDate() === today.getDate()
	    ) {
	      dayElem.classList.add("today-highlight");
	    }
	  }
	});
</script>
</body>
</html>