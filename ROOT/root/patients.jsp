<%@page import="dbController.DatabaseController, model.Patient" %>
<%
  DatabaseController controller = new DatabaseController();
  String response = controller.respond(request, new Patient());
  out.write(response);

//   if (request.getMethod() == "POST") {
    
//   } else {
//       // Default to returning a response for a GET
//       JSON json = Patient.getPatients();
//       out.write(json.toString());
//   }
%>
