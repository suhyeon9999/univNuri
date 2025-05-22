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
				<c:if test="${empty rvo.admin_idx }">
					<button type="button"
						onclick="location.href='/sApplyForChangeUpdate?req_idx=${rvo.req_idx}'"
						class="btn-blue">수정</button>
					<button type="button" onclick="showPwModal()" class="btn-red">삭제</button>
				</c:if>
			</div>
			<table>
				<tr>
					<th>이름</th>
					<td>${uvo.name}</td>
				</tr>
				<tr>
					<th>학번</th>
					<td>${uvo.user_id}</td>
				</tr>
				<tr>
					<th>신청구분</th>
					<c:choose>
						<c:when test="${rvo.req_type == 1}">
							<td>복학</td>
						</c:when>
						<c:when test="${rvo.req_type == 2}">
							<td>휴학</td>
						</c:when>
					</c:choose>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td><c:choose>
							<c:when test="${not empty fileList}">
								<c:forEach var="k" items="${fileList}" varStatus="v">
									<a href="/sRequestFileDown?req_f_name=${k.req_f_name}"
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
					<th>신청사유</th>
					<td>${rvo.req_reason}</td>
				</tr>
				<tr>
					<th>처리상태</th>
					<c:choose>
						<c:when test="${rvo.req_response eq 0}">
							<td>대기</td>
						</c:when>
						<c:when test="${rvo.req_response eq 1}">
							<td style="color: blue">승인</td>
						</c:when>
						<c:when test="${rvo.req_response eq 2}">
							<td style="color: red">반려</td>
						</c:when>
					</c:choose>
				</tr>
				<tr>
					<th>처리일</th>
					<c:choose>
						<c:when test="${not empty rvo.req_response_date}">
							<td>${fn:substring(rvo.req_response_date, 0, 16)}</td>
						</c:when>
						<c:otherwise>
							<td style="color: red">- 학적 변경 처리 전 -</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</table>

			<button type="button" onclick="location.href='/sApplyforchange'"
				class="btn-gray">목록</button>
			<!-- 비밀번호 입력 모달 -->
			<div id="pwModal" class="modal" style="display: none;">
				<div class="modal-content">
					<h3>비밀번호 확인</h3>
					<input type="password" id="pwInput" placeholder="비밀번호 입력" /> <br>
					<button id="pwCheckBtn" type="button" class="btn-blue">확인</button>
					<button id="pwCancelBtn" type="button" class="btn-red">취소</button>
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		// 비밀번호 체크 모달창
		function showPwModal() {
			$('#pwModal').show();
		}

		// 모달창 닫기
		$('#pwCancelBtn').click(function() {
			$('#pwModal').hide();
			$('#pwInput').val('');
		});

		// 수정된 data 전송 부분
		$('#pwCheckBtn').click(function() {
			var inputPw = $('#pwInput').val();
			console.log('입력된 비밀번호: ' + inputPw); // 입력된 비밀번호 출력
			if (!inputPw) {
				alert("비밀번호를 입력해주세요.");
				return;
			}

			var req_idx = '${rvo.req_idx}';
			$.ajax({
				url : "/sApplyForChangeCheckPw",
				type : "POST",
				data : {
					req_idx : req_idx,
					inputPwd : inputPw
				},
				success : function(response) {
					if (response === "OK") {
						// 비밀번호가 맞으면 데이터 전송
						$.ajax({
							url : "/sApplyForChangeDeleteOk",
							type : "POST",
							data : {
								req_idx : req_idx
							},
							success : function(response) {
								if (response === "OK") {
									location.href = "/sApplyforchange";
								} else {
									alert("삭제 실패");
								}
							},
							error : function() {
								alert("서버 오류");
							}
						});
					} else {
						alert("비밀번호가 틀렸습니다.");
						$('#pwInput').val('');
					}
				},
				error : function() {
					alert("서버 오류가 발생했습니다.");
				}
			});
		});
	</script>
</body>
</html>
