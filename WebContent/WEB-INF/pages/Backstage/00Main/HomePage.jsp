<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-tw">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%-- 在IE運行最新的渲染模式 --%>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%-- 初始化移動瀏覽顯示 --%>
<meta name="Author" content="Dreamer-1.">

<!-- 引入各種CSS樣式表 -->
<link rel="stylesheet"
	href="<c:url value='/resources/backstage/css/bootstrap.css'/>">
<link rel="stylesheet"
	href="<c:url value='/resources/backstage/css/font-awesome.css'/>">
<link rel="stylesheet"
	href="<c:url value='/resources/backstage/css/index.css'/>">
<!-- 修改自Bootstrap官方Demon，你可以按自己的喜好制定CSS樣式 -->
<link rel="stylesheet"
	href="<c:url value='/resources/backstage/css/font-change.css'/>">
<!-- 將默認字體從宋體换成微軟雅黑 -->

<script type="text/javascript"
	src="<c:url value='/resources/backstage/js/jquery-1.12.3.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/backstage/js/bootstrap.min.js'/>"></script>

<title>- 後臺管理系統 -</title>
</head>

<body>
	<!-- 頂部菜單（From bootstrap Demon）==================================== -->
	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="<c:url value="HomePage" />">SuKiBenTou.com</a>
		</div>

		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a><i class="fa fa-check-circle"></i> ${loginSuccess}</a></li>
				<li><a
					href="javascript: showAtRight('<c:url value="show_Customer" />');"><i
						class="fa fa-users"></i> 用戶列表</a></li>
				<li><a
					href="javascript: showAtRight('<c:url value="show_Supply" />');"><i
						class="fa fa-users"></i> 廠商列表</a></li>
				<li><a
					href="javascript: showAtRight('<c:url value="show_Announcement" />');"><i
						class="fa fa-list"></i> 公告列表</a></li>
				<li><a href="<c:url value='login_out' />"><i
						class="fa fa-minus-circle"></i> 登出</a></li>
			</ul>
		</div>
	</div>
	</nav>

	<!-- 左側菜單選項========================================= -->
	<div class="container-fluid">
		<div class="row-fluie">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<!-- 一級菜單 -->
					<li class="active"><a href="#userMeun"
						class="nav-header menu-first collapsed" data-toggle="collapse">
							<i class="fa fa-user"></i>&nbsp; 用户管理 <span class="sr-only">(current)</span>
					</a></li>
					<!-- 二級菜單 -->
					<!-- 注意一級菜單中<a>標籤内的href="#……"裡面的内容要與二級菜單中<ul>標籤内的id="……"裡面的内容一致 -->
					<ul id="userMeun" class="nav nav-list collapse menu-second">
						<li><a
							href="javascript: showAtRight('<c:url value="show_Customer" />');"><i
								class="fa fa-users"></i> 用戶列表</a></li>
						<li><a
							href="javascript: showAtRight('<c:url value="show_Supply" />');"><i
								class="fa fa-users"></i> 廠商列表</a></li>
					</ul>

					<li><a href="#announcementMeun"
						class="nav-header menu-first collapsed" data-toggle="collapse">
							<i class="fa fa-bullhorn"></i>&nbsp; 公告管理 <span class="sr-only">(current)</span>
					</a></li>
					<ul id="announcementMeun" class="nav nav-list collapse menu-second">
						<li><a
							href="javascript: showAtRight('<c:url value="InsertAnnouncement" />');"><i
								class="fa fa-mouse-pointer"></i> 公告新增</a></li>
						<li><a
							href="javascript: showAtRight('<c:url value="show_Announcement" />');"><i
								class="fa fa-list"></i> 公告列表</a></li>
						<li><a
							href="javascript: showAtRight('<c:url value="announcement_SelectStatus" />');"><i
								class="fa fa-tag"></i> 前台顯示</a></li>
					</ul>
				</ul>

			</div>
		</div>
	</div>

	<!-- 右側內容展示==================================================   -->
	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h1 class="page-header">
			<i class="fa fa-cog fa-spin"></i>&nbsp;管理台<small>&nbsp;&nbsp;&nbsp;SuKiBenTou後臺管理系统</small>
		</h1>

		<!-- 載入左側菜單指向的jsp（或html等）頁面内容 -->
		<div id="content">

			<h3>
				<strong>歡迎使用：</strong>
			</h3>
			<br>
			<h4>最新消息</h4>
			${msg} <br>
			<hr>
			<h4>更新紀錄</h4>
			${message}


		</div>
	</div>


	<script type="text/javascript">
		/*
		 * 對選中的標籤激活active狀態，對先前處於active狀態但之後未被選中的標籤取消active
		 * （實現左側菜單中的標籤點擊後變色的效果）
		 */
		$(document).ready(function() {
			$('ul.nav > li').click(function(e) {
				//e.preventDefault();	加上這句則導航的<a>標籤會失效
				$('ul.nav > li').removeClass('active');
				$(this).addClass('active');
			});
		});

		/*
		 * 解決ajax返回的頁面中含有javascript的辦法：
		 * 把xmlHttp.responseText中的腳本都抽取出來，不管AJAX加載的HTML包含多少个腳本塊，我們對找出來的腳本塊都調用eval方法執行它即可
		 */
		function executeScript(html) {

			var reg = /<script[^>]*>([^\x00]+)$/i;
			//對整段HTML片段按<\/script>拆分
			var htmlBlock = html.split("<\/script>");
			for ( var i in htmlBlock) {
				var blocks;//匹配正則表示式的内容數組，blocks[1]就是真正的一段腳本内容，因為前面reg定義我們用了括號進行了捕獲分組
				if (blocks = htmlBlock[i].match(reg)) {
					//清除可能存在的註釋標記，對於註釋結尾-->可以忽略處理，eval一樣能正常工作
					var code = blocks[1].replace(/<!--/, '');
					try {
						eval(code) //執行腳本
					} catch (e) {
					}
				}
			}
		}

		/*
		 * 利用div實現左邊點擊右邊顯示的效果（以id="content"的div進行内容展示）
		 * 注意：
		 *   ①：js獲取網頁的地址，是根據當前網頁来相對獲取的，不會識別根目錄；
		 *   ②：如果右邊加載的内容顯示頁裡面有css，必須放在主頁（即例中的index.jsp）才起作用
		 *   （如果單純的兩個頁面之間include，子頁面的css和js在子頁面是可以執行的。 主頁面也可以調用子頁面的js。但這時要考慮頁面中js和渲染的先後顺序 ）
		 */
		function showAtRight(url) {
			var xmlHttp;

			if (window.XMLHttpRequest) {
				// code for IE7+, Firefox, Chrome, Opera, Safari
				xmlHttp = new XMLHttpRequest(); //創建 XMLHttpRequest對象
			} else {
				// code for IE6, IE5
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}

			xmlHttp.onreadystatechange = function() {
				//onreadystatechange — 當readystate變化時調用後面的方法

				if (xmlHttp.readyState == 4) {
					//xmlHttp.readyState == 4	——	finished downloading response

					if (xmlHttp.status == 200) {
						//xmlHttp.status == 200		——	伺服器反饋正常			

						document.getElementById("content").innerHTML = xmlHttp.responseText; //重設頁面中id="content"的div裡的内容
						executeScript(xmlHttp.responseText); //執行從伺服器返回的頁面内容裡包含的JavaScript函數
					}
					//錯誤狀態處理
					else if (xmlHttp.status == 404) {
						alert("出錯了☹   （錯誤代碼：404 Not Found），……！");
						/* 對404的處理 */
						return;
					} else if (xmlHttp.status == 403) {
						alert("出錯了☹   （錯誤代碼：403 Forbidden），……");
						/* 對403的處理  */
						return;
					} else {
						alert("出錯了☹   （錯誤代碼：" + request.status + "），……");
						/* 對出現了其他錯誤代碼所示錯誤的處理   */
						return;
					}
				}

			}

			//把請求發送到伺服器上的指定文件（url指向的文件）進行處理
			xmlHttp.open("GET", url, true); //true表示異步處理
			xmlHttp.send();
		}

		//判斷瀏覽器是否支持FileReader接口
		if (typeof FileReader == 'undefined') {
			alert("<h1>當前瀏覽器不支持FileReader接口</h1>");
		}
		//選擇圖片，馬上預覽
		function xmTanUploadImg1(obj) {
			var file = obj.files[0];
			var reader = new FileReader();
			reader.onload = function(e) {
				var img = document.getElementById("img1");
				img.src = e.target.result;
			}
			reader.readAsDataURL(file);
		}
		function xmTanUploadImg2(obj) {
			var file = obj.files[0];
			var reader = new FileReader();
			reader.onload = function(e) {
				var img = document.getElementById("img2");
				img.src = e.target.result;
			}
			reader.readAsDataURL(file);
		}
		function xmTanUploadImg3(obj) {
			var file = obj.files[0];
			var reader = new FileReader();
			reader.onload = function(e) {
				var img = document.getElementById("img3");
				img.src = e.target.result;
			}
			reader.readAsDataURL(file);
		}
		//公告更新一鍵輸入
		function autoFillInsert() {
			var inputTitle = document.getElementById("inputTitle");
			var inputContent = document.getElementById("inputContent");

			inputTitle.value = "如何註冊成為VIP會員";
			inputContent.value = "會員登錄後在會員設定的進階功能中，點擊付費升級並且完成付費即可升級成為VIP會員"
					+ "  如有任何問題,請撥打客服專線(02)2231-1154,或加入客服line:kevin9453,將會有專員立即為您服務";
		}

		//新增修改不可給空值
		function chk() {
			var t = document.getElementById("inputTitle").value;
			var c = document.getElementById("inputContent").value;
			if (t.length == 0) {
				alert("請輸入標題")
				return false;
			}
			if (c.length == 0) {
				alert("請輸入內容")
				return false;
			}
			return true;
		}
	</script>

</body>
</html>