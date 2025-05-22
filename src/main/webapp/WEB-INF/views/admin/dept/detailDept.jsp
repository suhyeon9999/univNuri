<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				<h1>학과 상세보기</h1>
			</div>
			<div class="btn-group">
				<button class="btn-red"
					onclick="if(confirm('정말 삭제하시겠습니까?')) location.href='${pageContext.request.contextPath}/deleteDept?dept_idx=${detailDept.dept_idx}'">삭제</button>
				<button class="btn-blue" 
					onclick="location.href='${pageContext.request.contextPath}/moveUpdateDept?dept_idx=${detailDept.dept_idx}'">수정</button>				
			</div>
			<table>
				 <tr>
		        	<th>학과명</th>
		            <td>${detailDept.dept_name }</td>
		         </tr>		                       
				 <tr>
		        	<th>학과번호</th>
		            <td>${detailDept.dept_id }</td>
		         </tr>		                       
		         <tr>
					<th>학과장</th>
						<td>	
							<c:choose>
								<c:when test="${empty detailDept.t_idx}">
									<p>미배정</p>
								</c:when>
								<c:otherwise>
									<p class="submit">${detailDept.name}</p>									
								</c:otherwise>
							</c:choose>															
					</td>
				</tr>		                       
			</table>
			<div class="btn-group">
				<button class="btn-gray" onclick="location.href='/deptList'">목록</button>
			</div>
		</div>
	</div>
</body>
</html>
