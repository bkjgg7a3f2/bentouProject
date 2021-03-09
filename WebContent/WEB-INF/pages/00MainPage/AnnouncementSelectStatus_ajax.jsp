<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<script>
	var JSON_Announcement =
<%=request.getAttribute("JSON_Announcement")%>
	;
	var tbodyStr = "announceTbody"; //準備寫入表格的tbody之id
	if (JSON_Announcement != null) {
		var statusNum = JSON_Announcement.length; //取得公告筆數
		showStatus(statusNum, tbodyStr, JSON_Announcement);
	} else {
		var statusNum = 0;
	}

	//產品表格生成程式
	function showStatus(statusNum, tbodyName, statusInfo) {
		//statusNum 		公告筆數
		//tbodyName 	輸入tbody的字串名
		//statusInfo 		公告清單
		$("#" + tbodyName).empty(); //清除原有資料
		for (i = 0; i < statusNum; i++) {
			let innerTdStr = ""; //準備寫入的td字串
			let trNum = i + 1;
			//td字串  	
			innerTdStr = "<th scope=\"row\">" + trNum + "</th>" + "<td>"
					+ statusInfo[i].announcement_title + "</td>" + "<td >"
					+ statusInfo[i].announcement_time + "</td>";
			//寫入table
			$("#" + tbodyName).append(
					"<tr id=\"announceTr"+i+"\" name=\""+statusInfo[i].key+"\">"
							+ innerTdStr + "</tr>");
		}
	};
	
	
</script>
<body>

	<table class="table table-hover  table-sm">
		<thead>
			<tr name="theadRow">
				<th scope="col">#</th>
				<th scope="col">標題</th>
				<th scope="col">時間</th>
			</tr>
		</thead>
		<tbody id="announceTbody">
		</tbody>
	</table>
</body>
</html>