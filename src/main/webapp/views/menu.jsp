<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="navbar navbar-inverse">
  <div class="container">
    <a class="navbar-brand" href="./">Mister Gifts</a>
    <ul class="nav navbar-nav">
      <li><a href="#">Family lists</a></li>
      <li><a href="#">My List</a></li>
      <li><a href="#">About</a></li>
    </ul>
    <ul class="nav pull-right navbar-nav">
    	<li><a href="<c:url value="/login" />">Log in</a></li>
    </ul>
  </div>
</div>
