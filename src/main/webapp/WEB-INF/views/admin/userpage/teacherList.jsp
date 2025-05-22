<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>교수 관리</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/deptList.css">
<style type="text/css">
.main-container{
overflow-x:hidden;  
overflow-y:auto; 
}
</style>
</head>
<body>
	<%@ include file="../../headerNavi.jsp"%>

	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>교수 관리</h1>
		</div>
		<div class="main-container">
			<p class="count">
				전체 교수: <span>${fn:length(teacherList)}</span>
			</p>
			<div class="main-top">
				<div class="top-bar">
					<div class="search">
						<label for="name">이름</label>
						<input type="text" name="name" value="${param.name}">
						<label for="user_id">사번</label>
						<input type="text" name="user_id" value="${param.user_id}">
						<button type="submit" class="btn-orange" onclick="searchTeacher()">
							<i class="fa-solid fa-magnifying-glass"></i>
						</button>
					</div>
					<div class="btn-group">
						<button type="button" class="btn-green" onclick="location.href='/amoveInsertManyTeacher'">일괄등록</button>
						<button type="button" class="btn-blue" onclick="location.href='/amoveInsertTeacher'">등록</button>
					</div>
				</div>
			</div>
			<c:choose>
				<c:when test="${empty teacherList}">
					<p>조건에 맞는 교수가 없습니다.</p>
				</c:when>
				<c:otherwise>
					<c:forEach var="k" items="${teacherList}" varStatus="v">
						<div class="search-list-wrapper">
							<div class="search-list" id="teacher-search-list">
								<h2>
									<a href="/adetailTeacher?user_id=${k.user_id}" class="link-black">${v.count}. ${k.name}</a>
								</h2>
								<div class="a_id">
									<p>사번</p>
									<p class="submit">${k.user_id}</p>
								</div>
								<div class="count">
									<p>학과</p>
									<p class="submit">${k.dept_name}</p>								
								</div>
								<div class="count">
									<p>재직일</p>
									<p class="submit">${fn:substring(k.created_date, 0, 10)}</p>
								</div>
								<div class="count">
									<p>학과장 여부</p>
									<p class="submit">${k.t_dept_chair}</p>
								</div>
							</div>
						</div>	
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

<script type="text/javascript">
function searchTeacher() {
    const name = document.querySelector('input[name="name"]').value.trim();
    const userId = document.querySelector('input[name="user_id"]').value.trim();

    if (!name && !userId) {
        window.location.href = '/ateacherList';
        return;
    }

    let query = '/filteredTeacherList?';
    const params = [];

    if (name) params.push('name=' + encodeURIComponent(name));
    if (userId) params.push('user_id=' + encodeURIComponent(userId));

    query += params.join('&');
    window.location.href = query;
}
</script>

</body>
</html>