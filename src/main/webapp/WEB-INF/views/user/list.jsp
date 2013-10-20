<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="railwaysystem" uri="http://tsystem.com/tags/railwaysystem"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<spring:url value="/resources/user-list.js" var="user_list_scripts_url" />
<spring:url value="/resources/edit.png" var="edit_img_url" />
<spring:url value="/resources/remove.png" var="remove_img_url" />

<script src="${user_list_scripts_url}"></script>

<railwaysystem:message-handler message="${message}" />

<div id="remove-loading" class="loading" style="display: none;"></div>

<a href="/user/add" target="_blank" class="button">Add user</a>

<table id="usersList" class="CSSTableGenerator">
    <tr>
        <td></td>
        <td>Surname</td>
        <td>Name</td>
        <td>Login</td>
        <td>User role</td>
        <td>Registration date and time</td>
    </tr>

    <c:forEach items="${usersList}" var="userFromList">
        <tr id="user_${userFromList.id}">
            <td>

                <a href="/user/edit/${userFromList.id}"><img src="${edit_img_url}" title="Edit" /></a>&nbsp;
                <a id="removeUser" href="/rest/user/remove/${userFromList.id}" user_id="${userFromList.id}">
                    <img src="${remove_img_url}" title="Remove" />
                </a>
            </td>
            <td>${userFromList.surname}</td>
            <td>${userFromList.name}</td>
            <td>${userFromList.login}</td>
            <td>${userFromList.userRole}</td>
            <td><fmt:formatDate value="${userFromList.regDate}" pattern="dd.MM.yyyy HH:mm" /></td>
        </tr>
    </c:forEach>
</table>