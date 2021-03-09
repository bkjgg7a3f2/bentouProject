<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>訊息</title>
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
	<div class="container -fluid mt-3 ">
		<div id="msgShowContent" class="row mh-100 "
			style="height: 73vh; margin-bottom: 45pt;">
			<div class="col-4">
				<div class="list-group overflow-auto" id="nameList" role="tablist">
				</div>
			</div>
			<div class="col-8 border border-primary rounded-lg px-4 pt-3">
				<div id="scrollShowDiv" class="row  overflow-auto"
					style="height: 60vh; margin-bottom: 60px;">
					<div class="col overflow-auto ">
						<div id="tabContent" class="tab-content">
							<div class="tab-pane active" id="noCust" role="tabpanel">
								<h4>請選擇訊息傳遞顧客</h4>
							</div>
						</div>
					</div>
				</div>
				<div id="msgInPut"
					class="row-sm-1 border-top  position-absolute fixed-bottom px-1 d-none">
					<form action="<c:url value="/shop/message_write" />"class="col align-items-center" method="post">
						<div class="row my-2">
							<div class="col-8">
								<textarea class="form-control" id="inputMsgArea" rows="1"
									style="min-width: 100%; resize: none;" placeholder="請輸入訊息"></textarea>
							</div>
							<div class="col">
								<button id="sendMsgBtn" type="button"
									class="btn btn-primary btn-block">送出</button>
								<input id="nowChatting" type="hidden" value="">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="row">
			<div id="nothingToReply" class="jumbotron my-4 d-none col">
				<h1 class="display-4">尚無訊息</h1>
				<p id="welcome" class="lead">送出訂單後就能使用訊息功能囉！</p>
				<hr class="my-4">
				<p class="d-none"></p>
				<a class="btn btn-primary btn-lg"
					href='<c:url value="/shop/search" />' role="button">去逛逛</a>
			</div>
		</div>

	</div>



	<c:import url="/FooterSec" />


</body>
<script>
	var JSON_cstmr_list =
<%=request.getAttribute("JSON_cstmr_list")%>
	;
	var divStr = {
		customerUrlStr : "/cstmrPics/",
		divList : "nameList",
		divChat : "tabContent"
	}
	var scrollStatus = false;//卷軸尚未滾動
	var divWindowHeight //視窗高度
	if (JSON_cstmr_list != null) {
		chatListShow(JSON_cstmr_list, divStr);
	} else {
		$("#nothingToReply").removeClass("d-none");
		$("#msgShowContent").addClass("d-none");
	}
		//自動刷新聊天內容
		setInterval(function() {
			if ($("#nowChatting").val() != "") {
				var freshID = $("#nowChatting").val();
				refreshChatZone(freshID);
			}
		}, 500);

		//當卷軸捲動時控制是否將div卷軸至底
		$("#scrollShowDiv").scroll(function() {
			let diffheight=divWindowHeight-$("#scrollShowDiv").scrollTop();
			console.log(diffheight);
			if (diffheight>=10 && divWindowHeight!=0) {
				scrollStatus = true;
			} else {
				scrollStatus =false;
			}
			console.log(scrollStatus);
		})

	$("#nameList").children("a[name='chatList']").on("click", function() {
		$("#msgInPut").removeClass("d-none");
		let theId = $(this).attr("id");
		$("#nowChatting").val(theId);
		refreshChatZone(theId);
	})

	function chatListShow(chatListArray, otherStr) {
		//chatListArray 名單陣列
		//nameList			相關參數路徑

		for (i = 0; i < chatListArray.length; i++) {
			let listNameStr = "" //商家名稱字串預設空值
			let listContentStr = "" //聊天內容字串預設空值
			let imgStr = ""//圖片字串預設空值

			//商家圖片區
			imgStr = "<img src=\""+otherStr.customerUrlStr+chatListArray[i].image+"\" class=\"mh-100 rounded-circle\" style=\"height:1.5em;max-width:1.5em; object-fit: cover\">"

			//寫入聊天對象清單
			listNameStr = "<a id=\""+chatListArray[i].id+"\" name=\"chatList\" class=\"list-group-item list-group-item-action\" data-toggle=\"list\" href=\"#"
				+"cstmr"+chatListArray[i].id+"\" role=\"tab\">"
					+ imgStr
					+ "<span class=\"ml-1 text-break\">"
					+ chatListArray[i].name + "<span></a>";

			$("#" + otherStr.divList).prepend(listNameStr);

			//建立聊天對象對話框顯示區
			listContentStr = "<div class=\"tab-pane\" id=\""+"cstmr"+chatListArray[i].id+"\" role=\"tabpanel\"></div>";
			$("#" + otherStr.divChat).prepend(listContentStr);

			//預先載入對話內容
			refreshChatZone(chatListArray[i].id);
		}

	}

	function refreshChatZone(chatID) {
		let urlStr = "${pageContext.request.contextPath}/shop/message_list/"
		$.get(urlStr + chatID).done(function(data) {
			$("#cstmr" + chatID).html(data);
			if (scrollStatus==false) {
				scrollToBtm("scrollShowDiv");
			}
		}).fail(function(xhr, status, error) {

		});
	};

	$("#sendMsgBtn")
			.click(
					function() {
						let textVal = $("#inputMsgArea").val();
						let sendUrlStr = "${pageContext.request.contextPath}/shop/message_write"
						$("#inputMsgArea").val("");
						if (textVal != "") {
							$.post(sendUrlStr, {
								"cstmr_id" : $("#nowChatting").val(),
								"msg_save" : textVal,
							}).done(function(data) {
								scrollStatus = false;
								refreshChatZone($("#nowChatting").val())
								scrollToBtm("#scrollShowDiv");
							})
						}
					});

	//特定div卷軸滾動到最底部程式
	function scrollToBtm(divID) {
		let divTarget = $("#" + divID);
		let divTargetheight = divTarget[0].scrollHeight;
		$("#" + divID).animate({
			scrollTop : divTargetheight,
		}, 100);
		divWindowHeight=$("#" + divID).scrollTop();
	};
</script>
</html>