<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>會員資料</title>
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
		<form action="<c:url value="/cstmr/data/update" />" method="post"
			enctype="multipart/form-data">
			<div class="row">
				<div class="col-2">
					<figure class="figure w-100" style="height: 100px">
						<img id="custPic" src='/cstmrPics/${cstmr_data.image }'
							class="figure-img img-fluid rounded mh-100"
							style="object-fit: cover" alt="圖片預覽區">
						<figcaption class="figure-caption text-center"></figcaption>
					</figure>
					<button onclick="sycnPic()" type="button" class="btn btn-primary"
						data-toggle="modal" data-target="#picUpdate">更改圖片</button>
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
							<textarea readonly rows="2" class="form-control-plaintext"
								style="resize: none" id="custName">${cstmr_data.name }</textarea>
						</div>
					</div>
					<div class="form-group row">
						<label for="custPhone" class="col-sm-6 col-form-label text-right">公司電話：</label>
						<div class="col-sm-6">
							<input type="text" readonly class="form-control-plaintext"
								id="custPhone" value="${cstmr_data.ph }">
						</div>
					</div>
					<div class="form-group row">
						<label for="custAdd" class="col-sm-6 col-form-label text-right">公司地址：</label>
						<div class="col-sm-6">
							<textarea readonly class="form-control-plaintext" rows="2"
								style="resize: none" id="custAdd">${cstmr_data.address }</textarea>
						</div>
					</div>
					<div class="form-group row">
						<label for="cusPerson" class="col-sm-6 col-form-label text-right">負責人姓名：</label>
						<div class="col-sm-6">
							<input type="text" readonly class="form-control-plaintext"
								id="cusPerson" value="${cstmr_data.contact }">
						</div>
					</div>
					<div class="form-group row">
						<label for="custPersonPhone"
							class="col-sm-6 col-form-label text-right">負責人電話：</label>
						<div class="col-sm-6">
							<input type="text" readonly class="form-control-plaintext"
								id="custPersonPhone" value="${cstmr_data.contactphnum }">
						</div>
					</div>
					<div class="form-group row">
						<label for="custEmail" class="col-sm-6 col-form-label text-right">電子信箱：</label>
						<div class="col-sm-6">
							<input type="text" readonly class="form-control-plaintext"
								id="custEmail" value="${cstmr_data.email }">
						</div>
					</div>
					<div class="form-group row">
						<label for="custNum" class="col-sm-6 col-form-label text-right">統一編號：</label>
						<div class="col-sm-6">
							<input type="text" readonly class="form-control-plaintext"
								id="custNum" value="${cstmr_data.conumber }">
						</div>
					</div>
					<div class="form-group row">
						<label for="custTitle" class="col-sm-6 col-form-label text-right">發票抬頭：</label>
						<div class="col-sm-6">
							<textarea readonly rows="2" class="form-control-plaintext"
								style="resize: none" id="custTitle">${cstmr_data.invoice }</textarea>
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
							<input type="text" readonly class="form-control-plaintext"
								id="custBankAcc" value="${cstmr_data.bankaccount }">
						</div>
					</div>
					<div class="form-group row">
						<label for="custAccSta" class="col-sm-6 col-form-label text-right">會員狀態：</label>
						<div class="col-sm-3">
							<input type="text" readonly class="form-control-plaintext"
								id="custAccSta" value="${cstmr_data.vip }"> <a
								style="display: ${display }"
								onclick="window.open('https://p.ecpay.com.tw/8E30F');" href="#"><img
								src="https://payment.ecpay.com.tw/Content/themes/WebStyle20170517/images/ecgo.png" /></a>
							<a href='#' style="display: ${display }" onclick="sycnPic()"
								data-toggle="modal" data-target="#codeInsert"
								class="badge badge-pill badge-warning">繳費代碼輸入</a>
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
						<button type="submit" class="btn btn-primary">修改資料</button>
						<button type="button" class="btn btn-primary"
							onclick="location.href='<c:url value="/cstmr/data/account/update" />'">修改密碼</button>
					</div>
				</div>
				<div class="col"></div>

			</div>
		</form>
	</div>

	<!-- 改圖Modal -->
	<div class="modal fade" id="picUpdate" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="picUpdateTitle">更改目前圖片</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form action="<c:url value='/cstmr/data/picchange'/>" method="post"
					enctype="multipart/form-data" onsubmit="return picchk()">
					<div class="modal-body">
						<figure class="figure mw-100" style="height: 100px">
							<img id="custPicModel" src=''
								class="figure-img img-fluid rounded h-100"
								style="object-fit: cover" alt="圖片預覽區">
							<figcaption class="figure-caption text-center"></figcaption>
						</figure>
						<div class="input-group input-group-sm mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text" id="inputGroupFileAddon01">上傳圖片</span>
							</div>
							<div class="custom-file">
								<input type="file" class="custom-file-input"
									id="inputGroupFileInput01" name="picFiles"
									accept="image/gif, image/jpeg, image/png, image/bmp, image/jpg"
									onchange="xmTanUploadImg(this)"
									aria-describedby="inputGroupFileAddon01" name="myFiles">
								<label class="custom-file-label" for="picFiles"
									id="imgUpload01Lab"></label>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">取消</button>
						<button type="submit" class="btn btn-primary">確定更換</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- 代碼Modal -->
	<div class="modal fade" id="codeInsert" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="picUpdateTitle">繳費代碼輸入</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form action="<c:url value='/cstmr/data/codeinsert'/>" method="post"
					onsubmit="return codechk()">
					<div class="modal-body">
						請於繳費後正確輸入繳費代碼，將於1至3個工作日內查驗完畢，即可立即升級為付費會員 <br> <br> <span
							class="font-weight-bold">注意事項：</span> <br>(1)未繳費請勿輸入代碼 <br>(2)若未透過正常程序而輸入隨機號碼，經查證且行為嚴重者，將實行<span
							class="text-danger font-weight-bold">永久停權</span>之處分
					</div>
					<div class="modal-body">
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text" id="basic-addon1">ECPay</span>
							</div>
							<input type="text" id="ecpaycode" class="form-control"
								placeholder="請輸入繳費代碼" aria-label="Username"
								aria-describedby="basic-addon1" name="code" maxlength="16">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">取消</button>
						<button type="submit" class="btn btn-primary">確定</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<c:import url="/FooterSec" />
