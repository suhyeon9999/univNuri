<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 목록 보기</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/commonNoticeList.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/index.css">

</head>
<body>
	<div class="wrap">
		<!-- 헤더 영역 -->
		<header>
			<div class="header-inner">
				<h1>
					<a href="/index">NURI UNIVERSITY</a>
				</h1>
				<nav id="gnav">
					<ul class="gnav-list">
						<li><a href="/noticeList">NOTICE</a></li>
						<li><a href="/faqList">FAQ</a></li>
						<li><a href="https://www.kmooc.kr/" target="_blank">K-MOOC</a></li>
					</ul>
				</nav>
				<div id="logInOut">
					<ul class="logInOut-list">
					<c:choose>
						<c:when test="${empty sessionScope.loginchk}">
					<li style="align-content: center;"><a href="/login">LOGIN</a></li> 
					</c:when>
					<c:otherwise>
					<li style="font-size: 27.5px; margin-top: 2.1px;"><a href="/dashboard"><i class="fa-solid fa-school"></i></a></li>
					<li style="align-content: center;"><a href="/logout">LOGOUT</a></li>
					</c:otherwise>
					</c:choose>
					<!-- 아래가 테스트용!!!! -->
<!-- 					<li><a href="/loginTest">LOGIN_TEST</a></li> -->
				</ul>
				</div>
			</div>
		</header>
		<div class="container">
			<h2>공지사항</h2>
			<div class="top">
				<p>게시글 : <span>${paging.totalRecord }</span>건</p>
				<form action="/searchNotice" method="get" class="search-box">
					<input type="text" name="search" class="search-txt" placeholder="검색할 제목이나 내용을 입력하세요" />
					<button type="submit" class="search-btn">
						<i class="fa-solid fa-magnifying-glass"></i>
					</button>
				</form>
			</div>
			<hr />
			<table>
				<colgroup>
					<col class="col-no" />
					<col class="col-necessary" />
					<col class="col-title" />
					<col class="col-writer" />
					<col class="col-date" />
				</colgroup>
				<thead>
					<tr>
						<th>No</th>
						<th>중요도</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>등록일</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty noticeList }">
							<tr>
	                        	<td colspan="4">
		                        	<h2>
		                            	<c:choose>
		                                	<c:when test="${not empty search}">검색 결과가 없습니다.</c:when>
		                                    	<c:otherwise>게시물이 존재하지 않습니다.</c:otherwise>
		                               </c:choose>
	                             	</h2>
	                             </td>
	                    	</tr>
						</c:when>
					<c:otherwise>
						<!-- 필수 공지사항 최신5개 -->                            
							<c:forEach var="k" items="${necessaryNoticeList }" varStatus="v">								
								<tr class="necessary">										
									<td>${v.count}</td>
									<td>
									<c:choose>
												<c:when test="${k.necessary == '0' }">일반공지</c:when>
												<c:when test="${k.necessary == '1' }">중요공지</c:when>
											</c:choose>
									</td>
									<td>
										<a href="/detailNotice?notice_idx=${k.notice_idx }&a_grade=${k.a_grade }&cPage=${paging.nowPage}">
											${k.title }</a>
									</td>
									<td>
										<c:choose>
											<c:when test="${k.a_grade == '0' }">최고관리자</c:when>
											<c:when test="${k.a_grade == '1' }">중간관리자</c:when>
											<c:when test="${k.a_grade == '2' }">일반관리자</c:when>
										</c:choose>
									</td>
									<td>${k.write_date.toString().substring(0, 10)}</td>
								</tr>								
							</c:forEach>
							<!-- 일반 공지사항 카운터 초기화 -->
                            <c:set var="necessaryCount" value="0" />
							<c:forEach var="k" items="${noticeList }" varStatus="v" step="1">								
									<c:set var="number" value="${paging.totalRecord - ((paging.nowPage - 1) * paging.numPerPage) - (v.count - 1)}" />							
									<tr>
										<td>${number}</td>
										<td>
											<c:choose>
												<c:when test="${k.necessary == '0' }">일반공지</c:when>
												<c:when test="${k.necessary == '1' }">중요공지</c:when>
											</c:choose>
									</td>										
										<td>
											<a href="/detailNotice?notice_idx=${k.notice_idx }&a_grade=${k.a_grade }&cPage=${paging.nowPage}">
												${k.title }</a>
										</td>
										<td>
											<c:choose>
												<c:when test="${k.a_grade == '0' }">최고관리자</c:when>
												<c:when test="${k.a_grade == '1' }">중간관리자</c:when>
												<c:when test="${k.a_grade == '2' }">일반관리자</c:when>
											</c:choose>
										</td>
										<td>${k.write_date.toString().substring(0, 10)}</td>
									</tr>								
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
				<!-- 페이징기법 -->				
				<tfoot>
					<tr>
						<td colspan="4">
							<ol class="paging">
								<!-- 이전 블록 버튼 -->
				                <c:choose>
				                    <c:when test="${paging.hasPrevBlock}">
				                        <li><a href="/noticeList?cPage=${paging.beginBlock - paging.pagePerBlock}"><<</a></li>
				                    </c:when>
				                    <c:otherwise>
				                        <li class="disable"><<</li>
				                    </c:otherwise>
				                </c:choose>
								<!-- 이전 페이지 버튼 -->
								<c:choose>
									 <c:when test="${paging.nowPage > 1}">
								        <li><a href="/noticeList?cPage=${paging.nowPage - 1}"><</a></li>
								    </c:when>
								    <c:otherwise>
								        <li class="disable"><</li>
								    </c:otherwise>
								</c:choose>
								<!-- 블록 안에 들어가는 페이지 번호 -->
								<c:forEach begin="${paging.beginBlock }" end="${paging.endBlock }" step="1" var="k">
									<!-- 현재 블록과 나머지 블록으로 나눈다 -->
									<c:choose>
										<c:when test="${k == paging.nowPage }">
											<li class="now">${k }</li>
										</c:when>
										<c:otherwise>
											<li><a href="/noticeList?cPage=${k }">${k }</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<!-- 다음 페이지 버튼 -->
								<c:choose>
									<c:when test="${paging.nowPage < paging.totalPage}">
								        <li><a href="/noticeList?cPage=${paging.nowPage + 1}">></a></li>
								    </c:when>
								    <c:otherwise>
								        <li class="disable">></li>
								    </c:otherwise>
								</c:choose>
								<!-- 다음 블록 버튼 -->
				                <c:choose>
				                    <c:when test="${paging.hasNextBlock}">
				                        <li><a href="/noticeList?cPage=${paging.endBlock + 1}">></a></li>
				                    </c:when>
				                    <c:otherwise>
				                        <li class="disable">>></li>
				                    </c:otherwise>
				                </c:choose>
							</ol>
						</td>
					</tr>
				</tfoot>
			</table>
				<div class="buttons">
					<button class="btn-gray" id="long-btn"
						onclick="location.href='${pageContext.request.contextPath}/noticeList'">전체글 보기</button>
					<c:if test="${sessionScope.aInfo.a_grade <= 1 || sessionScope.access.acc_user_insert == 1}">
					<button class="btn-gray" onclick="location.href='${pageContext.request.contextPath}/moveInsertNotice'">글쓰기</button>
					</c:if>
				</div>
		</div>
	</div>

	<footer>
		<div class="footer-inner">
			<p>© 2025 NURI UNIVERSITY. All rights reserved.</p>
			<p>문의: 02-1234-5678 | 주소: 서울시 누리구 누리로 123</p>
		</div>
	</footer>
</body>
</html>