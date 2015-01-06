<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="header.jsp"></jsp:include>

<div class="container">
  <div class="jumbotron">
  
    <h2>Book a gift</h2>
    
    <c:choose>
	    	<c:when test="${empty gift}">
	    	
	    	<div class="alert alert-warning" role="alert">
				<p>This gift does not exist at the moment...</p>
			</div>
			
	    </c:when>
	    <c:otherwise>
	    
			<fieldset>
			
				You tried to book ${gift.name}.
					
			</fieldset>
			
			<p style="text-align: right;">
				<button onclick="window.location='<c:url value="/family" />';" class="btn btn-default">Back to gift list</button>
			</p>
	
		</c:otherwise>
    </c:choose>
		
  </div>
</div>

<jsp:include page="footer.jsp"></jsp:include> 