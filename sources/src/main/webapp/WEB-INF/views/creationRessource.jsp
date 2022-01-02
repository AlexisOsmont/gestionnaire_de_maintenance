<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<!-- 
	Page permettant de créer un nouveau kanban
	Contient un formulaire, avec 4 informations différentes, le nom, la description, une checkbox
		afin de rendre public ou non le kanban, et la liste des colonnes du kanban
	Cette page n'est accessible que lorsque l'utilisateur est connecté
	Le nombre maximum de colonne possible d'un kanban est de 7
 -->
 <%@page import="beans.User"%>
 
 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" />
		<title>Création d'une ressource</title>
	</head>
	<body>
		<%
		User user = session.getAttribute("user") != null ? (User) session.getAttribute("user") : null;
		%>
		<h3 id="page"><%=user.getUsername()%>, vous êtes connecté(e) en tant que <%=user.getRole()%></h3>
		<div class="segment">
   			 <h1>Création de ressource</h1>
  		</div> 
		<div id="container">
			<form method="post">
				<div>
					<label for="name">Nom de la ressource :</label>
					<input type="text" id="name" placeholder="" name="name" required>
				</div>
				<br><br>
				<div>
					<label for="name">Description :</label>
					<input type="text" id="description" placeholder="Ne s'allume plus" name="description" required>
				</div>
				<br><br>
				<div>
					<label for="public">localisation :</label>
					<input type="text" id="localisation" placeholder="U2.2.52" name="localisation" required>
				</div>
				<br><br>
				<div>
					<button type="submit" id="submit-form" value="Créer">Créer la ressource</button>
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
    

    
