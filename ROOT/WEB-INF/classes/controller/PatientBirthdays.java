package controller;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class PatientBirthdays extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Database db = new Database();
        db.open();

        List<List<String>> data = new ArrayList<>();
        List<String> cols = Arrays.asList(new String[] {
            "patient#",
            "fname",
            "lname",
            "dob",
        });

        // Query patients with birthdays this month
        String query =
            " SELECT *" + 
            " FROM patient" + 
            " WHERE to_char(dob, 'MONTH') = to_char(SYSDATE, 'MONTH')";

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
        req.getRequestDispatcher("/WEB-INF/view/patient-birthdays/patient-birthdays.jsp").forward(req, resp);
    }
}
