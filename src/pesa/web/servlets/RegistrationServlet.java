package pesa.web.servlets;

import pesa.app.UserBO;
import pesa.db.model.WebUserEntity;
import pesa.db.DBO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet(name = "RegistrationServlet", urlPatterns = "/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {

    private static int registerError = 0;

    private static String username;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPass = request.getParameter("confirmPass");

        if (!password.equals(confirmPass)) {
            setErrorCode(2); // Passwords don't match
            response.sendRedirect("register.jsp");
        } else {
            WebUserEntity user = new WebUserEntity();
            user.setNickname(username);
            Integer passHash = password.hashCode();
            user.setPassword(passHash.toString());
            user.setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()));

            boolean userAdded = UserBO.addNewUser(user);

            if (!userAdded) {
                setErrorCode(1); // Username already exists
                response.sendRedirect("register.jsp");
            } else {
                setErrorCode(3); // Registration done
                response.sendRedirect("register.jsp");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }

    public static int getErrorCode() {
        return registerError;
    }

    public static void setErrorCode(int e) {
        registerError = e;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        RegistrationServlet.username = username;
    }
}
