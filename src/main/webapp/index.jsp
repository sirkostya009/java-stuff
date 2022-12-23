<%@include file="head.jsp"%>

<c:choose>
    <c:when test="${sessionScope.get('user') == null}">
        <c:redirect url="/authenticate"/>
    </c:when>
    <c:otherwise>
        <c:redirect url="/profile.jsp"/>
    </c:otherwise>
</c:choose>
