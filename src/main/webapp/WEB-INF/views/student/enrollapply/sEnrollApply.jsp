<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/student/sEnrollApply.css">
  <meta charset="UTF-8">
  <title>수강신청</title>
</head>
<body>
<%@include file="../../headerNavi.jsp"%>
<div id="wrap">
  <div class="title-container">
    <div class="highlight"></div>
    <h1>수강 신청</h1>
  </div>
  <!-- 🔍 검색 영역 -->
  <form method="get" action="/senrollapply">
    <ul class="selector">
      <li>
        <label for="dept_name">학과</label>
        <input type="text" id="dept_name" name="dept_name" value="${search.dept_name}" />
      </li>
      <li>
        <label for="lect_name">강의명</label>
        <input type="text" id="lect_name" name="lect_name" value="${search.lect_name}" />
      </li>
    </ul>
    <br>
    <ul class="selector">
      <li>
        <label for="professor_name">교수명</label>
        <input type="text" id="professor_name" name="professor_name" value="${search.professor_name}" />
      </li>
      <li>
        <label for="lect_day">강의요일</label>
        <select id="lect_day" name="lect_day">
          <option value="" ${search.lect_day == '전체' ? 'selected' : ''}>전체</option>
          <option value="1" ${search.lect_day == '1' ? 'selected' : ''}>월</option>
          <option value="2" ${search.lect_day == '2' ? 'selected' : ''}>화</option>
          <option value="3" ${search.lect_day == '3' ? 'selected' : ''}>수</option>
          <option value="4" ${search.lect_day == '4' ? 'selected' : ''}>목</option>
          <option value="5" ${search.lect_day == '5' ? 'selected' : ''}>금</option>
        </select>
      </li>
      <li>
        <label for="lect_id">과목코드</label>
        <input type="text" id="lect_id" name="lect_id" value="${search.lect_id}" />
      </li>
      <li>
        <button type="submit" class="btn-orange" style="color: white;">검색</button>
      </li>
    </ul>
  </form>

  <!-- 📋 수강신청 가능 강의 -->
  <table>
    <thead>
      <tr>
        <th>No.</th>
        <th>신청</th>
        <th>과목코드</th>
        <th>강의명</th>
        <th>교수명</th>
        <th>잔여인원</th>
        <th>정원</th>
        <th>강의요일</th>
        <th>강의시간</th>
        <th>학점</th>
        <th>강의실</th>
      </tr>
    </thead>
    <tbody>
      <c:choose>
        <c:when test="${empty lectureList}">
          <tr><td colspan="11" style="text-align: center;">수강 신청 가능한 강의가 없습니다.</td></tr>
        </c:when>
        <c:otherwise>
         <form method="post" action="/sEnrollApplyOK">
          <c:forEach var="lec" items="${lectureList}" varStatus="vs">
            <tr>
              <td>${vs.index + 1}</td>
              <td>
                <c:choose>
                  <c:when test="${lec.remain_count > 0}">
                      <input type="hidden" name="lect_idx" value="${lec.lect_idx}" />
                      <button type="submit" style="background-color: transparent; color: blue;">신청</button>
                  </c:when>
                  <c:otherwise>
                    <span style="color: red;">잔여인원 없음</span>
                  </c:otherwise>
                </c:choose>
              </td>
              <td>${lec.lect_id}</td>
              <td>${lec.lect_name}</td>
              <td>${lec.professor_name}</td>
              <td>${lec.remain_count}</td>
              <td>${lec.lect_max}</td>
              <td>
                <c:choose>
                  <c:when test="${lec.lect_day == 1}">월</c:when>
                  <c:when test="${lec.lect_day == 2}">화</c:when>
                  <c:when test="${lec.lect_day == 3}">수</c:when>
                  <c:when test="${lec.lect_day == 4}">목</c:when>
                  <c:when test="${lec.lect_day == 5}">금</c:when>
                </c:choose>
              </td>
              <td>${lec.lect_time}</td>
              <td>${lec.lect_credit}</td>
              <td>${lec.class_building}${lec.class_room}</td>
            </tr>
          </c:forEach>
                    </form>
        </c:otherwise>
      </c:choose>
    </tbody>
  </table>

  <!-- ✅ 수강 신청 내역 -->
  <div class="title-credit-wrapper" id="appliedSection">
    <div class="small-title-container">
      <div class="highlight" style="vertical-align: bottom;"></div>
      <h2 style="vertical-align: bottom;">수강 신청 내역</h2>
    </div>
    <div class="credit-summary-box">
      <p>수강신청 가능 학점: <span class="highlight-credit">${max_credit}</span></p>
      <p style="margin: 15px 0px 10px 0px;">신청한 과목 총 학점: <span class="highlight-credit">${current_credit}</span></p>
    </div>
  </div>

  <table>
    <thead>
      <tr>
        <th>No.</th>
        <th>신청</th>
        <th>과목코드</th>
        <th>강의명</th>
        <th>교수명</th>
        <th>잔여인원</th>
        <th>정원</th>
        <th>강의요일</th>
        <th>강의시간</th>
        <th>학점</th>
        <th>강의실</th>
      </tr>
    </thead>
<tbody>
  <c:choose>
    <c:when test="${empty enrolledLectureList}">
      <tr><td colspan="11" style="text-align: center;">수강 신청 내역이 없습니다.</td></tr>
    </c:when>
    <c:otherwise>
      <c:forEach var="enrolled" items="${enrolledLectureList}" varStatus="vs">
        <tr>
          <td>${vs.index + 1}</td>
          <td>
            <form method="post" action="/sEnrollApplyCancel">
              <input type="hidden" name="lect_idx" value="${enrolled.lect_idx}" />
              <button type="submit" style="background-color: transparent; color: red;">취소</button>
            </form>
          </td>
          <td>${enrolled.lect_id}</td>
          <td>${enrolled.lect_name}</td>
          <td>${enrolled.professor_name}</td>
          <td>${enrolled.remain_count}</td>
          <td>${enrolled.lect_max}</td>
          <td>
            <c:choose>
              <c:when test="${enrolled.lect_day == 1}">월</c:when>
              <c:when test="${enrolled.lect_day == 2}">화</c:when>
              <c:when test="${enrolled.lect_day == 3}">수</c:when>
              <c:when test="${enrolled.lect_day == 4}">목</c:when>
              <c:when test="${enrolled.lect_day == 5}">금</c:when>
            </c:choose>
          </td>
          <td>${enrolled.lect_time}</td>
          <td>${enrolled.lect_credit}</td>
          <td>${enrolled.class_building}${enrolled.class_room}</td>
        </tr>
      </c:forEach>
    </c:otherwise>
  </c:choose>
</tbody>

  </table>
</div>

<!-- ✅ 신청/취소 시 alert + 스크롤 -->
<c:if test="${applySuccess}">
<script>
  alert("수강 신청이 완료되었습니다.");
  window.scrollTo({ top: document.querySelector("#appliedSection").offsetTop, behavior: 'smooth' });
</script>
</c:if>
<c:if test="${cancelSuccess}">
<script>
  alert("수강 신청이 취소되었습니다.");
  window.scrollTo({ top: document.querySelector("#appliedSection").offsetTop, behavior: 'smooth' });
</script>
</c:if>

</body>
</html>
