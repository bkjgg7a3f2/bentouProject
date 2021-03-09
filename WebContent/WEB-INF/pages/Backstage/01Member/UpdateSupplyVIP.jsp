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
	height: 43px;
	font-size: 20px;
}

td {
	width: 400px;
}
</style>

</head>
<body>
	<form action="<c:url value="/manager/Supply_update" />" method="post">
		<input type="hidden" name="supply_id"
			value="${MAP_Supply_PreUpdate.supply_id}">
		<div>
			<table style="margin-left: 200px">
				<tr>
					<td>廠商編號</td>
					<td>${MAP_Supply_PreUpdate.supply_id}</td>
				</tr>
				<tr>
					<td>廠商帳號</td>
					<td>${MAP_Supply_PreUpdate.supply_acc}</td>
				</tr>
				<tr>
					<td>廠商抬頭</td>
					<td>${MAP_Supply_PreUpdate.supply_name}</td>
				</tr>
				<tr>
					<td>廠商電話</td>
					<td>${MAP_Supply_PreUpdate.supply_ph}</td>
				</tr>
				<tr>
					<td>廠商地址</td>
					<td>${MAP_Supply_PreUpdate.supply_address}</td>
				</tr>
				<tr>
					<td>廠商負責人</td>
					<td>${MAP_Supply_PreUpdate.supply_contact}</td>
				</tr>
				<tr>
					<td>負責人電話</td>
					<td>${MAP_Supply_PreUpdate.supply_contactphnum}</td>
				</tr>
				<tr>
					<td>廠商信箱</td>
					<td>${MAP_Supply_PreUpdate.supply_email}</td>
				</tr>
				<tr>
					<td>廠商統編</td>
					<td>${MAP_Supply_PreUpdate.supply_conumber}</td>
				</tr>
				<tr>
					<td>廠商銀行帳戶</td>
					<td>${MAP_Supply_PreUpdate.supply_bankaccount}</td>
				</tr>
				<tr>
					<td>最低出貨金額</td>
					<td>${MAP_Supply_PreUpdate.supply_limit}</td>
				</tr>
				<tr>
					<td>廠商VIP</td>
					<td><select name="supply_vip" size="1">
							<option value="member" selected>一般會員</option>
							<option value="vip" ${MAP_Supply_PreUpdate.supply_vip_Y}>付費會員</option>
							<option value="banned" ${MAP_Supply_PreUpdate.supply_vip_OUT}>停權</option>
					</select><br /></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<button type="submit" class="btn btn-primary">修改</button>
						<button type="button" class="btn btn-warning" onclick="showAtRight('<c:url value="show_Supply" />')">取消</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>