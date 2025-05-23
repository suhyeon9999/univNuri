<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
		<form action="/insertSubject" method="post"  onsubmit="return checkForm();">
			<div class="total-container">
				<div class="title-container">
					<div class="highlight"></div>
					<h1>과목 등록</h1>
				</div>
				<div class="btn-group">
					<button type="button" class="btn-red" onclick="location.href='/subjectList'">취소</button>
					<input type="submit" class="btn-blue" value="등록" />
				</div>
				<table>
					<tr>
						<th><label for="subject_name">과목명</label></th>
						<td><input type="text" name="subject_name"  id="subject_name" required></td>
					</tr>
					<tr>
						<th><label for="dept_idx">*학과</label></th>
						<td><select name="dept_idx" required>
								<option value="" disabled selected>학과선택</option>
								<c:forEach var="k" items="${deptList}">
									<option value="${k.dept_idx}">${k.dept_name}</option>
								</c:forEach>
						</select></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
<script type="text/javascript">
		function checkForm() {
			  var subject_name = document.getElementById("subject_name").value;		 
	
			  // 이름: 비어 있지 않은지 확인
			  if (subject_name.trim() === "") {
			    alert("과목이름을 입력해주세요.");
			    return false;
			  }		
	
			  return true; // 모든 검사를 통과하면 폼 제출
			}
	</script>
</body>
</html>

