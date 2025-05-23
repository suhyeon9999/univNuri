<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>과목군 수정</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/insertLect.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<%@ include file="../../headerNavi.jsp" %>
<div id="wrap">
    <div class="total-container">
        <div class="title-container">
            <div class="highlight"></div>
            <h1>과목군 수정</h1>
        </div>
        <c:if test="${not empty errorMessage}">
            <p id="error">${errorMessage}</p>
        </c:if>
        <form id="subjectSetForm" action="${pageContext.request.contextPath}/updateSubjectSet" method="post"  onsubmit="return checkForm();">
            <div class="btn-group">
                <button type="button" class="btn-red" onclick="location.href='${pageContext.request.contextPath}/subjectSetList'">취소</button>
                <input type="submit" class="btn-blue" value="수정"/>
            </div>
            <input type="hidden" name="sub_set_num" value="${detailSubjectSet[0].sub_set_num}">
            <input type="hidden" name="dept_idx" value="${detailSubjectSet[0].dept_idx}">
            <table>
                <tr>
                    <th><label for="sub_set_name">*과목군명</label></th>
                    <td><input type="text" name="sub_set_name" id="sub_set_name" required value="${detailSubjectSet[0].sub_set_name}"/></td>
                </tr>
                <tr>
                    <th>학과</th>
                    <td>${detailSubjectSet[0].dept_name}</td>
                </tr>
                <tr>
                    <th><label for="subjects">과목 구성</label></th>
                    <td>
                        <input type="text" id="subjects" name="subjects" readonly required 
                               value="${detailSubjectSet[0].subjects}" onclick="openModal('subjectModal')" placeholder="과목을 선택하세요">
                        <input type="hidden" id="selectedSubjectsIdx" name="selectedSubjectsIdx" value="${detailSubjectSet[0].subject_idx}">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 전체 과목정보(스크립트 용, 화면에 보이지 않음) -->
<table style="display:none;">
    <tbody id="all-subjects">
        <c:forEach var="k" items="${subjectList}" varStatus="v">
            <tr data-dept="${k.dept_idx}">
                <td>
                    <input type="checkbox" value="${k.subject_idx}" data-subject-name="${k.subject_name}">
                </td>
                <td>${v.index + 1}</td>
                <td>${k.dept_name}</td>
                <td>${k.subject_name}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<!-- 과목 선택 모달 -->
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
                <tbody id="filtered-subjects">
                    <tr><td colspan="5">과목을 불러오는 중입니다...</td></tr>
                </tbody>
            </table>
            <span>*최소 1개, 최대 5개 배정 가능</span>
        </div>
        <div class="btn-group">
            <button class="btn-red" type="button" onclick="closeModal('subjectModal')">취소</button>
            <button type="button" onclick="applySubject()" class="btn-blue" id="applyBtn">확인</button>
        </div>
    </div>
</div>

<script>
let selectedDeptIdx = "${detailSubjectSet[0].dept_idx}";
let selectedSubjects = {}; // { subject_idx: subject_name }

document.getElementById('subjects').onclick = function() { openModal('subjectModal'); }

function openModal(modalId) {
    if (!selectedDeptIdx) {
        alert("학과 정보가 없습니다.");
        return;
    }
    document.getElementById(modalId).style.display = "block";
    filterSubjectsByDept(selectedDeptIdx);
}

function filterSubjectsByDept(deptIdx) {
    const allRows = document.querySelectorAll('#all-subjects tr');
    const filteredContainer = document.getElementById('filtered-subjects');
    filteredContainer.innerHTML = '';

    let hasResult = false;
    allRows.forEach(row => {
        if (row.getAttribute('data-dept') === deptIdx) {
            const cloned = row.cloneNode(true);
            const checkbox = cloned.querySelector('input[type="checkbox"]');
            const subjectIdx = checkbox.value;
            const subjectName = checkbox.getAttribute('data-subject-name') || 
                               row.querySelector('td:nth-child(4)')?.textContent?.trim() || 'Unnamed';

            // 체크박스 상태 설정
            checkbox.checked = !!selectedSubjects[subjectIdx];
            checkbox.onchange = function() { updateSelectedSubjects(this); };

            filteredContainer.appendChild(cloned);
            hasResult = true;
        }
    });

    if (!hasResult) {
        filteredContainer.innerHTML = '<tr><td colspan="5">해당 학과의 과목이 없습니다.</td></tr>';
    }
    updateCheckboxStates();
}

function updateSelectedSubjects(checkbox) {
    const subjectIdx = checkbox.value;
    const subjectName = checkbox.getAttribute('data-subject-name') || 
                       checkbox.closest('tr').querySelector('td:nth-child(4)')?.textContent?.trim() || 'Unnamed';

    if (checkbox.checked) {
        if (Object.keys(selectedSubjects).length >= 5) {
            alert('최대 5개 과목까지만 선택할 수 있습니다.');
            checkbox.checked = false;
            return;
        }
        selectedSubjects[subjectIdx] = subjectName;
    } else {
        delete selectedSubjects[subjectIdx];
    }
    updateCheckboxStates();
}

function updateCheckboxStates() {
    const modalCheckboxes = document.querySelectorAll('#filtered-subjects input[type="checkbox"]');
    if (Object.keys(selectedSubjects).length >= 5) {
        modalCheckboxes.forEach(cb => {
            if (!cb.checked) cb.disabled = true;
        });
    } else {
        modalCheckboxes.forEach(cb => cb.disabled = false);
    }
}

function applySubject() {
    const selectedNames = Object.values(selectedSubjects);
    const selectedIdxs = Object.keys(selectedSubjects);
    document.getElementById('subjects').value = selectedNames.join(', ') || '';
    document.getElementById('selectedSubjectsIdx').value = selectedIdxs.join(',') || '';
    closeModal('subjectModal');
}

function closeModal(modalId) {
    document.getElementById(modalId).style.display = "none";
}

window.onload = function() {
    selectedSubjects = {};
    const selectedIdxs = "${detailSubjectSet[0].subject_idx}";
    const selectedNames = "${detailSubjectSet[0].subjects}";

    if (selectedIdxs && selectedNames) {
        const idxArray = selectedIdxs.split(',').map(idx => idx.trim()).filter(idx => idx !== '');
        const nameArray = selectedNames.split(',').map(name => name.trim()).filter(name => name !== '');
        
        idxArray.forEach((idx, i) => {
            if (nameArray[i]) {
                selectedSubjects[idx] = nameArray[i];
            }
        });
    }

    // input 초기화
    document.getElementById('subjects').value = Object.values(selectedSubjects).join(', ') || '';
    document.getElementById('selectedSubjectsIdx').value = Object.keys(selectedSubjects).join(',') || '';

    // 모달 내 체크박스 초기화
    const allCheckboxes = document.querySelectorAll('#all-subjects input[type="checkbox"]');
    allCheckboxes.forEach(cb => {
        cb.checked = !!selectedSubjects[cb.value];
    });
};

window.onclick = function(event) {
    if (event.target.classList.contains("modal")) {
        event.target.style.display = "none";
    }
};


function submitForm() {
	 if(checkForm()){
	        document.getElementById('subjectSetForm').submit();
	    }
}
function checkForm() {
	  var sub_set_name = document.getElementById("sub_set_name").value;
	  if (sub_set_name.trim() === "") {
	    alert("과목군이름을 입력해주세요.");
	    return false;
	  }
	  return true; // 모든 검사를 통과하면 폼 제출
	}
</script>
</body>
</html>