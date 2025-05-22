<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../headerNavi.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학적 변경 신청 - 수정</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/student/sApplyForChangeUpdate.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<div id="wrap">
		<form method="post" enctype="multipart/form-data"
			action="/sApplyForChangeUpdateOk">
			<div class="title-container">
				<div class="highlight"></div>
				<h1>학적 변경 신청</h1>
				<div class="btn-group">
					<button type="submit" class="btn-submit">저장</button>
					<button type="button" onclick="goToDetail()" class="btn-cancel">취소</button>
					<input type="hidden" name="req_idx" value="${rvo.req_idx}" />
				</div>
			</div>

			<table>
				<tr>
					<th>이름</th>
					<td>${uvo.name}</td>
				</tr>
				<tr>
					<th>학번</th>
					<td>${uvo.user_id}</td>
				</tr>
				<tr>
					<th>신청구분</th>
					<td><select name="req_type">
							<option value="2" ${rvo.req_type == '2' ? 'selected' : ''}>휴학</option>
							<option value="1" ${rvo.req_type == '1' ? 'selected' : ''}>복학</option>
					</select></td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td>
						<div class="file-inputs" id="fileUploadWrapper">
							<c:choose>
								<c:when test="${empty fileList}">
									<p>첨부파일이 없습니다.</p>
								</c:when>
								<c:otherwise>
									<!-- 기존 파일들 -->
									<c:forEach items="${fileList}" varStatus="v" var="k">
										<div class="file-group">
											<span>현재 파일: <a
												href="/sRequestFileDown?req_f_name=${k.req_f_name}" class="a-black">
													${fn:substringAfter(k.req_f_name, '_')} </a>
											</span>
											<button type="button" class="remove-btn">삭제</button>

											<input type="hidden" name="existing_file"
												value="${k.req_f_name}" /> <input type="hidden"
												name="existing_file_old_name" value="${k.req_f_old_name}" />
											<br>
										</div>
									</c:forEach>
								</c:otherwise>

							</c:choose>
						</div> <!-- 새 파일 추가 버튼 -->
						<button type="button" id="add-file-btn">＋파일 추가</button>
					</td>
				</tr>
				<tr>
					<th>신청사유</th>
					<td><textarea name="req_reason" rows="10" cols="100" required>${rvo.req_reason}</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<script>
	function goToDetail(){
		location.href = "/sApplyForChangeDetail?req_idx=" + ${rvo.req_idx};
	}
	   document.addEventListener("DOMContentLoaded", function () {
	    	  const addBtn = document.getElementById("add-file-btn");
	    	  const container = document.querySelector(".file-inputs");

	    	  addBtn.addEventListener("click", function () {
	    	    const group = document.createElement("div");
	    	    group.className = "file-group";
	    	    group.innerHTML = `
	    	      <input type="file" name="files" accept=".jpg,.jpeg,.pdf,.doc,.docx,.ppt,.pptx,.txt" />
	    	      <button type="button" class="remove-btn">삭제</button>
	    	    `;
	    	    container.appendChild(group);
	    	  });

	    	  container.addEventListener("click", function (e) {
	    	    if (e.target.classList.contains("remove-btn")) {
	    	      const group = e.target.parentElement;
	    	      const hidden = group.querySelector("input[type='hidden']");
	    	      if (hidden) hidden.value = '';
	    	      group.remove();
	    	    }
	    	  });
	    	});
	    document.querySelector(".file-inputs").addEventListener("click", function (e) {
	    	  if (e.target.classList.contains("remove-btn")) {
	    	    e.target.parentElement.remove(); 
	    	  }
	    	});
	</script>
</body>
</html>
