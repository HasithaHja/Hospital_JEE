import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/MakeAppointmentServlet")
public class MakeAppointmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String patientNIC = (String) request.getSession().getAttribute("patientNIC");
        int scheduleID = Integer.parseInt(request.getParameter("scheduleID"));

        RequestDispatcher dispatcher = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "Hh53447522#");

            // Check if the patient already has an appointment for the same schedule
            PreparedStatement checkStmt = con.prepareStatement("SELECT * FROM appointments WHERE NIC = ? AND scheduleID = ?");
            checkStmt.setString(1, patientNIC);
            checkStmt.setInt(2, scheduleID);
            ResultSet checkRs = checkStmt.executeQuery();
            if (checkRs.next()) {
                request.setAttribute("status", "already-booked");
                dispatcher = request.getRequestDispatcher("ViewChannelingSchedules.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Insert new appointment
            PreparedStatement pst = con.prepareStatement("INSERT INTO appointments (NIC, scheduleID) VALUES (?, ?)");
            pst.setString(1, patientNIC);
            pst.setInt(2, scheduleID);
            int rowCount = pst.executeUpdate();

            // Update the current patients count
            if (rowCount > 0) {
                PreparedStatement updateStmt = con.prepareStatement("UPDATE channeling_schedules SET current_patients = current_patients + 1 WHERE scheduleID = ?");
                updateStmt.setInt(1, scheduleID);
                updateStmt.executeUpdate();

                request.setAttribute("status", "appointment-success");
            } else {
                request.setAttribute("status", "appointment-failed");
            }
            dispatcher = request.getRequestDispatcher("ViewChannelingSchedules.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
