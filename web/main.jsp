<%@ page import="pesa.db.model.WebUserEntity" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="pesa.db.model.DiscussionEntity" %>
<%@ page import="pesa.app.UserBO" %>
<%@ page import="pesa.app.CommentBO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Radim Pesa Website</title>
    <link rel="stylesheet" href="css/mainStyle.css"/>
</head>
<body>
<%
    WebUserEntity u = (WebUserEntity) session.getAttribute("loggedUser");
    int role = (int) session.getAttribute("userRole");

    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
    DateTimeFormatter commentFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String registered = u.getRegistrationDate().toLocalDateTime().format(format);
%>
<header>
    <div class="container">

        <a href="https://prf.osu.cz/" target="_blank"><img src="assets/bilaprf.png" alt="logo" class="logo"/></a>

        <nav>
            <ul>
                <li><a href="main.jsp">Home</a></li>
                <%  if (role == 1) {%>
                <li><a href="users.jsp">Users</a></li>
                <%}%>
                <li><a href="profile.jsp?manage=<%=u.getNickname()%>">Profile</a></li>
                <li><a href="LogoutServlet">Logout</a></li>
            </ul>
        </nav>
    </div>
</header>

<div class="container">
    <div class="leftPanel">
        <b><%=u.getNickname()%></b> <br /><br />
        Registered on: <%=registered%><br /><br />
        <a href="LogoutServlet">Logout</a> <br />
        <hr>
    </div>
    <div class="middlePanel">
        Welcome to OOPR3 assignment
    </div>
    <div class="rightPanel">
        <h2 align="center">Discussion</h2>
        <hr>
        <table style="width: 100%">
            <tbody>
            <% for (DiscussionEntity comment : CommentBO.getComments()) { %>
            <tr>
                <td style="white-space: nowrap"><%=comment.getAdditionDate().toLocalDateTime().format(commentFormat)%></td>
                <td>
                    <span><%=UserBO.getUser(comment.getUserId()).getNickname()%></span>
                    <%=comment.getComment()%>
                    <% if ((u.getUserId() == comment.getUserId()) || role == 1) {%>
                    <form action="DeleteCommentServlet" method="post">
                        <input type="hidden" name="commentId" value="<%=comment.getCommentId()%>"/>
                        <input type="hidden" name="actualPage" value="main.jsp"/>
                        <script>
                            function confirmFunc() {
                                var res = confirm("Are you sure you want to delete this comment?");
                                document.getElementById("delete").value = res;
                            }
                        </script>
                        <input type="hidden" name="delete" id="delete"/>
                        <input type="image" src="assets/delete.png" alt="deleteIcon" class="deleteIcon" onclick="confirmFunc()"/>
                    </form>
                    <%}%>
                </td>
            </tr>
            <%}%>
            </tbody>
        </table>
        <hr />
        <h3 align="center">Add comment</h3>
        <form action="CommentServlet" method="post">
            <textarea name="comment" rows="4"></textarea>
            <input type="hidden" name="userId" value="<%=u.getUserId()%>"/>
            <input type="hidden" name="currentPage" value="main.jsp"/>
            <button type="submit" class="submitBtn">Submit</button>
        </form>
    </div>
</div>

</body>
</html>
