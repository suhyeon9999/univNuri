<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../headerNavi.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/headerNavi.css">
    <style>
    .main-container{
		overflow-x:hidden;  
		overflow-y:auto; 
		}
      .nav-container {
        margin-top: 20px;
        display: flex;
        justify-content: space-between;
        align-items: center;
      }

      .nav-left,
      .nav-right {
        display: flex;
      }
      .nav-left div,
      .nav-right div {
        width: 110px;
        height: 50px;
        text-align: center;
        line-height: 50px;
        border-radius: 8px 8px 0 0;
        margin-right: 2px;
      }
      .list {
        background-color: #fdecb2;
      }

      .attend {
        background-color: #e7f2d8;
      }
      .assign {
        background-color: #b9e5fc;
      }
      .exam {
        background-color: #f9bcb5;
      }
      .detail {
        background-color: #d9c5ec;
      }

      .nav-container div a {
        text-decoration: none;
        color: gray;
      }
      .nav-container .exam a {
        color: black;
        font-weight: bold;
      }
      .main-container {
        background-color: #f2f2f2;
        padding: 10px 20px 20px 20px;
      }

      .main-top {
        display: flex;
        justify-content: space-between;
        margin: 15px 0;
      }
      .exam-condition {
        display: flex;
        gap: 15px;
        margin-top: 5px;
      }

      .assign-list {
        background-color: white;
        border-radius: 5px;
        display: flex;
        justify-content: space-between;
        padding: 20px;
        align-items: center;
        margin: 4px;
        height: 90px;
      }

      .assign-list div {
        display: flex;
        flex-direction: column;
        align-items: center;
      }
      .assign-list div p {
        margin: 2px;
      }
      .assign-list h2 {
        width: 350px;
      }
      .submit {
        color: blue;
      }
      .not-submit {
        color: red;
      }
      .search-box {
        display: flex;
        justify-content: flex-start;
        gap: 10px;
        align-items: center;
      }
      .search-btn {
        margin: 10px 0;
        padding: 10px;
        border: none;
        background-color: orange;
        color: white;
        border-radius: 5px;
        cursor: pointer;
        width: 35px;
      }
      select {
        padding: 8px;
        margin-left: 5px;
        height: 35px;
        width: 180px;
      }
      .name {
        color: black;
      }
    </style>
  </head>
  <body>
    <div id="wrap">
      <div class="title-container">
        <div class="highlight"></div>
        <h1>자바 기초</h1>
      </div>
		<div class="nav-container">
			<div class="nav-left">
				<div class="list">
					<a href="/get-lecture-student-list?lect_idx=${lect_idx}">학생 목록</a>
				</div>
				<div class="attend">
					<a href="/get-lecture-attendance-list?lect_idx=${lect_idx}">출결 관리</a>
				</div>
				<div class="assign">
					<a href="/assign-list?lect_idx=${lect_idx}">과제 현황</a>
				</div>
				<div class="exam">
					<a href="/exam-list?lect_idx=${lect_idx}">시험 출제</a>
				</div>
			</div>
			<div class="nav-right">
				<div class="detail">
					<a href="/lecture-info?lect_idx=${lect_idx}">강의 정보</a>
				</div>
			</div>
		</div>
      <div class="main-container">
        <div class="main-top">
          <div class="exam-condition">
            <p>중간고사: <span class="${resultMid==0?'not-submit':'submit'}">${resultMid==0? "출제 전" : "출제완료"}</span></p>
            <p>기말고사: <span class="${retusltFinal==0?'not-submit':'submit'}">${retusltFinal==0? "출제 전" : "출제완료"}</span></p>
          </div>
          <button onclick="go_exam_make()" id="assign-btn" class="btn-blue">출제</button>
        </div>

        <!-- 내용 없으면 없다고 나와야 함 -->
        <div class="assign-list-wrapper">
           <c:choose>
	          <c:when test="${empty examList}">
		          출제한 시험이 존재하지 않습니다. 
	          </c:when>
            <c:otherwise>
              <c:forEach var="k" items="${examList}" varStatus="v">
                <div class="assign-list">
                  <!-- assign_idx 수정 필요 -->
                  <h2>
                    <a class="name"  href="/exam-detail?lect_idx=${lect_idx}&test_make_idx=${k.test_make_idx}"
                      >${v.index + 1}. ${k.test_subject}</a
                    >
                  </h2>
                  <div class="time">
                    <p>시험 구분</p>
                    <p class="not-submit">${k.mid_final == 0 ? '중간고사' : '기말고사'}</p>
                  </div>
                  <div class="time">
                    <p>시험날짜</p>
                    <p class="not-submit">
                      <fmt:formatDate
                        value="${k.test_date}"
                        pattern="yyyy-MM-dd"
                      />
                    </p>
                  </div>
                  <div class="count">
                    <p>시험시간</p>
                     <p class="not-submit"><fmt:formatDate value="${k.teststart_time}" pattern="HH:mm"/> - <fmt:formatDate value="${k.testend_time}" pattern="HH:mm"/></p> 
                  </div>
                </div>
              </c:forEach>
            </c:otherwise>
          </c:choose> 
        </div>
      </div>
    </div>
    <script>
      function go_exam_make() {
        location.href = "/exam-make?lect_idx="+"${lect_idx}";
      }
    </script>
  </body>
</html>