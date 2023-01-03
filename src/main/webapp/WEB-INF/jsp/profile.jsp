<%--
  Created by IntelliJ IDEA.
  User: Constantine
  Date: 16.12.22
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@include file="menu.jsp"%>

<title>Profile</title>

<jsp:useBean id="user" scope="session" type="org.sirkostya009.models.User"/>

<c:if test="${not empty user}">
  <h1>Henlo, ${user.name}</h1>
</c:if>
