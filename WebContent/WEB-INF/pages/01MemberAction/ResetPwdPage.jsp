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
				<div id="inputData">
					<h4 class="mt-3 text-center">請輸入新的密碼</h4>
					<div class="rounded-lg border border-dark mx-auto"
						style="padding: 5%; margin: 5%;">
						<form action="<c:url value="/welcome/reset_password_fail" />" method="post">
							<input type='hidden' name='acc' value='${acc}'> <input
								type='hidden' name='memberType' value='${memberType}'>
							<div class="form-group">
								<label for="newPwd">新的密碼：</label> <input type="password"
									class="form-control" id="newPwd" name="pwd" />
							</div>
							<div class="form-group">
								<label for="newPwd2">再次輸入新的密碼：</label> <input type="password"
									class="form-control" id="newPwd2" name="pwd2" />
							</div>

							<hr />
							<small id="checkPwdMsg" class="form-text text-danger"></small>
							<button id="changePwd" type="submit" class="btn btn-primary"
								disabled>送出</button>

						</form>
					</div>
				</div>
			</div>


			<div class="col"></div>
		</div>
	</div>

	<c:import url="/FooterSec" />
</body>
<script>
	$("input").attr("autocomplete", "off");
	$("#newPwd,#newPwd2").change(function() {
		var pwd1 = $("#newPwd").val();
		var pwd2 = $("#newPwd2").val();
		if (pwd1 === pwd2 && pwd1 !== "") {
			$("#changePwd").attr("disabled", false)
			$("#checkPwdMsg").empty();
		} else {
			$("#changePwd").attr("disabled", true)
			$("#checkPwdMsg").text("兩次輸入的密碼不相同");
		}
	})
</script>
</html>