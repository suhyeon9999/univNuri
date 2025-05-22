<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/commonBoard.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/admin/statusMenu.css">
<meta charset="UTF-8">
<title>학생 학적 상태 관리</title>
</head>
<body>
	<%@ include file="../../headerNavi.jsp"%>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>학적 상태 관리</h1>
		</div>

		<div class="main-container">
			<!-- 일괄 처리 버튼 제거됨 -->
			<div class="table-container">
				<table id="requestTable">
					<thead>
						<tr>
							<th>No.</th>
							<th>이름</th>
							<th>학번</th>
							<th>신청구분</th>
							<th>신청일</th>
							<th>처리상태</th>
							<th>처리자</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="k" items="${requestList}" varStatus="v">
							<tr>
								<td>${fn:length(requestList) - v.count + 1}</td>
								<td>
									<a href="/requestDetail?req_idx=${k.req_idx}" class="a-black">
										${k.student_name}
									</a>
								</td>
								<td>${k.student_user_id}</td>
								<td>
									<c:choose>
										<c:when test="${k.req_type eq 1}">복학</c:when>
										<c:when test="${k.req_type eq 2}">휴학</c:when>
									</c:choose>
								</td>
								<td>
									<fmt:formatDate value="${k.req_date}" pattern="yyyy-MM-dd HH:mm" />
								</td>
								<td>
									<c:choose>
										<c:when test="${k.req_response eq 0 }">
											<span style="color: black;">대기</span>
										</c:when>
										<c:when test="${k.req_response eq 1 }">
											<span style="color: blue;">승인</span>
										</c:when>
										<c:when test="${k.req_response eq 2 }">
											<span style="color: red;">반려</span>
										</c:when>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${not empty k.admin_idx }">
											${k.admin_name}
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
