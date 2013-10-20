<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div id="menu">
    <div class="menu-item">
        <span class="title">Buy ticket</span>
        <a href='<c:url value="/shedule/search/by-station" />'>Shedule by station</a>
        <a href='<c:url value="/shedule/search/between-stations" />'>Search trains between stations</a>
    </div>

    <security:authorize access="hasRole('ROLE_MANAGER')">
        <div class="menu-item">
            <span class="title">Manager controls</span>
            <a href='<c:url value="/station-info/add" />'>New station info</a>
            <a href='<c:url value="/train/add" />'>New train</a>
            <a href='<c:url value="/route/add" />'>New route</a>
            <a href='<c:url value="/shedule/add" />'>New shedule item</a>
            <a href='<c:url value="/station-info/list" />'>List of station info</a>
            <a href='<c:url value="/train/list-of-unused" />'>List of unused trains</a>
            <a href='<c:url value="/route/list-of-unused" />'>List of unused routes</a>
            <a href='<c:url value="/shedule/show/trains-by-routes" />'>Show trains</a>
            <a href='<c:url value="/ticket/show" />'>Show passengers with tickets</a>
        </div>
    </security:authorize>

    <security:authorize access="hasRole('ROLE_ADMIN')">
        <div class="menu-item">
            <span class="title">Admin controls</span>
            <a href='<c:url value="/user/list" />'>Manage users</a>
        </div>
    </security:authorize>
</div>
