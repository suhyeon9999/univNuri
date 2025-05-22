<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../headerNavi.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/student/myPageInfo.css">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="wrap">
		<div class="total-container">
			<div class="title-container">
				<div class="highlight"></div>
				<h1>개인정보 관리</h1>
				<button id="btn-update" onclick="location.href='/aMyPageUpdate'">수정</button>
			</div>
			<div class="main-container">
				<div id="profile">
					<c:if test="${not empty myPage.f_name}">
						<img src="${pageContext.request.contextPath}/resources/upload/${sessionScope.uvo.user_id}/${myPage.f_name}" />
					</c:if>
					<c:if test="${empty myPage.f_name}">
						<img src="${pageContext.request.contextPath}/resources/upload/default_profile.png" />
					</c:if>

				</div>
				<div id="content">
					<div id="content-top">
						<p id="num">${myPage.user_id}</p>
						<p id="a_grade">
							<c:choose>
								<c:when test="${myPage.a_grade eq 0}">최고관리자
								</c:when>
								<c:when test="${myPage.a_grade eq 1}">중간관리자
								</c:when>
								<c:when test="${myPage.a_grade eq 2}">일반관리자
								</c:when>
							</c:choose>
						</p>
						<p id="name">${myPage.name}</p>
					</div>
					<div id="admin-status">
						<c:choose>
							<c:when test="${myPage.status eq 4}">재직
							</c:when>
						</c:choose>
					</div>
					<div class="input-form">
						<label for="phone">휴대폰</label> <input type="text"
							value="${myPage.phone }" id="phone" readonly disabled>
					</div>
					<div class="input-form">
						<label for="email">E-mail</label> <input type="text"
							value="${myPage.email }" id="email" readonly disabled>
					</div>
					<div class="input-form">
						<label for="birth">생년월일</label> <input type="text"
							value="${myPage.birth }" id="birth" readonly disabled>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	
</script>
</body>
</html>