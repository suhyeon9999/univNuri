<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@include file="../../headerNavi.jsp"%>
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
				<button class="btn-red" onclick="location.href='/sBoardList'">취소</button>
				<input type="button" class="btn-blue" value="등록" onclick="sBoardWriteOk()" />
			</div>
			<table>
				<tr>
					<th class="board-detail-th">제목</th>
					<td class="board-detail-td"><input type="text" name="subject" required></td>
				</tr>				
				<tr>
					<th class="board-detail-th">이름</th>
					<td class="board-detail-td">${sessionScope.uvo.name}</td>
				</tr>
				<tr>
					<th class="board-detail-th">내용</th>
					<td class="board-detail-td"><textarea rows="25" cols="45" name="content"  required></textarea></td>
				</tr>         
			</table>
		</div>
	</div>
	<script type="text/javascript">
	function sBoardWriteOk() {
	    const title = $("input[name='subject']").val();
	    const content = $("textarea[name='content']").val();

	    // 입력 값이 비어있는지 체크 (필요시)
	    if (!title || !content) {
	        alert("모든 항목을 입력해 주세요.");
	        return; // 입력값이 없으면 종료
	    }
	    
	    $.ajax({
	        url: "/sBoardWriteOk",  // 너가 실제 저장하는 컨트롤러 매핑 경로
	        type: "POST",
	        data: {
	            title: title,
	            content: content
	        },
	        success: function(response) {
	            if (response.result === "OK") {
	                // board_idx를 사용해서 상세페이지로 이동
	                location.href = "/sBoardDetail?board_idx=" + response.board_idx;
	            } else {
	                alert("등록 실패: " + response.message);
	            }
	        },
	        error: function() {
	            alert("서버 오류");
	        }
	    });
	}
	</script>
</body>
</html>

