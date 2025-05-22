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
        	<h1>${assigninfo.assign_title }</h1>
        	<button onclick="sEnrollDetailSubjectUpdateOK(this.form)" class="btn-blue">수정</button>
        	<input type="hidden" name="submit_idx" value="${assigninfo.submit_idx}">
        	<input type="hidden" name="assign_idx" value="${param.assign_idx}" />
        	<input type="hidden" name="lect_idx" value="${sessionScope.lect_idx}" />
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
			    <span class="value status" style="color: blue">제출</span>
			  </div>
			</div>
      <hr>	
     	     <p>${assigninfo.assign_content}</p>
      <hr>	
<div class="main-container">

  <label style="font-weight: bold;">기존 제출 파일:</label>
  <c:choose>
    <c:when test="${not empty fileList}">
      <ul class="submitted-files">
        <c:forEach var="file" items="${fileList}">
          <li>${fn:substringAfter(file.submit_f_name, '_')}</li>
        </c:forEach>
      </ul>
    </c:when>
    <c:otherwise>
      <p>첨부된 파일이 없습니다.</p>
    </c:otherwise>
  </c:choose>

  <hr>

  <div class="file-upload-section" style="margin-top: 15px;">
    <label for="submit_file_name"><strong>파일 추가 업로드:</strong></label><br>
    <input type="file" id="submit_file_name" name="submit_file_name" multiple onchange="previewFiles()" style="margin-top: 5px;">
    <p style="font-size: 0.85em; color: gray;">※ 여러 개 선택 가능</p>

    <ul id="file-preview-list" style="margin-top: 10px; padding-left: 20px;"></ul>
  </div>

  <hr>

  <textarea rows="12" cols="158" name="submit_content">${assigninfo.submit_content}</textarea>

</div>
		</form>
		</div>
	<script type="text/javascript">
		function sEnrollDetailSubjectUpdateOK(f){
				f.action="/sEnrollDetailSubjectDetailUpdateOK";
				f.submit();
				alert("정상적으로 수정 되었습니다.");
			}
		function sEnrollDetailSubjectBack(f){
			f.action="/sEnrollSubjectDetail";
			f.submit();
		}
		function previewFiles() {
		    const input = document.getElementById("submit_file_name");
		    const previewList = document.getElementById("file-preview-list");
		    previewList.innerHTML = ""; // 초기화

		    if (input.files.length > 0) {
		      for (let i = 0; i < input.files.length; i++) {
		        const li = document.createElement("li");
		        li.textContent = input.files[i].name;
		        previewList.appendChild(li);
		      }
		    }
		  }
	</script>
</body>
</html>