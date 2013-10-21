<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="railwaysystem" uri="http://tsystem.com/tags/railwaysystem"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form:form modelAttribute="stationsFilter" method="POST" action="/shedule/search/between-stations">
  <fieldset>
      <legend>Search trains params</legend>
      <table>
          <tr>
              <td><form:label path="stationInfoFrom">Select arrival station: </form:label></td>
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
          </tr>

          <tr>
              <td><form:label path="startDate">Enter start of dates range: </form:label></td>

              <c:if test="${not empty stationsFilter}">
                  <fmt:formatDate value='${stationsFilter.startDate}' pattern='dd.MM.yyyy HH:mm' var="date"/>
              </c:if>
              <td>
                  <input name="startDate" class="date-time-input" value="<c:out value='${date}' default='' />"/></td>
              </td>
          </tr>

          <tr>
              <td><form:label path="endDate">Enter end of dates range: </form:label></td>

              <c:if test="${not empty stationsFilter}">
                  <fmt:formatDate value='${stationsFilter.endDate}' pattern='dd.MM.yyyy HH:mm' var="date"/>
              </c:if>
              <td>
                  <input name="endDate" class="date-time-input" value="<c:out value='${date}' default='' />"/></td>
              </td>
          </tr>

          <tr>
              <td colspan="2"><input type="submit" value="Search" /></td>
          </tr>
      </table>
  </fieldset>
</form:form>

<c:if test="${not empty sheduleItemsList}">
  <form id="buyForm" method="GET" action="/ticket/buy/{id}">
      <input type="button" id="buyButton" value="Buy">
  </form>
  <railwaysystem:stations-table sheduleItemsList="${sheduleItemsList}" radioClass="sheduleItemRadio" />
</c:if>

<spring:url value="/resources/shedule-search.js" var="shedule_scripts_url" />
<script src="${shedule_scripts_url}"></script>