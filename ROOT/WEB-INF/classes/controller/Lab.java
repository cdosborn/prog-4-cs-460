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

public class Lab extends HttpServlet {
    
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
        
        String query = "SELECT l.lab#, l.name, s.name AS service FROM cdosborn.lab l, cdosborn.service s " +
            "WHERE EXISTS (SELECT * FROM cdosborn.labservice lb WHERE l.lab#=lb.lab# AND s.service#=lb.service#)";
        List<List<String>> data = new ArrayList<>();
        List<String> cols = Arrays.asList(new String[] {
            "lab#",
            "name",
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
        req.getRequestDispatcher("/WEB-INF/view/lab/lab.jsp").forward(req, resp);
    }
}
