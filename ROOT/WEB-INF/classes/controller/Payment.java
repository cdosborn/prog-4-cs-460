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

public class Payment extends HttpServlet {

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
        
        // Validate that parameter is present
        if (req.getParameter("patient") == null) {
                // Missing parameter, return bad request
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
        }
        
        String pno = req.getParameter("patient");
        System.out.println(pno); 

        /*String query = String.format("SELECT payment#, fname, lname, py.time AS time, amount " +
                    "FROM cdosborn.payment py, cdosborn.patient pa " + 
                    "WHERE py.patient#=pa.patient# AND py.patient#=(" +
                    "SELECT patient# FROM cdosborn.visit v, cdosborn.appt a " +
                    "WHERE v.visit#=%s AND v.appt#=a.appt#) ORDER BY payment#", vno);*/ 
        String query = String.format("SELECT payment#, fname, lname, time AS time, amount " +
                                     "FROM cdosborn.payment py, cdosborn.patient pa " +
                                     "WHERE py.patient#=pa.patient# AND pa.patient#=%s " +
                                     "ORDER BY payment#", pno);
        List<List<String>> data = new ArrayList<>();
        List<String> cols = Arrays.asList(new String[] {
            "payment#",
            "fname",
            "lname", 
            "time", 
            "amount"
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
        req.getRequestDispatcher("/WEB-INF/view/payment/payment.jsp").forward(req, resp);
    } // end doGet 
    
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException { 
        System.out.println("doPOST!!!");
        Database db = new Database();
        db.open();
        
        // Validate that all parameters are present
        if (req.getParameter("payment#")   == null ||
            req.getParameter("patient")   == null ||
            req.getParameter("time")       == null ||
            req.getParameter("amount")     == null) {
                // Missing parameter, return bad request
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
        }

        String pyno = req.getParameter("payment#");
        String pano = req.getParameter("patient");
        String time = req.getParameter("time");
        String amount = req.getParameter("amount");

        // Insert row into table
        String query = String.format("INSERT INTO cdosborn.payment VALUES (%s, %s, DATE '%s', %s)",
                pyno, pano, time, amount);

        try {
            db.executeUpdate(query);
        } catch (SQLException exc) {
            // Database unique constraint violation
            if (exc.getErrorCode() == 1) {
                req.setAttribute("redirect_url", "/payment.jsp?patient=" + pano);
                req.getRequestDispatcher("/WEB-INF/view/error/duplicate_key_error.jsp").forward(req, resp);
                return;
            } else {
                throw new ServletException("Database error!", exc);
            }
        } finally {
            db.close();
        }
        this.doGet(req, resp);
    } // end doPost 
    
} 
