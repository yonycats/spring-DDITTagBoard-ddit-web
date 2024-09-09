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
					<h1 class="fw-light">DDIT 목록</h1>
				</div>
			</div>
		</section>
		<section class="py-1 container">
			<div class="row">
				<div class="col-md-12">
				
					<form class="row g-3 mb-3" id="searchForm">
						<input type="hidden" name="page" id="page">
						
						<div class="row">
							<div class="col-md-6"></div>
							<div class="col-md-6" align="right">
								<div class="row">
									<div class="col-md-4">
										<select class="form-select" name="searchType">
											<option value="title" <c:if test="${searchType eq 'title' }">selected</c:if>>제목</option>
											<option value="writer"<c:if test="${searchType eq 'writer' }">selected</c:if>>작성자</option>
											<option value="both"<c:if test="${searchType eq 'both' }">selected</c:if>>제목+작성자</option>
										</select>
									</div>
									<div class="col-md-5">
										<label for="inputPassword2" class="visually-hidden">키워드</label>
										<input type="text" class="form-control" id="inputPassword2" name="searchWord" placeholder="검색 키워드" value="${searchWord }">
									</div>
									<div class="col-md-3">
										<button type="submit" class="btn btn-dark">검색하기</button>
									</div>
								</div>
							</div>
						</div>
					</form>
					
					<table class="table">
						<thead class="table-dark">
							<tr>
								<th scope="col" width="8%">번호</th>
								<th scope="col">제목</th>
								<th scope="col" width="14%">작성자</th>
								<th scope="col" width="16%">작성일</th>
								<th scope="col" width="8%">조회수</th>
							</tr>
						</thead>
							<tbody>
								<c:set value="${pagingVO.dataList }" var="boardList"/>
								<c:choose>
									<c:when test="${empty boardList }">
										<tr>
											<td colspan="5">조회하신 게시글이 존재하지 않습니다.</td>									
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${boardList }" var="boardVO">
											<tr>
												<td>${boardVO.boNo }</td>
												<td>
													<a href="/ddit/board/detail.do?boNo=${boardVO.boNo }"> 
														${boardVO.boTitle }
													</a>
												</td>
												<td>
													<font class="badge bg-danger" style="font-size: 14px">${boardVO.boWriter }</font>
												</td>
												<td>${boardVO.boDate }</td>
												<td>${boardVO.boHit }</td>
											</tr>
										</c:forEach>
									</c:otherwise>	
								</c:choose>
							</tbody>
					</table>
					<div align="right">
						<button type="button" class="btn btn-primary" id="registerBtn" onclick="location.href='/ddit/board/insert.do'">등록</button>
					</div>
					<div id="pagingArea" style="display: flex; justify-content: center;"> 
						<!-- PaginationInfoVO 안에 getPagingHTML 메서드를  EL표현문의 형식으로 불러오는 것임 -->
						${pagingVO.pagingHTML }
					</div>
				</div>
			</div>
		</section>
	</main>
	<script src="${pageContext.request.contextPath }/resources/assets/dist/js/bootstrap.bundle.min.js"></script>
</body>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
	let searchForm = $('#searchForm');		// 검색 및 페이징 처리 위한 Form
	let pagingArea = $('#pagingArea');		// 페이징 처리 위한 div
	
	// pagingArea 내의 a 태그를 클릭했을 경우의 이벤트 주기
	pagingArea.on('click', 'a', function(event) {
		event.preventDefault(); // a 태그의 기본 기능 멈추기
		let pageNo = $(this).data('page');
		searchForm.find('#page').val(pageNo);
		console.log(pageNo);
		searchForm.submit();
	});
	
});
</script>

</html>