<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Unused routes list</title>
  </head>
  <body>
      <ul>
          <c:forEach items="${unusedRoutesList}" var="unusedRoute">
              <li><c:out value="${unusedRoute}"/></li>
          </c:forEach>
      </ul>
  </body>
</html>