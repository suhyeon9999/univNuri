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
				<button class="btn-blue">취소</button>
			<c:if test="${sessionScope.aInfo.a_grade <= 1 || sessionScope.access.acc_lect_delete == 1}">
				<button class="btn-red" onclick="showPwModal()">수정하기</button>
			</c:if>				
			</div>
			<table>
						<tr>
						    <th>강의명</th>
						    <td><input type="text" name="lect_name" value="${detailLect.lect_name }" /></td>
						</tr>
							<tr>
							    <th>강의코드</th>
							    <td><input type="text" name="lect_id" value="${detailLect.lect_id }"></td>
							</tr>
		           <tr>
						    <th>개설학과</th>
						    <td>
						        <select name="dept_idx" id="deptSelect">
						            <option value="" disabled selected>학과 선택</option>
						        </select>
						    </td>
						</tr>

						<tr>
						   <th>과목구성</th>
									<td>
									  <select name="sub_set_name" required>
									    <c:forEach var="group" items="${subjectGroupList}">
									      <option value="${group}" <c:if test="${group eq detailLect.sub_set_name}">selected</c:if>>
									        ${group}
									      </option>
									    </c:forEach>
									  </select>
									</td>


						</tr>
		            <tr>
		                <th>이수학점</th>
		                <td>
						<select name="lect_credit">
						  <option value="1" <c:if test="${detailLect.lect_credit == 1}">selected</c:if>>1 학점</option>
						  <option value="2" <c:if test="${detailLect.lect_credit == 2}">selected</c:if>>2 학점</option>
						  <option value="3" <c:if test="${detailLect.lect_credit == 3}">selected</c:if>>3 학점</option>
						</select>

		            </tr>
		            <tr>
		                <th>담당교수</th>
							<td>
							  <c:choose>
							    <c:when test="${empty professorList}">
							      담당교수 미배정
							    </c:when>
							    <c:otherwise>
							      <select name="t_idx">
							        <c:forEach var="prof" items="${professorList}">
							          <option value="${prof.t_idx}"
							            <c:if test="${prof.t_idx == lecture.t_idx}">selected</c:if>>
							            ${prof.name}
							          </option>
							        </c:forEach>
							      </select>
							    </c:otherwise>
							  </c:choose>
							</td>

		            </tr>
							<tr>
							    <th>수강정원</th>
							    <td><input name="lect_max" max="30" type="number" min="1" value="${detailLect.lect_max}" id="lect_max"></td>
							</tr>
								           <tr>
						    <th>강의실</th>
						    <td>
						        <c:choose>
						            <c:when test="${empty detailLect.class_building or empty detailLect.class_room}">강의실 미정</c:when>
						            <c:otherwise>
						                <select id="buildingSelect" name="class_building">
						                    <option value="0" ${detailLect.class_building eq '미래관' ? 'selected' : ''}>미래관</option>
						                    <option value="1" ${detailLect.class_building eq '현재관' ? 'selected' : ''}>현재관</option>
						                    <option value="2" ${detailLect.class_building eq '과거관' ? 'selected' : ''}>과거관</option>
						                </select>
						                <select id="roomSelect" name="class_room" disabled>
						                    <option value="">강의실 선택</option>
						                </select>
						            </c:otherwise>
						        </c:choose>
						    </td>
						</tr>
		            <tr>
		            <!-- 강의 요일도 모달 -->
		                <th>강의요일</th>
		                <td>
		                	<c:choose>
			                	<c:when test="${empty detailLect.lect_day} ">강의요일 미정</c:when>
			                	<c:otherwise>
				                	<select id="weekdaySelect" name="lect_day">
									  <option value="2">월</option>
									  <option value="3">화</option>
									  <option value="4">수</option>
									  <option value="5">목</option>
									  <option value="6">금</option>
									</select>
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
						       <c:set var="startTime" value="${fn:substring(detailLect.lect_start_time, 0, 5)}" />
									<c:set var="endTime" value="${fn:substring(detailLect.lect_end_time, 0, 5)}" />
									<select id="timeSelect" name="lect_start_time">
									  <option value="${startTime}" selected>${startTime} ~ ${endTime}</option>
									</select>
						    </c:otherwise>
						</c:choose>
		                </td>
		            </tr>		       
		                		            
			</table>
					<input type="hidden" id="defaultBuilding" value="${detailLect.class_building}" />
					<input type="hidden" id="defaultRoom" value="${detailLect.class_room}" />
					<input type="hidden" id="defaultDay" value="${detailLect.lect_day}" />
					<input type="hidden" id="defaultStartTime" value="${fn:substring(detailLect.lect_start_time, 0, 5)}" />
			<div class="btn-group">
				<button class="btn-gray" onclick="location.href='${pageContext.request.contextPath}/lectList'">목록</button>

			</div>
		</div>
								<!-- 비밀번호 입력 모달 -->
			<div id="pwModal" class="modal" style="display: none;">
				<div class="modal-content">
					<h3>비밀번호 확인</h3>
					<input type="password" id="pwInput" placeholder="비밀번호 입력" /> <br>
					<button id="pwCheckBtn" class="btn-blue">확인</button>
					<button id="pwCancelBtn" class="btn-red">취소</button>
				</div>
			</div>
					<input type="hidden" id="lect_idx" value="${detailLect.lect_idx}" />
	</div>
