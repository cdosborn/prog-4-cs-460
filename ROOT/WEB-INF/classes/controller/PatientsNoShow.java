/*+----------------------------------------------------------------------
 ||
 ||  Class DailyAppts
 ||
 ||         Author:  Connor Osborn  
 ||
 ||        Purpose:  Controller class for the 2nd query: 
 ||                     'Appointments where the patient did not show up'. 
 ||
 ||  Inherits From:  Extends HttpServlet. 
 ||
 ||     Interfaces:  None. 
 ||
 |+-----------------------------------------------------------------------
 ||
 ||      Constants:  None. 
 ||
 |+-----------------------------------------------------------------------
 ||
 ||   Constructors:  None; 
 ||
 ||  Class Methods:  doDelete(HttpServletRequest req, HttpServletResponse resp) 
 ||                  Returns a HttpServletResponse to patients-no-show.jsp. 
 ||
 ||  Inst. Methods:  None. 
 ||
 ++-----------------------------------------------------------------------*/
package controller;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class PatientsNoShow extends HttpServlet {
    
    /*---------------------------------------------------------------------
    |  Method doGet
    |
    |  Purpose:  Gets the response to the query. 
    |
    |  Pre-condition:  None. 
    |
    |  Post-condition: None.   
    |
    |  Parameters: 
    |      req -- HttpServletRequest containing tuple info.  
    |      resp -- HttpServletResponse that will be sent back to the
    |              patients-no-show.jsp file. 
    |   
    |  Returns:  Returns the query results to the web application. 
    *-------------------------------------------------------------------*/
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Database db = new Database();
        db.open();

        List<List<String>> data = new ArrayList<>();
        List<String> cols = Arrays.asList(new String[] {
            "appt#",
            "name",
            "time",
        });

        // Query for table of transactions
        String query =
            " WITH apptsnovisits as (" +
            "     SELECT * FROM appt WHERE appt.time < SYSDATE" +
            "     MINUS" +
            "     SELECT appt.* FROM visit, appt WHERE visit.appt#=appt.appt#)" +
            " SELECT apptsnovisits.appt#, fname || ' ' || lname name, time" +
            " FROM" +
            "     apptsnovisits" +
            "     INNER JOIN" +
            "     patient on apptsnovisits.patient# = patient.patient#";


        try {
            List<String> row;
            ResultSet rs = db.execute(query);
            while (rs.next()) {
                row = new ArrayList<>();
                for (String col : cols) {
                    row.add(rs.getString(col));
                }
                data.add(row);
            }
        } catch (SQLException exc) {
            throw new ServletException("Database error!", exc);
        } finally {
            db.close();
        }

        req.setAttribute("cols", cols);
        req.setAttribute("data", data);
        req.setAttribute("numrows", data.size());
        req.getRequestDispatcher("/WEB-INF/view/patients-no-show/patients-no-show.jsp").forward(req, resp);
    }
}
