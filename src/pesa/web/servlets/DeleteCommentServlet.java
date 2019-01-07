package pesa.web.servlets;

import pesa.app.CommentBO;
import pesa.db.DBO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteCommentServlet", urlPatterns = "/DeleteCommentServlet")
public class DeleteCommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String actualPage = request.getParameter("actualPage");
        String commentIdS = request.getParameter("commentId");
        int commentId = Integer.parseInt(commentIdS);
        String delete = request.getParameter("delete");

        boolean deleted = true;

        if (delete.equals("true")) {
            deleted = CommentBO.deleteComment(commentId);
        }

        if (deleted) {
            response.sendRedirect(actualPage);
        } else {
            response.sendRedirect("errorPage.jsp");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("main.jsp");
    }
}
