<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<body>
	<div class="container mb-0" >
		<div class="row mt-3">
			<div class="col bg-light text-bold text-center">
				<h3>公告標題：${MAP_Announcement.announcement_title}</h3>
			</div>
		</div>
		<div class="row mt-3">
			<div id="img1Div" class="col-4 border-right">
				<figure class="figure "> <img id="img1"
					src="/billboardPics/${MAP_Announcement.announcement_picture1}"
					class="figure-img img-fluid rounded h-100 "
					style="height: 225px; width: 225px; object: fit" alt="標題圖片預覽區">
				</figure>
			</div>
			<div class="col-8">
				<div class="row mt-3" style="height: 150px;">
					<div class="col ">
						<h4>=公告內容=</h4>
						<p>${MAP_Announcement.announcement_content}</p>
					</div>
				</div>
				<div class="row mt-3 py-3 border-top">
					<div id="img2Div" class="col-6  text-center">
						<figure class="figure "> <img id="img2" name="img2"
							src="/billboardPics/${MAP_Announcement.announcement_picture2}"
							class="figure-img img-fluid rounded h-100 align-center"
							style="height: 150px; width: 150px; object: fit" alt="內容圖片預覽區（一）" />
						</figure>
					</div>
					<div id="img3Div" class="col-6 text-center border-left">
						<figure class="figure "> <img id="img3" name="img3"
							src="/billboardPics/${MAP_Announcement.announcement_picture3}"
							class="figure-img img-fluid rounded h-100 align-center"
							style="height: 150px; width: 150px; object: fit" alt="內容圖片預覽區（二）">
						</figure>
					</div>
				</div>
			</div>
		</div>
		<div class="row bg-light">
			<div class="col"></div>
			<div class="col">
				<button name="announceBtn" type="button"
					class="btn btn-danger btn-block" onclick="showannouncement()">回公告列表</button>
			</div>
			<div class="col"></div>
		</div>

	</div>
</body>
<script>
	$("img").one("error", function() {
		let divNameVal = $(this).attr("name");
		$(this).parents("#"+divNameVal+"Div").addClass("d-none");
	});
	
</script>