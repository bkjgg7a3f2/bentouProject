<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>會員註冊</title>
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
					<form action="<c:url value="/welcome/register/progress1_fail" />"
						method="post">
						<div class="form-group row">
							<label for="inputAccount"
								class="col-sm-3 col-form-label text-right">請輸入帳號：</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="inputAccount"
									name="useracc" placeholder="Account">
							</div>
						</div>
						<div class="form-group row">
							<label for="inputPassword"
								class="col-sm-3 col-form-label text-right">請輸入密碼：</label>
							<div class="col-sm-9">
								<input type="password" class="form-control" id="inputPassword"
									name="userpwd" placeholder="Password">
							</div>
						</div>
						<div class="form-group row">
							<label for="inputPasswordAgain"
								class="col-sm-3 col-form-label text-right">請再次輸入密碼：</label>
							<div class="col-sm-9">
								<input type="password" class="form-control" name="userpwd2"
									id="inputPasswordAgain" placeholder="Check Password">
							</div>
						</div>
						<div class="form-group row">
							<label for="inputIDNum"
								class="col-sm-3 col-form-label text-right">請輸入統一編號：</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="inputIDNum"
									name="conumber" maxlength="8" size="8" placeholder="統一編號">
							</div>
						</div>
						<div class="col-sm-12 text-right">
							<div class="form-check form-check-inline text-right">
								<input class="form-check-input" type="radio" name="memberType"
									id="memberType1" value="customer" checked> <label
									class="form-check-label" for="memberType1">消費者</label>
							</div>
							<div class="form-check form-check-inline text-right">
								<input class="form-check-input" type="radio" name="memberType"
									id="memberType2" value="shop"> <label
									class="form-check-label" for="memberType2">廠商</label>
							</div>
						</div>
						<hr />

						<div class="text-right">
							<h6 id="checkaccount" class="form-text text-right text-danger">${msgError }</h6>
							<button type="submit" class="btn btn-primary">註冊</button>
							<button type="button" class="btn btn-primary"
								onclick="location.href='<c:url value="/welcome/login" />'">取消</button>
							<br>
							<br>
							<button type="button" class="btn btn-success"
								onclick="autoFill()">一鍵輸入(消費者)</button>
						</div>
					</form>
				</div>
			</div>


			<div class="col"></div>
		</div>
	</div>

	<c:import url="/FooterSec" />
</body>
<script>
	function autoFill() {
		var user = document.getElementById("inputAccount");
		var password = document.getElementById("inputPassword");
		var inputPasswordAgain = document.getElementById("inputPasswordAgain");
		var inputIDNum = document.getElementById("inputIDNum");

		user.value = "dddd4321";
		password.value = "Dddd2021";
		inputPasswordAgain.value = "Dddd2021";
		inputIDNum.value = "27940391";
	}

	$("input").attr("autocomplete", "off");
</script>
</html>