<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="java.util.*,java.lang.StringBuffer,
    dbController.DatabaseController" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search Results</title>
</head>
<body>
<div id="searchresult">
<%
  request.setCharacterEncoding("utf-8");
  response.setContentType("text/html;charset=utf-8");

  DatabaseController dbcontroller = new DatabaseController();
  // connect to backend database server via the databasecontroller, which
  // is a wrapper class providing necessary methods for this particular
  // application
  dbcontroller.Open();

  // writing the content on output/response page
  out.write("<h2>All Employees</h2>");

  // stringbuffer to hold final content
  StringBuffer content = new StringBuffer();;
  content.append("<br/><table>");

  // asking dbcontroller to list the employee table
  Vector<String> vecResult = dbcontroller.FindAllEmployees();
   if (vecResult == null) {
     content.append("Query result is null!");
   }
   content.append("<tr><th>EMPNO</th><th>EMPNAME</th><th>EMPSALARY</th>" +
   "<th>DEPARTMENT</th><th>BOSSNO</th></tr>");
  if (vecResult != null && vecResult.size() > 0) {
    for (int i = 0; i < vecResult.size(); i++) {
      String row = vecResult.get(i);
      String[] detail = row.split("##");
      if (detail.length != 5) {
        //break;
      }
      content.append(
          "<tr id=\"tablerow_" + i + "\">");
      content.append(
          "<td class=\"postlist\"><a href=\"javascript:void(0)\" " +
          "\"><b>" + detail[0] + "</b></a></td>");
      content.append(
          "<td><a href=\"javascript:void(0)\" >" +
          "<b>" + detail[1] + "</b></a></td>");
      content.append("<td>" + detail[2] + "</td>" +
                     "<td>" + detail[3] + "</td>" +
                     "<td>" + detail[4] + "</td>");
      content.append("</tr>");
    }
  }
  out.write(content.toString());

  // close the dbcontroller and relase all resources occupied by it.
  dbcontroller.Close();
%>
</div>
</body>
</html>
