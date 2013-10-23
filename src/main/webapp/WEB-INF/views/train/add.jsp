<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="railwaysystem" uri="http://tsystem.com/tags/railwaysystem"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<railwaysystem:message-handler message="${message}" />
<form:form modelAttribute="trainEntity" method="POST" action="/train/add">
    <fieldset>
        <legend>Train data</legend>
        <table>
            <tr>
                <td><form:label path="trainNumber">Train number: </form:label></td>
                <td><form:input path="trainNumber"/></td>
                <td><form:errors path="trainNumber" cssClass="message-error"/></td>
            </tr>

            <tr>
                <td><form:label path="capacity">Train capacity: </form:label></td>
                <td><form:input path="capacity" id="passengers-spinner-input"/></td>
                <td><form:errors path="capacity" cssClass="message-error" /></td>
            </tr>

            <tr>
                <td colspan="3"><input type="submit" value="Ok" /></td>
            </tr>
        </table>
    </fieldset>
</form:form>