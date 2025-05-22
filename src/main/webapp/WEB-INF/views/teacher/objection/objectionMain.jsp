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
table {
	border-collapse: collapse;
	width: 100%;
	background-color: white;
	text-align: center;
	table-layout: fixed; /* 고정된 셀 너비 사용 */
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

#mid-container {
	display: flex;
	align-items: center;
}

.btn-orange {
	margin: 10px;
}

#content-container p {
	margin-top: 20px;
}


th:nth-child(1), td:nth-child(1),th:nth-child(2), td:nth-child(2) { width: 10%; }
th:nth-child(3), td:nth-child(3),th:nth-child(4), td:nth-child(4), th:nth-child(5), td:nth-child(5), th:nth-child(6), td:nth-child(6) { width: 20%; }
</style>
</head>
<body>
	<div id="wrap">
		<div class="title-container">
			<div class="highlight"></div>
			<h1>이의제기 관리</h1>
		</div>
		<div id="mid-container">
			<select id="objectionStatus" name="objectionStatus">

				<option value="0">대기</option>
				<option value="1">승인</option>
				<option value="2">반려</option>
			</select>

			<button type="button" class="btn-orange"
				onclick="search_objection_list(event)">
				<i class="fa-solid fa-magnifying-glass"></i>
			</button>
		</div>
		<div id="content-container">
			<c:choose>
				<c:when test="${empty objectionList}">
					<p>조회된 결과가 없습니다.</p>
				</c:when>
				<c:otherwise>
					<table id="table">
						<thead>
							<tr>

								<th></th>
								<th>이름</th>
								<th>학번</th>
								<th>강의</th>
								<th>신청일</th>
								<th>상태</th>

							</tr>

						</thead>
						<tbody>
							<c:forEach var="k" items="${objectionList}" varStatus="status">
								<tr>
									<td>${status.index + 1}</td>

									<td><c:choose>
											<c:when test="${k.objection_status == 0}">
												<a
													href="/objection-main-detail?objection_idx=${k.objection_idx}">${k.name}</a>
											</c:when>
											<c:when test="${k.objection_status == 1}">${k.name}</c:when>
											<c:otherwise>반려</c:otherwise>
										</c:choose></td>
									<td>${k.user_id}</td>
									<td>${k.lect_name}</td>
									<td>${k.formattedDate}</td>

									<td><c:choose>
											<c:when test="${k.objection_status == 0}">대기</c:when>
											<c:when test="${k.objection_status == 1}">승인</c:when>
											<c:otherwise>반려</c:otherwise>
										</c:choose></td>
								</tr>


							</c:forEach>
						</tbody>

					</table>

				</c:otherwise>
			</c:choose>

		</div>
	</div>
	<script type="text/javascript">
function search_objection_list(event) {
	event.preventDefault(); 
	const objectionStatus = document.getElementById("objectionStatus").value;

	fetch('/get-wait-permit-reject-objection', {
	    method: 'POST',
	    headers: { 'Content-Type': 'application/json' },
	    body: JSON.stringify({ objectionStatus })
	})
	.then(response => response.json())
	.then(data => {

	    console.log("받은 데이터:", data);
	    console.log("타입:", typeof data); 
	    console.log("isArray:", Array.isArray(data));

	    const listContainer = document.getElementById("content-container");
	    listContainer.innerHTML = "";

	    if (!data || data.length === 0) {
	        const emptyMsg = document.createElement("p");
	        emptyMsg.textContent = "조회된 결과가 없습니다.";
	        listContainer.appendChild(emptyMsg);
	        return;
	    }

	    const statusMap = { 0: "대기", 1: "승인", 2: "반려" };

	    const table = document.createElement("table");
	    table.id = "table";
	    let tbodyHtml = "";

	    data.forEach(function(k, index) {
	        let nameCellContent = "";
	        if (k.objection_status === 0) {
	            nameCellContent = "<a href=\"/objection-main-detail?objection_idx=" + k.objection_idx + "\">" + k.name + "</a>";
	        } else {
	            nameCellContent = k.name;
	        }

	        tbodyHtml += 
	            "<tr>" +
	                "<td>" + (index + 1) + "</td>" +
	                "<td>" + nameCellContent + "</td>" +
	                "<td>" + k.user_id + "</td>" +
	                "<td>" + k.lect_name + "</td>" +
	                "<td>" + k.formattedDate  + "</td>" +
	                "<td>" + statusMap[k.objection_status] + "</td>" +
	            "</tr>";
	    });

	    table.innerHTML = 
	        "<thead>" +
	            "<tr>" +
	                "<th></th>" +
	                "<th>이름</th>" +
	                "<th>학번</th>" +
	                "<th>강의</th>" +
	                "<th>신청일</th>" +
	                "<th>상태</th>" +
	            "</tr>" +
	        "</thead>" +
	        "<tbody>" +
	            tbodyHtml +
	        "</tbody>";

	    listContainer.appendChild(table);
	});

}

</script>
</body>



</html>


