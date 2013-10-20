<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table class="CSSTableGenerator">
    <tr><td>Station info</td></tr>
    <c:forEach items="${stationInfoList}" var="stationInfo">
        <tr><td><c:out value="${stationInfo}" /></td></tr>
    </c:forEach>
</table>
