<%--
  Created by IntelliJ IDEA.
  User: Constantine
  Date: 14.12.22
  Time: 21:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="head.jsp"%>

<title>Login</title>

<style>
    div {
        height: 90%;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
    }
    .form {
        flex-direction: column;
        display: flex;
    }
    label {
        padding-bottom: 5px;
    }
</style>

<div>
    <c:if test="${param.get('retry') == '1'}">
        Username or password incorrect. Try again.
    </c:if>
    <form method="post" action="authenticate" class="form">
        <label for="username">
            <input type="text" name="username" id="username" placeholder="username" required>
        </label>
        <label for="password">
            <input type="password" name="password" id="password" placeholder="password" required>
        </label>

        <input type="submit" value="Login">
    </form>
</div>
