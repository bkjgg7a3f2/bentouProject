<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>會員資料-修改</title>
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
	<h5 class="my-4" style="text-align: center">會員資料－消費者</h5>

	<div class="container" style="margin-bottom: 45pt;">
		<form action="<c:url value="/cstmr/data/update_fail" />" method="post">
			<div class="row">
				<div class="col-2">
					<figure class="figure mw-100" style="height: 100px">
						<img id="custPic" src='/cstmrPics/${cstmr_data.image }'
							class="figure-img img-fluid rounded mh-100"
							style="object-fit: cover" alt="圖片預覽區">
						<figcaption class="figure-caption text-center"></figcaption>
					</figure>
				</div>
				<div class="col-5">
					<div class="form-group row">
						<div class="col-sm-4"></div>
						<h5 class="col-sm-6 rounded-pill  text-white"
							style="text-align: center; background-color: #000066">基本資料</h5>
						<div class="col-sm"></div>
					</div>

					<div class="form-group row">
						<label for="custName" class="col-sm-6 col-form-label text-right">公司抬頭：</label>
						<div class="col-sm-6">
							<textarea class="form-control" rows="2" id="custName" name="name"
								style="resize: none">${cstmr_data.name }</textarea>
						</div>
					</div>
					<div class="form-group row">
						<label for="custPhone" class="col-sm-6 col-form-label text-right">公司電話：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="custPhone" name="ph"
								maxlength="10" value="${cstmr_data.ph }">
						</div>
					</div>
					<div class="form-group row">
						<label for="custAdd" class="col-sm-6 col-form-label text-right">公司地址：</label>
						<div class="col-sm-6">
							<textarea class="form-control" rows="2" name="address"
								style="resize: none" id="custAdd">${cstmr_data.address }</textarea>
						</div>
					</div>
					<div class="form-group row">
						<label for="cusPerson" class="col-sm-6 col-form-label text-right">負責人姓名：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="cusPerson"
								name="contact" value="${cstmr_data.contact }">
						</div>
					</div>
					<div class="form-group row">
						<label for="custPersonPhone"
							class="col-sm-6 col-form-label text-right">負責人電話：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="custPersonPhone"
								name="contactphnum" maxlength="10"
								value="${cstmr_data.contactphnum }">
						</div>
					</div>
					<div class="form-group row">
						<label for="custEmail" class="col-sm-6 col-form-label text-right">電子信箱：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="custEmail"
								name="email" value="${cstmr_data.email }">
						</div>
					</div>
					<div class="form-group row">
						<label for="custNum" class="col-sm-6 col-form-label text-right">統一編號：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="custNum"
								name="conumber" maxlength="8" value="${cstmr_data.conumber }">
						</div>
					</div>
					<div class="form-group row">
						<label for="custTitle" class="col-sm-6 col-form-label text-right">發票抬頭：</label>
						<div class="col-sm-6">
							<textarea rows="2" class="form-control" style="resize: none"
								id="custTitle" name="invoice">${cstmr_data.invoice }</textarea>
							<small id="changeMsg" class="form-text text-muted text-right">${ChangeMsg }</small>
						</div>
					</div>

				</div>
				<div class="col-5">
					<div class="form-group row">
						<div class="col-sm-4"></div>
						<h5 class="col-sm-6 rounded-pill  text-white"
							style="text-align: center; background-color: #000066">進階設定</h5>
						<div class="col-sm"></div>
					</div>
					<div class="form-group row">
						<label for="custBankAcc"
							class="col-sm-6 col-form-label text-right">銀行帳號：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="custBankAcc"
								name="bankaccount" value="${cstmr_data.bankaccount }">
						</div>
					</div>
					<div class="form-group row">
						<label for="custAccSta" class="col-sm-6 col-form-label text-right">會員狀態：</label>
						<div class="col-sm-3">
							<input type="text" readonly class="form-control-plaintext"
								id="custAccSta" value="${cstmr_data.vip }">
						</div>
						<div class="col-sm-3 text-center"></div>

					</div>

					<div class="form-group row">
						<div class="col-sm-12">
							<span> </span>
						</div>
					</div>
					<div class="form-group row">
						<div class="col-sm-12">
							<span> </span>
						</div>
					</div>
					<div class="text-right ">
						<button type="submit" class="btn btn-primary">確認</button>
						<button type="button" class="btn btn-primary"
							onclick="location.href='<c:url value="/cstmr/data"/>'">取消</button>
					</div>
				</div>
				<div class="col"></div>

			</div>
		</form>
	</div>

	<c:import url="/FooterSec" />
</body>
<script>
	//資料修改確認
	function chk() {
		if (confirm("是否修改資料？")) {
			alert("資料修改成功");
			return true;
		} else {
			return false;
		}
	}
	$("input").attr("autocomplete", "off");
</script>
</html>