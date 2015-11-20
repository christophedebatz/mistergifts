<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="header.jsp"></jsp:include>

<div class="container">
  <div class="jumbotron text-center">
    <h1>Mister Gifts</h1>
    <p class="lead" style="margin-top: 25px;"><spring:message code="site.page.home.description"/></p>
    
    <p style="text-align: center;">
    	<img src="<c:url value="/resources/pictures/biggift.png" />" />
    </p>
    
    <p style="margin-top: 50px; text-align: center;">
    	<a class="btn btn-lg btn-primary" href="<c:url value="/mylist" />"><spring:message code="site.page.home.begin"/></a>
    </p>
    
  </div>
</div>

<jsp:include page="footer.jsp"></jsp:include> 