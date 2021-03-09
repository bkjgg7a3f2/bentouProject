<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>顧客評論</title>
</head>

<link rel="stylesheet"
	href="<c:url value='resources/css/bootstrap.min.css'/>" />
<link
	href="https://fonts.googleapis.com/css?family=Noto+Sans+TC&display=swap"
	rel="stylesheet">
<style>
body {
	font-family: 'Noto Sans TC', sans-serif;
}
</style>
<body>
	<c:import url="/TopSec" />

	<div class="container" style="margin-bottom: 45pt;">
		<div class="row">
			<div class="col"></div>
			<div class="col-10">
				<div id="commentZone" class="rounded-lg border border-dark mx-auto"
					style="padding: 5%; margin: 5%;">
					<h3 style="text-align: center">顧客評價</h3>
				</div>

			</div>
			<div class="col"></div>
		</div>

	</div>

	<c:import url="/FooterSec" />

	<!-- 彈出視窗 -->
	<div class="modal fade" id="commentDiv" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<form name="commentForm" action="" method="post"
			onsubmit="return commentchk()">
			<div class="modal-dialog modal-dialog-centered " role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title text-dark" id="exampleModalCenterTitle">
							請輸入回覆</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body align-center">
						<div class="form-group row px-3">
							<label for="comment_id"
								class="col-sm-3 col-form-label text-right">訂單編號：</label>
							<div class="col-sm">
								<input name="order_id" type="text" readonly
									class="form-control-plaintext" id="comment_id" value="">
							</div>
						</div>
						<div class="form-group row px-3">
							<label for="shopComment"
								class="col-sm-3  pt-3 col-form-label text-right border-top">您的回覆：</label>
							<div class="col-sm pt-3 border-top">
								<textarea name="supply_evaluation" class="form-control" rows="3"
									id="shopComment" placeholder="請輸入您的回覆" /></textarea>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">送出評論</button>
					</div>
				</div>
			</div>
		</form>
	</div>

	<script>
		var JSON_cu =
	<%=request.getAttribute("JSON_viewEvaluation_cu")%>
		;
		var JSON_su =
	<%=request.getAttribute("JSON_viewEvaluation_su")%>
		;
		var length = JSON_cu.length;
		if (length !== 0) {
			for (i = 0; i < length; i++) {
				var orderIdStr = "";
				var custStr = "";
				var shopStr = "";
				var btnStr = "<button id=\""+JSON_cu[i].comment_id+"\" name=\"commentReply\" type=\"button\" class=\"btn btn-outline-secondary btn-sm\">";
				//評論內容
				custStr = "<div class=\"row w-100\"><div class=\"col-sm-4 text-right border-right\">"
						+ JSON_cu[i].cstmr_name
						+ "</div>"
						+ "<div class=\"col-sm-5\"> 評價："
						+ JSON_cu[i].cstmr_fraction
						+ "</div>"
						+ "<div class=\"col-sm-3 text-center\"><small>"
						+ JSON_cu[i].msgTime
						+ "</small></div>"
						+ "</div><div class=\"row w-100 \"><div class=\"col-sm-4 border-right\"></div><div class=\"col-sm border-top text-break\">"
						+ JSON_cu[i].cstmr_evaluation + "</div></div>";
				//評論框線樣式
				custStr = "<div class=\"row\"><div class=\"col-sm-12 border border-secondary rounded my-2\">"
						+ custStr + "</div></div>";

				if (JSON_su[i].supply_evaluation != null) {
					shopStr = "<div class=\"row\">"
							+ "<div class=\"col-sm-2 text-right\">"
							+ btnStr
							+ "修改回覆</button>"
							+ "</div>"
							+ "<div class=\"col-sm-2 text-right\">回覆：</div>"
							+ "<div id=\"custComm"+JSON_cu[i].comment_id+"\" class=\"col-sm-5 text-break\">"
							+ JSON_su[i].supply_evaluation + "</div>"
							+ "<div class=\"col-sm-3 text-center\"><small>"
							+ JSON_su[i].replyTime + "</small></div>"
							+ "</div>";
				} else {
					shopStr = "<div class=\"row\">"
							+ "<div class=\"col-sm-2 text-right\">" + btnStr
							+ "回覆評價</button>" + "</div>"
							+ "<div class=\"col-sm-2 text-right\"></div>"
							+ "<div class=\"col-sm-5 \">" + "</div>"
							+ "<div class=\"col-sm-3 text-center\"><small>"
							+ "</small></div>" + "</div>";
				}
				shopStr = "<div class=\"alert alert-secondary mb-2 p-1\" role=\"alert\">"
						+ shopStr + "</div>";
				orderIdStr = "<div class=\" p-4 border-top border-secondary \">"
						+ "<h4>訂單編號："
						+ JSON_cu[i].comment_id
						+ "</h4>"
						+ custStr + shopStr + "</div>";
				$("#commentZone").append(orderIdStr)
			}
		} else {
			$("#commentZone").html("目前無評論")
		}

		$("button[name='commentReply']")
				.click(
						function() {
							var comment_id = $(this).attr("id");
							// 							if ($("#custComm" + comment_id).text() != "") {
							$("#commentDiv").find("textarea").text(
									$("#custComm" + comment_id).text());
							// 							}
							;
							var urlStr = "${pageContext.request.contextPath}/shop/comment/feedback/"
									+ comment_id;
							$("#comment_id").val(comment_id);
							$("#commentDiv").find("form[name = 'commentForm']")
									.attr("action", urlStr);
							$("#commentDiv").modal('show');
						})
		//繳費代碼確認
		function commentchk() {
			var t = document.getElementById("shopComment").value;
			if (t.length == 0) {
				alert("請輸入評價回覆");
				return false;
			} else {
				alert("輸入成功");
				return true;
			}
		}
	</script>
</body>
</html>