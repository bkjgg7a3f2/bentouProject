<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>訂單基本資料表</title>
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap.min.css"/>" />
<link
	href="https://fonts.googleapis.com/css?family=Noto+Sans+TC&display=swap"
	rel="stylesheet">

</head>
<style>
body {
	font-family: 'Noto Sans TC', sans-serif;
}
</style>


<body>
	<c:import url="/TopSec" />
	<div class="container my-4" style="margin-bottom: 45pt;">
		<form action="<c:url value="/cstmr/shoppingcart/confirm_fail" />"
			method="post">
			<div class="row">
				<div class="col-8 p-1">
					<div class="form-group row ">
						<div class="col-4"></div>
						<h5 class="col-sm-6 rounded-pill  text-dark"
							style="text-align: center; background-color: #1be7ff">取貨資料</h5>
						<div class="col"></div>
					</div>
					<div class="form-group row">
						<label for="order_cstmr_name"
							class="col-sm-6 col-form-label text-right">取貨聯絡人：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="order_cstmr_name"
								name="cstmr_contact" value=${Map_cstmr_data.cstmr_contact }>
						</div>
					</div>
					<div class="form-group row">
						<label for="order_ph" class="col-sm-6 col-form-label text-right">聯絡人電話：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="order_ph"
								name="order_ph" maxlength="10" value=${Map_cstmr_data.order_ph }>
						</div>
					</div>
					<div class="form-group row">
						<label for="order_address"
							class="col-sm-6 col-form-label text-right">取貨地址：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="order_address"
								name="order_address" value=${Map_cstmr_data.order_address }>
						</div>
					</div>
					<div class="form-group row">
						<label for="custName" class="col-sm-6 col-form-label text-right">預計取貨時間：</label>
						<div class="col-sm-6">
							<div class="input-group">
								<input id="dateForm" type="date" aria-label="dateVal"
									class="form-control"> <input id="timeForm" type="time"
									aria-label="timeVal" class="form-control" step="1800">
								<input id="hiddenInputTime" type="hidden"
									name="expected_arrivetime">
							</div>
							<%-- value=${Map_cstmr_data.expected_arrivetime }> --%>
						</div>
					</div>
					<div class="form-group row">
						<label for="custNum" class="col-sm-6 col-form-label text-right">統一編號：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="custNum"
								name="cstmr_conumber" value=${Map_cstmr_data.cstmr_conumber }>
						</div>
					</div>
					<div class="form-group row">
						<label for="cstmr_name" class="col-sm-6 col-form-label text-right">發票抬頭：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="cstmr_name"
								name="cstmr_invoice" value=${Map_cstmr_data.cstmr_invoice }>
							<h6 id="orderInfoMsg" class="form-text text-danger text-right">${OrderInfoMsg }</h6>
						</div>
					</div>

				</div>

				<div class="col pt-4 text-center">
					<button class="btn btn-primary btn-warning mb-2" type="submit">馬上結帳</button>
					<br /> <a class="btn btn-primary mb-2"
						href="<c:url value="/cstmr/shoppingcart" />" role="button">回上一頁</a>
				</div>
			</div>
		</form>
		<div class="col-9 p-4">
			<div class="row ">
				<div id="table1" class="col">
					<table
						class="table table-sm table-bordered table-hover table-light text-center"
						style="background-color: #6EEB83;">
						<thead>
							<tr>
								<th scope="col">#</th>
								<th scope="col">品項</th>
								<th scope="col">店家</th>
								<th scope="col">單價</th>
								<th scope="col">數量</th>
								<th scope="col">小計</th>
							</tr>
						</thead>
						<tbody id="tbody1"></tbody>
					</table>
				</div>
			</div>

		</div>
	</div>

	<c:import url="/FooterSec" />
	<script>
		
	</script>
</body>



<script>
	var JSON_shoppingcart =
