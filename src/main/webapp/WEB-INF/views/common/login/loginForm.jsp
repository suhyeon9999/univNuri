<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>누리끼리 로그인</title>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/loginForm.css">
   	<script type="text/javascript">
	 window.onload = function () {
		    // 로컬스토리지에서 user_id가 있으면 입력창에 채워넣고 체크박스 체크
		    const savedId = localStorage.getItem("savedUserId");
		    if (savedId) {
		      document.getElementById("user_id").value = savedId;
		      document.getElementById("save_id").checked = true;
		    }
		  };

		  function saveId() {
		    const userId = document.getElementById("user_id").value;
		    const saveCheck = document.getElementById("save_id").checked;

		    if (saveCheck) {
		      localStorage.setItem("savedUserId", userId);
		    } else {
		      localStorage.removeItem("savedUserId");
		    }
		  }
	</script>
</head>
<body>
  <div id="login">
    <!-- 학교명 위치 -->
    <div class="school-name">누리대학교 LMS</div>

    <form action="/user_login" method="post" class="login-box" onsubmit="saveId()">
      <h2>로그인</h2>

      <!-- 아이디 입력 -->
      <div class="input-group">
        <input type="text" name="user_id" id="user_id" required autocomplete="off" />
        <label for="user_id">아이디</label>
      </div>

      <!-- 비밀번호 입력 -->
      <div class="input-group">
        <input type="password" name="user_pw" id="user_pw" required />
        <label for="user_pw">비밀번호</label>
      </div>

      <label>
        <input type="checkbox" name="save_id" id="save_id" /> 아이디 저장
      </label>
      <input type="submit" value="로그인"/>

      <div class="links">
        <a href="#">비밀번호 찾기</a>
      </div>

      <p class="info">
        신입생 최초 로그인 비밀번호는<br>
        생년월일 6자리 + 휴대폰 번호 뒤 4자리입니다.
      </p>
    </form>
  </div>
  <c:if test="${loginchk=='fail'}">
  	<script type="text/javascript">
  		alert("ID or PW 확인해주세요.");
  	</script>
  </c:if>
</body>
</html>
