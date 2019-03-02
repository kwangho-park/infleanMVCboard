
<%--게시글 작성 양식 --%>

<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<title>Insert title here</title>
</head>
<body>

	<form action="write.do" method="post">
		<table width="500" cellpadding="0" cellspacing="0" border="1">
			<tr>
				<td> 작성자 </td>
				<td> <input type="text" name="bName" size = "50"> </td>
			</tr>
			<tr>
				<td> 게시글/답변 제목 </td>
				<td> <input type="text" name="bTitle" size = "50"> </td>
			</tr>
			<tr>
				<td> 게시글/답변 내용 </td>
				<td> <textarea name="bContent" rows="10" ></textarea> </td>
			</tr>
			<tr >
				<td colspan="2"> <input type="submit" value="게시글 입력"> &nbsp; &nbsp; <a href="list.do">목록보기</a></td>
			</tr>
		</table>
	</form>
	
</body>
</html>