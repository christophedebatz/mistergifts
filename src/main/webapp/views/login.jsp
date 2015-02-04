<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<jsp:include page="header.jsp">
	<jsp:param name="currentTab" value="login" />
</jsp:include>
 
<div class="container">

	<img src="<c:url value="/resources/pictures/connection.png" />" class="img-rounded" style="padding: 50px; float: right;" />
	
	<div class="jumbotron">
	
		<fieldset style="margin-top: 30px; max-width: 425px;">
			<legend>Login</legend>
					
			<c:choose>
				<c:when test="${empty pageContext.request.userPrincipal.name}">
		
					<c:if test="${not empty error}">
						<p class="alert alert-danger" role="alert">${error}</p>
					</c:if>
					
					<c:if test="${not empty msg}">
						<p class="alert alert-info" role="alert">${msg}</p>
					</c:if>
					
					<form name="loginForm" action="<c:url value="/j_spring_security_check" />" method="post">
					
					  	<div class="form-group">
					    	<input type="username" name="username" class="form-control" placeholder="Username...">
					  	</div>
					  	
					  	<div class="form-group">
					    	<input type="password" name="password" class="form-control" placeholder="Password...">
					  	</div>
					  
					  	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					  	<button type="submit" class="btn btn-large btn-primary">Log in</button>
						
					</form>
					
				</c:when>
				<c:otherwise>
				
					<p class="alert alert-info">
						You are already connected as <b>${pageContext.request.userPrincipal.name}</b>.
					</p>
					
					<c:if test="${not empty error}">
						<p class="alert alert-danger">${error}</p>
					</c:if>
					
				</c:otherwise>
			</c:choose>
			
		</fieldset>
			
		
			
	</div>
</div>
	
<jsp:include page="footer.jsp"></jsp:include>