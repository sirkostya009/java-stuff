<%@include file="head.jsp"%>

<c:choose>
    <c:when test="${sessionScope.get('user') == null}">
        <c:redirect url="/authenticate"/>
    </c:when>
    <c:otherwise>
        <c:redirect url="/profile"/>
    </c:otherwise>
</c:choose>
