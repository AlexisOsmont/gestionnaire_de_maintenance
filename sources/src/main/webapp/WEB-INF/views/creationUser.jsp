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
					<input type="text" id="userName" placeholder="Nom de la ressource" name="userName" required>
				</div>
				<div>
					<label for="name">password :</label>
					<input type="text" id="password" placeholder="Description" name="password" required>
				</div>
				<div>
					<input type="submit" id="submit-form" value="Créer">
				</div>
			</form>
			<%
					if (request.getAttribute("infoCreation") != null) {
						if(request.getAttribute("infoCreation").toString().startsWith("Creation")) { %>
							<p style="color:red">Erreur pendant la création de la ressource.</p>
				<% 	
						}
					}
				%>
		</div>
	</body>
</html>
    

    
