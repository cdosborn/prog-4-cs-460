<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/view/partials/header.jsp"></jsp:include>
<form action="/visit.jsp" method="post">
<p>Enter the details for a new visit.</p>
<table>
    <tbody>
        <tr>
            <td>visit#</td>
            <td><input type="number" name="visit#" placeholder="123">
        </tr>
        <tr>
            <td>appt#</td>
            <td><input type="number" name="appt#" placeholder="123"/td>
        </tr>
        <tr>
            <td>service#</td>
            <td><input type="number" name="service#" placeholder="123"/td>
        </tr>
    </tbody>
</table>
<input type="submit" value="Submit">
</form>
<jsp:include page="/WEB-INF/view/partials/footer.jsp"></jsp:include>
