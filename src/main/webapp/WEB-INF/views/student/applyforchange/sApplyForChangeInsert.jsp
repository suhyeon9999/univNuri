<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="../../headerNavi.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/student/sApplyForChangeInsert.css">
<meta charset="UTF-8">
<title>학적 변경 신청</title>
</head>
<body>
	<div id="wrap">
		<form id="changeForm" method="post" enctype="multipart/form-data">
			<div class="title-container">
				<div class="highlight"></div>
				<h1>학적 변경 신청</h1>
				<button type="button" onclick="showPwModal()" class="btn-blue">신청</button>
				<button type="button" onclick="location.href='/sApplyforchange'"
					class="btn-red">취소</button>
			</div>
			<table>
				<tr>
					<th>이름</th>
					<td>${uvo.name}</td>
				</tr>
				<tr>
					<th>학번</th>
					<td>${uvo.user_id}</td>
				</tr>
				<tr>
					<th>신청구분</th>
					<td><select name="req_type">
							<option value="2" selected>휴학</option>
							<option value="1">복학</option>
					</select></td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td>
						<div id="fileUploadWrapper">
							<div class="file-row">
								<input type="file" name="fileUpload[]" class="file-input"
									onchange="updateFileName(this)"
									accept=".jpg,.jpeg,.txt,.ppt,.pptx,.pdf,.doc,.docx"> <span
									class="filename">선택된 파일 없음</span>
								<button type="button" class="remove-btn"
									onclick="removeFile(this)">❌</button>
							</div>
						</div>
						<button type="button" onclick="addFile()" class="add-btn">추가</button>
					</td>
				</tr>
				<tr>
					<th>신청사유</th>
					<td><textarea name="req_reason" rows="10" cols="100" required></textarea>
					</td>
				</tr>
			</table>

			<!-- 비밀번호 입력 모달 -->
			<div id="pwModal" class="modal" style="display: none;">
				<div class="modal-content">
					<h3>비밀번호 확인</h3>
					<input type="password" id="pwInput" placeholder="비밀번호 입력" /> <br>
					<button id="pwCheckBtn" type="button" class="btn-blue">확인</button>
					<button id="pwCancelBtn" type="button" class="btn-red">취소</button>
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript">
	function addFile() {
		const wrapper = document.getElementById('fileUploadWrapper');
		const row = document.createElement('div');
		row.className = 'file-row';

		row.innerHTML = `
			<input type="file" name="fileUpload[]" class="file-input" onchange="updateFileName(this)" accept=".jpg,.jpeg,.txt,.ppt,.pptx,.pdf,.doc,.docx">
			<span class="filename">선택된 파일 없음</span>
			<button type="button" class="remove-btn" onclick="removeFile(this)">❌</button>
		`;

		wrapper.appendChild(row);
	}

	function removeFile(btn) {
		const row = btn.closest('.file-row');
		row.remove();
	}

	function updateFileName(input) {
		const file = input.files[0];
		const span = input.closest('.file-row').querySelector('.filename');
		span.textContent = file ? file.name : '선택된 파일 없음';
	}
	// 비밀번호 체크 모달창
	function showPwModal() {
		const reason = $('textarea[name="req_reason"]').val().trim();
		if (reason === "") {
			alert("신청 사유를 입력해주세요.");
			return;
		}
		$('#pwModal').show();
	}

	// 모달창 닫기
	$('#pwCancelBtn').click(function () {
		$('#pwModal').hide();
		$('#pwInput').val('');
	});

	// 비밀번호 확인 및 신청 처리
	$('#pwCheckBtn').click(function () {
		var inputPw = $('#pwInput').val();
		if (!inputPw) {
			alert("비밀번호를 입력해주세요.");
			return;
		}

		// 1차: 비밀번호 확인
		$.ajax({
			url: "/sApplyForChangeCheckPw",
			type: "POST",
			data: { inputPwd: inputPw },
			success: function (response) {
				if (response === "OK") {
					// 2차: FormData로 신청 전송
					var formData = new FormData($('#changeForm')[0]);

					$.ajax({
						url: "/sApplyForChangeInsertOk",
						type: "POST",
						data: formData,
						processData: false,
						contentType: false,
						success: function (res) {
							console.log("서버 응답:", res); // 진짜 값 찍어보기

							if (res === "OK") {
								alert("신청이 완료되었습니다.");
								location.href = "/sApplyforchange";
							} else if (res === "FAIL_REQ") {
								alert("요청 저장에 실패했습니다.");
							} else if (res === "INVALID_FILE_TYPE") {
								alert("허용되지 않은 파일 형식입니다.");
							} else {
								alert("알 수 없는 서버 응답: " + res);
							}
						},
						error: function () {
							alert("신청 중 오류 발생");
						}
					});
				} else {
					alert("비밀번호가 틀렸습니다.");
				}
			}
		});
	});
	</script>
</body>
</html>
