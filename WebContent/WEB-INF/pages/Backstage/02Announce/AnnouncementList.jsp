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
	var JSON_Announcement_ListAll =
<%=session.getAttribute("JSON_Announcement_ListAll")%>
	;
	var arrayTab = []; //建立table對應的array,儲存數量[0]與小計[1]
	var announcementNum = JSON_Announcement_ListAll.length; //取得訂單筆數
	var tbodyStr = "tbody1"; //準備寫入表格的tbody之id

	showAnnouncement(announcementNum, tbodyStr, JSON_Announcement_ListAll);

	//產品表格生成程式
	function showAnnouncement(announcementNum, tbodyName, announcementInfo) {
		//orderNum 訂單筆數
		//tbodyNames 輸入tbody的字串名
		//orderInfo 訂單清單
		$("#" + tbodyName).empty(); //清除原有資料
		for (i = 0; i < announcementNum; i++) {
			var innerTdStr = ""; //準備寫入的td字串
			var btnStr = "";
			//td字串
			innerTdStr = "<td>" + (i + 1) + "</td>" +
					"<th scope=\"row\">" + "<a onclick=\"showAtRight('<c:url value='announcementSelectOne/"
					+ announcementInfo[i].announcement_id + "' />')\">"
					+ announcementInfo[i].announcement_title + "</a>" + "</th>"
					+ "<td >" + announcementInfo[i].announcement_time + "</td>"
					+ "<td >" + announcementInfo[i].announcement_status
					+ "</td>" + "<td >"
					+ "<button type=\"button\" class=\"btn btn-success\" onclick=\"showAtRight('<c:url value='announcementList/"+announcementInfo[i].announcement_id+"' />')\">"
					+ "更新" + "</button>" + "</td>"
					+ "<td >"
					+ "<button type=\"button\" class=\"btn btn-danger\" onclick=\"location.href='<c:url value='announcementDelete/"+announcementInfo[i].announcement_id+"' />'\">"
					+ "刪除" + "</button>" + "</td>";

			//寫入table
			$("#" + tbodyName).append(
					"<tr id=\""+i+"\" name=\"supplyItems\">" + innerTdStr + "</tr>");
		}
	}
</script>
</head>
<body>
	<form action="<c:url value="/manager/show_Announcement" />" method="get">
		<div class="d1">
			<table>
				<thead>
					<tr>
						<th style="width: 150px;"></th>
						<th style="width: 230px">標題</th>
						<th style="width: 200px">時間</th>
						<th style="width: 100px">公告狀態</th>
						<th style="width: 65px"></th>
						<th style="width: 50px"></th>
					</tr>
				</thead>
				<tbody id="tbody1">
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>