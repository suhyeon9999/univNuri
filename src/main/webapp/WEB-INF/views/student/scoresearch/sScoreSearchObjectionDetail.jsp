<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../../headerNavi.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>성적관리</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/student/sScoreSearchObjectionInsert.css">
</head>
<body>
<div id="wrap">
  <form method="get">
    <div class="title-container">
      <div class="highlight"></div>
      <h1>이의제기 조회</h1>
      <input type="hidden" value="${objectionList.lect_idx}">
      <button type="button" onclick="location.href='/sScoreSearchObjectionDetailUpdate?objection_idx=${objectionList.objection_idx}&lect_idx=${objectionList.lect_idx}'" class="btn-blue">수정</button>
    </div>

    <c:if test="${not empty objectionList}">
      <table>
       <tr>
			 <th>학년도/학기</th>
			  <td>
			    <fmt:formatDate value="${objectionList.apply_date}" pattern="yyyy" />-
			    <fmt:formatDate value="${objectionList.apply_date}" pattern="MM" var="month"/>
			    <c:choose>
			      <c:when test="${month le '06'}">1</c:when>
			      <c:otherwise>2</c:otherwise>
			    </c:choose>
			  </td>
			</tr>
        <tr>
          <th>강의</th>
          <td>${objectionList.lect_name}</td>
        </tr>
        <tr>
          <th>교수명</th>
          <td>${objectionList.professor_name}</td>
        </tr>
        <tr>
          <th>신청일</th>
          <td><fmt:formatDate value="${objectionList.apply_date}" pattern="yyyy-MM-dd"/></td>
        </tr>
        <tr>
          <th>내용</th>
          <td>
            <textarea name="objection_content" rows="12" cols="100" readonly>${objectionList.objection_content}</textarea>
          </td>
        </tr>
      </table>
    </c:if>

    <button type="button" onclick="location.href='/sScoreSearch'" style="float: right;">
      이의제기 목록
    </button>
  </form>
</div>
</body>
</html>
