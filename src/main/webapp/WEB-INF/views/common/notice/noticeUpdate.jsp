<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 수정</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/commonNoticeList.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/commonNoticeInsert.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/index.css">
</head>
<body>
	<div class="wrap">
	<!-- 헤더 영역 -->
		<header>
			<div class="header-inner">
				<h1>
					<a href="/index">NURI UNIVERSITY</a>
				</h1>
				<nav id="gnav">
					<ul class="gnav-list">
						<li><a href="/noticeList">NOTICE</a></li>
						<li><a href="/faqDetail">FAQ</a></li>
						<li><a href="https://www.kmooc.kr/" target="_blank">K-MOOC</a></li>
					</ul>
				</nav>
				<div id="logInOut">
					<ul class="logInOut-list">
					<c:choose>
						<c:when test="${empty sessionScope.loginchk}">
					<li style="align-content: center;"><a href="/login">LOGIN</a></li> 
					</c:when>
					<c:otherwise>
					<li style="font-size: 27.5px; margin-top: 2.1px;"><a href="/dashboard"><i class="fa-solid fa-school"></i></a></li>
					<li style="align-content: center;"><a href="/logout">LOGOUT</a></li>
					</c:otherwise>
					</c:choose>
					<!-- 아래가 테스트용!!!! -->
<!-- 					<li><a href="/loginTest">LOGIN_TEST</a></li> -->
				</ul>
				</div>
			</div>
		</header>
		<form action="/noticeUpdate" method="post" name="noticeForm" onsubmit="return validateForm()">
			<div class="container">
					<p class="meta">  작성일 : ${detailNotice.write_date.toString().substring(0, 10) }</p>
				<div class="form-group">
					<input type="text" class="line-input" name="title" value="${detailNotice.title }" required />
					 <select name="necessary" required>					
						<option value="0" ${detailNotice.necessary == '0' ? 'selected' : ''}>일반공지</option>
                  		<option value="1" ${detailNotice.necessary == '1' ? 'selected' : ''}>중요공지</option>					
					</select>
				</div>
				<hr />	
				<div class="form-group">
					<textarea class="line-textarea" name="content"  required >${detailNotice.content }</textarea>
				</div>
				<div class="buttons">
					<button type="button" class="btn-gray" onclick="location.href='${pageContext.request.contextPath}/detailNotice?notice_idx=${detailNotice.notice_idx}'">취소</button>
					<button type="submit" class="btn-gray">등록</button>
					<input type="hidden" name="notice_idx"	value="${detailNotice.notice_idx }">
					<input type="hidden" name="cPage"	value="${cPage }">
				</div>
			</div>
		</form>
	</div>
<footer>
		<div class="footer-inner">
			<p>© 2025 NURI UNIVERSITY. All rights reserved.</p>
			<p>문의: 02-1234-5678 | 주소: 서울시 누리구 누리로 123</p>
		</div>
	</footer>
<script>
function validateForm() {
    var title = document.forms["noticeForm"]["title"].value.trim();
    var content = document.forms["noticeForm"]["content"].value.trim();
    if (title === "") {
        alert("제목을 입력해주세요.");
        return false;
    }
    if (content === "") {
        alert("내용을 입력해주세요.");
        return false;
    }
    if (title.length > 100) {
        alert("제목은 100자를 초과할 수 없습니다.");
        return false;
    }
    if (content.length > 4000) {
        alert("내용은 4000자를 초과할 수 없습니다.");
        return false;
    }
    return true;
}
</script>
</body>
</html>