<jsp:include page="/WEB-INF/view/partials/header.jsp"></jsp:include>
<form action="/visit.jsp" method="post">
<input type="hidden" name="_method" value="delete" />
<p>Enter the visit# for the visit to be deleted</p>
<table>
    <tbody>
        <tr>
            <td>visit#</td>
            <td><input type="number" name="visit#" placeholder="123">
        </tr>
    </tbody>
</table>
<input type="submit" value="Submit">
</form>
<jsp:include page="/WEB-INF/view/partials/footer.jsp"></jsp:include>
