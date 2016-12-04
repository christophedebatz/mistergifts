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
					<p>Form was not filled correctly or an error occured. Try again or contact me.</p>
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
									<c:if test="${gift.viewers != null && gift.viewers.size() > 0}">
										<img src="<c:url value="/resources/pictures/cadna.png" />" data-toggle="tooltip" data-placement="bottom" title="<spring:message code="site.page.mylist.onlyviewercadna" arguments="${gift.viewers.size() }" />">&nbsp;
									</c:if>
									<a about="w" href="<c:url value="/gift/${gift.slug}" />">
									<c:choose>
										<c:when test="${fn:length(gift.name) > 30 }">
											${fn:substring(gift.name, 0, 27)}...
										</c:when>
										<c:otherwise>
											${gift.name}
										</c:otherwise>
									</c:choose>
								</a></td>
								<td>
									<c:choose>
										<c:when test="${gift.brand eq '' }">
											<span style="text-align: center;">-</span>
										</c:when>
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
										<c:when test="${fn:length(gift.details) > 40 }">
											${fn:substring(gift.details, 0, 38)}...
										</c:when>
										<c:otherwise>
											${gift.details}
										</c:otherwise>
									</c:choose>
								</td>
								<td width="20">
									<c:if test="${gift.booker eq null}">
										<form action="<c:url value="/gift/${gift.id}" />" method="post">
											<input type="hidden" name="giftId" value="${gift.id}" />
											<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
											<label for="submitDelete-${gift.id}" class="btn btn-xs btn-danger" data-toggle="tooltip" data-placement="top" title="Remove gift"><i class="glyphicon glyphicon-trash"></i></label>
											<input id="submitDelete-${gift.id}" type="submit" class="hidden" />
										</form>
									</c:if>
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
					<select name="viewers" class="selectpicker show-tick form-control" multiple data-live-search="true" title="<spring:message code="site.page.mylist.form.onlyvieweroption"/>" multiple>
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

				<div style="float: right; margin-bottom: 10px;" class="btn-group">
					<button type="button" onclick="addLink();" class="btn btn-xs btn-success"><spring:message code="site.page.mylist.form.add"/></button>
					<button type="button" onclick="removeLink();" class="btn btn-xs btn-danger"><spring:message code="site.page.mylist.form.remove"/></button>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title"><spring:message code="site.page.mylist.form.picture"/></h3>
					</div>
					<div class="panel-body">
						<div class="form-group">
							<div id="TextBoxesGroup">
								<div id="TextBoxDiv1">
									<input type="text" name="shoplink" id="shoplink1" class="form-control" onBlur="javascript: onLinkChanged(this.id, this.value);" style="margin-bottom: 2px; margin-top: 2px;" placeholder='<spring:message code="site.page.mylist.form.shoplink"/>1...' />
								</div>
							</div>
						</div>

                        <div align="center">
                            <img src="<c:url value="/resources/pictures/action-loader.gif" />" alt="Wait..." id="previews-waiter" style="padding:5px; display:none; margin:auto;"/>
                        </div>

                        <div class="row">
                            <div class="col-md-1">
                                <button type="button" class="btn btn-xs btn-primary" style="display: none;" id="previous" onclick="javascript: onPrevious()">
                                    <span class="glyphicon glyphicon-chevron-left"></span>
                                </button>
                            </div>
                            <div class="col-md-2">
                                <span id="paging"></span>
                            </div>
                            <div class="col-md-1">
                                <button type="button" class="btn btn-xs btn-primary" style="display: none;" id="next" onclick="javascript: onNext();">
                                    <span class="glyphicon glyphicon-chevron-right"></span>
                                </button>
                            </div>
                            <div class="col-md-6">
                                <div id="previews"></div>
                            </div>
                            <div class="col-md-2">
                                <button type="button" class="btn btn-xs btn-primary" style="display: none;" id="remove" onclick="javascript: onRemove();">
                                    <span class="glyphicon glyphicon-remove"></span>
                                </button>
                            </div>
                        </div>

                        <input type="hidden" name="picture" id="picture">

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
    var currentPicture = 0;
    var pictures = [];

    function onPrevious() {
        if (currentPicture === 0) return;
        if (currentPicture - 1 === 0) $("#previous").hide();
        else $("#previous").show();

        currentPicture--;
        $("#next").show();
        $("#previews").html(getPictureLink(pictures[currentPicture]));
        $("#picture").val(pictures[currentPicture]);
        $("#paging").html((currentPicture + 1) + " / " + pictures.length);
    }

    function onNext() {
        if (currentPicture === pictures.length - 1) return;
        if (currentPicture + 1 === pictures.length - 1) $("#next").hide();
        else $("#next").show();

        currentPicture++;
        $("#previous").show();
        $("#previews").html(getPictureLink(pictures[currentPicture]));
        $("#picture").val(pictures[currentPicture]);
        $("#paging").html((currentPicture + 1) + " / " + pictures.length);
    }

    function onRemove() {
        $("#previous").hide();
        $("#next").hide();
        $("#picture").val("");
        $("#previews").hide();
        $("#remove").hide();
        $("#paging").hide();
        pictures = [];
    }

	function addLink() {
		if (counter > 5) {
			window.alert('You cannot overpass 5 shop links for a gift.');
			return;
		}

		var newTextBoxDiv = $(document.createElement('div')).attr("id", 'TextBoxDiv' + counter);

		newTextBoxDiv.after().html('<input style="margin-bottom: 2px; margin-top: 2px;" type="text" name="shoplink" onBlur="javascript: onLinkChanged(this.id, this.value);" id="shoplink' + counter + '" class="form-control" placeholder="<spring:message code="site.page.mylist.form.shoplink"/>' + counter + '..." />');
		newTextBoxDiv.appendTo("#TextBoxesGroup");
		counter++;
	}

    function getPictureLink(url) {
        return '<button type="button" onclick="javascript:$(\'#picture\').value=\'' + url + '\';" class="thumbnail"><img src="' + url + '">'
    }

    function onLinkChanged(id, link) {
        //var address = "https://old.mistergift.io/link-images";
        var address = "http://localhost:8080/mistergifts/link-images";

        if (link === "") return;
        $("#previews-waiter").show();
        $("#previews").hide();
        $.ajax({
            url: address,
            data: { url: link },
            success: function (data) {
                $.each(data, function( key, val ) {
                    if (pictures.indexOf(val) === -1) {
                        pictures.push(val);
                        if (pictures.length > 0) {
                            $("#paging").show().html("1 / " + pictures.length);
                            $("#previews").html(getPictureLink(val));
                            $("#next").show();
                            $("#picture").val(val);
                            $("#remove").show();
                        }
                    }
                });
            },
            dataType: "json"
        }).always(function() {
            $("#previews-waiter").hide();
            $("#previews").show();
        });
    }

	function removeLink() {
		if (counter > 2) {
			counter--;
			$("#TextBoxDiv" + counter).remove();
		}
	}
</script>

<jsp:include page="footer.jsp"></jsp:include>
