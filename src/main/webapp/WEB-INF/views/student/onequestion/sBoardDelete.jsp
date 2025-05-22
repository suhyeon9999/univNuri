<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../headerNavi.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>1:1문의 작성 (학생)</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/commonBoard.css">
</head>
<body>
	<div id="wrap">
		<div class="total-container">
			<div class="title-container">
				<div class="highlight"></div>
				<h1>1:1문의</h1>
			</div>
			<div class="btn-group">
				<!-- 관리자가 답변 작성시 수정버튼 비활성화 처리해야함 -->
				<c:if test="${boardDetail['answer_status'] == '답변대기'}">
				<button class="btn-blue" onclick="sBoard_update()">수정</button>
				<button class="btn-red" onclick="sBoard_delete()">삭제</button>
				</c:if>
			</div>
			<table>
				<tr>
					<th class="board-detail-th">제목</th>
					<td class="board-detail-td">${boardDetail['title']}</td>
				</tr>
				<tr>
					<th class="board-detail-th">작성일</th>
					<td class="board-detail-td">${boardDetail['write_date'] }</td>
				</tr>
				<tr>
					<th class="board-detail-th">이름</th>
					<td class="board-detail-td">${boardDetail['writer_name'] }</td>
				</tr>
				<tr>
					<th class="board-detail-th">내용</th>
					<td class="board-detail-td">${boardDetail['content'] }</td>
				</tr>
				<tr>
					<th class="board-detail-th">처리상태</th>
					<td class="board-detail-td ${boardDetail['answer_status'] == '답변대기' ? 'status-wait' : 'status-complete'}">
						${boardDetail['answer_status'] }
					</td>
				</tr>
				<tr>
					<th class="board-detail-th">관리자 답변</td>
						<!-- 답변이 있는경우 -->
						<td class="board-detail-td">								
						<!-- 답변 내용 문자열 유무로 판단 -->
							<c:if test="${boardDetail['answer_status'] == '답변대기'}">
								- 아직 답변이 없습니다 -						
							</c:if> 
							<c:if test="${boardDetail['answer_status'] == '답변완료'}">
							${boardDetail['answer_content'] }
							</c:if>
					</td>
				</tr>
			</table>
			<div class="btn-group">			
				<button class="btn-gray" onclick="location.href='/sBoardList'">목록</button>			
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var board_idx = '${boardDetail["board_idx"]}';  
		function sBoard_update(){
		    location.href = "/sBoardUpdate?board_idx=" + board_idx;
		}
		function sBoard_delete(){
		    location.href = "/sBoardDelete?board_idx=" + board_idx;
		}
	</script>
</body>
</html>
