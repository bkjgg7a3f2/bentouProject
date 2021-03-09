<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品列表</title>
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
.modal {
  padding: 0 !important; // override inline padding-right added from js
}
.modal .modal-dialog {
  width: 100%;
  max-width: none;
  height: 100%;
  margin: 0;
}
.modal .modal-content {
  height: 100%;
  border: 0;
  border-radius: 0;
}
.modal .modal-body {
  overflow-y: auto;
}
</style>

<body>
	<c:import url="/TopSec" />
	<div class="container my-4" style="margin-bottom: 45pt;">
		<div class="row">
			<div class="col"></div>
			<div class="col-9">
				<div class="row ">
					<div id="itemsView" class="col"></div>
				</div>
				<div class="row ">
					<div class="col">
						<nav id="btmPageShift" class="my-5"></nav>
					</div>
				</div>

				<div id="nothingToBuy" class="jumbotron my-4 d-none">
					<h1 class="display-4">尚無相關品項</h1>
					<p id="welcome" class="lead">快來新增產品吧!</p>
				</div>

			</div>
			<div class="col">
				<form action="<c:url value="/shop/sales/form/0" />" method="post">
					<button class="btn btn-outline-info btn-block my-3" type="submit">新增商品</button>
				</form>
				<button id="preViewBtn" type="button"
					class="btn btn-outline-primary btn-block my-3">預覽頁面</button>
			</div>
		</div>
	</div>
	<c:import url="/FooterSec" />

	<!-- Modal -->
	<div class="modal fade bd-example-modal-xl" id="preViewZone"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-xl" role="document">
			<div class="modal-content">
				<div class="modal-header bg-danger">
					<h5 class="modal-title text-white" id="picUpdateTitle">消費者頁面預覽</h5>
					<button type="button" class="close text-white" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div id="preViewShow" class="modal-body"></div>
				<div class="modal-footer bg-light">
					<button type="button" class="btn btn-danger" data-dismiss="modal">關閉預覽</button>
				</div>
			</div>
		</div>
	</div>

</body>

<script>
	var JSON_sales_supply_id = 
	<%=request.getAttribute("JSON_sales_supply_id")%>;
	
	var shopIItemsNum = 2;//設定一頁顯示商品數
	var pageNum = Math.ceil(JSON_sales_supply_id.length/shopIItemsNum);//取得回傳的頁面數
	var pageArr = new Array(pageNum);//產生頁數陣列
	var nowPage = 1;//起始頁面為第一頁
	
	// 寫入底部頁面轉換標籤
	// 取得navBar資料
	$("#btmPageShift")
			.html(
					function() {
						var prev = "<ul id=\"ulPageShift\" class=\"pagination justify-content-center\"></ul>";
						return prev;
					});

