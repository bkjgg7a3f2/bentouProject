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
							style="background-color: #1be7ff;">
							<caption class="text-dark">
								您與 <span class="text-danger font-weight-bold">${Map_cstmr_order_data.supply_name }</span>
								的訂單
							</caption>
							<thead>
								<tr>
									<th scope="col">訂單編號</th>
									<td scope="col">${Map_cstmr_order_data.order_id }</td>
									<th scope="col">訂購日期</th>
									<td scope="col">${Map_cstmr_order_data.order_date }</td>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th scope="row">取貨地址</th>
									<td colspan="3">${Map_cstmr_order_data.order_address }</td>
								</tr>
								<tr>
									<th scope="row">預計取貨時間</th>
									<td colspan="3">${Map_cstmr_order_data.expected_arrivetime }</td>
								</tr>
								<tr>
									<th scope="row">統一編號</th>
									<td colspan="3">${Map_cstmr_order_data.conumber }</td>
								</tr>
								<tr>
									<th scope="row">發票抬頭</th>
									<td colspan="3">${Map_cstmr_order_data.invoice }</td>
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
									<th scope="col">單價</th>
									<th scope="col">數量(份)</th>
									<th scope="col">總金額</th>
									<th scope="col">確認狀態</th>
								</tr>
							</thead>
							<tbody id="tbody1"></tbody>
						</table>
					</div>
				</div>

			</div>
			<div class="col-2 pt-4 text-center">
				<a class="btn btn-danger mb-2 btn-block" href="<c:url value="/cstmr/orders" />" role="button">返回訂單紀錄</a>
				<form action="<c:url value='/cstmr/orders/cancel/'/>" method="post" onsubmit="return cancelchk()">

					<button id="cancelBtn" type="submit"class=" btn btn-secondary btn-block text-white mb-2 d-none">取消訂單</button>
					<!-- 						//進這一頁的controller要加key值 -->
					<input type="hidden" name="order_id" value="${Map_cstmr_order_data.order_id }" />
				</form>
				<button id="commentBtn" type="submit" class=" btn btn-info btn-block text-white mb-2 d-none" onclick="time()">我要評論</button>
			</div>
		</div>
	</div>
	<c:import url="/FooterSec" />

	<!-- 彈出視窗 -->
	<div class="modal fade" id="commentDiv" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<form action="<c:url value='/cstmr/comment/write'/>" method="post" onsubmit="return chk()">
					<div class="modal-header">
						<h5 class="modal-title text-dark" id="exampleModalCenterTitle">
							請輸入評論<span class="badge badge-secondary">評論只能輸入一次</span>
						</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body align-center">
						<div class="form-group row px-3">
							<label for="custNum" class="col-sm-3 col-form-label text-right">訂單編號：</label>
							<div class="col-sm">
								<input name="order_id" type="text" readonly class="form-control-plaintext" id="custNum" value="${Map_cstmr_order_data.order_id}">
							</div>
						</div>
						<div class="form-group row px-3">
							<label for="custRank"
								class="col-sm-3 pt-3 col-form-label text-right border-top">評分：</label>
							<div class="col-sm-2 border-top">
								<input name="cstmr_fraction" type="number" readonly
									class="form-control-plaintext pt-3 text-danger" min="0" max="5"
									value="3" id="custRankNum" />
							</div>
							<div class="col-sm-7 pt-3 align-middle border-top">
								<input type="range" class="custom-range form-control-range pt-1"
									min="1" max="5" id="custRank" value="3">
							</div>
						</div>
						<div class="form-group row px-3">
							<label for="custEvaluation"
								class="col-sm-3  pt-3 col-form-label text-right border-top">您的評論：</label>
							<div class="col-sm pt-3 border-top">
								<textarea name="cstmr_evaluation" class="form-control" rows="3"
									id="custEvaluation" placeholder="請輸入您的評論" /></textarea>
							</div>

						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">送出評論</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script>
		var JSON_cstmr_details =
	<%=request.getAttribute("JSON_cstmr_details")%>
		;
		var detailsNum = JSON_cstmr_details.length; //取得訂單筆數
		var supply_name = $
		{
			supply_name
		}; //供應商名字
		var tbodyStr = "tbody1"; //準備寫入表格的tbody之id
		var notConfirmed = "true";//判斷訂單是否未被確認
		var hasConfirmed = "false";//判斷是否有單一訂單被確認
		const dateNow = Date
				.now("${Map_cstmr_order_data.expected_arrivetime }"); //目前時間
		const dateFin = Date
				.parse("${Map_cstmr_order_data.expected_arrivetime }");//預計取貨時間
		showOrders(supply_name, detailsNum, tbodyStr, JSON_cstmr_details);

		//產品表格生成程式
		function showOrders(supplyName, detailsNum, tbodyName, orderInfo) {
			//supplyName		廠商名稱
			//detailsNum 		明細筆數
			//tbodyNames 		輸入tbody的字串名
			//orderInfo			明細
			var urlForm = "${pageContext.request.contextPath}/cstmr/orders/cancel/";
			$("#" + tbodyName).empty(); //清除原有資料
			for (i = 0; i < detailsNum; i++) {
				var innerTdStr = ""; //準備寫入的td字串
				var priceTotal = parseInt(orderInfo[i].order_quantity, 10)
						* parseInt(orderInfo[i].commodity_price, 10);
				//商品總價

				//td字串
				innerTdStr = "<th scope=\"row\">" + orderInfo[i].key + "</th>"
						+ "<td>" + orderInfo[i].commodity_name + "</td>"
						+ "<td>" + orderInfo[i].commodity_price + "</td>"
						+ "<td >" + orderInfo[i].order_quantity + "</td>"
						+ "<td >" + priceTotal + "</td>" + "<td>"
						+ orderInfo[i].order_confirm + "</td>";

				//寫入table
				$("#" + tbodyName).append(
						"<tr id=\""+i+"\" name=\"orderItems\">" + innerTdStr
								+ "</tr>");
				//交易狀態確認
				if (orderInfo[i].order_confirm !== "未受理") {
					notConfirmed = "false";
				}
				if (orderInfo[i].order_confirm == "已受理") {
					hasConfirmed = "true"
				}
			}
		}

		if (notConfirmed === "true") {
			if(dateNow<dateFin){
				$("#cancelBtn").removeClass("d-none");
			}		
		}
		if (hasConfirmed === "true") {
			$("#commentBtn").removeClass("d-none");
		}
		$("#custRank").change(function() {
			$("#custRankNum").val($(this).val())
		})

		//時間評估
		function time() {
			  var now = new Date();
			  var t = '${Map_cstmr_order_data.expected_arrivetime}';
			  var date = new Date(t);
			  var inter = parseInt((now.getTime() - date.getTime())/1000/60);
			  if(inter < 0){
				  alert("訂單還未完成無法評價")
	            }else{
	            	$('#commentDiv').modal('show')
	            }
		}
		
		var comment =
	<%=request.getAttribute("comment")%>
		;
		if (comment === false) {
			$("#commentBtn").attr("disabled", true);
		}
		
		//評論確認
		function chk() {
			var t = document.getElementById("custEvaluation").value;
			if (t.length == 0) {
				alert("請輸入評論");
				return false;
			} else {
				if (confirm("送出後便無法修改，是否確定送出評論？")) {
					alert("評論送出");
					return true;
				} else {
					return false;
				}
			}
		}
		
		//是否取消
		function cancelchk() {
			if (confirm("取消後便無法回復，確定取消整筆訂單？")) {
				return true;
			} else {
				return false;
			}
		}
		
		$("input").attr("autocomplete", "off");
	</script>
</body>
</html>