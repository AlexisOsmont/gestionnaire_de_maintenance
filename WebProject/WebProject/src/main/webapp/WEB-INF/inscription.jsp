<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<!-- 
	Page permettant de créer un nouveau compte utilisateur
	Contient un formulaire avec 4 champs : username, nom, prenom et mot de passe
 -->
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Création de compte responsable</title>
	</head>
	<body>
		<div id="container">
			<form method="post">
				<h1>Créer le compte responsable</h1>
				<span>
					<label for="username">Nom de l'utilisateur :</label>
					<input type="text" id="username" placeholder="Nom de l'utilisateur" name="username" required>
				</span>
				<span>
					<label for="role">role :</label>
					<input type="text" id="role" placeholder="Role" name="role" required>
				</span>
				<span>
					<label for="password">Mot de passe :</label>
					<input type="password" id="password" placeholder="Mot de passe" name="password" required>
				</span>
				<span>
					<input type="submit" value="Valider">
				</span>
		
				<!-- 
					On affiche un message à l'adiministrateur en fonction de l'erreur renvoyé côté serveur
				 -->
				 
				<%
					if (request.getAttribute("infoRegistration") != null) {%>
						<p style="color:red">L'enregistrement a échoué.</p>
				<%	}
					
				%>
			</form>
		</div>
	</body>
</html>
