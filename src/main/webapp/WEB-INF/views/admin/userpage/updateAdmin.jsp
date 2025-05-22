<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 정보 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/commonUser.css">
</head>
<body>
<%@ include file="../../headerNavi.jsp"%>
<div id="wrap">
    <form action="/updateAdmin" method="post" onsubmit="return checkForm();">
        <div class="total-container">
            <div class="title-container">
                <div class="highlight"></div>
                <h1>관리자 정보 수정</h1>
            </div>
            <div class="btn-group">
                <button type="button" class="btn-red" onclick="location.href='/adminList'">취소</button>
                <input type="submit" class="btn-blue" value="수정" />
            </div>
            <table>
                <tr>
                    <th><label for="name">이름</label></th>
                    <td><input type="text" name="name" value="${detailAdmin.name }" id="name"  required></td>
                </tr>
                <tr>
                    <th><label for="birth">생년월일</label></th>
                    <td><input type="date" name="birth" value="${detailAdmin.birth }" id="birthdate"  required></td>
                </tr>
                <tr>
                    <th><label for="user_id">사번</label></th>
                    <td>
                        <input type="text" name="user_id" value="${detailAdmin.user_id }" readonly>
                        <input type="hidden" name="user_idx" value="${detailAdmin.user_idx }">
                    </td>
                </tr>
                <tr>
                    <th><label for="status">재직상태</label></th>
                    <td>
                        <select name="status" required>
                            <option value="4" ${detailAdmin.status == '4' ? 'selected' : ''}>재직</option>
                            <option value="5" ${detailAdmin.status == '5' ? 'selected' : ''}>퇴직</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th><label for="email">이메일</label></th>
                    <td><input type="email" name="email" value="${detailAdmin.email }"  id="email"  required></td>
                </tr>
                <tr>
                    <th><label for="phone">휴대폰 번호</label></th>
                    <td><input type="text" name="phone" value="${detailAdmin.phone }" id="phone" placeholder="010-1234-5678" required></td>
                </tr>
                <tr>
                    <th><label for="a_grade">관리자 등급</label></th>
                    <td>
                        <select name="a_grade" required>
                            <option value="1" ${detailAdmin.a_grade == '1' ? 'selected' : ''}>중간관리자</option
                            >
                            <option value="2" ${detailAdmin.a_grade == '2' ? 'selected' : ''}>일반관리자</option>
                        </select>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
	<script type="text/javascript">
		function checkForm() {
			  var name = document.getElementById("name").value;
			  var birthdate = document.getElementById("birthdate").value;
			  var phone = document.getElementById("phone").value;
			  var email = document.getElementById("email").value;
	
			  // 이름: 비어 있지 않은지 확인
			  if (name.trim() === "") {
			    alert("이름을 입력해주세요.");
			    return false;
			  }
	
			  // 생년월일: YYYY-MM-DD 형식 확인
			  var birthdatePattern = /^\d{4}-\d{2}-\d{2}$/;
			  if (!birthdatePattern.test(birthdate)) {
			    alert("생년월일을 YYYY-MM-DD 형식으로 입력해주세요.");
			    return false;
			  }
	
			  // 전화번호: 010-1234-5678 형식 확인
			  var phonePattern = /^010-\d{4}-\d{4}$/;
			  if (!phonePattern.test(phone)) {
			    alert("전화번호를 010-1234-5678 형식으로 입력해주세요.");
			    return false;
			  }
	
			  // 이메일: 간단한 형식 확인
			  var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
			  if (!emailPattern.test(email)) {
			    alert("유효한 이메일 주소를 입력해주세요.");
			    return false;
			  }
	
			  return true; // 모든 검사를 통과하면 폼 제출
			}
	</script>
</body>
</html>