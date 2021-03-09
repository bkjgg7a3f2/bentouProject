<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登入</title>
</head>

<link rel="stylesheet"
	href="<c:url value='resources/css/bootstrap.min.css'/>" />
<link
	href="https://fonts.googleapis.com/css?family=Noto+Sans+TC&display=swap"
	rel="stylesheet">
<style>
body {
	font-family: 'Noto Sans TC', sans-serif;
}

div {
	border: 5pt black bold;
}
</style>
<body>
	<c:import url="/TopSec" />

	<div class="container" style="margin-bottom: 40pt;">
		<div class="row">
			<div class="col"></div>
			<div class="col-8">

				<div class="rounded-lg border border-dark mx-auto"
					style="padding: 5%; margin: 5%;">
					<form action="<c:url value="/welcome/login_fail" />" method="post">
						<div class="form-group">
							<label for="UserAcc">使用者帳號：</label> <input type="text"
								class="form-control" id="UserAcc" name="useracc" />
						</div>
						<div class="form-group row">
							<div class="form-group col-sm">
								<label for="InputPassword">使用者密碼：</label> <input type="password"
									class="form-control" id="InputPassword" name="userpwd" />
							</div>

						</div>
						<div class="form-group row">
							<div class="form-group col-sm mx-0">
								<label for="code">驗證碼：</label> <input id="code"
									type="text" name="verifycode" size="5"/>
									<img src="createCode" class="h-100">
							</div>
						</div>
						
						<div class="row ">
							<div class="col-sm text-right">
								<a class="text-primary"
									href='<c:url value="/welcome/forgetpwd" />'>忘記密碼</a>
							</div>
						</div>

						<!-- 						<div class="form-group form-check"> -->
						<!-- 							<input type="checkbox" class="form-check-input" id="rememberPwd" -->
						<!-- 								name="auto" value="true"> <label -->
						<!-- 								class="form-check-label" for="rememberPwd">記住帳號密碼</label> -->
						<!-- 						</div> -->

						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="memberType"
								id="memberType1" value="customer" checked> <label
								class="form-check-label" for="memberType1">消費者</label>
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="memberType"
								id="memberType2" value="shop"> <label
								class="form-check-label" for="memberType2">廠商</label>
						</div>
						<hr />
						<h6 id="checkaccount" class="form-text text-right text-danger">${msgError }</h6>
						<button type="submit" class="btn btn-primary">登入</button>
						<button type="button" class="btn btn-primary"
							onclick="location.href='<c:url value="/welcome/register" />'">註冊</button>
						<br> <br>
						<button type="button" class="btn btn-success"
							onclick="cstmr_autoFill()">一鍵輸入(消費者)</button>
						<button type="button" class="btn btn-success"
							onclick="supply_autoFill()">一鍵輸入(廠商)</button>
					</form>
				</div>
			</div>


			<div class="col"></div>
		</div>
	</div>

	<c:import url="/FooterSec" />
</body>
<script>
	//一鍵輸入
	function cstmr_autoFill() {
		var user = document.getElementById("UserAcc");
		var password = document.getElementById("InputPassword");

		user.value = "aaaa4321";
		password.value = "Aaaa2021";
		$("#memberType1").attr("checked", true);
		$("#memberType2").attr("checked", false);
	}

	function supply_autoFill() {
		var user = document.getElementById("UserAcc");
		var password = document.getElementById("InputPassword");

		user.value = "aaaa1234";
		password.value = "Aaaa2020";
		$("#memberType1").attr("checked", false);
		$("#memberType2").attr("checked", true);
	}

	$("input").attr("autocomplete", "off");
</script>
</html>