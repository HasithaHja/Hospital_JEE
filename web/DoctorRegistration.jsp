<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Doctor Registration</title>
        <link href="CSS/Dreg.css" rel="stylesheet" type="text/css"/>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h2>Doctor Registration Form</h2>
        <form action="DoctorRegistration" method="post">
            <label for="doctorID">User ID:</label>
            <input type="text" id="doctorID" name="doctorID" required><br><br>

            <label for="name">Name:</label>
            <input type="text" id="Dname" name="Dname" required><br><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br><br>

            <label for="Dphone">Phone:</label>
            <input type="text" id="Dphone" name="Dphone" required><br><br>

            <label for="specialization">Specialization:</label>
            <input type="text" id="specialization" name="specialization" required><br><br>

            <input type="submit" value="Register">
        </form>

        <!-- Display status messages -->
        <%
            String status = (String) request.getAttribute("status");
            if (status != null) {
                out.println("<p>" + status + "</p>");
            }
        %>
    </body>
</html>
