/*+----------------------------------------------------------------------
 ||
 ||  Class  MostUsedSupplies
 ||
 ||         Author:  Connor Osborn
 ||
 ||        Purpose:  Controller class for the 4rd query:
 ||                     'Most used supplies'.
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
 ||                  Returns a HttpServletResponse to most-used-supplies.jsp.
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

public class MostUsedSupplies extends HttpServlet {

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
    |              most-used-supplies.jsp file.
    |
    |  Returns:  Returns the query results to the web application.
    *-------------------------------------------------------------------*/
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Database db = new Database();
        db.open();

        List<List<String>> data = new ArrayList<>();
        List<String> cols = Arrays.asList(new String[] {
            "supply#",
            "name",
            "total",
        });

        // Query supplies most used in treating patients
        String query =
            " SELECT supply.supply#, supply.name, SUM(COALESCE(servicesupply.quantity,0)) total" +
            " FROM" +
            "     visit" +
            "     INNER JOIN" +
            "     servicesupply ON visit.service# = servicesupply.service#" +
            "     RIGHT OUTER JOIN" +
            "     supply ON servicesupply.supply# = supply.supply#" +
            " GROUP BY supply.supply#, supply.name, supply.supply#" +
            " ORDER BY total DESC";

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
        req.getRequestDispatcher("/WEB-INF/view/most-used-supplies/most-used-supplies.jsp").forward(req, resp);
    }
}
