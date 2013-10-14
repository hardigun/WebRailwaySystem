<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Route add</title>
        <spring:url value="/resources/jquery-1.10.2.min.js" var="jquery_url" />
        <spring:url value="/resources/route-add.js" var="add_route_scripts_url" />
        <script src="${jquery_url}"></script>
        <script src="${add_route_scripts_url}"></script>
    </head>
    <body>
        <select id="stationInfoSelect">
            <c:forEach items="${stationInfoList}" var="stationInfo">
                <option value="${stationInfo.id}">${stationInfo.title}</option>
            </c:forEach>
        </select>

        <c:out value="${resultMessage}" default="" />
        <form:form modelAttribute="routeEntity" method="POST" action="/route/add">

            <form:label path="routeNumber">Route number: </form:label>
            <form:input path="routeNumber" />
            <c:out value="${routeNumberErrors}" default=""/>

            <input type="button" id="addStationButton" value="Add station">&nbsp;
            <input type="button" id="removeStationButton" value="Remove station">&nbsp;

            <c:out value="${routeStationsErrors}" default=""/>
            <table id="stationsTable">
                <c:forEach items="${routeEntity.stationsList}" varStatus="stationIndex" var="station">
                    <tr id="station_${stationIndex.index}">
                        <td><input type="checkbox" class="stationCheckboxes" row_id="station_${stationIndex.index}" /></td>
                        <td>
                            <select id="stationinfo_${stationIndex.index}" name="stationsinfo[]">
                                <c:forEach items="${stationInfoList}" var="stationInfo">
                                    <option value="${stationInfo.id}"
                                            <c:if test="${station.stationInfo == stationInfo}">
                                                   selected="selected"
                                            </c:if>
                                            >${stationInfo.title}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <input type="text" id="timeoffset_${stationIndex.index}" name="timeoffsets[]"
                                   value="${station.timeOffset}" />
                        </td>
                        <td>
                            <c:forEach items="${stationsErrors}" var="stationError">
                                <c:if test="${stationError.key == station}" >
                                    <c:out value="${stationError.value}" />
                                </c:if>
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <input type="submit" value="Submit" />
        </form:form>
  </body>
</html>