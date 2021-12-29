<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Accueil</title>
</head>
<body>
	<div id="formulaire">
		<form method="post" action="accueil">
			<span>
				<label for="anomalie">Décrivez l'anomalie : </label>
				<input type="text" id="anomalie" placeholder="Rentrez vos constations" name="anomalie" required>
			</span>
			<span>
				<input type="submit" value="Envoyez">
			</span>
		</form>
	</div>
</body>
</html>