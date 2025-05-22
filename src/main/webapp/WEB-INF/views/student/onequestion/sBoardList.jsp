<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	<%@include file="../../headerNavi.jsp"%>
	
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>1:1문의</h1>
		</div>
		<div class="btn-group">				
				<button class="btn-blue" onclick="location.href='/sBoardWrite?s_idx=${sessionScope.sInfo.s_idx}'">등록</button>
		</div>
		<table>
			<thead>
				<tr>
					<th>No.</th>
					<th>문의일</th>
					<th>제목</th>
					<th>처리상태</th>
					<th>답변일</th>
				</tr>
			</thead>
			<tbody>
    <c:forEach var="k" items="${boardList}" varStatus="v">
        <tr>
            <td>${fn:length(boardList) - v.count + 1}</td> <!-- 최신순 넘버링 -->
            <td>${k.write_date}</td>
            <c:choose>
                <c:when test="${k.board_active == 1}">
                <!-- 삭제된거임 -->
                </c:when>
                <c:otherwise>
                	<!--  이건 진짜 쓸거
                    <td><a href="/sBoardDetail?b_idx=${k.b_idx}" class="link-subject">${k.subject}</a></td>
                	-->
                	<!-- 아래는 테스트용 -->
                    <td><a href="/sBoardDetail?board_idx=${k.board_idx}" class="link-subject">${k.title}</a></td>
                </c:otherwise>
            </c:choose>
            <td class="${k.answer_status == '답변대기' ? 'status-wait' : 'status-complete'}">${k.answer_status}</td>
            <td>${k.answer_date}</td>
        </tr>
    </c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>