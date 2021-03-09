<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>無標題</title>
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
	<div class="container">
		<div class="row">
			<div class="col"></div>
			<div class="col-8">
				<div class="jumbotron my-4">
					<h1 class="display-4 text-danger">${warning }</h1>
					<h1 class="display-4">${error }</h1>
				</div>
			</div>
			<div class="col"></div>
		</div>
	</div>
	<c:import url="/FooterSec" />

</body>
</html>