<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="header.jsp">
	<jsp:param name="currentTab" value="myList" />
</jsp:include>

<div class="container">
	<div class="jumbotron">

		<h2><spring:message code="site.page.mylist.title"/></h2>
		<h4><spring:message code="site.page.mylist.subtitle"/></h4>

		<c:choose>
			<c:when test="${error != null}">

				<div class="alert alert-warning" role="alert">
					<p>Error when uploading picture.</p>
				</div>

			</c:when>
			<c:when test="${fn:length(user.ownedGifts) <= 0 }">

				<div class="alert alert-info" role="alert">
					<p><spring:message code="site.page.mylist.warn.empty"/></p>
				</div>

			</c:when>

			<c:otherwise>

				<div class="table-responsive">
					<table class="table table-hover">
						<thead>
							<tr>
								<th><spring:message code="site.page.mylist.tableheader.name"/></th>
								<th><spring:message code="site.page.mylist.tableheader.brand"/></th>
								<th><spring:message code="site.page.mylist.tableheader.details"/></th>
								<th>&nbsp;</th>
							</tr>
						</thead>

						<c:forEach items="${user.ownedGifts}" var="gift">

							<tr>
								<td>
									<c:if test="${gift.onlyViewer != null}">
										<img src="<c:url value="/resources/pictures/cadna.png" />" data-toggle="tooltip" data-placement="bottom" title="<spring:message code="site.page.mylist.onlyviewercadna" arguments="${gift.onlyViewer.username}"/>">&nbsp;
									</c:if>
									<a about="w" href="<c:url value="/gift/${gift.slug}" />">
									<c:choose>
										<c:when test="${fn:length(gift.name) > 20 }">
											${fn:substring(gift.name, 0, 20)}...
										</c:when>
										<c:otherwise>
											${gift.name}
										</c:otherwise>
									</c:choose>
								</a></td>
								<td>
									<c:choose>
										<c:when test="${fn:length(gift.brand) > 20 }">
											${fn:substring(gift.brand, 0, 20)}...
										</c:when>
										<c:otherwise>
											${gift.brand}
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${fn:length(gift.details) > 47 }">
											${fn:substring(gift.details, 0, 47)}...
										</c:when>
										<c:otherwise>
											${gift.details}
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${gift.booker ne null}"><spring:message code="site.page.mylist.unremovable"/></c:when>
										<c:otherwise>
											<form action="<c:url value="/gift" />" method="post">
												<input type="hidden" name="giftId" value="${gift.id}" />
												<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
												<input type="submit" class="btn btn-xs btn-danger" value="<spring:message code="site.page.mylist.remove"/>" />
											</form>
										</c:otherwise>
									</c:choose>
									
									</td>
								</tr>

						</c:forEach>

					</table>
				</div>

			</c:otherwise>

		</c:choose>

		<p>&nbsp;</p>

		<img src="<c:url value="/resources/pictures/gifts.png" />"style="float: right;" />

		<h4><spring:message code="site.page.mylist.form.title"/></h4>
		<form action="<c:url value="/mylist" />" method="post" style="width: 85%;">
			<fieldset>
				<legend><spring:message code="site.page.mylist.form.required"/></legend>

				<div class="form-group">
					<input type="text" name="name" class="form-control"
						placeholder="<spring:message code="site.page.mylist.form.name"/>" />
				</div>

				<div class="form-group">
					<input type="text" name="brand" class="form-control"
						placeholder="<spring:message code="site.page.mylist.form.brand"/>" />
				</div>
			</fieldset>

			<fieldset>
				<legend><spring:message code="site.page.mylist.form.onlyviewertitle"/></legend>
				<div class="form-group">
					<select name="onlyViewer" class="selectpicker show-tick form-control" data-live-search="true" title="<spring:message code="site.page.mylist.form.onlyvieweroption"/>">
						<c:forEach items="${users}" var="user">
							<option data-icon="glyphicon-user" value="${user.username}">${user.username}</option>
						</c:forEach>
					</select>
				</div>
			</fieldset>

			<fieldset>
				<legend><spring:message code="site.page.mylist.form.optional"/></legend>
				<div class="form-group">
					<textarea name="details" class="form-control"
						placeholder="<spring:message code="site.page.mylist.form.details"/>"></textarea>
				</div>

				<div class="form-group">
					<input type="text" name="picture" class="form-control"
						placeholder="<spring:message code="site.page.mylist.form.picture"/>" />
				</div>

				<div style="float: right; margin-bottom: 10px;" class="btn-group">
					<button type="button" onclick="addLink();" class="btn btn-xs btn-success"><spring:message code="site.page.mylist.form.add"/></button>
					<button type="button" onclick="removeLink();" class="btn btn-xs btn-danger"><spring:message code="site.page.mylist.form.remove"/></button>
				</div>

				<div class="form-group">
					<div id="TextBoxesGroup">
						<div id="TextBoxDiv1">
							<input type="text" name="shoplink" id="shoplink1" class="form-control" style="margin-bottom: 2px; margin-top: 2px;" placeholder="<spring:message code="site.page.mylist.form.shoplink"/>1..." />
						</div>
					</div>
				</div>

				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</fieldset>

			<img src="<c:url value="/resources/pictures/action-loader.gif" />" alt="Wait..." id="waiter"
				 style="padding:5px; display:none;"/>
			<input type="submit" class="btn btn-large btn-primary"
				   onclick="this.style.display='none'; document.getElementById('waiter').style.display='block';"
				   value="<spring:message code="site.page.mylist.form.validate"/>"/>

		</form>

	</div>
</div>

<script type="text/javascript">
	var counter = 2;

	function addLink() {
		if (counter > 10) {
			window.alert('You cannot overpass 10 shop links for a gift.');
			return;
		}

		var newTextBoxDiv = $(document.createElement('div')).attr("id", 'TextBoxDiv' + counter);

		newTextBoxDiv.after().html('<input style="margin-bottom: 2px; margin-top: 2px;" type="text" name="shoplink" id="shoplink' + counter + '" class="form-control" placeholder="<spring:message code="site.page.mylist.form.shoplink"/>' + counter + '..." />');
		newTextBoxDiv.appendTo("#TextBoxesGroup");

		counter++;
	}

	function removeLink() {
		if (counter > 2) {
			counter--;
			$("#TextBoxDiv" + counter).remove();
		}
	}
</script>

<jsp:include page="footer.jsp"></jsp:include>