// 	取得產生的ul資料，寫入頁面頁數選項
	$("#ulPageShift").html(function() {
		var innerTxt = "";
		$.each(pageArr,function(pNum, notImp) {
			x = pNum + 1;
			innerTxt = innerTxt+ "<li id=\"liPage"+x+"\" name=\"liPageNum\" class=\"page-item\" value=\""+x+" \"><a class=\"page-link\" >"
													+ x + "</a></li>";})
			return innerTxt
		})
	//上一頁按鈕
	$("#ulPageShift").prepend(
					"<li id=\"prePage\" class=\"page-item disabled\"><a class=\"page-link\" tabindex=\"-1\" aria-disabled=\"true\">&laquo;</a></li>")
	//下一頁按鈕
	$("#ulPageShift").append(
					"<li id=\"nextPage\"class=\"page-item\"><a class=\"page-link\" tabindex=\"-1\" aria-disabled=\"true\"  >&raquo;</a>")
	//預設停在第一頁
	$("#liPage1").addClass("active");
	
	//檢查上一頁與下一頁按鈕功能
	checkpage();
	//產生商品畫面
	showShop(nowPage,shopIItemsNum,JSON_sales_supply_id);
	
	
	//滑到ul標籤上改變滑鼠樣式
	$("#ulPageShift").hover(function() {
		$(this).css('cursor', 'pointer');
	}, function() {
		$(this).css('cursor', 'auto');
	})

	//上一頁功能
	$('#prePage').click(
			function() {
				if (!$(this).hasClass("disabled")) {
					nowPage = nowPage - 1;
					$("#liPage" + nowPage).siblings().removeClass("active").end().addClass("active");
					checkpage();
					showShop(nowPage,shopIItemsNum,JSON_sales_supply_id);
				}
			})

	//下一頁按鈕
	$('#nextPage').click(
			function name() {
				if (!$(this).hasClass("disabled")) {
					nowPage = nowPage + 1;
					$("#liPage" + nowPage).siblings().removeClass("active").end().addClass("active");
					checkpage();
					showShop(nowPage,shopIItemsNum,JSON_sales_supply_id);
				}
			})

	//跳到指定頁面
	$('li[name="liPageNum"]').click(function() {
		$(this).siblings().removeClass("active").end().addClass("active");
		nowPage = $(this).val();
		checkpage();
		showShop(nowPage,shopIItemsNum,JSON_sales_supply_id);
	})

	//檢查上一頁與下一頁功能程式
	function checkpage() {
		//第一頁時取消上一頁功能
		if (nowPage === 1) {
			$("#prePage").addClass("disabled");
		} else {
			$("#prePage").removeClass("disabled");
		};
		//最後一頁時取消下一頁功能
		if (nowPage == pageNum) {
			$("#nextPage").addClass("disabled");
		} else {
			$("#nextPage").removeClass("disabled");
		};
	};
	//圖片不存在時以預設圖片代替
	
	
	//產生螢幕商家程式
	function showShop(pageNow,shopIItemsNum1page,shopItemsInfo){
		//設定商品圖片位置
		var urlShopPic="/salesPics/"
		//設定導入商店商品業的控制器路徑，控制器不含商家的key值
		var urlToItem="sales";
		//根據一頁顯示的商家數以及目前頁面決定本頁顯示商家的陣列起終點
		var startItems=pageNow*shopIItemsNum1page-shopIItemsNum1page; //起點
		var endItems=pageNow*shopIItemsNum1page-1;//終點
		var maxShop=shopItemsInfo.length;//最多登入的商家數
		
		$("#itemsView").empty();//清除原有資料
		
		for(i=startItems;i<=endItems;i++){
			var innerHTMLtxt="";	//準備寫入的html字串
			var innerImg="";			//準備圖片字串
			var cardbody="";			//準備內容字串
			var innerContex="";		//商家介紹字串
			var innerBtn="";			//按鈕超連結字串
			var discountType="否"//預設折扣狀態

			if(i<maxShop){
				//店家圖片字串
				innerImg="<div class=\"col-md-3\"  ><img name=\"items\" src="+urlShopPic+shopItemsInfo[i].image+" alt='So delicieous' class=\"card-img h-100\" style=\"object-fit: cover\" /></div>";
				//折扣狀況判斷
				const dateIni = Date.parse(shopItemsInfo[i].timeini);//折扣開始時間
				const dateFin = Date.parse(shopItemsInfo[i].timefini);//折扣結束時間
				const dateNow = Date.now(shopItemsInfo[i].timefini);//目前時間
				if (dateNow>dateIni && dateNow<dateFin ){
					discountType="是"
				}
				var innerText=[["品項原價："+shopItemsInfo[i].price],
					["品項類別(葷\素)：",shopItemsInfo[i].vegan],
					["折扣狀態：",discountType],
					["折扣起始時間：",shopItemsInfo[i].timeini],
					["折扣結束時間：",shopItemsInfo[i].timefini],
					["上架狀況：",shopItemsInfo[i].onsale],
					["最後更新時間：",shopItemsInfo[i].update_date]];
				var innerContex2="";
				for(j=0;j<innerText.length;j++){
					if(innerText[j][1]!=null){
						innerContex2=innerContex2+innerText[j][0]+innerText[j][1];
					}
					innerContex2=innerContex2+"<br/>";
				}
				//店家內容字串
				innerContex="<h5 class=\"card-title \">"+shopItemsInfo[i].name+"</h5> <p class=\"card-text\"><small>"+
				innerContex2+"</small></p>";
				
				//店家導向頁面字串
				innerBtn="<div class=\"card-footer mb-0 pb-0 bg-transparent border-secondary text-right\"> <button name=\"go2Shop\" type=\"button\" \class=\"btn btn-outline-danger btn-sm\" onclick=\"location.href='"+urlToItem+"/"+shopItemsInfo[i].key+"';\">詳細資料</button> </div>";
				
				//店家完整表格字串
				cardbody="<div class=\"col-md-9\"><div class=\"card-body\">"+innerContex+innerBtn+"</div> </div>";
	
				$("#itemsView").append("<div class=\"card mb-3\"  > <div class=\"row no-gutters\">"+innerImg+cardbody+"</div> </div>");
				$("img").one("error", function(e) {
					$(this).attr("src", "/salesPics/noPic.png");
				});
			}
		}		
	}
	
	if(JSON_sales_supply_id.length==0){
		$("#itemsView,#btmPageShift").empty();
		$("#nothingToBuy").removeClass("d-none");
	};
	$("#preViewBtn").click(function() {
			$.get("${pageContext.request.contextPath}/shop/view").done(function(data) {
			$("#preViewShow").html(data);
			$('#preViewZone').modal('show');
			});
		})
	</script>
</html>