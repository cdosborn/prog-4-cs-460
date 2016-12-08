/*+----------------------------------------------------------------------
 ||
 ||  Class Procedure
 ||
 ||         Author:  Margarita Norzagaray
 ||
 ||        Purpose:  This class is the controller for the 'procedures'
 ||                  view.
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
 ||                  Returns a HttpServletResponse to procedure.jsp.
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

public class Procedure extends HttpServlet {

     /*---------------------------------------------------------------------
    |  Method doGet
    |
    |  Purpose:  Selects all procedures provided by the main office.
    |
    |  Pre-condition:  None.
    |
    |  Post-condition: None.
    |
    |  Parameters:
    |      req -- HttpServletRequest containing tuple info.
    |      resp -- HttpServletResponse that will be sent back to the
    |              procedure.jsp file.
    |
    |  Returns:  Returns the query results to the web application.
    *-------------------------------------------------------------------*/
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("doGET!!!");
        Database db = new Database();
        db.open();

        String query = "SELECT s.service#, s.name as \"service name\" " +
            "FROM cdosborn.service s, cdosborn.procedure p " +
            "WHERE s.service#=p.service# ORDER BY s.service#"; 
        List<List<String>> data = new ArrayList<>();
        List<String> cols = Arrays.asList(new String[] {
            "service#",
            "service name"
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
        req.getRequestDispatcher("/WEB-INF/view/service/procedure.jsp").forward(req, resp);
    }
}
