<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>교수의 성적관리</title>

<%@ include file="../../headerNavi.jsp"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/header_navi.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<style type="text/css">


#mid-container{
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 5px;

}


#reject-red{
color: red;

}
#reject-blue{
color: blue;

}


#content-container {
	background-color: #f4f4f4;
	border-radius: 5px;
	padding: 10px 20px 20px 20px;
	max-width: 1400px;
}


#buttons button{
height:50px;
}

#info {
	display: flex;
	justify-content: right;
	margin-top:5px;
}
.span-red{
color:red;
}


.onestudent {
		box-sizing: border-box;
        background-color: white;
        border-radius: 5px;
        display: flex;
        padding: 20px;
        align-items: center;
         margin: 4px;
        height: 90px;
        width: 100%;
        
}


.onestudent h2{
width: 50%;
}
.scores {
	display: flex;
	width: 50%;
}

.score {
	display: flex;
	flex-direction: column;
	margin: 30px;
	align-items: center;
	width: 10%;
}

input{

width:50px;
text-align: center;
}

.top {
	font-weight: bold;
}

.btn-long{
  width: 180px;
  margin-left:5px;

}

 .btn-group{
   display: flex;
   justify-content: right;
   align-items: center;
   margin-top: 10px;
   
 }
  
</style>
</head>
<body>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>이의제기 관리</h1>
		</div>
		
		<div id="mid-container">
			<div class="small-title-container">
				<div class="highlight"></div>
				<h2>${scoreMap.lect_name}</h2>
			</div>


		<div id="nav">
			<c:choose>
				<c:when test="${result eq 'permit'}">
					<h2 id="reject-blue">이의 제기가 승인되었습니다.(성적 재반영 완료)</h2>
				</c:when>
				<c:when test="${result eq 'reject'}">
					<h2 id="reject-red">이의 제기가 반려되었습니다.(기존 성적 유지)</h2>
				</c:when>
				<c:otherwise>
					<h2>결과 상태를 알 수 없습니다.</h2>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

			<div id="content-container">
				<div id="info">
					<p id="standard">학점 산청 기준: 중간 <span  class="span-red">35점</span>, 기말 <span  class="span-red">35점</span>, 과제<span  class="span-red"> 20점</span>, 출석 <span  class="span-red">10점</span></p>
				</div>
			<c:choose>
				<c:when test="${empty scoreMap}">
					<p>조회된 결과가 없습니다.</p>
				</c:when>
				<c:otherwise>

						<div class="onestudent">
							<h2>1. ${scoreMap.name}</h2>

							<div class="scores">
								<div class="score">
									<div class="top">중간</div>
									<div class="bottom">${scoreMap.score_mid }</div>
								</div>
								<div class="score">
									<div class="top">기말</div>
									<div class="bottom">${scoreMap.score_final }</div>
								</div>
								<div class="score">
									<div class="top">과제</div>
									<div class="bottom">${scoreMap.score_assign }</div>
								</div>
								<div class="score">
									<div class="top">출석</div>
									<div class="bottom">${scoreMap.score_attend}</div>
								</div>
								<div class="score">
									<div class="top">총점</div>
									<div class="bottom">${scoreMap.score_total}</div>
								</div>

							</div>
						</div>

				</c:otherwise>
			</c:choose>

		</div>

			<div class="btn-group">
			<button class="btn-gray btn-long"  type="button" onclick="go_objection_list()">이의제기목록</button>
			<button class="btn-green btn-long"  type="button" onclick="go_objection_detail()">이의제기내용</button>
			<input type="hidden" name="objection_idx" id="objection_idx"
				value="${objection_idx}"> <input type="hidden"
				name="enroll_idx" id="enroll_idx" value="${enroll_idx}">
			</div>


	</div>

	<script type="text/javascript">
		const objection_idx = document.getElementById("objection_idx").value;

		function go_objection_list() {
			location.href = "/objection-main";
		}
		function go_objection_detail() {
			location.href = "/objection-main-detail?objection_idx="
					+ objection_idx;
		}
	</script>
</body>
</html>
