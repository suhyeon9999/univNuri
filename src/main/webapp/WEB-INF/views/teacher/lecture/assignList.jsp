<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../headerNavi.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/headerNavi.css">
<link rel="stylesheet" type="text/css" href="/resources/css/common.css">
<script src="https://kit.fontawesome.com/67e1f0bf0d.js"
	crossorigin="anonymous"></script>
<style>
.nav-container {
	margin-top: 20px;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.nav-left, .nav-right {
	display: flex;
}

.nav-left div, .nav-right div {
	width: 110px;
	height: 50px;
	text-align: center;
	line-height: 50px;
	border-radius: 8px 8px 0 0;
	margin-right: 2px;
}

.tab-btn active {
	background-color: #fdecb2;
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

.nav-container .assign a {
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
}

.count {
	margin: 0;
}

.count span {
	color: orange;
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

.name{
	color: black;
}

.accent {
	color: red;
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
					<a href="/get-lecture-attendance-list?lect_idx=${lect_idx}">출결
						관리</a>
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
			<p class="count">
				전체 과제 : <span>${ListSize}</span>
			</p>
			<div class="main-top">

				<input type="hidden" id="lect_idx" name="lect_idx"
					value="${lect_idx}" />
				<div class="search-box">
					<p>제출 기간</p>
					<select id="end" name="end" >
						<option value="all">전체</option>
						<option value="0">제출 가능</option>
						<option value="1">기간 만료</option>
					</select>
					<button type="submit" class="search-btn" onclick="filter_assign_list()">
						<i class="fa-solid fa-magnifying-glass"></i>
					</button>
				</div>

				<button onclick="go_assign_make()" id="assign-btn" class="btn-blue" onchange="filter_assign_list()">출제</button>
			</div>

			<!-- 과제 리스트 그릴 영역 ID 지정 -->
			<div class="assign-list-wrapper" id="assign-list-container">
				<%-- JS에서 여기를 지우고 새로 그림 --%>
				<c:forEach var="k" items="${assignList}" varStatus="v">
					<div class="assign-list">
						<h2>
							<a class="name" href="/assign-detail?assign_idx=${k.assign_idx}&lect_idx=${lect_idx}"> ${v.index + 1}. ${k.assign_title}</a>
						</h2>
						<div class="time">
							<p>제출 시작</p>
							<span class="${k.end==1?'':'accent'}"><fmt:formatDate
									value="${k.upload_time}" pattern="yyyy-MM-dd HH:mm" /></span>
						</div>
						<div class="time">
							<p>제출 종료</p>
							<span class="${k.end==1?'':'accent'}"><fmt:formatDate
									value="${k.assign_due_date}" pattern="yyyy-MM-dd HH:mm" /></span>
						</div>
						<div class="count">
							<p>제출</p>
							<p class="submit">${k.submit_count}</p>
						</div>
						<div class="count">
							<p>미제출</p>
							<p class="not-submit">${k.unsubmit_count}</p>
						</div>
					</div>
				</c:forEach>
			</div>

		</div>
	</div>
	<script>
	function go_assign_make(){
		location.href="/assign-make?lect_idx="+${lect_idx};
	}
	
	
	
</script>


	<script type="text/javascript">
function filter_assign_list() {
    const end = document.getElementById("end").value;
    const lect_idx = document.getElementById("lect_idx").value;


    fetch("/get-assign", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ end: end, lect_idx })
    })
    .then(response => response.json())
    .then(data => {
        console.log("서버에서 받은 데이터:", data);

        const container = document.getElementById("assign-list-container");
        container.innerHTML = "";

        if (!data || data.length === 0) {
            const p = document.createElement("p");
            p.textContent = "해당 조건에 맞는 과제가 없습니다.";
            container.appendChild(p);
            return;
        }

        data.forEach(function(k, index) {
            const div = document.createElement("div");
            div.className = "assign-list";

            div.innerHTML = 
                '<h2><a class="name" href="/assign-detail?assign_idx=' + k.assign_idx + '&lect_idx=' + lect_idx + '">' +
                (index + 1) + '. ' + k.assign_title + '</a></h2>' +
                '<div class="time">' +
                    '<p>제출 시작</p>' +
                    '<span class="' + (k.end == 1 ? '' : 'accent') + '">' + formatDateTime(k.upload_time) + '</span>' +
                '</div>' +
                '<div class="time">' +
                    '<p>제출 종료</p>' +
                    '<span class="' + (k.end == 1 ? '' : 'accent') + '">' + formatDateTime(k.assign_due_date) + '</span>' +
                '</div>' +
                '<div class="count">' +
                    '<p>제출</p>' +
                    '<p class="submit">' + k.submit_count + '</p>' +
                '</div>' +
                '<div class="count">' +
                    '<p>미제출</p>' +
                    '<p class="not-submit">' + k.unsubmit_count + '</p>' +
                '</div>';

            container.appendChild(div);
        });
    })
    .catch(error => {
        console.error("에러 발생:", error);
    });

}

// 날짜 포맷 함수 (YYYY-MM-DD HH:mm)
function formatDateTime(dateStr) {
    const date = new Date(dateStr);
    const yyyy = date.getFullYear();
    const mm = String(date.getMonth() + 1).padStart(2, '0');
    const dd = String(date.getDate()).padStart(2, '0');
    const hh = String(date.getHours()).padStart(2, '0');
    const min = String(date.getMinutes()).padStart(2, '0');
    return yyyy + '-' + mm + '-' + dd + ' ' + hh + ':' + min;
}

</script>

</body>
</html>