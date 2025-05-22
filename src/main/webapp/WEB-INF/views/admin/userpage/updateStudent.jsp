<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
				<h1>학생 정보 수정</h1>
			</div>
			<div class="btn-group">
				<button class="btn-red"
					onclick="location.href='/detailStudent?user_id=${detailStudent.user_id}'">취소</button>
				<input type="button" class="btn-blue" value="저장"
					onclick="updateOkStudent()" />
			</div>
			<table>
				<tr>
					<th><label for="name">이름</label></th>
					<td><input type="text" name="name"
						value="${detailStudent.name}" required></td>
				</tr>
				<tr>
					<th><label for="birthDate">생년월일</label></th>
					<td><input type="date" name="birthDate"
						value="${detailStudent.birth}" required></td>
				</tr>
				<tr>
					<th><label for="s-idx">학번</label></th>
					<td><input type="text" name="s-idx"
						value="${detailStudent.user_id}" required disabled></td>
				</tr>
				<tr>
					<th><label for="s-status">학적상태</label></th>
					<c:choose>
						<c:when test="${detailStudent.status eq '1'}">
							<c:set var="statusText" value="재학" />
						</c:when>
						<c:when test="${detailStudent.status eq '2'}">
							<c:set var="statusText" value="휴학" />
						</c:when>
						<c:when test="${detailStudent.status eq '3'}">
							<c:set var="statusText" value="졸업" />
						</c:when>
						<c:otherwise>
							<c:set var="statusText" value="미정" />
						</c:otherwise>
					</c:choose>
					<td><input type="text" name="s-status" value="${statusText}"
						required disabled></td>
				</tr>
				<tr>
					<th><label for="email">이메일</label></th>
					<td><input type="email" name="email"
						value="${detailStudent.email}" placeholder="nuri@naver.com"
						required></td>
				</tr>
				<tr>
					<th><label for="phone">휴대폰 번호</label></th>
					<td><input type="text" name="phone"
						value="${detailStudent.phone}" placeholder="010-1234-5678"
						required></td>
				</tr>
				<tr>
					<th><label for="grade">학년</label></th>
					<td><select name="grade" required>
							<!-- 현재 학년을 첫 번째 option으로, 선택 가능하게 (disabled ❌) -->
							<option value="${detailStudent.s_grade}" selected>
								${detailStudent.s_grade}학년</option>
							<!-- 나머지 학년 중에서 중복되지 않게 반복 -->
							<c:forEach begin="1" end="6" var="i">
								<c:if test="${i ne detailStudent.s_grade}">
									<option value="${i}">${i}학년</option>
								</c:if>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th><label for="major">전공</label></th>
					<td><select name="major" required>
							<option value="${detailStudent.dept_idx}" selected>
								${detailStudent.dept_name}</option>
							<c:forEach var="k" items="${deptList}">
								<!-- ne ==> not equal(같지 않음) (JSTL 비교 연산자) -->
								<!-- 이 학생의 기존 전공이 드롭박스에 안 나오게 하기 위함 -->
								<c:if test="${k.dept_idx ne detailStudent.dept_idx}">
									<option value="${k.dept_idx}">${k.dept_name}</option>
								</c:if>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>주소</th>
					<td><input type="text" name="addr1" id="address1"
						value="${detailStudent.s_address}" onclick="execDaumPostcode()"
						placeholder="클릭하여 주소를 입력하세요(필수)"> <input type="text"
						name="addr2" value="${detailStudent.s_address2}" id="address2"
						placeholder="상세주소를 입력하세요(선택)"></td>
				</tr>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
	// 주소 찾기
	function execDaumPostcode() {
		new daum.Postcode({
			oncomplete : function(data) {
				// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분
				$("#address1").val(data.address);
			}
		}).open();
	}
	
	// 저장
	function updateOkStudent(){
			// 입력값 가져오기 (trim은 공백 제거)
			const name = $('input[name="name"]').val().trim();
			const birth = $('input[name="birthDate"]').val().trim();
			const email = $('input[name="email"]').val().trim();
			const phone = $('input[name="phone"]').val().trim();
			const s_grade = $('select[name="grade"]').val();
			const dept_idx = $('select[name="major"]').val();
			const s_address = $('input[name="addr1"]').val().trim();
			const s_address2 = $('input[name="addr2"]').val().trim();
			const user_id = '${detailStudent.user_id}';  // 학번은 disabled니까 EL로 직접 넣기

			// 유효성 검사
			if (!name || !birth || !email || !phone || !s_grade || !dept_idx || !s_address) {
				alert("필수 항목을 모두 입력해 주세요.");
				return;
			}

			// 전송할 데이터 객체
			const data = {
				user_id: user_id,
				name: name,
				birth: birth,
				email: email,
				phone: phone,
				s_grade: s_grade,
				dept_idx: dept_idx,
				s_address: s_address,
				s_address2: s_address2
			};

			// AJAX 요청
			$.ajax({
				url: "/updateOkStudent",
				type: "POST",
				data: data,
				success: function(response) {
					if (response === "OK") {
						alert("학생 정보가 성공적으로 수정되었습니다.");
						location.href = "/detailStudent?user_id=" + user_id;
					} else {
						alert("수정에 실패했습니다.");
					}
				},
				error: function() {
					alert("서버 요청 중 오류가 발생했습니다.");
				}
			});
		}
</script>
</html>
