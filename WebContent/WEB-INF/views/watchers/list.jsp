<%@ include file="/WEB-INF/views/inc/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div>
		<h1>Watchers</h1>
		<h2>List of all watchers</h2>
		<div id="watchList">
			<table>
			 <thead><tr><td>name</td><td>status</td><td>actions</td></tr></thead>
			 <tbody>
			<c:forEach items="${watchers}" var="watch">
				<tr>
					<td><c:out value="${ watch.name }"/></td>
					<td><c:out value="${ watch.status }"/></td>
					<td>todo... actions: de/activate / info</td>
				</tr>
			</c:forEach>
			 </tbody>
			</table>
		</div>
	</div>
</body>
</html>