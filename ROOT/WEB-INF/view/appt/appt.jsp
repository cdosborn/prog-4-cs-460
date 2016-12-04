<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/view/partials/header.jsp"></jsp:include>
<h2>Appointments</h2>
<div>
    <a href='/appt/edit.jsp'>edit</a>
    <a href='/appt/create.jsp'>create</a>
    <a href='/appt/delete.jsp'>delete</a>
</div>
<c:choose>
    <c:when test="${false}">
        <p>The table has no results.</p>
    </c:when>
    <c:otherwise>
        <p>There are ${numrows} results.</p>
        <table>
            <tr>
            <c:forEach items="${cols}" var="col">
                <td>${col}</td>
            </c:forEach>
            </tr>
            <c:forEach items="${data}" var="row" varStatus="status">
            <tr>
                <c:forEach items="${row}" var="elem">
                <td>${elem}</td>
                </c:forEach>
            </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
<jsp:include page="/WEB-INF/view/partials/footer.jsp"></jsp:include>

