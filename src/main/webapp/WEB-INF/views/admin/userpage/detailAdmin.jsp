<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/commonUser.css">
</head>
<body>
<%@ include file="../../headerNavi.jsp"%>
	<div id="wrap">
		<div class="total-container">
			<div class="title-container">
				<div class="highlight"></div>
				<h1>관리자 상세보기</h1>
			</div>
			<div class="btn-group">
				<button class="btn-red"
					onclick="if(confirm('정말 삭제하시겠습니까?')) location.href='${pageContext.request.contextPath}/deleteAdmin?user_idx=${detailAdmin.user_idx}'">삭제</button>
				<button class="btn-blue" 
					onclick="location.href='/moveUpdateAdmin?user_idx=${detailAdmin.user_idx}'">수정</button>		
			</div>
			<table>
				 <tr>
		                <th>이름</th>
		                <td>${detailAdmin.name }</td>
		            </tr>
		            <tr>
		                <th>생년월일</th>
		                <td>${detailAdmin.birth }</td>
		            </tr>
		            <tr>
		                <th>사번</th>
		                <td>${detailAdmin.user_id }</td>
		            </tr>
		            <tr>
		                <th>재직상태</th>
		                <td>
		                	<c:choose>
									<c:when test="${detailAdmin.status == '4'}">
							            <p class="submit">재직</p>
							        </c:when>
							        <c:when test="${detailAdmin.status == '5'}">
							            <p class="submit">퇴직</p>
							        </c:when>							     
							</c:choose>	
		                </td>
		            </tr>	
		            <tr>
		                <th>이메일</th>
		                <td>${detailAdmin.email }</td>
		            </tr>
		            <tr>
		                <th>휴대폰 번호</th>
		                <td>${detailAdmin.phone }</td>
		            </tr>		           
		            <tr>
		                <th>관리자 등급</th>
		                <td>
		                	<c:choose>
									<c:when test="${detailAdmin.a_grade == '0'}">
							            <p class="submit">최고관리자</p>
							        </c:when>
							        <c:when test="${detailAdmin.a_grade == '1'}">
							            <p class="submit">중간관리자</p>
							        </c:when>
							        <c:when test="${detailAdmin.a_grade == '2'}">
							            <p class="submit">일반관리자</p>
							        </c:when>
								</c:choose>
		                </td>
		            </tr>		            
			</table>
			<div class="btn-group">
				<button class="btn-gray" onclick="location.href='adminList'">목록</button>
			</div>
		</div>
	</div>
</body>
</html>
