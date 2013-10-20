<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="railwaysystem" uri="http://tsystem.com/tags/railwaysystem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<spring:url value="/resources/route-add.js" var="add_route_scripts_url" />
<script src="${add_route_scripts_url}"></script>

<select id="stationInfoSelect">
    <c:forEach items="${stationInfoList}" var="stationInfo">
        <option value="${stationInfo.id}">${stationInfo.title}</option>
    </c:forEach>
</select>

<railwaysystem:message-handler message="${message}" />

<form:form modelAttribute="routeEntity" method="POST" action="/route/add">
    <table>
        <tr>
            <td> <form:label path="routeNumber">Route number: </form:label></td>
            <td><form:input path="routeNumber" /></td>
            <td>
                <c:if test="${not empty routeNumberErrors}">
                    <span class="message-error"><c:out value="${routeNumberErrors}" /></span>
                </c:if>
            </td>
        </tr>

        <tr style="text-align: center;">
            <td colspan="3">
                <input type="button" id="addStationButton" value="Add station">&nbsp;
                <input type="button" id="removeStationButton" value="Remove station">&nbsp;
            </td>
        </tr>

        <!-- temp solution -->
        <tr><td colspan="3"></td></tr>

        <c:if test="${not empty routeStationsErrors}">
            <tr>
                <td colspan="3">
                    <span class="message-error"><c:out value="${routeStationsErrors}" /></span>
                </td>
            </tr>
        </c:if>

        <!-- temp solution -->
        <tr><td colspan="3"></td></tr>

        <tr>
            <td colspan="3">
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
                                        <span class="message-error"><c:out value="${stationError.value}" /></span>
                                    </c:if>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>

        <tr>
            <td colspan="3"><input type="submit" value="Submit" /></td>
        </tr>
    </table>
</form:form>
