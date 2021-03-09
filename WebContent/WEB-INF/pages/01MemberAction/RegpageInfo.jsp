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
</style>
<body>

	<c:import url="/TopSec" />
	<div class="container" style="margin-bottom: 40pt;">
		<div class="row">
			<div class="col"></div>
			<div class="col-8">
				<div id="divCust" class="rounded-lg border border-dark mx-auto"
					style="padding: 5%; margin: 5%;">
					<form action="<c:url value="/welcome/register/progress2_fail" />"
						method="post">
						<h3 style="text-align:center; display: ${display_c} ">會員註冊：消費者</h3>
						<h3 style="text-align:center; display: ${display_s} ">會員註冊：廠商</h3>
						<hr />

						<div class="form-group row">
							<label for="custName" class="col-sm-3 col-form-label text-right">公司抬頭：</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="custName"
									name="company_name" autocomplete="off"
									value=${register_data.company_name }>
							</div>
						</div>
						<div class="form-group row">
							<label for="custPhone" class="col-sm-3 col-form-label text-right">公司電話：</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="custPhone"
									name="company_ph" autocomplete="off" maxlength="10"
									value=${register_data.company_ph }>
							</div>
						</div>
						<div class="form-group row">
							<label for="custAdd" class="col-sm-3 col-form-label text-right">公司地址：</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="custAdd"
									name="company_address" autocomplete="off"
									value=${register_data.company_address }>
							</div>
						</div>
						<div class="form-group row">
							<label for="cusPerson" class="col-sm-3 col-form-label text-right">負責人姓名：</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="cusPerson"
									name="contact_name" autocomplete="off"
									value=${register_data.contact_name }>
							</div>
						</div>
						<div class="form-group row">
							<label for="custPersonPhone"
								class="col-sm-3 col-form-label text-right">負責人電話：</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="custPersonPhone"
									name="contact_ph" maxlength="10"
									value=${register_data.contact_ph }>
							</div>
						</div>
						<div class="form-group row">
							<label for="custEmail" class="col-sm-3 col-form-label text-right">電子信箱：</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="custEmail"
									name="email" value=${register_data.email }>
							</div>
						</div>
						<div class="form-group row">
							<label for="custNum" class="col-sm-3 col-form-label text-right">統一編號：</label>
							<div class="col-sm-9">
								<input type="text" readonly class="form-control-plaintext"
									id="custNum" value=${register_data.conumber }>
							</div>
						</div>
						<div class="form-group row">
							<label for="custTitle" class="col-sm-3 col-form-label text-right">發票抬頭：</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="custTitle"
									name="invoice" value=${register_data.invoice }>
							</div>
						</div>


						<div class="text-right">
							<h6 id="checkaccount" class="form-text text-right text-danger">${msgError }</h6>
							<button type="submit" class="btn btn-primary">繼續</button>
							<button type="button" class="btn btn-primary"
								onclick="location.href='<c:url value="/welcome/login" />'">取消</button>
							<br> <br>
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
		var custName = document.getElementById("custName");
		var custPhone = document.getElementById("custPhone");
		var custAdd = document.getElementById("custAdd");
		var cusPerson = document.getElementById("cusPerson");
		var custPersonPhone = document.getElementById("custPersonPhone");
		var custEmail = document.getElementById("custEmail");
		var custTitle = document.getElementById("custTitle");

		custName.value = "茲廁慧股份有限公司";
		custPhone.value = "0204875487";
		custAdd.value = "台北市大安區信義路四段87號4樓";
		cusPerson.value = "吳巴啟";
		custPersonPhone.value = "0954879487";
		custEmail.value = "bkjgg7a3f3@gmail.com";
		custTitle.value = "茲廁慧股份有限公司";
	}

	$("input").attr("autocomplete", "off");
</script>
</html>