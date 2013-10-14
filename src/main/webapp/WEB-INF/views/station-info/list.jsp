<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Station info list</title>
    </head>
    <body>
        <ul>
            <c:forEach items="${stationInfoList}" var="stationInfo">
                <li><c:out value="${stationInfo}"/></li>
            </c:forEach>
        </ul>
    </body>
</html>

