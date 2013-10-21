<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="railwaysystem" uri="http://tsystem.com/tags/railwaysystem"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
            </tr>
            <tr>
                <td><form:label path="date">Enter date:</form:label></td>

                <c:if test="${not empty sheduleFilter}">
                    <fmt:formatDate value='${sheduleFilter.date}' pattern='dd.MM.yyyy' var="date"/>
                </c:if>
                <td><input name="date" class="date-input" value="<c:out value='${date}' default='' />"/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Search" /></td>
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

<spring:url value="/resources/shedule-search.js" var="shedule_scripts_url" />
<script src="${shedule_scripts_url}"></script>
