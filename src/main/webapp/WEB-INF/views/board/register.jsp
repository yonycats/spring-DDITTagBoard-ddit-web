<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<header>
		<div class="collapse bg-dark" id="navbarHeader">
		</div>
		<div class="navbar navbar-dark bg-dark shadow-sm">
			<div class="container"></div>
		</div>
	</header>
	<main>
		<section class="py-1 text-center container" style="margin-top: 50px;"> 
			<div class="row py-lg-4">
				<div class="col-lg-6 col-md-8 mx-auto">
					<h1 class="fw-light">DDIT 회원가입</h1>
				</div>
			</div>
		</section>
		<section class="py-1 text-center container mb-5">
		
			<div class="register-box mx-auto" style="width: 700px;">
				<div class="card card-outline card-danger mt-4 mb-5" id="card-signup">  
					<div class="card-header text-center pt-5 pb-5"> 
						<a href="" class="h1"><b>회원가입</b></a> 
					</div>
					<div class="card-body">
			
						<form action="/ddit/board/signup.do" method="post" id="signupForm">
							<div class="input-group mb-3">
								<label for="inputDescription">프로필 정보</label>
							</div>
							<div class="input-group mb-3">
								<input type="text" class="form-control" id="memId" name="memId" placeholder="아이디를 입력해주세요"> 
								<span
									class="input-group-append">
									<button type="button" class="btn btn-secondary btn-flat" id="idCheckBtn">중복확인</button>
								</span> 
								<span class="error invalid-feedback" style="display: block;"></span>
							</div>
							<div class="input-group mb-3">
								<input type="text" class="form-control" id="memPw" name="memPw" placeholder="비밀번호를 입력해주세요"> 
								<span class="error invalid-feedback" style="display: block;"></span>
							</div>
							<div class="input-group mb-3">
								<input type="text" class="form-control" id="memName" name="memName" placeholder="이름을 입력해주세요"> 
								<span class="error invalid-feedback" style="display: block;"></span>
							</div>
							<div class="input-group mb-3">
								<div class="form-group clearfix" style="margin-left: 10px;">
									<div class="icheck-primary d-inline">
										<input type="radio" id="memGenderM" name="memGender" value="M" checked="checked"> <label for="memGenderM">남자&nbsp;</label>
									</div>
									<div class="icheck-primary d-inline">
										<input type="radio" id="memGenderF" name="memGender" value="F">
										<label for="memGenderF">여자 </label>
									</div>
								</div>
							</div>
							<div class="input-group mb-3">
								<input type="text" class="form-control" id="memEmail" name="memEmail" placeholder="이메일을 입력해주세요">
							</div>
							<div class="input-group mb-3">
								<input type="text" class="form-control" id="memPhone" name="memPhone" placeholder="전화번호를 입력해주세요">
							</div>
							<div class="input-group mb-3">
								<input type="text" class="form-control" id="memPostCode" name="memPostcode"> <span class="input-group-append">
									<button type="button" class="btn btn-secondary btn-flat" onclick="DaumPostcode()">우편번호 찾기</button>
								</span>
							</div>
							<div class="input-group mb-3">
								<input type="text" class="form-control" id="memAddress1" name="memAddress1" placeholder="주소를 입력해주세요">
							</div>
							<div class="input-group mb-3">
								<input type="text" class="form-control" id="memAddress2" name="memAddress2" placeholder="상세주소를 입력해주세요">
							</div>
							
							<!-- 지도 표시하기 -->
							<div class="input-group mb-3">
								<div id="map" style="width: 100%; height: 300px; display: none;"></div>
							</div>
							
							<div class="icheck-primary mb-4"> 
								<input type="checkbox" id="memAgree" name="memAgree" value="Y">
								<label for="memAgree">개인정보 처리방침</label>
							</div>
							<button type="button" class="btn btn-primary mt-5 mb-4" id="signupBtn">가입하기</button>
							<button type="button" class="btn btn-secondary mt-5 mb-4" onclick="location.href='/ddit/board/login.do'">뒤로가기</button>
						</form>
					</div>
				</div>
			</div>

		</section>
	</main>
	<script src="${pageContext.request.contextPath }/resources/assets/dist/js/bootstrap.bundle.min.js"></script>
</body>

