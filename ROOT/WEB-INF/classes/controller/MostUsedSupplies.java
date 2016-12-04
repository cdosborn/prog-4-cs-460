package controller;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class MostUsedSupplies extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Database db = new Database();
        db.open();

        List<List<String>> data = new ArrayList<>();
        List<String> cols = Arrays.asList(new String[] {
            "supply#",
            "name",
            "total",
        });

        // Query supplies most used in treating patients
        String query =
            " SELECT  supply.supply#, supply.name, SUM(servicesupply.quantity) total" +
            " FROM" +
            "     visit, servicesupply, supply" +
            " WHERE visit.service# = servicesupply.service#" +
            "   AND servicesupply.supply# = supply.supply#" +
            " GROUP BY supply.supply#, supply.name, supply.supply#" +
            " ORDER BY total DESC";

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
        req.getRequestDispatcher("/WEB-INF/view/most-used-supplies/most-used-supplies.jsp").forward(req, resp);
    }
}
