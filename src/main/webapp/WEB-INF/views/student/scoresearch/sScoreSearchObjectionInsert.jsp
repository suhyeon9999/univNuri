<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../../headerNavi.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이의제기 신청</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/commonUser.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/student/sScoreSearchObjectionInsert.css">
</head>
<body>
<div id="wrap" style="position: relative;">
    <form id="objectionForm" method="post">
        <div class="title-container">
            <div class="highlight"></div>
            <h1>이의제기 신청</h1>
            <button type="button" onclick="submitObjection(this.form)" class="btn-blue">등록</button>
            <button type="button" onclick="cancelObjection(this.form)" class="btn-red">취소</button>
        </div>


        <c:if test="${not empty objectionInfo}">
            <table>
                <tr>
                    <th>학년도/학기</th>
					<td>
					  ${fn:substring(objectionInfo.apply_date, 0, 4)}-
					  <c:choose>
					    <c:when test="${fn:substring(objectionInfo.apply_date, 5, 7) le '06'}">1</c:when>
					    <c:otherwise>2</c:otherwise>
					  </c:choose>
					</td>

                </tr>
                <tr>
                    <th>강의</th>
                    <td>${objectionInfo.lect_name}</td>
                </tr>
                <tr>
                    <th>교수명</th>
                    <td>${objectionInfo.professor_name}</td>
                </tr>
					<tr>
					    <th>신청일</th>
					    <td>
					        ${fn:substring(objectionInfo.apply_date, 0, 10)}
					    </td>
					</tr>
                <tr>
                    <th>내용</th>
                    <td>
                        <textarea name="objection_content" rows="12" cols="100" required></textarea>
                    </td>
                </tr>
            </table>
        </c:if>
        <button type="button" onclick="location.href='/sScoreSearch'" style="float: right; background-color: gray; color: white; width: 150px; position: absolute; top: 930px; left:89.4%;">
            이의제기 목록
        </button>
        								<!-- 비밀번호 입력 모달 -->
			<div id="pwModal" class="modal" style="display: none;">
				<div class="modal-content">
					<h3>비밀번호 확인</h3>
					<input type="password" id="pwInput" placeholder="비밀번호 입력" /> <br>
					<button id="pwCheckBtn" class="btn-blue">확인</button>
					<button id="pwCancelBtn" class="btn-red">취소</button>
				</div>
			</div>
    </form>
</div>

<script type="text/javascript">
function submitObjection(f) {
    const confirmSubmit = confirm("이의제기를 등록하시겠습니까?");
    if (confirmSubmit) {
        // 비밀번호 입력 모달 표시
        console.log("✅ 모달 열기 시도됨");  // 확인용
        $('#pwModal').show();
    }
}
/* //비밀번호 체크 모달창
function showPwModal() {
    $('#pwModal').show();
} */

// 모달창 닫기
$('#pwCancelBtn').click(function () {
    $('#pwModal').hide();
    $('#pwInput').val('');
});

// 수정된 data 전송 부분
$('#pwCheckBtn').click(function () {
    var inputPw = $('#pwInput').val();
    if (!inputPw) {
        alert("비밀번호를 입력해주세요.");
        return;
    }

    $.ajax({
        url: "/objectionPwCheck", // 비밀번호 확인은 동일
        type: "POST",
        data: { inputPwd: inputPw },
        success: function (response) {
            if (response === "OK") {
                // 비밀번호 인증 성공 → 수정 로직 실행
                const form = document.getElementById("objectionForm");
                form.action = "/sScoreSearchObjectionDetailInsertOK";
                form.method = "POST";
                form.submit(); // 이제 문제 없이 전송됨
            } else {
                alert("비밀번호가 틀렸습니다.");
                $('#pwInput').val('');
            }
        },
        error: function () {
            alert("서버 오류가 발생했습니다.");
        }
    });
});

function cancelObjection(f) {
    f.action = "/sScoreSearch";
    f.submit();
}
</script>
</body>
</html>
