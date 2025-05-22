<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
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
				<h1>학생 등록</h1>
			</div>
			<form action="/studentInsert" method="post" autocomplete="off"
				onsubmit="return checkForm();">
				<div class="btn-group">
					<button class="btn-red" onclick="location.href='/studentList'">취소</button>
					<input type="submit" class="btn-blue" value="등록" />
				</div>
				<table>
					<tr>
						<th><label for="name">이름</label></th>
						<td><input type="text" name="name" id="name" placeholder="학생 이름" required></td>
					</tr>
					<tr>
						<th><label for="birth">생년월일</label></th>
						<td><input type="date" name="birth" id="birth" required></td>
					</tr>
					<tr>
						<th><label for="email">이메일</label></th>
						<td><input type="email" name="email" id="email" placeholder="nuri@naver.com" required></td>
					</tr>
					<tr>
						<th><label for="phone">휴대폰 번호</label></th>
						<td><input type="text" name="phone"
							placeholder="010-1234-5678" id="phone" required></td>
					</tr>
					<tr>
						<th><label for="s_grade">학년</label></th>
						<td>
							<select name="s_grade" id="s_grade" required>
								<c:forEach begin="1" end="6" var="i">
									<option value="${i}" ${i == 1 ? 'selected' : ''}>${i}학년</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th><label for="dept_idx">전공</label></th>
						<td><select name="dept_idx" required>
								<c:forEach var="k" items="${deptList}" varStatus="status">
									<option value="${k.dept_idx}" ${status.first ? 'selected' : ''}>
										${k.dept_name}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<th>주소</th>
						<td><input type="text" name="s_address" id="s_address"
							placeholder="클릭하여 주소를 입력하세요(필수)" onclick="execDaumPostcode()">
							<input type="text" name="s_address2" id="s_address2"
							placeholder="상세주소를 입력하세요(선택)"></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		function checkForm() {
			  var name = document.getElementById("name").value;
			  var birthdate = document.getElementById("birthdate").value;
			  var phone = document.getElementById("phone").value;
			  var email = document.getElementById("email").value;
			  var s_address = document.getElementById("s_address").value;

			  // 이름: 비어 있지 않은지 확인
			  if (name.trim() === "") {
			    alert("이름을 입력해주세요.");
			    return false;
			  }
			  // 주소: 비어 있지 않은지 확인
			  if (s_address.trim() === "") {
			    alert("주소를 입력해주세요.");
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
		// 1. 주소 찾기
		function execDaumPostcode() {
			new daum.Postcode({
				oncomplete : function(data) {
					// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
					$("#s_address").val(data.address);
				}
			}).open();
		}
	</script>
</body>
</html>
