<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../headerNavi.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/headerNavi.css">
<style>


.nav-container {
	margin-top: 20px;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.nav-left, .nav-right {
	display: flex;
}

.nav-left div, .nav-right div {
	width: 110px;
	height: 50px;
	text-align: center;
	line-height: 50px;
	border-radius: 8px 8px 0 0;
	margin-right: 2px;
}

.list {
	background-color: #fdecb2;
}

.attend {
	background-color: #e7f2d8;
}

.assign {
	background-color: #b9e5fc;
}

.exam {
	background-color: #f9bcb5;
}

.detail {
	background-color: #d9c5ec;
}

.nav-container div a {
	text-decoration: none;
	color: gray;
}

.nav-container .detail a {
	color: black;
	font-weight: bold;
}

.highlight {
	position: relative;
	top: 45px;
	width: 200px;
	height: 13px;
	z-index: 0;
	background-color: cornflowerblue;
}


.main-container {
	background-color: #f2f2f2;
	padding: 30px;
}

.main-container {
	background-color: #f2f2f2;
	padding: 10px 20px 20px 20px;
}

table {
	border-collapse: collapse;
	width: 100%;
	background-color: white;
}

th {
	background-color: #e2e2f8;
}

th, td {
	border: 1px solid black;
	padding: 12px;
	text-align: left;
	text-align: center;
}
</style>
</head>
<body>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>${lectureInfo.lect_name}</h1>
		</div>
		<div class="nav-container">
			<div class="nav-left">
				<div class="list">
					<a href="/get-lecture-student-list?lect_idx=${lect_idx }">학생 목록</a>
				</div>
				<div class="attend">
					<a href="/get-lecture-attendance-list?lect_idx=${lect_idx }">출결 관리</a>
				</div>
				<div class="assign">
					<a href="/assign-list?lect_idx=${lect_idx }">과제 현황</a>
				</div>
				<div class="exam">
					<a href="/exam-list?lect_idx=${lect_idx }">시험 출제</a>
				</div>
			</div>
			<div class="nav-right">
				<div class="detail">
					<a href="/lecture-info?lect_idx=${lect_idx }">강의 정보</a>
				</div>
			</div>
		</div>
		<div class="main-container">
			<table>
				<tr>
					<th>강의명</th>
					<td>${lectureInfo['lect_name']}</td>
				</tr>
				<tr>
					<th>강의코드</th>
					<td>${lectureInfo['lect_id']}</td>
				</tr>
				<tr>
					<th>담당 교수</th>
					<td>${lectureInfo['name']}</td>
				</tr>
				<tr>
					<th>이수 학점</th>
					<td>${lectureInfo['lect_credit']}</td>
				</tr>
				<tr>
					<th>강의 요일</th>
					<td>${lectureInfo['lect_day']}</td>
				</tr>
				<tr>
					<th>강의 시간</th>
					<td>${lectureInfo['lect_start_time']}-${lectureInfo['lect_end_time']}</td>
				</tr>
				<tr>
					<th>강의실</th>
					<td>${lectureInfo['class_building']}
						${lectureInfo['class_room']}호</td>
				</tr>
				<tr>
					<th>강의 기간</th>
					<td>${lectureInfo['lect_start_date']}-
						${lectureInfo['lect_end_date']}</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>