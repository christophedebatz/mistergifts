<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp"></jsp:include>

<div class="container">
  <div class="jumbotron">
  
    <h2><spring:message code="site.page.details.title"/></h2>

	<fieldset>
	
		<c:choose>
			<c:when test="${selectedGift != null}">
				<legend>${selectedGift.name}</legend>
				
				<c:if test="${selectedGift.picture != null}">
                    <div id="details-picture">
					    <img src="<c:url value="/file/${selectedGift.picture}/" />" title="${selectedGift.brand} ${selectedGift.name}" />
                    </div>
                </c:if>

				<h4><span class="label label-default">Date de création</span></h4>
				<p><fmt:formatDate type="both" timeStyle="short" dateStyle="short" value="${selectedGift.creationDate}"/></p>

				<c:if test="${not empty selectedGift.brand}">
					<h4><span class="label label-default"><spring:message code="site.page.details.brand"/></span></h4>
					<p>${selectedGift.brand}</p>
				</c:if>

				<c:if test="${not empty selectedGift.details}">
					<h4><span class="label label-default"><spring:message code="site.page.details.details"/></span></h4>
					<p>${selectedGift.details}</p>
				</c:if>

				<c:if test="${not empty selectedGift.shopLinks}">
					<div style="margin-top: 40px;">
						<h4><span class="label label-default">
							<spring:message code="site.page.details.shoplinks"/>
						</span></h4>
						<ul style="list-style-type: none;	">
							<c:forEach items="${links}" var="link">
								<li><span class="glyphicon glyphicon-link"> <spring:message code="site.page.details.shoplinks.link"/> <a class="link-list" href="${link.key}" target="_blank">${link.value}</a></span></li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
			</c:when>
				
			<c:otherwise>
				<div class="alert alert-info">
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