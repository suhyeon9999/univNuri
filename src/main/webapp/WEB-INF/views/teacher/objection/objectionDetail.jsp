<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>교수의 성적관리</title>

<%@ include file="../../headerNavi.jsp"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/header_navi.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<style type="text/css">

      #top-container{
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 5px;
      }
      .btn-group{
        display: flex;
        justify-content: right;
        align-items: center;
        margin-top: 10px;
      }
      button{
        width: 180px;
      }

      table {
        border-collapse: collapse;
        width: 100%;
        background-color: white;
      }
      th {
        background-color: #e2e2f8;
        width: 30%;
      }
      th,
      td {
        border: 1px solid black;
        padding: 12px;
        text-align: left;
        text-align: center;
      }

    </style>
  </head>
  <body>
    <div id="wrap">

    <form method="post" action="/objection-update" >
        <div id="top-container">
          <div class="title-container">
            <div class="highlight"></div>
            <h1>이의제기 관리</h1>
          </div>

          <button type="submit" class="btn-orange">성적조회 및 수정</button>
        </div>
   <input type="hidden" name="enroll_idx" value="${objectionMap.enroll_idx}"> 
   <input type="hidden" name="objection_idx" value="${objectionMap.objection_idx}"> 
   
   </form>
  <table>
    <tr>
      <th>학생명</th>
      <td>${objectionMap.name}</td>
    </tr>
    <tr>
      <th>학번</th>
      <td>${objectionMap.user_id}</td>
    </tr>
    <tr>
      <th>학과</th>
      <td>${objectionMap.dept_name}</td>
    </tr>
    <tr>
      <th>강의</th>
      <td>${objectionMap.lect_name}</td>
    </tr>
    <tr>
      <th>처리상태</th>
      <td>
       <c:choose>
        <c:when test="${objectionMap.objection_status == 0}">대기</c:when>
         <c:when test="${objectionMap.objection_status == 1}">승인</c:when>
           <c:otherwise>반려</c:otherwise>
          </c:choose></td>

    </tr>
    <tr>
      <th>내용</th>
        <td>${objectionMap.objection_content}</td>
    </tr>
  </table>
  <div class="btn-group">
  <button type="button" onclick="go_objection_list()" class="btn-gray">이의 제기 목록</button>
  </div>
 </div>
<script>
	function go_objection_list() {
		location.href = "/objection-main";
	}
	
	</script>
	
	</body>
	
	</html>