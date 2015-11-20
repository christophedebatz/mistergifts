<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="header.jsp">
	<jsp:param name="currentTab" value="group" />
</jsp:include>

<div class="container">
  <div class="jumbotron">
  
    <h2><spring:message code="site.page.booking.title"/></h2>
    
    <c:choose>
    	<c:when test="${empty gift}">
	    	
	    	<div class="alert alert-warning" role="alert">
				<p><spring:message code="site.page.booking.error.notfound"/></p>
			</div>
			
	    </c:when>
	    <c:otherwise>

			<c:set var="giftUrl">
				<c:url value="/gift/${gift.slug}"/>
			</c:set>
	    
	    	<c:choose>
    			<c:when test="${empty response}">
	    
					<fieldset>
						<p class="alert alert-warning font-size: 14px;">
							<spring:message code="site.page.booking.description"/>
						</p>

						<p>
							<spring:message code="site.page.booking.confirmmsg" arguments="${giftUrl},${gift.name},${gift.owner.username}"/>
						</p>
					</fieldset>
					
					<form action="<c:url value="/grouplist/booking" />" method="post">
						
						<input type="hidden" name="giftId" value="${gift.id}" />
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<input type="hidden" name="action" value="completeBooking" />
					
						<p style="text-align: right;">
							<button onclick="window.location='<c:url value="/grouplist" />'; return false;" class="btn btn-default">
								<spring:message code="site.page.booking.form.no"/>
							</button>
							<input type="submit" value="<spring:message code="site.page.booking.form.yes"/>" class="btn btn-primary" />
						</p>
					</form>
				</c:when>
				
				<c:otherwise>
				
					<div class="alert alert-success" role="alert">
						<p>
							<spring:message code="site.page.booking.successmsg" arguments="${giftUrl},${gift.owner.username}"/>
						</p>
					</div>
				
					<p style="text-align: right;">
						<button onclick="window.location='<c:url value="/grouplist" />';" class="btn btn-default">
							<spring:message code="site.page.booking.back"/>
						</button>
					</p>
				</c:otherwise>
			</c:choose>
			
		</c:otherwise>
    </c:choose>
		
  </div>
</div>

<jsp:include page="footer.jsp"></jsp:include> 