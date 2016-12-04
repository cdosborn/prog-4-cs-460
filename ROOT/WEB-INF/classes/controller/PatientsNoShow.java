package controller;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class PatientsNoShow extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Database db = new Database();
        db.open();

        List<List<String>> data = new ArrayList<>();
        List<String> cols = Arrays.asList(new String[] {
            "appt#",
            "name",
            "time",
        });

        // Query for table of transactions
        String query =
            " WITH apptsnovisits as (" +
            "     SELECT * FROM appt WHERE appt.time < SYSDATE" +
            "     MINUS" +
            "     SELECT appt.* FROM visit, appt WHERE visit.appt#=appt.appt#)" +
            " SELECT apptsnovisits.appt#, fname || ' ' || lname name, time" +
            " FROM" +
            "     apptsnovisits" +
            "     INNER JOIN" +
            "     patient on apptsnovisits.patient# = patient.patient#";


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
        req.getRequestDispatcher("/WEB-INF/view/patients-no-show/patients-no-show.jsp").forward(req, resp);
    }
}
