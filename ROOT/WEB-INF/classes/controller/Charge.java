/*+----------------------------------------------------------------------
 ||
 ||  Class Charge
 ||
 ||         Author:  Connor Osborn
 ||
 ||        Purpose:  This class is the controller for the 'charges'  
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
 ||  Class Methods:  doGet(HttpServletRequest req, HttpServletResponse resp) 
 ||                  Returns a HttpServletResponse to charge.jsp.
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

public class Charge extends HttpServlet {
    
    /*---------------------------------------------------------------------
    |  Method doGet
    |
    |  Purpose:  Selects all 'charges' in the database. 
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
        Database db = new Database();
        db.open();

        String query = "SELECT service# FROM cdosborn.service";
        String subquery;
        String serviceno;

        // A list of tables, each table is a list of rows, each row a list of
        // fields
        List<List<List<String>>> tables = new ArrayList<>();
        List<List<String>> table;
        List<String> row;

        // The columns of each charge table (each charge table is a list of
        // charges for a service)
        List<String> cols = Arrays.asList(new String[] {
            "type",
            "name",
            "fee",
            "quantity",
            "total"
        });

        try {
            ResultSet serviceResults = db.execute(query);
            ResultSet chargeResults;
            while (serviceResults.next()) {
                table = new ArrayList<>();
                serviceno = serviceResults.getString("service#");
                subquery = String.format(
                    " SELECT 'service' type, service.name, service.cost fee, 1 quantity, service.cost TOTAL" +
                    " FROM service" +
                    " WHERE service.service#=%1$s" +
                    " UNION" +
                    " SELECT 'supply' type, supply.name, supply.cost fee, servicesupply.quantity, supply.cost * servicesupply.quantity TOTAL" +
                    " FROM service," +
                    "      servicesupply," +
                    "      supply" +
                    " WHERE service.service#=%1$s" +
                    "   AND service.service#=servicesupply.service#" +
                    "   AND supply.supply#=servicesupply.supply#", serviceno);

                try {
                    chargeResults = db.execute(subquery);
                    while (chargeResults.next()) {
                        row = new ArrayList<>();
                        for (String col : cols) {
                            row.add(chargeResults.getString(col));
                        }
                        table.add(row);
                    }
                } catch (SQLException exc) {
                    throw new ServletException("Database error!", exc);
                }
                tables.add(table);
            }
        } catch (SQLException exc) {
            throw new ServletException("Database error!", exc);
        } finally {
            db.close();
        }

        req.setAttribute("cols", cols);
        req.setAttribute("tables", tables);
        req.setAttribute("numrows", tables.size());
        req.getRequestDispatcher("/WEB-INF/view/charge/charge.jsp").forward(req, resp);
    }
}
