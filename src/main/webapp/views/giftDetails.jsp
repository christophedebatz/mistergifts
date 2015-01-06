<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="header.jsp"></jsp:include>

<div class="container">
  <div class="jumbotron">
  
    <h2>Gift informations</h2>

	<fieldset>
	
		<c:choose>
			<c:when test="${selectedGift != null}">
				<legend>${selectedGift.name}</legend>

				<h4><span class="label label-default">Details</span></h4>
				<p>${selectedGift.details}</p>
				
				<div style="margin-top: 40px;">
					<c:if test="${not empty selectedGift.shopLinks}">
						<h4><span class="label label-default">Shop links</span></h4>
						<ul style="list-style-type: none;">
							<c:forEach items="${selectedGift.shopLinks}" var="link">
								<li><span class="glyphicon glyphicon-link"> <a class="link-list" href="${link}" target="_blank">${link}</a></span></li>
							</c:forEach>
						</ul>
					</c:if>
				</div>
			</c:when>
				
			<c:otherwise>
				<div class="alert alert-info" role="warning">
					<p>This gift does not exist...</p>
				</div>
			</c:otherwise>
		</c:choose>
			
	</fieldset>
	
	<p style="text-align: right;">
		<button onclick="window.location='<c:url value="/mylist" />';" class="btn btn-default">Back to gift list</button>
	</p>
		
  </div>
</div>

<jsp:include page="footer.jsp"></jsp:include> 