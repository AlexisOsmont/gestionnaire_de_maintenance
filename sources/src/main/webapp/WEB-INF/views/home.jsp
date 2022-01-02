<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" />
<title>Login</title>
</head>
<body>  
	<div class="segment">
    	<h1>Connectez Vous</h1>
 	</div>
	<div id="formulaire">
		<form method="post">
			<span>
				<label for="login">Login :</label>
				<input type="text" id="login" name="login" required>
			</span>
			<br><br>
			<span>	
				<label for="password">Password :</label>
				<input type="password" id="password" name="password" required>
			</span>
			<br><br>
			<span>			
				<input type="submit" value="Connexion">
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