
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
				<input type="button" class="btn-blue" value="등록" onclick="submitLecture()">
			</div>
			<table>
				 <tr>
		              <th><label for="name">*강의명</label></th>
		              <td><input type="text" name="name" required></td>
		          </tr>
		          <tr>
				    <th><label for="dept_idx">*개설학과</label></th>
				    <td>
				        <select name="dept_idx" required onchange="onDeptChange(this)">
						    <c:choose>
						        <c:when test="${not empty deptList}">
						            <option value="" disabled selected>학과선택</option>
						            <c:forEach var="dept" items="${deptList}">
						                <option value="${dept.dept_idx}">${dept.dept_name}</option>
						            </c:forEach>
						        </c:when>
						        <c:otherwise>
						            <option value="" disabled selected>등록된 학과가 없습니다.</option>
						        </c:otherwise>
						    </c:choose>
						</select>
				    </td>
				</tr>
					 <tr>
					 	<!-- 모달 -->
			              <th><label for="subjectGroup">*과목군</label></th>
			              <td><input type="text" id="subjectGroup" name="subjectGroup" 
			              		readonly onclick="openModal('subjectModal')" required></td>
			          </tr>
					 <tr>
		                <th><label for="lect_credit">*이수학점</label></th>
		                <td><!-- 학점어떻게할지 변경해야함 -->
		                    <select name="lect_credit" required >
		                        <option value="" disabled selected>이수학점 선택</option>
		                        <option value="1">1</option>
		                        <option value="2">2</option>
		                        <option value="3">3</option>
		                    </select>
		                </td>
		            </tr>
		          	 <tr>
					    <th><label for="teacher">*담당교수</label></th>
					    <td>
							<select name="teacher" id="teacherSelect" required>
							    <option value="" disabled selected>담당교수 선택</option>
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
        <label for="sub_set_name">과목군</label>
        <input type="text" id="sub_set_name" name="sub_set_name" placeholder="과목군 입력">
        <label for="subject_name"> 과목명</label>
        <input type="text" id="subject_name" name="subject_name" placeholder="과목명 입력">
        <button type="submit" onclick="loadSubjectGroups()" class="btn-orange"><i class="fa-solid fa-magnifying-glass"></i></button>
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
									                   data-subjects="${k.subjects}"
									                   onchange="limitSelection(this)">
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
     	<button type="button" class="btn-red" onclick="closeModal('subjectModal')">취소</button>
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
							<select id="buildingSelect" name="building" onchange="onBuildingChange(this)">
							    <option value="" disabled selected>강의건물 선택</option>
							    <option value="0">미래관</option>
							    <option value="1">현재관</option>
							    <option value="2">과거관</option>
							</select>

		                </td>
	        		</tr>
        		<tr>
        			<th><label for="major">강의실</label></th>
		                <td>
							<select id="roomSelect" name="classroom">
							    <option value="" disabled selected>강의실 선택</option>
							</select>
		                </td>
	        		</tr>
        		<tr>
        			<th><label for="major">강의요일</label></th>
		                <td>
							<select id="daySelect" name="lect_day" required>
							    <option value="" disabled selected>강의요일 선택</option>
							    <option value="2">월</option>
							    <option value="3">화</option>
							    <option value="4">수</option>
							    <option value="5">목</option>
							    <option value="6">금</option>
							</select>
		                </td>
	        		</tr>
        		<tr>
        			<th><label for="major">강의시간</label></th>
		                <td>
							<select name="lect_start_time" id="timeSelect" required>
							    <option value="" disabled selected>강의시간 선택</option>
							</select>

		                </td>
	        		</tr>
        	</table>
        <button type="button" class="btn-red" onclick="closeModal('classroomModal')">취소</button>
        <button onclick="applyClassroom()" class="btn-blue">확인</button>
    </div>
</div>

<script>
function openModal(modalId) {
    document.getElementById(modalId).style.display = "block";
    if (modalId === 'subjectModal') loadSubjectGroups();
}

