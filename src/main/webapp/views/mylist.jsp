<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="header.jsp"></jsp:include>

<div class="container">
  <div class="jumbotron">
    <h2>My gift list</h2>

	<p style="margin-top: 40px;">

		<fieldset>
			<legend>My list</legend>

			<c:choose>
				<c:when test="${ empty user.ownedGifts}">
				
					<div class="alert alert-info" role="alert">
						<p>Your current gift list is still empty yet...</p>
					</div>
					
				</c:when>
			
				<c:otherwise>
				
					<div class="table-responsive">
					  <table class="table table-hover">
			  			<thead>#</thead>
					  	<thead>Name</thead>
					  	<thead>Details</thead>
					  	
					  	<c:forEach items="${user.ownedGifts}" var="gift">
					  	
					  		<td>${gift.id}</td>
					  		<td>${gift.name}</td>
					  		<td>${gift.details}</td>
					  	
				  		</c:forEach>
				  		
		  			  </table>
				   </div>
				
				</c:otherwise>
		
			</c:choose>
		</fieldset>

		<img src="<c:url value="/resources/pictures/gifts.png" />" style="float: right;" />
		<fieldset>
			<legend>New gift</legend>
			<form action="<c:url value="/mylist" />" method="post">
			
				<div class="form-group">
				    <input type="text" name="name" class="form-control" placeholder="Name..." />
			  	</div>
			  	<div class="form-group">
				    <input type="text" name="details" class="form-control" placeholder="Details..." />
			  	</div>
				<div class="form-group">
				    <input type="text" name="shoplink" class="form-control" placeholder="Shop link..." />
			  	</div>
			  	
			  	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			  	
			  	<button class="btn btn-large btn-default">Add this!</button>
			</form>
		</fieldset>
	</p>
	
  </div>
</div>

<jsp:include page="footer.jsp"></jsp:include> 