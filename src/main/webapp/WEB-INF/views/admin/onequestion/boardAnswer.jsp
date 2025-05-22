<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
	<%@ include file="../../headerNavi.jsp"%>
	<div id="wrap">
		<div class="total-container">
			<div class="title-container">
				<div class="highlight"></div>
				<h1>1:1문의</h1>
			</div>
			<table>
				<tr>
					<th class="board-detail-th">문의일</th>
					<td class="board-detail-td">${boardDetail.title}</td>
				</tr>
				<tr>
					<th class="board-detail-th">문의자</th>
					<td class="board-detail-td">${boardDetail.writer_name}</td>
				</tr>
				<tr>
					<th class="board-detail-th">문의자 학번</th>
					<td class="board-detail-td">${boardDetail.student_user_id}</td>
				</tr>
				<tr>
					<th class="board-detail-th">문의 제목</th>
					<td class="board-detail-td">${boardDetail.title}</td>
				</tr>
				<tr>
					<th class="board-detail-th">문의 내용</th>
					<td class="board-detail-td">${boardDetail.write_content}</td>
				</tr>
				<tr>
					<th class="board-detail-th">답변상태</th>
					<td class="board-detail-td">${boardDetail.answer_status}</td>
				</tr>
				<tr>
					<th class="board-detail-th">답변 내용</th>
					<td class="board-detail-td">
						<textarea rows="25" cols="45"
								id="content"></textarea>
					</td>
				</tr>
			</table>
			<div class="btn-group">
				<button class="btn-red" onclick="location.href='/aBoardDetail?board_idx=${boardDetail.board_idx}'">취소</button>
				<input type="button" onclick="aBoardAnswerOk()" class="btn-blue" id="long-btn" value="답변 등록" />
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function aBoardAnswerOk() {
			var board_idx = '${boardDetail.board_idx}';
			var content = document.getElementById('content').value; // 내용
		    // 입력 값이 비어있는지 체크 (필요시)
		    if (!content) {
		        alert("모든 항목을 입력해 주세요.");
		        return; // 입력값이 없으면 종료
		    }else{
		    	var content = document.getElementById('content').value; // 내용
		    	
		    	var boardAnswer = {
		    			board_idx : board_idx,
		    			content : content
		    	};
		    	
		    	$.ajax({
		    		url : "/aBoardAnswerOk",
		    		type : "POST",
		    		data : boardAnswer,
		    		success: function (response) {
				        if (response === "OK") {
				            // 업데이트 성공하면 상세보기로
				            location.href = "/aBoardDetail?board_idx=" + board_idx;
				        } else {
				            alert("수정에 실패했습니다.");
				        }
				    },
				    error: function() {
				        alert("서버 오류");
				    }
		    	});
		    }
		}
	</script>
</body>
</html>
