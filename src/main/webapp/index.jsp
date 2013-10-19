<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>WebRailwaySystem v1.0</title>
  </head>
  <body>
    <c:choose>
        <c:when test="${pageContext.request.userPrincipal.authenticated}">
            User name: ${pageContext.request.userPrincipal.name}&nbsp;
            <a href="/user/show/${pageContext.request.userPrincipal.name}">Show profile</a>&nbsp;
            <a href="/auth/logout">Logout</a>
        </c:when>
        <c:otherwise>
            <a href="/auth/login">Login</a>
            <a href="/user/add">Registration</a>
        </c:otherwise>
    </c:choose>

    <h3>Welcome! Select action, please: </h3>
    <ul>
        <security:authorize access="hasRole('ROLE_MANAGER')">
            <li><a href='<c:url value="/station-info/add" />'>New station info</a></li>
        </security:authorize>
        <li><a href='<c:url value="/station-info/list" />'>List of station info</a></li>
        <br/>
        <li><a href='<c:url value="/train/add" />'>New train</a></li>
        <li><a href='<c:url value="/train/list-of-unused" />'>List of unused trains</a></li>
        <br/>
        <li><a href='<c:url value="/route/add" />'>New route</a></li>
        <li><a href='<c:url value="/route/list-of-unused" />'>List of unused routes</a></li>
        <br/>
        <li><a href='<c:url value="/shedule/add" />'>New shedule item</a></li>
        <br/>
        <li><a href='<c:url value="/shedule/search/by-station" />'>Shedule by station</a></li>
        <li><a href='<c:url value="/shedule/search/between-stations" />'>Search trains between stations</a></li>
        <br/>
        <li><a href='<c:url value="/shedule/show/trains-by-routes" />'>Show trains</a></li>
        <li><a href='<c:url value="/ticket/show" />'>Show passengers with tickets</a></li>
        <br/>
        <li><a href='<c:url value="/user/list" />'>Manage users</a></li>
    </ul>
  </body>
</html>