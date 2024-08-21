import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/UpdateDoctorProfile")
public class UpdateDoctorProfile extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Retrieve form data
        String doctorID = request.getParameter("doctorID");
        String Dname = request.getParameter("Dname");
        String Dphone = request.getParameter("Dphone");
        String specialization = request.getParameter("specialization");
        
        RequestDispatcher dispatcher = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "", "");
            PreparedStatement pst = con.prepareStatement("UPDATE doctors SET name = ?, phone = ?, specialization = ? WHERE userID = ?");
            
            pst.setString(1, Dname);
            pst.setString(2, Dphone);
            pst.setString(3, specialization);
            pst.setString(4, doctorID);
            
            int rowCount = pst.executeUpdate();
            
            if (rowCount > 0) {
                request.setAttribute("status", "Profile updated successfully!");
            } else {
                request.setAttribute("status", "Profile update failed. Please try again.");
            }
            
            // Fetch the updated profile details
            PreparedStatement fetchProfile = con.prepareStatement("SELECT * FROM doctors WHERE userID = ?");
            fetchProfile.setString(1, doctorID);
            ResultSet rs = fetchProfile.executeQuery();
            if (rs.next()) {
                request.setAttribute("Dname", rs.getString("name"));
                request.setAttribute("Dphone", rs.getString("phone"));
                request.setAttribute("specialization", rs.getString("specialization"));
            }
            
            dispatcher = request.getRequestDispatcher("DoctorProfile.jsp");
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
