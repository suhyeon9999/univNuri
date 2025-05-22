<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/student/sEnrollDetail.css">
<meta charset="UTF-8">
<title>수강 관리</title>
</head>
<body>
<%@include file="../../headerNavi.jsp"%>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>${lectName}</h1>
		</div>
		<div class="nav-container">
			<div class="nav-left">
				<div class="list">
					<a href="/sEnrollDetail?lect_idx=${sessionScope.lect_idx }">과제</a>
				</div>
				<div class="attend">
					<a href="/sAttendance?lect_idx=${sessionScope.lect_idx }">출결</a>
				</div>
			</div>
			<div class="nav-right">
				<div class="detail">
					<a href="/LectureInfo?lect_idx=${sessionScope.lect_idx }">강의 정보</a>
				</div>
			</div>
		</div>
	<div class="main-container">
        <p class="count" style=" width: 80px; margin-bottom: 10px;">전체 : <span style="color: orange;">${fn:length(taskList)}</span></p>
        <div class="title-credit-wrapper">
	<form action="/sEnrollDetail" method="post">
	 <input type="hidden" name="lect_idx" value="${sessionScope.lect_idx}" />
		<ul class="selector">
			<li>
			<label for="submitPeriod">제출 기간</label>
<select id="submitPeriod" name="submitPeriod">
    <option value="" ${param.submitPeriod == null || param.submitPeriod == '' ? 'selected' : ''}>전체</option>
    <option value="가능" ${param.submitPeriod == '가능' ? 'selected' : ''}>제출 가능</option>
    <option value="불가능" ${param.submitPeriod == '불가능' ? 'selected' : ''}>제출 불가능</option>
</select>
			</li>
			<li>
			<label for="submitStatus">제출 여부</label>
<select id="submitStatus" name="submitStatus">
    <option value="" ${param.submitStatus == null || param.submitStatus == '' ? 'selected' : ''}>전체</option>
    <option value="미제출" ${param.submitStatus == '미제출' ? 'selected' : ''}>미제출</option>
    <option value="제출" ${param.submitStatus == '제출' ? 'selected' : ''}>제출</option>
</select>
			</li>
			<li>
				<button type="submit" style="color: white;" class="btn-orange">검색</button>
			</li>
		</ul>
	</form>
	</div>
        <div class="assign-list-wrapper">
            <c:choose>
                <c:when test="${empty taskList}">
                    <p>과제가 없습니다.</p>
                </c:when>
                <c:otherwise>
                    <c:forEach var="k" items="${taskList}" varStatus="v">
                        <div class="assign-list">
                            <h2>
								<c:choose>
								    <c:when test="${k.submit_status eq '제출완료'}">
								      <a class="link-black" 
								         href="/sEnrollDetailSubjectDetail?lect_idx=${lect_idx}&assign_idx=${k.assign_idx}" 
								         data-title="${k.assign_title}">
								         ${v.index + 1}. ${k.assign_title}
								      </a>
								    </c:when>
								    <c:otherwise>
								      <a class="link-black" 
								         href="/sEnrollDetailSubjectSubmit?lect_idx=${lect_idx}&assign_idx=${k.assign_idx}" 
								         data-title="${k.assign_title}">
								         ${v.index + 1}. ${k.assign_title}
								      </a>
								    </c:otherwise>
								  </c:choose>
                            </h2>
                            <div class="time">
                                <p class="top ">제출 시작</p>
                                <p class="bottom submit-start">${fn:substring(k.submit_start, 0, 10)}</p>
                            </div>
                            <div class="time">
                                <p class="top">제출 마감</p>
                                <p class="bottom due-date">${fn:substring(k.submit_end, 0, 10)}</p>
                            </div>
                            <div class="count">
                                <p class="top">제출 상태</p>
							     <c:choose>
							        <c:when test="${k.submit_status eq '제출완료'}">
							            <p class="submit">${k.submit_status}</p>
							        </c:when>
							        <c:otherwise>
							            <p class="not-submit">${k.submit_status}</p>
							        </c:otherwise>
							    </c:choose>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<script>
window.addEventListener('DOMContentLoaded', () => {
    const now = new Date();

    document.querySelectorAll('.assign-list').forEach(assignBlock => {
        const dueText = assignBlock.querySelector('.bottom.due-date')?.textContent.trim();

        if (dueText) {
            const parsedDue = new Date(dueText.replace(/\./g, '-'));

            if (now > parsedDue) {
                // 제출 상태는 제외하고 .top과 .bottom만 회색 처리
                assignBlock.querySelectorAll('.time .top, .time .bottom').forEach(el => {
                    el.classList.add('gray');
                });
            }
        }
    });
});
</script>
<script>
document.addEventListener("DOMContentLoaded", function () {
    const assignList = document.querySelectorAll('.assign-list-wrapper .assign-list');

    assignList.forEach(function (el, idx) {
        const aTag = el.querySelector('h2 a');
        if (aTag) {
            const title = aTag.getAttribute('data-title');
            if (title) {
                aTag.innerHTML = (idx + 1) + ". " + title;
            }
        }
    });
});
</script>
</body>
</html>