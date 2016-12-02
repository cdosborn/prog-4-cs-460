<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/view/partials/header.jsp"></jsp:include>
<h2><a href="/">home</a>&nbsp;/ charges</h2>
<div>
    <!--
    <a href='/patient/edit.jsp'>edit</a>
    <a href='/patient/create.jsp'>create</a>
    <a href='/patient/delete.jsp'>delete</a>
    -->
</div>
<c:choose>
    <c:when test="${numrows == 0}">
        <p>The table has no results.</p>
    </c:when>
    <c:otherwise>
        <p>There are ${numrows} results. Each result is a charge table for a specific service.</p>
        <table>
        <c:forEach items="${tables}" var="table">
            <tr>
            <c:forEach items="${cols}" var="col">
                <td>${col}</td>
            </c:forEach>
            </tr>
            <c:forEach items="${table}" var="row" varStatus="status">
            <tr>
                <c:forEach items="${row}" var="elem">
                <td>${elem}</td>
                </c:forEach>
            </tr>
            </c:forEach>
            <tr>
            <c:forEach items="${cols}" var="col"> <td>&nbsp;</td></c:forEach>
            </tr>
        </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
<jsp:include page="/WEB-INF/view/partials/footer.jsp"></jsp:include>
