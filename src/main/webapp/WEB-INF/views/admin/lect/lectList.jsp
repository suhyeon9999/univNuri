<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
			<h1>강의 관리</h1>
		</div>

		<!-- 탭 버튼 -->
		<div class="nav-container">
			<div class="lect active" data-tab="lect">
				<a href="javascript:void(0)">강의개설</a>
			</div>
			<div class="subjectSet" data-tab="subjectSet">
				<a href="javascript:void(0)">과목군 관리</a>
			</div>
			<div class="subject" data-tab="subject">
				<a href="javascript:void(0)">과목 관리</a>
			</div>
		</div>

		<!-- 탭별 콘텐츠 -->
		<div class="tab-content-container">
			<!-- 강의개설 (기본 활성화) -->
			<div class="tab-content active" id="lect">
				<div class="main-container">
					<div class="btn-group">
						<button class="btn-green" id="list-btn"
							onclick="location.href='${pageContext.request.contextPath}/lectList'">전체
							강의 보기</button>
						<button class="btn-green" id="long-btn"
							onclick="location.href='${pageContext.request.contextPath}/searchNullLect'">입력
							미완료 강의 보기</button>
							<c:if test="${sessionScope.aInfo.a_grade <= 1 || sessionScope.access.acc_lect_insert == 1}">
						<button class="btn-blue"
							onclick="location.href='${pageContext.request.contextPath}/moveInsertLect'">등록</button>
							</c:if>
					</div>
					<p class="count">
						개설된 강의(개강 전) : <span>${lectList.size()}</span> <span>*표시는
							정보 입력 미완료된 강의에 대한 표시입니다.</span>
					</p>
					<!-- 검색 폼 -->
					<div class="main-top">
						<form action="/searchLect" method="get" class="form-top" 	id="lecture-search-form">
							<div class="search-area">
								<div class="search-row">
									<label>강의 코드</label> <input type="text" name="lect_id" value="">
									<label>강의명</label> <input type="text" name="lect_name">
								</div>
								<div class="search-row" id="search-day">
									<label>개설학과</label> <input type="text" name="dept_name">
									<label>강의 요일</label> <select name="lect_day">
										<option value="" disabled selected></option>
										<option value="2">월</option>
										<option value="3">화</option>
										<option value="4">수</option>
										<option value="5">목</option>
										<option value="6">금</option>
									</select> <label>담당교수</label><input type="text" name="name">
									<button type="submit" class="btn-orange">
										<i class="fa-solid fa-magnifying-glass"></i>
									</button>
								</div>
							</div>
						</form>
					</div>
					<!-- 강의 리스트 -->
					<div class="search-list-wrapper" id="lectList">
						<c:choose>
							<c:when test="${empty lectList}">
								<p>현재 표시할 강의가 없습니다. 검색 조건을 입력하세요.</p>
							</c:when>
							<c:otherwise>
								<c:forEach var="k" items="${lectList}" varStatus="v">
									<div class="search-list">
										<c:if
											test="${empty k.t_idx or empty k.lect_day or empty k.dept_name or empty k.lect_start_time or empty k.lect_end_time}">
											<span>*</span>
										</c:if>
										<h2>
											<a href="/detailLect?lect_idx=${k.lect_idx}">${v.count}.${k.lect_name}</a>
										</h2>
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
											<p>${k.lect_max}</p>
										</div>
									</div>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>

			<!-- 과목군 관리 -->
			<div class="tab-content" id="subjectSet">
				<div class="main-container">
					<div class="btn-group">
						<button class="btn-green" id="list-btn"
							onclick="location.href='${pageContext.request.contextPath}/subSetList'">전체	과목군 보기</button>
					<c:if test="${sessionScope.aInfo.a_grade <= 1}">
						<button class="btn-blue" onclick="location.href='${pageContext.request.contextPath}/moveInsertSubjectSet'">등록</button>
					</c:if>
					</div>
					<p class="count">
						전체 과목군 : <span>${fn:length(subjectSetList)}</span>
					</p>
					<!-- 검색 폼 -->
					<div class="main-top">
						<form action="/searchSubjectSet" method="get" class="form-top" id="subject-group-search-form">
							<div class="search-area">
								<div class="search-sub">
									<select id="searchType" name="searchType" required>
										<option value="sub_set_name">과목군명</option>
										<option value="dept_name" selected>학과명</option>
									</select> 
									<input type="text" name="keyword" required>
									<button type="submit" class="btn-orange">
										<i class="fa-solid fa-magnifying-glass"></i>
									</button>
								</div>
							</div>
						</form>
					</div>
					<!-- 과목군 리스트 -->
					<div class="search-list-wrapper" id="subjectSetList">
						<c:choose>
							<c:when test="${empty subjectSetList}">
								<p>해당 조건에 맞는 과목군이 없습니다.</p>
							</c:when>
							<c:otherwise>
								<table>
									<thead>
										<tr>
											<th>No.</th>
											<th>과목군명</th>
											<th>학과</th>
											<th>과목</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="k" items="${subjectSetList}" varStatus="v">
											<tr>
												<td>${v.index + 1}</td>
												<td><a href="/detailSubjectSet?sub_set_idx=${k.sub_set_idx}">${k.sub_set_name}</a></td>
												<td>${k.dept_name}</td>
												<td>
													<c:choose>
                                                        <c:when test="${empty k.subjects}">
                                                            과목 선택 전
                                                        </c:when>
                                                        <c:otherwise>                                                            
                                                            ${k.subjects}                                                            
                                                        </c:otherwise>
                                                    </c:choose>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>

			<!-- 과목 관리 -->
			<div class="tab-content" id="subject">
				<div class="main-container">
					<div class="btn-group">
						<button class="btn-green" id="list-btn"
							onclick="location.href='${pageContext.request.contextPath}/subjectList'">전체 과목 보기</button>
							<c:if test="${sessionScope.aInfo.a_grade <= 1 || sessionScope.access.acc_lect_insert == 1}">
						<button class="btn-blue" onclick="location.href='${pageContext.request.contextPath}/moveInsertSubject'">등록</button>
							</c:if>
					</div>
					<p class="count">
						전체 : <span>${subjectList != null ? subjectList.size() : 0}</span>
					</p>
					<!-- 검색 폼 -->
					<div class="main-top">
						<form action="/searchSubject" method="get" class="form-top"
							id="subject-search-form">
							<div class="search-area">
								<div class="search-sub">
									<select id="searchType" name="searchType" required>
										<option value="subject_name" selected>과목명</option>
										<option value="dept_name">학과명</option>
									</select> <input type="text" name="keyword" required>
									<button type="submit" class="btn-orange">
										<i class="fa-solid fa-magnifying-glass"></i>
									</button>
								</div>
							</div>
						</form>
					</div>
					<!-- 과목 리스트 -->
					<div class="search-list-wrapper" id="subjectList">
						<c:choose>
							<c:when test="${empty subjectList}">
								<p>해당 조건에 맞는 과목이 없습니다.</p>
							</c:when>
							<c:otherwise>
								<c:forEach var="k" items="${subjectList}" varStatus="v">
									<div class="search-list">
										<h2>
											<a href="/detailSubject?subject_idx=${k.subject_idx}">${v.index + 1}.${k.subject_name}</a>
										</h2>
										<div>
											<p>개설학과</p>
											<p>${k.dept_name}</p>
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
	$(document).ready(function() {
	    // 초기 탭 설정
	    var currentUrl = window.location.pathname;
	    var activeTab = 'lect';
	    if (currentUrl.includes('subject') || currentUrl.includes('searchSubject')) {
	        activeTab = 'subject';
	    } else if (currentUrl.includes('subjectSet') || currentUrl.includes('subjectSetList')) {
	        activeTab = 'subjectSet';
	    }

	    console.log('Initial activeTab:', activeTab);
	    $('.tab-content').removeClass('active').hide();
	    $('#' + activeTab).addClass('active').show();
	    $('.nav-container > div').removeClass('active');
	    $('.nav-container > div[data-tab="' + activeTab + '"]').addClass('active');

	    // 탭 클릭 이벤트
	    $('.nav-container > div').click(function() {
	        const tabId = $(this).data('tab');
	        console.log('Tab clicked, tabId:', tabId);
	        $('.nav-container > div').removeClass('active');
	        $(this).addClass('active');
	        $('.tab-content').removeClass('active').hide();
	        $('#' + tabId).addClass('active').show();
	        loadTabContent(tabId);
	    });

	    // 과목군 검색 폼 AJAX 처리
	    $('#subject-group-search-form').on('submit', function(e) {
	    	e.preventDefault();
	        var searchType = $('#searchType').val();
	        var keyword = $('input[name="keyword"]').val().trim();

	        console.log('Search Request - searchType:', searchType, 'keyword:', keyword);
	        if (!searchType) {
	            console.error('searchType is null or undefined');
	            alert('검색 유형을 선택하세요.');
	            return;
	        }
	        if (!keyword) {
	            alert('검색어를 입력하세요.');
	            return;
	        }

	        $.ajax({
	            url: '/searchSubjectSet',
	            type: 'GET',
	            data: { searchType: searchType, keyword: keyword },
	            headers: { 'X-Requested-With': 'XMLHttpRequest' },
	            cache: false,
	            success: function(response) {
	                console.log('Response Type:', typeof response);
	                var $response = typeof response === 'string' ? $(response) : response;
	                var content = $response.find('#subjectSetList').html();
	                if (!content) {
	                    content = '<p>검색 결과가 없습니다.</p>';
	                }
	                console.log('Extracted Content:', content);
	                $('#subjectSetList').html(content);

	                $('.tab-content').removeClass('active').hide();
	                $('#subjectSet').addClass('active').show();
	                $('.nav-container > div').removeClass('active');
	                $('.nav-container > div[data-tab="subjectSet"]').addClass('active');
	            },
	            error: function(xhr, status, error) {
	                console.error('AJAX Error:', status, error, xhr.responseText);
	                $('#subjectSetList').html('<p>검색 중 오류가 발생했습니다: ' + error + '</p>');
	            }
	        });
	    });

	    // 과목 검색 폼 AJAX 처리
	    $('#subject-search-form').on('submit', function(e) {
	        e.preventDefault();
	        var searchType = $('#searchType').val();
	        var keyword = $('input[name="keyword"]').val().trim();

	        console.log('Search Request - searchType:', searchType, 'keyword:', keyword);
	        if (!searchType) {
	            console.error('searchType is null or undefined');
	            alert('검색 유형을 선택하세요.');
	            return;
	        }
	        if (!keyword) {
	            alert('검색어를 입력하세요.');
	            return;
	        }

	        $.ajax({
	            url: '/searchSubject',
	            type: 'GET',
	            data: { searchType: searchType, keyword: keyword },
	            headers: { 'X-Requested-With': 'XMLHttpRequest' },
	            cache: false,
	            success: function(response) {
	                console.log('Response Type:', typeof response);
	                var $response = typeof response === 'string' ? $(response) : response;
	                var content = $response.find('#subjectList').html();
	                if (!content) {
	                    content = '<p>검색 결과가 없습니다.</p>';
	                }
	                console.log('Extracted Content:', content);
	                $('#subjectList').html(content);

	                $('.tab-content').removeClass('active').hide();
	                $('#subject').addClass('active').show();
	                $('.nav-container > div').removeClass('active');
	                $('.nav-container > div[data-tab="subject"]').addClass('active');
	            },
	            error: function(xhr, status, error) {
	                console.error('AJAX Error:', status, error, xhr.responseText);
	                $('#subjectList').html('<p>검색 중 오류가 발생했습니다: ' + error + '</p>');
	            }
	        });
	    });

	    function loadTabContent(tabId) {
	        let url, targetId;
	        if (tabId === 'lect') {
	            url = '${pageContext.request.contextPath}/lectList';
	            targetId = '#lectList';
	        } else if (tabId === 'subjectSet') {
	            url = '${pageContext.request.contextPath}/subjectSetList';
	            targetId = '#subjectSetList';
	        } else if (tabId === 'subject') {
	            url = '${pageContext.request.contextPath}/subjectList';
	            targetId = '#subjectList';
	        }

	        console.log('Request URL:', url, 'targetId:', targetId);
	        $(targetId).html('<p>로딩 중...</p>');

	        $.ajax({
	            url: url,
	            type: 'GET',
	            headers: { 'X-Requested-With': 'XMLHttpRequest' },
	            cache: false,
	            success: function(response) {
	                console.log('Response Type:', typeof response);
	                var $response = typeof response === 'string' ? $(response) : response;
	                var content = $response.find(targetId).html() || '<p>데이터가 없습니다.</p>';
	                console.log('Extracted Content:', content);
	                $(targetId).html(content);
	            },
	            error: function(xhr, status, error) {
	                console.error('AJAX Error:', status, error, xhr.responseText);
	                $(targetId).html('<p>데이터를 불러오지 못했습니다. 오류: ' + error + '</p>');
	            }
	        });
	    }

	    window.loadAllSubjectSets = function() {
	        console.log('loadAllSubjectSets called');
	        $.ajax({
	            url: '${pageContext.request.contextPath}/subjectSetList',
	            type: 'GET',
	            headers: { 'X-Requested-With': 'XMLHttpRequest' },
	            cache: false,
	            success: function(response) {
	                console.log('Response Type:', typeof response);
	                var $response = typeof response === 'string' ? $(response) : response;
	                var content = $response.find('#subjectSetList').html();
	                if (!content) {
	                    content = '<p>과목군이 없습니다.</p>';
	                }
	                console.log('Extracted Content:', content);
	                $('#subjectSetList').html(content);

	                $('.tab-content').removeClass('active').hide();
	                $('#subjectSet').addClass('active').show();
	                $('.nav-container > div').removeClass('active');
	                $('.nav-container > div[data-tab="subjectSet"]').addClass('active');
	            },
	            error: function(xhr, status, error) {
	                console.error('AJAX Error:', status, error, xhr.responseText);
	                $('#subjectSetList').html('<p>과목군 조회 중 오류가 발생했습니다: ' + error + '</p>');
	            }
	        });
	    };
	});
	</script>
</body>
</html>