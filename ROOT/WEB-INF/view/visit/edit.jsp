<jsp:include page="/WEB-INF/view/partials/header.jsp"></jsp:include>
<form action="/visit.jsp" method="post">
<input type="hidden" name="_method" value="put" />
<p>Enter the id of the visit you wish to edit</p>
    <table>
        <tbody>
            <tr>
                <td>visit#</td>
                <td><input type="number" name="visit#" placeholder="123">
            </tr>
        </tbody>
    </table>
<p>Enter the details you wish to alter</p>
    <table>
        <tbody>
            <tr>
                <td>appt#</td>
                <td><input type="number" name="appt#" placeholder="123"</td>
            </tr>
            <tr>
                <td>service#</td>
                <td><input type="number" name="service#" placeholder="123"</td>
            </tr>
        </tbody>
    </table>
    <input type="submit" value="Submit">
</form>
<jsp:include page="/WEB-INF/view/partials/footer.jsp"></jsp:include>
