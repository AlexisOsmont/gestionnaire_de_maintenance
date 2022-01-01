<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<div id="formulaire">
		<form method="post">
			<span>
				<label for="login">Login :</label>
				<input type="text" id="login" name="login" required>
			</span>
			<span>	
				<label for="password">Password :</label>
				<input type="password" id="password" name="password" required>
			</span>
			<span>
				<input type="submit" value="Envoyez">
			</span>
		</form>
		<%
		if (request.getAttribute("infoLogin") != null) {
		%>
			<p style="color:red">Nom d'utilisateur ou mdp incorrect.</p>
		<%		
		}	
		%>
	</div>
</body>
</html>