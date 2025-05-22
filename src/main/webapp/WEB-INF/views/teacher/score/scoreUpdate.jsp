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




#info {
	display: flex;
	justify-content: space-between;
}




#check p{

font-size:25px;
font-weight: bold;
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


.bottom input{

width:50px;
text-align: center;
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
</style>
</head>
<body>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>${lect_name }</h1>
		</div>


			<form id="scoreForm" method="post" action="score_update_ok">
		<div id="mid-container">

				<div id="search">
				
						<label for="name">이름</label> 
						<input type="text" id="name" name="name"> 
						<label for="user_id">학번</label> 
						<input type="text" id="user_id" name="user_id"> 
						<input type="hidden" name="lect_idx" id="lect_idx" value="${lect_idx}"> 
						
				<button type="button" class="btn-orange" onclick="search_student(event)">
					<i class="fa-solid fa-magnifying-glass"></i>
				</button>
				
				</div>


					<div id="check">
						<p>성적 입력 완료: <span  class="span-blue"> ${scoreInputComplete} </span></p>
						<p>성적 입력 미완료: <span  class="span-red"> ${scoreInputNotComplete} </span></p>
					</div>
		
			
			<div id="buttons">
				<button id="list" type="button"   class="btn-red" onclick="go_score_detail()">취소</button>
				<button type="submit"  class="btn-blue">저장</button>
				<input type="hidden" name="lect_idx" value="${lect_idx}">


			</div>

		</div>
		<div id="content-container">



			<div id="info">
				<p id="totalstudent">전체 학생:<span class="span-red"> ${studentNum}</span></p>

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
									<!-- enroll_idx 숨겨서 전송 -->
									<input type="hidden" name="enroll_idx[]" value="${k.enroll_idx}" />

									<div class="score">
										<div class="top">중간</div>
										<div class="bottom">
											<input type="text" name="score_mid[]" size="45"
												value="${k.score_mid }" placeholder="입력 전">
										</div>
									</div>
									<div class="score">
										<div class="top">기말</div>
										<div class="bottom">
											<input type="text" name="score_final[]" size="45"
												value="${k.score_final }" placeholder="입력 전">
										</div>
									</div>
									<div class="score">
										<div class="top">과제</div>
										<div class="bottom">
											<input type="text" name="score_assign[]" size="45"
												value="${k.score_assign }" placeholder="입력 전">
										</div>
									</div>
									<div class="score">
										<div class="top">출석</div>
										<div class="bottom">
											<input type="text" name="score_attend[]" size="45"
												value="${k.score_attend}" placeholder="입력 전">
										</div>
									</div>
									<div class="score">
										<div class="top">총점</div>
										<div class="bottom">${empty k.score_total ? "입력 전" : k.score_total}</div>
									</div>

								</div>
							</div>

					</c:forEach>
					
				</c:otherwise>
			</c:choose>
		</div>
		</form>

	</div>
	<script type="text/javascript">

	function go_score_detail() {
		location.href = "/get-lecture-score-list?currentPastIs=current&lect_idx="+ ${lect_idx};
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
		    document.querySelector("#totalstudent").textContent = `전체 학생: ${studentNum}`;

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

		      const nameDiv = document.createElement("div");
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
		        	  if (type !== "score_total") {
		        	    const rawValue = k[type];
		        	    const isEmpty = rawValue === null || rawValue === undefined || rawValue.trim?.() === "";

		        	    if (isEmpty) {
		        	      const input = document.createElement("input");
		        	      input.type = "text";
		        	      input.name = type + "[]";
		        	      input.placeholder = "입력 전";
		        	      input.style.width = "50px";
		        	      input.style.textAlign = "center";
		        	      bottom.appendChild(input);

		        	      const span = document.createElement("span");
		        	      span.textContent = "입력 전";
		        	      span.style.color = "gray";
		        	      span.style.fontSize = "12px";
		        	      span.style.marginLeft = "5px";
		        	      bottom.appendChild(span);
		        	    } else {
		        	      const input = document.createElement("input");
		        	      input.type = "text";
		        	      input.name = type + "[]";
		        	      input.value = rawValue;
		        	      input.style.width = "50px";
		        	      input.style.textAlign = "center";
		        	      bottom.appendChild(input);
		        	    }
		        	  } else {
		        	    bottom.textContent = (k[type] === null || k[type] === "") ? "입력 전" : k[type];
		        	  }
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

	

	document.querySelectorAll("input[type='text']").forEach(input => {
    if (input.value === "") {
        input.value = ""; // 공백을 명시적으로 넣어서 전송되게 함
    }
});
	</script>

</body>
</html>


