<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

            </div>
        </div>

        <div id="footer">
            <div class="container">
                <p class="text-muted credit"><spring:message code="site.footer.credits"/>
                    <c:set var="now" value="<%=new java.util.Date()%>" />
                    <a href="http://github.com/christophedebatz" target="_blank">Christophe de Batz</a>&nbsp; | &nbsp; &copy; MisterGift.io 2015 - <fmt:formatDate pattern="yyyy" value="${now}" />.
                </p>
            </div>
        </div>

        <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
        <script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
        <script src="<c:url value="/resources/js/option-control/bootstrap-select.min.js" />"></script>

        <script type="text/javascript">
            $(function () {
                $('[data-toggle="tooltip"]').tooltip();
                $('.language-menu').tooltip().dropdown();
            });
        </script>
    </body>
</html>