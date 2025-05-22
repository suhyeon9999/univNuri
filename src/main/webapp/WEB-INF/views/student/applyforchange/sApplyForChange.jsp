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
	href="${pageContext.request.contextPath}/resources/css/student/sApplyForChange.css">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="wrap">
		<form method="post">
			<div class="title-container">
				<div class="highlight"></div>
				<h1>학적 변경 신청</h1>
				<button onclick="applyforchangeinsert(this.form)" id="btn-apply">신청</button>
			</div>
			<div id="caption">
				<p>
					<span class="font-sizes">전체 신청 :</span> <span
						style="color: orange;" class="font-sizes">${fn:length(applyList)}</span>
					<span class="decription">*모든 학적 변경 신청은 한 학기 기준, 휴/복학 연장시, 신청
						기간에 재신청 필수.</span>
				</p>
				<ul class="selector">
					<div class="selector-left">
						<li><label for="req_type">신청구분</label> <select id="req_type"
							name="req_type">
								<option value="" ${empty param.req_type ? 'selected' : ''}>전체</option>
								<option value="1" ${param.req_type == '1' ? 'selected' : ''}>복학</option>
								<option value="2" ${param.req_type == '2' ? 'selected' : ''}>휴학</option>
						</select></li>
						<li><label for="req_response">처리상태</label> <select
							id="req_response" name="req_response">
								<option value="" ${empty param.req_response ? 'selected' : ''}>전체</option>
								<option value="0" ${param.req_response == '0' ? 'selected' : ''}>대기</option>
								<option value="1" ${param.req_response == '1' ? 'selected' : ''}>승인</option>
								<option value="2" ${param.req_response == '2' ? 'selected' : ''}>반려</option>
						</select></li>
						<li>
							<button type="button" style="color: white;" class="btn-orange"
								onclick="searchApply()">검색</button>
						</li>
					</div>
				</ul>
				<table>
					<thead>
						<tr>
							<th>신청날짜</th>
							<th>신청구분</th>
							<th>처리상태</th>
							<th>처리일</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty applyList}">
								<tr>
									<td colspan="4" style="text-align: center;">조건에 맞는 신청내역이
										없습니다.</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="k" items="${applyList}">
									<tr>
										<td>
											<button type="button"
												onclick="location.href='/sApplyForChangeDetail?req_idx=${k.req_idx}'"
												class="date-detail">
												<fmt:formatDate value="${k.req_date}"
													pattern="yyyy-MM-dd HH:mm" />
											</button>
										</td>
										<td><c:choose>
												<c:when test="${k.req_type eq 1}">복학</c:when>
												<c:when test="${k.req_type eq 2}">휴학</c:when>
											</c:choose></td>
										<c:choose>
											<c:when test="${k.req_response eq 0}">
												<td>대기</td>
											</c:when>
											<c:when test="${k.req_response eq 1}">
												<td style="color: #007bff">승인</td>
											</c:when>
											<c:when test="${k.req_response eq 2}">
												<td style="color: #dc3545">반려</td>
											</c:when>
										</c:choose>
										<td><c:choose>
												<c:when test="${empty k.req_response_date}">-</c:when>
												<c:otherwise>
													<fmt:formatDate value="${k.req_response_date}"
														pattern="yyyy-MM-dd HH:mm" />
												</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		// 조건 검색
		function searchApply() {
			const req_type = document.querySelector('select[name="req_type"]').value;
			const req_response = document.querySelector('select[name="req_response"]').value;
		
			let query = '/filteredSApplyList?';
			const params = [];
		
			// 전체("")는 아예 안 보냄 → 컨트롤러에서 null로 받음
			if (req_type !== '') {
				params.push('req_type=' + encodeURIComponent(req_type));
			}
			if (req_response !== '') {
				params.push('req_response=' + encodeURIComponent(req_response));
			}
		
			if (params.length === 0) {
				// 전체 조건이면 전체 페이지 이동
				window.location.href = '/sApplyforchange';
			} else {
				window.location.href = query + params.join('&');
			}
		}
		// 아직 수정 안 함
		function applyforchangeupdate(f) {
			f.action = "/applyforchangeupdate";
			f.submit();
		}

		function applyforchangeinsert(f) {
			f.action = "/sApplyForChangeInsert";
			f.submit();
		}
	</script>
</body>
</html>