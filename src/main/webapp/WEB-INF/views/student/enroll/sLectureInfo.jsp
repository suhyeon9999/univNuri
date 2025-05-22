<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../headerNavi.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/student/sLectureInfo.css">
<meta charset="UTF-8">
<title>수강 관리</title>
</head>
<body>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>${lectInfo.lect_name }</h1>
		</div>
		<div class="nav-container">
						<div class="nav-left">
				<div class="list">
					<a href="/sEnrollDetail?lect_idx=${sessionScope.lect_idx }">과제</a>
				</div>
				<div class="attend">
					<a href="/sAttendance?lect_idx=${sessionScope.lect_idx }">출결</a>
				</div>
			</div>
			<div class="nav-right">
				<div class="detail">
					<a href="/LectureInfo?lect_idx=${sessionScope.lect_idx }">강의 정보</a>
				</div>
			</div>
		</div>
	<div class="main-container">
        <div class="title-credit-wrapper">
        	<table style="font-weight: bold; font-size: 18px;">
			  <tr>
			    <th>강의명</th>
			    <td>${lectInfo.lect_name}</td>
			  </tr>
			  <tr>
			    <th>강의코드</th>
			    <td>${lectInfo.lect_id}</td>
			  </tr>
			  <tr>
			    <th>담당 교수</th>
			    <td>${lectInfo.professor_name}</td>
			  </tr>
			  <tr>
			    <th>이수 학점</th>
			    <td>${lectInfo.lect_credit} 학점</td>
			  </tr>
			  <tr>
			    <th>강의 요일</th>
			    <td>${lectInfo.lect_day}요일</td>
			  </tr>
			  <tr>
			    <th>강의 시간</th>
			    <td>${lectInfo.lecture_time}</td>
			  </tr>
			  <tr>
			    <th>강의실</th>
			    <td>${lectInfo.class_room}</td>
			  </tr>
			  <tr>
			    <th>강의 기간</th>
			    <td>${lectInfo.lect_period}</td>
			  </tr>
			</table>
		</div>
    </div>
</div>
</body>
</html>