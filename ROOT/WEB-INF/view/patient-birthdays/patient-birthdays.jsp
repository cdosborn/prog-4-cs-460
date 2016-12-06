<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/view/partials/header.jsp"></jsp:include>
<h2><a href="/">home</a>&nbsp;/&nbsp;<a href="/queries.jsp">queries</a>&nbsp;/ patient-birthdays</h2>
<p>View patients with birthdays this month</p>
<c:choose>
    <c:when test="${numrows == 0}">
        <p>There are no results.</p>
    </c:when>
    <c:otherwise>
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
