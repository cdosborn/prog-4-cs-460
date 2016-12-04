<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/view/partials/header.jsp"></jsp:include>
<h2><a href="/">home</a>&nbsp;/&nbsp;<a href="/queries.jsp">queries</a>&nbsp;/ transactions by patient</h2>
<form action="/queries/xaction-per-patient.jsp" method="post">
    <p>View transactions by patient#: </span><input type="number" name="patient#" placeholder="123">
    <input type="submit" value="Submit">
</form>
<c:choose>
    <c:when test="${numrows == 0}">
        <p>The patient has no transactions.</p>
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
