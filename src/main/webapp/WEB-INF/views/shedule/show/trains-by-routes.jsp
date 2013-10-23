<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="railwaysystem" uri="http://tsystem.com/tags/railwaysystem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="loading-routes" class="loading" style="visibility: hidden;"></div>

<div style="height: 250px;">
    <railwaysystem:routes-trains-tree sheduleItemsByRoutesMap="${sheduleItemsByRoutesMap}" />
</div>

<div style="margin-top: 15px;">
    <form id="showTicketsRedirectForm" method="GET" action="/ticket/show/{id}">
        <input type="button" id="showPassengersButton" value="Show passengers" />
        <input type="button" id="showRouteStationsButton" value="Show stations" />
    </form>

    <table id="sheduleItemsTable" class="CSSTableGenerator" style="display: none;">
        <tr>
            <td></td>
            <td>Train number</td>
            <td>Departure date</td>
            <td>Sale tickets count</td>
            <td>Confirmed tickets count</td>
        </tr>
    </table>

    <br/>

    <table id="routeStationsTable" class="CSSTableGenerator" style="display: none;"></table>
</div>

<spring:url value="/resources/jquery.dynatree.js" var="dynatree_scripts_url" />
<spring:url value="/resources/shedule-show-trains-by-routes.js" var="trains_by_routes_scripts_url" />

<script src="${dynatree_scripts_url}"></script>
<script src="${trains_by_routes_scripts_url}"></script>

