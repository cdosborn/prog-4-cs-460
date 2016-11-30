<jsp:include page="/WEB-INF/view/partials/header.jsp"></jsp:include>
<form action="/patient.jsp" method="post">
<p>Enter the details for a new patient</p>
<table>
    <tbody>
        <tr>
            <td>patient#</td>
            <td><input type="number" name="patient#" placeholder="123">
        </tr>
        <tr>
            <td>first name</td>
            <td><input type="text" name="fname" placeholder="Alex"</td>
        </tr>
        <tr>
            <td>last name</td>
            <td><input type="text" name="lname" placeholder="Smith"</td>
        </tr>
        <tr>
            <td>date of birth</td>
            <td><input type="text" name="dob" placeholder="YYYY-MM-DD"</td>
        </tr>
    </tbody>
</table>
<input type="submit" value="Submit">
</form>
<jsp:include page="/WEB-INF/view/partials/footer.jsp"></jsp:include>