function closeModal(modalId) {
    document.getElementById(modalId).style.display = "none";
}

// 과목군 적용
function applySubject() {
    const checkboxes = document.querySelectorAll('input[name="subjectGroupCheckbox"]:checked');
    const selectedGroups = [];
    const selectedSubjects = [];

    for (var i = 0; i < checkboxes.length; i++) {
        var checkbox = checkboxes[i];
        selectedGroups.push(checkbox.value);
        selectedSubjects.push(checkbox.getAttribute('data-subjects'));
    }

    document.getElementById('subjectGroup').value = selectedGroups.join(', ') || '';
    console.log('선택된 과목군:', selectedGroups);
    console.log('선택된 과목:', selectedSubjects);
    closeModal('subjectModal');
}

// 강의실 적용
function applyClassroom() {
    var building = document.getElementById("buildingSelect").value;
    var room = document.getElementById("roomSelect").value;
    var day = document.getElementById("daySelect").value;
    var time = document.getElementById("timeSelect").value;

    if (!building || !room || !day || !time) {
        alert("모든 강의실 정보를 선택해야 합니다.");
        return;
    }

    var classroomValue = "[" + getBuildingName(building) + "] " + room + " / " + getDayName(day) + " / " + time;
    document.getElementById("classroom").value = classroomValue;
    closeModal('classroomModal');
}

function getDayName(value) {
    var days = {'2': '월', '3': '화', '4': '수', '5': '목', '6': '금'};
    return days[value] || value;
}

function getBuildingName(value) {
    var buildings = {'0': '미래관', '1': '현재관', '2': '과거관'};
    return buildings[value] || value;
}

// 체크 개수 제한
function limitSelection(checkbox) {
    var selected = document.querySelectorAll('input[name="subjectGroupCheckbox"]:checked');
    if (selected.length > 3) {
        alert("최대 3개의 과목군만 선택할 수 있습니다.");
        checkbox.checked = false;
    }
}

// 과목군 불러오기
function loadSubjectGroups() {
    var deptIdx = document.querySelector('select[name="dept_idx"]').value;
    var subjectGroupList = document.getElementById('subject-group-list');

    if (!deptIdx) {
        alert("먼저 학과를 선택해주세요.");
        return;
    }

    $.ajax({
        url: '/subjectGroupList',
        type: 'get',
        data: { dept_idx: deptIdx },
        success: function(data) {
            console.log("✅ 응답 데이터:", data);
            if (data.length === 0) {
                subjectGroupList.innerHTML = "<p>해당 조건에 맞는 과목군이 없습니다.</p>";
                return;
            }
            subjectGroupList.innerHTML = generateSubjectGroupHTML(data);
        },
        error: function() {
            alert("과목군 정보를 불러오는 중 오류가 발생했습니다.");
        }
    });
}

function generateSubjectGroupHTML(data) {
    var html = "<table><thead><tr>" +
        "<th>선택</th><th>No.</th><th>과목군명</th><th>학과</th><th>과목</th>" +
        "</tr></thead><tbody>";

    for (var i = 0; i < data.length; i++) {
        var item = data[i];
        html += "<tr>";
        html += "<td><input type='checkbox' name='subjectGroupCheckbox' value='" + item.group_name +
                "' data-subjects='" + item.subjects + "' onchange='limitSelection(this)'></td>";
        html += "<td>" + (i + 1) + "</td>";
        html += "<td>" + item.group_name + "</td>";
        html += "<td>" + item.dept_name + "</td>";
        html += "<td>" + item.subjects + "</td>";
        html += "</tr>";
    }

    html += "</tbody></table>";
    return html;
}

// 학과 변경 시 교수 불러오기
function onDeptChange(select) {
    var deptIdx = select.value;

    $.ajax({
        url: '/teachersByDept',
        type: 'get',
        data: { dept_idx: deptIdx },
        success: function(list) {
            var $teacherSelect = $('#teacherSelect');
            $teacherSelect.empty();
            $teacherSelect.append('<option value="" disabled selected>담당교수 선택</option>');

            if (list.length > 0) {
                for (var i = 0; i < list.length; i++) {
                    $teacherSelect.append('<option value="' + list[i].t_idx + '">' + list[i].name + '</option>');
                }
            } else {
                $teacherSelect.append('<option disabled selected>등록된 교수가 없습니다.</option>');
            }
        },
        error: function() {
            alert("교수 정보를 불러오는 중 오류가 발생했습니다.");
        }
    });
}

