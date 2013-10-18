<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="railwaysystem" uri="http://tsystem.com/tags/railwaysystem"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
        <title>Shedule by station</title>
        <spring:url value="/resources/jquery-1.10.2.min.js" var="jquery_url" />
        <spring:url value="/resources/shedule-search.js" var="shedule_scripts_url" />
        <script src="${jquery_url}"></script>
        <script src="${shedule_scripts_url}"></script>
  </head>
  <body>
    <form:form modelAttribute="sheduleFilter" method="POST" action="/shedule/search/by-station">
        <fieldset>
            <legend>Shedule search params</legend>
            <table>
                <tr>
                    <td><form:label path="stationInfo">Select station: </form:label></td>
                    <td>
                        <form:select path="stationInfo">
                            <c:forEach items="${stationInfoList}" var="stationInfoFromList">
                                <option value="${stationInfoFromList.id}"
                                        <c:if test="${sheduleFilter.stationInfo == stationInfoFromList}">
                                            selected="selected"
                                        </c:if>
                                        >${stationInfoFromList.title}
                                </option>
                            </c:forEach>
                        </form:select>
                    </td>
                    <td><form:errors path="stationInfo" /></td>
                </tr>
                <tr>
                    <td><form:label path="date">Enter date(ex. 25.10.2013 17:00): </form:label></td>
                    <td><form:input path="date" /></td>
                    <td><form:errors path="stationInfo" /></td>
                </tr>
                <tr>
                    <td colspan="3"><input type="submit" value="Search" /></td>
                </tr>
            </table>
        </fieldset>
    </form:form>

    <c:if test="${not empty sheduleItemsList}">
        <form id="buyForm" method="GET" action="/ticket/buy/{id}">
            <input type="button" id="buyButton" value="Buy">
        </form>
        <railwaysystem:shedule-table sheduleItemsList="${sheduleItemsList}" radioClass="sheduleItemRadio" />
    </c:if>

  </body>
</html>