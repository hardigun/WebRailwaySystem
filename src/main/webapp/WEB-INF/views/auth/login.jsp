<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>Login</head>
    <body>
        <div id="error">${error}</div>
        <form action="/j_spring_security_check" method="POST">
            <table>
                <tr>
                    <td>Login: </td>
                    <td><input type="text" name="j_username"></td>
                </tr>
                <tr>
                    <td>Password: </td>
                    <td><input type="password" name="j_password"></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Login" /></td>
                </tr>
            </table>
        </form>
    </body>
</html>