<!-- 다음 주소 API CDN -->
<!-- https://postcode.map.daum.net/guide -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<!-- 카카오 주소 API CDN -->
<!-- https://apis.map.kakao.com/ -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=4ec7163f5523fef7017bbaeb8274b3a9&libraries=services"></script>
<script>
$(function() {
	let idCheckBtn = $('#idCheckBtn');		// 중복확인 버튼
	let memAddress2 = $('#memAddress2');	// 상세주소 Element
	let signupBtn = $('#signupBtn');		// 가입하기 버튼
	let signupForm = $('#signupForm');		// 가입하기 Form

	let idCheckFlag = false;				// 중복확인 flag (false = 진행안함)
	
	// 아이디 중복확인
	idCheckBtn.on('click', function() {
		let id = $('#memId').val(); // 아이디 값
		
		if (id == null || id == "") {
			alert("아이디를 입력해주세요.");
			return false;
		}
		
		let data = {
				memId : id
		}
		
		$.ajax({
			url : "/ddit/board/idCheck.do",
			type : "post",
			contentType : "application/json;charset=utf-8",
			data : JSON.stringify(data),
			success : function(res) {
				// 결과로 넘어오는 데이터가 ServiceResult
				// Exist, NotExist에 따라서
				// '사용가능한 아이디입니다.' 또는 '이미 사용 중인 아이디입니다.'로 출력
				// 중복 확인시, idCheckFlag 라는 스위치가 발동 (true로 변환)
				let err = $(".error")[0];	// error 클래스를 가지고 있는 element 중 id 요소에 해당하는 Element
				
				if (res == "NOTEXIST") {	// 아이디 사용가능
					alert("사용 가능한 아이디입니다!");
					$(err).html("사용 가능한 아이디입니다!").css("color", "green");
					idCheckFlag = true;		// 아이디 중복체크 > 진행 가능
				} else {	// 아이디 사용 불가능
					alert("이미 사용 중인 아이디입니다!");
					$(err).html("이미 사용 중인 아이디입니다!").css("color", "red");
					idCheckFlag = false;	// 아이디 중복체크 > 진행 불가능
				}
			}
		});
	});
	
	let mapContainer;
	let map;
	
	// 상세주소 입력 후 포커스 아웃될 때, 지도가 표시됨
	memAddress2.on("focusout", function() {
		let address1 = $('#memAddress1').val();	// 기본주소 값 (필수값)
		let address2 = $('#memAddress2').val();	// 상세주소 값
		
		if (address1 != null && address1 != "") {
			mapContainer = document.getElementById('map'), // 지도를 표시할 div 
			
			mapOption = {
		        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
		        level: 3 // 지도의 확대 레벨
		    };  
			mapContainer.style.display = "block";
	
			// 지도를 생성합니다    
			var map = new kakao.maps.Map(mapContainer, mapOption); 
	
			// 주소-좌표 변환 객체를 생성합니다
			var geocoder = new kakao.maps.services.Geocoder();
	
			// 주소로 좌표를 검색합니다
			// 검색한 주소값을 데이터로 집어넣기
			geocoder.addressSearch(address1 + " " + address2, function(result, status) {
	
			    // 정상적으로 검색이 완료됐으면 
			     if (status === kakao.maps.services.Status.OK) {
	
			        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
	
			        // 결과값으로 받은 위치를 마커로 표시합니다
			        var marker = new kakao.maps.Marker({
			            map: map,
			            position: coords
			        });
	
			        // 인포윈도우로 장소에 대한 설명을 표시합니다
			        var infowindow = new kakao.maps.InfoWindow({
			            content: '<div style="width:150px;text-align:center;padding:6px 0;">HOME</div>'
			        });
			        infowindow.open(map, marker);
	
			        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
			        map.setCenter(coords);
			    } 
			});   
		}
		
		$('#card-signup').css("top", "140px");
	});
	
	signupBtn.on('click', function() {
		// 아이디, 비밀번호, 이름까지만 일반적인 데이터 검증
		// 개인정보 처리방침 동의를 체크했을 대만 가입하기가 가능하도록
		// 중복확인 처리가 true일 때
		let agreeFlag = false;		// 개인정보 처리방침 동의 체크 안함
		
		let memId = $('#memId').val();		// 아이디 값
		let memPw = $('#memPw').val();		// 비밀번호 값
		let memName = $('#memName').val();	// 이름 값
		
		if (memId == null || memId == "") {
			alert("아이디를 입력해주세요.");
			return false;
		}
		if (memPw == null || memPw == "") {
			alert("비밀번호를 입력해주세요.");
			return false;
		}
		if (memName == null || memName == "") {
			alert("이름을 입력해주세요.");
			return false;
		}
		
		// 개인정보 처리방침을 동의하게 되면  Y값이 넘어오므로, 동의 여부는 true로 처리한다.
		let memAgree = $('#memAgree:checked').val();
		if (memAgree == 'Y') {
			agreeFlag = true;
		}
		
		// 개인정보 처리방침을 동의했습니까?
		// 아이디 중복체크를 이행하고 오셨나요?
		// 하고 오셨다고요? 그럼 가입하기를 진행할게요
		// 예? 안했어요? 그럼 가입하기 어려운데? 안해줌
		if (agreeFlag) {
			if (idCheckFlag) {
				signupForm.submit();	// 중복체크함, 개인정보 동의함 > 컨트롤러로
			} else {
				alert("아이디 중복체크를 해주세요!");
			}
		} else {
			alert("개인정보 동의를 체크해주세요!");
		}
		
	});

});

/* 다음 주소 API */
/* https://postcode.map.daum.net/guide */
function DaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('memPostCode').value = data.zonecode;
            document.getElementById("memAddress1").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("memAddress2").focus();
        }
    }).open();
}
</script>