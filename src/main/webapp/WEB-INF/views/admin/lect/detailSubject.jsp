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
					onclick="if(confirm('정말 삭제하시겠습니까?')) location.href='${pageContext.request.contextPath}/deleteSubject?subject_idx=${detailSubject.subject_idx}'">삭제</button>
			</div>
			<table>
				 <tr>
		                <th>과목명</th>
		                <td>${detailSubject.subject_name }</td>
		            </tr>
		            <tr>
		                <th>학과</th>
		                <td>${detailSubject.dept_name }</td>
		            </tr>		         			       		            
			</table>
			<div class="btn-group">
				<button class="btn-gray" onclick="location.href='/subjectList'">목록</button>
			</div>
		</div>
	</div>
</body>
</html>
