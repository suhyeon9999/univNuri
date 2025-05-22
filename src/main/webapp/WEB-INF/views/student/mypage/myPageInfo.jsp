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
				<button id="btn-update" onclick="location.href='/sMyPageInfoUpdate'">수정</button>
			</div>
			<div class="main-container">
				<div id="profile">
					<c:if test="${not empty myPageInfo.f_name}">
						<img src="${pageContext.request.contextPath}/resources/upload/${sessionScope.uvo.user_id}/${myPageInfo.f_name}" />
					</c:if>
					<c:if test="${empty myPageInfo.f_name}">
						<img src="${pageContext.request.contextPath}/resources/upload/default_profile.png" />
					</c:if>

				</div>
				<div id="content">
					<div id="content-top">
						<p id="num">${myPageInfo.user_id}</p>
						<p id="dept">${myPageInfo.dept_name}</p>
						<p id="name">${myPageInfo.name}</p>
					</div>
					<div id="student-status">
						<c:choose>
							<c:when test="${myPageInfo.status == 1}">재학
							</c:when>
							<c:when test="${myPageInfo.status == 2}">휴학
							</c:when>
							<c:when test="${myPageInfo.status == 3}">졸업
							</c:when>
						</c:choose>
					</div>
					<div class="input-form">
						<label for="phone">휴대폰</label> <input type="text"
							value="${myPageInfo.phone }" id="phone" readonly disabled>
					</div>
					<div class="input-form">
						<label for="email">E-mail</label> <input type="text"
							value="${myPageInfo.email }" id="email" readonly disabled>
					</div>
					<div class="input-form">
						<label for="birth">생년월일</label> <input type="text"
							value="${myPageInfo.birth }" id="birth" readonly disabled>
					</div>
				</div>
			</div>
			<div class="main-container2">
				<hr>
				<div id="content">
					<div class="input-form address-form">
						<label for="address"> 주소 : </label>
						<div class="address-box">
							<input type="text" id="addr1" name="addr1"
								value="${myPageInfo.s_address}" required readonly disabled>
							<input type="text" id="addr2" name="addr2"
								value="${myPageInfo.s_address2}" required readonly disabled>
						</div>
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