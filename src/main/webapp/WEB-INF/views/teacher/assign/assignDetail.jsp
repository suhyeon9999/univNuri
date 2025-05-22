<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../headerNavi.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/headerNavi.css">
<link rel="stylesheet" type="text/css" href="/resources/css/common.css">
<style>
#modalContainer {
	width: 100vw;
	height: 100vh;
	position: fixed;
	top: 0;
	left: 0;
	display: none;
	justify-content: center;
	align-items: center;
	background: rgba(0, 0, 0, 0.5);
	z-index: 1000;
}

#modalContent {
	background-color: #ffffff;
	width: 400px;
	height: 250px;
	padding: 15px;
	text-align: center;
	border: 1px solid black;
	border-radius: 10px;
	position: relative;
	top: 0;
	left: 0;
}

h2 {
	margin: 15px;
}

.btn-group {
	display: flex;
	justify-content: center;
	gap: 30px;
	margin: 10px 0;
}

.modal-btn-group input[type="submit"], input[type="reset"] {
	margin-top: 10px;
	background-color: #3f51b5;
}

input[type="text"] {
	width: 70%;
	height: 40px;
	padding: 5px;
	border: 1px solid #ccc;
	border-radius: 4px;
	font-size: 14px;
	margin-bottom: 10px;
}

.descript {
	font-size: 12px;
	color: red;
	margin: 20px 0;
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

.btn-group button, input[type="reset"], input[type="submit"] {
	width: 80px;
	height: 38px;
	border: none;
	border-radius: 5px;
	font-size: 15px;
	color: white;
	cursor: pointer;
}

h2 {
	margin: 15px;
}

.btn-group {
	display: flex;
	justify-content: center;
	gap: 30px;
	margin: 10px 0;
}

.modal-btn-group input[type="submit"], input[type="reset"] {
	margin-top: 10px;
	background-color: #3f51b5;
}

.descript {
	font-size: 12px;
	color: red;
	margin: 20px 0;
}

.highlight {
	position: relative;
	top: 45px;
	width: 400px;
	height: 13px;
	z-index: 0;
	background-color: cornflowerblue;
}

.btn-group {
	display: flex;
	justify-content: flex-end;
	gap: 5px;
	margin-bottom: 20px;
}

hr {
	height: 2.5px;
	background-color: lightgray;
	border: none;
	margin-bottom: 4px;
}

.info {
	display: flex;
	justify-content: space-between;
}

.info p {
	margin: 0;
	font-weight: bold;
}

pre {
	margin: 30px 0;
	font-size: 17px;
}

.submit-lists {
	background-color: #f2f2f2;
	padding: 10px 20px 20px 20px;
}

.count {
	display: flex;
	gap: 10px;
}

.submit-list {
	display: flex;
	align-items: center;
	background-color: white;
	border-radius: 5px;
	display: flex;
	justify-content: space-between;
	padding: 20px;
	align-items: center;
	margin: 10px 0;
}

.submit-list p {
	margin: 2px;
}

.btn-list {
	margin-top: 10px;
	background-color: gray;
	position: relative;
	left: 1070px;
}

.blue {
	color: blue;
}

.accent {
	color: red;
}

.time {
	margin-right: 120px;
}

.name {
	color: black;
}

.info {
	margin-bottom: 4px;
}
</style>
</head>
<body>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>${assignDetail.assign_title}</h1>
		</div>
	<c:choose>
		<c:when test="${isEnd == '0' }">
		<div class="btn-group">
			<button class="btn-blue" onclick="go_assign_update()">수정</button>
			<button class="btn-red" onclick="exam_delete_modal()">삭제</button>
		</div>
		</c:when>
		<c:otherwise></c:otherwise>
	</c:choose>
		<hr />
		<div class="info">
			<p>강의명 : ${getTLecName.lect_name}</p>
			<p>교수명 : ${getTLecName.name}</p>
			<p>
				제출 기간 : <span class="accent"><fmt:formatDate
						value="${assignDetail.start_time}" pattern="yyyy-MM-dd HH:mm" />
					- <fmt:formatDate value="${assignDetail.assign_due_date}"
						pattern="yyyy-MM-dd HH:mm" /> </span>
			</p>
		</div>
		<hr />
		<pre>${assignDetail.assign_content}
      </pre>
		<div id="fileWrapper">
			<c:choose>
				<c:when test="${empty assignFiles}">
				</c:when>
				<c:otherwise>
					<c:forEach items="${assignFiles}" varStatus="v" var="k">
						<li><a class="name"  href="/assign-down?f_name=${k.assign_f_name}">
								${fn:substringAfter(k.assign_f_name, "_")} </a></li>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
		<hr />
		<div class="submit-lists">
			<div class="count">
				<p>
					제출 학생 : <span class="blue">${countSubmit}</span>
				</p>
				<p>
					미제출 학생 : <span class="accent">${countNotSubmit}</span>
				</p>
			</div>
			<c:choose>
				<c:when test="${empty submitList}">
					<p>제출된 과제가 없습니다.</p>
				</c:when>
				<c:otherwise>
					<c:forEach var="k" items="${submitList}" varStatus="v">
						<div class="submit-list">

							<!-- submit_idx 수정 필요 -->
							<h2>
								<a class="name" 
									href="/submit-detail?submit_idx=${k.submit_idx}&assign_idx=${assign_idx}&lect_idx=${lect_idx}">${v.index+1}.
									${k.name}</a>
							</h2>
							<div class="time">
								<p>제출일/시간</p>
								<p class="accent">
									<fmt:formatDate value="${k.submit_write_date}"
										pattern="yyyy-MM-dd HH:mm" />
								</p>
							</div>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
		<button class="btn-list" onclick="go_assign_list()">목록</button>
	</div>
	<div id="modalContainer">
		<div id="modalContent">
			<form action="assign-delete-ok">
				<h2>시험 삭제 확인</h2>
				<p class="descript">비밀번호를 입력하세요.</p>
				<input type="text" placeholder="비밀번호 입력" name="user_pw" />
				<!-- hidden 타입에 값 연결 필요 -->
				<input type="hidden" name="assign_idx"
					value="${assignDetail.assign_idx}">
					<input type="hidden" name="lect_idx" value="${lect_idx}">
				<div class="modal-btn-group">
					<input type="submit" value="확인" /> <input type="reset" value="취소"
						onclick="close_modal()" />
				</div>
			</form>
		</div>
	</div>

	<script>
		function go_assign_list() {
			location.href = "/assign-list?lect_idx=" + ${lect_idx};
		}
		function go_assign_update() {
			location.href = "/assign-update?lect_idx=${lect_idx}&assign_idx=${assignDetail.assign_idx}";
		}
		function exam_delete_modal() {
			document.getElementById("modalContainer").style.display = "flex";
		};

		function close_modal() {
			document.getElementById("modalContainer").style.display = "none";
		};

		window.onload = function() {
			if ("${param.pwdchk}" === 'fail') {
				alert("비밀번호가 일치하지 않습니다.");
			}
		};
	</script>
</body>
</html>