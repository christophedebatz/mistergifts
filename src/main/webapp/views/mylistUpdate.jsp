<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="header.jsp"></jsp:include>

<div class="container">
  <div class="jumbotron">
    <h2>My gifts list</h2>


	<c:choose>
		<c:when test="${ empty user.ownedGifts}">
		
			<div class="alert alert-info" role="alert" style="padding-bottom: 30px;">
				<div style="float: right;">
					<a class="btn btn-large btn-primary" href="<c:url value="/mylist/update" />">Create one now</a>
				</div>
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

	


	
		
	
	
    
  </div>
</div>

<jsp:include page="footer.jsp"></jsp:include> 