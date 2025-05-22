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
.span-red {
	color: red;
}

.span-blue {
	color: blue;
}

#mid-container {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 5px;
}

#content-container {
	background-color: #f4f4f4;
	border-radius: 5px;
	padding: 10px 20px 20px 20px;
	max-width: 1400px;
}

#buttons {
	display: flex;
	align-items: center;
}

#buttons button {
	height: 50px;
	width: 100px;
}

#buttons :last-child {
	margin-left: 10px;
}

#check p {
	font-size: 25px;
	font-weight: bold;
}

#info {
	display: flex;
	justify-content: space-between;
	margin-top: 5px;
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

.name {
	width: 40%;
}

.scores {
	display: flex;
	width: 60%;
}

.score {
	display: flex;
	flex-direction: column;
	margin: 30px;
	align-items: center;
	width: 20%;
}

.top {
	font-weight: bold;
}

#check-flex {
	display: flex;
}

#check {
	margin-right: 70px;
}


</style>
</head>
<body>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>${lect_name }</h1>
		</div>


		<div id="mid-container">

			<div id="search">

				<label for="name">이름</label> <input type="text" id="name"
					name="name"> <label for="user_id">학번</label> <input
					type="text" id="user_id" name="user_id"> <input
					type="hidden" name="lect_idx" id="lect_idx" value="${lect_idx}">

				<button type="button" class="btn-orange"
					onclick="search_student(event)">
					<i class="fa-solid fa-magnifying-glass"></i>
				</button>

			</div>
			<%-- 성적 수정 form 및 버튼 표시 금학기 수업 성적 조회--%>
			<form action="/score-update" method="post">
				<c:choose>


					<c:when test="${currentPastIs eq 'current'}">

						<div id="check-flex">
							<div id="check">
								<p>
									성적 입력 완료: <span class="span-blue"> ${scoreInputComplete}
									</span>
								</p>
								<p>
									성적 입력 미완료: <span class="span-red">
										${scoreInputNotComplete} </span>
								</p>
							</div>




							<div id="buttons">
								<button id="list" type="button" class="btn-gray"
									onclick="go_main()">강의목록</button>
								<button id="update" type="submit" class="btn-blue">수정</button>
							</div>


						</div>
					</c:when>

					<c:otherwise>
						<%-- 그냥 조회만 하는 경우 과거 수업 성적 조회--%>
						<div id="buttons">
							<button id="list" type="button" class="btn-gray" onclick="go_main()">강의목록</button>



						</div>
					</c:otherwise>
				</c:choose>
				<input type="hidden" name="lect_idx" id="lect_idx"
					value="${lect_idx}">
			</form>

		</div>

		<div id="content-container">



			<div id="info">
				<p id="totalstudent">
					전체 학생:<span class="span-red"> ${studentNum}</span>
				</p>

				<p id="standard">학점 산청 기준: 중간 <span  class="span-red">35점</span>, 기말 <span  class="span-red">35점</span>, 과제<span  class="span-red"> 20점</span>, 출석 <span  class="span-red">10점</span></p>
			
			</div>
			<c:choose>
				<c:when test="${empty scoreList}">
					<p>조회된 결과가 없습니다.</p>
				</c:when>
				<c:otherwise>

					<c:forEach var="k" items="${scoreList}" varStatus="status">


						<div class="onestudent">
							<h2 class="name">${status.index + 1}.${k.name} (${k.user_id})</h2>

							<div class="scores">
								<c:forEach var="type" items="${scoreTypes}">
									<div class="score">
										<div class="top">
											<c:choose>
												<c:when test="${type eq 'score_mid'}">중간</c:when>
												<c:when test="${type eq 'score_final'}">기말</c:when>
												<c:when test="${type eq 'score_assign'}">과제</c:when>
												<c:when test="${type eq 'score_attend'}">출석</c:when>
												<c:when test="${type eq 'score_total'}">총점</c:when>
											</c:choose>
										</div>
										<div class="bottom">
											<c:choose>
												<c:when test="${currentPastIs eq 'current'}">
                ${empty k[type] ? "입력 전" : k[type]}
              </c:when>
												<c:otherwise>
                ${k[type]}
              </c:otherwise>
											</c:choose>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>

					</c:forEach>
				</c:otherwise>
			</c:choose>

		</div>

	</div>


	<script type="text/javascript">
 	function go_main() {
		location.href = "/main?scoreLectureIs=score";
	}




