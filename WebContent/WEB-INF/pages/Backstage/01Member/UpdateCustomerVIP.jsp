<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告新增</title>
<style>
tr {
	height: 45px;
	font-size: 20px;
}

td {
	width: 400px;
}
</style>

</head>
<body>
	<form action="<c:url value="/manager/Customer_update" />" method="post">
		<input type="hidden" name="customer_id"
			value="${MAP_Customer_PreUpdate.cstmr_id}">
		<div>
			<table style="margin-left: 200px">
				<tr>
					<td>消費者編號</td>
					<td>${MAP_Customer_PreUpdate.cstmr_id}</td>
				</tr>
				<tr>
					<td>消費者帳號</td>
					<td>${MAP_Customer_PreUpdate.cstmr_acc}</td>
				</tr>
				<tr>
					<td>消費者抬頭</td>
					<td>${MAP_Customer_PreUpdate.cstmr_name}</td>
				</tr>
				<tr>
					<td>消費者電話</td>
					<td>${MAP_Customer_PreUpdate.cstmr_ph}</td>
				</tr>
				<tr>
					<td>消費者地址</td>
					<td>${MAP_Customer_PreUpdate.cstmr_address}</td>
				</tr>
				<tr>
					<td>負責人</td>
					<td>${MAP_Customer_PreUpdate.cstmr_contact}</td>
				</tr>
				<tr>
					<td>負責人電話</td>
					<td>${MAP_Customer_PreUpdate.cstmr_contactphnum}</td>
				</tr>
				<tr>
					<td>信箱</td>
					<td>${MAP_Customer_PreUpdate.cstmr_email}</td>
				</tr>
				<tr>
					<td>消費者統編</td>
					<td>${MAP_Customer_PreUpdate.cstmr_conumber}</td>
				</tr>
				<tr>
					<td>消費者銀行帳戶</td>
					<td>${MAP_Customer_PreUpdate.cstmr_bankaccount}</td>
				</tr>
				<tr class="text-danger" style="display: ${MAP_Customer_PreUpdate.display}">
					<td>升級付款序號</td>
					<td>${num}</td>
				</tr>
				<tr>
					<td>用戶VIP</td>
					<td><select name="cstmr_vip" size="1">
							<option value="member" selected>一般會員</option>
							<option value="vip" ${MAP_Customer_PreUpdate.cstmr_vip_Y}>付費會員</option>
							<option value="banned" ${MAP_Customer_PreUpdate.cstmr_vip_OUT}>停權</option>
					</select><br /></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<button type="submit" class="btn btn-primary">修改</button>
						<button type="button" class="btn btn-warning"
							onclick="showAtRight('<c:url value="show_Customer" />')">取消</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>