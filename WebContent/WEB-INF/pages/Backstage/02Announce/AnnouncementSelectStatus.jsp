<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>顧客資料</title>
<style>
.d1 {
	border: 1.5px;
	border-radius: 15px;
	width: 1200px;
	margin: auto;
	height: 400px;
}

th {
	height: 50px;
}
</style>
<script>
	var JSON_Announcement_SelectStatus =
<%=request.getAttribute("JSON_Announcement_SelectStatus")%>
	;
	var arrayTab = []; //建立table對應的array,儲存數量[0]與小計[1]
	var statusNum = JSON_Announcement_SelectStatus.length; //取得訂單筆數
	var tbodyStr = "tbody1"; //準備寫入表格的tbody之id

	showStatus(statusNum, tbodyStr, JSON_Announcement_SelectStatus);

	//產品表格生成程式
	function showStatus(statusNum, tbodyName, statusInfo) {
		//orderNum 訂單筆數
		//tbodyNames 輸入tbody的字串名
		//orderInfo 訂單清單
		$("#" + tbodyName).empty(); //清除原有資料
		for (i = 0; i < statusNum; i++) {
			var innerTdStr = ""; //準備寫入的td字串
			//td字串  	
			innerTdStr = "<td>"								
					+ "<img src=\"/billboardPics/" + statusInfo[i].announcement_picture1 + " \" class=\"figure-img img-fluid rounded\" style=\"height: 225px; width: 225px\" alt=\"圖片預覽區\">	"
					+ "</td>" + "<th scope=\"row\">"
					+ statusInfo[i].announcement_title + "</th>" + "<td >"
					+ statusInfo[i].announcement_content + "</td>" + "<td >"
					+ statusInfo[i].announcement_time + "</td>";
			//寫入table
			$("#" + tbodyName).append(
					"<tr id=\""+i+"\" name=\"customerItems\" style=\"height: 250px; width: 250px\">" + innerTdStr
							+ "</tr>");
		}
	}
</script>
</head>
<body>
	<form action="<c:url value="announcement_SelectStatus" />" method="get">
		<div class="d1">
			<table>
				<thead>
					<tr>
						<th style="width: 300px;"></th>
						<th style="width: 230px">標題</th>
						<th style="width: 250px">內容</th>
						<th style="width: 200px">時間</th>
					</tr>
				</thead>
				<tbody id="tbody1">
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>