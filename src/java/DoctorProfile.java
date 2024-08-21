import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/DoctorProfile")
public class DoctorProfile extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Retrieve doctor ID from session
        String doctorID = (String) request.getSession().getAttribute("doctorID");
        
        if (doctorID == null) {
            response.sendRedirect("DoctorSignin.jsp");
            return;
        }
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "", "");
            PreparedStatement pst = con.prepareStatement("SELECT * FROM doctors WHERE userID = ?");
            pst.setString(1, doctorID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                request.setAttribute("Dname", rs.getString("name"));
                request.setAttribute("Dphone", rs.getString("phone"));
                request.setAttribute("specialization", rs.getString("specialization"));
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("DoctorProfile.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
