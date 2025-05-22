<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"	href="${pageContext.request.contextPath}/resources/css/index.css">
<meta charset="UTF-8">
<title>누리대학교 학습관리시스템</title>
<link rel="stylesheet"	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
	<div class="wrap">
	<!-- 헤더 영역 -->
	<header>
		<div class="header-inner">
			<h1>
				<a href="/index">NURI UNIVERSITY</a>
			</h1>
			<nav id="gnav">
				<ul class="gnav-list">
					<li><a href="/noticeList">NOTICE</a></li>
					<li><a href="/faqList">FAQ</a></li>
					<li><a href="https://www.kmooc.kr/" target="_blank">K-MOOC</a></li>
				</ul>
			</nav>
			<div id="logInOut">
				<ul class="logInOut-list">
					<c:choose>
						<c:when test="${empty sessionScope.loginchk}">
					<li style="align-content: center;"><a href="/login">LOGIN</a></li> 
					</c:when>
					<c:otherwise>
					<li style="font-size: 27.5px; margin-top: 2.1px;"><a href="/dashboard"><i class="fa-solid fa-school"></i><span style="font-size: 20px; margin: 0px 10px;">등교하기</span></a></li>
					<li style="align-content: center;"><a href="/logout">LOGOUT</a></li>
					</c:otherwise>
					</c:choose>
					<!-- 아래가 테스트용!!!! -->
<!-- 					<li><a href="/loginTest">LOGIN_TEST</a></li> -->
				</ul>
			</div>
		</div>
	</header>
	<!-- 이미지 슬라이더 -->
	<section class="slider">
		<div class="slides">
			<img
				src="${pageContext.request.contextPath}/resources/images/main1.jpg"
				class="slide" alt="슬라이드 이미지 1"> <img
				src="${pageContext.request.contextPath}/resources/images/main2.jpg"
				class="slide" alt="슬라이드 이미지 2"> <img
				src="${pageContext.request.contextPath}/resources/images/main3.jpg"
				class="slide" alt="슬라이드 이미지 3"> <img
				src="${pageContext.request.contextPath}/resources/images/main4.jpg"
				class="slide" alt="슬라이드 이미지 4"> <img
				src="${pageContext.request.contextPath}/resources/images/main5.jpg"
				class="slide" alt="슬라이드 이미지 5">
		</div>
		<div class="dots">
			<span class="dot"></span> <span class="dot"></span> <span class="dot"></span>
			<span class="dot"></span> <span class="dot"></span>
		</div>
	</section>
	<section id="mid-section">
		<div id="mid-btns">
			<button class="mid-btn" 
				onclick="window.open('/manual?f_name=누리대학교_학생_메뉴얼.pdf', '_blank')">
				학생 메뉴얼
			</button>
			
			<button class="mid-btn" 
				onclick="window.open('/manual?f_name=누리대학교_교수_메뉴얼.pdf', '_blank')">
				교수 메뉴얼
			</button>
			
		</div>
	</section>

	<!-- 메인 콘텐츠 영역 -->
	<main>
		<!-- 학교 소개 섹션 -->
		<section id="school-intro">
			<div class="intro-item">
				<span class="icon">🎓</span>
				<p>
					<strong>학문과 실무의 조화를 이루는 교육과정</strong><br> 누리대학교는 이론과 실무를 균형 있게
					갖춘 교육과정을 통해 학생들이 전문성과 창의성을 동시에 키울 수 있도록 지원합니다. <br> 각 학과는 산업
					현장의 요구를 반영한 커리큘럼을 운영하며, 캡스톤 디자인, 인턴십, 산학협력 프로젝트 등 다양한 실습 기회를 제공합니다.
					<br>이를 통해 학생들은 졸업 후 바로 현장에 투입될 수 있는 실무 능력을 갖추게 됩니다.
				</p>
			</div>
			<div class="intro-item">
				<span class="icon">🌐</span>
				<p>
					<strong>글로벌 역량 강화를 위한 국제화 프로그램</strong><br> 누리대학교는 글로벌 시대에 발맞춰
					다양한 국제화 프로그램을 운영하고 있습니다.<br> 해외 자매대학과의 교환학생 프로그램, 글로벌 인턴십, 해외
					봉사활동 등은 학생들에게 국제적인 경험을 제공하며, 글로벌 마인드를 함양하는 데 기여합니다.
				</p>
			</div>
			<div class="intro-item">
				<span class="icon">🏢</span>
				<p>
					<strong>최첨단 시설과 쾌적한 캠퍼스 환경</strong><br> 누리대학교는 학생들의 학습과 연구를
					지원하기 위해 최첨단 시설을 갖추고 있습니다.<br> 최신 장비를 갖춘 실험실, 스마트 강의실, 디지털 도서관
					등은 학생들이 효율적으로 학습할 수 있는 환경을 제공합니다. <br> 또한, 쾌적한 기숙사, 체육 시설, 문화
					공간 등은 학생들의 복지와 여가를 지원하며, 풍요로운 대학 생활을 가능하게 합니다.

				</p>
			</div>
			<div class="intro-item">
				<span class="icon">🤝</span>
				<p>
					<strong>산학협력과 취업 지원을 통한 실질적인 진로 지원</strong><br> 누리대학교는 산업체와의
					긴밀한 협력을 통해 학생들에게 다양한 현장 경험을 제공하고 있습니다.<br> 산학협력 프로젝트, 현장 실습,
					기업체 특강 등은 학생들이 실제 산업 현장을 이해하고, 실무 능력을 향상시키는 데 큰 도움이 됩니다.
				</p>
			</div>
			<div class="intro-item">
				<span class="icon">💖</span>
				<p>
					<strong>학생 중심의 복지와 지원 시스템</strong><br> 누리대학교는 학생들의 복지를 최우선으로
					생각하며, 다양한 지원 시스템을 운영하고 있습니다.<br> 장학금 제도, 상담 프로그램, 건강 관리 서비스 등은
					학생들이 학업에 전념할 수 있는 환경을 조성합니다.
				</p>
			</div>
			<div class="intro-item">
				<span class="icon">🌱</span>
				<p>
					<strong>지역사회와의 상생을 위한 노력</strong><br> 누리대학교는 지역사회와의 상생을 중요하게
					생각하며, 다양한 사회공헌 활동을 전개하고 있습니다. <br> 지역 주민을 위한 평생교육 프로그램, 지역
					기업과의 협력 프로젝트, 봉사활동 등은 지역사회 발전에 기여하고 있습니다.
				</p>
			</div>
		</section>
	</main>
	<footer>
		<div class="footer-inner">
			<p>© 2025 NURI UNIVERSITY. All rights reserved.</p>
			<p>문의: 02-1234-5678 | 주소: 서울시 누리구 누리로 123</p>
		</div>
	</footer>
</div>
	<!-- 슬라이드 JS -->
	<script>
    let current = 0;
    const slides = document.querySelectorAll('.slides img');
    const dots = document.querySelectorAll('.dot');

    function showSlide(n) {
      slides.forEach((s, i) => s.classList.toggle('active', i === n));
      dots.forEach((d, i) => d.classList.toggle('active', i === n));
      current = n;
    }

    function nextSlide() {
      let next = (current + 1) % slides.length;
      showSlide(next);
    }

    setInterval(nextSlide, 3000);

    dots.forEach((dot, i) => {
      dot.addEventListener('click', () => showSlide(i));
    });

    showSlide(0);
  </script>
</body>
</html>