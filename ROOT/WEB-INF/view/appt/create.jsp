<jsp:include page="/WEB-INF/view/partials/header.jsp"></jsp:include>
<form action="/appt.jsp" method="post">
<p>Enter the details for a new appointment</p>
<table>
    <tbody>
        <tr>
            <td>appt#</td>
            <td><input type="number" name="appt#" placeholder="123">
        </tr>
        <tr>
            <td>patient#</td>
            <td><input type="number" name="patient#" placeholder="123"/td>
        </tr>
        <tr>
            <td>date</td>
            <td><input type="text" name="date" placeholder="YYYY/MM/DD"/td>
        </tr>
	    <tr>
            <td>time</td>
            <td><input type="text" name="time" placeholder="HH24:MI:SS"/td>
        </tr>
    </tbody>   
</table>
<input type="submit" value="Submit">
</form> 
<jsp:include page="/WEB-INF/view/partials/footer.jsp"></jsp:include>
