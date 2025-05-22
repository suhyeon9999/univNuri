<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/admin/insertLect.css">
</head>
<body>
<%@ include file="../../headerNavi.jsp"%>
	<div id="wrap">
		<div class="total-container">
			<div class="title-container">
				<div class="highlight"></div>
				<h1>강의 등록</h1>
			</div>
			<div class="btn-group">
				<button class="btn-red" onclick="location.href='${pageContext.request.contextPath}/lectList'">취소</button>
				<input type="button" class="btn-blue" value="등록" />
			</div>
			<table>
				 <tr>
		              <th><label for="name">*강의명</label></th>
		              <td><input type="text" name="name" required></td>
		          </tr>
		           <tr>
		                <th><label for="major">*개설학과</label></th>
		                <td>
		                    <select name="major" required >
		                        <option value="" disabled selected>학과선택</option>
		                        <option value="컴퓨터공학">컴퓨터공학</option>
		                        <option value="전자공학">전자공학</option>
		                        <option value="경영학">경영학</option>
		                    </select>
		                </td>
		            </tr>		            
					 <tr>
					 	<!-- 모달 -->
			              <th><label for="name">*과목군</label></th>
			              <td><input type="text" id="subjectGroup" name="subjectGroup" 
			              		readonly onclick="openModal('subjectModal')" required></td>
			          </tr>
					 <tr>
		                <th><label for="major">*이수학점</label></th>
		                <td><!-- 학점어떻게할지 변경해야함 -->
		                    <select name="major" required >
		                        <option value="" disabled selected>이수학점 선택</option>
		                        <option value="1">1</option>
		                        <option value="2">2</option>
		                        <option value="3">3</option>
		                    </select>
		                </td>
		            </tr>
		          	  <tr>
		                <th><label for="major">*담당교수</label></th>
		                <td>
		                    <select name="major" required >
		                        <option value="" disabled selected>담당교수 선택</option>
		                        <option value="이정은">이정은</option>
		                        <option value="강지윤">강지윤</option>
		                        <option value="이수현">이수현</option>
		                    </select>
		                </td>
		            </tr>
		             <tr><!-- 모달 -->
			              <th><label for="name">강의실</label></th>
			              <td><input type="text" id="classroom" name="classroom"
			              		 readonly onclick="openModal('classroomModal')" required></td>
		          	 </tr>
			</table>
		</div>
	</div>
	
	<!-- 과목군 모달 -->
<div id="subjectModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal('subjectModal')">&times;</span>
        <h2>과목군 배정하기</h2>
        <label for="subject-set">과목군</label>
        <input type="text" id="subject-set" name="subject-set" placeholder="강의실 입력">
        <label for="subject">과목명</label>
        <input type="text" id="subject" name="subject" placeholder="강의실 입력">
        <button type="submit" class="btn-orange"><i class="fa-solid fa-magnifying-glass"></i></button>
        <!-- 선택한 과목군 표시 -->
        
          <div class="assign-list-wrapper" id="subject-group-list">
                    <c:choose>
                        <c:when test="${empty subjectSetList}">
                            <p>해당 조건에 맞는 과목군이 없습니다.</p>
                        </c:when>
                        <c:otherwise>
                            <table>
                                <thead>
                                    <tr>
                                    	<th>선택</th>
                                        <th>No.</th>
                                        <th>과목군명</th>                                        
                                        <th>과목</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="k" items="${subjectSetList}" varStatus="v">
                                        <tr>
                                        	<td>
                                        		<input type="checkbox" name="subjectGroupCheckbox" 
                                       value="${k.group_name}" 
                                       data-subjects="${k.subjects}">
                                        	</td>
                                            <td>${v.index + 1}</td>
                                            <td>${k.group_name}</td>
                                            <td>${k.dept_name}</td>
                                            <td>${k.subjects}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>
     	<button class="btn-red">취소</button>
        <button onclick="applySubject()" class="btn-blue">확인</button>
    </div>
</div>

<!-- 강의실 모달 -->
<div id="classroomModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal('classroomModal')">&times;</span>
        <h2>강의실 배정</h2>
        	<table>
        		<tr>
        			<th><label for="major">강의건물</label></th>
		                <td>
		                    <select name="major" required >
		                        <option value="" disabled selected>강의건물 선택</option>
		                        <option value="미래관">미래관</option>
		                        <option value="현재관">현재관</option>
		                        <option value="과거관">과거관</option>
		                    </select>
		                </td>
	        		</tr>
        		<tr>
        			<th><label for="major">강의실</label></th>
		                <td>
		                    <select name="major" required >
		                        <option value="" disabled selected>강의실 선택</option>
		                        <option value="미래관">101</option>
		                        <option value="현재관">201</option>
		                        <option value="과거관">105</option>
		                    </select>
		                </td>
	        		</tr>
        		<tr>
        			<th><label for="major">강의요일</label></th>
		                <td>
		                    <select name="major" required >
		                        <option value="" disabled selected>강의요일 선택</option>
		                        <option value="월">월</option>
		                        <option value="화">화</option>
		                        <option value="수">수</option>
		                    </select>
		                </td>
	        		</tr>
        		<tr>
        			<th><label for="major">강의시간</label></th>
		                <td>
		                    <select name="major" required >
		                        <option value="" disabled selected>강의시간 선택</option>
		                        <option value="1">09:00 ~ 09:50</option>
		                        <option value="2">10:00 ~ 10:50</option>
		                        <option value="3">11:00 ~ 11:50</option>
		                    </select>
		                </td>
	        		</tr>
        	</table>
        <button class="btn-red">취소</button>
        <button onclick="applyClassroom()" class="btn-blue">확인</button>
    </div>
</div>

<script>
// 모달 열기
function openModal(modalId) {
    document.getElementById(modalId).style.display = "block";
}

// 모달 닫기
function closeModal(modalId) {
    document.getElementById(modalId).style.display = "none";
}

// 과목군 선택
function selectSubject() {
    let select = document.getElementById("subjectSelect");
    let value = select.value;
    document.getElementById("subjectGroup").value = value;
}

// 과목군 적용
function applySubject() {
    closeModal('subjectModal');
}

function applySubject() {
    const checkboxes = document.querySelectorAll('input[name="subjectGroupCheckbox"]:checked');
    const selectedGroups = [];
    const selectedSubjects = [];

    checkboxes.forEach(checkbox => {
        selectedGroups.push(checkbox.value);
        selectedSubjects.push(checkbox.getAttribute('data-subjects'));
    });

    // 선택된 과목군과 과목을 입력 필드에 반영 (예: 쉼표로 구분)
    document.getElementById('subjectGroup alım').value = selectedGroups.join(', ') || '';
    
    // 선택된 과목 정보를 추가로 활용하려면 여기서 처리
    console.log('선택된 과목군:', selectedGroups);
    console.log('선택된 과목:', selectedSubjects);

    closeModal('subjectModal');
}




// 강의실 입력
function applyClassroom() {
    let input = document.getElementById("classroomInput").value;
    document.getElementById("classroom").value = input;
    closeModal('classroomModal');
}

// 모달 외부 클릭 시 닫기(취소버튼으로 변경해야함)
window.onclick = function(event) {
    if (event.target.className === "modal") {
        event.target.style.display = "none";
    }
}
</script>
</body>
</html>

</body>
</html>