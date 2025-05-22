<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../headerNavi.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/headerNavi.css">
<style>
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
	select, input[type="date"], textarea {
	width: 95%;
	padding: 5px;
	border: 1px solid #ccc;
	border-radius: 4px;
	font-size: 14px;
}
input[type="file"]{
	width : 74px;
	padding: 0;
	border-radius:0;
	border: none;
}

input[type="time"] {
	width: 42.7%;
	padding: 5px;
	border: 1px solid #ccc;
	border-radius: 4px;
	font-size: 14px;
}

textarea {
	resize: vertical;
	height: 100px;
}

label {
	margin-right: 10px;
}

.btn-list {
	margin-top: 10px;
	background-color: gray;
	position: relative;
	left: 1120px;
}

hr {
	height: 2.5px;
	background-color: lightgray;
	border: none;
}

button {
	width: 80px;
	height: 38px;
	border: none;
	border-radius: 5px;
	font-size: 15px;
	color: white;
	cursor: pointer;
}

.btn-delete {
	background-color: #f44336;
}

.btn-update {
	background-color: #3f51b5;
}
</style>
</head>
<body>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>시험 출제</h1>
		</div>
		<form action="/exam-update-ok" enctype="multipart/form-data"
			method="post">
			<div class="btn-group">
				<button class="btn-update" onclick="go_exam_detail()" type="button">취소</button>
				<input type="submit" value="수정" class="btn-delete">
			</div>
			<table>
				<tr>
					<th>강의명</th>
					<td>${testMakeVO.lect_name}</td>
				</tr>
				<tr>
					<th>교수명</th>
					<td>${testMakeVO.name}</td>
				</tr>
				<tr>
					<th>시험명</th>
					<td><input type="text" name="test_subject"
						value="${testMakeVO.test_subject}" /></td>
				</tr>
				<tr>
					<th>시험구분</th>
					<td><select name="mid_final">
							<option value="0"
								<c:if test="${testMakeVO.mid_final == 0}">selected</c:if>>중간고사</option>
							<option value="1"
								<c:if test="${testMakeVO.mid_final == 1}">selected</c:if>>기말고사</option>
					</select></td>
				</tr>
				<tr>
					<th>시험날짜</th>
					<td><input type="date" name="test_date"
						value="${testMakeVO.test_date}" /></td>
				</tr>
				<tr>
					<th>시험시간</th>
					<td>시작 <input type="time" name="teststart_time"
						value="${testMakeVO.teststart_time}" /> 종료 <input type="time"
						name="testend_time" value="${testMakeVO.testend_time}" />
					</td>
				</tr>
<tr>
    <th>첨부파일</th>
    <td>
        <input type="file" name="file_name" id="fileInput">
        <span id="fileNameDisplay">
            <c:choose>
                <c:when test="${empty testMakeVO.f_name}">
                    선택된 파일 없음
                </c:when>
                <c:otherwise>
                    ${testMakeVO.f_name}
                </c:otherwise>
            </c:choose>
        </span>
        <input type="hidden" name="old_f_name" value="${testMakeVO.f_name}">
    </td>
</tr>

			</table>
			<!--  ${testMakeVO.lect_idx}  ${testMakeVO.test_make_idx} -->
			<input type="hidden" name="lect_idx" value="${lect_idx}" /> <input
				type="hidden" name="test_make_idx" value="${test_make_idx}" />
		</form>
	</div>


	<script>
		function go_exam_detail() {
			/* "/exam-detail?test_make_idx=${testMakeVO.test_make_idx}" */
			location.href = "/exam-detail?test_make_idx=${test_make_idx}&lect_idx=${lect_idx}";
		}
		document.getElementById("fileInput").addEventListener("change", function(event) {
		    var fileName = event.target.files[0] ? event.target.files[0].name : "선택된 파일 없음";
		    document.getElementById("fileNameDisplay").textContent = fileName;
		});

	</script>
</body>
</html>