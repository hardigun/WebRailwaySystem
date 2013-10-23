<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="railwaysystem" uri="http://tsystem.com/tags/railwaysystem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<railwaysystem:message-handler message="${message}" />
<form:form modelAttribute="passengerEntity" method="POST" action="/ticket/buy/${sheduleItem.id}">
    <fieldset>
        <legend>Passenger info</legend>
        <table>
            <tr>
                <td><form:label path="name">Enter name: </form:label></td>
                <td><form:input path="name" /></td>
                <td><form:errors path="name" cssClass="message-error"/></td>
            </tr>
            <tr>
                <td><form:label path="surname">Enter surname: </form:label></td>
                <td><form:input path="surname" /></td>
                <td><form:errors path="surname" cssClass="message-error"/></td>
            </tr>
            <tr>
                <td><form:label path="birthday">Enter birthday: </form:label></td>

                <c:if test="${not empty passengerEntity.birthday}">
                    <fmt:formatDate value='${passengerEntity.birthday}' pattern='dd.MM.yyyy' var="birthday"/>
                </c:if>
                <td><input name="birthday" class="date-input" value="<c:out value='${birthday}' default='' />"/></td>
                <td><form:errors path="birthday" cssClass="message-error"/></td>
            </tr>
            <tr>
                <td colspan="3"><input type="submit" value="Ok" /></td>
            </tr>
        </table>
    </fieldset>
</form:form>
