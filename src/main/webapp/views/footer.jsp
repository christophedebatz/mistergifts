			<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

			</div>
			
			<div id="footer">
	      <div class="container">
	        <p class="text-muted credit">Developed in Java by <a href="http://github.com/christophedebatz" target="_blank">Christophe de Batz</a>.</p>
	      </div>
	    </div>
	
		<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
		<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
		
		<script type="text/javascript">

			$(function () {
			  $('[data-toggle="tooltip"]').tooltip()
			});
		</script>
			
	</body>
</html>