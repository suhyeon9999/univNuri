<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/access.css">
<meta charset="UTF-8">
<title>성적조회</title>
</head>
<body>
<%@include file="../../headerNavi.jsp"%>
	<div id="wrap">
	<form action="" method="post">
	<div class="title-container">
        <div class="highlight"></div>
        <h1>사용자 권한 관리</h1>
      </div>
      <div class="search-btn">
      	<select>
      		<option>중간 관리자</option>
      		<option>일반 관리자</option>
      	</select>
        <button onclick="accessOK(this.form)" class="btn-blue">저장</button>
        <button onclick="accessBack(this.form)" class="btn-red">되돌리기</button>
        <input type="hidden" name="" value="" />
        </div>
      <div class="box-controll">
      <div class="insert">
    	  <table>
			<thead>
			<tr>
				<th><input type="checkbox" name="insert-chk" id="insert-all" class="insert-item"></th>
				<th>등록</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td><input type="checkbox" name="insert-chk" class="insert-item"></td>
				<td>유저 등록</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="insert-chk" class="insert-item"></td>
				<td>강의 등록</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="insert-chk" class="insert-item"></td>
				<td>수강 등록</td>
			</tr>
			</tbody>	
		</table>
		</div>
      <div class="select">
    	  <table>
			<thead>
			<tr>
				<th><input type="checkbox" id="select-all" class="select-item"></th>
				<th>조회</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td><input type="checkbox" class="select-item"></td>
				<td>사용자 조회</td>
			</tr>
			<tr>
				<td><input type="checkbox" class="select-item"></td>
				<td>강의 조회</td>
			</tr>
			<tr>
				<td><input type="checkbox" class="select-item"></td>
				<td>수강 조회</td>
			</tr>
			</tbody>	
		</table>
		</div>
      <div class="update">
    	  <table>
			<thead>
			<tr>
				<th><input type="checkbox" id="update-all" class="update-item"></th>
				<th>수정</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td><input type="checkbox" class="update-item"></td>
				<td>사용자 수정</td>
			</tr>
			<tr>
				<td><input type="checkbox" class="update-item"></td>
				<td>강의 수정</td>
			</tr>
			<tr>
				<td><input type="checkbox" class="update-item"></td>
				<td>수강 수정</td>
			</tr>
			<tr>
				<td><input type="checkbox" class="update-item"></td>
				<td>개인정보 수정</td>
			</tr>
			</tbody>	
		</table>
		</div>
      <div class="delete">
    	  <table>
			<thead>
			<tr>
				<th><input type="checkbox" id="delete-all" class="delete-item"></th>
				<th>삭제</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td><input type="checkbox" class="delete-item"></td>
				<td>사용자 삭제</td>
			</tr>
			<tr>
				<td><input type="checkbox" class="delete-item"></td>
				<td>강의 삭제</td>
			</tr>
			<tr>
				<td><input type="checkbox" class="delete-item"></td>
				<td>수강 삭제</td>
			</tr>
			<tr>
				<td><input type="checkbox" class="delete-item"></td>
				<td>개인정보 삭제</td>
			</tr>
			</tbody>	
		</table>
		</div>
	</div>
	</form>
</div>
<script type="text/javascript">
document.getElementById("insert-all").addEventListener("change", function () {
    const isChecked = this.checked;
    const checkboxes = document.querySelectorAll(".insert-item");

    checkboxes.forEach(cb => {
      cb.checked = isChecked;
    });
  });
document.getElementById("select-all").addEventListener("change", function () {
    const isChecked = this.checked;
    const checkboxes = document.querySelectorAll(".select-item");

    checkboxes.forEach(cb => {
      cb.checked = isChecked;
    });
  });
document.getElementById("update-all").addEventListener("change", function () {
    const isChecked = this.checked;
    const checkboxes = document.querySelectorAll(".update-item");

    checkboxes.forEach(cb => {
      cb.checked = isChecked;
    });
  });
document.getElementById("delete-all").addEventListener("change", function () {
    const isChecked = this.checked;
    const checkboxes = document.querySelectorAll(".delete-item");
    checkboxes.forEach(cb => {
      cb.checked = isChecked;
    });
  });
	function accessOK(f){
		
	}
	function accessBack(f){
		
	}
</script>
</body>
</html>