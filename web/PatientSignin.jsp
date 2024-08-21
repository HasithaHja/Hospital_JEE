<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Patient Sign In</title>
    <link href="CSS/Psignin.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div class="container">
        <h2>Sign In</h2>
        <form action="PatientSignin" method="post">
            <label for="NIC">NIC:</label>
            <input type="text" id="NIC" name="NIC" required>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            
            <input type="submit" value="Sign In">
        </form>
        <p>Don't have an account? <a href="PatientRegistration.jsp">Register here</a></p>
        
        <%
            String status = (String) request.getAttribute("status");
            if ("failed".equals(status)) {
                out.println("<p style='color:red;'>Invalid NIC or Password. Please try again.</p>");
            }
        %>
    </div>
</body>
</html>
