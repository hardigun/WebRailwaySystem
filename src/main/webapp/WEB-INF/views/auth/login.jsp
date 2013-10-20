<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty param.error}">
    <div class="message-error">Failure, check your login and password!</div>
</c:if>
<form action="/j_spring_security_check" method="POST">
    <fieldset>
        <legend>Enter your login and password</legend>
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
    </fieldset>
</form>