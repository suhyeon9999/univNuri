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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/student/sEnrollDetailSubject.css">
<meta charset="UTF-8">
<title>수강 관리</title>
</head>
<body>
<%@include file="../../headerNavi.jsp"%>
<div id="wrap">
       <form method="post" enctype="multipart/form-data">
		<div class="title-container">
       	 <div class="highlight"></div>
        	<h1>${assigninfo.assign_title}</h1>
        	<button onclick="sEnrollDetailSubjectUpdate(this.form)" class="btn-blue">수정하기</button>
        	<input type="hidden" name="submit_idx" value="${assigninfo.submit_idx}">
        	<input type="hidden" name="assign_idx" value="${param.assign_idx}" />
        </div>
        <hr>
			<div class="info-bar" style="font-weight: bold;">
			  <div class="info-item">
			    <span class="label">강의명 :</span>
			    <span class="value" style="color: black;">${assigninfo.lect_name}</span>
			  </div>
			  <div class="info-item">
			    <span class="label">교수명 :</span>
			    <span class="value" style="color: black;">${assigninfo.professor_name}</span>
			  </div>
			  <div class="info-item">
			    <span class="label">마감기한 :</span>
			    <span class="value deadline" style="color: orange;">${assigninfo.assign_due_date}</span>
			  </div>
			  <div class="info-item">
			    <span class="label">제출상태 :</span>
			    <span class="value status" style="color: blue;">제출</span>
			  </div>
			</div>
      <hr>	
      	<p>${assigninfo.assign_content}</p>
      <hr>	
		<div class="main-container">
			 <c:choose>
			    <c:when test="${not empty fileList}">
			      <c:forEach var="file" items="${fileList}">
			        <div class="filename">
			          <a style="color: black;" href="${pageContext.request.contextPath}/download?fileName=${file.submit_f_name}&originalName=${file.submit_f_old_name}">
			            ${fn:substringAfter(file.submit_f_name, '_')}
			          </a>
			        </div>
			      </c:forEach>
			    </c:when>
			    <c:otherwise>
			      <div class="filename">첨부된 파일이 없습니다.</div>
			    </c:otherwise>
			  </c:choose>
			 <hr>
			 <textarea rows="12" cols="158" name="submit_content" readonly>${assigninfo.submit_content}</textarea>			 
   	    </div>
		</form>
		</div>
<script type="text/javascript">
	function sEnrollDetailSubjectUpdate(f) {
		// 서버에서 JSTL 변수로 마감기한 넘겨받음
		const deadlineStr = "${assigninfo.assign_due_date}".replace(' ', 'T');  // ISO 형식으로 변환
		const deadline = new Date(deadlineStr);
		const now = new Date();

		if (now > deadline) {
			alert("수정이 불가능합니다. 마감 시간이 지났습니다.");
			return false; // 폼 제출 막기
		}

		// 마감 전이면 정상 제출
		f.action = "/sEnrollDetailSubjectUpdate?lect_idx=${lect_idx}&assign_idx=${param.assign_idx}";
		f.submit();
	}
</script>
</body>
</html>