<%@ page import="pesa.db.model.WebUserEntity" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="pesa.web.servlets.ProfileServlet" %>
<%@ page import="pesa.db.model.DiscussionEntity" %>
<%@ page import="pesa.app.UserBO" %>
<%@ page import="pesa.app.CommentBO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="css/mainStyle.css"/>
</head>
<body>
<%
    WebUserEntity u = (WebUserEntity) session.getAttribute("loggedUser");
    int role = (int) session.getAttribute("userRole");

    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
    DateTimeFormatter commentFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String registered = u.getRegistrationDate().toLocalDateTime().format(format);

    String managedUsername = request.getParameter("manage");
    WebUserEntity managedUser = UserBO.getUser(managedUsername);
    if (!u.getNickname().equals(managedUser.getNickname())) {
        if (role != 1) {
            throw new UnsupportedOperationException("Insufficient permission to manage this profile");
        }
    }

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
        <hr />
    </div>

    <div class="middlePanel">
        <h3 align="center">EDIT USER INFORMATION</h3>
        <form method="post" action="ProfileServlet?name=<%=managedUser.getNickname()%><% if (role == 1) {%>&admin=true<%}%>">
            Username: <input type="text" name="username" value="<%=managedUser.getNickname()%>" pattern="([A-z]|\d){3,}" title="Must contain 3 or more characters. Use letters and numbers only" required="required"/> <br />
            <button type="submit" class="changeBtn">Change</button>
        </form>
        <form method="post" action="ProfileServlet?name=<%=managedUser.getNickname()%>">
            Password: <input type="password" name="password" value="<%=managedUser.getPassword()%>" <% if (role != 1) {%> pattern="(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{8,}" title="Must contain 8 or more characters and at least one uppercase and lowercase letter and one number" <%}%> required/> <br />
            <button type="submit" class="changeBtn">Change</button>
        </form>
        <%
            if (ProfileServlet.getProfileError() == 0) {%>
                <br />
        <%} else if (ProfileServlet.getProfileError() == 1) {%>
                <p>This username already exists.</p>
        <%} else if (ProfileServlet.getProfileError() == 2) {%>
                <p>Password was successfully changed.</p>
        <%} else if (ProfileServlet.getProfileError() == 3) {%>
                <p>This should never happen!</p>
        <%}%>
    </div>

    <div class="rightPanel">
        <h2 align="center">Discussion</h2>
        <hr />
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
                        <input type="hidden" name="actualPage" value="profile.jsp?manage=<%=managedUser.getNickname()%>"/>
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
            <input type="hidden" name="currentPage" value="profile.jsp?manage=<%=managedUser.getNickname()%>"/>
            <button type="submit" class="submitBtn">Submit</button>
        </form>
    </div>
</div>

<%
    ProfileServlet.setProfileError(0);
%>

</body>
</html>
