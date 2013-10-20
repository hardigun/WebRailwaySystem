<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
        <title>WebRailwaySystem</title>
        <spring:url value="/resources/jquery-1.10.2.min.js" var="jquery_url" />
        <spring:url value="/resources/jquery-ui-1.10.3.custom.min.js" var="jquery_ui_url" />
        <spring:url value="/resources/bootstrap.min.js" var="bootstrap_scripts_url" />
        <spring:url value="/resources/common.js" var="common_scripts_url" />

        <spring:url value="/resources/bootstrap-responsive.min.css" var="bootstrap_resp_styles_url" />
        <spring:url value="/resources/bootstrap.min.css" var="bootstrap_styles_url" />
        <spring:url value="/resources/skin/ui.dynatree.css" var="dynatree_styles_url" />
        <spring:url value="/resources/style.css" var="my_styles_url" />

        <script src="${jquery_url}"></script>
        <script src="${jquery_ui_url}"></script>
        <!-- <script src="${bootstrap_scripts_url}"></script> -->
        <script src="${common_scripts_url}"></script>

        <!-- <link rel="stylesheet" type="text/css" href="${bootstrap_resp_styles_url}" />
        <link rel="stylesheet" type="text/css" href="${bootstrap_styles_url}" /> -->
        <link rel="stylesheet" type="text/css" href="${dynatree_styles_url}" />
        <link rel="stylesheet" type="text/css" href="${my_styles_url}" />
  </head>
  <body>
    <div id="container">
        <tiles:insertAttribute name="header" ignore="true" />
        <div id="wrapper">
            <tiles:insertAttribute name="menu" ignore="true" />
            <div id="content">
                <tiles:insertAttribute name="body" />
            </div>
        </div>
        <tiles:insertAttribute name="footer" ignore="true" />
    </div>
  </body>
</html>