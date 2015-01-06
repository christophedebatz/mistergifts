<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="header.jsp"></jsp:include>


<div class="container">
  <div class="jumbotron">
    <h2>Family gifts lists</h2>

	<fieldset>
		<legend>Lists</legend>

		<c:choose>
			<c:when test="${ empty users }">
			
				<div class="alert alert-info" role="alert">
					<p>There is no users currently.</p>
				</div>
				
			</c:when>
			
			<c:otherwise>
			
				<div class="table-responsive overflow">
				  <table class="table">
					  <thead>
						  <tr>
						  	<th>User name</th>
						  	<th>Gift name</th>
						  	<th>Gift brand</th>
						  	<th>&nbsp;</th>
						  </tr>
				  		</thead>
				  		
				  		<c:set var="currentButtonClass" value="btn-success" />
				  		
				  		<c:forEach items="${users}" var="user">
				  		
				  			<c:set var="currentGift" value="0" />
				  		
					  		<c:forEach items="${user.ownedGifts}" var="gift">
					  		
						  		<tr<c:if test="${not empty currentRawBackground}"> style="background: ${currentRawBackground};"</c:if>>
				
									<td>
										  		
						  			<c:choose>
						  				<c:when test="${currentGift eq 0}">
							  				<b>${user.username}</b>
							  			</c:when>
							  			<c:otherwise>
							  				&nbsp;
							  			</c:otherwise>
							  		</c:choose>
							  		
							  		</td>
							  		
					  				<td>${gift.name}</td>
					  				<td>${gift.brand}</td>
					  				<td>
					  					<form action="<c:url value="/family/booking" />" method="post">
					  						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					  						<input type="hidden" name="giftId" value="${gift.id}" />
					  						<input type="submit" class="btn btn-xs ${currentButtonClass}" value="Book" />
				  						</form>
					  				</td>
							  	
							  	</tr>
							  	
							  	<c:set var="currentGift" value="${currentGift + 1}" />
						  	
						  	</c:forEach>
						  	
						  	<c:choose>
						  		<c:when test="${currentButtonClass eq 'btn-success' }">
						  			<c:set var="currentButtonClass" value="btn-primary" />
						  			<c:set var="currentRawBackground" value="#F9F9F9" />
						  		</c:when>
						  		<c:otherwise>
						  			<c:set var="currentButtonClass" value="btn-success" />
						  			<c:remove var="currentRawBackground" />
						  		</c:otherwise>
						  	</c:choose>
						  	
				  		</c:forEach>
			  		
	  			  </table>
			   </div>
			
			</c:otherwise>
	
		</c:choose>
	</fieldset>
  </div>
</div>

<jsp:include page="footer.jsp"></jsp:include> 