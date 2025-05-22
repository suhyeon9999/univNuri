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
				<h1>교수 정보 수정</h1>
			</div>
			<div class="btn-group">
				<button class="btn-red"
					onclick="location.href='/adetailTeacher?user_id=${detailTeacher.user_id}'">취소</button>
				<input type="button" class="btn-blue" value="저장"
					onclick="updateOkTeacher()" />
			</div>
			<table>
				<tr>
					<th><label for="name">이름</label></th>
					<td><input type="text" name="name"
						value="${detailTeacher.name}" required></td>
				</tr>
				<tr>
					<th><label for="birthDate">생년월일</label></th>
					<td><input type="date" name="birthDate"
						value="${detailTeacher.birth}" required></td>
				</tr>
				<tr>
					<th><label for="t-idx">사번</label></th>
					<td><input type="text" name="t-idx"
						value="${detailTeacher.user_id}" required disabled></td>
				</tr>
				<tr>
					<th><label for="t-status">재직상태</label></th>
				<td>
					<select name="status" id="status" required>
						<option value="4" <c:if test="${detailTeacher.status == '재직'}">selected</c:if>>재직</option>
						<option value="5" <c:if test="${detailTeacher.status == '퇴직'}">selected</c:if>>퇴직</option>
					</select>
				</td>

				</tr>
				<tr>
					<th><label for="email">이메일</label></th>
					<td><input type="email" name="email"
						value="${detailTeacher.email}" required></td>
				</tr>
				<tr>
					<th><label for="phone">휴대폰 번호</label></th>
					<td><input type="text" name="phone"
						value="${detailTeacher.phone}" placeholder="010-1234-5678"
						required></td>
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
			<tr>
				<th><label for="t_dept_chair">학과장 여부</label></th>
				<td>
					<select name="t_dept_chair" id="t_dept_chair" required>
						<option value="0" <c:if test="${detailTeacher.t_dept_chair == '교수'}">selected</c:if>>교수</option>
						<option value="1" <c:if test="${detailTeacher.t_dept_chair == '학과장'}">selected</c:if>>학과장</option>
					</select>
				</td>
			</tr>

			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
	
	// 저장
function updateOkTeacher() {
    // 입력값 가져오기
    const name = $('input[name="name"]').val().trim();
    const birth = $('input[name="birthDate"]').val().trim();
    const email = $('input[name="email"]').val().trim();
    const phone = $('input[name="phone"]').val().trim();
    const dept_idx = $('select[name="dept_idx"]').val(); // 교수는 전공만 있음
    const user_id = '${detailTeacher.user_id}';  // 교수 사번
    const t_dept_chair = $('select[name="t_dept_chair"]').val();  // 학과장 여부
    const status = $('select[name="status"]').val(); 

    // 유효성 검사
    if (!name || !birth || !email || !phone || !dept_idx || !t_dept_chair) {
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
        dept_idx: dept_idx, 
        t_dept_chair : t_dept_chair, 
        status : status
    };

    // AJAX 요청
    $.ajax({
        url: "/updateOkTeacher", // 교수 정보 수정에 맞는 URL로 변경
        type: "POST",
        data: data,
        success: function(response) {
            if (response === "OK") {
                alert("교수 정보가 성공적으로 수정되었습니다.");
                location.href = "/adetailTeacher?user_id=" + user_id;
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
