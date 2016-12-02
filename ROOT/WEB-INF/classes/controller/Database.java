package controller;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;


public class Database {
    /**
     * A handle to the connection to the DBMS.
     */
    private Connection connection_;
    /**
     * A handle to the statement.
     */
    private List<Statement> statements;

    public int executeUpdate(String query) throws SQLException {
        Statement stmt = this.createStatement();

        // Return the number of affected rows
        return stmt.executeUpdate(query);
    }

    public ResultSet execute(String query) throws SQLException {
        Statement stmt = this.createStatement();

        return stmt.executeQuery(query);
    }

    private Statement createStatement() throws SQLException {
        Statement stmt = connection_.createStatement();
        statements.add(stmt);
        return stmt;
    }

    public void executeMany(String... queries) throws SQLException {
        Statement stmt = this.createStatement();
        try {
            // Begin a transaction
            connection_.setAutoCommit(false);
            for (String query : queries) {
                stmt.execute(query);
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

    public void open() {
        String username = "cdosborn";
        String password = "a1211";
        String connect_string_ = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
	try {
	    Class.forName("oracle.jdbc.OracleDriver");
	    connection_ = DriverManager.getConnection(connect_string_, username, password);
            statements = new ArrayList<>();
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
    public void close() {
        try {
            // Try closing each open statement
            try {
                for (Statement s: statements) {
                    s.close();
                }

            // No matter what, close the connection
            } finally {
                connection_.close();
            }

        // Catch any raised exceptions from statement/connection closing
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
