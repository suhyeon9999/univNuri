<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../../headerNavi.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/student/sApplyForChangeInsert.css">
<meta charset="UTF-8">
<title>학적 변경 신청</title>
</head>
<body>
	<div id="wrap">
		<form id="changeForm" method="post" enctype="multipart/form-data">
			<div class="title-container">
				<div class="highlight"></div>
				<h1>학적 변경 신청 내용</h1>
				<c:if test="${empty requestDetail.admin_idx}">
					<button type="button" onclick="requestAccept()" class="btn-blue">승인</button>
					<button type="button" onclick="requestReject()" class="btn-red">반려</button>
				</c:if>
			</div>
			<div class="table-container">
				<table>
					<tr>
						<th>이름</th>
						<td>${requestDetail.student_name}</td>
					</tr>
					<tr>
						<th>학번</th>
						<td>${requestDetail.user_id}</td>
					</tr>
					<tr>
						<th>신청구분</th>
						<c:choose>
							<c:when test="${requestDetail.req_type eq 1}">
								<td>복학</td>
							</c:when>
							<c:when test="${requestDetail.req_type eq 2}">
								<td>휴학</td>
							</c:when>
						</c:choose>
					</tr>
					<tr>
						<th>첨부파일</th>
						<td><c:choose>
								<c:when test="${not empty fileList}">
									<c:forEach var="k" items="${fileList}" varStatus="v">
										<a
											href="/aRequestFileDown?req_f_name=${k.req_f_name}&req_idx=${k.req_idx}"
											class="a-black">${fn:substringAfter(k.req_f_name, '_')}</a>
										<br>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<p>제출된 파일이 없습니다.</p>
								</c:otherwise>
							</c:choose></td>
					</tr>
					<tr>
						<th>신청일</th>
						<td><fmt:formatDate value="${requestDetail.req_date}"
								pattern="yyyy-MM-dd HH:mm" /></td>
					</tr>
					<tr>
						<th>신청사유</th>
						<td>${requestDetail.req_reason}</td>
					</tr>
					<tr>
						<th>처리상태</th>
						<c:choose>
							<c:when test="${requestDetail.req_response eq 0}">
								<td>대기</td>
							</c:when>
							<c:when test="${requestDetail.req_response eq 1}">
								<td style="color: blue">승인</td>
							</c:when>
							<c:when test="${requestDetail.req_response eq 2}">
								<td style="color: red">반려</td>
							</c:when>
						</c:choose>
					</tr>
					<tr>
						<th>처리일</th>
						<c:choose>
							<c:when test="${not empty requestDetail.req_response_date}">
								<td><fmt:formatDate
										value="${requestDetail.req_response_date}"
										pattern="yyyy-MM-dd HH:mm" /></td>
							</c:when>
							<c:otherwise>
								<td style="color: red">- 학적 변경 처리 전 -</td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr>
						<th>처리자</th>
						<c:choose>
							<c:when test="${not empty requestDetail.admin_idx}">
								<td>${requestDetail.admin_name}</td>
							</c:when>
							<c:otherwise>
								<td style="color: red">- 학적 변경 처리 전 -</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</table>
			</div>
			<button type="button" onclick="location.href='/requestList'"
				class="btn-gray">목록</button>

		</form>
	</div>
	<c:if test="${not empty requestDetail.req_idx}">
		<script type="text/javascript">
			var req_idx = '${requestDetail.req_idx}';
			var req_type = '${requestDetail.req_type}';

			function requestAccept() {
				location.href = '/requestResponse?req_idx=' + req_idx
						+ '&req_type=' + req_type;
			}

			function requestReject() {
				location.href = '/requestResponse?req_idx=' + req_idx
						+ '&req_type=' + req_type + '&reject=true';
			}
		</script>
	</c:if>
</body>
</html>
