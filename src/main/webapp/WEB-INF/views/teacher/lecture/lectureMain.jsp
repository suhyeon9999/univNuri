<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../headerNavi.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>교수-수업관리 메인</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/header_navi.css">
<script src="https://kit.fontawesome.com/67e1f0bf0d.js"
	crossorigin="anonymous"></script>
<style type="text/css">


.content-container {
	background-color: #f4f4f4;
	border-radius: 5px;
	padding: 10px 20px 20px 20px;
	max-width: 1400px;
}


.span-red{
color:red;
}



.one-class {
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


.classes :not(:last-child){
	margin-bottom: 10px;
}


.name {
	font-size: 30px;
	margin-left: 20px;
	width:60%;
}

.name a {
	text-decoration: none;
	color: black;
}

.name a:visited {
	color: black;
}

.infos {
	display: flex;
	width:100%;

}

.info {
	display: flex;
	flex-direction: column;
	width:25%;
	align-items: center;
}

.top {
	font-weight: bold;
	
}

.bottom {
	color: red;
	text-align: center;
	
}

.mid-header {
	display: flex;
	align-items: center;
	max-width: 1400px;
	justify-content: space-around;
	margin-top: 40px;
}

.teacher{
	font-size: 200px;
}



</style>
</head>
<body>
	<div id="wrap">
	<c:choose>
    <c:when test="${user_level == 1}">
        <div class="title-container">
            <div class="highlight"></div>
            <h1 id="teacher">${name} 교수</h1>
        </div>
    </c:when>
</c:choose>
		<div class="title-container">
			<div class="highlight"></div>
			<h1>금학기 강의</h1>
		</div>

		<div class="content-container">
			<div class="container-top" >
				<p class="totalstudent" id="current-lecture-count">
					전체: <span class="span-red"> ${mainThisSemesterLectureCount}</span>
				</p>

			</div>
			<c:choose>
				<c:when test="${empty mainThisSemester}">
					<p>조회된 결과가 없습니다.</p>
				</c:when>
				<c:otherwise>

					<c:forEach var="k" items="${mainThisSemester}">
							<div class="one-class">

			<div class="name">
    <c:choose>
        <c:when test="${scoreLectureIs eq 'score'}">
            <!-- 과거 성적 조회일 경우, user_level과 t_idx 추가 -->
            <c:choose>
                <c:when test="${user_level == 1}">
                    <a href="/get-lecture-score-list?currentPastIs=current&lect_idx=${k.lect_idx}&user_level=${user_level}&t_idx=${t_idx}">
                        ${k.lect_name}
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="/get-lecture-score-list?currentPastIs=current&lect_idx=${k.lect_idx}">
                        ${k.lect_name}
                    </a>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:when test="${scoreLectureIs eq 'lecture'}">
            <!-- 강의 조회일 경우 -->
            <c:choose>
                <c:when test="${user_level == 1}">
                    <a href="/get-lecture-student-list?lect_idx=${k.lect_idx}&user_level=${user_level}&t_idx=${t_idx}">
                        ${k.lect_name}
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="/get-lecture-student-list?lect_idx=${k.lect_idx}">
                        ${k.lect_name}
                    </a>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>
</div>


								<div class="infos">
									<div class="info">
										<div class="top">강의실</div>
										<div class="bottom">${k.class_building}${k.class_room}</div>
									</div>
									<div class="info">
										<div class="top">강의 요일</div>
										<div class="bottom">${k.lect_day}</div>
									</div>
									<div class="info">
										<div class="top">강의 시간</div>
										<div class="bottom">${k.lect_start_time}-${k.lect_end_time}</div>
									</div>
									<div class="info">
										<div class="top">학생 수</div>
										<div class="bottom">${k.student_count}</div>
									</div>
									

								
							</div>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>

		</div>

		<div class="mid-header">
			<div class="title-container">
				<div class="highlight"></div>
				<h1>지난 강의 과목</h1>
			</div>

				<div class="select">
					<label for="year">연도</label> 
					<select id="year" name="year" ></select>
				</div>
				<div class="select">
					<label for="semester">학기</label> 
					<select id="semester" name="semester">

						<option value="all">전체</option>
						<option value="2">2학기</option>
						<option value="1">1학기</option>
					</select>
				</div>

				<div class="select">
					<label for="subject">과목명</label> <input id="subject" name="subject" placeholder="전체">
					<button type="button" class="btn-orange" onclick="search_past_lectures(event)">
						<i class="fa-solid fa-magnifying-glass"></i>
					</button>
				</div>

				<input type="hidden" id="t_idx" name="t_idx" value="${t_idx}">

		</div>


		<div class="content-container">
			<div class="container-top">
				<p class="totalstudent" id="past-lecture-count">
					전체: <span class="span-red"> ${mainPastSemesterLectureCount}</span>
				</p>

			</div>
			<div class="classes">
			<c:choose>
				<c:when test="${empty mainPastSemester}">
					<p>조회된 결과가 없습니다.</p>
				</c:when>
				<c:otherwise>

					<c:forEach var="k" items="${mainPastSemester}">

				<div class="one-class">
					
							<div class="name">
								  <c:choose>
								    <c:when test="${scoreLectureIs eq 'score'}">
								      <a href="/get-lecture-score-list?currentPastIs=past&lect_idx=${k.lect_idx}">${k.lect_name}</a>
								    </c:when>
								    <c:when test="${scoreLectureIs eq 'lecture'}">
								      ${k.lect_name}
								    </c:when>
								  </c:choose>
							</div>
							

								<div class="infos">
									<div class="info">
										<div class="top">강의실</div>
										<div class="bottom">${k.class_building}${k.class_room}</div>
									</div>
									<div class="info">
										<div class="top">강의 요일</div>
										<div class="bottom">${k.lect_day}</div>
									</div>
									<div class="info">
										<div class="top">강의 시간</div>
										<div class="bottom">${k.lect_start_time}-${k.lect_end_time}</div>
									</div>
									<div class="info">
										<div class="top">학생 수</div>
										<div class="bottom">${k.student_count}</div>
									</div>

								
								</div>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
				</div>

		</div>

	</div>

<script type="text/javascript">
// 현재 연도를 가져옴
const currentYear = new Date().getFullYear();
const yearSelect = document.getElementById("year");


// "전체" 옵션 추가
const allOption = document.createElement("option");
allOption.value = "all";
allOption.textContent = "전체";
yearSelect.appendChild(allOption);

// 최근 5년간의 연도 옵션 추가
for (let i = 0; i < 15; i++) {
	const year = currentYear - i;
	const option = document.createElement("option");
	option.value = year;
	option.innerText = year + "학년도";
	yearSelect.appendChild(option);
}
function search_past_lectures(event) {
	  event.preventDefault();
	  const scoreLectureIs = "${scoreLectureIs}";
	  const year = document.getElementById("year").value;
	  const semester = document.getElementById("semester").value;
	  const subject = document.getElementById("subject").value;
	  const t_idx = document.getElementById("t_idx").value;

	  fetch("/get-year-semester-lecture", {
	    method: "POST",
	    headers: { "Content-Type": "application/json" },
	    body: JSON.stringify({ year: year, semester: semester, subject: subject, t_idx: t_idx })
	  })
	  .then(response => response.json())
	  .then(data => {
		  console.log("받은 데이터:", data);
		  console.log("타입:", typeof data);
		  console.log("isArray:", Array.isArray(data));

		  const lectureList = data; // 바로 사용 가능
		  const count = data.length;

		  document.getElementById("past-lecture-count").querySelector("span").textContent = count;

		  const container = document.querySelector(".classes");
		  container.innerHTML = ""; // 기존 강의 목록 초기화

		  if (!lectureList || lectureList.length === 0) {
		    container.innerHTML = "<p>조회된 결과가 없습니다.</p>";
		    return;
		  }

		  lectureList.forEach(function(k) {
		    const div = document.createElement("div");
		    div.className = "one-class";

		    const nameDiv = document.createElement("div");
		    nameDiv.className = "name";

		    if (scoreLectureIs === "score") {
		      const link = document.createElement("a");
		      link.href = "/get-lecture-score-list?currentPastIs=past&lect_idx=" + k.lect_idx;
		      link.textContent = k.lect_name;
		      nameDiv.appendChild(link);
		    } else {
		      nameDiv.textContent = k.lect_name;
		    }
		    div.appendChild(nameDiv);


		    const infosDiv = document.createElement("div");
		    infosDiv.className = "infos";

		    const makeInfo = function(label, value) {
		      const infoDiv = document.createElement("div");
		      infoDiv.className = "info";

		      const topDiv = document.createElement("div");
		      topDiv.className = "top";
		      topDiv.textContent = label;

		      const bottomDiv = document.createElement("div");
		      bottomDiv.className = "bottom";
		      bottomDiv.textContent = value;

		      infoDiv.appendChild(topDiv);
		      infoDiv.appendChild(bottomDiv);

		      return infoDiv;
		    };

		    infosDiv.appendChild(makeInfo("강의실", k.class_building + k.class_room));
		    infosDiv.appendChild(makeInfo("강의 요일", k.lect_day));
		    infosDiv.appendChild(makeInfo("강의 시간", k.lect_start_time + "-" + k.lect_end_time));
		    infosDiv.appendChild(makeInfo("학생 수", k.student_count));

		    div.appendChild(infosDiv);
		    container.appendChild(div);
		  });
		});

	}






</script>
</body>

</html>