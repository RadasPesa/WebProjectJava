<%@ page import="pesa.web.servlets.RegistrationServlet" %>
<%@ page import="pesa.web.servlets.LoginServlet" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login</title>
  <link rel="stylesheet" href="css/loginStyle.css"/>
</head>
<body>

<div class="login">
  <h1>Login</h1>
  <form action="LoginServlet" method="post">
    <input type="text" name="username" placeholder="Username" required="required"
            <% if (LoginServlet.getLoginError() == 1) { %>
           value="<%=LoginServlet.getUsername()%>"
            <%}%> />
    <input type="password" name="password" placeholder="Password" required="required"/>
    <button type="submit" class="loginBtn">Sign in</button>
  </form>
  <p>Not registered yet? <button onclick="location.href='register.jsp'" type="button" class="signBtn">Sign up</button></p> <br />
  <% if (LoginServlet.getLoginError() == 1) {%>
  <p>The username or password you have entered is incorrect.</p>
  <%}%>
</div>

<%
  LoginServlet.setLoginError(0);
  RegistrationServlet.setErrorCode(0);
  RegistrationServlet.setUsername("");
%>

</body>
</html>
