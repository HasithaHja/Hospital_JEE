import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/DoctorRegistration")
public class DoctorRegistration extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Retrieve form data
        String doctorID = request.getParameter("doctorID");
        String Dname = request.getParameter("Dname");
        String password = request.getParameter("password");
        String Dphone = request.getParameter("Dphone");
        String specialization = request.getParameter("specialization");
        
        RequestDispatcher dispatcher = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "", "");

            // Check if doctor already exists
            PreparedStatement checkStmt = con.prepareStatement("select * from doctors where userID = ?");
            checkStmt.setString(1, doctorID);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Doctor already exists
                request.setAttribute("status", "Doctor already exists!");
                dispatcher = request.getRequestDispatcher("DoctorRegistration.jsp");
            } else {
                // Insert new doctor
                PreparedStatement pst = con.prepareStatement("insert into doctors(userID, name, password, phone, specialization) values(?,?,?,?,?)");
                
                pst.setString(1, doctorID);
                pst.setString(2, Dname);
                pst.setString(3, password);
                pst.setString(4, Dphone);
                pst.setString(5, specialization);
                
                int rowCount = pst.executeUpdate();
                
                if (rowCount > 0) {
                    request.setAttribute("status", "Registration successful!");
                    dispatcher = request.getRequestDispatcher("DoctorSignin.jsp");
                } else {
                    request.setAttribute("status", "Registration failed. Please try again.");
                    dispatcher = request.getRequestDispatcher("DoctorRegistration.jsp");
                }
            }
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
