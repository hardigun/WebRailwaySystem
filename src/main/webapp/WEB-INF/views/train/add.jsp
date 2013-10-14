<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Train add</title>
  </head>
  <body>
    <c:out value="${resultMessage}" default="" />
    <form:form modelAttribute="trainEntity" method="POST" action="/train/add">
        <form:errors path="*" />
        <table>
            <tr>
                <td><form:label path="trainNumber">Train number: </form:label></td>
                <td><form:input path="trainNumber"/></td>
                <td><form:errors path="trainNumber"/></td>
            </tr>

            <tr>
                <td><form:label path="capacity">Train capacity: </form:label></td>
                <td><form:input path="capacity"/></td>
                <td><form:errors path="capacity"/></td>
            </tr>

            <tr>
                <td colspan="3"><input type="submit" value="Submit" /></td>
            </tr>
        </table>
    </form:form>
  </body>
</html>