
<%--게시글의 리스트 페이지 --%>
<%-- 
[참고]
1. 게시글과 답변의 id(primary key)는 서로 같은 넘버링 방식을 사용

2. id의 증가방식은 idGroup table에서 id를 보관하고 참조함

3. 해당 소스의 출처는 infleand의 '신입프로그래머를 위한 실전 jsp강좌' 이며 나의 상황에 따라 커스터마이징함

4. 날짜부분 미구현

 --%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	
	<table width="500" border="1">
		<tr>
			<td>게시글/답변 번호</td>	
			<td>작성자</td>
			<td>게시글/답변 제목</td>		
			<td>작성일</td>
			<td>조회수</td>	
		</tr>
		
		<!-- db table의 data를 표출 -->
		<c:forEach items="${requestScope.list}" var="dto">
		<tr>
			<td>${dto.bId}</td>
			<td>${dto.bName}</td>
			<td>
				<%-- indent 수 만큼 들여쓰기 --%>
				<c:forEach begin="1" end="${dto.bIndent}">-</c:forEach>
				<a href="content_view.do?bId=${dto.bId}">${dto.bTitle}</a>
			</td>	
			<td>${dto.bDate}</td>
			<td>${dto.bHit}</td>
		</tr>
		</c:forEach>
		
		<tr>
			<td colspan="5"> <a href="write_view.do">게시글 작성</a> </td>
		</tr>
	</table>
	
</body>
</html>