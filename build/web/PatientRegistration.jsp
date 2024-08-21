
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Patient Registration</title>
        <link href="CSS/Preg.css" rel="stylesheet" type="text/css"/>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h2>Patient Registration Form</h2>
    <form action="PatientRegistration" method="post">
        <label for="NIC">NIC:</label>
        <input type="text" id="NIC" name="NIC" required><br><br>
        
        <label for="name">Name:</label>
        <input type="text" id="Pname" name="Pname" required><br><br>
        
        <label for="phone">Phone:</label>
        <input type="text" id="phone" name="phone" required><br><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <input type="submit" value="Register">
    </form>
    </body>
</html>
