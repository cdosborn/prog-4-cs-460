package model;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

import json.JSON;

public class Patient extends Model {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        JSON result = JSON.list();
        JSON patient;
        String query = "SELECT * FROM cdosborn.patient";
        try {
            ResultSet rs  = this.execute(query);
            while (rs.next()) {
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
    // public JSON update(String pk) {}
    // public JSON delete(String pk) {}
    // public JSON create(Map<String, String> params) {}
    // public JSON all() {
    //     JSON result = JSON.list();
    //     JSON patient;
    //     String sql_query = "SELECT * FROM cdosborn.patient";
    //     try {
    //         ResultSet rs  = statement_.executeQuery(sql_query);
    //         while (rs.next()) {
    //             patient = JSON.object();
    //             patient.insert("patient#", rs.getInt("patient#"));
    //             patient.insert("fname",    rs.getString("fname"));
    //             patient.insert("lname",    rs.getString("lname"));
    //             patient.insert("dob",      rs.getString("dob"));
    //             result.insert(patient);
    //         }
    //     } catch (SQLException sqlex) {
    //         sqlex.printStackTrace();
    //     }
    //     return result;
    // }
}
