<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../headerNavi.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/student/myPageInfoUpdate.css">
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<div id="wrap">
		<div class="total-container">
			<div class="title-container">
				<div class="highlight"></div>
				<h1>개인정보 관리</h1>
				<button onclick="sMyPageInfoUpdateOK()" id="btn-submit">저장</button>
				<button onclick="location.href='/sMyPageInfoDetail'" id="btn-cancel">취소</button>
				<input type="hidden" name="" value="" />
			</div>
			<div class="main-container">
				<div id="profile">
					<c:if test="${not empty myPageInfo.f_name}"><!-- 사진 올린 적 1번이라도 있는 사람 --> 
						<img
							src="${pageContext.request.contextPath}/resources/upload/${sessionScope.uvo.user_id}/${myPageInfo.f_name}"
							alt="프로필 사진" />
							<!-- [경우의 수 1] 사진 2번 이상 올린적 있는 사람 (f_old_name이 NOT NULL) -->
							<c:if test="${not empty myPageInfo.f_old_name }">
								<input type="hidden" id="f_old_name" name="f_old_name" value="${myPageInfo.f_old_name}">
								<input type="hidden" id="f_curr_name" name="f_curr_name" value="${myPageInfo.f_name}">
							</c:if>
							<!-- [경우의 수 2] 사진 딱 1번 올린 사람 (f_old_name이 NULL) -->
							<c:if test="${empty myPageInfo.f_old_name }">
								<input type="hidden" id="f_old_name" name="f_old_name" value="null_data">
								<input type="hidden" id="f_curr_name" name="f_curr_name" value="${myPageInfo.f_name}">
							</c:if>
					</c:if>
					<c:if test="${empty myPageInfo.f_name}"> <!-- [경우의 수 3] 아예 사진 올린 적 없는 사람 -->
						<img
							src="${pageContext.request.contextPath}/resources/upload/default_profile.png"
							alt="기본 프로필" />
							<input type="hidden" id="f_old_name" name="f_old_name" value="null_data">
							<input type="hidden" id="f_curr_name" name="f_curr_name" value="null_data">
					</c:if>
					<button id="btn-picture">사진등록</button>
					<!-- 숨겨진 파일 입력 -->
					<input type="file" id="change-pic-file" style="display: none;" />

				</div>
				<div id="content">
					<div id="content-top">
						<p id="num">${myPageInfo.user_id}</p>
						<p id="dept">${myPageInfo.dept_name}</p>
						<p id="name">${myPageInfo.name}</p>
					</div>
					<div id="student-status">
						<c:choose>
							<c:when test="${myPageInfo.status == 1}">재학
							</c:when>
							<c:when test="${myPageInfo.status == 2}">휴학
							</c:when>
							<c:when test="${myPageInfo.status == 3}">졸업
							</c:when>
						</c:choose>
					</div>

					<div class="input-form">
						<label for="phone">휴대폰</label> <input type="text"
							value="${myPageInfo.phone }" id="phone" required>
					</div>
					<div class="input-form">
						<label for="email">E-mail</label> <input type="text"
							value="${myPageInfo.email }" id="email" required>
					</div>
					<div class="input-form">
						<label for="birth">생년월일</label> <input type="date"
							value="${myPageInfo.birth }" id="birth" required>
					</div>

					<button id="btn-password-change" onclick="showPwModal()">비밀번호
						변경하기</button>

					<!-- 비밀번호 입력 모달 -->
					<div id="pwModal" class="modal" style="display: none;">
						<div class="modal-content">
							<h3>비밀번호 확인</h3>
							<div id="current-pw-msg" class="pw-msg">1. 현재 비밀번호를 입력해주세요</div>
							<input type="password" id="current-password"
								placeholder="현재 비밀번호"><br>
							<div class="modal-buttons">
								<button id="current-pw-chk" onclick="currentPwChk()">확인</button>
							</div>
							<div id="current-pw-chk-msg" class="pw-msg"></div>
							<div id="new-pw-msg" class="pw-msg">2. 새 비밀번호를 입력해주세요</div>
							<input type="password" id="new-password"
								placeholder="8자 이상 10자 이하. 영문자+숫자." disabled><br> <input
								type="password" id="confirm-password" placeholder="새 비밀번호 확인"
								disabled><br>
							<div id="new-pw-chk-msg" class="pw-msg"></div>
							<br> <br>
							<div id="change-pw-ok-msg" class="pw-msg">*아래 버튼을 눌러야 변경
								완료됩니다*</div>
							<div class="modal-buttons">
								<button id="btn-change-pw" onclick="changePwOk()">비밀번호
									변경 저장</button>
								<button id="pwCancelBtn" class="btn-red">취소</button>
							</div>
						</div>
					</div>

				</div>
			</div>
			<div class="main-container2">
				<hr>
				<div id="content">
					<div class="input-form address-form">
						<label for="address"> 주소 : </label>
						<div class="address-box">
							<input type="text" id="addr1" name="addr1"
								value="${myPageInfo.s_address}" onclick="execDaumPostcode()"
								required readonly> <input type="text" id="addr2"
								name="addr2" value="${myPageInfo.s_address2}">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		// 0. 전역 변수
		let selectedFileSize = 0;
		let selectedFileType = null;

		// 1. 비밀번호 체크 모달창
		function showPwModal() {
			$('#pwModal').show();
		}
		// 2. 모달창 닫기
		$('#pwCancelBtn').click(function() {
			$('#pwModal').hide();
			$('#current-password').val('');
			$('#new-password').val('');
			$('#confirm-password').val('');
		});
		//3. 현재 비밀번호 일치 확인
		function currentPwChk() {
			var inputPw = $('#current-password').val();
			$.ajax({
				url : "/checkSpassword",
				type : "POST",
				data : {
					inputPwd : inputPw
				},
				success : function(response) {
					console.log('서버 응답: ' + response); // 서버 응답 출력
					if (response === "OK") {
						// 비밀번호가 맞으면 데이터 전송
						console.log('서버 응답: ' + response); // 서버 응답 출력
						$('#current-pw-chk-msg').text('비밀번호 확인 완료').css(
								'color', 'blue');
						$('#current-password').prop('disabled', true);

						// 새 비밀번호 입력란 활성화
						$('#new-password').prop('disabled', false);
						$('#confirm-password').prop('disabled', false);
					} else {
						$('#current-pw-chk-msg').text('비밀번호가 틀렸습니다.').css(
								'color', 'red');
						$('#current-password').val('');
					}
				},
				error : function() {
					alert("서버 오류가 발생했습니다.");
				}
			});
		}

		// 4. 새 비밀번호 상동 확인
		// 	4-1. 입력값 저장, 이벤트 메소드 호출
		const newPwInput = document.getElementById("new-password");
		const confirmPwInput = document.getElementById("confirm-password");
		const msgBox = document.getElementById("new-pw-chk-msg");

		newPwInput.addEventListener("input", checkPasswordMatch);
		confirmPwInput.addEventListener("input", checkPasswordMatch);

		// 	4-2. 상동 확인 메소드
		function checkPasswordMatch() {
			const newPw = newPwInput.value;
			const confirmPw = confirmPwInput.value;

			if (newPw === "" || confirmPw === "") {
				msgBox.textContent = ""; // 입력이 비어 있으면 메시지 없음
				return;
			}

			if (newPw === confirmPw) {
				msgBox.textContent = "비밀번호가 일치합니다.";
				msgBox.style.color = "#404cac";

			} else {
				msgBox.textContent = "비밀번호가 일치하지 않습니다.";
				msgBox.style.color = "#fc522f";
			}
		}

		// 5. 비밀번호 저장
		function changePwOk() {
			const currentPw = $('#current-password').val();
			const newPw = $('#new-password').val();
			const confirmPw = $('#confirm-password').val();

			// 5-1. 필수값 비어있는지 확인
			if (!currentPw || !newPw || !confirmPw) {
				alert("모든 항목을 입력해주세요.");
				return;
			}

			// 5-2. 현재 비밀번호 인증되지 않았는지 확인 (readonly 또는 disabled 여부 체크)
			if (!$('#current-password').prop('disabled')) {
				alert("현재 비밀번호 인증을 먼저 완료해주세요.");
				return;
			}

			// 5-3. 새 비밀번호와 확인 비밀번호 불일치
			if (newPw !== confirmPw) {
				alert("새 비밀번호가 일치하지 않습니다.");
				return;
			}

			// 5-4. 현재 비밀번호와 새 비밀번호가 같을 경우
			if (currentPw === newPw) {
				alert("현재 비밀번호와 새 비밀번호가 같습니다.");
				return;
			}

			// 5-5. AJAX로 변경 요청
			$.ajax({
				url : "/updateSpassword",
				type : "POST",
				data : {
					currentPassword : currentPw, // 인증된 현재 비밀번호
					newPassword : newPw
				},
				success : function(response) {
					console.log(response); // 확인용 로그
					if (response === "OK") {
						alert("비밀번호가 성공적으로 변경되었습니다.");
						$('#pwModal').hide();
						resetPasswordFields();
					} else {
						alert("비밀번호 변경에 실패했습니다.");
					}
				},
				error : function() {
					alert("비밀번호 변경 요청 중 오류가 발생했습니다.");
				}
			});
		}

		// 비밀번호 변경 후, 입력란 초기화
		function resetPasswordFields() {
			$('#current-password').val(''); // 현재 비밀번호 입력란 초기화
			$('#new-password').val(''); // 새 비밀번호 입력란 초기화
			$('#confirm-password').val(''); // 새 비밀번호 확인란 초기화
			$('#current-password').prop('disabled', false); // 현재 비밀번호 입력란 활성화
			$('#current-pw-chk-msg').text(''); // 메시지 박스를 비웁니다.
			$('#new-pw-chk-msg').text(''); // 메시지 박스를 비웁니다.
		}
		// 6. 주소 찾기
		function execDaumPostcode() {
			new daum.Postcode({
				oncomplete : function(data) {
					// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
					$("#addr1").val(data.address);
				}
			}).open();
		}

		// 7. 저장
		function sMyPageInfoUpdateOK() {
			// 각 입력 값 가져오기
			const phone = $('#phone').val();
			const email = $('#email').val();
			const birth = $('#birth').val();
			const address1 = $('#addr1').val();
			const address2 = $('#addr2').val();
			const f_size = selectedFileSize;
			const f_type_string = selectedFileType; /* 안 보낼 데이터임 */
			const f_type = 
			    f_type_string === "image/jpg" ? 0
			    : f_type_string === "image/jpeg" ? 1
			    : f_type_string === "image/png" ? 2 
			    : -1;
		    const fileInput = document.getElementById('change-pic-file'); /* 안 보낼 데이터임 */
		    const file = fileInput.files[0];

			// 입력 값이 비어있는지 체크 (필요시)
			if (!phone || !email || !birth || !address1 || !address2) {
				alert("모든 항목을 입력해 주세요.");
				return; // 입력값이 없으면 종료
			}

		    const formData = new FormData();
		    formData.append("phone", phone);
		    formData.append("email", email);
		    formData.append("birth", birth);
		    formData.append("address1", address1);
		    formData.append("address2", address2);
		    formData.append("f_old_name", $('#f_old_name').val()); 
		    formData.append("f_curr_name", $('#f_curr_name').val()); 

		    if (file) {
		        formData.append("file", file);
			    formData.append("f_size", f_size);
			    formData.append("f_type", f_type);
		    }

		    console.log("보내는 f_size:", f_size);
		    console.log("보내는 f_type:", f_type);
			// AJAX 요청
		    $.ajax({
		        url: "/sMyPageInfoUpdateOK",
		        type: "POST",
		        data: formData,
		        processData: false,
		        contentType: false,
		        success: function(response) {
		            console.log(response);
		            if (response === "OK") {
		                alert("회원 정보가 성공적으로 업데이트되었습니다.");
		                location.href = "/sMyPageInfoDetail";
		            } else {
		                alert("정보 업데이트에 실패했습니다.");
		            }
		        },
		        error: function() {
		            alert("정보 업데이트 중 오류가 발생했습니다.");
		        }
		    });
		}

		// 8. 사진 등록 버튼 → 숨겨진 파일 선택창 열기
		document.getElementById('btn-picture').addEventListener('click',
				function() {
					document.getElementById('change-pic-file').click();
				});

		// 9. 선택된 파일 확인 (선택적으로)
		// [change 이벤트]
		// 사용자가 파일을 선택하거나 입력값을 바꿨을 때 발생하는 이벤트
		// 위에서, click() 했으니까 파일 선택 또는 입력값 바꾼 꼴
		document
				.getElementById('change-pic-file')
				.addEventListener(
						'change',
						function(event) {
							const file = event.target.files[0];
							if (!file)
								return;

							// [파일 형식 검사]
							// /(\.jpg|\.jpeg|\.png)$/i ===> 정규표현식(Regular Expression)
							/*
								\.jpg → .jpg로 끝나는지
								\.jpeg → .jpeg로 끝나는지
								\.png → .png로 끝나는지
								$ → 문자열 끝
								i → 대소문자 구분 안 함 (예: .JPG도 허용)
							
							   [exec() 함수] 정규표현식에서 문자열을 검사해서:
													매칭되면 매칭 결과 배열 반환
													매칭 안 되면 null 반환
							 */
							const allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;
							if (!allowedExtensions.exec(file.name)) {
								alert("jpg 또는 png 파일만 업로드할 수 있습니다.");
								event.target.value = "";
								return;
							}

							// FileReader는 브라우저 메모리에서 파일을 읽어오는 JS 내장 객체
							const reader = new FileReader();
							// FileReader가 파일을 다 읽었을 때 실행할 콜백 함수
							reader.onload = function(e) {
								document.querySelector('#profile img').src = e.target.result;
							};
							reader.readAsDataURL(file);

							// 파일 정보 따로 저장
							selectedFileSize = file.size;
							selectedFileType = file.type;
						});
	</script>
</body>
</html>