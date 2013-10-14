<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Station info add</title>
  </head>
  <body>
    <c:out value="${resultMessage}" default="" />
    <form:form modelAttribute="stationInfoEntity" method="POST" action="/station-info/add">
        <table>
            <tr>
                <td><form:label path="title">Title: </form:label></td>
                <td><form:input path="title" /></td>
                <td><form:errors path="title" /></td>
            </tr>
            <tr>
                <td colspan="3"><input type="submit" value="Submit" /></td>
            </tr>
        </table>
    </form:form>
  </body>
</html>