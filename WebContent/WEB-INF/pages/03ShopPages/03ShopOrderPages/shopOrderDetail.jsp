<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>訂單明細-${Map_cstmr_order_data.order_id }</title>
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
		<div class="row">
			<div class="col"></div>
			<div class="col-9 p-4">
				<div class="row ">
					<div id="table1" class="col">
						<table
							class="table table-sm table-bordered table-striped table-light text-center"
							style="background-color: #6eeb83;">
							<caption class="text-dark">
								您與 <span class="text-danger font-weight-bold">${Map_cstmr_order_data.cstmr_name}</span>
								的訂單
							</caption>
							<thead>
								<tr>
									<th scope="row" colspan="4">訂單概要</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th scope="row">訂單編號</th>
									<td class="text-left" scope="col">${Map_cstmr_order_data.order_id }</td>
									<th scope="row">訂購日期</th>
									<td class="text-left" scope="col">${Map_cstmr_order_data.order_date }</td>
								</tr>
								<tr>
									<th scope="row">取貨人</th>
									<td class="text-left">${Map_cstmr_order_data.order_cstmr_name}</td>
									<th scope="row">連絡電話</th>
									<td class="text-left">${Map_cstmr_order_data.order_ph}</td>
								</tr>
								<tr>
									<th scope="row">取貨人統一編號</th>
									<td class="text-left">${Map_cstmr_order_data.cstmr_conumber}</td>
									<th scope="row">取貨人發票抬頭</th>
									<td class="text-left">${Map_cstmr_order_data.cstmr_invoice}</td>
								</tr>
								<tr>
									<th scope="row">取貨地址</th>
									<td class="text-left" colspan="3">${Map_cstmr_order_data.order_address}</td>
								</tr>
								<tr>
									<th scope="row">取貨時間</th>
									<td class="text-left" colspan="3">${Map_cstmr_order_data.expected_arrivetime}</td>
								</tr>
							</tbody>
						</table>
						<hr class="my-4" />
						<table
							class="table table-sm table-bordered table-striped table-light text-center"
							style="background-color: #ffb400;">
							<caption class="text-dark">訂單明細</caption>
							<thead>
								<tr>
									<th scope="col">#</th>
									<th scope="col">品項</th>
									<th scope="col">數量</th>
									<th scope="col">單價</th>
									<th scope="col">總金額</th>
									<th scope="col">訂單狀態</th>
									<th scope="col">訂單控制選項</th>
								</tr>
							</thead>
							<tbody id="tbody1"></tbody>
						</table>
					</div>
				</div>

			</div>
			<div class="col-2 pt-4 text-center">
				<a class="btn btn-danger mb-2" href="<c:url value="/shop/orders" />"
					role="button">返回訂單紀錄</a>
			</div>
		</div>
	</div>
	<c:import url="/FooterSec" />

	<script>
		var JSON_Supplydetails =
	<%=request.getAttribute("JSON_cstmr_details")%>
		;

		var detailsNum = JSON_Supplydetails.length; //取得訂單筆數
		var tbodyStr = "tbody1"; //準備寫入表格的tbody之id
		const dateNow = Date
				.now("${Map_cstmr_order_data.expected_arrivetime }"); //目前時間
		const dateFin = Date
				.parse("${Map_cstmr_order_data.expected_arrivetime }");//預計取貨時間
		showOrders(detailsNum, tbodyStr, JSON_Supplydetails);

		//產品表格生成程式
		function showOrders(detailsNum, tbodyName, orderInfo) {
			//detailsNum 		明細筆數
			//tbodyNames 		輸入tbody的字串名
			//orderInfo			明細
			$("#" + tbodyName).empty(); //清除原有資料
			for (i = 0; i < detailsNum; i++) {
				var innerTdStr = ""; //準備寫入的td字串
				var btnStr = ""; //準備產生接受訂單與拒絕訂單按鈕的字串
				var urlForm = "check/${Map_cstmr_order_data.order_id }";//按鈕控制器連結

				var pricesum = orderInfo[i].commodity_price
						* orderInfo[i].order_quantity;
				//td字串
				innerTdStr = "<th scope=\"row\">" + (i + 1) + "</th>" + "<td>"
						+ orderInfo[i].commodity_name + "</td>" + "<td >"
						+ orderInfo[i].order_quantity + "</td>" + "<td>"
						+ orderInfo[i].commodity_price + "</td>" + "<td>"
						+ pricesum + "</td>" + "<td>"
						+ orderInfo[i].order_confirm + "</td>";
				//按鈕字串
				btnStr = "<td ><form action="
						+ urlForm
						+ "/"
						+ orderInfo[i].key
						+ " method=\"post\" onsubmit=\"return chk()\"><input class=\"btn btn-primary btn-sm\" onclick=\"agree()\" type=\"submit\" name=\"submit\" value=\"接受\"/>"
						+ "<input class=\"btn btn-danger btn-sm\" onclick=\"deny()\"  type=\"submit\" name=\"submit\" value=\"拒絕\"/></td>";
				//寫入table
				$("#" + tbodyName).append(
						"<tr id=\"shopDetail"+i+"\" name=\"orderItems\">"
								+ innerTdStr + btnStr + "</tr>");
				if (orderInfo[i].order_confirm !== "未確認" || dateNow > dateFin) {
					$("#shopDetail" + i).find(".btn").attr("disabled", true);
				}

			}
		}

		//訂單明細確認
		var msg = "";
		function agree() {
			msg = "決定後便無法修改，確定 接受 此訂單？";
		}

		function deny() {
			msg = "決定後便無法修改，確定 拒絕 此訂單？";
		}

		function chk() {
			if (confirm(msg)) {
				return true;
			} else {
				return false;
			}
		}
	</script>

</body>
</html>