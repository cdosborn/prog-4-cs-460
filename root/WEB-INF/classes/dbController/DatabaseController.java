package dbController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.ResultSet;
import java.util.*;
import json.JSON;

/**
 * Servlet implementation class for Servlet: DatabaseController
 *
 */
public class DatabaseController {
  static final long serialVersionUID = 1L;
  /**
   * A handle to the connection to the DBMS.
   */
  protected Connection connection_;
  /**
   * A handle to the statement.
   */
  protected Statement statement_;
  /**
   * The connect string to specify the location of DBMS
   */
  protected String connect_string_ = null;
  /**
   * The password that is used to connect to the DBMS.
   */
  protected String password = null;
  /**
   * The username that is used to connect to the DBMS.
   */
  protected String username = null;


  public DatabaseController() {
    username = "cdosborn";
    password = "a1211";
    connect_string_ = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
  }


  /**
   * Closes the DBMS connection that was opened by the open call.
   */
  public void close() {
    try {
      statement_.close();
      connection_.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    connection_ = null;
  }


  public void Open() {
      try {
          Class.forName("oracle.jdbc.OracleDriver");
          connection_ = DriverManager.getConnection(connect_string_, username, password);
          statement_ = connection_.createStatement();
          return;
      } catch (SQLException sqlex) {
          sqlex.printStackTrace();
      } catch (ClassNotFoundException e) {
          e.printStackTrace();
          System.exit(1); //programemer/dbsm error
      } catch (Exception ex) {
          ex.printStackTrace();
          System.exit(2);
      }
  }

  public JSON getPatients() {
    JSON result = JSON.list();
    JSON patient;
    String sql_query = "SELECT * FROM cdosborn.patient";
    try {
      ResultSet rs  = statement_.executeQuery(sql_query);
      while (rs.next()) {
          patient = JSON.object();
          patient.insert("patient#", rs.getInt("patient#"));
          patient.insert("fname",    rs.getString("fname"));
          patient.insert("lname",    rs.getString("lname"));
          patient.insert("dob",      rs.getString("dob"));
          result.insert(patient);
      }
    } catch (SQLException sqlex) {
      sqlex.printStackTrace();
    }
    return result;
  }
}
