/*+----------------------------------------------------------------------
 ||
 ||  Class Appt
 ||
 ||         Author:  Margarita Norzagaray
 ||
 ||        Purpose:  This class is the controller for the 'appointments'
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

public class Appt extends HttpServlet {

    /*---------------------------------------------------------------------
    |  Method doDelete
    |
    |  Purpose:  Deletes a specific appointment with the information (pk)
    |            given by the user. Calls doGet to show the updated tuples
    |            to the user.
    |
    |  Pre-condition:  Said appointment should exists in the database.
    |
    |  Post-condition: Said appointment will be deleted from the database.
    |
    |  Parameters:
    |      req -- HttpServletRequest containing user input.
    |      resp -- HttpServletResponse that will be sent back to the
    |              appt.jsp file.
    |
    |  Returns:  None.
    *-------------------------------------------------------------------*/
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Database db = new Database();
        db.open();

        // Yield the primary key from the paramaters
        String ano = req.getParameter("appt#");

        // Validate that the pk is not null
        if (ano == null) {
            // Missing parameter, return bad request
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Validate that the pk is a number
        try {
            Integer.parseInt(ano);
        } catch (NumberFormatException e) {
            // Paramater is not an integer, return bad request
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Delete the visit associated with this appointment
        String visitQuery = String.format(
            "DELETE FROM cdosborn.visit WHERE appt#=%s", ano);

        // Delete the appointment
        String apptQuery = String.format(
            "DELETE FROM cdosborn.appt WHERE appt#=%s", ano);

        // Delete the patient
        try {
            db.executeMany(visitQuery, apptQuery);
        } catch (SQLException exc) {
            throw new ServletException("Database error!", exc);
        }

        this.doGet(req, resp);
    } // end doDelete

    /*---------------------------------------------------------------------
    |  Method doPut
    |
    |  Purpose:  Updates a specific appointment with the information
    |            given by the user. Calls doGet to show the updated tuples
    |            to the user.
    |
    |  Pre-condition:  Said appointment should exists in the database.
    |
    |  Post-condition: Said appointment will be updated with the user input.
    |
    |  Parameters:
    |      req -- HttpServletRequest containing user input.
    |      resp -- HttpServletResponse that will be sent back to the
    |              appt.jsp file.
    |
    |  Returns:  None.
    *-------------------------------------------------------------------*/
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Database db = new Database();
        db.open();

        // Validate that all parameters are present
        if (req.getParameter("appt#")     == null ||
            req.getParameter("patient#")  == null ||
            req.getParameter("date")      == null ||
            req.getParameter("time")      == null) {
                // Missing parameter, return bad request
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
        }

        String ano = req.getParameter("appt#");
        String pno = req.getParameter("patient#");
        String date = req.getParameter("date");
        String time = req.getParameter("time");

        // Define SQL Update's where clause
        String whereClause = String.format("WHERE %s=%s",
               "appt#", ano);

        // Define the pairs of column and values to be updated
        String updatePairs = String.format("%s=%s, %s=TO_DATE('%s %s', 'YYYY/MM/DD HH24:MI:SS')",
               "patient#", pno,
               "time", date, time);

        // Define the entire update query
        String query = String.format("UPDATE cdosborn.appt SET %s %s",
                updatePairs,
                whereClause);

        System.out.println(query);

        // The number of updated rows
        int updated;

        // Update tuple
        try {
            updated = db.executeUpdate(query);
            if (updated == 0) {
                req.setAttribute("redirect_url", "/appt.jsp");
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
    |  Purpose:  Inserts a new appointment with the information
    |            given by the user. Calls doGet to show the updated tuples
    |            to the user.
    |
    |  Pre-condition:  None.
    |
    |  Post-condition: The new appointment will be added to the database.
    |
    |  Parameters:
    |      req -- HttpServletRequest containing user input.
    |      resp -- HttpServletResponse that will be sent back to the
    |              appt.jsp file.
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
        if (req.getParameter("appt#")     == null ||
            req.getParameter("patient#")  == null ||
            req.getParameter("date")      == null ||
            req.getParameter("time")      == null) {
                // Missing parameter, return bad request
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
        }

        String ano = req.getParameter("appt#");
        String pno = req.getParameter("patient#");
        String date = req.getParameter("date");
        String time = req.getParameter("time");

        // Insert row into table
        String query = String.format("INSERT INTO cdosborn.appt VALUES (%s, %s, TO_DATE('%s %s', 'YYYY/MM/DD HH24:MI:SS'))",
                ano, pno, date, time);

        try {
            db.executeUpdate(query);
        } catch (SQLException exc) {
            // Database unique constraint violation
            if (exc.getErrorCode() == 1) {
                req.setAttribute("redirect_url", "/appt.jsp");
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
    |  Purpose:  Selects all the current appointments in the database.
    |
    |  Pre-condition:  None.
    |
    |  Post-condition: None.
    |
    |  Parameters:
    |      req -- HttpServletRequest containing tuple info.
    |      resp -- HttpServletResponse that will be sent back to the
    |              appt.jsp file.
    |
    |  Returns:  Returns the query results to the web application.
    *-------------------------------------------------------------------*/
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("doGET!!!");
        Database db = new Database();
        db.open();

        String query = "SELECT * FROM cdosborn.appt";
        List<List<String>> data = new ArrayList<>();
        List<String> cols = Arrays.asList(new String[] {
            "appt#",
            "patient#",
            "time"
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
        req.getRequestDispatcher("/WEB-INF/view/appt/appt.jsp").forward(req, resp);
    } // end doGet
}
