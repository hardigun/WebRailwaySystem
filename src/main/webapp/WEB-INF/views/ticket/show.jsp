<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="railwaysystem" uri="http://tsystem.com/tags/railwaysystem"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
      <title>Show passengers</title>

      <spring:url value="/resources/jquery-1.10.2.min.js" var="jquery_url" />
      <spring:url value="/resources/ticket-show.js" var="tickets_scripts_url" />
      <spring:url value="/resources/style.css" var="my_styles_url" />

      <script src="${jquery_url}"></script>
      <script src="${tickets_scripts_url}"></script>
      <link rel="stylesheet" type="text/css" href="${my_styles_url}" />
  </head>
  <body>
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
                    <td><form:label path="passenger.birthday" >Passenger birthday(ex. 08.06.1955): </form:label></td>
                    <td><form:input path="passenger.birthday" /></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Search" /> </td>
                </tr>
            </table>
        </fieldset>
    </form:form>

    <div id="loading-confirm" class="loading" style="display: none;"></div>

    <div id="last-operation-result"></div>

    <c:if test="${(not empty ticketsList) && (fn:length(ticketsList) > 0)}">
        <input type="button" id="confirmSaleButton" value="Confirm">
        <railwaysystem:tickets-table ticketsList="${ticketsList}" checkboxClass="ticketConfirmCheckbox"/>
    </c:if>
  </body>
</html>