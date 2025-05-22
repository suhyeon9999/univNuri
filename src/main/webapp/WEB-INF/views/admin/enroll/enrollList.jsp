<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강의 관리</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/admin/lectList.css">
<script src="https://kit.fontawesome.com/67e1f0bf0d.js"
	crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<%@ include file="../../headerNavi.jsp"%>
	<div id="wrap">
		<!-- 강의 관리 타이틀 -->
		<div class="title-container">
			<div class="highlight"></div>
			<h1>수강 관리</h1>
		</div>

		<!-- 탭 버튼 -->
		<div class="nav-container">
			<div class="lect" data-tab="lecture">
				<a href="#">개강된 강의</a>
			</div>
		</div>

		<!-- 탭별 콘텐츠 -->
		<div class="tab-content-container">
			<!-- 강의개설 (기본 활성화) -->
			<div class="tab-content active" id="lecture">
				<div class="main-container">
					<div class="btn-group">
						<button class="btn-green" id="long-btn"
							onclick="location.href='${pageContext.request.contextPath}/moveEnrollApplyList'">수강신청
							관리</button>
					</div>
					<p class="count">
						개강된 강의 : <span id="lecture-count">${lectListNum}</span>
					</p>
					<!-- 검색 폼 -->
					<div class="main-top">
						<form action="/searchLectEnroll" method="get" class="form-top"
							id="lecture-search-form">
							<div class="search-area">
								<div class="search-row">
									<label>개설학과</label> <select id="date" name="dept_name">
										<option value="">전체</option>
										<c:forEach var="d" items="${deptList}">
											<option value="${d.dept_name}">${d.dept_name}</option>
										</c:forEach>

									</select> <label for="semester">학기</label> 
									<select id="semester" name="semester">
										<option value="">전체</option>
									</select>
								</div>
								<div class="search-row">
									<label>강의 코드</label> <input type="text" name="lect_id" value="">
									<label>강의명</label> <input type="text" name="lect_name">
									<label>담당교수</label><input type="text" name="name">
									<button type="submit" class="btn-orange">
										<i class="fa-solid fa-magnifying-glass"></i>
									</button>
								</div>
							</div>
						</form>
					</div>
					<!--수강 리스트 -->
					<div class="search-list-wrapper" id="lecture-list">
						<c:choose>
							<c:when test="${empty lectList}">
								<p>현재 표시할 강의가 없습니다. 검색 조건을 입력하세요.</p>
							</c:when>
							<c:otherwise>
								<c:forEach var="k" items="${lectList}" varStatus="v">
									<div class="search-list">
										<h2><a href="/detailLect?lect_idx=${k.lect_idx}">${v.index + 1}.${k.lect_name}</a></h2>
										<div>
											<p>강의코드</p>
											<p>${k.lect_id}</p>
										</div>
										<div>
											<p>개설학과</p>
											<p>${k.dept_name}</p>
										</div>
										<div>
											<p>학점</p>
											<p>${k.lect_credit}</p>
										</div>
										<div>
											<p>교수</p>
											<p>${k.name}</p>
										</div>
										<div>
											<p>강의정원</p>
											<p>${k.class_max}</p>
										</div>
									</div>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
// currentYear1이 이미 선언되지 않았다면, 한번만 선언하도록 처리
if (typeof currentYear1 === "undefined") {
var currentYear1 = new Date().getFullYear(); // 'const' 대신 'var' 또는 기존 선언을 활용
}

// 'yearSelect'가 이미 선언되지 않았다면 선언
let yearSelect = document.getElementById("semester");

// 최근 5년간의 연도 옵션 추가
for (let i = 0; i < 10; i++) {
const year = currentYear1 - i;

const option1 = document.createElement("option");
option1.value = year + "-1";
option1.innerText = year + "-1"; // 학기 -1
yearSelect.appendChild(option1);

const option2 = document.createElement("option");
option2.value = year + "-2";
option2.innerText = year + "-2"; // 학기 -2
yearSelect.appendChild(option2);
}
/* $(document).ready(function() {
$('#lecture-search-form').submit(function(event) {
event.preventDefault(); // 기본 제출 방지

$.ajax({
url : '/searchLectEnroll',
method : 'GET',
data : $(this).serialize(),
success : function(response) {
$('#lecture-list').html(response); // 리스트 영역 교체
},
error : function() {
alert("검색 중 오류가 발생했습니다.");
}
});
});
}); */

$(document).ready(function() {
  $('#lecture-search-form').submit(function(event) {
    event.preventDefault(); // 기본 제출 방지

    $.ajax({
      url: '/searchLectEnroll',
      method: 'GET',
      data: $(this).serialize(),
      success: function(response) {
        // 임시 div 생성해서 응답 HTML을 파싱
        const tempDiv = $('<div>').html(response);

        // 새로 받은 강의 개수 추출
        const newCount = tempDiv.find('#ajax-lecture-count').text();
        $('#lecture-count').text(newCount); // 기존 카운트 업데이트

        // 강의 리스트만 교체
        const newListHtml = tempDiv.find('#ajax-lecture-list').html();
        $('#lecture-list').html(newListHtml);
      },
      error: function() {
        alert("검색 중 오류가 발생했습니다.");
      }
    });
  });
});

</script>
</body>
</html>