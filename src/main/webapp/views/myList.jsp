<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="header.jsp"></jsp:include>


<div class="container">
  <div class="jumbotron">
    <h2>My gift list</h2>

		<fieldset>
			<legend>My list</legend>

			<c:choose>
				<c:when test="${ empty user.ownedGifts }">
				
					<div class="alert alert-info" role="alert">
						<p>Your current gift list is still empty yet...</p>
					</div>
					
				</c:when>
				
				<c:otherwise>
				
					<div class="table-responsive">
					  <table class="table table-hover">
						  <thead>
							  <tr>
							  	<th>Name</th>
							  	<th>Details</th>
							  	<th>&nbsp;</th>
							  </tr>
					  		</thead>
					  		
					  		<c:forEach items="${user.ownedGifts}" var="gift">
						  	
						  		<tr style="cursor: pointer;" onclick="window.location='./mylist/${gift.id}';">
							  		<td><a href="<c:url value="/mylist/${gift.id}" />">${gift.name}</a></td>
							  		<td>${gift.details}</td>
							  		<td><a class="btn btn-xs btn-danger">Remove</a></td>
						  		</tr>
						  		
					  		</c:forEach>
				  		
		  			  </table>
				   </div>
				
				</c:otherwise>
		
			</c:choose>
		</fieldset>

		<img src="<c:url value="/resources/pictures/gifts.png" />" style="float: right;" />
		
		<fieldset>
			<legend>New gift</legend>
			<form action="<c:url value="/mylist" />" method="post">
			
				<div class="form-group">
				    <input type="text" name="name" class="form-control" placeholder="Name..." />
			  	</div>
			  	
			  	<div class="form-group">
				    <input type="text" name="details" class="form-control" placeholder="Details..." />
			  	</div>
			  	
			  	<div style="float:right;" class="btn-group">
					<button type="button" onclick="addLink();" class="btn btn-xs btn-success">Add</button>
					<button type="button" onclick="removeLink();" class="btn btn-xs btn-danger">Remove</button>
				</div>
			  	
				<div class="form-group">
				    <div id="TextBoxesGroup">
						<div id="TextBoxDiv1">
							<input type="text" name="shoplink" id="shoplink1" class="form-control" style="margin-bottom: 2px; margin-top: 2px;" placeholder="Shop link #1..." />
						</div>
					</div>
			  	</div>
			  	
			  	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			  	
			  	<hr />
			  	<button class="btn btn-large btn-primary">Add this gift</button>
			</form>
		</fieldset>
			
  </div>
</div>

<script type="text/javascript">
 
	var counter = 2;
	
	function addLink () 
	{
		if (counter > 10) {
			window.alert('You cannot overpass 10 shop links for a gift.');
			return;
		}
		
		var newTextBoxDiv = $(document.createElement('div')).attr("id", 'TextBoxDiv' + counter);
		 
		newTextBoxDiv.after().html('<input style="margin-bottom: 2px; margin-top: 2px;" type="text" name="shoplink" id="shoplink' + counter + '" class="form-control" placeholder="Shop link #' + counter + '..." />');
		newTextBoxDiv.appendTo("#TextBoxesGroup");
	 
		counter++;
	}
	
	function removeLink() 
	{
	   	if(counter > 2) {
			counter--;
		    $("#TextBoxDiv" + counter).remove();
	   	}
	}
	    
</script>

<jsp:include page="footer.jsp"></jsp:include> 