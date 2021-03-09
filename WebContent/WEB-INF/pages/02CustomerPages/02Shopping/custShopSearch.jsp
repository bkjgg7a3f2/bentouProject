<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商家展示</title>
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
			<div class="col-9">
				<div class="row ">
					<div id="shopView" class="col"></div>
				</div>
				<div class="row ">
					<div class="col">
						<nav id="btmPageShift" class="my-5"></nav>
					</div>
				</div>
			</div>
			<div class="col"></div>
		</div>
	</div>
	<c:import url="/FooterSec" />
</body>


<script>
	var JSON_supply = <%=request.getAttribute("JSON_supply")%>;
	var JSON_sales=<%=request.getAttribute("JSON_sales")%>;
	
	var shopNum = 2;//設定一頁顯示店家數
	var pageNum = Math.ceil(JSON_supply.length/shopNum);  //取得回傳的頁面數
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
		$.each(pageArr, function(pNum, notImp) {
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
	//產生商家畫面
	showShop(nowPage,shopNum,JSON_supply);
	
	
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
					showShop(nowPage,shopNum,JSON_supply);
				}
			})

	//下一頁按鈕
	$('#nextPage').click(
			function name() {
				if (!$(this).hasClass("disabled")) {
					nowPage = nowPage + 1;
					$("#liPage" + nowPage).siblings().removeClass("active").end().addClass("active");
					checkpage();
					showShop(nowPage,shopNum,JSON_supply);
				}
			})

	//跳到指定頁面
	$('li[name="liPageNum"]').click(function() {
		$(this).siblings().removeClass("active").end().addClass("active");
		nowPage = $(this).val();
		checkpage();
		showShop(nowPage,shopNum,JSON_supply);
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
	
	
	//產生螢幕商家程式
	function showShop(pageNow,shopNum1page,shopInfo){
	
		//設定商家圖片位置
		var urlShopPic="/supplyPics/"
		//設定導入商店商品業的控制器路徑，控制器不含商家的key值
		var urlToShop="search";
		//根據一頁顯示的商家數以及目前頁面決定本頁顯示商家的陣列起終點
		var startShop=pageNow*shopNum1page-shopNum1page; //起點
		var endShop=pageNow*shopNum1page-1;//終點
		var maxShop=shopInfo.length;//最多登入的商家數
		
		$("#shopView").empty();//清除原有資料
		
		for(i=startShop;i<=endShop;i++){
			var innerHTMLtxt="";	//準備寫入的html字串
			var innerImg="";			//準備圖片字串
			var cardbody="";			//準備內容字串
			var innerContex="";		//商家介紹字串
			var innerBtn="";			//按鈕超連結字串
			var comment="";   	//評論區字串
			
			var btnGoToShop="";
			var btnShowComment="";
			
			if(i<maxShop){
				//店家圖片字串
				innerImg="<div class=\"col-md-3\"  ><img src="+urlShopPic+shopInfo[i].image+" alt='So delicious' class=\"card-img h-100\" style=\"object-fit: cover\" /></div>";
				
				//店家內容字串
				innerContex="<h5 class=\"card-title \">"+shopInfo[i].supply_name+"<p class=\"card-text\"><small>"+"店家電話："+
										shopInfo[i].supply_ph+"<br/>"+"店家地址："+shopInfo[i].supply_address+"<br/>"+"店家Email："+shopInfo[i].supply_email+"<br/> </small>"+searchappend(i)+"</p>";
				
				//店家導向頁面字串
				btnGoToShop=" <button name=\"go2Shop\" type=\"button\" \class=\"btn btn-outline-info btn-sm\" onclick=\"location.href='"+urlToShop+"/"+shopInfo[i].key+"';\">去看看</button> ";
				btnShowComment="<button name=\"showComm"+shopInfo[i].key+"\" class=\"btn btn-outline-primary btn-sm\" type=\"button\" data-toggle=\"collapse\" data-target=\"#showComm"+shopInfo[i].key
				+"\" aria-expanded=\"false\" aria-controls=\"showComm"+shopInfo[i].key+"\" >看評論 </button>";
				innerBtn="<div class=\"card-footer mb-0 pb-0 bg-transparent border-secondary text-right\">"+	btnShowComment+btnGoToShop+"</div>";
				
				//評論區展開的字串
				comment="<div class=\"collapse multi-collapse\" id=\"showComm"+shopInfo[i].key+"\">showComm"+shopInfo[i].key+"尚無評論</div>"
				
				//店家完整表格字串
				cardbody="<div class=\"col-md-9\"><h3 class=\"text-primary\" style=\"text-align: right\">"+shopInfo[i].score+" 分</h3> <div class=\"card-body\">"+innerContex+innerBtn+"</div> </div>";
	
				$("#shopView").append("<div id=\"shopNum"+shopInfo[i].key+"\" class=\"card mt-3\"  > <div class=\"row no-gutters\">"+innerImg+cardbody+"</div> </div>"+comment);
				commentAjax(shopInfo[i].key)
			}else{
// 				$("#shopView").append("<div class=\"card mb-3  invisible\"></div>");//寫入空白且不可視的html內容
			}
		}		
	}
	function searchappend(i){
		var stringSearch=" ";
		if(JSON_sales!==null){
			if(JSON_sales[i].commodity_name!==0){
				stringSearch="<small >相關商品：<span class=\"text-danger\"> "+JSON_sales[i].commodity_name+"</span></small>"
			}
		}
		return stringSearch
	}
	//點擊評論按鈕執行ajax

	function commentAjax(key){
		//評論controller要 return "02CustomerPages/02Shopping/custShopCommentRead_ajax";
		$.get("${pageContext.request.contextPath}/cstmr/comment/"+key, function(result){
		    $("#showComm"+key).html(result);
		    //controller需增加key值才不會爆掉  request.setAttribute("key", key);
// 			$("#showComm"+key).html("#showComm"+key+"測試");
		  });
	}
	 
</script>
<script>
		$("input").attr("autocomplete","off");
	</script>
</html>