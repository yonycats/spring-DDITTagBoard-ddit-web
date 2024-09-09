<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
<meta name="generator" content="Hugo 0.108.0">
<title>DDIT BOARD LIST</title>
<link rel="canonical" href="https://getbootstrap.com/docs/5.3/examples/album/">
<link href="${pageContext.request.contextPath }/resources/assets/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}

.b-example-divider {
	height: 3rem;
	background-color: rgba(0, 0, 0, .1);
	border: solid rgba(0, 0, 0, .15);
	border-width: 1px 0;
	box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em
		rgba(0, 0, 0, .15);
}

.b-example-vr {
	flex-shrink: 0;
	width: 1.5rem;
	height: 100vh;
}

.bi {
	vertical-align: -.125em;
	fill: currentColor;
}

.nav-scroller {
	position: relative;
	z-index: 2;
	height: 2.75rem;
	overflow-y: hidden;
}

.nav-scroller .nav {
	display: flex;
	flex-wrap: nowrap;
	padding-bottom: 1rem;
	margin-top: -1px;
	overflow-x: auto;
	text-align: center;
	white-space: nowrap;
	-webkit-overflow-scrolling: touch;
}
</style>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<body>

<c:if test="${not empty message }">
	<script type="text/javascript">
		alert("${message}");	
	</script>
	<c:remove var="message" scope="request"/>
</c:if>

	<header>
		<div class="collapse bg-dark" id="navbarHeader">
		</div>
		<div class="navbar navbar-dark bg-dark shadow-sm">
			<div class="container"></div>
		</div>
	</header>
	<main>
		<section class="py-1 text-center container">
			<div class="row py-lg-4">
				<div class="col-lg-6 col-md-8 mx-auto">
					<h1 class="fw-light">DDIT 상세보기</h1>
				</div>
			</div>
		</section>
		<section class="py-1 container">
			<div class="row">
				<div class="col-md-12">
					<div class="card">
						<div class="card-header">${board.boTitle }</div>
						<div class="card-body">${board.boWriter } | ${board.boDate } | ${board.boHit }</div>
						<div class="card-body">${board.boContent }</div>
						<div class="card-body">
							<c:choose>
								<c:when test="${board.tagList == null }">
								</c:when>
								<c:otherwise>
									<c:forEach items="${board.tagList }" var="tags">
										<span class="badge bg-success">#${tags.tag }</span>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="card-footer">
													
							<form action="/ddit/board/delete.do" method="post" id="deleteForm">
								<input type="hidden" name="boNo" value="${board.boNo }">
								<button type="button" class="btn btn-warning" id="modifyBtn">수정</button>
								<button type="button" class="btn btn-danger" id="delBtn">삭제</button>
								<button type="button" class="btn btn-info" id="listBtn" onclick="location.href='/ddit/board/list.do'">목록</button>
							</form>
						
						</div>
					</div>
				</div>
			</div>
		</section>
	</main>
	<script src="${pageContext.request.contextPath }/resources/assets/dist/js/bootstrap.bundle.min.js"></script>
</body>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script type="text/javascript">
window.onload = function() {
	
let modifyBtn = document.querySelector('#modifyBtn');
let delBtn = document.querySelector('#delBtn');
let deleteForm = document.querySelector('#deleteForm');

	modifyBtn.addEventListener('click', function() {
		console.log("mmmmmmm");
		
		location.href="/ddit/board/modify.do?boNo=${board.boNo }";
	});
	
	delBtn.addEventListener('click', function() {
		console.log("ddddddd");
		
		if (confirm("정말로 삭제하시겠습니까?")) {
			deleteForm.submit();
		}
	});


};
</script>

</html>