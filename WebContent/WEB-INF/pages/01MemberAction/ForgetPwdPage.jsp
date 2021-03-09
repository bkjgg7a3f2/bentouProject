<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>忘記密碼</title>
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
					<h4 class="mt-3 text-center">請輸入註冊時的帳號與電子郵件</h4>
					<div class="rounded-lg border border-dark mx-auto"
						style="padding: 5%; margin: 5%;">
						<form action="<c:url value="/forgetpwd" />" method="post">
							<div class="form-group">
								<label for="UserAcc">使用者帳號：</label> <input type="text"
									class="form-control" id="UserAcc" name="name" />
							</div>
							<div class="form-group">
								<label for="InputEmail">使用者電子信箱：</label> <input type="text"
									class="form-control" id="InputEmail" name="email" />
							</div>

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
							<button id="doForget" type="button" class="btn btn-primary">送出</button>
							<button type="button" class="btn btn-secondary"
								onclick="location.href='<c:url value="/welcome/login" />'">取消</button>
						</form>
					</div>
				</div>
				<div id="msgSuccess"></div>
				<!-- 彈出視窗 -->
				<div class="modal fade" id="errMsgDiv" tabindex="-1" role="dialog"
					aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
					<div class="modal-dialog modal-dialog-centered" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title text-danger" id="exampleModalCenterTitle">輸入資料錯誤</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">輸入的帳號或電子郵件網址錯誤，請重新輸入</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-primary"
									data-dismiss="modal">確定</button>
							</div>
						</div>
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
	$("#doForget").click(function() {

		$.post("${pageContext.request.contextPath}/welcome/forgetpwd_fail", {
			"acc" : $("#UserAcc").val(),
			"email" : $("#InputEmail").val(),
			"memberType" : $("input[name='memberType']:checked").val()
		}).done(function(data) {
			$("#inputData").addClass("d-none");
			$("#msgSuccess").html(data);
		}).fail(function(xhr, status, error) {
			$('#errMsgDiv').modal('show');
		});

	})
</script>
</html>