</body>
<script>
	console.log("${cstmr_data.image }");
	//圖片不存在時以預設圖片代替
	$("img")
			.one(
					"error",
					function(e) {
						$(this)
								.attr("src",
										"/Presentation/resources/images/cstmrPics/default.jpg");
					});

	//同步跳出視窗內的圖片與頁面顯示的縮圖
	function sycnPic() {
		$("#custPicModel").attr("src", $("#custPic").attr("src"))
	}
	//判斷瀏覽器是否支持FileReader接口
	if (typeof FileReader == 'undefined') {
		alert("<h1>當前瀏覽器不支持FileReader</h1>");
	}
	//選擇圖片，馬上預覽
	function xmTanUploadImg(obj) {
		var file = obj.files[0];
		var reader = new FileReader();
		reader.onload = function(e) {
			var img = document.getElementById("custPicModel");
			img.src = e.target.result;
		}
		reader.readAsDataURL(file);
		$("#imgUpload01Lab").text(file.name)
	}

	//圖片確認
	function picchk() {
		if (!$('#inputGroupFileInput01').val()) {
			alert("請上傳圖片");
			return false;
		}
		return true;
	}

	//繳費代碼確認
	function codechk() {
		var t = document.getElementById("ecpaycode").value;
		if (t.length == 0) {
			alert("未輸入繳費代碼");
			return false;
		} else {
			if (confirm("送出後便無法修改，請確認繳費代碼是否正確")) {
				return true;
			} else {
				return false;
			}
		}
	}

	$("input").attr("autocomplete", "off");
</script>
</html>