<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>과목군 상세보기</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/commonUser.css">
</head>
<body>
<%@ include file="../../headerNavi.jsp" %>
    <div id="wrap">
        <c:if test="${not empty detailSubjectSet}">
                <div class="total-container">
                    <div class="title-container">
                        <div class="highlight"></div>
                        <h1>과목군 상세보기</h1>
                    </div>
                    <div class="btn-group">
                        <button class="btn-blue"
                        	onclick="location.href='${pageContext.request.contextPath}/moveUpdateSubjectSet?sub_set_num=${detailSubjectSet[0].sub_set_num}'">수정</button>
                        <button class="btn-red"
                            onclick="if(confirm('정말 삭제하시겠습니까?')) location.href='${pageContext.request.contextPath}/deleteSubjectSet?sub_set_num=${detailSubjectSet[0].sub_set_num}'">삭제</button>
                    </div>
                    <table>
                        <c:forEach var="subjectSet" items="${detailSubjectSet}">
                            <tr>
                                <th>과목군명</th>
                                <td>${subjectSet.sub_set_name}</td>
                            </tr>
                            <tr>
                                <th>학과</th>
                                <td>${subjectSet.dept_name}</td>
                            </tr>
                            <tr>
                                <th>과목 구성</th>
                                <td>${subjectSet.subjects}</td>
                            </tr>
                        </c:forEach>
                    </table>
                    <div class="btn-group">
                        <button type="button" class="btn-gray" onclick="location.href='${pageContext.request.contextPath}/subjectSetList'">목록</button>
                    </div>
                </div>            
        </c:if>
        <c:if test="${empty detailSubjectSet}">
            <p>데이터가 없습니다.</p>
            <button type="button" class="btn-gray" onclick="location.href='${pageContext.request.contextPath}/subjectSetList'">목록으로 돌아가기</button>
        </c:if>
    </div>
 
</body>
</html>