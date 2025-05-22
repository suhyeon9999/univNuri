<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/commonBoard.css">
<meta charset="UTF-8">
<title>1:1문의사항 리스트(학생)</title>

</head>
<body>
	<%@ include file="../../headerNavi.jsp"%>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>1:1문의</h1>
		</div>
		<div class="main-container">
			<form action="/aBoardListFiltered" method="get">
				<ul class="selector">
					<li><label for="student_name">문의자</label> <input type="text"
						id="student_name" name="student_name"
						value="${param.student_name}"></li>
					<li><label for="student_user_id">학번</label> <input type="text"
						id="student_user_id" name="student_user_id"
						value="${param.student_user_id}"></li>
					<li><label for="title">문의 제목</label> <input type="text"
						id="title" name="title" value="${param.title}"></li>
				</ul>
				<ul class="selector">
					<li><label for="board_staus">처리상태</label> <select
						id="board_staus" name="board_staus">
							<option value=""
								<c:if test="${param.board_staus == ''}">selected</c:if>>전체보기</option>
							<option value="미답변"
								<c:if test="${param.board_staus == '미답변'}">selected</c:if>>미답변</option>
							<option value="답변완료"
								<c:if test="${param.board_staus == '답변완료'}">selected</c:if>>답변완료</option>
					</select></li>
					<li><label for="answer_date">처리일</label> <input type="date"
						id="answer_date" name="answer_date" value="${param.answer_date}">
					</li>
					<li><label for="admin_name">처리자</label> <input type="text"
						id="admin_name" name="admin_name" value="${param.admin_name}">
						<div class="btn-group">
							<button type="submit" class="btn-blue" style="margin-left: 40px">검색</button>
						</div></li>
				</ul>
			</form>
			<div>
				<div class="table-container">
					<table>
						<thead>
							<tr>
								<th>No.</th>
								<th>문의일</th>
								<th>이름</th>
								<th>학번</th>
								<th>제목</th>
								<th>처리상태</th>
								<th>처리자</th>
								<th>답변일</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${empty boardList}">
								<tr>
									<td colspan="8" style="text-align: center;">조건에 맞는 게시글이
										없습니다.</td>
								</tr>
							</c:if>
							<c:forEach var="k" items="${boardList}" varStatus="v">
								<tr>
									<td>${fn:length(boardList) - v.count + 1}</td>
									<!-- 최신순 넘버링 -->
									<td>${fn:substring(fn:replace(k.write_date, 'T', ' '), 0, 16)}</td>
									<td>${k.student_name }</td>
									<td>${k.student_user_id }</td>
									<td><a href="aBoardDetail?board_idx=${k.board_idx}"
										class="link-subject">${k.title }</a></td>
									<td
										class="${k.answer_status == '답변대기' ? 'status-wait' : 'status-complete'}">${k.answer_status}</td>
									<td><c:choose>
											<c:when test="${k.answer_status == '답변대기'}">-</c:when>
											<c:otherwise>${k.admin_name}</c:otherwise>
										</c:choose></td>
									<td><c:choose>
											<c:when test="${k.answer_status == '답변대기'}">-</c:when>
											<c:otherwise>${k.answer_date}</c:otherwise>
										</c:choose></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>