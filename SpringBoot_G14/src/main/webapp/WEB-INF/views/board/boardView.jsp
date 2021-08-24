<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/css/board.css" >
<script type="text/javascript">
function open_win(url, name) {
	window.open(url, name, "toolbar=no, menubar=no, scrollbars=no, "
				+ " resizable=no, width=500, height=500");
}
</script>
</head>
<body>
<div id="wrap" align="center">
<h1>게시글 상세보기</h1>
<table>
	<tr><th width="150">작성자</th><td width="335">${board.userid}</td>
		<th width="150">이메일</th><td width="335">${board.email}</td></tr>
	<tr><th>작성일</th><td><fmt:formatDate value="${board.writedate}"/></td>
		<th>조회수</th><td>${board.readcount }</td></tr>
	<tr><th>제목</th><td colspan="3">${board.title}</td></tr>
	<tr>
		<th>내용</th>
		<td valign="top">${board.content}</td>
		<th>이미지</th>
		<td align="center"  valign="top">
			<c:choose>
				<c:when test="${empty board.image}">
					<img src="/upload/noimage.jpg" width="300">
				</c:when>
				<c:otherwise>
					<img src="/upload/${board.image}" width="300">
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table><br> <br>
<input type="button" value="게시글 리스트" onclick="location.href='main'">
	<input type="button" value="게시글 수정" 
	onclick="open_win('boardEditForm?num=${board.num}', 'update')">
	<input type="button" value="게시글 삭제" 
	onclick="open_win('boardDeleteForm?num=${board.num}', 'delete')">
</div>
<br />
<c:set var="now" value="<%=new java.util.Date()%>"></c:set>
<div id="wrap" align="center">
<form action="addReply" method="post" name="frm2">
<input type="hidden" name="boardnum" value="${board.num}">
<input type="hidden" name="userid" value="${loginUser.id}">
<table>
	<tr><th>작성자</th><th>작성일시</th><th>내용</th><th>&nbsp;</th></tr>
	<tr align="center">
		<td width="100">${loginUser.id}</td>
		<td width="100">
			<fmt:formatDate value="${now}"	pattern="MM/dd HH:mm"></fmt:formatDate>
		</td>
		<td width="670"><input type="text" name="content" size="80"></td>
		<td width="100"><input type="submit" value="답글작성"></td>
	</tr>
	<c:choose>
		<c:when test="${empty ReplyList}">
			<tr><td colspan="4" align="center">첫 댓글을 남겨주세요 ㅠㅠ</td></tr>
		</c:when>
		<c:otherwise>
			<c:forEach var="reply" items="${ReplyList}">
				<tr><td align="center">${reply.userid}</td>
					<td align="center"><fmt:formatDate value="${reply.writedate}"	
										pattern="MM/dd HH:mm"></fmt:formatDate></td>
					<td>${reply.content}</td>
					<td align="center">
						<c:if test="${reply.userid==loginUser.id}">
							<input type="button" value="삭제" 	onclick="location.href=
							'deleteReply?num=${reply.num}&boardnum=${reply.boardnum}'">
						</c:if>&nbsp;
					</td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>
</body>
</html>