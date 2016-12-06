<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/view/partials/header.jsp"></jsp:include>
<h2><a href="/">home</a>&nbsp;/ labs</h2>
<c:choose>
    <c:when test="${numrows == 0}">
        <p>The table has no results.</p>
    </c:when>
    <c:otherwise>
        <p>A list of the labs and the services they offer.</p>
        <table class="border">
            <thead>
                <tr>
                    <c:forEach items="${cols}" var="col">
                    <th>${col}</td>
                    </c:forEach>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${data}" var="row" varStatus="status">
                <tr>
                    <c:forEach items="${row}" var="elem">
                    <td>${elem}</td>
                    </c:forEach>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>
<jsp:include page="/WEB-INF/view/partials/footer.jsp"></jsp:include>

