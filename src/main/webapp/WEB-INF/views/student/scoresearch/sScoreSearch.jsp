<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/student/sScoreSearch.css">
 <meta charset="UTF-8">
 <title>성적관리</title>
</head>
<body>
<%@include file="../../headerNavi.jsp"%>
<div id="wrap">
  <div class="title-container">
    <div class="highlight"></div>
    <h1>성적조회</h1>
  </div>

  <form action="/sScoreSearch" method="get">
    <ul class="selector">
      <li>
        <label for="year">연도</label>
			<select id="year" name="year">
			  <option value="">전체</option>
			  <c:forEach var="y" items="${yearList}">
			    <option value="${y}" ${search.year == y ? 'selected' : ''}>${y}학년도</option>
			  </c:forEach>
			</select>
      </li>
      <li>
        <label for="semester">학기</label>
			<select id="semester" name="semester">
			  <option value="">전체</option>
			  <c:forEach var="s" items="${semesterList}">
			    <option value="${s}" ${search.semester == s ? 'selected' : ''}>${s}학기</option>
			  </c:forEach>
			</select>
      </li>
      <li>
        <label for="lect_name">과목명</label>
        <input type="text" name="lect_name" id="lect_name" value="${search.lect_name}">
      </li>
      <li>
        <button type="submit" class="btn-orange" style="color: white;">검색</button>
      </li>
      <li>
        <button type="button" onclick="location.href='/sScoreSearchObjectionManagement'" class="btn-blue" style="color: white; width: 125px; font-size: 16px;">이의제기 관리</button>
      </li>
    </ul>


  <div class="main-container">
    <div class="description-text">
      <p class="count left-text">학점 산정 기준 : 중간<span> 35점</span>, 기말<span> 35점</span>, 과제<span> 20점</span>, 출석<span> 10점</span></p>
      <p class="right-text" style="color: orange;">*모든 성적과 최종 학점이 입력된 강의만 이의제기 가능</p>
    </div>

    <div class="assign-list-wrapper">
      <c:choose>
        <c:when test="${empty scoreList}">
          <p>해당 조건에 맞는 과제가 없습니다.</p>
        </c:when>
        <c:otherwise>
          <c:forEach var="k" items="${scoreList}" varStatus="v">
            <div class="assign-list">
              <h2>${k.assign_title}</h2>
				<fmt:formatDate value="${k.lect_start_date}" pattern="MM" var="lectMonth" />
				
				<c:if test="${(lectMonth ge '03' and lectMonth le '06') or (lectMonth ge '09' and lectMonth le '12')}">
				  <c:if test="${not empty k.score_total}">
				    <button type="button" onclick="location.href='/sScoreSearchObjectionInsert?lect_idx=${k.lect_idx}'"
				            style="background-color: orange; color: white; font-size: 16px; width: 88px;">이의제기</button>
				  </c:if>
				</c:if>
              <input type="hidden" name="lect_idx" value="${k.lect_idx}" />
              <div class="time">
                <p>연도</p>
                <p><fmt:formatDate value="${k.lect_start_date}" pattern="yyyy" /></p>
              </div>

              <div class="time">
                <p>학기</p>
                <c:choose>
                  <c:when test="${fn:substring(k.lect_start_date, 5, 7) le '06'}">
                    <p>1학기</p>
                  </c:when>
                  <c:otherwise>
                    <p>2학기</p>
                  </c:otherwise>
                </c:choose>
              </div>

              <div class="count">
                <p>교수명</p>
                <p class="submit">${k.professor_name}</p>
              </div>

              <div class="count">
                <p>강의실</p>
                <p class="submit">
			 <c:choose>
				<c:when test="${k.class_building == 0}">미래관</c:when>
				<c:when test="${k.class_building == 1}">현재관</c:when>
				 <c:when test="${k.class_building == 2}">과거관</c:when>
				<c:otherwise>미지정</c:otherwise>
				</c:choose>
				 ${k.class_room}</p>
              </div>

              <div class="count">
                <p>중간</p>
                <p class="not-submit">${k.score_mid}</p>
              </div>
              <div class="count">
                <p>기말</p>
                <p class="not-submit">${k.score_final}</p>
              </div>
              <div class="count">
                <p>과제</p>
                <p class="not-submit">${k.score_assign}</p>
              </div>
              <div class="count">
                <p>출석</p>
                <p class="not-submit">${k.score_attend}</p>
              </div>
              <div class="count">
                <p>학점</p>
                <p class="not-submit">${k.score_total}</p>
              </div>
            </div>
          </c:forEach>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
   </form>
   <script type="text/javascript">
   const buildingMap = {
		    0: '미래관',
		    1: '현재관',
		    2: '과거관'
		  };
   function renderClassroom(building, room) {
	    return (buildingMap[building] || '미지정') + ' ' + room;
	  }
   </script>
</div>
</body>
</html>
