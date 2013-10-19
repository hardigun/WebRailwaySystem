<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Users list</title>

    <spring:url value="/resources/jquery-1.10.2.min.js" var="jquery_url" />
    <spring:url value="/resources/user-list.js" var="user_list_scripts_url" />

    <spring:url value="/resources/style.css" var="my_styles_url" />

    <script src="${jquery_url}"></script>
    <script src="${user_list_scripts_url}"></script>

    <link rel="stylesheet" type="text/css" href="${my_styles_url}" />
  </head>
  <body>
    <div id="resultMessage"><c:out value="${resultMessage}" default="" /></div>
    <div id="remove-loading" class="loading" style="display: none;"></div>
    <a href="/user/add" target="_blank">Add user</a>
    <table id="usersTable">
        <tr>
            <th></th>
            <th>Surname</th>
            <th>Name</th>
            <th>Login</th>
            <th>User role</th>
            <th>Registration date and time</th>
        </tr>
    
        <c:forEach items="${usersList}" var="userFromList">
            <tr id="user_${userFromList.id}">
                <td>

                    <a href="/user/edit/${userFromList.id}">Edit</a>&nbsp;
                    <a id="removeUser" href="/rest/user/remove/${userFromList.id}" user_id="${userFromList.id}">Remove</a>
                </td>
                <td>${userFromList.surname}</td>
                <td>${userFromList.name}</td>
                <td>${userFromList.login}</td>
                <td>${userFromList.userRole}</td>
                <td><fmt:formatDate value="${userFromList.regDate}" pattern="dd.MM.yyyy HH:mm" /></td>
            </tr>
        </c:forEach>
    </table>
  </body>
</html>