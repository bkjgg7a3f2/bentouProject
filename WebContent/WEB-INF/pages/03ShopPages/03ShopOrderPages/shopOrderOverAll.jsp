<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>訂單紀錄</title>
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
			<div class="col p-4">
				<div class="row ">
					<div id="table1" class="col">
						<table
							class="table table-sm table-bordered table-hover table-light text-center"
							style="background-color: #6eeb83;">
							<caption class="text-dark">點擊訂單可察看明細</caption>
							<thead>
								<tr>
									<th scope="col">訂單編號</th>
									<th scope="col">聯絡人名稱</th>
									<th scope="col">預計<br>總額
									</th>
									<th scope="col">取貨地址</th>
									<th scope="col">預計到貨時間</th>
									<th scope="col">聯絡電話</th>
									<th scope="col">訂單狀態</th>
								</tr>
							</thead>
							<tbody id="tbody1"></tbody>
						</table>
					</div>
				</div>

				<div id="nothingToBill" class="jumbotron my-4 d-none">
					<h1 class="display-4">尚無訂單</h1>
				</div>

			</div>
			
		</div>
	</div>
	<c:import url="/FooterSec" />


	<script>
		var JSON_SupplyOrders =
	<%=request.getAttribute("JSON_supply_orders")%>
		;

		var orderNum = JSON_SupplyOrders.length;
		//取得訂單筆數
		var tbodyStr = "tbody1"; //準備寫入表格的tbody之id

		showOrders(orderNum, tbodyStr, JSON_SupplyOrders);

		//產品表格生成程式
		function showOrders(orderNum, tbodyName, orderInfo) {
			//orderNum 		訂單筆數
			//tbodyNames 輸入tbody的字串名
			//orderInfo		訂單清單
			$("#" + tbodyName).empty(); //清除原有資料
			for (i = 0; i < orderNum; i++) {
				var innerTdStr = ""; //準備寫入的td字串
				var tdNum = orderInfo[i].order_id; //顯示的商品序號
				const nowTime=new Date(); //現在時間
				const expTime=new Date(orderInfo[i].expected_arrivetime); //預計到達時間


				//td字串
				innerTdStr = "<th scope=\"row\">" + tdNum + "</th>" + "<td >"
						+ orderInfo[i].order_cstmr_name + "</td>" + "<td >"
						+ orderInfo[i].price_sum + "</td>" + "<td >"
						+ orderInfo[i].order_address + "</td>" + "<td >"
						+ orderInfo[i].expected_arrivetime + "</td>" + "<td>"
						+ orderInfo[i].order_ph + "</td>" + "<td>"
						+ orderInfo[i].confirm + "</td>";

				//寫入table
				$("#" + tbodyName).append(
						"<tr id=\""+i+"\" name=\"orderItems\">" + innerTdStr
								+ "</tr>");
				//改顏色
				//過期
				if(nowTime>expTime || orderInfo[i].confirm =="顧客取消"){
					$("#"+i).addClass("table-secondary")
				}
				//拒絕
				if(orderInfo[i].confirm =="未確認"){
					$("#"+i).addClass("bg-warning")
				}
			}
		}

		//滑到tr標籤上改變滑鼠樣式，點擊進入超連結
		$("tr[name='orderItems']").hover(function() {
			$(this).css('cursor', 'pointer');
		}, function() {
			$(this).css('cursor', 'auto');
		}).click(function() {
			var k = $(this).attr("id");
			var urlForm = "orders" + "/" + JSON_SupplyOrders[k].order_id;
			//各筆交易虛擬路徑
			window.location.href = urlForm;
			return false;
		})
		
		if(JSON_SupplyOrders.length==0){
			$("#nothingToBill").removeClass("d-none");
			$("#table1").addClass("d-none");
		}
	</script>

</body>



<script>
	
</script>
</html>