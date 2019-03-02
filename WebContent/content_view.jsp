<%--게시글 내용보기 페이지 --%>
<%--게시글 수정, 목록이동, 게시글 삭제, 답변 --%>

<%@
 page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<title>Insert title here</title>
</head>
<body>
	
	<form action="modify.do" method="post">
		<table width="500" border="1">
		
			<%--modify.do페이지로 이동시에 id를 request영역에 저장하기위한 작업! --%>
			<%--[추후] 변경이 필요함 --%>
			<input type="hidden" name="bId" value="${requestScope.content_view.bId}">

			<tr>
				<td> 게시글/답변 번호 </td>
				<td> ${requestScope.content_view.bId} </td>
			</tr>
			<tr>
				<td> 조회수 </td>
				<td> ${requestScope.content_view.bHit} </td>
			</tr>
			<tr>
				<td> 작성자 </td>
				<td> <input type="text" name="bName" value="${requestScope.content_view.bName}"></td>
			</tr>
			<tr>
				<td> 게시글/답변 제목 </td>
				<td> <input type="text" name="bTitle" value="${requestScope.content_view.bTitle}"></td>
			</tr>
			<tr>
				<td> 게시글/답변 내용 </td>
				<td> <textarea rows="10" name="bContent" >${requestScope.content_view.bContent}</textarea></td>
			</tr>
		</table>
		
		<a href="list.do">목록으로 이동</a> &nbsp;&nbsp;
		<input type="submit" value="게시글 수정"> &nbsp;&nbsp;
		<a href="delete.do?bId=${requestScope.content_view.bId}">게시글 삭제</a> &nbsp;&nbsp; 
		<a href="reply_view.do?bId=${requestScope.content_view.bId}">답변 달기</a>
		
	</form>	
	
</body>
</html>