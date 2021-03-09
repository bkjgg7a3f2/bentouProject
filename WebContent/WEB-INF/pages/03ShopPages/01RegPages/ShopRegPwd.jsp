<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>會員資料-修改密碼</title>
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
</style>
<body>
	<c:import url="/TopSec" />
	<h5 class="my-4" style="text-align: center">修改密碼－廠商</h5>

	<div class="container" style="margin-bottom: 45pt;">
		<form action="<c:url value="/shop/data/account/update_fail" />" method="post">
			<div class="row">
				<div class="col"></div>
				<div class="col-6">
					<div class="form-group row">
						<label for="oriPwd" class="col-sm-4 col-form-label text-right">輸入原本密碼：</label>
						<div class="col-sm-8">
							<input type="password" class="form-control" id="oriPwd" name="pwd">
						</div>
					</div>
					<div class="form-group row">
						<label for="newPwd" class="col-sm-4 col-form-label text-right">輸入新的密碼：</label>
						<div class="col-sm-8">
							<input type="password" class="form-control" id="newPwd" name="pwd1">
						</div>
					</div>
					<div class="form-group row">
						<label for="newPwd2" class="col-sm-4 col-form-label text-right">再次輸入新的密碼：</label>
						<div class="col-sm-8">
							<input type="password" class="form-control" id="newPwd2" name="pwd2">
							<small id="changeMsg" class="form-text text-danger text-right">${ChangePwdMsg }</small>
						</div>
					</div>

					<div class="text-right ">
						<button type="submit" class="btn btn-primary">修改密碼</button>
						<button type="button" class="btn btn-primary"
							onclick="location.href='<c:url value="/shop/data"/>'">取消</button>
					</div>
				</div>
				<div class="col"></div>
			</div>
		</form>
	</div>

	<c:import url="/FooterSec" />
</body>
</html>