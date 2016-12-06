/*+----------------------------------------------------------------------
 ||
 ||  Class  Visit
 ||
 ||         Author:  Margarita Norzagaray
 ||
 ||        Purpose:  This class is the controller for the 'visits'
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
 ||  Class Methods:  doDelete(HttpServletRequest req, HttpServletResponse resp)
 ||                  doPut(HttpServletRequest req, HttpServletResponse resp)
 ||                  doPost(HttpServletRequest req, HttpServletResponse resp)
 ||                  doGet(HttpServletRequest req, HttpServletResponse resp)
 ||                  None of the classes return anything.
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

public class Visit extends HttpServlet {

    /*---------------------------------------------------------------------
    |  Method doDelete
    |
    |  Purpose:  Deletes a specific visit with the information (pk)
    |            given by the user. Calls doGet to show the updated
    |            tuples to the user.
    |
    |  Pre-condition:  Said visit should exists in the database.
    |
    |  Post-condition:  Said visit will be deleted from the database.
    |
    |  Parameters:
    |      req -- HttpServletRequest containing user input.
    |      resp -- HttpServletResponse that will be sent back to the
    |              visit.jsp file.
    |
    |  Returns:  None.
    *-------------------------------------------------------------------*/
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Database db = new Database();
        db.open();

        // Yield the primary key from the paramaters
        String vno = req.getParameter("visit#");

        // Validate that the pk is not null
        if (vno == null) {
            // Missing parameter, return bad request
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Validate that the pk is a number
        try {
            Integer.parseInt(vno);
        } catch (NumberFormatException e) {
            // Paramater is not an integer, return bad request
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Delete the visit
        String visitQuery = String.format(
            "DELETE FROM cdosborn.visit WHERE visit#=%s", vno);

        // Delete the patient
        try {
            db.executeUpdate(visitQuery); ///////////////////////
        } catch (SQLException exc) {
            throw new ServletException("Database error!", exc);
        }

        this.doGet(req, resp);
    } // end doDelete

    /*---------------------------------------------------------------------
    |  Method doPut
    |
    |  Purpose:  Updates a specific visit with the information
    |            given by the user. Calls doGet to show the updated tuples
    |            to the user.
    |
    |  Pre-condition:  Said visit should exists in the database.
    |
    |  Post-condition: Said visit will be updated with the user input.
    |
    |  Parameters:
    |      req -- HttpServletRequest containing user input.
    |      resp -- HttpServletResponse that will be sent back to the
    |              visit.jsp file.
    |
    |  Returns:  None.
    *-------------------------------------------------------------------*/
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Database db = new Database();
        db.open();

        // Validate that all parameters are present
        if (req.getParameter("visit#")    == null ||
            req.getParameter("appt#")  == null ||
            req.getParameter("service#")  == null) {
                // Missing parameter, return bad request
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
        }

        String vno = req.getParameter("visit#");
        String ano = req.getParameter("appt#");
        String sno = req.getParameter("service#");

        // Define SQL Update's where clause
        String whereClause = String.format("WHERE %s=%s",
               "visit#", vno);

        // Define the pairs of column and values to be updated
        String updatePairs = String.format("%s=%s, %s=%s",
               "appt#", ano,
               "service#", sno);

        // Define the entire update query
        String query = String.format("UPDATE cdosborn.visit SET %s %s",
                updatePairs,
                whereClause);

        System.out.println(query);

        // The number of updated rows
        int updated;

        // Update tuple
        try {
            updated = db.executeUpdate(query);
            if (updated == 0) {
                req.setAttribute("redirect_url", "/visit.jsp");
                req.getRequestDispatcher("/WEB-INF/view/error/no_record.jsp").forward(req, resp);
                return;
            }
        } catch (SQLException exc) {
            throw new ServletException("Database error!", exc);
        } finally {
            db.close();
        }

        this.doGet(req, resp);
    } // end doPut

    /*---------------------------------------------------------------------
    |  Method doPost
    |
    |  Purpose:  Inserts a new visit with the information given by the
    |            user. Calls doGet to show the updated tuples to the user.
    |
    |  Pre-condition:  None.
    |
    |  Post-condition: The new visit will be added to the database.
    |
    |  Parameters:
    |      req -- HttpServletRequest containing user input.
    |      resp -- HttpServletResponse that will be sent back to the
    |              visit.jsp file.
    |
    |  Returns:  None.
    *-------------------------------------------------------------------*/
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Database db = new Database();
        db.open();

        // Note: In the client we are emulating a put request, by sending a
        // post with a "_method" parameter. Here we redirect to `doPut`
        // to handle a put.
        String method = req.getParameter("_method");
        if (method != null) {
            if (method.equals("put")) {
                this.doPut(req, resp);
                return;
            } else if (method.equals("delete")) {
                this.doDelete(req, resp);
                return;
            }
        }

        // Validate that all parameters are present
        if (req.getParameter("visit#")     == null ||
            req.getParameter("appt#")  == null ||
            req.getParameter("service#")      == null) {
                // Missing parameter, return bad request
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
        }

        String vno = req.getParameter("visit#");
        String ano = req.getParameter("appt#");
        String sno = req.getParameter("service#");

        // Insert row into table
        String query = String.format("INSERT INTO cdosborn.visit VALUES (%s, %s, %s)",
                vno, ano, sno);

        try {
            db.executeUpdate(query);
        } catch (SQLException exc) {
            // Database unique constraint violation
            if (exc.getErrorCode() == 1) {
                req.setAttribute("redirect_url", "/visit.jsp");
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

    /*---------------------------------------------------------------------
    |  Method doGet
    |
    |  Purpose:  Selects all the current visits in the database.
    |
    |  Pre-condition:  None.
    |
    |  Post-condition: None.
    |
    |  Parameters:
    |      req -- HttpServletRequest containing tuple info.
    |      resp -- HttpServletResponse that will be sent back to the
    |              visit.jsp file.
    |
    |  Returns:  Returns the query results to the web application.
    *-------------------------------------------------------------------*/
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("doGET!!!");
        Database db = new Database();
        db.open();

        String query = "SELECT visit#, a.appt#, p.patient#, fname AS \"first name\", lname AS \"last name\", " +
            "name AS service, time AS \"date and time\" " +
            "FROM cdosborn.visit v, cdosborn.appt a, cdosborn.service s, cdosborn.patient p " +
            "WHERE v.appt#=a.appt# AND v.service#=s.service# AND a.patient#=p.patient#";
        List<List<String>> data = new ArrayList<>();
        List<String> cols = Arrays.asList(new String[] {
            "visit#",
            "appt#",
            "patient#",
            "first name",
            "last name",
            "service",
            "date and time"
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
        req.getRequestDispatcher("/WEB-INF/view/visit/visit.jsp").forward(req, resp);
    } // end doGet
}
