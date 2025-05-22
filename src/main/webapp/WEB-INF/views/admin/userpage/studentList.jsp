<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	import="java.util.List, java.util.Map, java.util.ArrayList, java.util.HashMap"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학생 관리</title>
<style>
</style>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/admin/deptList.css">
</head>
<body>
	<%@ include file="../../headerNavi.jsp"%>

	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>학생 관리</h1>
		</div>
		<div class="main-container">
			<p class="count">
				전체 학생: <span>${fn:length(studentList)}</span>
			</p>
			<div class="main-top">
				<div class="top-bar">
					<div class="search">
						<label for="name">이름</label> <input type="text" name="name" value="${param.name}">
						<label for="user_id">학번</label> <input type="text" name="user_id" value="${param.user_id}">
						<button type="submit" class="btn-orange" onclick="searchStudent()">
							<i class="fa-solid fa-magnifying-glass"></i>
						</button>
					</div>
					<div class="btn-group">
						<button type="button" class="btn-green"
							onclick="location.href='/moveInsertManyStudent'">일괄등록</button>
						<button type="button" class="btn-blue"
							onclick="location.href='/moveInsertStudent'">등록</button>
					</div>
				</div>
			</div>
			<c:choose>
				<c:when test="${empty studentList}">
					<p>조건에 맞는 학생이 없습니다.</p>
				</c:when>
				<c:otherwise>
					<div class="big-wrapper">
					<c:forEach var="k" items="${studentList}" varStatus="v">
						<div class="search-list-wrapper">
							<div class="search-list" id="student-search-list">
								<h2>
									<a href="/detailStudent?user_id=${k.user_id }" class="link-black">${v.count}.
										${k.name}</a>
								</h2>
								<div class="a_id">
									<p>학번</p>
									<p class="span-red">${k.user_id}</p>
								</div>
								<div class="count">
									<p>학년</p>
									<p class="span-red">${k.s_grade }</p>
								</div>
								<div class="count">
									<p>학과</p>
									<p class="span-red">${k.dept_name }</p>
								</div>
							</div>
						</div>
					</c:forEach>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<script type="text/javascript">
	function searchStudent() {
	    const name = document.querySelector('input[name="name"]').value.trim(); // 공백제거
	    const userId = document.querySelector('input[name="user_id"]').value.trim(); // 공백제거

	    if (!name && !userId) {
	        // 둘 다 비었으면 전체 리스트 요청
	        window.location.href = '/studentList'; // 매핑 주소
	        return;
	    }

	    let query = '/filteredStudentList?';
	    const params = [];

	    // 입력값이 있다면~ (쿼리에 붙일거임) encodeURI어쩌구는 특수문자도 인식하게
	    if (name) params.push('name=' + encodeURIComponent(name));
	    if (userId) params.push('user_id=' + encodeURIComponent(userId));

	    query += params.join('&');

	    window.location.href = query;
	}
	</script>

</body>
</html>