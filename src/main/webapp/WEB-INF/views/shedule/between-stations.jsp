<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="railwaysystem" uri="http://tsystem.com/tags/railwaysystem"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
        <title>Search trains</title>
        <spring:url value="/resources/jquery-1.10.2.min.js" var="jquery_url" />
        <spring:url value="/resources/shedule-by-station.js" var="shedule_scripts_url" />
        <script src="${jquery_url}"></script>
        <script src="${shedule_scripts_url}"></script>
  </head>
  <body>

        <form:form modelAttribute="stationsFilter" method="POST" action="/shedule/between-stations">
          <fieldset>
              <legend>Search trains params</legend>
              <table>
                  <tr>
                      <td><form:label path="stationInfoFrom">Select departure station: </form:label></td>
                      <td>
                          <form:select path="stationInfoFrom">
                              <c:forEach items="${stationInfoList}" var="stationInfoFromList">
                                  <option value="${stationInfoFromList.id}"
                                          <c:if test="${stationsFilter.stationInfoFrom == stationInfoFromList}">
                                              selected="selected"
                                          </c:if>
                                          >${stationInfoFromList.title}
                                  </option>
                              </c:forEach>
                          </form:select>
                      </td>
                      <td><form:errors path="stationInfoFrom" /></td>
                  </tr>

                  <tr>
                      <td><form:label path="stationInfoTo">Select departure station: </form:label></td>
                      <td>
                          <form:select path="stationInfoTo">
                              <c:forEach items="${stationInfoList}" var="stationInfoFromList">
                                  <option value="${stationInfoFromList.id}"
                                          <c:if test="${stationsFilter.stationInfoTo == stationInfoFromList}">
                                              selected="selected"
                                          </c:if>
                                          >${stationInfoFromList.title}
                                  </option>
                              </c:forEach>
                          </form:select>
                      </td>
                      <td><form:errors path="stationInfoTo" /></td>
                  </tr>

                  <tr>
                      <td><form:label path="startDate">Enter date(ex. 25.10.2013 17:00): </form:label></td>
                      <td><form:input path="startDate" /></td>
                      <td><form:errors path="startDate" /></td>
                  </tr>

                  <tr>
                      <td><form:label path="endDate">Enter date(ex. 26.10.2013 18:00): </form:label></td>
                      <td><form:input path="endDate" /></td>
                      <td><form:errors path="endDate" /></td>
                  </tr>

                  <tr>
                      <td colspan="3"><input type="submit" value="Submit" /></td>
                  </tr>
              </table>
          </fieldset>
        </form:form>

        <c:if test="${not empty sheduleItemsList}">
          <form id="buyForm" method="GET" action="/ticket/buy/{id}">
              <input type="button" id="buyButton" value="Buy">
          </form>
          <railwaysystem:stations-table sheduleItemsList="${sheduleItemsList}" radioClass="sheduleItemsClass" />
        </c:if>

  </body>
</html>