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
	<div class="d1">
		<table>
			<tr style="height: 20px;">
				<td align="center">標題：</td>
				<td>${MAP_Announcement_SelectOne.announcement_title}</td>
			</tr>
			<tr style="height: 250px;">
				<td align="center">標題圖片：</td>
				<td>
					<div>
						<figure class="figure w-100">
							<img id="img1" src="/billboardPics/${MAP_Announcement_SelectOne.announcement_picture1}"
								class="figure-img img-fluid rounded" style="height: 225px; width: 225px"
								alt="標題圖片預覽區">
						</figure>
					</div>
				</td>
			</tr>
			<tr>
				<td align="center">內容：</td>
				<td>${MAP_Announcement_SelectOne.announcement_content}</td>
			</tr>
			<tr style="height: 250px;">
				<td align="center">內容圖片（一）：</td>
				<td>
					<div>
						<figure class="figure w-100">
							<img id="img1" src="/billboardPics/${MAP_Announcement_SelectOne.announcement_picture2}"
								class="figure-img img-fluid rounded" style="height: 225px; width: 225px"
								alt="內容圖片預覽區（一）">
						</figure>
					</div>
				</td>
			</tr>
			<tr style="height: 250px;">
				<td align="center">內容圖片（二）：</td>
				<td>
					<div>
						<figure class="figure w-100">
							<img id="img1" src="/billboardPics/${MAP_Announcement_SelectOne.announcement_picture3}"
								class="figure-img img-fluid rounded" style="height: 225px; width: 225px"
								alt="內容圖片預覽區（二）">
						</figure>
					</div>
				</td>
			</tr>
			<tr>
				<td align="center">上下架：</td>
				<td>${MAP_Announcement_SelectOne.announcement_status}</td>
				<!-- <td></td>
				<td></td> -->
			</tr>
			<tr>
				<td align="center">時間：</td>
				<td>${MAP_Announcement_SelectOne.announcement_time}</td>
			</tr>
		</table>
	</div>
</body>
</html>