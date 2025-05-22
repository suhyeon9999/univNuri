<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>교수 정보 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/commonUser.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<%@ include file="../../headerNavi.jsp"%>
<div id="wrap">
  <div class="total-container">
    <div class="title-container">
      <div class="highlight"></div>
      <h1>교수 정보 상세보기</h1>
    </div>
    <div class="btn-group">
      <button class="btn-red" onclick="showPwModal()">삭제</button>
      <button class="btn-blue" onclick="location.href='/aupdateTeacher?user_id=${detailTeacher.user_id}'">수정</button>
    </div>
    <table>
      <tr><th>이름</th><td>${detailTeacher.name}</td></tr>
      <tr><th>생년월일</th><td>${detailTeacher.birth}</td></tr>
      <tr><th>사번</th><td>${detailTeacher.user_id}</td></tr>
      <tr><th>재직상태</th><td>${detailTeacher.status}</td></tr>
      <tr><th>이메일</th><td>${detailTeacher.email}</td></tr>
      <tr><th>휴대폰 번호</th><td>${detailTeacher.phone}</td></tr>
      <tr><th>전공</th><td>${detailTeacher.dept_name}</td></tr>
      <tr><th>학과장 여부</th><td>${detailTeacher.t_dept_chair}</td></tr>
    </table>

    <!-- 비밀번호 입력 모니터 -->
    <div id="pwModal" class="modal" style="display: none;">
      <div class="modal-content">
        <h3>비밀번호 확인</h3>
        <input type="password" id="pwInput" placeholder="비밀번호 입력" /><br>
        <button id="pwCheckBtn" class="btn-blue">확인</button>
        <button id="pwCancelBtn" class="btn-red">취소</button>
      </div>
    </div>

    <div class="btn-group">
      <button class="btn-gray" onclick="location.href='/ateacherList'">목록</button>
    </div>
  </div>
</div>

<script type="text/javascript">
var user_id = '${detailTeacher.user_id}';
var t_idx = '${detailTeacher.t_idx}';

function showPwModal() {
  $('#pwModal').show();
}

$('#pwCancelBtn').click(function () {
  $('#pwModal').hide();
  $('#pwInput').val('');
});

$('#pwCheckBtn').click(function() {
	var inputPw = $('#pwInput').val();
	console.log('입력된 비밀번호: ' + inputPw); // 입력된 비밀번호 출력
	if (!inputPw) {
		alert("비밀번호를 입력해주세요.");
		return;
	}

	$.ajax({
		url : "/aTeacherCheckPassword",
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
					url : "/aTeacherDeleteOk",
					type : "POST",
					data : {
						user_id : user_id,
						t_idx : t_idx
					},
					success : function(response) {
						if (response === "OK") {
							location.href = "/ateacherList";
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
