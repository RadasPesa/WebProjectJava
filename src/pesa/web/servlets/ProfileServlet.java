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
import java.io.PrintWriter;

@WebServlet(name = "ProfileServlet", urlPatterns = "/ProfileServlet")
public class ProfileServlet extends HttpServlet {

    private static int profileError = 0;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newUsername = request.getParameter("username");
        String newPassword = request.getParameter("password");

        String isAdmin = request.getParameter("admin");

        String managedUsername = request.getParameter("name");
        WebUserEntity user = UserBO.getUser(managedUsername);

        PrintWriter out = response.getWriter();

        if (newPassword == null) {
            boolean changed = UserBO.changeUsername(user, newUsername);
            if (changed) {
                setProfileError(0);
                if (isAdmin != null) {
                    if (isAdmin.equals("true")) {
                        response.sendRedirect("users.jsp");
                    }
                } else {
                    request.getSession().invalidate();
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Change successful. You can now login with new username.');");
                    out.println("location='index.jsp';");
                    out.println("</script>");
                }
            } else {
                setProfileError(1);
                response.sendRedirect("profile.jsp?manage=" + user.getNickname());
            }
        } else if (newUsername == null) {
            Integer passHash = newPassword.hashCode();
            boolean changed = UserBO.changePassword(user, passHash.toString());
            if (changed) {
                setProfileError(2);
                response.sendRedirect("profile.jsp?manage=" + user.getNickname());
            } else {
                setProfileError(3);
                response.sendRedirect("profile.jsp?manage=" + user.getNickname());
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("main.jsp");
    }

    public static int getProfileError() {
        return profileError;
    }

    public static void setProfileError(int profileError) {
        ProfileServlet.profileError = profileError;
    }
}
