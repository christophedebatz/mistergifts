<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>


<form action="<c:url value="/j_spring_security_logout" />" method="post" id="logoutForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>

<div class="navbar navbar-inverse">
  <div class="container">
  
    <a class="navbar-brand" href="<c:url value="/" />">
    	<img src="<c:url value="/resources/pictures/rsz_gift.png" />" />
    	&nbsp; MisterGifts
    </a>
    
    <ul class="nav navbar-nav">
      <li><a href="#">Family lists</a></li>
      <li><a href="<c:url value="/mylist" />">My List</a></li>
      <li><a href="<c:url value="/about" />">About</a></li>
    </ul>
    
    <ul class="nav pull-right navbar-nav">
    
    <c:choose>
    	<c:when test="${pageContext.request.userPrincipal.name == null}">
	    	<li><a href="<c:url value="/login" />">Log in</a></li>
    	</c:when>
    	
    	<c:otherwise>
   			<li><a href="javascript: $('#logoutForm').submit();">Log out</a></li>
    	</c:otherwise>
    </c:choose>
    
   	</ul>
  </div>
</div>
