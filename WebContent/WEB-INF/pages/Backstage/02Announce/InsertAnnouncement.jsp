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
	font-size: 18px;
}

td {
	width: 400px;
}
</style>
<script type="text/javascript">
	
</script>
</head>
<body>
	<form action="<c:url value="/manager/announcementInsert" />" onsubmit="return chk()"
		method="post" enctype="multipart/form-data">
		<table style="margin-left: 30px">
			<tr>
				<td align="center"><label for="inputTitle">標題：</label></td>
				<td><input type="text" name="title"
					class="form-control form-control-sm" style="max-width: 25em;"
					id="inputTitle"></td>
			</tr>
			<tr style="height: 250px;">
				<td align="center">
					<div>
						<figure class="figure w-100"> <img id="img1"
							src="/billboardPics/noPic.png"
							class="figure-img img-fluid rounded"
							style="height: 225px; width: 225px;" alt="標題圖片預覽區"> <figcaption
							style="font-size: 13px; font-style: italic"
							class="figure-caption text-center">標題預覽圖片</figcaption> </figure>
					</div>
				</td>
				<td><input type="file" name="titleImage"
					accept="image/gif, image/jpeg, image/png, image/bmp, image/jpg"
					onchange="xmTanUploadImg1(this)" /></td>
			</tr>
			<tr>
				<td align="center"><label for="inputContent">內容：</label></td>
				<td><textarea name="content"
					class="form-control form-control-sm" id="inputContent"
					style="max-width: 25em; height: 120px;"></textarea></td>
			</tr>
			<tr style="height: 250px;">
				<td align="center">
					<div>
						<figure class="figure w-100"> <img id="img2"
							src="/billboardPics/noPic.png"
							class="figure-img img-fluid rounded"
							style="height: 225px; width: 225px;" alt="標題圖片預覽區（一）"> <figcaption
							style="font-size: 13px; font-style: italic"
							class="figure-caption text-center">內容預覽圖片（一）</figcaption> </figure>
					</div>
				</td>
				<td><input type="file" name="contentImage1"
					accept="image/gif, image/jpeg, image/png, image/bmp, image/jpg"
					onchange="xmTanUploadImg2(this)" /></td>
			</tr>
			<tr style="height: 250px;">
				<td align="center">
					<div>
						<figure class="figure w-100"> <img id="img3"
							src="/billboardPics/noPic.png"
							class="figure-img img-fluid rounded"
							style="height: 225px; width: 225px;" alt="內容圖片預覽區（二）"> <figcaption
							style="font-size: 13px; font-style: italic"
							class="figure-caption text-center">內容預覽圖片（二）</figcaption> </figure>
					</div>
				</td>
				<td><input type="file" name="contentImage2"
					accept="image/gif, image/jpeg, image/png, image/bmp, image/jpg"
					onchange="xmTanUploadImg3(this)" /></td>
			</tr>
			<tr>
				<td align="center"><label>上下架：</label></td>
				<td><label><input type="radio" name="status" value="on"
						id="yes" checked />上架</label> <label><input type="radio"
						name="status" value="off" id="no" />下架</label></td>

			</tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit" class="btn btn-primary">新增</button>
					<button type="button" class="btn btn-warning"
						onclick="showAtRight('<c:url value="show_Announcement" />')">取消</button>
					<button type="button" class="btn btn-success"
						onclick="autoFillInsert()">一鍵輸入</button>
				</td>
			</tr>
		</table>
	</form>
</body>

<script>
	$("input").attr("autocomplete", "off");
</script>
</html>