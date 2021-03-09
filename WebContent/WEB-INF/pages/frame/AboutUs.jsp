<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>

<link rel="stylesheet"
	href="<c:url value='/resources/css/bootstrap.min.css'/>" />
<link
	href="https://fonts.googleapis.com/css?family=Noto+Sans+TC&display=swap"
	rel="stylesheet">
<style>
body {
	font-family: 'Noto Sans TC', sans-serif;
}

#FooterBox {
	height: 40pt;
	width: 100%;
	position: fixed;
	bottom: 0px;
}

#FooterSec {
	font-size: 10pt;
	color: #ffffff;
	align: center;
	text-align: center;
	padding-top: 10pt;
}
</style>
<body>
	<div id="FooterBox" class="fixed-bottom"
		style="background-color: #000033;">
		<div id="FooterSec" class="align-self-center">
			<span>Copyright ©2020 <a class="text-white"
				href='<c:url value="/" />'>SuKiBenTou</a>. All Rights Reserved.
			</span><br /> <span> <a class="text-white"
				href='<c:url value="/" />'>關於我們</a>｜ <a class="text-white"
				href='https://getbootstrap.com/'>Bootstrap</a>｜ <a
				class="text-white" href='https://www.w3schools.com/'>w3schools</a>｜
				<a class="text-white"
				href='https://www.oracle.com/java/technologies/'>Oracle Java
					Technologies</a><span style="display: ${display}">｜ <a
					class="text-white" href='<c:url value="/manager/" />'>後台</a></span>
			</span>
		</div>

	</div>
</body>
</html>