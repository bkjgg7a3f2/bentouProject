<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<link rel="stylesheet" href="<c:url value='/resources/backstage/css/bootstrap.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/backstage/css/font-awesome.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/backstage/css/index.css'/>">
<!-- 修改自Bootstrap官方Demon，你可以按自己的喜好制定CSS樣式 -->
<link rel="stylesheet" href="<c:url value='/resources/backstage/css/font-change.css'/>">
<!-- 將默認字體從宋體换成微軟雅黑 -->

<script type="text/javascript" src="<c:url value='/resources/backstage/js/jquery-1.12.3.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/backstage/js/bootstrap.min.js'/>"></script>

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
			<a class="navbar-brand" href="<c:url value="/manager/" />">SuKiBenTou.com</a>
		</div>

		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="<c:url value="/welcome/" />"><i
						class="fa fa-arrow-circle-right"></i> 註冊/登入</a></li>
			</ul>
		</div>
	</div>
	</nav>

	<!-- 左側菜單選項========================================= -->
	<div class="container-fluid"></div>

	<!-- 右側內容展示==================================================   -->
	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h1 class="page-header">
			<i class="fa fa-cog fa-spin"></i>&nbsp;管理台<small>&nbsp;&nbsp;&nbsp;SuKiBenTou後臺管理系统</small>
		</h1>

		<!-- 載入左側菜單指向的jsp（或html等）頁面内容 -->
		<div id="content">
			<div style="margin-bottom: 40pt;">
				<div style="width: 600px;">
					<div class="rounded-lg border border-dark mx-auto"
						style="padding: 5%; margin: 5%;">
						<form action="<c:url value="/manager/login_fail" />" method="POST">
							<div class="form-group">
								<label for="UserAcc">使用者帳號：</label> <input type="text"
									class="form-control" id="UserAcc" name="useracc"/>
							</div>
							<div class="form-group">
								<label for="InputPassword">使用者密碼：</label> <input type="password"
									class="form-control" id="InputPassword" name="userpwd">
							</div>

							<div class="form-group form-check">
								&nbsp;&nbsp;&nbsp;<em>${msgError}</em>
							</div>

							<hr/>

							<button type="submit" class="btn btn-primary">登入</button>
							<button type="button" class="btn btn-success" onclick="autoFill()">一鍵輸入</button>
						</form>
					</div>
				</div>
			</div>
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
		
		//一鍵輸入
		function autoFill(){
			var user = document.getElementById("UserAcc");
			var password = document.getElementById("InputPassword");

			user.value = "123";
			password.value = "123";
		}
		
		$("input").attr("autocomplete", "off");
	</script>

</body>

</html>