<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page session="true"%>

<c:set var="baseURL" value="${requestScope['javax.servlet.forward.request_uri']}" />

<form action="<c:url value="/logout" />" method="post" id="logoutForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>

<div class="navbar navbar-inverse">
  <div class="container">
  
    <a class="navbar-brand" href="<c:url value="/" />" data-toggle="tooltip" data-placement="bottom" title="<spring:message code="site.menu.home"/>">
    	<img src="<c:url value="/resources/pictures/rsz_gift.png" />" />&nbsp; Mister Gifts
    </a>
    
    <ul class="nav navbar-nav">
        <c:if test="${not empty pageContext.request.userPrincipal.name}">
            <li<c:if test="${param.currentTab eq 'group'}"> class="active"</c:if>><a href="<c:url value="/grouplist" />"<c:if test="${param.currentTab ne 'family'}"> data-toggle="tooltip" data-placement="bottom" title="<spring:message code="site.menu.grouplist.tooltip"/>"</c:if>><spring:message code="site.menu.grouplist"/></a></li>
            <li<c:if test="${param.currentTab eq 'myList'}"> class="active"</c:if>><a href="<c:url value="/mylist" />"<c:if test="${param.currentTab ne 'myList'}"> data-toggle="tooltip" data-placement="bottom" title="<spring:message code="site.menu.mylist.tooltip"/>"</c:if>><spring:message code="site.menu.mylist"/></a></li>
         </c:if>
        <li<c:if test="${param.currentTab eq 'about'}"> class="active"</c:if>><a href="<c:url value="/about" />"<c:if test="${param.currentTab ne 'about'}"> data-toggle="tooltip" data-placement="bottom" title="<spring:message code="site.menu.about.tooltip"/>"</c:if>><spring:message code="site.menu.about"/></a></li>
    </ul>
    
    <ul class="nav navbar-nav pull-right">

        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="site.menu.language"/> <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="<c:url value="${baseURL}"><c:param name="lang" value="fr"/></c:url>"><img src="<c:url value="/resources/pictures/fr.png" />" width="16" height="16"> Français</a></li>
                <li><a href="<c:url value="${baseURL}"><c:param name="lang" value="en"/></c:url>"><img src="<c:url value="/resources/pictures/en.png" />" width="16" height="16"> English</a></li>
            </ul>
        </li>
    
        <c:choose>
            <c:when test="${pageContext.request.userPrincipal.name == null}">
                <li<c:if test="${param.currentTab eq 'login'}"> class="active"</c:if>>
                    <a href="<c:url value="/login" />" data-toggle="tooltip" data-placement="bottom" title="<spring:message code="site.menu.login.tooltip"/>">
                        <spring:message code="site.menu.login"/>
                    </a>
                </li>
            </c:when>

            <c:otherwise>
                <li>
                    <a href="javascript: $('#logoutForm').submit();" data-toggle="tooltip" data-placement="bottom" title="<spring:message code="site.menu.logout.tooltip"/>">
                        <spring:message code="site.menu.logout"/> (${pageContext.request.userPrincipal.name})
                    </a>
                </li>
            </c:otherwise>
        </c:choose>
    
   	</ul>
  </div>
</div>
