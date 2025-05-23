
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강의관리 상세보기</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/commonUser.css">
</head>
<body>
<%@ include file="../../headerNavi.jsp"%>
	<div id="wrap">
		<div class="total-container">
			<div class="title-container">
				<div class="highlight"></div>
				<h1>강의관리 상세보기</h1>
			</div>
			<div class="btn-group">
			<c:if test="${sessionScope.aInfo.a_grade <= 1 || sessionScope.access.acc_lect_delete == 1}">
				<button class="btn-red" onclick="showPwModal()">삭제</button>
			</c:if>				
			<c:if test="${sessionScope.aInfo.a_grade <= 1 || sessionScope.access.acc_lect_update == 1}">
				<button class="btn-blue" onclick="location.href='/updateLect?lect_idx=${detailLect.lect_idx}'">수정</button>
			</c:if>
			</div>
			<table>
				 <tr>
		                <th>강의명</th>
		                <td>${detailLect.lect_name }</td>
		            </tr>
		            <tr>
		                <th>강의코드</th>
		                <td>${detailLect.lect_id }</td>
		            </tr>
		            <tr>
		                <th>개설학과</th>
		                <td>${detailLect.dept_name }</td>
		            </tr>
							<tr>
							  <th>과목구성</th>
							  <td>
							    <c:choose>
							      <c:when test="${empty lectSubSet}">
							        과목군 미배정
							      </c:when>
							      <c:otherwise>
							        <c:forEach var="group" items="${lectSubSet}">
							          <strong>[${group.sub_set_name}]</strong>
							          ${group.subject_name}<br/>
							        </c:forEach>
							      </c:otherwise>
							    </c:choose>
							  </td>
							</tr>


		            <tr>
		                <th>이수학점</th>
		                <td>${detailLect.lect_credit }</td>
		            </tr>
		            <tr>
		                <th>담당교수</th>
		                <td>
		                	<c:choose>
			                	<c:when test="${empty detailLect.name} ">담당교수 미배정</c:when>
			                	<c:otherwise>
				                	${detailLect.name }
			                	</c:otherwise>
		                	</c:choose>
		                </td>
		            </tr>
		            <tr>
		                <th>수강정원</th>
		                <td>${detailLect.lect_max }</td>
		            </tr>
		            <tr>
		                <th>강의실</th>
		                <td>
		                	<c:choose>
			                	<c:when test="${empty detailLect.class_building or empty detailLect.class_room} ">강의실 미정</c:when>
			                	<c:otherwise>
				                	${detailLect.class_building} ,${detailLect.class_room }호
			                	</c:otherwise>
		                	</c:choose>
		                </td>
		            </tr>
		            <tr>
		                <th>강의요일</th>
		                <td>
		                	<c:choose>
			                	<c:when test="${empty detailLect.lect_day} ">강의요일 미정</c:when>
			                	<c:otherwise>
				                	${detailLect.lect_day } 
			                	</c:otherwise>
		                	</c:choose>
		                </td>
		            </tr>
		            <tr>
		                <th>강의시간</th>
		                <td>
							<c:choose>
						    <c:when test="${empty detailLect.lect_start_time or empty detailLect.lect_end_time}">
						        강의시간 미정
						    </c:when>
						    <c:otherwise>
						        <fmt:parseDate var="startTime" value="${detailLect.lect_start_time}" pattern="HH:mm:ss"/>
						        <fmt:parseDate var="endTime" value="${detailLect.lect_end_time}" pattern="HH:mm:ss"/>
						        <fmt:formatDate value="${startTime}" pattern="HH:mm"/> - <fmt:formatDate value="${endTime}" pattern="HH:mm"/>
						    </c:otherwise>
						</c:choose>
		                </td>
		            </tr>		           		            
			</table>
			<div class="btn-group">
				<button class="btn-gray" onclick="location.href='${pageContext.request.contextPath}/lectList'">목록</button>

			</div>
		</div>
		<!-- 비밀번호 입력 모달 -->
								<!-- 비밀번호 입력 모달 -->
			<div id="pwModal" class="modal" style="display: none;">
				<div class="modal-content">
					<h3>비밀번호 확인</h3>
					<input type="password" id="pwInput" placeholder="비밀번호 입력" /> <br>
					<button id="pwCheckBtn" class="btn-blue">확인</button>
					<input type="hidden" id="lect_idx" value="${detailLect.lect_idx}" />
					<button id="pwCancelBtn" class="btn-red">취소</button>
				</div>
			</div>
	</div>
	<script type="text/javascript">
	let lect_idx = $("#lect_idx").val();
		// 비밀번호 체크 모달창
		function showPwModal() {
			$('#pwModal').show();
		}

		// 모달창 닫기
		$('#pwCancelBtn').click(function() {
			$('#pwModal').hide();
			$('#pwInput').val('');
		});

		// 수정된 data 전송 부분
		$('#pwCheckBtn').click(function() {
			var inputPw = $('#pwInput').val();
			console.log('입력된 비밀번호: ' + inputPw); // 입력된 비밀번호 출력
			if (!inputPw) {
				alert("비밀번호를 입력해주세요.");
				return;
			}

			$.ajax({
				url : "/lectureDeletePwCheck",
				type : "POST",
				data : {
					inputPwd : inputPw
				},
				success : function(response) {
					console.log('서버 응답: ' + response); // 서버 응답 출력
					if (response === "OK") {
						// 비밀번호가 맞으면 데이터 전송
						console.log('서버 응답: ' + response); // 서버 응답 출력
						$.ajax({
							url : "/lectureDelete",
							type : "POST",
							data : {
								lect_idx: lect_idx 
							},
							success : function(response) {
								if (response === "OK") {
									alert("삭제 성공")
									location.href = "/lectList";
								} else {
									alert("삭제 실패");
								}
							},
							error : function() {
								alert("서버 오류");
							}
						});
					} else {
						alert("비밀번호가 틀렸습니다.");
						$('#pwInput').val('');
					}
				},
				error : function() {
					alert("서버 오류가 발생했습니다.");
				}
			});
		});
	</script>
</body>
</html>
