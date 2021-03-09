<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>感謝訂購</title>
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap.min.css"/>" />
<link
	href="https://fonts.googleapis.com/css?family=Noto+Sans+TC&display=swap"
	rel="stylesheet">

</head>




<style>
body {
	font-family: 'Noto Sans TC', sans-serif;
}
</style>

<!-- <script src="/js/bootstrap.min.js"></script> -->
<!-- <script src="/js/JQuery3.4.1.js"></script> -->


<body>
	<c:import url="/TopSec" />
	<div class="container">
		<div class="row">
			<div class="col"></div>
			<div class="col-8">
				<div class="jumbotron my-4" style="background-color:e4ff1a !important;">
					<h1 class="display-4">感謝您使用本站服務</h1>
					<p id="welcome" class="lead">您的訂單已送出！廠商目前確認中。</p>
					<hr class="my-4">
					<p class="d-none"></p>
					<a class="btn btn-danger btn-lg" href="<c:url value="/cstmr/shoppingcart/" />" role="button">繼續購物</a>
				</div>
			</div>
			<div class="col"></div>
		</div>
	</div>



	<c:import url="/FooterSec" />


</body>
<!-- <script type="text/javascript"> -->
<!-- // 	onload = function() { -->
<!-- // 		setInterval(go, 1000); -->
<!-- // 	}; -->
<!-- // 	var x = 3; //設定跳轉秒數 -->
<!-- // 	function go() { -->
<!-- // 		x--; -->
<!-- // 		if (x > 0) { -->
<!-- // 			document.getElementById("sp").innerHTML = x; //倒數 -->
<!-- // 		} else { -->
<!-- // 			location.href = '#'; -->
<!-- // 		} -->
<!-- // 	} -->
<!-- </script> -->
</html>