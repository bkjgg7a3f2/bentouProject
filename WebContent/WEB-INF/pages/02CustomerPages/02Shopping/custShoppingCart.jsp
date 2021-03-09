<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>購物車</title>
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
					<div id="somethingToBuy">
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
										<th scope="col">修改選項</th>
									</tr>
								</thead>
								<tbody id="tbody1"></tbody>
							</table>
						</div>

					</div>
					<div>
						<h6 id="msg" class="form-text text-left text-danger">${msg }</h6>
						<div id="supplyname"></div>
					</div>
				</div>
				<div id="nothingToBuy" class="jumbotron my-4 d-none">
					<h1 class="display-4">尚無欲購買品項</h1>
					<p id="welcome" class="lead">前往商家選購想要的商品，或是使用搜尋按鈕搜尋相關產品吧。</p>
					<hr class="my-4">
					<p class="d-none"></p>
					<a class="btn btn-primary btn-lg"
						href='<c:url value="/welcome/login" />' role="button">去逛逛</a>
				</div>
			</div>
			<div class="col-2 pt-4 text-center ">
				<a class="btn btn-warning  btn-block"
					href="<c:url value="/cstmr/search" />" role="button">繼續購物</a>
				<button id="payTheBill" type="button"
					class="btn btn-warning  btn-block"
					onclick="location.href = '<c:url value="/cstmr/shopping_undone" />'">馬上結帳</button>
				<a id="emptyCart" class="btn btn-primary  btn-block"
					href="<c:url value="/cstmr/shoppingcart/delete" />" role="button">清空購物車</a>

			</div>
		</div>
	</div>
	<c:import url="/FooterSec" />
	<script>
		var JSON_shoppingcart =
	<%=request.getAttribute("JSON_shoppingcart")%>
		;
		var arrayTab = []; //建立table對應的array,儲存數量[0]與小計[1]
		var itemNum = JSON_shoppingcart.length; //取得訂單筆數
		var tbodyStr = "tbody1"; //準備寫入表格的tbody之id

		//未達最低下標金額商家參數
		var JSON_supply_name = <%=request.getAttribute("JSON_supply_name")%>;//你媽的這根本沒東西是null
		
		if (JSON_supply_name !== null) {
			var num = JSON_supply_name.length;
		} else {
			var num = 0;
		}
		var divname = 'supplyname';//寫入的div名稱

		showItems(itemNum, tbodyStr, JSON_shoppingcart);//產生表格
		showsupply(num, divname, JSON_supply_name);//未達商家最低下標金額時顯示，JSON_supply_name是null，num是0，這東西根本不會執行

		//如未選購商品進入頁面則改變顯示內容
		if (JSON_shoppingcart.length == 0) {
			$("#somethingToBuy,#emptyCart").addClass("d-none");
			$("#nothingToBuy").removeClass("d-none");
			$("#payTheBill").attr("disabled", true);

		};
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
				var btnStr = ""; //準備產生修改與刪除按鈕的字串
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
				if (dateNow > dateIni && dateNow < dateFin) {//有在折扣期限pricePer1才會打折
					pricePer1 = "<span name=\"pricePer1\" >"
							+ parseInt(price, 10) + "</span>";
					pricePer1 = pricePer1
							+ "<span class=\"badge badge-pill badge-warning\">折扣中</span>";
				} else {//過了折扣期限price變回原價
					price = itemInfo[i].original_price;
					pricePer1 = "<span name=\"pricePer1\" >"
							+ parseInt(price, 10) + "</span>";
				}

				itemSum = parseInt(price, 10) * parseInt(itemInfo[i].quantity, 10);//總價

				//td字串
				innerTdStr = "<th scope=\"row\">" + tdNum + "</th>" + "<td >"
						+ itemInfo[i].commodity_name + "</td>" + "<td >"
						+ itemInfo[i].supply_name + "</td>" + "<td >"
						+ pricePer1 + "</td>" + "<td >" + quantityStr + "</td>"
						+ "<td name=\"priceSum\" >" + itemSum + "</td>";
				btnStr = "<td ><form action="+urlForm+"/"+ itemInfo[i].key+" method=\"post\"><input class=\"btn btn-warning btn-sm\" type=\"submit\" name=\"submit\" value=\"修改\">"
						+ "<input class=\"btn btn-danger btn-sm\" type=\"submit\" name=\"submit\" value=\"刪除\">"
						+ "<input type=\"hidden\" name=\"order_quantity\" value=\"\"></form></td >";
				//寫入table
				$("#" + tbodyName).append(
						"<tr id=\""+i+"\" name=\"shopItems\">" + innerTdStr
								+ btnStr + "</tr>");
				arrayTab[i] = [ itemInfo[i].quantity, itemSum ];//arrayTab[0]={3,165} arrayTab[1]={1,65} arrayTab[2]={1,100}
				priceTotal = priceTotal + itemSum;
				itemsNumTotal = itemsNumTotal + itemInfo[i].quantity;
			}
			$("#" + tbodyName).append(
					"<tr><td colspan=\"4\"></td><td colspan=\"3\">總共：<span name=\"itemsNumTotal\">"
							+ itemsNumTotal + "</span>項</td></tr>");
			$("#" + tbodyName).append(
					"<tr><td colspan=\"4\"></td><td colspan=\"3\">總計：<span name=\"priceTotal\">"
							+ priceTotal + "</span>元</td></tr>");
		}

		//數量更改時執行之script
		$("td").on(
				"change",
				"input[name='number-input']",
				function() {
					var itemNum = $(this).val();//取得改變後的數量
					var pricePer1 = $(this).parent().siblings().children(
							"span[name='pricePer1']").text(); //取得單價
					var thisID = $(this).parents("tr").attr("id"); //取得數量改變的行數id
					var priceTotal = 0;//總價
					var itemsNumTotal = 0;//總數

					if (itemNum < 1) {
						$(this).val("1");
					}
					//計算並更新小計欄位
					var sumNum = parseInt(pricePer1, 10)
							* parseInt($(this).val(), 10);
					$(this).parent().siblings("td[name='priceSum']").text(
							sumNum);

					//更新數量與小計矩陣
					arrayTab[thisID] = [ $(this).val(), sumNum ];

					//更新總計欄位
					for (i = 0; i < arrayTab.length; i++) {
						itemsNumTotal = itemsNumTotal
								+ parseInt(arrayTab[i][0], 10);
						priceTotal = priceTotal + parseInt(arrayTab[i][1], 10);
					}

					$("span[name='priceTotal']").text(priceTotal);
					$("span[name='itemsNumTotal']").text(itemsNumTotal);

				})

		//submit更改時執行之script
		$("td").on("click", ".btn", function() {
			var k = $(this).parents("tr").attr("id");
			var inputNum = $("#itemNum" + JSON_shoppingcart[k].key).val();
			$(this).siblings("input[name='order_quantity']").val(inputNum);
		})
		//顯示未達標商家的程式
		function showsupply(num, divname, list) {
			for (i = 0; i < num; i++) {
				$("#" + divname).append(
						"<h6 class='form-text text-right text-muted'>"
								+ list[i].supply_name + "</h6>");
			}
		}
	</script>
</body>

</html>