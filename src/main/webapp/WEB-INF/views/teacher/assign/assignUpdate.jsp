<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../headerNavi.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>과제 수정</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/headerNavi.css">
<link rel="stylesheet" type="text/css" href="/resources/css/common.css">
<style>
.total-container {
	margin: 20px;
	max-width: 900px;
}

.title-container {
	position: relative;
}

.highlight {
	position: relative;
	top: 45px;
	width: 200px;
	height: 13px;
	z-index: 0;
	background-color: cornflowerblue;
}

.title-container h1 {
	position: relative;
	z-index: 1;
	margin: 0;
}

.btn-group {
	display: flex;
	justify-content: flex-end;
	gap: 5px;
	margin-bottom: 20px;
}

.btn-group button, input[type="submit"] {
	width: 80px;
	height: 38px;
	border: none;
	border-radius: 5px;
	font-size: 15px;
	color: white;
	cursor: pointer;
}

.btn-cancel {
	background-color: #f44336;
}

.btn-submit {
	background-color: #3f51b5;
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

th, td {
	border: 1px solid black;
	padding: 12px;
	text-align: center;
}

input[type="text"], input[type="datetime-local"], input[type="file"],
	textarea {
	width: 95%;
	padding: 5px;
	border: 1px solid #ccc;
	border-radius: 4px;
	font-size: 14px;
}

textarea {
	resize: vertical;
	height: 100px;
}

.file-inputs {
	display: flex;
	flex-direction: column;
}

.file-group {
	margin-bottom: 3px;
}

.remove-btn, #add-file-btn {
	padding: 5px;
	border: 1px solid #ccc;
	border-radius: 4px;
	font-size: 14px;
	background-color: #404cac;
	height: 38px;
	border-radius: 5px;
	border: none;
}

.remove-btn {
	width: 100px;
}

#add-file-btn {
	margin-top: 10px;
	width: 95%;
}

input[type="file"] {
	width: 81%;
	padding: 5px;
	border: 1px solid #ccc;
	border-radius: 4px;
	font-size: 14px;
}

label {
	margin-right: 10px;
}

input[type="file"] {
	width: 74px;
	padding: 0;
	border-radius: 0;
	border: none;
}

.file-add {
	display: flex;
	align-content: space-between;
}

#file-name {
	border: 1px solid #ccc;
	padding: 2px;
	display: flex;
	width: 82%;
	margin-left: 20px;
	margin-bottom: 2px;
}
</style>
</head>
<body>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>과제 출제</h1>
		</div>

		<!-- JSTL 포맷 처리 -->
		<fmt:formatDate value="${assignVO.start_time}"
			pattern="yyyy-MM-dd'T'HH:mm" var="formattedStart" />
		<fmt:formatDate value="${assignVO.assign_due_date}"
			pattern="yyyy-MM-dd'T'HH:mm" var="formattedDue" />

		<form method="post" action="/assign-update-ok"
			enctype="multipart/form-data">
			<div class="btn-group">
				<button type="button" onclick="go_assign_detail()" class="btn-red">취소</button>
				<input type="submit" value="완료" class="btn-blue" />
			</div>

			<input type="hidden" name="lect_idx" value="${lect_idx}" /> <input
				type="hidden" name="assign_idx" value="${assignVO.assign_idx}" />

			<table>
				<tr>
					<th>강의명</th>
					<td>${getTLecName.lect_name}</td>
				</tr>
				<tr>
					<th>교수명</th>
					<td>${getTLecName.name}</td>
				</tr>
				<tr>
					<th>과제 제목</th>
					<td><input type="text" name="assign_title"
						value="${assignVO.assign_title}" required /></td>
				</tr>
				<tr>
					<th>제출 시작일</th>
					<td><input type="datetime-local" name="start_time"
						value="${formattedStart}" required /></td>
				</tr>
				<tr>
					<th>제출 마감일</th>
					<td><input type="datetime-local" name="assign_due_date"
						value="${formattedDue}" required /></td>
				</tr>

				<tr>
					<th>첨부파일</th>
					<td>
						<div class="file-inputs">
							<c:choose>
								<c:when test="${empty assignFiles}">
									<p>첨부파일이 없습니다.</p>
								</c:when>
								<c:otherwise>
									<!-- 기존 파일들 -->
									<c:forEach items="${assignFiles}" varStatus="v" var="k">
										<div class="file-group">
											
											<button type="button" class="remove-btn">목록 삭제</button>
											<input type="hidden" name="existing_file"
												value="${k.assign_f_name}" /> <input type="hidden"
												name="existing_file_old_name" value="${k.assign_f_old_name}" />

											<span>${fn:substringAfter(k.assign_f_name, "_")}</span>
										</div>
									</c:forEach>


								</c:otherwise>
							</c:choose>
						</div>
						<button type="button" id="add-file-btn">＋파일 추가</button>
					</td>
				</tr>

				<tr>
					<th>과제 설명 및 내용</th>
					<td><textarea name="assign_content">${assignVO.assign_content}</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<script>
    function go_assign_detail() {
      location.href = "/assign-detail?assign_idx=${assignVO.assign_idx}&lect_idx=${lect_idx}";
    }

    document.addEventListener("DOMContentLoaded", function () {
    	  const addBtn = document.getElementById("add-file-btn");
    	  const container = document.querySelector(".file-inputs");

    	  addBtn.addEventListener("click", function () {
    	    const group = document.createElement("div");
    	    group.className = "file-group";
    	    group.innerHTML = `
    	      <input type="file" name="files" />
    	      <button type="button" class="remove-btn">목록 삭제</button>
    	    `;
    	    container.appendChild(group);
    	  });

    	  container.addEventListener("click", function (e) {
    	    if (e.target.classList.contains("remove-btn")) {
    	      const group = e.target.parentElement;
    	      const hidden = group.querySelector("input[type='hidden']");
    	      if (hidden) hidden.value = '';
    	      group.remove();
    	    }
    	  });
    	});
    document.querySelector(".file-inputs").addEventListener("click", function (e) {
    	  if (e.target.classList.contains("remove-btn")) {
    	    e.target.parentElement.remove(); 
    	  }
    	});

  </script>
</body>
</html>