// 건물 선택 시 강의실 불러오기
function onBuildingChange(select) {
    var building = select.value;

    $.ajax({
        url: '/getRoomsByBuilding',
        type: 'get',
        data: { building: building },
        success: function(roomList) {
            var $roomSelect = $('#roomSelect');
            $roomSelect.empty();
            $roomSelect.append('<option value="" disabled selected>강의실 선택</option>');

            if (roomList.length > 0) {
                for (var i = 0; i < roomList.length; i++) {
                    var room = roomList[i];
                    $roomSelect.append('<option value="' + room.class_room + '">' + room.class_room + '</option>');
                }
            } else {
                $roomSelect.append('<option disabled selected>강의실 없음</option>');
            }
        },
        error: function() {
            alert("강의실 정보를 불러오는 데 실패했습니다.");
        }
    });
}

// 강의시간 불러오기
function loadAvailableTimes() {
    var building = document.getElementById("buildingSelect").value;
    var room = document.getElementById("roomSelect").value;
    var day = document.getElementById("daySelect").value;

    if (!building || !room || !day) return;

    $.ajax({
        url: "/availableStartTimes",
        type: "get",
        data: {
            class_building: building,
            class_room: room,
            lect_day: day
        },
        success: function(list) {
            console.log(list);
            var $timeSelect = $("#timeSelect");
            $timeSelect.empty();
            $timeSelect.append('<option value="" disabled selected>강의시간 선택</option>');

            for (var i = 0; i < list.length; i++) {
                var time = list[i];
                var endTime = getEndTime(time, 50);
                $timeSelect.append('<option value="' + time + '">' + time + ' ~ ' + endTime + '</option>');
            }
        },
        error: function() {
            alert("시간 정보를 불러오는 데 실패했습니다.");
        }
    });
}

function getEndTime(startTime, durationMinutes) {
    var parts = startTime.split(':');
    var hour = parseInt(parts[0], 10);
    var minute = parseInt(parts[1], 10);
    var date = new Date(0, 0, 0, hour, minute + durationMinutes);
    return date.toTimeString().slice(0, 5);
}

// 이벤트 등록
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("daySelect").addEventListener("change", loadAvailableTimes);
    document.getElementById("roomSelect").addEventListener("change", loadAvailableTimes);
});

//강의 등록
function submitLecture() {
	var startTime = document.getElementById("timeSelect").value;
    var endTime = getEndTime(startTime, 50); // 여기서 종료 시간 생성

    var data = {
        lect_name: document.querySelector('input[name="name"]').value,
        dept_idx: document.querySelector('select[name="dept_idx"]').value,
        subject_group: document.getElementById("subjectGroup").value,
        lect_credit: document.querySelector('select[name="lect_credit"]').value,
        t_idx: document.getElementById("teacherSelect").value,
        class_building: document.getElementById("buildingSelect").value,
        class_room: document.getElementById("roomSelect").value,
        lect_day: document.getElementById("daySelect").value,
        lect_start_time: startTime,
        lect_end_time: endTime // ✅ 추가된 종료 시간
    };

    console.log("📦 전송할 데이터:", data);

    // 유효성 검사
    for (var key in data) {
        if (!data[key]) {
            alert("모든 항목을 입력해야 합니다. (" + key + ")");
            return;
        }
    }

    $.ajax({
        url: "/insertLecture",
        method: "post",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(res) {
            alert("강의 등록이 완료되었습니다.");
            location.href = "/lectList";
        },
        error: function(err) {
            alert("등록 중 오류 발생");
            console.error(err);
        }
    });
}

</script>
</body>
</html>