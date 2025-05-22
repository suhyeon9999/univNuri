<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../headerNavi.jsp"%>

<!DOCTYPE html>

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

table {
	border-collapse: collapse;
	width: 100%;
	background-color: white;
	text-align: center;
}

th, td {
	border: 1px solid black;
	padding: 5px 10px;
}

th {
	background-color: #e2e2f8;
}

td a {
	color: black;
	text-decoration: none;
}

.btn-blue {
	color: white;
}

.content-container {
	background-color: #f4f4f4;
	border-radius: 5px;
	padding: 10px 20px 20px 20px;
	max-width: 1400px;
}

#container-top-bottom {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 10px;
}

#search {
	display: flex;
	align-items: center;
}

#search button {
	margin-left: 10px;
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
					<a href="/get-lecture-student-list?lect_idx=${lect_idx }">학생 목록</a>
				</div>
				<div class="attend">
					<a href="/get-lecture-attendance-list?lect_idx=${lect_idx }">출결 관리</a>
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
		<div class="content-container">
			<div id="container-top">
				<p id="totalstudent">
					전체 학생: <span class="span-red">${studentNum}</span>
				</p>

				<form method="post" action="/get-lecture-attendance-today">

					<div id="container-top-bottom">
						<div id="search">

							<div class="select">
								<label for="date">날짜</label> <select id="date" name="date">
									<option value="all" selected>전체</option>
									<!-- 이게 기본값 -->
									<c:forEach var="d" items="${lectDateWeekList}">
										<option value="${d.lectDate}">${d.countWeek}회차 |
											${d.lectDate}</option>
									</c:forEach>

								</select>
							</div>

							<button type="button" class="btn-orange"
								onclick="search_attendance_by_date(event)">
								<i class="fa-solid fa-magnifying-glass"></i>
							</button>

						</div>

						<input type="submit" class="btn-blue" value="출석 체크하기">
					</div>
					<!-- 	// 오늘자 출석체크 페이지로 이동, 수업하는 날 아닐 시 해당 날짜는 수업이 없습니다 띄우기 -->
					<input type="hidden" id="lect_idx" name="lect_idx"
						value="${lect_idx}"> <input type="hidden"
						id="lectDateList" name="lectDateList" value="lectDateList">
				</form>
			</div>
			<div id="container-content"></div>
			<c:choose>
				<c:when test="${empty attendanceSummaryList}">
					<p>조회된 결과가 없습니다.</p>
				</c:when>
				<c:otherwise>
					<table id="table">
						<thead>
							<tr>

								<th>수업회차</th>
								<th>날짜</th>
								<th>출결</th>
							</tr>

						</thead>
						<tbody>
							<c:forEach var="k" items="${attendanceSummaryList}"
								varStatus="status">
								<tr>
									<td><a
										href="/get-lecture-attendance-by-date-student?lectDate=${k.lectDate}&lect_idx=${lect_idx}">${k.countWeek}회차</a></td>
									<td><a
										href="/get-lecture-attendance-by-date-student?lectDate=${k.lectDate}&lect_idx=${lect_idx}">${k.lectDate}</a></td>
									<td>미정: ${k.undecided} 출석:${k.present} 지각:${k.late}
										결석:${k.absent} 조퇴:${k.early_leave} 지각/조퇴:${k.late_early_leave}</td>

								</tr>

							</c:forEach>
						</tbody>

					</table>

				</c:otherwise>
			</c:choose>
		</div>
	</div>


	<script type="text/javascript">
    
    function search_attendance_by_date(event) {
        event.preventDefault(); 
      const date = document.getElementById("date").value;
      const lect_idx = document.getElementById("lect_idx").value;
      const lectDateWeekList = [
          <c:forEach var="item" items="${lectDateWeekList}" varStatus="status">
              {
                  lectDate: "${item.lectDate}",
                  countWeek: "${item.countWeek}"
              }<c:if test="${!status.last}">,</c:if>
          </c:forEach>
      ];
    
      fetch('/get-attendance-by-date-ajax', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ date, lect_idx, lectDateWeekList })
      })
      .then(response => response.json())
      .then(data => {
    	    console.log(data);
    	    const containerContent = document.querySelector("#table");
    	    containerContent.innerHTML = ""; // 기존 내용 제거

    	    if (data.length === 0) {
    	        containerContent.innerHTML = "<p>조회된 결과가 없습니다.</p>";
    	        return;
    	    }


    	    // 헤더 생성
    	    const thead = document.createElement("thead");
    	    thead.innerHTML = `
    	        <tr>
    	            <th>수업회차</th>
    	            <th>날짜</th>
    	            <th>출결</th>
    	        </tr>
    	    `;
    	    table.appendChild(thead);

    	    // 바디 생성
    	    const tbody = document.createElement("tbody");

    	    data.forEach(k => {
    	        const row = document.createElement("tr");

    	        const weekCell = document.createElement("td");
    	        const weekLink = document.createElement("a");
    	        
    	        const y = k.lectDate.year;
    	        const m = String(k.lectDate.month).padStart(2, '0');
    	        const d = String(k.lectDate.day).padStart(2, '0');
    	        
    	        
    	        weekLink.href = "/get-lecture-attendance-by-date-student?lectDate="+ y + "-" + m + "-" + d + "&lect_idx=" + lect_idx;
    	        weekLink.textContent = k.countWeek + "회차";
    	        weekCell.appendChild(weekLink);

    	        const dateCell = document.createElement("td");
    	        const dateLink = document.createElement("a");
    	        console.log("lectDate 값:", k.lectDate);
/*     	        const dateObj = new Date(k.lectDate.year, k.lectDate.month - 1, k.lectDate.day);
    	        dateLink.textContent = dateObj.toISOString().slice(0, 10); */


    	        dateLink.href = "/get-lecture-attendance-by-date-student?lectDate=" + y + "-" + m + "-" + d + "&lect_idx=" +lect_idx;
    	        dateLink.textContent = y+ "-" + m + "-" + d;
    	        dateCell.appendChild(dateLink);

    	        const statusCell = document.createElement("td");
    	        statusCell.textContent = "미정:" +  k.undecided + "출석:" + k.present + "지각:" + k.late + "결석:" + k.absent  + "조퇴:" + k.early_leave  +"지각/조퇴: " + k.late_early_leave;

    	        row.appendChild(weekCell);
    	        row.appendChild(dateCell);
    	        row.appendChild(statusCell);

    	        tbody.appendChild(row);
    	    });

    	    table.appendChild(tbody);
    	    containerContent.appendChild(table);
    	});
    }

    </script>
</body>
</html>
