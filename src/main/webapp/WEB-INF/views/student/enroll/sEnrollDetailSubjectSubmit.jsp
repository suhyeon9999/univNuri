<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        	<button onclick="sEnrollDetailSubjectOK(this.form)" class="btn-blue">제출</button>
        	<button onclick="sEnrollDetailSubjectBack(this.form)" class="btn-red">취소</button>
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
			    <span class="value status" style="color: red">미제출</span>
			  </div>
			</div>
      <hr>	
      	<p>${assigninfo.assign_content}</p>
      <hr>	
      	<div class="main-container">
	     	 <input type="file" name="submit_file_name" id="submit_file_name" multiple style="border: none; width: 800px;">
	     	 <div id="fileNameList" style="margin-top: 10px;"></div>
			 <hr>
			 <textarea rows="12" cols="158" name="submit_content"></textarea>			 
   	    </div>
		</form>
      </div>
	<script type="text/javascript">
		function sEnrollDetailSubjectOK(f){
				f.action="/sEnrollDetailSubjectSubmitOK";
				f.submit();
				alert("정상적으로 제출 되었습니다.");
			}
		function sEnrollDetailSubjectBack(f){
			f.action="/sEnrollDetail?lect_idx="+${sessionScope.lect_idx};
			f.submit();
		}
		document.getElementById("submit_file_name").addEventListener("change", function (event) {
		    const files = event.target.files;
		    const fileNameList = document.getElementById("fileNameList");
		    fileNameList.innerHTML = ""; // 초기화

		    if (files.length === 0) {
		        fileNameList.innerHTML = "<p>선택된 파일이 없습니다.</p>";
		        return;
		    }

		    const ul = document.createElement("ul");
		    ul.style.listStyleType = "disc";
		    ul.style.paddingLeft = "20px";

		    for (let i = 0; i < files.length; i++) {
		        const li = document.createElement("li");
		        li.textContent = files[i].name;
		        ul.appendChild(li);
		    }

		    fileNameList.appendChild(ul);
		});
	</script>
</body>
</html>