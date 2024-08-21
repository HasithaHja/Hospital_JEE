<%--
if (session.getAttribute("name")==null){
    response.sendRedirect("index.jsp");
    }
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hospital Management System</title>
    <link href="CSS/frontpage_css.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <header>
        <h1>Welcome to the Hospital</h1>
    </header>
    <div class="container">
        <div class="button-container">
            <div class="button-wrapper">
                <img src="Images/doctor_icon.png" alt="Doctor Icon" class="icon">
                <button onclick="location.href='DoctorSignin.jsp'">I'm a Doctor</button>
            </div>
            <div class="button-wrapper">
                <img src="Images/patient_icon.png" alt="Patient Icon" class="icon">
                <button onclick="location.href='PatientSignin.jsp'">I'm a Patient</button>
            </div>
        </div>
    </div>
</body>
</html>
