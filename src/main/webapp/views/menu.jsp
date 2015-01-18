<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<form action="<c:url value="/j_spring_security_logout" />" method="post" id="logoutForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>

<div class="navbar navbar-inverse">
  <div class="container">
  
    <a class="navbar-brand" href="<c:url value="/" />" data-toggle="tooltip" data-placement="bottom" title="Home">
    	<img src="<c:url value="/resources/pictures/rsz_gift.png" />" />
    	&nbsp; Mister Gifts
    </a>
    
    <ul class="nav navbar-nav">
    <c:if test="${not empty pageContext.request.userPrincipal.name}">
      <li<c:if test="${param.currentTab eq 'family'}"> class="active"</c:if>><a href="<c:url value="/family" />"<c:if test="${param.currentTab ne 'family'}"> data-toggle="tooltip" data-placement="bottom" title="See the family gifts list"</c:if>>Their list</a></li>
      <li<c:if test="${param.currentTab eq 'myList'}"> class="active"</c:if>><a href="<c:url value="/mylist" />"<c:if test="${param.currentTab ne 'myList'}"> data-toggle="tooltip" data-placement="bottom" title="See your gifts list"</c:if>>My List</a></li>
     </c:if>
      <li<c:if test="${param.currentTab eq 'about'}"> class="active"</c:if>><a href="<c:url value="/about" />"<c:if test="${param.currentTab ne 'about'}"> data-toggle="tooltip" data-placement="bottom" title="See who I am"</c:if>>About</a></li>
    </ul>
    
    <ul class="nav navbar-nav pull-right">
    
    <c:choose>
    	<c:when test="${pageContext.request.userPrincipal.name == null}">
	    	<li><a href="<c:url value="/login" />" data-toggle="tooltip" data-placement="bottom" title="Log in to share list">Log in</a></li>
    	</c:when>
    	
    	<c:otherwise>
   			<li><a href="javascript: $('#logoutForm').submit();" data-toggle="tooltip" data-placement="bottom" title="Log out of the portal">Log out (${pageContext.request.userPrincipal.name})</a></li>
    	</c:otherwise>
    </c:choose>
    
   	</ul>
  </div>
</div>
