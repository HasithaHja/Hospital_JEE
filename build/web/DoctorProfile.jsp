<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Doctor Profile</title>
    <link href="CSS/Dprof.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div class="container">
        <h2>Doctor Profile</h2>
        <p><%= request.getAttribute("status") != null ? request.getAttribute("status") : "" %></p>
        <form action="UpdateDoctorProfile" method="post">
            <input type="hidden" id="doctorID" name="doctorID" value="<%= session.getAttribute("doctorID") %>" required>
            
            <label for="Dname">Name:</label>
            <input type="text" id="Dname" name="Dname" value="<%= request.getAttribute("Dname") %>" required>
            
            <label for="Dphone">Phone:</label>
            <input type="text" id="Dphone" name="Dphone" value="<%= request.getAttribute("Dphone") %>" required>
            
            <label for="specialization">Specialization:</label>
            <input type="text" id="specialization" name="specialization" value="<%= request.getAttribute("specialization") %>" required>
            
            <input type="submit" value="Update">
        </form>
            
        <form action="DoctorProfile" method="get">
            <input type="submit" value="Back to Profile">
        </form>
    </div>
</body>
</html>
