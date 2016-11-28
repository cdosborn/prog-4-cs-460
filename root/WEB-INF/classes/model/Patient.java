package model;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;

import json.JSON;

public class Patient extends Model {
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // The json for reporting errors
        JSON error = JSON.object();

        // Validate that the pk is passed
        if (req.getParameter("patient#") == null) {
                // Missing parameter, return bad request
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                error.insert("error", "Missing required parameters");
                resp.getWriter().write(error.toString());
                return;
        }

        String pno = req.getParameter("patient#");

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

        // Delete the patient
        String patientQuery = String.format(
            "DELETE FROM cdosborn.patient p WHERE p.patient#=%s", pno);

        try {
            this.executeMany(visitQuery, apptQuery, patientQuery);
        } catch (SQLException exc) {
            throw new ServletException("Database error!", exc);
        }
    }

    public void performPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // The json for the updated tuple
        JSON result = JSON.object();

        // The json for reporting errors
        JSON error = JSON.object();

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

        // Update tuple
        try {
            this.execute(query);
        } catch (SQLException exc) {
            throw new ServletException("Database error!", exc);
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // The json for the updated tuple
        JSON result = JSON.object();

        // The json for reporting errors
        JSON error = JSON.object();

        // Validate that all parameters are present
        if (req.getParameter("patient#") == null ||
            req.getParameter("fname")    == null ||
            req.getParameter("lname")    == null ||
            req.getParameter("dob")      == null) {
                // Missing parameter, return bad request
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                error.insert("error", "Missing required parameters");
                resp.getWriter().write(error.toString());
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
            this.execute(query);
        } catch (SQLException exc) {

            // Perform a PUT if we get a duplicate entry error
            if (exc.getErrorCode() == 1) {
                this.performPut(req, resp);
            } else {
                throw new ServletException("Database error!", exc);
            }
        }

        // Fetch updated model
        query = String.format("SELECT * FROM cdosborn.patient WHERE patient#=%s", pno);
        try {
            ResultSet rs  = this.execute(query);
            if (rs != null && rs.next()) {
                result.insert("patient#", rs.getInt("patient#"));
                result.insert("fname",    rs.getString("fname"));
                result.insert("lname",    rs.getString("lname"));
                result.insert("dob",      rs.getString("dob"));
            } else {
                throw new ServletException("Database error! Expected at least one tuple.");
            }
        } catch (SQLException exc) {
            throw new ServletException("Database error!", exc);
        }

        // Write response
        resp.getWriter().write(result.toString());
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        JSON result = JSON.list();
        JSON patient;
        String query = "SELECT * FROM cdosborn.patient";
        try {
            ResultSet rs  = this.execute(query);
            while (rs != null && rs.next()) {
                patient = JSON.object();
                patient.insert("patient#", rs.getInt("patient#"));
                patient.insert("fname",    rs.getString("fname"));
                patient.insert("lname",    rs.getString("lname"));
                patient.insert("dob",      rs.getString("dob"));
                result.insert(patient);
            }
        } catch (SQLException exc) {
            throw new ServletException("Database error!", exc);
        }
        resp.getWriter().write(result.toString());
    }
    public void service(HttpServletRequest req,
            HttpServletResponse res) throws ServletException, IOException
    {
        super.service(req,res);
    }
}
