<jsp:include page="/WEB-INF/view/partials/header.jsp"></jsp:include>
<form action="/appt.jsp" method="post">
<input type="hidden" name="_method" value="delete" />
<p>Enter the appt# for the appointment to be deleted</p>
<table>
    <tbody>
        <tr>
            <td>appt#</td>
            <td><input type="number" name="appt#" placeholder="123">
        </tr>
    </tbody>
</table>
<input type="submit" value="Submit">
</form>
<jsp:include page="/WEB-INF/view/partials/footer.jsp"></jsp:include>
