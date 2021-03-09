<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>
	<div id="contentWith${supply_id }"></div>
</body>

<script>
	//讀json資料
	var JSON_dialogue =
<%=request.getAttribute("JSON_dialogue")%>
	;
	//取得寫入Div
	var divID = "contentWith${supply_id}";
	//設定對話屬性參數
	//myClass 	使用者方的span之Class字串
	//myDiv		使用者方的包裹div字串
	//myClass 	對象的span之Class字串
	//myDiv		對象的包裹div字串
	var divSettings = {
		myClass : "px-2  border-secondary  bg-light",
		myDiv : "<div class=\"my-2  text-right\"></div>",
		yourClass : "px-2 text-white  border-primary  bg-primary",
		yourDiv : "<div class=\"my-2\"></div>",
	};

	//說話對象圖片字串
	var imgShopStr_ajax = "<img name=\"shopPic\" src=\"/supplyPics/${supply_image}\" class=\"mh-100 rounded-circle\" style=\"height:3em;max-width:3em; object-fit: cover\">"

	if (JSON_dialogue != null) {
		for (i_dialogue = 0; i_dialogue < JSON_dialogue.length; i_dialogue++) {
			const timeConstAjax = new Date(JSON_dialogue[i_dialogue].sendTime)
			let timeStr = "<small class=\"text-black-50\">"
					+ timeGetter4Ajax(timeConstAjax) + "</small><br/>";
			let chatStr = "<div name=\""+JSON_dialogue[i_dialogue].speaker+"\" class=\"text-break border rounded-lg align-middle d-inline-block\"> "
					+timeStr +  JSON_dialogue[i_dialogue].msg + " </div>"
			$("#" + divID).append(chatStr);
		}
		;
	};
	$("div[name='customer']").addClass(divSettings.myClass).wrap(
			divSettings.myDiv);
	$("div[name='shop']").addClass(divSettings.yourClass).wrap(
			divSettings.yourDiv).before(imgShopStr_ajax);
	$("img[name='shopPic']").one("error", function(e) {
		$(this).attr("src", "/supplyPics/default.jpg");
	});

	//時間字串
	function timeGetter4Ajax(time) {
		let ajax_time = time.getFullYear() + "-" + (time.getMonth() + 1) + "-"
				+ time.getDate() + " " + time.getHours() + ":"
				+ time.getMinutes();
		return ajax_time;
	}
</script>