<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>首頁</title>
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

<!-- <script src="/js/bootstrap.min.js"></script> -->
<!-- <script src="/js/JQuery3.4.1.js"></script> -->


<body>
	<c:import url="/TopSec" />
	<div class="container" style="margin-bottom: 45pt;">
		<div class="row">
			<div class="col my-4">
				<button name="announceBtn" type="button"
					class="btn btn-danger btn-block">最新公告</button>
			</div>

			<div class="col-8">

				<div class="card-deck">
					<div class="card my-4" style="width: 100%;">
						<img name='items' src="/salesPics/${best_sales.image1 }"
							class="card-img-top" alt="...">
						<div class="card-body">
							<h5 class="card-title">
								${best_sales.name1 } <span class="badge badge-pill badge-danger">本店熱銷</span>
							</h5>
							<small>銷售總量：<span class="font-weight-bolder text-danger">${best_sales.num1 }</span>份
							</small>
						</div>
					</div>


					<div class="card my-4" style="width: 100%;">
						<img name='items' src="/salesPics/${best_sales.image2 }"
							class="card-img-top" alt="...">
						<div class="card-body">
							<h5 class="card-title">
								${best_sales.name2 } <span class="badge badge-pill badge-danger">本店熱銷</span>
							</h5>
							<small>銷售總量：<span class="font-weight-bolder text-danger">${best_sales.num2 }</span>份</small>
						</div>
					</div>


					<div class="card my-4" style="width: 100%;">
						<img name='items' src="/salesPics/${best_sales.image3 }"
							class="card-img-top" alt="...">
						<div class="card-body">
							<h5 class="card-title">
								${best_sales.name3} <span class="badge badge-pill badge-danger">本店熱銷</span>
							</h5>
							<small>銷售總量：<span class="font-weight-bolder text-danger">${best_sales.num3}</span>份</small>
						</div>
					</div>
				</div>

				<div class="card-deck">
					<div class="card my-4" style="width: 100%;">
						<img name='shop'
							src='<c:url value="/resources/images/supplyPics/${best_shop.image1 }"/>'
							class="card-img-top" alt="...">
						<div class="card-body">
							<h5 class="card-title">
								第一名 <span class="badge badge-pill badge-success">好評不斷</span>
								${best_shop.score1 }分
							</h5>
							<p class="card-text">${best_shop.supply_name1 }</p>
						</div>
					</div>

					<div class="card my-4" style="width: 100%;">
						<img name='shop'
							src='<c:url value="/resources/images/supplyPics/${best_shop.image2 }"/>'
							class="card-img-top" alt="...">
						<div class="card-body">
							<h5 class="card-title">
								第二名 <span class="badge badge-pill badge-success">好評不斷</span>
								${best_shop.score2 }分
							</h5>
							<p class="card-text">${best_shop.supply_name2}</p>
						</div>
					</div>

					<div class="card my-4" style="width: 100%;">
						<img name='shop'
							src='<c:url value="/resources/images/supplyPics/${best_shop.image3 }"/>'
							class="card-img-top" alt="...">
						<div class="card-body">
							<h5 class="card-title">
								第三名 <span class="badge badge-pill badge-success">好評不斷</span>
								${best_shop.score3 }分
							</h5>
							<p class="card-text">${best_shop.supply_name3 }</p>
						</div>
					</div>

				</div>

			</div>

			<div class="col-2">
				<div class="card my-4" style="width: 100%;">
					<img src='<c:url value="/resources/images/adPics/adPictest.png"/>'
						class="card-img-top" alt="...">
					<div class="card-body">
						<h5 class="card-title">超值優惠</h5>
						<p class="card-text">限時升級VIP會費87折！走過路過千萬不要錯過！</p>
						<a href='<c:url value="/shop/data" />' class="btn btn-warning">現在升級</a>
					</div>
				</div>
			</div>
		</div>

	</div>



	<c:import url="/FooterSec" />
	<!-- 彈出視窗 -->
	<div class="modal fade" id="announceList" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header bg-danger ">
					<h5 class="modal-title text-white" id="announceListTitle">最新公告</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span class="text-white" aria-hidden="true">&times;</span>
					</button>
				</div>
				<div id="announceListBody" class="modal-body"></div>
				<div class="modal-footer bg-light">
					<button type="button" class="btn btn-secondary btn-sm"
						data-dismiss="modal">關閉</button>
				</div>
			</div>
		</div>
	</div>


	<script>
		$("img[name='items']").one("error", function(e) {
			$(this).attr("src", "/salesPics/noPic.png");
		});
		$("img[name='shop']").one("error", function(e) {
			$(this).attr("src", "/supplyPics/default.jpg");
		});

		//進入先秀公告
		showannouncement();

		//公告視窗
		$("button[name='announceBtn']").click(function() {
			showannouncement();
		});

		//公告表
		function showannouncement() {
			$.get("${pageContext.request.contextPath}/shop/announcement").done(
					function announceList(data) {
						$("#announceListBody").empty();
						$("#announceListBody").html(data);
						$("tr[name!='theadRow']").hover(function() {
							//滑到tr標籤上改變滑鼠樣式
							$(this).css('cursor', 'pointer');
						}, function() {
							$(this).css('cursor', 'auto');
						}).click(function() {
							showOneAnn($(this));
						});
						$("#announceList").modal("show");
					}).fail(function() {
				$("#announceList").modal("hide");
			})
		}

		//公告內容
		function showOneAnn(obj) {
			let keyVal = obj.attr("name");
			console.log(keyVal)
			let urlOneAnn = "${pageContext.request.contextPath}/shop/announcement/"
					+ keyVal;
			$.get(urlOneAnn).done(function announceList(data) {
				$("#announceListBody").empty();
				$("#announceListBody").html(data);
				$("#announceList").modal("show");
			}).fail(function() {
				//失敗則回到公告列表
				showannouncement();
			});
		}
	</script>
</body>
</html>