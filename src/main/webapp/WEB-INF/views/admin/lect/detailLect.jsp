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
				<button class="btn-red">삭제</button>
				<button class="btn-blue">수정</button>				
			</div>
			<table>
				 <tr>
		                <th>강의명</th>
		                <td>${detailLect.lect_name }</td>
		            </tr>
		            <tr>
		                <th>강의코드</th>
		                <td>${detailLect.lect_id }</td>
		            </tr>
		            <tr>
		                <th>개설학과</th>
		                <td>${detailLect.dept_name }</td>
		            </tr>
		            <tr>
		                <th>과목구성</th>
		                <td>
		                	<c:choose>
			                	<c:when test="${empty lectSubSet} ">과목군 미배정</c:when>
			                	<c:otherwise>
				                	<c:forEach var="k" items="${lectSubSet}">
				                		${k.subject_name}
				                	</c:forEach>
			                	</c:otherwise>
		                	</c:choose>
		                </td>
		            </tr>	
		            <tr>
		                <th>이수학점</th>
		                <td>${detailLect.lect_credit }</td>
		            </tr>
		            <tr>
		                <th>담당교수</th>
		                <td>
		                	<c:choose>
			                	<c:when test="${empty detailLect.name} ">담당교수 미배정</c:when>
			                	<c:otherwise>
				                	${detailLect.name }
			                	</c:otherwise>
		                	</c:choose>
		                </td>
		            </tr>
		            <tr>
		                <th>수강정원</th>
		                <td>${detailLect.lect_max }</td>
		            </tr>
		            <tr>
		                <th>강의실</th>
		                <td>
		                	<c:choose>
			                	<c:when test="${empty detailLect.class_building or empty detailLect.class_room} ">강의실 미정</c:when>
			                	<c:otherwise>
				                	${detailLect.class_building} ,${detailLect.class_room }호
			                	</c:otherwise>
		                	</c:choose>
		                </td>
		            </tr>
		            <tr>
		                <th>강의요일</th>
		                <td>
		                	<c:choose>
			                	<c:when test="${empty detailLect.lect_day} ">강의요일 미정</c:when>
			                	<c:otherwise>
				                	${detailLect.lect_day } 
			                	</c:otherwise>
		                	</c:choose>
		                </td>
		            </tr>
		            <tr>
		                <th>강의시간</th>
		                <td>
		                	<c:choose>
			                	<c:when test="${empty detailLect.lect_start_time or empty detailLect.lect_end_time} ">강의시간 미정</c:when>
			                	<c:otherwise>
				                	${detailLect.lect_start_time } - ${detailLect.lect_end_time }
			                	</c:otherwise>
		                	</c:choose>
		                </td>
		            </tr>		           		            
			</table>
			<div class="btn-group">
				<button class="btn-gray">목록</button>
			</div>
		</div>
	</div>
</body>
</html>
