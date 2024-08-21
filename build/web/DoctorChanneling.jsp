
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Doctor Channeling Schedules</title>
    <link href="CSS/Dchan.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div class="container">
        <h2>Doctor Channeling Schedules</h2>
        
        <!-- Form to add or update a channeling schedule -->
        <form action="ChannelingServlet" method="post">
            <input type="hidden" name="action" value="addOrUpdate">
            <label for="channelingDate">Channeling Date:</label>
            <input type="date" id="channelingDate" name="channelingDate" required>
            
            <label for="time">Time:</label>
            <input type="time" id="time" name="time" required>
            
            <label for="noOfPatients">No. of Patients:</label>
            <input type="number" id="noOfPatients" name="noOfPatients" required>
            
            <input type="submit" value="Add/Update Schedule">
            <button onclick="location.href='DoctorProfile.jsp'">Edit Profile</button>
        </form>
        
        <!-- Display existing channeling schedules -->
        <h3>Your Schedules</h3>
        <table>
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Time</th>
                    <th>No. of Patients</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                
                <%
                    String doctorID = (String) session.getAttribute("doctorID");
                    if (doctorID != null) {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "", "");
                        PreparedStatement pst = con.prepareStatement("SELECT * FROM channeling WHERE doctorID = ?");
                        pst.setString(1, doctorID);
                        ResultSet rs = pst.executeQuery();
                        
                        while (rs.next()) {
                            out.println("<tr>");
                            out.println("<td>" + rs.getDate("channelingDate") + "</td>");
                            out.println("<td>" + rs.getTime("time") + "</td>");
                            out.println("<td>" + rs.getInt("noOfPatients") + "</td>");
                            out.println("<td>");
                            out.println("<form action='ChannelingServlet' method='post' style='display:inline;'>");
                            out.println("<input type='hidden' name='action' value='delete'>");
                            out.println("<input type='hidden' name='channelingDate' value='" + rs.getDate("channelingDate") + "'>");
                            out.println("<input type='submit' value='Delete'>");
                            out.println("</form>");
                            out.println("</td>");
                            out.println("</tr>");
                        }
                        con.close();
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
