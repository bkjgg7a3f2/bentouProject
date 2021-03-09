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
	var JSON_Supply_ListAll =
<%=request.getAttribute("JSON_Supply_ListAll")%>
	;
	var arrayTab = []; //建立table對應的array,儲存數量[0]與小計[1]
	var supplyNum = JSON_Supply_ListAll.length; //取得訂單筆數
	var tbodyStr = "tbody1"; //準備寫入表格的tbody之id

	showSupply(supplyNum, tbodyStr, JSON_Supply_ListAll);

	//產品表格生成程式
	function showSupply(supplyNum, tbodyName, supplyInfo) {
		//orderNum 訂單筆數
		//tbodyNames 輸入tbody的字串名
		//orderInfo 訂單清單
		$("#" + tbodyName).empty(); //清除原有資料
		for (i = 0; i < supplyNum; i++) {
			var innerTdStr = ""; //準備寫入的td字串
			//td字串 
			innerTdStr = "<th scope=\"row\">"
					+ (i + 1)
					+ "</th>"
					+ "<td >"
					+ supplyInfo[i].supply_acc
					+ "</td>"
					+ "<td >"
					+ supplyInfo[i].supply_name
					+ "</td>"
					+ "<td>"
					+ supplyInfo[i].supply_address
					+ "</td>"
					+ "<td>"
					+ supplyInfo[i].supply_contact
					+ "</td>"
					+ "<td>"
					+ supplyInfo[i].supply_contactphnum
					+ "</td>"
					+ "<td>"
					+ supplyInfo[i].supply_email
					+ "</td>"
					+ "<td>"
					+ supplyInfo[i].supply_vip
					+ "</td>"
					+ "<td >"
					+ "<button type=\"button\" class=\"btn btn-success\" onclick=\"showAtRight('<c:url value='Supply_preUpdate/" + supplyInfo[i].supply_id + "' />')\">"
					+ "檢視" + "</button>" + "</td>";

			//寫入table  
			$("#" + tbodyName).append(
					"<tr id=\""+i+"\" name=\"customerItems\">" + innerTdStr
							+ "</tr>");
		}
	}
</script>

</head>
<body>
	<form action="<c:url value="/show_Supply" />" method="get"
		enctype="multipart/form-data">
		<div class="d1">
			<table>
				<thead>
					<tr>
						<th style="width: 200px;"></th>
						<th style="width: 200px;">帳號</th>
						<th style="width: 1200px; text-align: center;">抬頭</th>
						<th style="width: 1300px; text-align: center;">地址</th>
						<th style="width: 500px;">負責人姓名</th>
						<th style="width: 400px;">負責人電話</th>
						<th style="width: 400px;">電子信箱</th>
						<th style="width: 350px;">會員狀態</th>
					</tr>
				</thead>
				<tbody id="tbody1">
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>