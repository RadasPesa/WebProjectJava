package pesa.web.servlets;

import pesa.app.UserBO;
import pesa.db.model.WebUserEntity;
import pesa.db.DBO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static int loginError = 0;

    private static String username;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        username = request.getParameter("username");
        String password = request.getParameter("password");
        Integer passHash = password.hashCode();

        WebUserEntity user = new WebUserEntity();
        user.setNickname(username);
        user.setPassword(passHash.toString());

        WebUserEntity loggedUser = UserBO.checkLoginData(user);

        if (loggedUser == null) {
            setLoginError(1); // Wrong login data
            response.sendRedirect("index.jsp");
        } else {
            int role = UserBO.getUserRole(loggedUser);
            setLoginError(0); // Successfully logged in
            session.setAttribute("loggedUser", loggedUser);
            session.setAttribute("userRole", role);
            response.sendRedirect("main.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }

    public static String getUsername() {
        return username;
    }

    public static int getLoginError() {
        return loginError;
    }

    public static void setLoginError(int loginError) {
        LoginServlet.loginError = loginError;
    }
}
