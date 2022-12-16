<%--
  Created by IntelliJ IDEA.
  User: Constantine
  Date: 14.12.22
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>

<%@ include file="head.jsp"%>

<ul class="nav justify-content-end">
  <li class="nav-item">
    <a class="nav-link" href="authenticate">Log
      <c:if test="${sessionScope.get('token') != null}"> out</c:if>
      <c:if test="${sessionScope.get('token') == null}"> in</c:if>
    </a>
  </li>
</ul>
