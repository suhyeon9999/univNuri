<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../headerNavi.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>교수- 학생목록</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/header_navi.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">

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
      .nav-container .list a {
        color: black;
        font-weight: bold;
      }
.span-red {
	color: red;
}

#content-container {
	background-color: #f4f4f4;
	border-radius: 5px;
	padding: 10px 20px 20px 20px;
	max-width: 1400px;
}

#container-top {
	display: flex;
	flex-direction: column;
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
	font-size: 30px;
	color: black;
	width: 20%;
}

.infos {
	display: flex;
	width: 80%;
}

.info {
	display: flex;
	flex-direction: column;
	width: 20%;
	align-items: center;
}

.bottom {
	color: #ed6c02;
}
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
					<a href="/assign-list?lect_idx=${lect_idx }">과제 현황</a>
				</div>
				<div class="exam">
					<a href="/exam-list?lect_idx=${lect_idx }">시험 출제</a>
				</div>
			</div>
			<div class="nav-right">
				<div class="detail">
					<a href="/lecture-info?lect_idx=${lect_idx }">강의 정보</a>
				</div>
			</div>
		</div>

		<div id="content-container">
			<div id="container-top">
				<p id="totalstudent">
					전체 학생: <span class="span-red">${studentNum}</span>
				</p>

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
			</div>
			<c:choose>
				<c:when test="${empty studentList}">
					<p>조회된 결과가 없습니다.</p>
				</c:when>
				<c:otherwise>


					<c:forEach var="k" items="${studentList}" varStatus="status">

						<div class="onestudent">

							<div class="name">${status.index + 1}.${k.name}</div>

							<div class="infos">
								<div class="info">
									<div class="top">학번</div>
									<div class="bottom">${k.user_id }</div>
								</div>
								<div class="info">
									<div class="top">학년</div>
									<div class="bottom">${k.s_grade }학년</div>
								</div>
								<div class="info">
									<div class="top">학과</div>
									<div class="bottom">${k.dept_name}</div>
								</div>
								<div class="info">
									<div class="top">전화번호</div>
									<div class="bottom">${k.phone }</div>
								</div>
								<div class="info">
									<div class="top">이메일</div>
									<div class="bottom">${k.email}</div>
								</div>
							</div>

						</div>

					</c:forEach>

				</c:otherwise>
			</c:choose>
		</div>
	</div>


	<script>
function search_student(event) {
  event.preventDefault();

  const name = document.getElementById("name").value;
  const user_id = document.getElementById("user_id").value;
  
  const lect_idx = document.getElementById("lect_idx").value;
  fetch('/get-name-idx-student', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name, user_id, lect_idx })
  })
  .then(response => response.json())
  .then(data => {
    const studentList = data.studentList;
    const studentNum = data.studentNum;

    // 전체 학생 수 업데이트
    document.querySelector("#totalstudent span").textContent = studentNum;

    const container = document.getElementById("content-container");

    // 기존 학생 목록 제거
    container.querySelectorAll(".onestudent").forEach(el => el.remove());

    // 기존 "조회된 결과가 없습니다." 메시지 제거
    const oldMsg = container.querySelector("p");
    if (oldMsg && oldMsg.textContent.includes("조회된 결과가 없습니다")) {
      oldMsg.remove();
    }

    if (!studentList || studentList.length === 0) {
    	  // 이미 메시지가 있는지 확인
    	  if (!container.querySelector(".no-result-msg")) {
    	    const msg = document.createElement("p");
    	    msg.classList.add("no-result-msg"); // 중복 방지용 클래스 추가
    	    msg.textContent = "조회된 결과가 없습니다.";
    	    container.appendChild(msg);
    	  }
    	  return;
    	}

    // 결과 studentList 출력
    studentList.forEach((k, index) => {
      const studentDiv = document.createElement("div");
      studentDiv.classList.add("onestudent");

      const nameDiv = document.createElement("div");
      nameDiv.classList.add("name");
      nameDiv.textContent = index + 1 + "." + k.name;



      const infosDiv = document.createElement("div");
      infosDiv.classList.add("infos");

      const makeInfo = (label, value) => {
        const div = document.createElement("div");
        div.classList.add("info");

        const top = document.createElement("div");
        top.classList.add("top");
        top.textContent = label;

        const bottom = document.createElement("div");
        bottom.classList.add("bottom");
        bottom.textContent = value;

        div.appendChild(top);
        div.appendChild(bottom);
        return div;
      };

      infosDiv.appendChild(makeInfo("학번", k.user_id));
      infosDiv.appendChild(makeInfo("학년", k.s_grade + "학년"));
      infosDiv.appendChild(makeInfo("학과", k.dept_name));
      infosDiv.appendChild(makeInfo("전화번호", k.phone));
      infosDiv.appendChild(makeInfo("이메일", k.email));

      studentDiv.appendChild(nameDiv);
      studentDiv.appendChild(infosDiv);

      container.appendChild(studentDiv);
    });
  });
}
</script>
</body>

</html>