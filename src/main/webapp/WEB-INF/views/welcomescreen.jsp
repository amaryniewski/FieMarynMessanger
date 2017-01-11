<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Home Page</title>
</head>
<body>


<%@include file="/WEB-INF/views/header.jspf" %>

<h1>Home Page</h1>

<c:choose>
    <c:when test="${nickname=='Guest' || nickname==''}">
        <p> Please log in </p>
        <IMG SRC="http://www.photographybylaporte.com/wp-content/uploads/2015/08/01-8-post/under-construction-1024x323(pp_w750_h236).jpg
" ALT="workinprogress">
    </c:when>
    <c:otherwise>
        <a href="<c:url value='/manageUsers' />"> Contacts </a>
    </c:otherwise>
</c:choose>



</body>

</html>