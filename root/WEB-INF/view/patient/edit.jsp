<form action="/patient.jsp" method="post">
<input type="hidden" name="_method" value="put" />
<p>Enter the id of the patient you wish to edit</p>
    <table>
        <tbody>
            <tr>
                <td>patient#</td>
                <td><input type="number" name="patient#" placeholder="123">
            </tr>
        </tbody>
    </table>
<p>Enter the details you wish to alter</p>
    <table>
        <tbody>
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
