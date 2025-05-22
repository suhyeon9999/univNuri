<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/student/sScoreSearchObjectionManagement.css">
<meta charset="UTF-8">
<title>성적관리</title>
</head>
<body>
<%@include file="../../headerNavi.jsp"%>
<div id="wrap">
	  <div class="title-container">
        <div class="highlight"></div>
        <h1>이의제기 관리</h1>
      </div>
	<table>
		<thead>
			<tr>
				<th>No.</th>
				<th>학기</th>
				<th>강의</th>
				<th>교수명</th>
				<th>신청일</th>
				<th>상태</th>
			</tr>
		</thead>
		<tbody>
  <!-- 실제로 출력된 row 수를 세기 위한 변수 -->
  <c:set var="visibleCount" value="0" />

  <!-- 전체 objectionList를 반복 -->
  <c:forEach var="obj" items="${objectionList}" varStatus="vs">
    <!-- objection_active == 0 인 경우만 출력 -->
    <c:if test="${obj.objection_active == 0}">
      <!-- 출력된 row 수 증가 -->
      <c:set var="visibleCount" value="${visibleCount + 1}" />
      <tr>
        <td>${vs.index + 1}</td>
        <td>
          <fmt:formatDate value="${obj.apply_date}" pattern="yyyy"/>-
          <c:choose>
            <c:when test="${fn:substring(obj.apply_date, 5, 7) le '06'}">1</c:when>
            <c:otherwise>2</c:otherwise>
          </c:choose>
        </td>
        <td>${obj.lect_name}</td>
        <td>${obj.professor_name}</td>
        <td><fmt:formatDate value="${obj.apply_date}" pattern="yyyy-MM-dd"/></td>
        <td>
          <c:choose>
            <c:when test="${obj.approve_objection == 0}">
              <span style="color: blue;">승인</span>
            </c:when>
            <c:when test="${obj.approve_objection == 1}">
              <span style="color: red;">반려</span>
            </c:when>
            <c:otherwise>
              <span style="color: black;">대기</span>
            </c:otherwise>
          </c:choose>
        </td>
      </tr>
    </c:if>
  </c:forEach>
  <c:if test="${visibleCount == 0}">
    <tr>
      <td colspan="6" style="font-size: 40px;">이의제기가 없습니다.</td>
    </tr>
  </c:if>
</tbody>
	</table>
</div>
</body>
</html>