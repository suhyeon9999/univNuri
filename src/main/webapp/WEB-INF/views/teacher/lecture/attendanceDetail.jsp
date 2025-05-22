<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../headerNavi.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>교수의 학생목록</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/header_navi.css">

<style type="text/css">


       .nav-container {
        margin-top: 20px;
        display: flex;
        justify-content: space-between;
        align-items: center;
      }

      .nav-left,
      .nav-right {
        display: flex;
      }
      .nav-left div,
      .nav-right div {
        width: 110px;
        height: 50px;
        text-align: center;
        line-height: 50px;
        border-radius: 8px 8px 0 0;
        margin-right: 2px;
      }
      .tab-btn active {
        background-color: #fdecb2;
      }
      
      .list {
	background-color: #fdecb2;
	}

      .attend {
        background-color: #e7f2d8;
      }
      .assign {
        background-color: #b9e5fc;
      }
      .exam {
  		background-color: #f9bcb5;
		}
      .detail {
        background-color: #d9c5ec;
      }

      .nav-container div a {
        text-decoration: none;
        color: gray;
      }
      .nav-container .attend a {
        color: black;
        font-weight: bold;
      }
.span-red {
	color: red;
}

.content-container {
	background-color: #f4f4f4;
	border-radius: 5px;
	padding: 10px 20px 20px 20px;
	max-width: 1400px;
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
	box-sizing: border-box;
	
}

.name {
	font-size: 25px;
	width:15%;
	
}


.infos {
	display: flex;
	width: 25%;
}


.info{
	display: flex;
	flex-direction: column;
	width: calc(100% / 3);
	

}


.scores {
	width: 45%;
	display: flex;
	}
	
	
.score1 {
	display: flex;
	flex-direction: column;
	align-items: center;
	width: 15%;
	flex-wrap: wrap;
	
	}

.score{
	display: flex;
	flex-direction: column;
	width: calc(100% / 6);


}


.top {
	font-weight: bold;
	text-align: center;
}


.bottom{
	text-align: center;

}
.bottom-total {
	display: flex;
	flex-wrap: wrap;
}

#nav-div{
display: flex;
justify-content: space-between;
align-items: center;
margin-bottom:10px;

}

.score label{
text-align: center;

}

input[type="radio"] {
  width: auto;     /* 👈 기본 크기로 되돌림 */
  margin: 0 auto;  /* 중앙 정렬하고 싶다면 */
  display: block; 
}


#search h3{

font-size: bold;
color:#ed6c02;

}

.bottom-total{
text-align: center;

}
</style>
</head>
<body>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>${lectureInfo.lect_name}</h1>
		</div>
		
		
		
		<div class="nav-container">
			<div class="nav-left">
				<div class="list">
					<a href="/get-lecture-student-list?lect_idx=${lect_idx}">학생 목록</a>
				</div>
				<div class="attend">
					<a href="/get-lecture-attendance-list?lect_idx=${lect_idx}">출결 관리</a>
				</div>
				<div class="assign">
					<a href="/assign-list?lect_idx=${lect_idx}">과제 현황</a>
				</div>
				<div class="exam">
					<a href="/exam-list?lect_idx=${lect_idx}">시험 출제</a>
				</div>
			</div>
			<div class="nav-right">
				<div class="detail">
					<a href="/lecture-info?lect_idx=${lect_idx}">강의 정보</a>
				</div>
			</div>
		</div>
		<form action="/go_attendance_update_ok" method="post">
			<div class="content-container">
				<div id="nav-container">
					<p id="totalstudent">
						전체 학생: <span class="span-red">${studentNum}</span>
					</p>
					<div id="nav-div">
						<div id="search">
							<h3>${theDate}</h3>
						</div>

						<div id="btn-group">
							<button type="button" class="btn-red" value="취소"
								onclick="get_reset()">취소</button>
							<button type="submit" class="btn-blue" value="저장">저장</button>
						</div>
					</div>
				</div>
				<input type="hidden" name="theDate" value="${theDate}"> <input
					type="hidden" id="lect_idx" name="lect_idx" value="${lect_idx}"> <input
					type="hidden" name="studentNum" value="${studentNum}">

				<c:choose>
					<c:when test="${empty getLectureAttendanceByDateStudentList}">
						<p>조회된 결과가 없습니다.</p>
					</c:when>
					<c:otherwise>


						<c:forEach var="k"
							items="${getLectureAttendanceByDateStudentList}"
							varStatus="status">

							<div class="onestudent">

								<div class="name">${status.index + 1}.${k.name}</div>

								
								<div class="infos">
									<div class="info">
										<div class="top">학번</div>
										<div class="bottom">${k.user_id }</div>
									</div>
									<div class="info">
										<div class="top">학년</div>
										<div class="bottom">${k.s_grade }</div>
									</div>
									<div class="info">
										<div class="top">학과</div>
										<div class="bottom">${k.dept_name}</div>
									</div>
									</div>
									<!-- // 해당 k.attend_status 값에 따라 checkbox에 체크되게 하기 -->
									<div class="scores ">

										<div class="score">
											<label for="undecided_${k.enroll_idx}">미정</label> <input
												type="radio" id="undecided_${k.enroll_idx}"
												name="attend_status_${k.enroll_idx}" value="0"
												<c:if test="${k.attend_status == 0 || k.attend_status == null}">checked</c:if> />
										</div>
										<%-- <div>attend값:${k.attend_status}</div> --%>
										<div class="score">
											<label for="present_${k.enroll_idx}">출석</label> <input
												type="radio" id="present_${k.enroll_idx}"
												name="attend_status_${k.enroll_idx}" value="1"
												<c:if test="${k.attend_status == 1}">checked</c:if> />
										</div>
										<div class="score">
											<label for="absent_${k.enroll_idx}">지각</label> <input
												type="radio" id="absent_${k.enroll_idx}"
												name="attend_status_${k.enroll_idx}" value="2"
												<c:if test="${k.attend_status == 2}">checked</c:if> />
										</div>
										<div class="score">
											<label for="late_${k.enroll_idx}">조퇴</label> <input
												type="radio" id="late_${k.enroll_idx}"
												name="attend_status_${k.enroll_idx}" value="3"
												<c:if test="${k.attend_status == 3}">checked</c:if> />
										</div>
										<div class="score">
											<label for="early-leave_${k.enroll_idx}">지각/조퇴</label> <input
												type="radio" id="early-leave_${k.enroll_idx}"
												name="attend_status_${k.enroll_idx}" value="4"
												<c:if test="${k.attend_status == 4}">checked</c:if> />
										</div>
										<div class="score">
											<label for="late-early-leave_${k.enroll_idx}">결석</label> <input
												type="radio" id="late-early-leave_${k.enroll_idx}"
												name="attend_status_${k.enroll_idx}" value="5"
												<c:if test="${k.attend_status == 5}">checked</c:if> />
										</div>

										<input type="hidden" name="enroll_idx[]"
											value="${k.enroll_idx}" />
									</div>
										<div class="score1">
											<div class="top">출결집계</div>
											<div class="bottom-total">미정:${k.undecided}
												출석:${k.present} 지각:${k.late} <br> 결석:${k.absent}
												조퇴:${k.early_leave} <br> 지각/조퇴:${k.late_early_leave}</div>

										</div>

								</div>

							
						</c:forEach>
					</c:otherwise>
				</c:choose>


			</div>
		</form>
	</div>

</body>
<script>


function get_reset() {
const lect_idx = document.getElementById("lect_idx").value;
	location.href = "/get-lecture-attendance-list?lect_idx=" + lect_idx;
}
</script>

</html>