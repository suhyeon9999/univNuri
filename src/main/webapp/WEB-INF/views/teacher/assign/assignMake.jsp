<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../headerNavi.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/headerNavi.css">
	<link rel="stylesheet" type="text/css" href="/resources/css/common.css">
    <style>
      .total-container {
        margin: 20px;
        max-width: 900px;
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
      .btn-group button,
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
      textarea {
        width: 95%;
        padding: 5px;
        border: 1px solid #ccc;
        border-radius: 4px;
        font-size: 14px;
      }
      .file-inputs{
      	display: flex;
      }
      .file-group{
      	margin-bottom: 3px;
      }
      .remove-btn,
      #add-file-btn {
        padding: 5px;
        border: 1px solid #ccc;
        border-radius: 4px;
        font-size: 14px;
        background-color: #404cac;
        height: 38px;
        border-radius: 5px;
        border: none;
      }
      .remove-btn {
        width: 100px;
      }
      #add-file-btn {
        margin-top: 10px;
        width: 95%;
      }
      input[type="file"] {
        width: 81%;
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
    </style>
  </head>
  <body>
    <div id="wrap">
      <div class="title-container">
        <div class="highlight"></div>
        <h1>과제 출제</h1>
      </div>

      <!-- 폼 제작 파일 관련 enctype 있어야 함-->
      <form
        method="post"
        action="/assign-list-make-ok"
        enctype="multipart/form-data"
      >
        <div class="btn-group">
          <button type="button" onclick="go_assign_list()" class="btn-red">
            취소
          </button>
          <input type="submit" value="등록" class="btn-blue" />
        </div>
        <input type="hidden" name="lect_idx" value="${lect_idx}" />
        <table>
          <tr>
            <th>강의명</th>
            <td>${getTLecName.lect_name}</td>
          </tr>
          <tr>
            <th>교수명</th>
            <td>${getTLecName.name}</td>
          </tr>
          <tr>
            <th>과제 제목</th>
            <td><input type="text" name="assign_title" required /></td>
          </tr>
          <tr>
            <th>제출 시작일</th>
            <td><input type="datetime-local" name="start_time" required /></td>
          </tr>
          <tr>
            <th>제출 마감일</th>
            <td>
              <input type="datetime-local" name="assign_due_date" required />
            </td>
          </tr>
          <tr>
            <th>첨부파일</th>
            <td>
              <div id="file-inputs">
                <div class="file-group">
                  <input type="file" name="files" />
                  <button type="button" class="remove-btn">목록 삭제</button>
                </div>
              </div>
              <button type="button" id="add-file-btn">＋파일 추가</button>
            </td>
          </tr>
          <tr>
            <th>과제 설명 및 내용</th>
            <td>
              <textarea
                placeholder="과제에 대한 설명을 입력하세요."
                name="assign_content"
              ></textarea>
            </td>
          </tr>
        </table>
      </form>
    </div>

    <script>
      function go_assign_list() {
        location.href = "/assign-list?lect_idx="+${lect_idx};
      }
      document.addEventListener("DOMContentLoaded", function () {
        const addBtn = document.getElementById("add-file-btn");
        const container = document.getElementById("file-inputs");

        addBtn.addEventListener("click", function () {
          const group = document.createElement("div");
          group.className = "file-group";
          group.innerHTML = `
            <input type="file" name="files" />
            <button type="button" class="remove-btn">목록 삭제</button>
          `;
          container.appendChild(group);
        });

        container.addEventListener("click", function (e) {
          if (e.target.classList.contains("remove-btn")) {
            e.target.parentElement.remove();
          }
        });
      });
    </script>
  </body>
</html>