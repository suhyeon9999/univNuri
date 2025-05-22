<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
			<h1>학과관리</h1>
		</div>
		<div class="main-container">
			<p class="count">
				전체 학과: <span>${deptList.size()}</span>
			</p>
			<div class="main-top">
					<div class="top-bar">
				<form action="/searchDept" method="get">
						<div class="search">
							<select id="searchType" name="searchType">
								<option value="dept_name">학과명</option>
								<option value="t_name">학과장</option>
							</select> 
							<input type="text" name="keyword"  required>
							<button type="submit" class="btn-orange">
								<i class="fa-solid fa-magnifying-glass" ></i>
							</button>
						</div>
				</form>
						<div class="btn-group">
							<button class="btn-green" id="long-btn"  
								onclick="location.href='/searchNullTIdx'">학과장 미배정 학과 보기</button>
							<button class="btn-green" id="list-btn"  
								onclick="location.href='/deptList'">전체 학과 보기</button>
							<button class="btn-blue"
								onclick="location.href='${pageContext.request.contextPath}/moveInsertDept'">등록</button>
						</div>
					</div>
			</div>
			<c:choose>
				<c:when test="${empty deptList}">
					<p>조건에 맞는 학과가 없습니다.</p>
				</c:when>
				<c:otherwise>
					<c:forEach var="k" items="${deptList}" varStatus="v">
				<div class="search-list-wrapper">
					<div class="search-list" id="dept-search-list">
						<h2>
							<a href="/detailDept?dept_idx=${k.dept_idx }"  class="link-black">${v.count}. ${k.dept_name}</a>
						</h2>
						<div class="dept_t">
							<p>학과장</p>
							<c:choose>
								<c:when test="${empty k.t_idx}">
									<p>미배정</p>
								</c:when>
								<c:otherwise>
									<p class="submit">${k.name}</p>									
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>