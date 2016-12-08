/*+----------------------------------------------------------------------
 ||
 ||  Class Lab
 ||
 ||         Author:  Margarita Norzagaray
 ||
 ||        Purpose:  Controller class for the 'lab' view.
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
 ||  Class Methods:  doGet(HttpServletRequest req, HttpServletResponse resp)
 ||                  Returns a HttpServletResponse to lab.jsp.
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

public class Service extends HttpServlet {

    /*---------------------------------------------------------------------
    |  Method doGet
    |
    |  Purpose:  Selects all 'labs' in the database and displays the
    |            services each lab offers.
    |
    |  Pre-condition:  None.
    |
    |  Post-condition: None.
    |
    |  Parameters:
    |      req -- HttpServletRequest containing tuple info.
    |      resp -- HttpServletResponse that will be sent back to the
    |              charge.jsp file.
    |
    |  Returns:  Returns the query results to the web application.
    *-------------------------------------------------------------------*/
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("doGET!!!");
        Database db = new Database();
        db.open();

        String query =
            " SELECT service.service# service#, lab.name lab, service.name service" +
            " FROM" +
            " labservice " +
            " INNER JOIN" +
            "     service on labservice.service#=service.service#" +
            " RIGHT OUTER JOIN" +
            "     lab on lab.lab# = labservice.lab#" +
            " ORDER BY service.service#";

        List<List<String>> data = new ArrayList<>();
        List<String> cols = Arrays.asList(new String[] {
            "service#", 
            "lab",
            "service"
        });

        List<String> row;
        try {
            ResultSet rs  = db.execute(query);
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
        req.getRequestDispatcher("/WEB-INF/view/service/service.jsp").forward(req, resp);
    }
}
