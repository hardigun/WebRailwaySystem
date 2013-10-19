<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>User info</title>
  </head>
  <body>
    <c:out value="${resultMessage}" default="" />

    <c:if test="${pageContext.request.isUserInRole('ROLE_CLIENT') || pageContext.request.isUserInRole('ROLE_MANAGER')}">
        <a href="/user/edit/${userEntity.id}">Edit info</a>
    </c:if>

    <table>
        <tr>
            <td>Login: </td>
            <td>${userEntity.login}</td>
        </tr>
        <tr>
            <td>Name: </td>
            <td>${userEntity.name}</td>
        </tr>
        <tr>
            <td>Surname: </td>
            <td>${userEntity.surname}</td>
        </tr>
        <tr>
            <td>Role: </td>
            <td>${userEntity.userRole}</td>
        </tr>
        <tr>
            <td>Birthday: </td>
            <td>${userEntity.birthday}</td>
        </tr>
        <tr>
            <td>Registration date: </td>
            <td>${userEntity.regDate}</td>
        </tr>
    </table>
  </body>
</html>