<%--
  Created by IntelliJ IDEA.
  User: Constantine
  Date: 16.12.22
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="menu.jsp"%>

<title>Profile</title>

<c:if test="${sessionScope.get('token') != null}">
  <table class="table table-hover">
    <thead>
      <tr>
        <th scope="col">Name</th>
        <th scope="col">Username</th>
      </tr>
    </thead>
    <tbody>
    <%--gotta make this shit work--%>
    <c:forEach var="user" items="${applicationScope.get('USERS')}">
      <tr>
        <td>${user.name}</td>
        <td>${user.username}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</c:if>
