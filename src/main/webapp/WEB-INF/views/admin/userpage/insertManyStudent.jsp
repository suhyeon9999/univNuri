<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../headerNavi.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/admin/insertManyStudent.css">
<meta charset="UTF-8">
<title>학생 일괄 등록</title>
</head>
<body>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>엑셀 파일 업로드</h1>
		</div>

		<div class="main-container">
			<!-- 엑셀 파일 업로드 폼 -->
			<div class="block">
				<form action="${pageContext.request.contextPath}/uploadExcel"
					method="post" enctype="multipart/form-data">
					<div id="upload-msg">파일을 업로드하거나 이미 업로드한 파일을 선택하세요.</div>
					<div>
						<input type="file" id="excelFile" name="excelFile"
							accept=".xls,.xlsx" required />
					</div>
					<button type="submit" class="btn-green" id="upload-btn">업로드</button>
				</form>
			</div>
			<!-- 일괄 등록 폼 -->
			<div class="block">
				<form action="${pageContext.request.contextPath}/saveStudentData"
					method="post">
					<button type="button" class="btn-red" id="excel-clean-btn"
						onclick="cleanExcel()">초기화</button>
					<button type="submit" class="btn-blue" id="excel-submit-btn">일괄등록</button>
					<c:forEach var="k" items="${studentList}" varStatus="v">
						<input type="hidden" name="name" value="${k.name}" />
						<input type="hidden" name="birth" value="${k.birth}" />
						<input type="hidden" name="email" value="${k.email}" />
						<input type="hidden" name="phone" value="${k.phone}" />
						<input type="hidden" name="s_grade" value="${k.s_grade}" />
						<input type="hidden" name="dept_name" value="${k.s_dept}" />
						<input type="hidden" name="s_address" value="${k.s_address}" />
						<input type="hidden" name="s_address2" value="${k.s_address2}" />
					</c:forEach>
				</form>
				<c:if test="${not empty studentList}">
					<div>
						<br>
						<div class="table-container">
							<table>
								<thead>
									<tr>
										<th>NO</th>
										<th>이름</th>
										<th>생년월일</th>
										<th>이메일</th>
										<th>휴대폰 번호</th>
										<th>학년</th>
										<th>전공</th>
										<th>주소</th>
										<th>상세주소</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="k" items="${studentList}" varStatus="v">
										<tr>
											<td>${v.count}</td>
											<td>${k.name}</td>
											<td>${k.birth}</td>
											<td>${k.email}</td>
											<td>${k.phone}</td>
											<td>${k.s_grade}</td>
											<td>${k.s_dept}</td>
											<td>${k.s_address}</td>
											<td>${k.s_address2}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</c:if>
				<!-- 데이터가 없을 때 메시지 표시 -->
				<c:if test="${empty studentList}">
					<div>
						<div class="table-container">
							<table>
								<thead>
									<tr>
										<th>NO</th>
										<th>이름</th>
										<th>생년월일</th>
										<th>이메일</th>
										<th>휴대폰 번호</th>
										<th>학년</th>
										<th>전공</th>
										<th>주소</th>
										<th>상세주소</th>
									</tr>
								</thead>
								<tbody>
									<tr class="empty-tr">
										<!-- 고정 높이 설정 -->
										<td class="empty-td" colspan="9">불러온 데이터가 없습니다.</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	// 1. 초기화
	function cleanExcel() {
	    // 테이블 비우기
	    const tableBody = document.querySelector('table tbody');
	    tableBody.innerHTML = '';
	}

	// 2. 표가 비어있으면 일괄등록 안되게
	document.getElementById('excel-submit-btn').addEventListener('click', function(e) {
	    const tableBody = document.querySelector('table tbody');
	    if (tableBody.children.length === 0) {
	        e.preventDefault();  // 비어있으면 제출을 막는다
	        alert("저장할 데이터가 없습니다.");
	    }else{
	    	saveData();
	    }
	});
	

	</script>
</body>
</html>