   <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%--
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  

 
 

 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <p class="count">
  개강된 강의 : <span id="lecture-count">${lectListNum}</span>
</p>
<div class="search-list-wrapper" id="lecture-list">
  <c:choose>
    <c:when test="${empty lectList}">
      <p>현재 표시할 강의가 없습니다. 검색 조건을 입력하세요.</p>
    </c:when>
    <c:otherwise>
      <c:forEach var="k" items="${lectList}" varStatus="v">
        <div class="search-list">
          <h2>${v.index + 1}. ${k.lect_name}</h2>
          <div><p>강의코드</p><p>${k.lect_id}</p></div>
          <div><p>개설학과</p><p>${k.dept_name}</p></div>
          <div><p>학점</p><p>${k.lect_credit}</p></div>
          <div><p>교수</p><p>${k.name}</p></div>
          <div><p>강의정원</p><p>${k.class_max}</p></div>
        </div>
      </c:forEach>
    </c:otherwise>
  </c:choose>
</div>
 --%>
 
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<p class="count">
  개강된 강의 : <span id="ajax-lecture-count">${lectList.size()}</span>
</p>
<div class="search-list-wrapper" id="ajax-lecture-list">
  <c:choose>
    <c:when test="${empty lectList}">
      <p>현재 표시할 강의가 없습니다. 검색 조건을 입력하세요.</p>
    </c:when>
    <c:otherwise>
      <c:forEach var="k" items="${lectList}" varStatus="v">
        <div class="search-list">
          <h2><a href="/detailLect?lect_idx=${k.lect_idx}">${v.index + 1}.${k.lect_name}</a></h2>
          <div><p>강의코드</p><p>${k.lect_id}</p></div>
          <div><p>개설학과</p><p>${k.dept_name}</p></div>
          <div><p>학점</p><p>${k.lect_credit}</p></div>
          <div><p>교수</p><p>${k.name}</p></div>
          <div><p>강의정원</p><p>${k.class_max}</p></div>
        </div>
      </c:forEach>
    </c:otherwise>
  </c:choose>
</div>
