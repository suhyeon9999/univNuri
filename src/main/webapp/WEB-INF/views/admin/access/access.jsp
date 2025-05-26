<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/access.css">
  <meta charset="UTF-8">
  <title>사용자 권한 관리</title>
</head>
<body>
<%@include file="../../headerNavi.jsp"%>
<div id="wrap">
  <form action="/accessSave" method="post">
    <div class="title-container">
      <div class="highlight"></div>
      <h1>사용자 권한 관리</h1>
    </div>

    <div class="search-btn">
      <select id="gradeSelector" name="a_grade">
        <option value="1" ${selectedGrade == '1' ? 'selected' : ''}>중간 관리자</option>
        <option value="2" ${selectedGrade == '2' ? 'selected' : ''}>일반 관리자</option>
      </select>
      <button type="submit" class="btn-blue">저장</button>
      <button type="button" class="btn-red" onclick="location.href='/access?a_grade=${selectedGrade}'">되돌리기</button>
    </div>

    <div class="box-controll">

  <!-- 등록 -->
  <div class="insert">
    <table>
      <thead><tr><th><input type="checkbox" id="insert-all"></th><th>등록</th></tr></thead>
      <tbody>
        <tr><td><input type="hidden" name="acc_user_insert" value="0" /><input type="checkbox" name="acc_user_insert" value="1" class="insert-item" <c:if test="${access.acc_user_insert == '1'}">checked</c:if> /></td><td>유저 등록</td></tr>
        <tr><td><input type="hidden" name="acc_lect_insert" value="0" /><input type="checkbox" name="acc_lect_insert" value="1" class="insert-item" <c:if test="${access.acc_lect_insert == '1'}">checked</c:if> /></td><td>강의 등록</td></tr>
        <tr><td><input type="hidden" name="acc_enroll_insert" value="0" /><input type="checkbox" name="acc_enroll_insert" value="1" class="insert-item" <c:if test="${access.acc_enroll_insert == '1'}">checked</c:if> /></td><td>수강 등록</td></tr>
      </tbody>
    </table>
  </div>

  <!-- 조회 -->
  <div class="select">
    <table>
      <thead><tr><th><input type="checkbox" id="select-all"></th><th>조회</th></tr></thead>
      <tbody>
        <tr><td><input type="hidden" name="acc_user_search" value="0" /><input type="checkbox" name="acc_user_search" value="1" class="select-item" <c:if test="${access.acc_user_search == '1'}">checked</c:if> /></td><td>사용자 조회</td></tr>
        <tr><td><input type="hidden" name="acc_lect_search" value="0" /><input type="checkbox" name="acc_lect_search" value="1" class="select-item" <c:if test="${access.acc_lect_search == '1'}">checked</c:if> /></td><td>강의 조회</td></tr>
        <tr><td><input type="hidden" name="acc_enroll_search" value="0" /><input type="checkbox" name="acc_enroll_search" value="1" class="select-item" <c:if test="${access.acc_enroll_search == '1'}">checked</c:if> /></td><td>수강 조회</td></tr>
      </tbody>
    </table>
  </div>

  <!-- 수정 -->
  <div class="update">
    <table>
      <thead><tr><th><input type="checkbox" id="update-all"></th><th>수정</th></tr></thead>
      <tbody>
        <tr><td><input type="hidden" name="acc_user_update" value="0" /><input type="checkbox" name="acc_user_update" value="1" class="update-item" <c:if test="${access.acc_user_update == '1'}">checked</c:if> /></td><td>사용자 수정</td></tr>
        <tr><td><input type="hidden" name="acc_lect_update" value="0" /><input type="checkbox" name="acc_lect_update" value="1" class="update-item" <c:if test="${access.acc_lect_update == '1'}">checked</c:if> /></td><td>강의 수정</td></tr>
        <tr><td><input type="hidden" name="acc_enroll_update" value="0" /><input type="checkbox" name="acc_enroll_update" value="1" class="update-item" <c:if test="${access.acc_enroll_update == '1'}">checked</c:if> /></td><td>수강 수정</td></tr>
        <tr><td><input type="hidden" name="acc_info_update" value="0" /><input type="checkbox" name="acc_info_update" value="1" class="update-item" <c:if test="${access.acc_info_update == '1'}">checked</c:if> /></td><td>개인정보 수정</td></tr>
      </tbody>
    </table>
  </div>

  <!-- 삭제 -->
  <div class="delete">
    <table>
      <thead><tr><th><input type="checkbox" id="delete-all"></th><th>삭제</th></tr></thead>
      <tbody>
        <tr><td><input type="hidden" name="acc_user_delete" value="0" /><input type="checkbox" name="acc_user_delete" value="1" class="delete-item" <c:if test="${access.acc_user_delete == '1'}">checked</c:if> /></td><td>사용자 삭제</td></tr>
        <tr><td><input type="hidden" name="acc_lect_delete" value="0" /><input type="checkbox" name="acc_lect_delete" value="1" class="delete-item" <c:if test="${access.acc_lect_delete == '1'}">checked</c:if> /></td><td>강의 삭제</td></tr>
        <tr><td><input type="hidden" name="acc_enroll_delete" value="0" /><input type="checkbox" name="acc_enroll_delete" value="1" class="delete-item" <c:if test="${access.acc_enroll_delete == '1'}">checked</c:if> /></td><td>수강 삭제</td></tr>
        <tr><td><input type="hidden" name="acc_info_delete" value="0" /><input type="checkbox" name="acc_info_delete" value="1" class="delete-item" <c:if test="${access.acc_info_delete == '1'}">checked</c:if> /></td><td>개인정보 삭제</td></tr>
      </tbody>
    </table>
  </div>

</div>

  </form>
</div>

<script type="text/javascript">
  ['insert', 'select', 'update', 'delete'].forEach(type => {
    document.getElementById(type + "-all")?.addEventListener("change", function () {
      const isChecked = this.checked;
      document.querySelectorAll("." + type + "-item").forEach(cb => cb.checked = isChecked);
    });
  });
  document.getElementById("gradeSelector").addEventListener("change", function () {
	    const grade = this.value;
	    location.href = "/access?a_grade=" + grade;  // 조회만 함
	  });
</script>
</body>
</html>