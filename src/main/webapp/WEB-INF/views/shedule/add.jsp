<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Shedule item add</title>
    </head>
    <body>
        <c:out value="${resultMessage}" default="" />
        <form:form modelAttribute="sheduleItemEntity" method="POST" action="/shedule/add">
            <form:errors path="*"/>

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
                    <td><form:errors path="route"/></td>
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
                    <td><form:errors path="train" /></td>
                </tr>

                <tr>
                    <td><form:label path="departureDate">Enter departure date(ex. 25.10.2013 17:00): </form:label></td>
                    <td><form:input path="departureDate" /></td>
                    <td><form:errors path="departureDate" /></td>
                </tr>

                <tr>
                    <td><input type="submit" value="Submit" /></td>
                </tr>
            </table>
        </form:form>
  </body>
</html>