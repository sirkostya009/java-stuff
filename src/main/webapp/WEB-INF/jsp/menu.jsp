<%--
  Created by IntelliJ IDEA.
  User: Constantine
  Date: 14.12.22
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>

<%@ include file="head.jsp"%>

<c:set var="firstMenuItemLink">
  <c:if test='<%=request.getRequestURI().endsWith("all_users.jsp")%>'>profile</c:if>
  <c:if test='<%=request.getRequestURI().endsWith("profile.jsp")%>'>all-users</c:if>
</c:set>

<ul class="nav justify-content-end">
  <li class="nav-item">
    <a class="nav-link" href="${firstMenuItemLink}">
      <c:if test="${firstMenuItemLink == 'all-users'}">All users</c:if>
      <c:if test="${firstMenuItemLink == 'profile'}">Profile</c:if>
    </a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="login">Log
      <c:if test="${sessionScope.get('user') != null}"> out</c:if>
      <c:if test="${sessionScope.get('user') == null}"> in</c:if>
    </a>
  </li>
</ul>
