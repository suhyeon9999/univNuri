<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/commonUser.css">
</head>
<body>
	<%@ include file="../../headerNavi.jsp"%>
	<div id="wrap">
		<div class="total-container">
			<div class="title-container">
				<div class="highlight"></div>
				<h1>학생 상세보기</h1>
			</div>
			<div class="btn-group">
				<button class="btn-red" onclick="showPwModal()">삭제</button>
				<button class="btn-blue"
					onclick="location.href='/updateStudent?user_id=${detailStudent.user_id}'">수정</button>
			</div>
			<table>
				<tr>
					<th>이름</th>
					<td>${detailStudent.name}</td>
				</tr>
				<tr>
					<th>생년월일</th>
					<td>${detailStudent.birth}</td>
				</tr>
				<tr>
					<th>학번</th>
					<td>${detailStudent.user_id}</td>
				</tr>
				<tr>
					<th>학적상태</th>
					<!-- eq는 문자열이든 숫자든 "값이 같으면" 비교가 성립!! -->
					<td><c:choose>
							<c:when test="${detailStudent.status eq '1'}">재학</c:when>
							<c:when test="${detailStudent.status eq '2'}">휴학</c:when>
							<c:when test="${detailStudent.status eq '3'}">졸업</c:when>
							<c:otherwise>미정</c:otherwise>
						</c:choose></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td>${detailStudent.email}</td>
				</tr>
				<tr>
					<th>휴대폰 번호</th>
					<td>${detailStudent.phone}</td>
				</tr>
				<tr>
					<th>학년</th>
					<td>${detailStudent.s_grade}</td>
				</tr>
				<tr>
					<th>전공</th>
					<td>${detailStudent.dept_name}</td>
				</tr>
				<tr>
					<th>주소</th>
					<td>${detailStudent.s_address}<br>
						${detailStudent.s_address2}
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
			<div class="btn-group">
				<button class="btn-gray" onclick="location.href='/studentList'">목록</button>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var s_idx = '${detailStudent.s_idx}';
		var user_id = '${detailStudent.user_id}';
		
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
		$('#pwCheckBtn').click(function() {
			var inputPw = $('#pwInput').val();
			console.log('입력된 비밀번호: ' + inputPw); // 입력된 비밀번호 출력
			if (!inputPw) {
				alert("비밀번호를 입력해주세요.");
				return;
			}

			$.ajax({
				url : "/aStudentCheckPassword",
				type : "POST",
				data : {
					inputPwd : inputPw
				},
				success : function(response) {
					console.log('서버 응답: ' + response); // 서버 응답 출력
					if (response === "OK") {
						// 비밀번호가 맞으면 데이터 전송
						console.log('서버 응답: ' + response); // 서버 응답 출력
						$.ajax({
							url : "/aStudentDeleteOk",
							type : "POST",
							data : {
								user_id : user_id,
								s_idx : s_idx
							},
							success : function(response) {
								if (response === "OK") {
									location.href = "/studentList";
								} else {
									alert("삭제 실패");
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
