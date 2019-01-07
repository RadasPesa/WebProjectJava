
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <title>Error</title>
</head>
<body>

<h1 align="center">Oops, something went wrong.</h1>
<%
    String error = request.getParameter("error");
    if (error == null) {
        error = "-1";
    }
    int errCode = Integer.parseInt(error);

    if (errCode == 1) {%>
        <h2 align="center">You need to login first.</h2>
        <p align="center"><a href="index.jsp">Back to login page</a></p>
<%
    } else if (errCode == 2) {%>
        <h2 align="center">You need to have an admin permission to view this site.</h2>
        <p align="center"><a href="main.jsp">Back to main page</a></p>
<%
    } else if (errCode == 3) {%>
        <h2 align="center">You need to have an admin permission to manage this profile.</h2>
        <p align="center"><a href="main.jsp">Back to main page</a></p>
<%
    } else {%>
<p align="center"><a href="index.jsp">Back to login page</a></p>
<hr />
<div>
    <%
        while (exception != null) { %>
            <p>
            Data type: <%= exception.getClass().getName() %> <br />
            Message: <%= exception.getMessage() %> <br />
            </p>
            <hr />
    <%      exception = exception.getCause();
        }%>
</div>
<%}%>
</body>
</html>
