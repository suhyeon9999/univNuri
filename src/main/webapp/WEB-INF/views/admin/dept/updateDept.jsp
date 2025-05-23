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
		<form action="/updateDept" method="get"   onsubmit="return checkForm();" >
			<div class="total-container">
				<div class="title-container">
					<div class="highlight"></div>
					<h1>학과 수정</h1>
				</div>
				<div class="btn-group">
					<button class="btn-red"
						onclick="location.href='${pageContext.request.contextPath}/detailDept?dept_idx=${detailDept.dept_idx}'">취소</button>
					<input type="submit" class="btn-blue" value="수정" />			
					<input type="hidden"	 name="dept_idx" value="${detailDept.dept_idx }">
				</div>
				<table>
					 <tr>
			              <th><label for="dept_name">학과명</label></th>
			              <td><input type="text" name="dept_name"  value="${detailDept.dept_name }"  id="dept_name" required></td>
			          </tr>		            
					 <tr>
			              <th><label for="dept_id">학과코드</label></th>
			              <td><input type="text" name="dept_id" value="${detailDept.dept_id }" required disabled></td>
			          </tr>	
			          <tr>
			                <th><label for="name">학과장</label></th>
			                <td>
			                	<select id="t_idx" name="t_idx">
			                		<c:if test="${empty detailDept.t_idx }">
			                		<option value="0">미배정</option>
			                		</c:if>									
		                                <c:forEach var="k" items="${teacherList}">
	                                    <option value="${k.t_idx }"
								            <c:if test="${k.t_idx == detailDept.t_idx}">selected</c:if>>
								            ${k.name}
								        </option>
	                                </c:forEach>	                                
	                            </select>
			                </td>
			            </tr>	            
				</table>
			</div>
		</form>
	</div>
<script type="text/javascript">
		function checkForm() {
			  var dept_name = document.getElementById("dept_name").value;
			 
	
			  // 이름: 비어 있지 않은지 확인
			  if (dept_name.trim() === "") {
			    alert("학과이름을 입력해주세요.");
			    return false;
			  }
	
		
	
			  return true; // 모든 검사를 통과하면 폼 제출
			}
	</script>
</body>
</html>
