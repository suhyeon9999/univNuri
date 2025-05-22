<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../headerNavi.jsp"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/headerNavi.css">
<link rel="stylesheet" type="text/css" href="/resources/css/common.css">
<style>
.highlight {
	position: relative;
	top: 45px;
	width: 400px;
	height: 13px;
	z-index: 0;
	background-color: cornflowerblue;
}
.info{
	margin-bottom: 4px;
}
hr {
	height: 2.5px;
	background-color: lightgray;
	border: none;
	margin-bottom: 4px;
}
hr {
	height: 2.5px;
	background-color: lightgray;
	border: none;
}

button {
	width: 110px;
	height: 38px;
	border: none;
	border-radius: 5px;
	font-size: 15px;
	color: white;
	cursor: pointer;
}

.info {
	display: flex;
	justify-content: space-between;
	gap: 30px;
}

.info p {
	margin: 0;
	font-weight: bold;
}

.assign-detail {
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

.submit-file {
	display: flex;
}

.submit-text {
	margin-top: 10px;
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
	width: 120px;
	left: 1030px;
}

.blue {
	color: blue;
}

.accent {
	color: red;
}
.submit-file{
	margin: 20px 0;
}
</style>
</head>
<body>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>${assignDetail.assign_title}</h1>
		</div>
		<hr />
		<div class="info">
			<p>강의명 : ${getTLecName.lect_name}</p>
			<p>학생명 : ${getName.name}</p>
			<p>
				제출일 : <span class="accent"> <fmt:formatDate
						value="${getSubmitDetail.submit_write_date}"
						pattern="yyyy-MM-dd HH:mm" />
				</span>
			</p>
			<p>
				마지막 수정일 : <span class="accent"> <fmt:formatDate
						value="${getSubmitDetail.updated_at}" pattern="yyyy-MM-dd HH:mm" />
				</span>
			</p>
		</div>
		<hr />
		<pre class="assign-detail">${assignDetail.assign_content}
      </pre>
		<hr />
		<div class="submit-file">
			<c:choose>
				<c:when test="${empty submitFiles}">
					<br/>
					<p>파일이 없습니다.</p>
				</c:when>
				<c:otherwise>
					<c:forEach items="${submitFiles}" varStatus="v" var="k">
						<li><a href="/assign-down?f_name=${k.submit_f_name}">
								${fn:substringAfter(k.submit_f_name, "_")} </a></li>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
		<hr />
		<div class="submit-text">
			<pre>${getSubmitDetail.submit_content}</pre>
		</div>
		<button class="btn-list btn-gray" onclick="go_submit_list()">제출 학생 목록</button>
	</div>
	<script>
		function go_submit_list() {
			/* assign_idx 필요 */
			location.href = "/assign-detail?assign_idx=${assign_idx}&lect_idx=${lect_idx}";
		}
	</script>
</body>
</html>