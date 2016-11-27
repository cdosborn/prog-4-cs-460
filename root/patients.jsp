<%@page import="json.JSON, dbController.DatabaseController" %>
<%@page contentType="application/json; charset=UTF-8"%>
<%
  DatabaseController db = new DatabaseController();
  db.Open();
  JSON json = db.getPatients();
  out.write(json.toString());
%>
