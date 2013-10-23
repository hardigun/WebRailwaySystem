<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="railwaysystem" uri="http://tsystem.com/tags/railwaysystem"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<railwaysystem:message-handler message="${message}" />

<c:if test="${pageContext.request.isUserInRole('ROLE_CLIENT') || pageContext.request.isUserInRole('ROLE_MANAGER')}">
    <a href="/user/edit/${userEntity.id}" class="button">Edit info</a>
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
        <td><fmt:formatDate value='${userEntity.birthday}' pattern='dd.MM.yyyy' /></td>
    </tr>
    <tr>
        <td>Registration date: </td>
        <td><fmt:formatDate value='${userEntity.regDate}' pattern='dd.MM.yyyy' /></td>
    </tr>
</table>