<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="railwaysystem" uri="http://tsystem.com/tags/railwaysystem"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form:form modelAttribute="ticketsFilter" method="POST" action="/ticket/show/">
    <fieldset>
        <legend>Search passengers params</legend>
        <table>
            <tr>
                <td><form:label path="ticket.id" >Ticket number: </form:label></td>
                <td><form:input path="ticket.id" /></td>
            </tr>
            <tr>
                <td><form:label path="passenger.name" >Passenger name: </form:label></td>
                <td><form:input path="passenger.name" /></td>
            </tr>
            <tr>
                <td><form:label path="passenger.surname" >Passenger surname: </form:label></td>
                <td><form:input path="passenger.surname" /></td>
            </tr>
            <tr>
                <td><form:label path="passenger.birthday" >Passenger birthday: </form:label></td>

                <c:if test="${not empty ticketsFilter.passenger.birthday}">
                    <fmt:formatDate value='${ticketsFilter.passenger.birthday}' pattern='dd.MM.yyyy' var="birthday"/>
                </c:if>
                <td><input name="passenger.birthday" class="date-input" value="<c:out value='${birthday}' default='' />"/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Search" /> </td>
            </tr>
        </table>
    </fieldset>
</form:form>

<div id="loading-confirm" class="loading" style="visibility: hidden;"></div>

<div id="last-operation-result"></div>

<c:if test="${(not empty ticketsList) && (fn:length(ticketsList) > 0)}">
    <input type="button" id="confirmSaleButton" value="Confirm">
    <railwaysystem:tickets-table ticketsList="${ticketsList}" checkboxClass="ticketConfirmCheckbox"/>
</c:if>

<spring:url value="/resources/ticket-show.js" var="tickets_scripts_url" />
<script src="${tickets_scripts_url}"></script>