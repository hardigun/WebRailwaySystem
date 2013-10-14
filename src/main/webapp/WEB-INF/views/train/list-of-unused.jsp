<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Unused trains list</title>
  </head>
  <body>
      <ul>
      <c:forEach items="${unusedTrainsList}" var="unusedTrain">
          <li><c:out value="${unusedTrain}"/></li>
      </c:forEach>
      </ul>
  </body>
</html>