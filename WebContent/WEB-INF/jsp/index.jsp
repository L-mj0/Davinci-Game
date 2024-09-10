<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-uA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/index.css">
<title>Login</title>

</head>
<body>
<div class="login-form">
    <h2>Login</h2>
    <form action="login" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        
        <div class="remember-me">
        	<label for="checkboxtext">记住我的用户ID</label>
        	<input type="checkbox" name="remember">
        </div>
        <button type="submit">Log in</button>
        <button type="button" onclick="window.location.href='register'">Register</button>
    </form>
</div>
<%
String errorMessage = (String)request.getAttribute("errorMessage");
if(errorMessage!= null && !errorMessage.isEmpty()){
%>
	<script type="text/javascript">alert("<%= errorMessage %>")</script>
<%	
}
%>

</body>
</html>