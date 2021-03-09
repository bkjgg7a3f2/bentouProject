<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<body>
	<div id="commentZone${ key }" class="card card-body mt-1"></div>

	<script>
		var JSON_cu =
	<%=request.getAttribute("JSON_viewEvaluation_cu")%>
		;
		var JSON_su =
	<%=request.getAttribute("JSON_viewEvaluation_su")%>
		;
		var length = JSON_cu.length;
		if (length !== 0) {
			for (i = 0; i < length; i++) {
				var custStr = "";
				var shopStr = "";
				// 				var custTimeStr = $.format.date(JSON_cu[i].msgTime,"dd/MM/yyyy HH:mm:ss");
				// 				var replyTimeStr = $.format.date(JSON_su[i].replyTime,"dd/MM/yyyy HH:mm:ss");	
				//評論內容
				custStr = "<div class=\"row w-100\"><div class=\"col-sm-4 text-right border-right\">"
						+ JSON_cu[i].cus_name
						+ "</div>"
						+ "<div class=\"col-sm-5\"> 評價："
						+ JSON_cu[i].cstmr_fraction
						+ "</div>"
						+ "<div class=\"col-sm-3 text-center\"><small>"
						+ JSON_cu[i].msgTime
						+ "</small></div>"
						+ "</div><div class=\"row w-100 \"><div class=\"col-sm-4 border-right\"></div><div class=\"col-sm border-top\">"
						+ JSON_cu[i].cstmr_evaluation + "</div></div>";
				//評論框線樣式
				custStr = "<div class=\"row\"><div class=\"col-sm-12 border border-secondary rounded my-2\">"
						+ custStr + "</div></div>";

				if (JSON_su[i].supply_evaluation != null) {
					shopStr = "<div class=\"row\">"
							+ "<div class=\"col-sm-4 text-right\">店家回覆：</div>"
							+ "<div class=\"col-sm-5 \">"
							+ JSON_su[i].supply_evaluation + "</div>"
							+ "<div class=\"col-sm-3 text-center\"><small>"
							+ JSON_su[i].replyTime + "</small></div>"
							+ "</div>";
					shopStr ="<div class=\"alert alert-secondary mb-2 p-1\" role=\"alert\">"
							+ shopStr+"</div>"
				}
				;
				$("#commentZone${ key }").append(custStr + shopStr)
			}
		} else {
			$("#commentZone${ key }").html("目前無評論")
		}
	</script>
</body>