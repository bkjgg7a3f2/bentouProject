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
					<form action="<c:url value="/welcome/register/progress3_fail" />"
						method="post">

						<h3 style="text-align:center; display: ${display_c} ">會員註冊：消費者</h3>
						<h3 style="text-align:center; display: ${display_s} ">會員註冊：廠商</h3>
						<hr />

						<div class="form-group row font-weight-bold">
							<label for="custAcc" class="col-sm-3 col-form-label text-right">使用者帳號：</label>
							<div class="col-sm-9">
								<input type="text" readonly class="form-control-plaintext"
									id="custAcc" value=${register_data.acc }>
							</div>
						</div>
						<div class="form-group row font-weight-bold">
							<label for="custPwd" class="col-sm-3 col-form-label text-right">使用者密碼：</label>
							<div class="col-sm-9">
								<input type="text" readonly class="form-control-plaintext"
									id="custPwd" value=${register_data.pwd }>
							</div>
						</div>

						<div class="form-group row">
							<label for="custName" class="col-sm-3 col-form-label text-right">公司抬頭：</label>
							<div class="col-sm-9">
								<input type="text" readonly class="form-control-plaintext"
									id="custName" value=${register_data.company_name }>
							</div>
						</div>
						<div class="form-group row">
							<label for="custPhone" class="col-sm-3 col-form-label text-right">公司電話：</label>
							<div class="col-sm-9">
								<input type="text" readonly class="form-control-plaintext"
									id="custPhone" value=${register_data.company_ph }>
							</div>
						</div>
						<div class="form-group row">
							<label for="custAdd" class="col-sm-3 col-form-label text-right">公司地址：</label>
							<div class="col-sm-9">
								<input type="text" readonly class="form-control-plaintext"
									id="custAdd" value=${register_data.company_address }>
							</div>
						</div>
						<div class="form-group row">
							<label for="cusPerson" class="col-sm-3 col-form-label text-right">負責人姓名：</label>
							<div class="col-sm-9">
								<input type="text" readonly class="form-control-plaintext"
									id="cusPerson" value=${register_data.contact_name }>
							</div>
						</div>
						<div class="form-group row">
							<label for="custPersonPhone"
								class="col-sm-3 col-form-label text-right">負責人電話：</label>
							<div class="col-sm-9">
								<input type="text" readonly class="form-control-plaintext"
									id="custPersonPhone" value=${register_data.contact_ph }>
							</div>
						</div>
						<div class="form-group row">
							<label for="custEmail" class="col-sm-3 col-form-label text-right">電子信箱：</label>
							<div class="col-sm-9">
								<input type="text" readonly class="form-control-plaintext"
									id="custEmail" value=${register_data.email }>
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
								<input type="text" readonly class="form-control-plaintext"
									id="custTitle" value=${register_data.invoice }>
							</div>
						</div>
						<hr />
						<div class="form-group row">
							<div class="col-sm-3"></div>
							<div class="col-sm-9">
								<div class="custom-control custom-checkbox ">
									<input type="checkbox" class="custom-control-input"
										id="custCheck" name="terms" value="true"> <label
										class="custom-control-label" for="custCheck">已同意<a
										class="text-primary" href='<c:url value="/" />'>服務條款</a>
									</label>
								</div>
								<h6 id="checkservice"
									class="form-text text-right text-danger">${msgError }</h6>
							</div>
						</div>

						<div class="text-right">
							<button type="button" class="btn btn-primary"
								onclick="location.href='<c:url value="/welcome/register/progress2"/>'">修改資料</button>
							<button type="submit" class="btn btn-primary">繼續</button>
							<button type="button" class="btn btn-primary"
								onclick="location.href='<c:url value="/welcome/login"/>'">取消</button>
						</div>
					</form>
				</div>

			</div>

			<div class="col"></div>
		</div>
	</div>
	<!-- 測試用程式碼，可刪除 -->
	<script>
		
	</script>
	<!-- 測試用程式碼結束 -->
	<c:import url="/FooterSec" />
</body>
</html>