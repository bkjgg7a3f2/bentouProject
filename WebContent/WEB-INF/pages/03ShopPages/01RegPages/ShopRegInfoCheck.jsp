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
	<h5 class="my-4" style="text-align: center">會員資料－廠商</h5>

	<div class="container" style="margin-bottom: 45pt;">
		<form action="<c:url value="/shop/data/update_fail" />" method="post">
			<div class="row">
				<div class="col-2">
					<figure class="figure w-100" style="height: 100px">

						<img id="shopPic" src='/supplyPics/${supply_data.image }'
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
						<label for="shopName" class="col-sm-6 col-form-label text-right">公司抬頭：</label>
						<div class="col-sm-6">
							<textarea class="form-control" rows="2" name="name"
								style="resize: none;" id="shopName">${supply_data.name }</textarea>
						</div>
					</div>
					<div class="form-group row">
						<label for="shopPhone" class="col-sm-6 col-form-label text-right">公司電話：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="shopPhone" name="ph"
								maxlength="10" value=${supply_data.ph }>
						</div>
					</div>
					<div class="form-group row">
						<label for="shopAdd" class="col-sm-6 col-form-label text-right">公司地址：</label>
						<div class="col-sm-6">
							<textarea class="form-control" rows="2" name="address"
								style="resize: none;" id="shopAdd">${supply_data.address }</textarea>
						</div>
					</div>
					<div class="form-group row">
						<label for="shopPerson" class="col-sm-6 col-form-label text-right">負責人姓名：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="shopPerson"
								name="contact" value="${supply_data.contact }">
						</div>
					</div>
					<div class="form-group row">
						<label for="shopPersonPhone"
							class="col-sm-6 col-form-label text-right">負責人電話：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="shopPersonPhone"
								name="contactphnum" maxlength="10"
								value="${supply_data.contactphnum }">
						</div>
					</div>
					<div class="form-group row">
						<label for="shopEmail" class="col-sm-6 col-form-label text-right">電子信箱：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="shopEmail"
								name="email" value="${supply_data.email }">
						</div>
					</div>
					<div class="form-group row">
						<label for="shopNum" class="col-sm-6 col-form-label text-right">統一編號：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="shopNum"
								name="conumber" maxlength="8" value="${supply_data.conumber }">
						</div>
					</div>
					<div class="form-group row">
						<label for="shopTitle" class="col-sm-6 col-form-label text-right">發票抬頭：</label>
						<div class="col-sm-6">
							<textarea class="form-control" rows="2" name="invoice"
								style="resize: none;" id="shopTitle">${supply_data.invoice }</textarea>
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
						<label for="shopBankAcc"
							class="col-sm-6 col-form-label text-right">銀行帳號：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="shopBankAcc"
								name="bankaccount" value="${supply_data.bankaccount }">
						</div>
					</div>
					<div class="form-group row">
						<label for="shopLimit" class="col-sm-6 col-form-label text-right">最低消費金額：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="shopLimit"
								name="limit" value="${limit }"><small id="changeLimit"
								class="form-text text-muted text-right">${ChangeLimit }</small>
						</div>
					</div>
					<div class="form-group row">
						<label for="shopAccSta" class="col-sm-6 col-form-label text-right">會員狀態：</label>
						<div class="col-sm-6">
							<input type="text" readonly class="form-control-plaintext"
								id="shopAccSta" value="${supply_data.vip }">
						</div>
						<div class="col-sm-6 text-center"></div>

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
							onclick="location.href='<c:url value="/shop/data"/>'">取消</button>
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