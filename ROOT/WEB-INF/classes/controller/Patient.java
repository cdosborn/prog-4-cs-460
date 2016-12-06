/*+----------------------------------------------------------------------
 ||
 ||  Class Patient 
 ||
 ||         Author:  Connor Osborn
 ||
 ||        Purpose:  Controllers class for the 'patient' view. 
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

public class Patient extends HttpServlet {
    
    /*---------------------------------------------------------------------
    |  Method doDelete
    |
    |  Purpose:  Deletes a specific patient with the information (pk) 
    |            given by the user. Also deletes appointments and visits 
    |            associated with the patient. Calls doGet to show the 
    |            updated tuples to the user. 
    |
    |  Pre-condition:  Said patient should exists in the database.
    |
    |  Post-condition:  Said patient will be deleted from the database. 
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


        // Delete all relevant patient visits
        String visitQuery = String.format(
            " DELETE FROM cdosborn.visit v" +
            " WHERE EXISTS(" +
            " SELECT * FROM  cdosborn.appt a, cdosborn.patient p" +
            " WHERE v.appt#=a.appt# " +
            "   AND a.patient#=p.patient#" +
            "   AND p.patient#=%s)", pno);

        // Delete all relevant patient appts
        String apptQuery = String.format(
            " DELETE FROM cdosborn.appt a" +
            " WHERE EXISTS(" +
            " SELECT * FROM cdosborn.patient p" +
            " WHERE a.patient#=p.patient#" +
            "   AND p.patient#=%s)", pno);

        String patientQuery = String.format(
            "DELETE FROM cdosborn.patient p WHERE p.patient#=%s", pno);

        // Delete the patient
        try {
            db.executeMany(visitQuery, apptQuery, patientQuery);
        } catch (SQLException exc) {
            throw new ServletException("Database error!", exc);
        }

        this.doGet(req, resp);
    }

    /*---------------------------------------------------------------------
    |  Method doPut
    |
    |  Purpose:  Updates a specific patient with the information
    |            given by the user. Calls doGet to show the updated tuples
    |            to the user. 
    |
    |  Pre-condition:  Said patient should exists in the database.
    |
    |  Post-condition: Said patient will be updated with the user input. 
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
        if (req.getParameter("patient#") == null ||
            req.getParameter("fname")    == null ||
            req.getParameter("lname")    == null ||
            req.getParameter("dob")      == null) {
                // Missing parameter, return bad request
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
        }

        String pno = req.getParameter("patient#");
        String fname = req.getParameter("fname");
        String lname = req.getParameter("lname");
        String dob = req.getParameter("dob");

        // Define SQL Update's where clause
        String whereClause = String.format("WHERE %s=%s",
               "patient#", pno);

        // Define the pairs of column and values to be updated
        String updatePairs = String.format("%s=%s,%s='%s',%s='%s',%s=DATE '%s'",
               "patient#", pno,
               "fname", fname,
               "lname", lname,
               "dob", dob);

        // Define the entire update query
        String query = String.format("UPDATE cdosborn.patient SET %s %s",
                updatePairs,
                whereClause);

        System.out.println(query);

        // The number of updated rows
        int updated;

        // Update tuple
        try {
            updated = db.executeUpdate(query);
            if (updated == 0) {
                req.setAttribute("redirect_url", "/patient.jsp");
                req.getRequestDispatcher("/WEB-INF/view/error/no_record.jsp").forward(req, resp);
                return;
            }
        } catch (SQLException exc) {
            throw new ServletException("Database error!", exc);
        } finally {
            db.close();
        }

        this.doGet(req, resp);
    }

    /*---------------------------------------------------------------------
    |  Method doPost
    |
    |  Purpose:  Inserts a new patient with the information 
    |            given by the user. Calls doGet to show the updated tuples
    |            to the user. 
    |
    |  Pre-condition:  None. 
    |
    |  Post-condition: The new patient will be added to the database.  
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
        if (req.getParameter("patient#") == null ||
            req.getParameter("fname")    == null ||
            req.getParameter("lname")    == null ||
            req.getParameter("dob")      == null) {
                // Missing parameter, return bad request
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
        }

        String pno = req.getParameter("patient#");
        String fname = req.getParameter("fname");
        String lname = req.getParameter("lname");
        String dob = req.getParameter("dob");

        // Insert row into table
        String query = String.format("INSERT INTO cdosborn.patient VALUES (%s, '%s', '%s', DATE '%s')",
                pno, fname, lname, dob);

        try {
            db.executeUpdate(query);
        } catch (SQLException exc) {
            // Database unique constraint violation
            if (exc.getErrorCode() == 1) {
                req.setAttribute("redirect_url", "/patient.jsp");
                req.getRequestDispatcher("/WEB-INF/view/error/duplicate_key_error.jsp").forward(req, resp);
                return;
            } else {
                throw new ServletException("Database error!", exc);
            }
        } finally {
            db.close();
        }

        this.doGet(req, resp);
    }

    /*---------------------------------------------------------------------
    |  Method doGet
    |
    |  Purpose:  Selects all the current patients in the database. 
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
        String query = "SELECT * FROM cdosborn.patient";
        List<List<String>> data = new ArrayList<>();
        List<String> cols = Arrays.asList(new String[] {
            "patient#",
            "fname",
            "lname",
            "dob"
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
        req.getRequestDispatcher("/WEB-INF/view/patient/patient.jsp").forward(req, resp);
    }
}
