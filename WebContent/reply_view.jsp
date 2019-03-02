<%-- 답변 입력양식 페이지 --%>

<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<title>Insert title here</title>
</head>
<body>

	<form action="reply.do" method="post">
		<table width="500" border="1">
			 
			<input type="hidden" name="bGroup" value="${requestScope.reply_view.bGroup}">
			<input type="hidden" name="bStep" value="${requestScope.reply_view.bStep}">
			<input type="hidden" name="bIndent" value="${requestScope.reply_view.bIndent}">
			
			<tr>
				<td> 게시글/답변 번호 </td>
				<td> ${requestScope.reply_view.bId} </td>
			</tr>
			<tr>
				<td> 조회수 </td>
				<td> ${requestScope.reply_view.bHit} </td>
			</tr>
			<tr>
				<td> 작성자 </td>
				<td> <input type="text" name="bName" value="${requestScope.reply_view.bName}"></td>
			</tr>
			<tr>
				<td> 게시글/답변 제목 </td>
				<td> <input type="text" name="bTitle" value="${requestScope.reply_view.bTitle}"></td>
			</tr>
			<tr>
				<td> 게시글/답변 내용 </td>
				<td> <textarea rows="10"  name="bContent">${requestScope.reply_view.bContent}</textarea></td>
			</tr>
			<tr>
				<td colspan="2">
				<input type="submit" value="답변 등록"> 
				<a href="list.do" >목록으로 이동</a>
				</td>
			</tr>
	
		</table>
	</form>
		
</body>
</html>