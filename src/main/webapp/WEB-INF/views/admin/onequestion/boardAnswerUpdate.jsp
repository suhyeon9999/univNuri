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
					<td class="board-detail-td"><textarea rows="25" cols="45"
							id="content">${boardDetail.answer_content }</textarea></td>
				</tr>
			</table>
			<div class="btn-group">
				<button class="btn-red"
					onclick="location.href='/aBoardDetail?board_idx=${boardDetail.board_idx}'">취소</button>
				<input type="button" onclick="showPwModal()" class="btn-blue"
					id="long-btn" value="답변 저장" />
			</div>
		</div>
	</div>
	<!-- 비밀번호 입력 모달 -->
	<div id="pwModal" class="modal" style="display: none;">
		<div class="modal-content">
			<h3>비밀번호 확인</h3>
			<input type="password" id="pwInput" placeholder="비밀번호 입력" /> <br>
			<button id="pwCheckBtn" class="btn-blue">확인</button>
			<button id="pwCancelBtn" class="btn-red">취소</button>
		</div>
	</div>

	<script type="text/javascript">
	var board_idx = '${boardDetail.board_idx}';
	function showPwModal() {
		var content = document.getElementById('content').value; // 내용
		    // 입력 값이 비어있는지 체크 (필요시)
		    if (!content) {
		        alert("모든 항목을 입력해 주세요.");
		        return; // 입력값이 없으면 종료
		    }
			$('#pwModal').show();
			
		}
		// 모달창 닫기
		$('#pwCancelBtn').click(function() {
			$('#pwModal').hide();
			$('#pwInput').val('');
		});
		// 수정된 data 전송 부분
		$('#pwCheckBtn')
				.click(
						function() {
							var inputPw = $('#pwInput').val();
							console.log('입력된 비밀번호: ' + inputPw); // 입력된 비밀번호 출력
							if (!inputPw) {
								alert("비밀번호를 입력해주세요.");
								return;
							}
							var content = document.getElementById('content').value; // 내용
							
							// 입력된 데이터를 객체로 저장
							var boardDetail = {
								board_idx : board_idx, // 게시판 고유 ID
								content : content // 내용
							};

						    
							$
									.ajax({
										url : "/aBoardCheckPassword",
										type : "POST",
										data : {
											inputPwd : inputPw
										},
										success : function(response) {
											console.log('서버 응답: ' + response); // 서버 응답 출력
											if (response === "OK") {
												// 비밀번호가 맞으면 데이터 전송
												$.ajax({
												    url:   "/aBoardAnswerUpdateOk",
												    type:  "POST",
												    data:  boardDetail,
												    success: function(response) {
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
											} else {
												alert("비밀번호가 틀렸습니다.");
												$('#pwInput').val('');
											}
										},
										error : function() {
											alert("서버 오류가 발생했습니다.");
										}
									});
						});

	</script>
</body>
</html>
