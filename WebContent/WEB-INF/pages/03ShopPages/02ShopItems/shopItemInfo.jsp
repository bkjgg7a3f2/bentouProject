<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品資訊-${Map_sales_form.name }</title>
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
	<h5 class="my-4" style="text-align: center">商品詳細資料</h5>

	<div class="container" style="margin-bottom: 45pt;">
		<form action="<c:url value="/shop/sales/form/${key }" />"
			method="post" enctype="multipart/form-data">
			<div class="row">
				<div class="col"></div>
				<div class="col-8">
					<div class="form-group row align-items-center">
						<label for="itemName" class="col-sm-4 col-form-label text-right">商品名稱：</label>
						<div class="col-sm-8">
							<input type="text" class="form-control form-control-sm"
								style="max-width: 15em;" id="itemName" name="commodity_name"
								value="${Map_sales_form.name }">
						</div>
					</div>
					<div class="form-group row align-items-center">
						<label for="itemPhone" class="col-sm-4 col-form-label text-right">商品定價：</label>
						<div class="col-sm-8">
							<input type="number" class="form-control form-control-sm "
								min="1" id="itemPrice" name="commodity_price"
								style="max-width: 15em;" value=${Map_sales_form.price }>
						</div>
					</div>
					<div class="form-group row align-items-center">
						<div class="col-sm-4 col-form-label text-right">
							<span class="align-baseline">品項類型：</span>
						</div>
						<div class="col-sm-8  form-inline">
							<div
								class="custom-control custom-radio custom-control-inline align-baseline">
								<input id="meat" type="radio" name="Vegan"
									class="custom-control-input" value="M" checked> <label
									class="custom-control-label" for="meat">葷</label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input id="Vege" type="radio" name="Vegan"
									class="custom-control-input" value="V"
									${Map_sales_form.Vegan_V }> <label
									class="custom-control-label" for="Vege">素</label>
							</div>
						</div>
					</div>

					<div class="form-group row align-items-center">
						<div class="col-sm-4 col-form-label text-right">
							<span class="align-baseline">折扣類型：</span>
						</div>
						<div class="col-sm-8 form-inline">
							<div class="form-group">
								<div
									class="custom-control custom-radio custom-control-inline align-baseline">
									<input type="radio" id="discountTypeNone"
										name="commodity_discount_type" class="custom-control-input"
										value=1 checked> <label class="custom-control-label"
										for="discountTypeNone">無</label>
								</div>
							</div>
							<div class="form-group">
								<div
									class="custom-control custom-radio custom-control-inline align-baseline">
									<input type="radio" id="discountTypePercent"
										name="commodity_discount_type" class="custom-control-input"
										value=2> <label class="custom-control-label"
										for="discountTypePercent">原價打折(%)：</label>
								</div>
								<input id="percentDInput"
									class="form-control form-control-sm mr-2" type="number"
									name="commodity_discount_number_2" style="max-width: 4em;"
									max="99" min="0" value=${Map_sales_form.discount_number_2 }
									id="percentDiscountInput">
							</div>
							<div class="form-group">
								<div
									class="custom-control custom-radio custom-control-inline align-baseline">
									<input type="radio" id="discountTypePrice"
										name="commodity_discount_type" class="custom-control-input"
										value=3> <label class="custom-control-label"
										for="discountTypePrice">原價降價(元)：</label>
								</div>
								<input id="priceDInput" class="form-control form-control-sm"
									type="number" name="commodity_discount_number_3"
									style="max-width: 4em;" min="1"
									value=${Map_sales_form.discount_number_3 }>
							</div>

						</div>
					</div>
					<div class="form-group row align-items-center">
						<div class="col-sm-4 col-form-label text-right">
							<span class="align-baseline">折扣開始時間：</span>
						</div>
						<div class="col-sm-8 form-inline">

							<div class="form-group">
								<input id="discountDateIniInput" name="discount_timeini"
									class="form-control form-control-sm" type="date"
									value="${Map_sales_form.timeini }" />
							</div>
						</div>
					</div>
					<div class="form-group row align-items-center">
						<div class="col-sm-4 col-form-label text-right">
							<span class="align-baseline">折扣結束時間：</span>
						</div>
						<div class="col-sm-8 form-inline">
							<div class="form-group">
								<input id="discountDateFiniInput"
									class="form-control form-control-sm" type="date"
									name="discount_timefini" value="${Map_sales_form.timefini }" />
							</div>
						</div>
					</div>
					<%--圖片上傳區 --%>
					<div class="row align-items-center">
						<div class="col-4">
							<figure class="figure w-100">
								<img id="img1"
									src='/salesPics/${image }'
									class="figure-img img-fluid rounded" style="object-fit: cover"
									alt="圖片預覽區">
								<figcaption class="figure-caption text-center">預覽圖片</figcaption>
							</figure>
						</div>
						<div class="col-8 ">
							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text" id="inputGroupFileAddon01">上傳圖片</span>
								</div>
								<div class="custom-file">
									<input type="file" class="custom-file-input " id="imgUpload01"
										accept="image/gif, image/jpeg, image/png, image/bmp, image/jpg"
										onchange="xmTanUploadImg(this)"
										aria-describedby="inputGroupFileAddon01" name="myFiles">
									<label class="custom-file-label" for="inputGroupFile01"
										id="imgUpload01Lab"></label>
								</div>
							</div>
						</div>
					</div>
					<div class="row align-items-center">
						<div class="col-sm-4 col-form-label text-right">
							<span class="align-baseline">是否上架：</span>
						</div>
						<div class="col-sm-8 ">
							<div
								class="custom-control custom-radio custom-control-inline align-baseline">
								<input type="radio" id="itemStatusYes" name="commodity_onsale"
									class="custom-control-input" value="Y" checked> <label
									class="custom-control-label" for="itemStatusYes">是</label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input type="radio" id="itemStatusNo" name="commodity_onsale"
									class="custom-control-input" value="N"
									${Map_sales_form.onsale_no }> <label
									class="custom-control-label" for="itemStatusNo">否</label>
							</div>
						</div>
					</div>

					<h6 id="updateSuccess" class="form-text text-muted text-right">${updateSuccess }</h6>
					<div class="row">
						<div class="col-sm text-right">
							<button class="btn btn-outline-info my-3" type="submit">修改商品資料</button>
							<button type="button" class="btn btn-outline-danger my-3"
								onclick="location.href='<c:url value="/shop/sales"/>'">返回列表</button>
						</div>

					</div>
				</div>

				<div class="col"></div>

			</div>
		</form>
	</div>

	<c:import url="/FooterSec" />
</body>
<script>
	var testParam = {"discount_type_2" : ${Map_sales_form.discount_type_2 }, 
			"discount_type_3" : ${Map_sales_form.discount_type_3 }};

	if (testParam.discount_type_2 == true) {
		$("#discountTypePercent").attr("checked", true);
	} else if (testParam.discount_type_3 == true) {
		$("#discountTypePrice").attr("checked", true);
	}

	$("input:not(.btn)").attr("readonly", true).attr('disabled', true);
</script>
</html>