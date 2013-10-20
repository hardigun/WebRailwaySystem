<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<spring:url value="/resources/logo.jpg" var="logo_img_url" />

<div id="header">
    <div id="logo"><a href="/"><img src="${logo_img_url}" title="Go to main page"></a></div>
    <div id="application-info">
        <div id="application-title">WebRailwaySystem</div>
        <div id="application-desc"> - system for buy ticket quick and easy</div>
    </div>
    <div id="login-form">
        <c:choose>
            <c:when test="${pageContext.request.userPrincipal.authenticated}">
                Hello, ${pageContext.request.userPrincipal.name}&nbsp;
                <a href="/user/show/${pageContext.request.userPrincipal.name}" class="button">Show profile</a>&nbsp;
                <a href="/auth/logout" class="button">Logout</a>
            </c:when>
            <c:otherwise>
                <a href="/auth/login" class="button">Login</a>
                <a href="/user/add" class="button">Registration</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>