import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/ChannelingServlet")
public class ChannelingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String action = request.getParameter("action");
        String doctorID = (String) request.getSession().getAttribute("doctorID");
        RequestDispatcher dispatcher = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "", "");
            
            if ("addOrUpdate".equals(action)) {
                // Add or update a channeling schedule
                String channelingDate = request.getParameter("channelingDate");
                String time = request.getParameter("time");
                int noOfPatients = Integer.parseInt(request.getParameter("noOfPatients"));

                // Check if a schedule already exists for the given date
                PreparedStatement checkStmt = con.prepareStatement("SELECT * FROM channeling WHERE doctorID = ? AND channelingDate = ?");
                checkStmt.setString(1, doctorID);
                checkStmt.setString(2, channelingDate);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // Update existing schedule
                    PreparedStatement updateStmt = con.prepareStatement("UPDATE channeling SET time = ?, noOfPatients = ? WHERE doctorID = ? AND channelingDate = ?");
                    updateStmt.setString(1, time);
                    updateStmt.setInt(2, noOfPatients);
                    updateStmt.setString(3, doctorID);
                    updateStmt.setString(4, channelingDate);
                    updateStmt.executeUpdate();
                } else {
                    // Insert new schedule
                    PreparedStatement insertStmt = con.prepareStatement("INSERT INTO channeling (doctorID, channelingDate, time, noOfPatients) VALUES (?, ?, ?, ?)");
                    insertStmt.setString(1, doctorID);
                    insertStmt.setString(2, channelingDate);
                    insertStmt.setString(3, time);
                    insertStmt.setInt(4, noOfPatients);
                    insertStmt.executeUpdate();
                }
            } else if ("delete".equals(action)) {
                // Delete a channeling schedule
                String channelingDate = request.getParameter("channelingDate");
                PreparedStatement deleteStmt = con.prepareStatement("DELETE FROM channeling WHERE doctorID = ? AND channelingDate = ?");
                deleteStmt.setString(1, doctorID);
                deleteStmt.setString(2, channelingDate);
                deleteStmt.executeUpdate();
            }
            
            dispatcher = request.getRequestDispatcher("DoctorChanneling.jsp");
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
