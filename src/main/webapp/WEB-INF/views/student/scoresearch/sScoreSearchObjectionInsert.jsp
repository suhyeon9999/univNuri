<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../../headerNavi.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/student/sScoreSearchObjectionInsert.css">
<meta charset="UTF-8">
<title>성적관리</title>
</head>
<body>
	<div id="wrap" style="position: relative;">
        <form method="post">
		<div class="title-container">
       	 <div class="highlight"></div>
        	<h1>이의제기 신청</h1>
        	<button type="button" onclick="sScoreSearchObjectionInsertOK(this.form)" class="btn-blue">등록</button>
        	<button type= "button" onclick="sScoreSearchObjectionInsertBack(this.form)" class="btn-red">취소</button>
        	<input type="hidden" name="objection_idx" value="${objectionList[0].objection_idx}" />
        	<input type="hidden" name="lect_idx" value="${param.lect_idx }" />
        	
        </div>
			<c:if test="${not empty objectionList[0]}">
        <table>
          <tr>
            <th>학년도/학기</th>
            <td>
              <fmt:formatDate value="${objectionList[0].apply_date}" pattern="yyyy" />-
              <c:choose>
                <c:when test="${fn:substring(objectionList[0].apply_date, 5, 7) le '06'}">1</c:when>
                <c:otherwise>2</c:otherwise>
              </c:choose>
            </td>
          </tr>
          <tr>
            <th>강의</th>
            <td>${objectionList[0].lect_name}</td>
          </tr>
          <tr>
            <th>교수명</th>
            <td>${objectionList[0].professor_name}</td>
          </tr>
          <tr>
            <th>신청일</th>
            <td><fmt:formatDate value="<%= new java.util.Date() %>" pattern="yyyy-MM-dd" /></td>
          </tr>
          <tr>
            <th>내용</th>
            <td>
              <textarea name="objection_content" rows="12" cols="100"></textarea>
            </td>
          </tr>
        </table>
      </c:if>
	<button onclick="location.href='/sScoreSearch'" type="button" style="float: right; background-color: gray; color: white; width: 150px; position: absolute; top: 930px; left:89.4%;">이의제기 목록</button>
	</form>
    </div>
	<script type="text/javascript">
		function sScoreSearchObjectionInsertOK(f){
			const chk = confirm("등록 하시겠습니까?");
			if(chk){
			const pwd = prompt("비밀번호를 입력해 주세요.");
			if(pwd == ${sessionScope.uvo.user_pw}){
			f.action="/sScoreSearchObjectionDetailInsertOK";
			f.submit()
			alert("정상적으로 등록 되었습니다.");
			}else{
				alert("비밀번호를 확인해 주세요.")
			}
		}
		}
		function sScoreSearchObjectionInsertBack(f){
			f.action="/sScoreSearch";
			f.submit();
		}
	</script>
</body>
</html>