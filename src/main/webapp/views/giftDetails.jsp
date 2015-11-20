<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="header.jsp"></jsp:include>

<div class="container">
  <div class="jumbotron">
  
    <h2><spring:message code="site.page.details.title"/></h2>

	<fieldset>
	
		<c:choose>
			<c:when test="${selectedGift != null}">
				<legend>${selectedGift.name}</legend>
				
				<c:if test="${not empty selectedGift.picture}">
					<img src="${selectedGift.picture}" title="${selectedGift.brand} ${selectedGift.name}" style="float: right;" />
				</c:if>

				<h4><span class="label label-default"><spring:message code="site.page.details.brand"/></span></h4>
				<p>${selectedGift.brand}</p>

				<h4><span class="label label-default"><spring:message code="site.page.details.details"/></span></h4>
				<p>${selectedGift.details}</p>
				
				<div style="margin-top: 40px;">
					<c:if test="${not empty selectedGift.shopLinks}">
						<h4><span class="label label-default"><spring:message code="site.page.details.shoplinks"/></span></h4>
						<ul style="list-style-type: none;">
							<c:forEach items="${selectedGift.shopLinks}" var="link">
								<li><span class="glyphicon glyphicon-link"> <a class="link-list" href="${link}" target="_blank">${fn:substring(link, 0, 80)}...</a></span></li>
							</c:forEach>
						</ul>
					</c:if>
				</div>
			</c:when>
				
			<c:otherwise>
				<div class="alert alert-info" role="warning">
					<p><spring:message code="site.page.details.error.notfound"/></p>
				</div>
			</c:otherwise>
		</c:choose>
			
	</fieldset>
	
	<p style="text-align: right;">
		<button onclick="javascript: history.back();" class="btn btn-default"><spring:message code="site.page.details.back"/></button>
	</p>
		
  </div>
</div>

<jsp:include page="footer.jsp"></jsp:include> 