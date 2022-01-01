<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<!-- 
	Page permettant de créer un nouveau kanban
	Contient un formulaire, avec 4 informations différentes, le nom, la description, une checkbox
		afin de rendre public ou non le kanban, et la liste des colonnes du kanban
	Cette page n'est accessible que lorsque l'utilisateur est connecté
	Le nombre maximum de colonne possible d'un kanban est de 7
 -->
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Création d'un utilisateur</title>
	</head>
	<body>
		<h1>Créer un compte responsable</h1>
		
		<!--
			Description du formulaire de la création de la ressource 
		 -->
		 
		<div id="container">
			<form method="post">
				<div>
					<label for="name">UserName :</label>
					<input type="text" id="userName" placeholder="username" name="userName" required>
				</div>
				<div>
					<label for="name">password :</label>
					<input type="text" id="password" placeholder="password" name="password" required>
				</div>
				<div>
					<input type="submit" id="submit-form" value="Créer">
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
    

    
