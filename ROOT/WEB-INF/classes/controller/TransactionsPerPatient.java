/*+----------------------------------------------------------------------
 ||
 ||  Class TransactionsPerPatient
 ||
 ||         Author:  Connor Osborn
 ||
 ||        Purpose:  Controller class for the 1st query:
 ||                     'Transactions by patient'.
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
 ||                  Returns a HttpServletResponse to xaction-per-patient.jsp.
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

public class TransactionsPerPatient extends HttpServlet {

   /*---------------------------------------------------------------------
    |  Method doPost
    |
    |  Purpose:  Executes the query and calls doGet to show the updated tuples
    |            to the user.
    |
    |  Pre-condition:  None.
    |
    |  Post-condition: None.
    |
    |  Parameters:
    |      req -- HttpServletRequest containing tuple info.
    |      resp -- HttpServletResponse that will be sent back to the
    |              xaction-per-patient.jsp file.
    |
    |  Returns:  None.
    *-------------------------------------------------------------------*/
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Database db = new Database();
        db.open();

        // Yield the primary key from the paramaters
        String pno = req.getParameter("patient#");

        // Validate that the pk is not null
        if (pno == null) {
            // Missing parameter, return bad request
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Validate that the pk is a number
        try {
            Integer.parseInt(pno);
        } catch (NumberFormatException e) {
            // Paramater is not an integer, return bad request
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        List<List<String>> data = new ArrayList<>();
        List<String> cols = Arrays.asList(new String[] {
            "time",
            "item",
            "amount",
        });

        // Query for table of transactions
        String query = String.format(
            " (" +
            "     SELECT TIME, 'PAYMENT' ITEM, amount AMOUNT  " +
            "     FROM payment" +
            "     WHERE payment.patient#=%1$s" +
            "     UNION ALL" +
            "     SELECT TIME, name ITEM, (-1 * QUANTITY * COST) AMOUNT" +
            "     FROM (" +
            "         SELECT appt.time TIME, supply.name NAME, servicesupply.quantity QUANTITY, supply.cost COST" +
            "         FROM appt, visit, service, servicesupply, supply" +
            "         WHERE appt.patient#=%1$s" +
            "           AND visit.appt#=appt.appt#    " +
            "           AND service.service#=visit.service#" +
            "           AND servicesupply.service#=visit.service#" +
            "           AND supply.supply#=servicesupply.supply#" +
            "         UNION" +
            "         SELECT appt.time TIME, service.name NAME,1 QUANTITY,service.cost COST" +
            "         FROM appt, visit, service " +
            "         WHERE appt.patient#=%1$s" +
            "           AND visit.appt#=appt.appt#    " +
            "           AND service.service#=visit.service#" +
            "     ) " +
            " ) ORDER BY TIME", pno);

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
        this.doGet(req, resp);
    }

    /*---------------------------------------------------------------------
    |  Method doGet
    |
    |  Purpose:  Gets an HttpServletRequest and HttpServletResponse from
    |            doPost and sends the answer of the query to the web
    |            application.
    |
    |  Pre-condition:  None.
    |
    |  Post-condition: None.
    |
    |  Parameters:
    |      req -- HttpServletRequest containing tuple info.
    |      resp -- HttpServletResponse that will be sent back to the
    |              xaction-per-patient.jsp file.
    |
    |  Returns:  Returns the query results to the web application.
    *-------------------------------------------------------------------*/
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/WEB-INF/view/xaction-per-patient/xaction-per-patient.jsp").forward(req, resp);
    }
}
