<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="header.jsp">
	<jsp:param name="currentTab" value="group" />
</jsp:include>

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
	    
	    	<c:choose>
    			<c:when test="${empty response}">
	    
					<fieldset>
					
						<p class="alert alert-warning font-size: 14px;">
							Attention, En réservant un cadeau pour un utilisateur, vous vous engagez à le lui offrir. En effet, dès lors que le cadeau à offrir vous est réservé, aucun autre utilisateur ne peut le réserver à son tour. Vous pouvez toujours dé-réserver un cadeau par la suite mais ce n'est pas conseillé.
						</p>
						
						<p>Are you sure that you want to book the gift "<a href="<c:url value="/gift/${gift.slug}" />" title="See details"><span style="font-weight: bold;">${gift.name}</span></a>" for <span style="font-weight: bold;">${gift.owner.username}</span>?</p>
						
					</fieldset>
					
					<form action="<c:url value="/grouplist/booking" />" method="post">
						
						<input type="hidden" name="giftId" value="${gift.id}" />
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<input type="hidden" name="action" value="completeBooking" />
					
						<p style="text-align: right;">
							<button onclick="window.location='<c:url value="/grouplist" />'; return false;" class="btn btn-default">No</button>  
							<input type="submit" value="Yes" class="btn btn-primary" />
						</p>
					</form>
				</c:when>
				
				<c:otherwise>
				
					<div class="alert alert-success" role="alert">
						<p>Success! You'd just book <a href="<c:url value="/gift/${gift.slug}"/>">a gift</a> for ${gift.owner.username}. Thanks.</p>
					</div>
				
					<p style="text-align: right;">
						<button onclick="window.location='<c:url value="/grouplist" />';" class="btn btn-default">Back to list</button> 
					</p>
				</c:otherwise>
			</c:choose>
			
		</c:otherwise>
    </c:choose>
		
  </div>
</div>

<jsp:include page="footer.jsp"></jsp:include> 