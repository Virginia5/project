<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>视频接口（采用本地浏览器驱动）</title>
</head>
<body>
	<form id=“query” action="video/local" method="get">
		<table>
			<tr>
				<td>视频保存地址：</td>
				<td><input type="text" name="saveVideo" id="saveVideo"></td>
			</tr>
			<tr>
				<td>chrome driver安装地址：</td>
				<td><input type="text" name="chromeDriver" id="chromeDriver"></td>
			</tr>
			<tr>
				<td>key words or url：</td>
				<td><input type="text" name="url" id="url"></td>
			</tr>
		</table>
		<input type="submit">

	</form>
</body>
</html>