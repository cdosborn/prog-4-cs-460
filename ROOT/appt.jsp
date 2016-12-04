<%@page import="dbController.DatabaseController, model.Appt" %>
<%
  DatabaseController controller = new DatabaseController();
  String response = controller.respond(request, new Appt());
  out.write(response);
%>
