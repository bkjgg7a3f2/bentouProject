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
	var JSON_Customer_ListAll =
<%=request.getAttribute("JSON_Customer_ListAll")%>
	;
	var arrayTab = []; //建立table對應的array,儲存數量[0]與小計[1]
	var customerNum = JSON_Customer_ListAll.length; //取得訂單筆數
	var tbodyStr = "tbody1"; //準備寫入表格的tbody之id

	showCustomer(customerNum, tbodyStr, JSON_Customer_ListAll);

	//產品表格生成程式
	function showCustomer(customerNum, tbodyName, customerInfo) {
		//orderNum 訂單筆數
		//tbodyNames 輸入tbody的字串名
		//orderInfo 訂單清單
		$("#" + tbodyName).empty(); //清除原有資料
		for (i = 0; i < customerNum; i++) {
			var innerTdStr = ""; //準備寫入的td字串
			// "<a onclick=\"showAtRight('<c:url value="Customer_preUpdate/i" />')\">"
			//td字串 customerInfo[i].cstmr_id
			innerTdStr = "<th scope=\"row\">"
					+ (i + 1)
					+ "</th>"
					+ "<td >"
					+ customerInfo[i].cstmr_acc
					+ "</td>"
					+ "<td >"
					+ customerInfo[i].cstmr_name
					+ "</td>"
					+ "<td>"
					+ customerInfo[i].cstmr_address
					+ "</td>"
					+ "<td>"
					+ customerInfo[i].cstmr_contact
					+ "</td>"
					+ "<td>"
					+ customerInfo[i].cstmr_contactphnum
					+ "</td>"
					+ "<td>"
					+ customerInfo[i].cstmr_email
					+ "</td>"
					+ "<td>"
					+ customerInfo[i].cstmr_vip
					+ "</td>"
					+ "<td >"
					+ "<button type=\"button\" class=\"btn btn-success\" onclick=\"showAtRight('<c:url value='Customer_preUpdate/" + customerInfo[i].cstmr_id + "' />')\">"
					+ "檢視" + "</button>" + "</td>";

			//寫入table     
			$("#" + tbodyName).append(
					"<tr id=\""+i+"\" name=\"customerItems\">" + innerTdStr
							+ "</tr>");
		}

		// 		$("tr[name='customerItems']").hover(function() {
		// 			$(this).css('cursor', 'pointer');
		// 		}, function() {
		// 			$(this).css('cursor', 'auto');
		// 		}).click(
		// 				function() {
		// 					var i = $(this).attr("id");
		// 					var urlForm = "Customer_preUpdate" + "/"
		// 							+ customerInfo[i].cstmr_id;
		// 					//各筆交易虛擬路徑
		// 					window.location.href = urlForm;
		// 					return false;
		// 				})
	}
</script>
</head>
<body>
	<form action="<c:url value="/show_Customer" />" method="get"
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