<script type="text/javascript">
let lect_idx = $("#lect_idx").val();
console.log("📌 LECT_IDX:", $("#lect_idx").val());
// 비밀번호 체크 모달창
function showPwModal() {
    $('#pwModal').show();
}

// 모달창 닫기
$('#pwCancelBtn').click(function () {
    $('#pwModal').hide();
    $('#pwInput').val('');
});

// 수정된 data 전송 부분
$('#pwCheckBtn').click(function () {
    var inputPw = $('#pwInput').val();
    if (!inputPw) {
        alert("비밀번호를 입력해주세요.");
        return;
    }

    $.ajax({
        url: "/lectureDeletePwCheck", // 비밀번호 확인은 동일
        type: "POST",
        data: { inputPwd: inputPw },
        success: function (response) {
            if (response === "OK") {
                // 비밀번호 인증 성공 → 수정 로직 실행
                submitLectureUpdate(); // 기존에 정의된 수정 AJAX 함수 호출
            } else {
                alert("비밀번호가 틀렸습니다.");
                $('#pwInput').val('');
            }
        },
        error: function () {
            alert("서버 오류가 발생했습니다.");
        }
    });
});

(function () {
    let defaultBuilding = $("#defaultBuilding").val();
    let defaultRoom = $("#defaultRoom").val();
    let defaultDay = $("#defaultDay").val();
    let defaultStartTime = $("#defaultStartTime").val();

    let $building = $("#buildingSelect");
    let $room = $("#roomSelect");
    let $day = $("#weekdaySelect");
    let $time = $("#timeSelect");

    // 건물 선택 시 → 강의실 목록 로드
    $building.on("change", function () {
        const building = $(this).val();
        const $roomSelect = $("#roomSelect");

        if (!building) {
            $roomSelect.empty().append('<option value="">강의실 선택</option>').prop("disabled", true);
            $day.prop("disabled", true).val("");
            $time.prop("disabled", true).empty().append('<option value="">강의시간 선택</option>');
            return;
        }

        $.ajax({
            url: "/classRoomListByBuilding",
            type: "get",
            data: { building: building },
            success: function (rooms) {
                $roomSelect.empty().append('<option value="">강의실 선택</option>').prop("disabled", false);

                if (rooms.length > 0) {
                    for (const room of rooms) {
                        let selected = (room === defaultRoom) ? " selected" : "";
                        $roomSelect.append('<option value="' + room + '"' + selected + '>' + room + '</option>');
                    }

                    if (defaultRoom) {
                        $roomSelect.val(defaultRoom).prop("disabled", false).trigger("change");

                        // 요일도 자동 선택
                        if (defaultDay) {
                            $day.prop("disabled", false).val(defaultDay).trigger("change");
                        }
                    }
                } else {
                    $roomSelect.append('<option disabled>해당 건물에 강의실 없음</option>');
                }
            },
            error: function () {
                alert("강의실 정보를 불러오는 중 오류 발생");
            }
        });
    });

    // 강의실 선택 시 → 요일 활성화
    $room.on("change", function () {
        let room = this.value;

        if (!room) {
            $day.prop("disabled", true).val("");
            $time.prop("disabled", true).empty().append('<option value="">강의시간 선택</option>');
            return;
        }

        $day.prop("disabled", false);
    });

    // 요일 선택 시 → 강의 시작 시간 목록 로딩
    $day.on("change", function () {
        let building = $building.val();
        let room = $room.val();
        let day = $day.val();

        if (!building || !room || !day) return;

        $.ajax({
            url: "/availableStartTimes",
            type: "get",
            data: {
                class_building: building,
                class_room: room,
                lect_day: day
            },
            success: function (list) {
                $time.prop("disabled", false).empty().append('<option value="">강의시간 선택</option>');

                for (let i = 0; i < list.length; i++) {
                    let start = list[i];
                    let end = getEndTime(start, 50);
                    let selected = (start === defaultStartTime) ? " selected" : "";
                    $time.append('<option value="' + start + '"' + selected + '>' + start + ' ~ ' + end + '</option>');
                }

                if (defaultStartTime) {
                    $time.val(defaultStartTime).prop("disabled", false);
                }
            },
            error: function () {
                alert("강의 시간 정보를 불러오지 못했습니다.");
            }
        });
    });

    // 종료 시간 계산
    function getEndTime(startTime, durationMinutes) {
        let [hour, minute] = startTime.split(':').map(Number);
        let date = new Date(0, 0, 0, hour, minute + durationMinutes);
        return String(date.getHours()).padStart(2, '0') + ':' + String(date.getMinutes()).padStart(2, '0');
    }

    // 초기 상태 비활성화
    $room.prop("disabled", true);
    $day.prop("disabled", true);
    $time.prop("disabled", true);

    // 초기값이 있으면 자동 트리거
    if (defaultBuilding) {
        $building.val(defaultBuilding).trigger("change");
    }

})();

