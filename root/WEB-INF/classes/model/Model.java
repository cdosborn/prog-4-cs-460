package model;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;


public class Model extends HttpServlet {
    /**
     * A handle to the connection to the DBMS.
     */
    private Connection connection_;
    /**
     * A handle to the statement.
     */
    private Statement statement_;

    public void service(HttpServletRequest req,
            HttpServletResponse res) throws ServletException, IOException
    {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        // Open the db connection
        this.open();

        super.service(req, res);

        // Close the db connection
        this.close();
        // switch (request.getMethod()) {
        //     case "POST":
        //         this.create(req, resp);
        //         break;
        //     case "PUT":
        //         this.update(req, resp);
        //         break;
        //     case "GET":
        //         this.all(req, resp);
        //         break;
        //     case "DELETE":
        //         this.delete(req, resp);
        //         break;
        //     default:
        //         res.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
        // }
    }

    public ResultSet execute(String query) throws SQLException {
        return statement_.executeQuery(query);
    }

    private void open() {
        String username = "cdosborn"; 
        String password = "a1211";
        String connect_string_ = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
	try {
	    Class.forName("oracle.jdbc.OracleDriver");
	    connection_ = DriverManager.getConnection(connect_string_, username, password);
	    statement_ = connection_.createStatement();
	    return;
	} catch (SQLException sqlex) {
	    sqlex.printStackTrace();
	    System.exit(1); //programemer/dbsm error
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	    System.exit(1); //programemer/dbsm error
	} catch (Exception ex) {
	    ex.printStackTrace();
	    System.exit(2);
	}
    }

    /**
     * Closes the DBMS connection that was opened by the open call.
     */
    private void close() {
        try {
            statement_.close();
            connection_.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection_ = null;
    }
}
