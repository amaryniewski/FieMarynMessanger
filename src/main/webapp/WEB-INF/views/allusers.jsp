<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Contacts</title>

    <style>
        tr:first-child {
            font-weight: bold;
            background-color: #C6C9C4;
        }
    </style>
</head>

<%@include file="/WEB-INF/views/header.jspf" %>

<body>
<h2>Contacts</h2>
<a href="<c:url value='/welcomescreen' />"> Home </a>
<table>
    <tr>
        <td>First Name</td>
        <%--<td>Second Name</td>--%>
        <td>Nickname</td>
        <td>Operations</td>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.firstName}</td>
            <%--<td>${user.secondName}</td>--%>
            <td>${user.nickname}</td>
            <td>
                <a href="<c:url value='/chat-${user.nickname}-user' />">chat</a>
                <p style="display:inline;"></p>
                <a href="<c:url value='/edit-${user.nickname}-user' />">edit</a>
                <p style="display:inline;"></p>
                <a href="<c:url value='/delete-${user.nickname}-user' />">delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

<c:choose>
    <c:when test="${chat}">
        <%@include file="/WEB-INF/views/chat.jspf" %>
    </c:when>
    <c:otherwise>
        <p> any contact selected to chat </p>
    </c:otherwise>
</c:choose>
<br/>
</body>
</html>