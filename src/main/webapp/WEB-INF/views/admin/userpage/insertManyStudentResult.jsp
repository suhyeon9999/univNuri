<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../headerNavi.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학생 일괄 등록 결과</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/admin/deptList.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/admin/insertManyStudent.css">
</head>
<body>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>일괄 등록 결과</h1>
		</div>

		<div class="main-container">
			<c:if test="${not empty successList}">
				<h2 class="list-title">등록 성공 학생 리스트</h2>
				<div class="table-container">
					<table>
						<thead>
							<tr>
								<th>NO</th>
								<th>이름</th>
								<th>학번</th>
								<th>생년월일</th>
								<th>이메일</th>
								<th>휴대폰</th>
								<th>학년</th>
								<th>전공</th>
								<th>주소</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="k" items="${successList}" varStatus="v">
								<tr>
									<td>${v.count}</td>
									<td>${k.name}</td>
									<td>${k.user_id}</td>
									<td>${k.birth}</td>
									<td>${k.email}</td>
									<td>${k.phone}</td>
									<td>${k.s_grade}</td>
									<td>${k.dept_name}</td>
									<td>${k.s_address}${k.s_address2}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

			</c:if>

			<c:if test="${not empty failList}">
				<h2 class="list-title" style="color: red">등록 실패한 리스트</h2>
				<div class="table-container">
					<table>
						<thead>
							<tr>
								<th>NO</th>
								<th>이름</th>
								<th>생년월일</th>
								<th>이메일</th>
								<th>휴대폰</th>
								<th>학년</th>
								<th>전공</th>
								<th>주소</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="k" items="${failList}" varStatus="v">
								<tr>
									<td>${v.count}</td>
									<td>${k.name}</td>
									<td>${k.birth}</td>
									<td>${k.email}</td>
									<td>${k.phone}</td>
									<td>${k.s_grade}</td>
									<td>${k.dept_name}</td>
									<td>${k.s_address}${k.s_address2}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
			<div class="btn-group">
				<button onclick="location.href='/studentList'" class="btn-gray" id="student-list-btn">학생목록</button>
				<button onclick="location.href='/insertManyStudent'"
					class="btn-blue" >일괄등록</button>
			</div>
		</div>

		<c:if test="${empty successList && empty failList}">
			<p>입력된 등록 정보가 없습니다.</p>
		</c:if>
		<br>

	</div>
</body>
</html>