function submitLectureUpdate() {
    // ✅ 여러 과목군 선택을 위한 배열 수집
    const subSetNames = [];
    $("select[name='sub_set_name'] option:selected").each(function () {
        subSetNames.push($(this).val());
    });

    const data = {
        lect_idx: $("#lect_idx").val(),
        lect_name: $("input[name='lect_name']").val(),
        lect_id: $("input[name='lect_id']").val(),
        dept_idx: $("select[name='dept_idx']").val(),
        sub_set_names: subSetNames, // ✅ 배열로 전송
        lect_credit: $("select[name='lect_credit']").val(),
        t_idx: $("select[name='t_idx']").val(),
        lect_max: $("#lect_max").val(),
        class_building: $("#buildingSelect").val(),
        class_room: $("#roomSelect").val(),
        lect_day: $("#weekdaySelect").val(),
        lect_start_time: $("#timeSelect").val()
    };

    $.ajax({
        url: "/updateLectureOK",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (res) {
            if (res === "OK") {
                location.href = "/detailLect?lect_idx=" + data.lect_idx;
            } else {
                alert("수정 실패");
            }
        },
        error: function () {
            alert("서버 오류");
        }
    });
}

$(document).ready(function () {
    const $dept = $("#deptSelect");
    const $teacher = $("select[name='t_idx']");
    const $subjectGroup = $("select[name='sub_set_name']");
    const defaultDept = "${detailLect.dept_idx}";
    const defaultProf = "${detailLect.t_idx}";
    const defaultGroup = "${detailLect.sub_set_name}";

    // ✅ 학과 목록 불러오기
    $.ajax({
        url: "/getDeptListForUpdate",
        method: "GET",
        success: function (list) {
            for (const dept of list) {
                $dept.append('<option value="' + dept.dept_idx + '">' + dept.dept_name + '</option>');
            }

            if (defaultDept) {
                $dept.val(defaultDept).trigger("change"); // 초기 학과 → 교수 & 과목군도 자동 트리거됨
            }
        },
        error: function () {
            alert("학과 목록을 불러오는 데 실패했습니다.");
        }
    });

    // ✅ 학과 선택 시 → 교수 & 과목군 모두 로드
    $dept.on("change", function () {
        const deptIdx = $(this).val();

        // 교수 초기화
        $teacher.empty();

        $.ajax({
            url: "/teachersByDept",
            method: "GET",
            data: { dept_idx: deptIdx },
            success: function (profList) {
                if (profList.length > 0) {
                    for (const prof of profList) {
                        const selected = (prof.t_idx == defaultProf) ? " selected" : "";
                        $teacher.append('<option value="' + prof.t_idx + '"' + selected + '>' + prof.name + '</option>');
                    }
                } else {
                    $teacher.append('<option disabled>등록된 교수가 없습니다.</option>');
                }
            },
            error: function () {
                alert("교수 목록 로딩 실패");
            }
        });

        // 과목군 초기화
        $subjectGroup.empty().append('<option value="" disabled selected>과목군 선택</option>');

        $.ajax({
            url: "/subjectGroupsByDept",
            method: "GET",
            data: { dept_idx: deptIdx },
            success: function (groupList) {
                for (const group of groupList) {
                    const selected = (group === defaultGroup) ? " selected" : "";
                    $subjectGroup.append('<option value="' + group + '"' + selected + '>' + group + '</option>');
                }
            },
            error: function () {
                alert("과목군 목록 로딩 실패");
            }
        });
    });
});

</script>

</body>
</html>
