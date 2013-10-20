<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table class="CSSTableGenerator">
    <tr>
        <td>Route number</td>
    </tr>
    <c:forEach items="${unusedRoutesList}" var="unusedRoute">
        <tr>
            <td><c:out value="${unusedRoute.routeNumber}"/></td>

            <%-- not for while initialization is lazy

            <c:set value="" var="concatStationsInfo" />
            <c:forEach items="${unusedRoute.stationsList}" var="station" varStatus="item">
                <c:set value="${concatStationsInfo} ${station.stationInfo}" var="concatStationsInfo"/>
                <c:if test="${not item.last}">
                    <c:set value="${concatStationsInfo}, " var="concatStationsInfo" />
                </c:if>
             </c:forEach>

            <td><c:out value="${concatStationsInfo}" default="" /></td> --%>
        </tr>
    </c:forEach>
</table>