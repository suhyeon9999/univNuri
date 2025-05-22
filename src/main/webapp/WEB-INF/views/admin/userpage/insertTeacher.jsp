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
				<h1>교수 등록</h1>
			</div>
			<form action="/teacherInsert" method="post" autocomplete="off"
				onsubmit="return checkForm();">
				<div class="btn-group">
					<button class="btn-red" onclick="location.href='/ateacherList'">취소</button>
					<input type="submit" class="btn-blue" value="등록" />
				</div>
				<table>
					<tr>
						<th><label for="name">이름</label></th>
						<td><input type="text" name="name" id="name" required></td>
					</tr>
					<tr>
						<th><label for="birth">생년월일</label></th>
						<td><input type="date" name="birth" id="birth" required></td>
					</tr>
					<tr>
						<th><label for="email">이메일</label></th>
						<td><input type="email" name="email" id="email" required></td>
					</tr>
					<tr>
						<th><label for="phone">휴대폰 번호</label></th>
						<td><input type="text" name="phone"
							placeholder="010-1234-5678" id="phone" required></td>
					</tr>
					<tr>
						<th><label for="dept_idx">전공</label></th>
						<td><select name="dept_idx" id="dept_idx">
								<c:forEach var="dept" items="${deptList}">
									<option value="${dept.dept_idx}"
										<c:if test="${dept.dept_idx == detailTeacher.dept_idx}">selected</c:if>>
										${dept.dept_name}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<th><label for="t_dept_chair">학과장 여부</label></th>
						<td><select name="t_dept_chair" id="t_dept_chair" required>
								<option value="" selected>학과장 여부 선택</option>
								<option value="0">교수</option>
								<option value="1">학과장</option>
						</select></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		function checkForm() {
			var name = document.getElementById("name").value;
			var birthdate = document.getElementById("birth").value;
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
			alert("정상적으로 등록 되었습니다.");
		}
	</script>
</body>
</html>
