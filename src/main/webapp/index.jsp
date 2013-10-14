<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>WebRailwaySystem v1.0</title>
  </head>
  <body>
    <h3>Welcome! Select action, please: </h3>
    <ul>
        <li><a href='<c:url value="/station-info/add" />'>New station info</a></li>
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
        <li><a href='<c:url value="/shedule/by-station" />'>Shedule by station</a></li>
        <li><a href='<c:url value="/shedule/between-stations" />'>Search trains between stations</a></li>
    </ul>
  </body>
</html>