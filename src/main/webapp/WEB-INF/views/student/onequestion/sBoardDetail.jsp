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
					<button class="btn-red" onclick="showPwModal()">삭제</button>
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
					<td
						class="board-detail-td ${boardDetail['answer_status'] == '답변대기' ? 'status-wait' : 'status-complete'}">
						${boardDetail['answer_status'] }</td>
				</tr>
				<tr>
					<th class="board-detail-th">관리자 답변
					</td>
					<!-- 답변이 있는경우 -->
					<td class="board-detail-td">
						<!-- 답변 내용 문자열 유무로 판단 --> <c:if
							test="${boardDetail['answer_status'] == '답변대기'}">
								- 아직 답변이 없습니다 -						
							</c:if> <c:if test="${boardDetail['answer_status'] == '답변완료'}">
							${boardDetail['answer_content'] }
							</c:if>
					</td>
				</tr>
			</table>
			<!-- 비밀번호 입력 모달 -->
			<div id="pwModal" class="modal" style="display: none;">
				<div class="modal-content">
					<h3>비밀번호 확인</h3>
					<input type="password" id="pwInput" placeholder="비밀번호 입력" /> <br>
					<button id="pwCheckBtn" class="btn-blue">확인</button>
					<button id="pwCancelBtn" class="btn-red">취소</button>
				</div>
			</div>
		</div>
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
		
		// 비밀번호 체크 모달창
		function showPwModal() {
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

							$
									.ajax({
										url : "/sBoardCheckPassword",
										type : "POST",
										data : {
											board_idx : board_idx,
											inputPwd : inputPw
										},
										success : function(response) {
											if (response === "OK") {
												// 비밀번호가 맞으면 데이터 전송
												$.ajax({
													  url: "/sBoardDeleteOk",
													  type: "POST",
													  data: { board_idx: board_idx },
													  success: function(response) {
													    if (response === "OK") {
													      location.href = "/sBoardList";
													    } else {
													      alert("삭제 실패");
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
