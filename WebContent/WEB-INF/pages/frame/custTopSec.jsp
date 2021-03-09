<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<link rel="stylesheet"
	href="<c:url value='/resources/css/bootstrap.min.css'/>" />
<%-- 	
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous"> --%>
<link
	href="https://fonts.googleapis.com/css?family=Noto+Sans+TC&display=swap"
	rel="stylesheet">
<style>
body {
	font-family: 'Noto Sans TC', sans-serif;
}
</style>

<body>
	<nav class="navbar navbar-expand-lg navbar-dark"
		style="background-color: #000066;">
		<a class="navbar-brand" href='<c:url value="/cstmr/" />'>SuKiBenTou <span
			class="badge badge-warning">${cstmracc_name}</span></a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a class="nav-link"
					href='<c:url value="/cstmr/" />'>首頁</a></li>
				<li class="nav-item"><a class="nav-link"
					href='<c:url value="/cstmr/search" />'>便當盒訂購</a></li>
				<li class="nav-item"><a class="nav-link"
					href='<c:url value="/cstmr/shoppingcart" />'>購物車</a></li>
				<li class="nav-item"><a class="nav-link"
					href='<c:url value="/cstmr/orders" />'>訂單紀錄</a></li>
				<li class="nav-item"><a class="nav-link"
					href='<c:url value="/cstmr/message" />'>訊息</a></li>
				<li class="nav-item"><a class="nav-link"
					href='<c:url value="/cstmr/data" />'>會員設定</a></li>
				<li class="nav-item"><a class="nav-link"
					href='<c:url value="/cstmr/logout" />'>登出</a></li>
				</ul>
			<form class="form-inline my-2 my-lg-0 "
				action='<c:url value="/cstmr/search_word" />' method="get" >
				<input class="form-control mr-sm-2" type="search" name="n"
					placeholder="查詢相關資訊" aria-label="Search" style="max-width:150px">
				<button class="btn btn-outline-light my-2 my-sm-0" type="submit">查詢</button>
			</form>
		</div>
	</nav>
	
</body>
<script src="<c:url value='/resources/js/JQuery3.4.1.js"'/>"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>
<script src="<c:url value='/resources/js/bootstrap.min.js"'/>"></script>
<%-- <script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
	integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
	crossorigin="anonymous"></script>--%>
</html>