function search_student(event) {
	  event.preventDefault();

	  const name = document.getElementById("name").value;
	  const user_id = document.getElementById("user_id").value;
	  const lect_idx = document.getElementById("lect_idx").value;

	  fetch('/get-name-idx-student-score', {
	    method: 'POST',
	    headers: { 'Content-Type': 'application/json' },
	    body: JSON.stringify({ name, user_id, lect_idx })
	  })
	  .then(response => response.json())
	  .then(data => {
	    const scoreList = data.scoreList;
	    const scoreTypes = data.scoreTypes; // 예: ['score_mid', 'score_final', ...]
	    const studentNum = data.studentNum;
	    const currentPastIs = data.currentPastIs; // 'current' or 'past'

	    // 전체 학생 수 업데이트
	    document.querySelector("#totalstudent").innerHTML = "전체 학생: <span class='span-red'>" + studentNum + "</span>"; ;

	    const container = document.getElementById("content-container");

	    // 기존 학생 목록 제거
	    container.querySelectorAll(".onestudent").forEach(el => el.remove());

	    // 기존 메시지 제거
	    const oldMsg = container.querySelector("p");
	    if (oldMsg && oldMsg.textContent.includes("조회된 결과가 없습니다")) {
	      oldMsg.remove();
	    }

	    if (!scoreList || scoreList.length === 0) {
	    	  // 이미 메시지가 있는지 확인
	    	  if (!container.querySelector(".no-result-msg")) {
	    	    const msg = document.createElement("p");
	    	    msg.classList.add("no-result-msg"); // 중복 방지용 클래스 추가
	    	    msg.textContent = "조회된 결과가 없습니다.";
	    	    container.appendChild(msg);
	    	  }
	    	  return;
	    	}


	    // 검색된 학생 목록 렌더링
	    scoreList.forEach((k, index) => {
	      const studentDiv = document.createElement("div");
	      studentDiv.classList.add("onestudent");

	      const nameDiv = document.createElement("h2");
	      nameDiv.classList.add("name");
	      nameDiv.textContent = (index + 1) + "." + k.name + "(" + k.user_id + ")";
	      studentDiv.appendChild(nameDiv);

	      const scoresDiv = document.createElement("div");
	      scoresDiv.classList.add("scores");

	      scoreTypes.forEach((type) => {
	        const scoreDiv = document.createElement("div");
	        scoreDiv.classList.add("score");

	        const top = document.createElement("div");
	        top.classList.add("top");
	        switch (type) {
	          case "score_mid": top.textContent = "중간"; break;
	          case "score_final": top.textContent = "기말"; break;
	          case "score_assign": top.textContent = "과제"; break;
	          case "score_attend": top.textContent = "출석"; break;
	          case "score_total": top.textContent = "총점"; break;
	          default: top.textContent = type;
	        }

	        const bottom = document.createElement("div");
	        bottom.classList.add("bottom");
	        if (currentPastIs === "current") {
	        	  bottom.textContent = (k[type] === null || k[type] === undefined || k[type] === "") ? "입력 전" : k[type];
	        	} else {
	          bottom.textContent = k[type];
	        }

	        scoreDiv.appendChild(top);
	        scoreDiv.appendChild(bottom);
	        scoresDiv.appendChild(scoreDiv);
	      });

	      studentDiv.appendChild(scoresDiv);
	      container.appendChild(studentDiv);
	    });
	  });
	}

</script>
</body>




</html>


