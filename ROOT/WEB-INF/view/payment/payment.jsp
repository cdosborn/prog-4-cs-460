<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/view/partials/header.jsp"></jsp:include>
<h2><a href="/">home</a>&nbsp;/<a href="/visit.jsp">visits</a>&nbsp;/ payment</h2>
<form action="/payment.jsp" method="post">
<p>Make a new payment</p>
<table>
    <tbody>
        <tr>
            <td>payment#</td>
            <td><input type="number" name="payment#" placeholder="123">
        </tr>
        <tr>
            <td>patient#</td>
            <td><input type="number" name="patient" placeholder="123"</td>
        </tr>
        <tr>
            <td>date</td>
            <td><input type="text" name="time" placeholder="YYYY-MM-DD"</td>
        </tr>
        <tr>
            <td>amount</td>
            <td><input type="number" name="amount" placeholder="123"</td>
        </tr>
    </tbody>
</table>
<input type="submit" value="Submit">
</form>
    
<c:choose>
    <c:when test="${numrows == 0}">
        <p>This patient has no payments.</p>
    </c:when>
    <c:otherwise>
        <p>${numrows} payments found.</p>
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
    
    