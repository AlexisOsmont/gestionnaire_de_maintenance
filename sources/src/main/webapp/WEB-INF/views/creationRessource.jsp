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
		<title>Création d'une ressource</title>
	</head>
	<body>
		<h1>Créer une ressource</h1>
		
		<!--
			Description du formulaire de la création de la ressource 
		 -->
		 
		<div id="container">
			<form method="post">
				<div>
					<label for="name">Nom de la ressource :</label>
					<input type="text" id="name" placeholder="Nom de la ressource" name="name" required>
				</div>
				<div>
					<label for="name">Description :</label>
					<input type="text" id="description" placeholder="Description" name="description" required>
				</div>
				<div>
					<label for="public">localisation :</label>
					<input type="text" id="localisation" placeholder="Localisation" name="localisation" required>
				</div>
				<div>
					<input type="submit" id="submit-form" value="Valider">
				</div>
			</form>
			<%
					if (request.getAttribute("infoCreation") != null) {
						if(request.getAttribute("infoCreation").toString().startsWith("Creation")) { %>
							<p style="color:red">Erreur pendant la création du kanban.</p>
				<% 	
						}
					}
				%>
		</div>
	</body>
</html>
    

    
