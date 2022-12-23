<%--
  Created by IntelliJ IDEA.
  User: Constantine
  Date: 18.12.22
  Time: 20:17
  To change this template use File | Settings | File Templates.
--%>
<%@include file="menu.jsp"%>

<title>Users</title>

<c:if test="${sessionScope.get('user') != null}">
    <table class="table table-hover w-50">
        <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Username</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${sessionScope.get('users')}">
            <tr>
                <td>${user.name}</td>
                <td>${user.username}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
