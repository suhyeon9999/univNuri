<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page
	import="java.util.List, java.util.Map, java.util.ArrayList, java.util.HashMap"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
			<h1>관리자 관리</h1>
		</div>
		<div class="main-container">
			<p class="count">
				전체 관리자: <span>${adminList.size()}</span>
			</p>
			<div class="main-top">
					<div class="top-bar">
				<form action="/searchAdmin" method="get">
						<div class="search">
							 <label for="a_grade">관리자 등급</label>
                                <select name="a_grade" id="a_grade">                                    
                                    <option value="" selected >전체관리자</option>
                                    <option value="2"  >일반관리자</option>
                                    <option value="1">중간관리자</option>
                                    <option value="0">최고관리자</option>
                                </select>
							<label for="name">이름</label>
							 <input type="text" name="name">
							<button type="submit" class="btn-orange">
								<i class="fa-solid fa-magnifying-glass" ></i>
							</button>
						</div>
				</form>
						<div class="btn-group">
								<button class="btn-green" id="list-btn"  
								onclick="location.href='/adminList'">전체 관리자 보기</button>
							<button type="button" class="btn-blue"
								 onclick="location.href='/moveInsertAdmin'">등록</button>
						</div>
					</div>
			</div>
			<c:choose>
				<c:when test="${empty adminList}">
					<p>조건에 맞는 관리자가 없습니다.</p>
				</c:when>
				<c:otherwise>
					<c:forEach var="k" items="${adminList}" varStatus="v">
					<div class="search-list-wrapper">
						<div class="search-list" id="admin-search-list">
							<h2>
								<a href="/detailAdmin?user_idx=${k.user_idx }" class="link-black">${v.count}. ${k.name}</a>
							</h2>
							<div class="a_id">
								<p>사번</p>
								<p class="submit">${k.user_id }</p>
							</div>
							<div class="count">
								<p>등급</p>
								<c:choose>
									<c:when test="${k.a_grade == '0'}">
							            <p class="submit">최고관리자</p>
							        </c:when>
							        <c:when test="${k.a_grade == '1'}">
							            <p class="submit">중간관리자</p>
							        </c:when>
							        <c:when test="${k.a_grade == '2'}">
							            <p class="submit">일반관리자</p>
							        </c:when>
								</c:choose>								
							</div>
							<div class="count">
								<p>재직일</p>
								<p class="submit">${fn:substring(k.created_date, 0, 10)}</p>
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