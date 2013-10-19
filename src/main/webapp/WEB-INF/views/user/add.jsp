<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="for" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>User info</title>


  </head>
  <body>
    <c:out value="${resultMessage}" default="" />
    <h3>Please, fill the next fields: </h3>

    <c:choose>
        <c:when test="${not empty isupdate}">
            <c:set value="/user/add?isupdate=true" var="actionLink"/>
        </c:when>
        <c:otherwise>
            <c:set value="/user/add" var="actionLink" />
        </c:otherwise>
    </c:choose>

    <form:form modelAttribute="userEntity" method="POST" action="${actionLink}">
        <form:hidden path="id" />

        <table>
            <tr>
                <td><form:label path="login">Login: </form:label></td>
                <td><form:input path="login" /></td>
                <td><form:errors path="login" /></td>
            </tr>

            <tr>
                <td><form:label path="name">Name: </form:label></td>
                <td><form:input path="name" /></td>
                <td><form:errors path="name" /></td>
            </tr>

            <tr>
                <td><form:label path="surname">Surname: </form:label></td>
                <td><form:input path="surname" /></td>
                <td><form:errors path="surname" /></td>
            </tr>

            <tr>
                <td><form:label path="userPass">Password: </form:label></td>
                <td><form:password path="userPass" /></td>
                <td><form:errors path="userPass" /></td>
            </tr>

            <tr>
                <td><form:label path="birthday">Birthday: </form:label></td>
                <td><form:input path="birthday" /></td>
                <td><form:errors path="birthday" /></td>
            </tr>

            <c:choose>
                <c:when test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
                    <tr>
                        <td><form:label path="userRole">User role: </form:label></td>
                        <td>
                            <form:select path="userRole">
                                <c:forEach items="${userRoles}" var="userRoleFromList">
                                    <form:option  value="${userRoleFromList}">${userRoleFromList}</form:option>
                                </c:forEach>
                            </form:select>
                        </td>
                        <td><form:errors path="userRole" /></td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="3">
                            <for:hidden path="userRole" />
                        </td>
                    </tr>
                </c:otherwise>
            </c:choose>

            <tr>
                <td colspan="3"><input type="submit" value="Ok" /></td>
            </tr>
        </table>
    </form:form>
  </body>
</html>