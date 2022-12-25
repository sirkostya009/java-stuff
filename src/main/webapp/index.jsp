<%@include file="WEB-INF/jsp/head.jsp"%>

<c:choose>
    <c:when test="${sessionScope.get('user') == null}">
        <c:redirect url="/login"/>
    </c:when>
    <c:otherwise>
        <c:redirect url="/profile"/>
    </c:otherwise>
</c:choose>
