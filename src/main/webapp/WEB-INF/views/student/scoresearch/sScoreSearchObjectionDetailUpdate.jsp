<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../../headerNavi.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/commonUser.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/student/sScoreSearchObjectionInsert.css">
  <meta charset="UTF-8">
  <title>성적관리</title>
</head>
<body>
  <div id="wrap" style="position: relative;">
    <form method="post" id="objectionDetailUpdate">
      <div class="title-container">
        <div class="highlight"></div>
        <h1>이의제기 조회</h1>
        <button type="button" onclick="sScoreSearchObjectionDetailUpdateOK(this.form)" class="btn-blue">수정</button>
        <input type="hidden" name="objection_idx" value="${objectionList[0].objection_idx}">
        <input type="hidden" name="lect_idx" value="${objectionList[0].lect_idx}">
        <button type="button" onclick="sScoreSearchObjectionDetailDeleteOK(this.form)" class="btn-red">삭제</button>
      </div>

      <c:if test="${not empty objectionList}">
        <table>
          <tr>
            <th>학년도/학기</th>
            <td>
              <fmt:formatDate value="${objectionList[0].apply_date}" pattern="yyyy" />-
              <c:choose>
                <c:when test="${fn:substring(objectionList[0].apply_date, 5, 7) le '06'}">1</c:when>
                <c:otherwise>2</c:otherwise>
              </c:choose>
            </td>
          </tr>
          <tr>
            <th>강의</th>
            <td>${objectionList[0].lect_name}</td>
          </tr>
          <tr>
            <th>교수명</th>
            <td>${objectionList[0].professor_name}</td>
          </tr>
          <tr>
            <th>신청일</th>
            <td><fmt:formatDate value="${objectionList[0].apply_date}" pattern="yyyy-MM-dd"/></td>
          </tr>
          <tr>
            <th>내용</th>
            <td>
              <textarea name="objection_content" rows="12" cols="100">${objectionList[0].objection_content}</textarea>
            </td>
          </tr>
        </table>
      </c:if>

      <button type="button" onclick="location.href='/sScoreSearch'" 
              style="float: right; background-color: gray; color: white; width: 150px; position: absolute; top: 930px; left:89.4%;">
        이의제기 목록
      </button>
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
       // 공통 모달 닫기
       $('#pwCancelBtn').click(function () {
           $('#pwModal').hide();
           $('#pwInput').val('');
       });

       // 모달 확인 → 비밀번호 체크 → 최종 폼 전송
       $('#pwCheckBtn').click(function () {
           var inputPw = $('#pwInput').val();
           if (!inputPw) {
               alert("비밀번호를 입력해주세요.");
               return;
           }

           $.ajax({
               url: "/objectionPwCheck",
               type: "POST",
               data: { inputPwd: inputPw },
               success: function (response) {
                   if (response === "OK") {
                       document.getElementById("objectionDetailUpdate").submit();
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

// 목록 버튼
function cancelObjection(f) {
    f.action = "/sScoreSearch";
    f.submit();
}
// 등록 버튼 클릭 → 수정용 모달 호출
function sScoreSearchObjectionDetailUpdateOK(form) {
    form.action = "/sScoreSearchObjectionDetailUpdateOK";
    $('#pwModal').show();
}

// 삭제 버튼 클릭 → 삭제용 모달 호출
function sScoreSearchObjectionDetailDeleteOK(form) {
    form.action = "/sScoreSearchObjectionDetailDeleteOK";
    $('#pwModal').show();
}
</script>
    </body>
    </html>
