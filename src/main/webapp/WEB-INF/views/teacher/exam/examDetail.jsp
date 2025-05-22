<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../headerNavi.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/common.css">
<link rel="stylesheet" type="text/css" href="/resources/css/commonBoard.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/headerNavi.css">
    <style>
    
       #modalContainer {
        width: 100vw;
        height: 100vh;
        position: fixed;
        top: 0;
        left: 0;
        display: none;
        justify-content: center;
        align-items: center;
        background: rgba(0, 0, 0, 0.5);
        z-index: 1000;
      }

      #modalContent {
        background-color: #ffffff;
        width: 400px;
        height: 250px;
        padding: 15px;
        text-align: center;
        border: 1px solid black;
        border-radius: 10px;
        position: relative;
        top: 0;
        left: 0;
      }

      h2 {
        margin: 15px;
      }

      .btn-group {
        display: flex;
        justify-content: center;
        gap: 30px;
        margin: 10px 0;
      }
      .modal-btn-group input[type="submit"],
      input[type="reset"] {
        margin-top: 10px;
        background-color: #3f51b5;
      }

      input[type="text"] {
        width: 70%;
        height: 40px;
        padding: 5px;
        border: 1px solid #ccc;
        border-radius: 4px;
        font-size: 14px;
        margin-bottom: 10px;
      }

      .descript {
        font-size: 12px;
        color: red;
        margin: 20px 0;
      }
      
      .title-container {
        position: relative;
      }
      .highlight {
        position: relative;
        top: 45px;
        width: 200px;
        height: 13px;
        z-index: 0;
        background-color: cornflowerblue;
      }
      .title-container h1 {
        position: relative;
        z-index: 1;
        margin: 0;
      }
      .btn-group {
        display: flex;
        justify-content: flex-end;
        gap: 5px;
        margin-bottom: 20px;
      }
      .btn-group button, input[type="reset"], 
      input[type="submit"] {
        width: 80px;
        height: 38px;
        border: none;
        border-radius: 5px;
        font-size: 15px;
        color: white;
        cursor: pointer;
      }
      .btn-cancel {
        background-color: #f44336;
      }
      .btn-submit {
        background-color: #3f51b5;
      }
      table {
        border-collapse: collapse;
        width: 100%;
        background-color: white;
      }
      th {
        background-color: #e2e2f8;
        width: 30%;
      }
      th,
      td {
        border: 1px solid black;
        padding: 12px;
        text-align: left;
        text-align: center;
      }
      input[type="text"],
      input[type="datetime-local"],
      input[type="file"],
      textarea {
        width: 95%;
        padding: 5px;
        border: 1px solid #ccc;
        border-radius: 4px;
        font-size: 14px;
      }
      textarea {
        resize: vertical;
        height: 100px;
      }
      label {
        margin-right: 10px;
      }
      .btn-group {
        display: flex;
        justify-content: flex-end;
        gap: 5px;
        margin-bottom: 20px;
      }
      .btn-list {
        margin-top: 10px;
        background-color: gray;
        position: relative;
        left: 1070px;
      }
      hr {
        height: 2.5px;
        background-color: lightgray;
        border: none;
      }
      button {
        width: 80px;
        height: 38px;
        border: none;
        border-radius: 5px;
        font-size: 15px;
        color: white;
        cursor: pointer;
      }
      .btn-delete {
        background-color: #f44336;
      }
      .btn-update {
        background-color: #3f51b5;
      }
    </style>
  </head>
  <body>
    <div id="wrap">
      <div class="title-container">
        <div class="highlight"></div>
        <h1>시험 출제</h1>
      </div>

      <div class="btn-group">
        <button class="btn-update" onclick="go_exam_update()">수정</button>
        <button class="btn-delete" onclick="exam_delete_modal()">삭제</button>
      </div>
      <input type="hidden" name="lect_idx" value="${lect_idx}" />
      <table>
        <tr>
          <th>강의명</th>
          <td>${examDetail.lect_name}</td>
        </tr>
        <tr>
          <th>교수명</th>
          <td>${examDetail.name}</td>
        </tr>
        <tr>
          <th>시험명</th>
          <td>${examDetail.test_subject}</td>
        </tr>
        <tr>
          <th>시험구분</th>
          <td>${examDetail.midFinalName}</td>
        </tr>
        <tr>
          <th>시험날짜</th>
          <td>
            <fmt:formatDate
              value="${examDetail.test_date}"
              pattern="yyyy-MM-dd"
            />
          </td>
        </tr>
        <tr>
          <th>시험시간</th>
          <td>${examDetail.teststart_time} ~ ${examDetail.testend_time}</td>
        </tr>
        <tr>
          <th>첨부파일</th>
          <td><c:if test="${not empty examDetail.f_name}"><a href="/exam-down?f_name=${examDetail.f_name}">
              <span style="color: gray">${fn:substringAfter(examDetail.f_name, "_")}</span></a>
            </c:if>
            <c:if test="${empty examDetail.f_name}">
              <span style="color: gray">첨부파일 없음</span>
            </c:if></td>
        </tr>
      </table>
      <button class="btn-list" onclick="go_exam_list()">목록</button>
    </div>
    
        <div id="modalContainer">
      <div id="modalContent">
        <form action="exam-delete-ok">
          <h2>시험 삭제 확인</h2>
          <p class="descript">비밀번호를 입력하세요.</p>
          <input type="text" placeholder="비밀번호 입력" name="user_pw" />
          <!-- hidden 타입에 값 연결 필요 -->
          <input type="hidden" name="t_idx" value="${t_idx}">
          <input type="hidden" name="test_make_idx" value="${test_make_idx}">
          <input type="hidden" name="lect_idx" value="${lect_idx}">
          <div class="modal-btn-group">
            <input type="submit" value="확인" />
            <input type="reset" value="취소" onclick="close_modal()" />
          </div>
        </form>
      </div>
    </div>
    
    <script>
      function go_exam_list() {
        location.href = "/exam-list?lect_idx="+"${lect_idx}";
      };
      
      function go_exam_update() {
        location.href = "/exam-update?test_make_idx="+"${test_make_idx}"+"&lect_idx="+"${lect_idx}";
      };
      
      function exam_delete_modal() {
          document.getElementById("modalContainer").style.display = "flex";
        };

       function close_modal() {
         document.getElementById("modalContainer").style.display = "none";
       };
        
       window.onload = function () {
           if ("${param.pwdchk}" === 'fail') {
               alert("비밀번호가 일치하지 않습니다.");
           }
       };
    </script>
  </body>
</html>
