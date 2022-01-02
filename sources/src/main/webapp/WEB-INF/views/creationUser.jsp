<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" />
		<title>Création d'un utilisateur</title>
	</head>
	<body>
		<div class="segment">
   			 <h1>Création d'un compte</h1>
   			 <h1>responsable maintenance</h1>
  		</div>
		
		<!--
			Description du formulaire de la création de la ressource 
		 -->
		 
		<div id="container">
			<form method="post">
				<div>
					<label for="name">UserName :</label>
					<input type="text" id="userName" placeholder="username" name="userName" required>
				</div>
				<br><br>
				<div>
					<label for="name">password :</label>
					<input type="text" id="password" placeholder="password" name="password" required>
				</div>
				<br><br>
				<div>
					<button type="submit" id="submit-form" value="Créer"> Créer l'utilisateur    </button>
				</div>
			</form>
			<%
				if (request.getAttribute("infoRegistration") != null) {
					if(request.getAttribute("infoRegistration").toString().startsWith("Username:")) { %>
						<p style="color:red">Nom d'utilisateur existe.</p>
			<% 	
					}
				}
			%>
		</div>
	</body>
</html>
    

    
