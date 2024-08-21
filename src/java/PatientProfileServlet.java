import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/PatientProfileServlet")
public class PatientProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String NIC = (String) request.getSession().getAttribute("NIC");
        String Pname = request.getParameter("Pname");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        
        RequestDispatcher dispatcher = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "", "");
            PreparedStatement pst = con.prepareStatement("UPDATE patients SET name = ?, phone = ?, password = ? WHERE NIC = ?");
            
            pst.setString(1, Pname);
            pst.setString(2, phone);
            pst.setString(3, password);
            pst.setString(4, NIC);

            int rowCount = pst.executeUpdate();
            
            if (rowCount > 0) {
                request.setAttribute("status", "Profile updated successfully!");
            } else {
                request.setAttribute("status", "Profile update failed. Please try again.");
            }
            
            // Fetch the updated profile details
            PreparedStatement fetchProfile = con.prepareStatement("SELECT * FROM patients WHERE NIC = ?");
            fetchProfile.setString(1, NIC);
            ResultSet rs = fetchProfile.executeQuery();
            if (rs.next()) {
                request.setAttribute("Pname", rs.getString("name"));
                request.setAttribute("phone", rs.getString("phone"));
                request.setAttribute("password", rs.getString("password"));
            }
            
            dispatcher = request.getRequestDispatcher("PatientProfile.jsp");
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
