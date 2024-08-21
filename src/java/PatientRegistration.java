import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/PatientRegistration")
public class PatientRegistration extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Retrieve form data
        String NIC = request.getParameter("NIC");
        String Pname = request.getParameter("Pname");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        
        RequestDispatcher dispatcher = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "", "");
            
            // Check if patient already exists
            PreparedStatement checkStmt = con.prepareStatement("select * from patients where NIC = ?");
            checkStmt.setString(1, NIC);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()){
                // Patient already exists
                request.setAttribute("status", "Patient already exists!");
                dispatcher = request.getRequestDispatcher("PatientRegistration.jsp");
            }else{
                PreparedStatement pst = con.prepareStatement("INSERT INTO patients (NIC, name, phone, password) VALUES (?, ?, ?, ?)");
            
                pst.setString(1, NIC);
                pst.setString(2, Pname);
                pst.setString(3, phone);
                pst.setString(4, password);

                int rowCount = pst.executeUpdate();
                
                if (rowCount > 0) {
                    request.getSession().setAttribute("patientNIC", NIC);
                    request.setAttribute("status", "Registration successful!");
                    dispatcher = request.getRequestDispatcher("PatientSignin.jsp");
                } else {
                    request.setAttribute("status", "Registration failed. Please try again.");
                    dispatcher = request.getRequestDispatcher("PatientRegistration.jsp");
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
