<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Patient Profile</title>
    <link href="CSS/Pprof.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div class="container">
        <h2>Patient Profile</h2>
        <%
            String NIC = (String) session.getAttribute("NIC");
            if (NIC != null) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "", "");
                    PreparedStatement pst = con.prepareStatement("SELECT * FROM patients WHERE NIC = ?");
                    pst.setString(1, NIC);
                    ResultSet rs = pst.executeQuery();
                    
                    if (rs.next()) {
                        out.println("<form action='PatientProfileServlet' method='post'>");
                        out.println("<label for='Pname'>Name:</label>");
                        out.println("<input type='text' id='Pname' name='Pname' value='" + rs.getString("name") + "' required><br><br>");
                        
                        out.println("<label for='phone'>Phone:</label>");
                        out.println("<input type='text' id='phone' name='phone' value='" + rs.getString("phone") + "' required><br><br>");
                        
                        out.println("<label for='password'>Password:</label>");
                        out.println("<input type='password' id='password' name='password' value='" + rs.getString("password") + "' required><br><br>");
                        
                        out.println("<input type='submit' value='Update Profile'>");
                        out.println("</form>");
                        out.println("<button onclick=\"location.href='ViewChannelingSchedules.jsp'\">Go Back</button>");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        %>
        <%
            String status = (String) request.getAttribute("status");
            if (status != null) {
                out.println("<p>" + status + "</p>");
            }
        %>
    </div>
</body>
</html>
