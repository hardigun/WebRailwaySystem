<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <td><form:label path="birthday">Enter birthday(ex. 08.06.1955): </form:label></td>
                <td><form:input path="birthday" /></td>
                <td><form:errors path="birthday" cssClass="message-error"/></td>
            </tr>
            <tr>
                <td colspan="3"><input type="submit" value="Submit" /></td>
            </tr>
        </table>
    </fieldset>
</form:form>
