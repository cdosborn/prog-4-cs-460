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
        // Return json by default
        res.setContentType("application/json");

        // Encode response as UTF8
        res.setCharacterEncoding("UTF-8");

        // Open the db connection
        this.open();

        // Delegate to deal with request
        super.service(req, res);

        // Close the db connection
        this.close();
    }

    public ResultSet execute(String query) throws SQLException {
        if (statement_.execute(query)) {
            return statement_.getResultSet();
        }

        // The query did not produce a result set (inserts)
        return null;
    }
    public void executeMany(String... queries) throws SQLException {
        try {
            // Begin a transaction
            connection_.setAutoCommit(false);
            for (String query : queries) {
                statement_.execute(query);
            }
        } catch (SQLException exc) {
            // Rollback and re-raise the exception (note finally will be called)
            connection_.rollback();
            throw exc;
        } finally {
            // Always revert to autocommit no matter what exceptions are
            // thrown
            connection_.setAutoCommit(true);
        }

        // Only commit if everything went well
        connection_.commit();
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
