<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<form action="<c:url value="/j_spring_security_logout" />" method="post" id="logoutForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>

<div class="navbar navbar-inverse">
  <div class="container">
  
    <a class="navbar-brand" href="<c:url value="/" />" data-toggle="tooltip" data-placement="bottom" title="Home">
    	<img src="<c:url value="/resources/pictures/rsz_gift.png" />" />
    	&nbsp; MisterGifts
    </a>
    
    <ul class="nav navbar-nav">
      <li><a href="<c:url value="/family" />" data-toggle="tooltip" data-placement="bottom" title="See the family gifts list">Family lists</a></li>
      <li><a href="<c:url value="/mylist" />" data-toggle="tooltip" data-placement="bottom" title="See your gifts list">My List</a></li>
      <li><a href="<c:url value="/about" />" data-toggle="tooltip" data-placement="bottom" title="See who I am">About</a></li>
    </ul>
    
    <ul class="nav pull-right navbar-nav">
    
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
