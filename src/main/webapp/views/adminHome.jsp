<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="header.jsp"></jsp:include>

<div class="container">
  <div class="jumbotron">
  
    <h2>Admin</h2>
    
		<form action="<c:url value="/admin" />" method="post" style="width: 85%;">
		
		<c:choose>
			<c:when test="${not empty error and error eq 'none' }">
				<p class="alert alert-info" role="alert">User has been added successfully.</p>
			</c:when>
			<c:when test="${not empty error}">
				<p class="alert alert-danger" role="alert">${error}</p>
			</c:when>
		</c:choose>
		
		<fieldset>
			<legend>Create user</legend>

			<div class="form-group">
				<input type="text" name="username" class="form-control"
					placeholder="Username..." />
			</div>

			<div class="form-group">
				<input type="password" name="password" class="form-control"
					placeholder="Password..." />
			</div>
			
			<select name="user_role" class="form-control">
			  <option value="ROLE_ADMIN">Administrateur</option>
			  <option value="ROLE_USER">Utilisateur</option>
			</select>
		
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</fieldset>

		<button class="btn btn-large btn-primary pull-right">Add user</button>
	</form>
  </div>
</div>

<jsp:include page="footer.jsp"></jsp:include> 