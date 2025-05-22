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
					<th class="board-detail-th">답변일</th>
					<td class="board-detail-td"><c:choose>
							<c:when test="${boardDetail.answer_status == '답변대기'}">-</c:when>
							<c:otherwise>${boardDetail.answer_date}</c:otherwise>
						</c:choose></td>
				</tr>
				<tr>
					<th class="board-detail-th">답변자</th>
					<td class="board-detail-td"><c:choose>
							<c:when test="${boardDetail.answer_status == '답변대기'}">-</c:when>
							<c:otherwise>${boardDetail.admin_name}</c:otherwise>
						</c:choose></td>
				</tr>
				<tr>
					<th class="board-detail-th">답변자 사번</th>
					<td class="board-detail-td"><c:choose>
							<c:when test="${boardDetail.answer_status == '답변대기'}">-</c:when>
							<c:otherwise>${boardDetail.admin_user_id}</c:otherwise>
						</c:choose></td>
				</tr>
				<tr>
					<th class="board-detail-th">답변 내용</th>
					<td class="board-detail-td"><c:choose>
							<c:when test="${boardDetail.answer_status == '답변대기'}">-</c:when>
							<c:otherwise>
								<c:if test="${boardDetail.answer_read eq true}">
									<p style="color: gray;">※ 학생이 이미 답변을 열람하여 답변 수정 및 삭제가 불가능합니다 ※</p>
								</c:if>
							${boardDetail.answer_content}
							</c:otherwise>
						</c:choose></td>
				</tr>
			</table>
			<div class="btn-group">
				<button class="btn-gray" onclick="location.href='/aBoardList'">목록</button>

				<c:choose>
					<c:when test="${boardDetail.answer_status == '답변대기'}">
						<input type="button"
							onclick="location.href='/aBoardAnswer?board_idx=${boardDetail.board_idx}'"
							class="btn-blue" id="long-btn" value="답변 등록" />
					</c:when>
					<c:otherwise>
						<%-- 학생이 아직 읽지 않은 경우에만 수정/삭제 가능 --%>
						<c:if test="${not boardDetail.answer_read}">
							<button class="btn-blue" id="long-btn"
								onclick="location.href='/aBoardAnswerUpdate?board_idx=${boardDetail.board_idx}'">답변
								수정</button>
							<button class="btn-red" id="long-btn" onclick="showPwModal()">답변
								삭제</button>
						</c:if>
					</c:otherwise>
				</c:choose>
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
	</div>
	<script type="text/javascript">
		var board_idx = '${boardDetail.board_idx}';

		function showPwModal() {
			$('#pwModal').show();
		}

		// 모달창 닫기
		$('#pwCancelBtn').click(function() {
			$('#pwModal').hide();
			$('#pwInput').val('');
		});

		// 수정된 data 전송 부분
		$('#pwCheckBtn').click(function() {
			var inputPw = $('#pwInput').val();
			console.log('입력된 비밀번호: ' + inputPw); // 입력된 비밀번호 출력
			if (!inputPw) {
				alert("비밀번호를 입력해주세요.");
				return;
			}

			$.ajax({
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
							url : "/aBoardAnswerDeleteOk",
							type : "POST",
							data : {
								board_idx : board_idx
							},
							success : function(response) {
								if (response === "OK") {
									alert("답변이 삭제되었습니다.");
									location.href = "/aBoardList"; // 이동
								} else {
									alert("답변 삭제에 실패했습니다.");
								}
							},
							error : function() {
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
