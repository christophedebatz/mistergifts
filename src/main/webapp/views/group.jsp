<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="header.jsp">
    <jsp:param name="currentTab" value="group"/>
</jsp:include>


<div class="container">
    <div class="jumbotron">
        <h2><spring:message code="site.page.grouplist.title"/></h2>

        <fieldset>
            <legend><spring:message code="site.page.grouplist.subtitle"/></legend>

            <c:choose>
                <c:when test="${ empty users }">

                    <div class="alert alert-info" role="alert">
                        <p><spring:message code="site.page.grouplist.error.nouser"/></p>
                    </div>

                </c:when>

                <c:when test="${ not hasGift }">

                    <div class="alert alert-info" role="alert">
                        <p><spring:message code="site.page.grouplist.error.nogiftlist"/></p>
                    </div>

                </c:when>

                <c:otherwise>

                    <div class="table-responsive overflow">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th><spring:message code="site.page.grouplist.username"/></th>
                                <th><spring:message code="site.page.grouplist.giftname"/></th>
                                <th><spring:message code="site.page.grouplist.giftbrand"/></th>
                                <th><spring:message code="site.page.grouplist.booker"/></th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:set var="currentButtonClass" value="btn-success"/>
                            <c:set var="currentRawClass" value="success"/>

                            <c:forEach items="${users}" var="user">

                                <c:set var="currentGift" value="0"/>

                                <c:forEach items="${user.ownedGifts}" var="gift">

                                    <c:choose>
                                        <c:when test="${gift.booker ne null}">
                                            <tr class="default">
                                        </c:when>
                                        <c:otherwise>
                                            <tr class="${currentRawClass}">
                                        </c:otherwise>
                                    </c:choose>

                                    <td>

                                        <c:choose>
                                            <c:when test="${currentGift eq 0}">
                                                <b>${user.username}</b>
                                            </c:when>
                                            <c:otherwise>
                                                &nbsp;
                                            </c:otherwise>
                                        </c:choose>

                                    </td>

                                    <td><a href="<c:url value="/gift/${gift.slug}" />">${gift.name}</a></td>
                                    <td>${gift.brand}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${empty gift.booker}">
                                                <form action="<c:url value="/grouplist/booking" />" method="post">
                                                    <input type="hidden" name="${_csrf.parameterName}"
                                                           value="${_csrf.token}"/>
                                                    <input type="hidden" name="giftId" value="${gift.id}"/>
                                                    <input type="submit" class="btn btn-xs ${currentButtonClass}"
                                                           value="<spring:message code="site.page.grouplist.book"/>"/>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <c:choose>
                                                    <c:when test="${gift.booker.username eq pageContext.request.userPrincipal.name}">
                                                        <form action="<c:url value="/grouplist/booking" />"
                                                              method="post">
                                                            <input type="hidden" name="${_csrf.parameterName}"
                                                                   value="${_csrf.token}"/>
                                                            <input type="hidden" name="giftId" value="${gift.id}"/>
                                                            <input type="submit" class="btn btn-xs btn-default"
                                                                   value="<spring:message code="site.page.grouplist.unbook"/>"/>
                                                        </form>
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${gift.booker.username}
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    </tr>

                                    <c:set var="currentGift" value="${currentGift + 1}"/>

                                </c:forEach>

                                <c:choose>
                                    <c:when test="${currentButtonClass eq 'btn-success' }">
                                        <c:set var="currentButtonClass" value="btn-primary"/>
                                        <c:set var="currentRawClass" value="info"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="currentButtonClass" value="btn-success"/>
                                        <c:set var="currentRawClass" value="success"/>
                                    </c:otherwise>
                                </c:choose>

                            </c:forEach>

                            </tbody>
                        </table>
                    </div>

                </c:otherwise>
            </c:choose>
        </fieldset>

        <fieldset>
            <legend><spring:message code="site.page.grouplist.userlist"/> (${fn:length(users)})</legend>

            <c:forEach items="${users}" var="user">
                <span style="font-size: 13px;" class="label label-default"><span class="glyphicon glyphicon-user"></span> ${user.username}</span>&nbsp;
            </c:forEach>
             <span style="font-size: 13px;" class="label label-primary"><span class="glyphicon glyphicon-user"></span> ${pageContext.request.userPrincipal.name}</span>

        </fieldset>
    </div>
</div>

<jsp:include page="footer.jsp"></jsp:include> 