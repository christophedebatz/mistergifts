<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<jsp:include page="header.jsp"></jsp:include>
 
<div class="container">

	<img src="<c:url value="/resources/pictures/connection.png" />" class="img-rounded" style="padding: 50px; float: right;" />
	
	<div class="jumbotron">
	
		<fieldset style="margin-top: 30px;">
			<legend>Login</legend>
					
			<c:choose>
				<c:when test="${pageContext.request.userPrincipal.name == null}">
		
					<c:if test="${not empty error}">
						<div class="alert alert-danger" role="alert">${error}</div>
					</c:if>
					
					<c:if test="${not empty msg}">
						<div class="alert alert-info" role="alert">${msg}</div>
					</c:if>
					
					<form name='loginForm' action="<c:url value='/j_spring_security_check' />" method='POST'>
					
					  	<div class="form-group">
					    	<input type="username" name="username" class="form-control" placeholder="Username...">
					  	</div>
					  	
					  	<div class="form-group">
					    	<input type="password" name="password" class="form-control" placeholder="Password...">
					  	</div>
					  
					  	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					  	<button type="submit" class="btn btn-large btn-primary">Sign in</button>
						
					</form>
					
				</c:when>
				<c:otherwise>
				
					<div class="alert alert-info" role="alert">You are already connected as <b>${pageContext.request.userPrincipal.name}</b>.</div>
					
					<c:if test="${not empty error}">
						<div class="alert alert-danger" role="alert">${error}</div>
					</c:if>
					
				</c:otherwise>
			</c:choose>
			
		</fieldset>
			
		
			
	</div>
</div>
	
<jsp:include page="footer.jsp"></jsp:include>