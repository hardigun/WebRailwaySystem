<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="railwaysystem" uri="http://tsystem.com/tags/railwaysystem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
        <title>Show trains by routes</title>
        <spring:url value="/resources/jquery-1.10.2.min.js" var="jquery_url" />
        <spring:url value="/resources/jquery-ui-1.10.3.custom.min.js" var="jquery_ui_url" />
        <spring:url value="/resources/jquery.dynatree.js" var="dynatree_url" />
        <spring:url value="/resources/common.js" var="common_scripts_url" />
        <spring:url value="/resources/shedule-show-trains-by-routes.js" var="trains_by_routes_scripts_url" />

        <spring:url value="/resources/skin/ui.dynatree.css" var="dynatree_styles_url" />
        <spring:url value="/resources/style.css" var="my_styles_url" />

        <script src="${jquery_url}"></script>
        <script src="${jquery_ui_url}"></script>
        <script src="${dynatree_url}"></script>
        <script src="${common_scripts_url}"></script>
        <script src="${trains_by_routes_scripts_url}"></script>

        <link rel="stylesheet" type="text/css" href="${dynatree_styles_url}" />
        <link rel="stylesheet" type="text/css" href="${my_styles_url}" />
  </head>
  <body>

    <div>
        <div style="float: left; width: 500px;">
            <div>
                <railwaysystem:routes-trains-tree sheduleItemsByRoutesMap="${sheduleItemsByRoutesMap}" />
            </div>
        </div>
        <div style="margin-left: 505px;">
            <div id="loading-routes" class="loading" style="display: none;"></div>

            <form id="showTicketsRedirectForm" method="GET" action="/ticket/show/{id}">
                <input type="button" id="showPassengersButton" value="Show passengers" />
                <input type="button" id="showRouteStationsButton" value="Show stations" />
            </form>

            <table id="sheduleItemsTable" style="display: none;">
                <tr>
                    <th></th>
                    <th>Train number</th>
                    <th>Departure date</th>
                    <th>Sale tickets count</th>
                    <th>Confirmed tickets count</th>
                </tr>
            </table>

            <table id="routeStationsTable" style="display: none;"></table>
        </div>
    </div>

  </body>
</html>