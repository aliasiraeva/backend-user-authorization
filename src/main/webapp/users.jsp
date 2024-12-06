<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.itis.backend.entity.User" %>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Users</title>
</head>

<body>
<h1>Users:</h1>
<table>
  <td>ID</td>
  <td>Username</td>

    <%
        List<User> users = (List<User>) request.getAttribute("users");
        for (var u : users) {
    %>
  <tr>
    <td><%=u.getId()%></td>
    <td><%=u.getUsername()%></td>
  </tr>
    <%
        }
    %>
</table>
</body>
</html>