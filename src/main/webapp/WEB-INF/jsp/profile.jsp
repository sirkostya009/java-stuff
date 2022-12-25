<%--
  Created by IntelliJ IDEA.
  User: Constantine
  Date: 16.12.22
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@include file="menu.jsp"%>

<title>Profile</title>

<c:if test="${sessionScope.get('user') != null}">
  <h1>Henlo, ${sessionScope.get('user').name}</h1>
</c:if>
