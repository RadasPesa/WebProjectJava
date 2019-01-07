<%@ page import="pesa.web.servlets.RegistrationServlet" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="css/registerStyle.css"/>
</head>
<body>

<div class="registration">
    <h2>Create account</h2>
    <form action="RegistrationServlet" method="post">
        <h3>Username</h3>
        <input type="text" name="username" placeholder="Username" pattern="([A-z]|\d){3,}" title="Must contain 3 or more characters. Use letters and numbers only" required="required"
                <%
                    if (RegistrationServlet.getErrorCode() == 1 || RegistrationServlet.getErrorCode() == 2) { %>
               value="<%=RegistrationServlet.getUsername()%>"
                <%}%> />

        <% if (RegistrationServlet.getErrorCode() == 1) { %>
        <p>That username is already used. Try another.</p>
        <%} else { %>
        <p><br /></p>
        <%}%>
        <h3>Password</h3>
        <input type="password" name="password" placeholder="Password" pattern="(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{8,}" title="Must contain 8 or more characters and at least one uppercase and lowercase letter and one number" required="required" />
        <input type="password" name="confirmPass" placeholder="Confirm" required="required" />

        <%
            if (RegistrationServlet.getErrorCode() == 2) { %>
        <p>These passwords don't match. Try again.<br /><br /></p>
        <%} else { %>
        <p style="color: white">Use 8 or more characters including at least one uppercase and lowercase letter and one number</p>
        <%}%>

        <button type="submit" class="registerBtn">Register</button>
        <button onclick="location.href='index.jsp'" type="button" class="registerBtn" style="width: 25%; float: left; padding: 5px; margin-top: -10px">Back</button>
    </form>
    <%
        if (RegistrationServlet.getErrorCode() == 3) {
            RegistrationServlet.setErrorCode(0); %>
    <h4>Registration was successful! <br />
        You will be redirected to login page <br /> in 3 seconds</h4>
    <meta http-equiv="refresh" content="3; url=index.jsp" />
    <%}
    RegistrationServlet.setErrorCode(0);%>
</div>

</body>
</html>
