<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/insertLect.css">
<script src="https://kit.fontawesome.com/67e1f0bf0d.js" crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="../../headerNavi.jsp"%>
<div id="wrap">
    <div class="total-container">
        <div class="title-container">
            <div class="highlight"></div>
            <h1>과목군 등록</h1>
        </div>
        <c:if test="${not empty errorMessage}">
            <p id="error">${errorMessage}</p>
        </c:if>
        <form id="subjectSetForm" action="/insertSubjectSet" method="post">
        <div class="btn-group">
            <button class="btn-red" onclick="location.href='/subjectSetList'">취소</button>
            <input type="button" class="btn-blue" value="등록" onclick="submitForm()" />
        </div>
            <table>
                <tr>
                    <th><label for="sub_set_name">*과목군명</label></th>
                    <td><input type="text" name="sub_set_name" id="sub_set_name" required value="${sub_set_name}"/></td>
                </tr>
                <tr>
                    <th><label for="dept_idx">*학과</label></th>
                    <td>
                    	<select name="dept_idx" id="deptSelect" required onchange="saveSelectedDept()">
                            <option value="" disabled ${empty dept_idx ? 'selected' : ''}>학과선택</option>
                            <c:forEach var="k" items="${deptList}">
                                <option value="${k.dept_idx}" ${k.dept_idx == dept_idx ? 'selected' : ''}>${k.dept_name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th><label for="subjectInput">과목구성</label></th>
                    <td>
                        <input type="text" id="subjectInput" name="subjectModal" readonly onclick="openModal('subjectModal')" required>
                        <input type="hidden" id="selectedSubjectsIdx" name="selectedSubjectsIdx" value="${selectedSubjectsIdx}">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 과목 모달 -->
<div id="subjectModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal('subjectModal')">×</span>
        <h2>과목 배정하기</h2>

        <div class="assign-list-wrapper" id="subject-group-list">         
                    <table>
                        <thead class="modal-table">
                            <tr>
                                <th>선택</th>
                                <th>No.</th>
                                <th>학과</th>                               
                                <th>과목명</th>
                            </tr>
                        </thead>
                        <tbody id="all-subjects">
                            <c:forEach var="k" items="${subjectList}" varStatus="v">
                                <tr data-dept="${k.dept_idx}">
                                    <td>
                                        <input type="checkbox" name="subjectCheckbox" value="${k.subject_idx}" 
                                            data-subject-name="${k.subject_name}" onchange="updateSelectedSubjects(this)">
                                    </td>
                                    <td>${v.index + 1}</td>
                                    <td>${k.dept_name}</td>
                                    <td>${k.subject_name}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tbody id="filtered-subjects">
                            <tr><td colspan="5">학과를 선택해주세요.</td></tr>
                        </tbody>
                    </table>              
            <span>*최소1개, 최대 5개 배정 가능</span>
        </div>
        <div class="btn-group">
            <button class="btn-red" onclick="closeModal('subjectModal')">취소</button>
            <button onclick="applySubject()" class="btn-blue">확인</button>
        </div>
    </div>
</div>

<script>
let selectedDeptIdx = null;
let selectedSubjects = {}; // key: subject_idx, value: subject_name

function saveSelectedDept() {
    selectedDeptIdx = document.getElementById("deptSelect").value;
    selectedSubjects = {};
    document.getElementById('subjectInput').value = '';
    document.getElementById('selectedSubjectsIdx').value = '';
}

function openModal(modalId) {
    if (!selectedDeptIdx) {
        alert("학과를 먼저 선택해주세요.");
        return;
    }
    filterSubjectsByDept(selectedDeptIdx);
    document.getElementById(modalId).style.display = "block";
}

function filterSubjectsByDept(deptIdx) {
    const allRows = document.querySelectorAll('#all-subjects tr');  // 모든 과목 항목
    const filteredContainer = document.getElementById('filtered-subjects');
    filteredContainer.innerHTML = '';  // 기존 과목 목록 초기화

    let hasResult = false;
    allRows.forEach(row => {
        if (row.getAttribute('data-dept') === deptIdx) {
            const cloned = row.cloneNode(true);  // 과목을 클론
            const checkbox = cloned.querySelector('input[type="checkbox"]');

            const subjectName = row.querySelectorAll("td")[2]?.textContent.trim();  // 과목명 추출
            checkbox.setAttribute("data-subject-name", subjectName);  // subject_name 설정

            checkbox.onchange = function () {
                updateSelectedSubjects(this);  // 체크박스 변경 시 이벤트 처리
            };

            if (selectedSubjects[checkbox.value]) {
                checkbox.checked = true;  // 이미 선택된 과목은 체크
            }

            filteredContainer.appendChild(cloned);  // 필터링된 과목 목록에 추가
            hasResult = true;
        }
    });

    // 해당 학과의 과목이 없는 경우 표시
    if (!hasResult) {
        filteredContainer.innerHTML = '<tr><td colspan="5">해당 학과의 과목이 없습니다.</td></tr>';
    }
}

function updateSelectedSubjects(checkbox) {
    const subjectIdx = checkbox.value;
    let subjectName = checkbox.getAttribute("data-subject-name");
    if (!subjectName || subjectName.trim() === "") {
        const tds = checkbox.closest("tr").querySelectorAll("td");
        subjectName = tds.length >= 3 ? tds[2].textContent.trim() : "Unnamed";
    }

    if (checkbox.checked) {
        if (Object.keys(selectedSubjects).length >= 5) {
            alert("최대 5개 과목까지만 선택할 수 있습니다.");
            checkbox.checked = false;
            return;
        }
        selectedSubjects[subjectIdx] = subjectName;
    } else {
        delete selectedSubjects[subjectIdx];
    }
}

function applySubject() {
    const selectedNames = Object.values(selectedSubjects);
    const selectedIdxs = Object.keys(selectedSubjects);
    document.getElementById('subjectInput').value = selectedNames.join(', ') || '';
    document.getElementById('selectedSubjectsIdx').value = selectedIdxs.join(',') || '';
    closeModal('subjectModal');
}

function submitForm() {
    document.getElementById('subjectSetForm').submit();
}

function closeModal(modalId) {
    document.getElementById(modalId).style.display = "none";
}

window.onload = function() {
    selectedSubjects = {};
    const selectedIdxs = document.getElementById('selectedSubjectsIdx').value;
    if (selectedIdxs) {
        const subjectIdxArr = selectedIdxs.split(',').map(s => s.trim()).filter(s => s !== "");
        subjectIdxArr.forEach(idx => {
            const checkbox = document.querySelector(`#all-subjects input[value="${idx}"]`);
            let subjectName = "";
            if (checkbox) {
                checkbox.checked = true;
                subjectName = checkbox.getAttribute("data-subject-name");
                if (!subjectName) {
                    const tds = checkbox.closest("tr").querySelectorAll("td");
                    subjectName = tds.length >= 3 ? tds[2].textContent.trim() : "Unnamed";
                }
                selectedSubjects[idx] = subjectName;
            }
        });
        // 과목명 동시 반영
        const selectedNames = Object.values(selectedSubjects);
        document.getElementById('subjects').value = selectedNames.join(', ') || '';
    }
};

window.onclick = function(event) {
    if (event.target.classList.contains("modal")) {
        event.target.style.display = "none";
    }
}
</script>
</body>
</html>
