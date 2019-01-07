package pesa.web.servlets;

import pesa.app.CommentBO;
import pesa.db.model.DiscussionEntity;
import pesa.db.DBO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet(name = "CommentServlet", urlPatterns = "/CommentServlet")
public class CommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String commText = request.getParameter("comment");
        String commUserId = request.getParameter("userId");
        String currentPage = request.getParameter("currentPage");

        DiscussionEntity comment = new DiscussionEntity();
        comment.setUserId(Integer.parseInt(commUserId));
        comment.setComment(commText);
        comment.setAdditionDate(Timestamp.valueOf(LocalDateTime.now()));

        boolean commentAdded = CommentBO.addNewComment(comment);

        response.sendRedirect(currentPage);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("main.jsp");
    }
}
