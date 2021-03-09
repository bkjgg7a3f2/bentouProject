<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<body>
	<div class="container " style="margin-bottom: 45pt;">
		<div class="row my-md-4">
			<div class="col"></div>
			<div class="col-9 p-4 rounded-lg border border-dark"
				style="border-color: #000066;">
				<div class="row mt-4">
					<div id="table1" class="col">
						<table
							class="table table-sm table-bordered table-hover table-light text-center"
							style="background-color: #FFB400;">
							<thead>
								<tr>
									<th scope="col">#</th>
									<th scope="col">品項</th>
									<th scope="col">價格</th>
									<th scope="col">飲食類別</th>
								</tr>
							</thead>
							<tbody id="tbody1"></tbody>
						</table>
					</div>
					<div id="table2" class="col ">
						<table
							class="table table-sm table-bordered table-hover table-light text-center"
							style="background-color: #FFB400;">
							<thead>
								<tr>
									<th scope="col">#</th>
									<th scope="col">品項</th>
									<th scope="col">價格</th>
									<th scope="col">飲食類別</th>
								</tr>
							</thead>
							<tbody id="tbody2"></tbody>
						</table>
					</div>
				</div>

				<form id="shoppingCartForm"
					action="<c:url value="/cstmr/shoppingcart/add/${key }" />"
					method="post">
					<div class="row">
						<div class="col-sm">
							<div class="form-group row my-3">
								<label for="shopItem" class="col-sm-2 col-form-label text-right">選擇餐點：</label>
								<div class="col-sm-10">
									<select id="shopItem" class="custom-select"></select>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6 align-right">
							<figure id="figZone" class="figure text-right"
								style="height: 150px;">
								<img id="figPicPre"
									src="<%=request.getContextPath()%>/resources/images/supplyPics/default.jpg"
									class="figure-img img-fluid rounded h-100 invisible"
									style="height: 100%; object-fit: cover;">
								<figcaption id="figCap" class="figure-caption"></figcaption>
							</figure>

							<figure id="figZone2" class="figure text-right d-none"
								style="height: 150px;">
								<img id="figPic"
									src="<%=request.getContextPath()%>/resources/images/supplyPics/default.jpg"
									class="figure-img img-fluid rounded h-100 invisible"
									style="height: 100%; object-fit: cover;">
								<figcaption id="figCap2" class="figure-caption"></figcaption>
							</figure>

						</div>
						<div class="col-sm-6">
							<div class="form-group row text-right">
								<label for="number-input" class="col-8 col-form-label-sm">訂購份數：</label>
								<div class="col-4">
									<input id="itemNum" class="form-control-sm w-100" type="number"
										name="order_quantity" value="0" id="number-input">
								</div>
							</div>
							<div class="row">
								<div class="col text-right">
									<h6 id="singlePrice">
										<small id="shoppingcartMsg" class="text-danger"></small>餐點小計：<span
											id="pricePer1" class="text-danger">0</span>元
									</h6>
								</div>
							</div>
							<div class="row">
								<div class="col text-right">
									<small id="addCartMsg" class="text-info"></small>
									<button id="submitBtn" class="btn btn-danger" type="button"
										value="submit" disabled>加入購物車</button>
								</div>
							</div>
							<input id="hiddenInput1" type="hidden" name="commodity_id">
						</div>
					</div>
				</form>

			</div>
			<div class="col-2">
				<div class="card">
					<img id="shopPics" src="/supplyPics/${ supply_image }"
						class="card-img-top" alt="...">
					<div class="card-body">
						<h5 class="card-title text-center">消費概況</h5>
						<p class="card-text">
							小計：<br> <span id="shoppingPrice">${sum }</span>元<br>
						</p>
						<p class="card-text">
							最低外送金額：<br> <span id="limitPrice" class="text-danger">${limit }</span>
							元
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		var JSON_supply_sales =
	<%=request.getAttribute("JSON_supply_sales")%>
		;
		var picUrl = "/supplyPics/";
		var picUrlitem = "/salesPics/";
		var itemNum = JSON_supply_sales.length; //取得產品筆數
		var tbodyStr = [ "tbody1", "tbody2" ]; //準備寫入表格的tbody之id
		$("#table2").removeClass("d-none"); //初始化
		var realPrice = [];//儲存打折後價格的陣列

		//產品不足4筆時只開啟表格一
		if (itemNum < 4) {
			tbodyStr[1] = tbodyStr[0];//設定輸入tbody參數
			$("#table2").addClass("d-none");
		};
		showItems(itemNum, tbodyStr, JSON_supply_sales) //產生table跟selector

		// 改變選擇商品及數量時執行程式
		$("#itemNum").change(function() {
			pickBentou();
		});
		$("#shopItem").change(function() {
			pickBentou();
			var k = $(this).val();
			console.log(k)
			if (k !== "no") {
				swithchPics("figPic", k);
				$("#figZone").addClass("d-none");
				$("#figZone2").removeClass("d-none");
			} else {
				$("#figZone").removeClass("d-none");
				$("#figZone2").addClass("d-none");
			}
		});

		//滑到tr標籤上改變滑鼠樣式與預覽圖片
		$("tr[name='shopItems']").hover(function() {
			$(this).css('cursor', 'pointer');
			var k = $(this).attr("id");
			swithchPics("figPicPre", k);
		}, function() {
			$(this).css('cursor', 'auto');
			$("#figPicPre").addClass("invisible");
		})

		//直接點擊菜單改變Select選項與預覽圖
		$("tr[name='shopItems']").click(function() {
			var k = $(this).attr("id");
			$("#shopItem").val(k);
			pickBentou();
			swithchPics("figPic", k);
			$("#figZone").addClass("d-none");
			$("#figZone2").removeClass("d-none");
		})

		//按下按鈕後將數值設定進hidden input內
		$("#submitBtn").click(function() {
			var k = $("#shopItem").val();
			var itemId = JSON_supply_sales[k].commodity_id;

			$("#hiddenInput1").val(itemId);
		});
		//圖片無法載入時已預設圖片替代
		$("#shopPics").one("error", function(e) {
			$(this).attr("src", picUrl + "default.jpg");
		});

		//選擇不同的產品自動產生預覽圖片

		//產品表格生成程式
		function showItems(itemNum, tbodyNames, itemInfo) {
			//itemNum 		商品筆數
			//tbodyNames 輸入tbody的字串名
			//itemInfo			商品清單

			//根據資料筆數決定表格產生陣列起終點
			var tableBreak = Math.floor(itemNum / 2);
			//中斷點

			$("#" + tbodyNames[0]).empty(); //清除原有資料
			$("#" + tbodyNames[1]).empty(); //清除原有資料
			$("#shopItem").empty(); //清除原有資料
			var k = 0;
			for (j = 0; j < tbodyStr.length; j++) {
				//for loop 使用參數
				var startpt = [ 0, tableBreak ];
				var endpt = [ tableBreak, itemNum ];
				for (i = startpt[j]; i < endpt[j]; i++) {
					var innerTdStr = ""; //準備寫入的td字串
					var tdNum = k + 1; //顯示的商品序號
					var innerOptTdStr = "";//準備寫入selector option的字串
					var price;//判斷用的單價字串
					var pricePer1 = ""; //準備單價字串
					const dateIni = Date.parse(itemInfo[k].discount_timeini);//折扣開始時間
					const dateFin = Date.parse(itemInfo[k].discount_timefini);//折扣結束時間
					const dateNow = Date.now(itemInfo[k].discount_timefini);//目前時間

					//折扣計算
					switch (itemInfo[k].commodity_discount_type) {
					case 1:
						price = itemInfo[k].commodity_price;
						break;
					case 2:
						price = itemInfo[k].commodity_price
								* itemInfo[k].commodity_discount_price / 100;
						break;
					case 3:
						price = itemInfo[k].commodity_price
								- itemInfo[k].commodity_discount_price;
						break;
					default:
						price = itemInfo[k].commodity_price;
					}
					if (dateNow > dateIni && dateNow < dateFin) {
						pricePer1 = "<span id=\"realPrice_"+k+"\" class=\"text-dark\" >"
								+ parseInt(price, 10) + "</span>";
						pricePer1 = pricePer1
								+ "<span class=\"badge badge-pill badge-danger\">折扣中</span>";
					} else {
						price = itemInfo[k].commodity_price;
						pricePer1 = parseInt(price, 10);
					}
					realPrice[k] = parseInt(price, 10);

					//td字串
					innerTdStr = "<th scope=\"row\">" + tdNum + "</th>"
							+ "<td >" + itemInfo[k].commodity_name + "</td>"
							+ "<td >" + pricePer1 + "</td>" + "<td >"
							+ itemInfo[k].vegan + "</td>";
					//寫入table
					$("#" + tbodyNames[j]).append(
							"<tr id=\""+k+"\" name=\"shopItems\">" + innerTdStr
									+ "</tr>");

					//option字串
					innerOptTdStr = "<option  value="+k+">"
							+ itemInfo[k].commodity_name + "，單價："
							+ realPrice[k] + "元</option>";
					//寫入selector
					$("#shopItem").append(innerOptTdStr);

					k++;

				}
			}
			$("#shopItem").prepend(
					"<option value=\"no\" selected>請選擇餐點</option>");
		}

		//便當種類與數量更改時執行之script
		function pickBentou() {
			var itemNum = $("#itemNum").val();
			if (itemNum < 0) {
				$("#itemNum").val("0");
			} else if (itemNum >= 0) {
				if ($("#shopItem").val() === "no") {
					$("#shoppingcartMsg").text("請選擇餐點！");
				} else {
					$("#shoppingcartMsg").empty();
					var k = $("#shopItem").val();
					var sum = realPrice[k] * parseInt($("#itemNum").val(), 10);

					$("#pricePer1").html(sum);
				}
			}
		}
		//圖片切換程式
		function swithchPics(picID, k) {
			$("#" + picID).removeClass("invisible").attr("src",
					picUrlitem + JSON_supply_sales[k].image).one("error",
					function(e) {
						$("#" + picID).attr("src", picUrlitem + "default.jpg");
					});
		};

		$("input").attr("autocomplete", "off");
	</script>
</body>

</html>