<%=request.getAttribute("JSON_shoppingcart_confirm")%>
	;

	var arrayTab = []; //建立table對應的array,儲存數量[0]與小計[1]
	var itemNum = JSON_shoppingcart.length; //取得訂單筆數
	var tbodyStr = "tbody1"; //準備寫入表格的tbody之id

	showItems(itemNum, tbodyStr, JSON_shoppingcart);

	//產品表格生成程式
	function showItems(itemNum, tbodyName, itemInfo) {
		//itemNum 		商品筆數
		//tbodyNames 輸入tbody的字串名
		//itemInfo			訂單清單
		var priceTotal = 0; //總價計算
		var itemsNumTotal = 0; //商品總數計算
		var urlForm = "shoppingcart/update_or_delete";//各筆交易虛擬路徑不含key值

		$("#" + tbodyName).empty(); //清除原有資料
		for (i = 0; i < itemNum; i++) {
			var innerTdStr = ""; //準備寫入的td字串
			var pricePer1 = ""; //準備單價字串

			var quantityStr = ""; //準備修改數字欄位按鈕
			const dateIni = Date.parse(itemInfo[i].discount_timeini);//折扣開始時間
			const dateFin = Date.parse(itemInfo[i].discount_timefini);//折扣結束時間
			const dateNow = Date.now(itemInfo[i].discount_timeini);//目前時間
			var tdNum = i + 1; //顯示的商品序號

			//數字輸入欄位字
			quantityStr = "<input id=\"itemNum"+itemInfo[i].key+"\" class=\"form-control-sm text-right w-100\" style=\"max-width:50pt\"  type=\"number\"value="+itemInfo[i].quantity+" name=\"number-input\">";

			//折扣計算
			switch (itemInfo[i].discount_type) {
			case 1:
				price = itemInfo[i].original_price;
				break;
			case 2:
				price = itemInfo[i].original_price
						* itemInfo[i].discount_number / 100;
				break;
			case 3:
				price = itemInfo[i].original_price
						- itemInfo[i].discount_number;
				break;
			default:
				price = itemInfo[i].original_price;
			}
			if (dateNow > dateIni && dateNow < dateFin) {
				pricePer1 = "<span name=\"pricePer1\" >" + parseInt(price, 10)
						+ "</span>";
				pricePer1 = pricePer1
						+ "<span class=\"badge badge-pill badge-warning\">折扣中</span>";
			} else {
				price = itemInfo[i].original_price;
				pricePer1 = "<span name=\"pricePer1\" >" + parseInt(price, 10)
						+ "</span>";
			}

			itemSum = parseInt(price, 10) * parseInt(itemInfo[i].quantity, 10);

			//td字串
			innerTdStr = "<th scope=\"row\">" + tdNum + "</th>" + "<td >"
					+ itemInfo[i].commodity_name + "</td>" + "<td >"
					+ itemInfo[i].supply_name + "</td>" + "<td >" + pricePer1
					+ "</td>" + "<td >" + itemInfo[i].quantity + "</td>"
					+ "<td name=\"priceSum\" >" + itemSum + "</td>";

			//寫入table
			$("#" + tbodyName).append(
					"<tr id=\""+i+"\" name=\"shopItems\">" + innerTdStr
							+ "</tr>");
			arrayTab[i] = [ itemInfo[i].quantity, itemSum ];
			priceTotal = priceTotal + itemSum;
			itemsNumTotal = itemsNumTotal + itemInfo[i].quantity;
		}
		$("#" + tbodyName).append(
				"<tr><td colspan=\"4\"></td><td colspan=\"2\">總共：<span name=\"itemsNumTotal\">"
						+ itemsNumTotal + "</span>項</td></tr>");
		$("#" + tbodyName).append(
				"<tr><td colspan=\"4\"></td><td colspan=\"2\">總計：<span name=\"priceTotal\">"
						+ priceTotal + "</span>元</td></tr>");
	}
	//日期改變後輸入hiddenInput內
	$("#dateForm , #timeForm").change(
			function() {
				$("#hiddenInputTime").val(
						$("#dateForm").val() + " " + $("#timeForm").val());
			})

	$("input").attr("autocomplete", "off");
</script>
</html>