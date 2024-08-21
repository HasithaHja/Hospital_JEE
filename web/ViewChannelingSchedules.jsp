<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Channeling Schedules</title>
    <link href="CSS/Channelingschedule.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div class="container">
        <h2>Available Channeling Schedules</h2>
        <button onclick="location.href='PatientProfile.jsp'">Edit Profile</button>
        <%
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "", "");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM channeling_schedules WHERE current_patients < max_patients");

                while (rs.next()) {
        %>
                    <form action="MakeAppointmentServlet" method="post">
                        <input type="hidden" name="scheduleID" value="<%= rs.getInt("scheduleID") %>">
                        <p>Doctor ID: <%= rs.getString("doctorID") %></p>
                        <p>Date: <%= rs.getString("channelingDate") %></p>
                        <p>Time: <%= rs.getString("time") %></p>
                        <p>Available Slots: <%= rs.getInt("max_patients") - rs.getInt("current_patients") %></p>
                        <input type="submit" value="Make Appointment">
                    </form>
                    <hr>
        <%
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>
    </div>
</body>
</html>
