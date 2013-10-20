<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table class="CSSTableGenerator">
    <tr>
        <td>Train number</td>
        <td>Capacity(passengers)</td>
    </tr>
    <c:forEach items="${unusedTrainsList}" var="unusedTrain">
        <tr>
            <td><c:out value="${unusedTrain.trainNumber}"/></td>
            <td><c:out value="${unusedTrain.capacity}"/></td>
        </tr>
    </c:forEach>
</table>