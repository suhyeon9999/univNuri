<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수강신청 관리</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/admin/lectList.css">
<script src="https://kit.fontawesome.com/67e1f0bf0d.js"
	crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<style type="text/css">
table {
	border-collapse: collapse;
	width: 100%;
	background-color: white;
}

table thead th {
	height: 10px; /* 원하는 높이로 설정 */
	line-height: 10px; /* 수직 정렬 맞추기 */
	background-color: #e2e2f8;
}

th, td {
	border: 1px solid black;
	padding: 12px;
	text-align: left;
	text-align: center;
}

.search-row {
	display: flex;
	align-items: center;
	margin-bottom: 12px;
}

.search-row label {
	width: 160px;
	font-weight: bold;
}

.search-row input {
	padding: 10px 12px;
	width: 300px; /* ← 넓이 키움 */
	font-size: 15px;
}

.main-top {
	display: flex;
	justify-content: space-between;
	justify-content: center;
	align-items: center;
}

.btn-group {
	display: flex;
	justify-content: center;
}

button {
	padding: 10px 20px;
	font-size: 14px;
	min-width: 120px; /* ← 버튼 가로 크기 최소값 설정 */
	border: none;
	border-radius: 4px;
	cursor: pointer;
	text-align: center;
}

.count-wrap {
	text-align: right;
	margin-top: 0px; /* 기존보다 줄임 */
	line-height: 1.4;
	font-size: 16px;
	font-weight: bold;
}

.count-wrap p span {
	color: red;
}
</style>
</head>
<body>
	<%@ include file="../../headerNavi.jsp"%>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>수강신청 관리</h1>
		</div>
		<div class="main-container">
			<div class="main-top">
				<div class="search-area">
					<div class="search-row">
						<c:if test="${not empty reservationDateTimeList}">
							<c:set var="startTime"
								value="${reservationDateTimeList[0].start_time}" />
							<c:set var="endTime"
								value="${reservationDateTimeList[0].end_time}" />
						</c:if>
						<label for="enroll-date-start">수강신청 시작일</label> <input
							type="datetime-local" name="enroll-date-start" disabled
							value="<fmt:formatDate value='${startTime}' pattern='yyyy-MM-dd\'T\'HH:mm'/>" />
					</div>
					<div class="search-row">
						<label for="enroll-date-end">수강신청 종료일</label> <input
							type="datetime-local" name="enroll-date-end" disabled
							value="<fmt:formatDate value='${endTime}' pattern='yyyy-MM-dd\'T\'HH:mm'/>" />
					</div>
				</div>
				<div class="main-top-side">
					<div class="btn-group">
						<c:choose>
							<c:when test="${reservationEnded}">
								<!-- 수정/삭제 버튼 안보이게 -->
								<p style="color: red;">※ 예약 시간이 종료되어 수정 및 취소가 불가능합니다.</p>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn-red"
									onclick="go_reservation_cancel()">예약 취소</button>
								<button type="button" class="btn-blue"
									onclick="go_reservation_update()">수정</button>
							</c:otherwise>
						</c:choose>

					</div>
					<div class="count-wrap">
						<p>
							총 강의 : <span>${lectListCount}</span>
						</p>
						<p>
							선택된 강의 : <span>${lectListCheckCount}</span>
						</p>
					</div>

				</div>
			</div>
			<div>
				<c:choose>
					<c:when test="${empty lectList}">
						<p id="empt-list">해당 조건에 맞는 강의가 없습니다.</p>
					</c:when>
					<c:otherwise>
						<table>
							<thead>
								<tr>

									<th>No.</th>
									<th>강의코드</th>
									<th>강의명</th>
									<th>개설학과</th>
									<th>학점</th>
									<th>담당교수</th>
									<th>수강정원</th>
									<th>강의실</th>
									<th>강의요일</th>
									<th>강의시간</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="k" items="${lectList}" varStatus="v">
									<tr>
										<td>${v.index + 1}</td>
										<td>${k.lect_id}</td>
										<td>${k.lect_name}</td>
										<td>${k.dept_name}</td>
										<td>${k.lect_credit}</td>
										<td>${k.name}</td>
										<td>${k.lect_max}</td>
										<td>${k.class_building}${k.class_room}</td>
										<td>${k.lect_day}</td>
										<td>${k.lect_start_time}-${k.lect_end_time}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</div>

		</div>
	</div>


	<script type="text/javascript">
		// 예약 취소 데이터 날리기
		function go_reservation_cancel() {
			location.href = "/enroll-apply-reservation-cancel?enroll_apply_idx="
					+ '${enroll_apply_idx}';

		}

		// 수정하기 페이지로 
		function go_reservation_update() {
			location.href = "/enroll-apply-reservation-update";
		}
	</script>
</body>


</html>