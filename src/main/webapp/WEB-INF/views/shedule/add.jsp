<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="railwaysystem" uri="http://tsystem.com/tags/railwaysystem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<railwaysystem:message-handler message="${message}" />
<form:form modelAttribute="sheduleItemEntity" method="POST" action="/shedule/add">
    <fieldset>
        <legend>Shedule item data</legend>
        <table>
            <tr>
                <td><form:label path="route">Select route: </form:label></td>
                <td>
                    <form:select path="route">
                        <c:forEach items="${routeList}" var="routeFromList">
                            <option value="${routeFromList.id}"
                                    <c:if test="${sheduleItemEntity.route.id == routeFromList.id}">
                                        selected="selected"
                                    </c:if>
                                    >${routeFromList}</option>
                        </c:forEach>
                    </form:select>
                </td>
                <td><form:errors path="route" cssClass="message-error"/></td>
            </tr>

            <tr>
                <td><form:label path="train">Select train: </form:label></td>
                <td>
                    <form:select path="train">
                        <c:forEach items="${trainList}" var="trainFromList">
                            <option value="${trainFromList.id}"
                                    <c:if test="${sheduleItemEntity.train.id == trainFromList.id}">
                                        selected="selected"
                                    </c:if>
                                    >${trainFromList}</option>
                        </c:forEach>
                    </form:select>
                </td>
                <td><form:errors path="train" cssClass="message-error"/></td>
            </tr>

            <tr>
                <td><form:label path="departureDate">Enter departure date and time: </form:label></td>

                <c:if test="${not empty sheduleItemEntity.departureDate}">
                    <fmt:formatDate value='${sheduleItemEntity.departureDate}' pattern='dd.MM.yyyy HH:mm' var="depDate"/>
                </c:if>
                <td><input name="departureDate" class="date-time-input" value="<c:out value='${depDate}' default='' />"/></td>
                <td><form:errors path="departureDate" cssClass="message-error"/></td>
            </tr>

            <tr>
                <td><input type="submit" value="Ok" /></td>
            </tr>
        </table>
    </fieldset>
</form:form>