<jsp:include page="/WEB-INF/view/partials/header.jsp"></jsp:include>
<h2><a href="/">home</a>&nbsp;/ queries</h2>
<ol>
    <li>
        <a href="/query/xaction-per-patient.jsp">Transactions by patient</a>
    </li>
    <li>
        <a href="/query/patients-no-show.jsp">Appointments where the patient did not show up</a>
    </li>
    <li>
        <a href="/query/todays-appts.jsp">Today's appointments</a>
    </li>
    <li>
        <a href="/query/most-used-supplies.jsp">Most used supplies</a>
    </li>
    <li>
        <a href="/query/patient-birthdays.jsp">Patients with birthdays this month</a>
    </li>
</ol>
<jsp:include page="/WEB-INF/view/partials/footer.jsp"></jsp:include>
