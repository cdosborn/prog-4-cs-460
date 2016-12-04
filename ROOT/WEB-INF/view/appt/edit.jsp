<jsp:include page="/WEB-INF/view/partials/header.jsp"></jsp:include>
<form action="/appt.jsp" method="post">
<input type="hidden" name="_method" value="put" />
<p>Enter the id of the appointment you wish to edit</p>
    <table>
        <tbody>
            <tr>
                <td>appt#</td>
                <td><input type="number" name="appt#" placeholder="123">
            </tr>
        </tbody>
    </table>
<p>Enter the details you wish to alter</p>
    <table>
        <tbody>
            <tr>
                <td>patient#</td>
                <td><input type="number" name="patient#" placeholder="123"</td>
            </tr>
            <tr>
                <td>date</td>
                <td><input type="text" name="date" placeholder="YYYY/MM/DD"</td>
            </tr>
            <tr>
                <td>time</td>
                <td><input type="text" name="time" placeholder="HH24:MI:SS"</td>
            </tr>
        </tbody>
    </table>
    <input type="submit" value="Submit">
</form>
<jsp:include page="/WEB-INF/view/partials/footer.jsp"></jsp:include>
