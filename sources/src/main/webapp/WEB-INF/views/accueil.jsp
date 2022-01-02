<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="dao.DemandeDAO" %>
<%@ page import="dao.RessourceDAO" %>
<%@ page import ="java.util.List"%>
<%@ page import="beans.Demande" %>
<%@ page import="beans.Ressource" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" />
<title>accueil</title>
</head>
<body>
	<%
	List<Demande> listDemandes = (List<Demande>) request.getAttribute("demandes");
	Ressource ressource = (Ressource) session.getAttribute("ressource");
	%>
	
	<div class="segment">
    <h1><%= ressource.getNom()%> : <%= ressource.getLocalisation()%></h1>
    <br>
    <br>
    <h1><%= ressource.getDescription() %></h1>
  	</div>
	
	<div id="formulaire">
		<form method="post" action="accueil">
			<span>
				<label for="anomalie">Décrivez l'anomalie : </label>
				<input type="text" id="anomalie" placeholder="Rentrez vos constations" name="anomalie" required><br><br>
				<label for="anomalie">Identifiez vous  (optionnel) : </label>
				<input type="test" id="idUser" placeholder="Entrez votre numero d'identifiant" name="idUser"><br><br>
			</span>
			<span><br>
				<input type="submit" value="Envoyez">
			</span>
		</form>
	</div>
	
	<p>Exemple d'anomalie précédemment rentré par des utilisateurs :</p>
	<ul>
	<%
	for (Demande demande : listDemandes) {
	%>
		<li> <%= demande.getDescription() %></li>
	<%
	} 
	%>
	</ul>
	<!-- 
	<p>
		<c:forEach var="demande" items="$ { demandes }">
			<c:out value="${ demande }" />
		</c:forEach>
		
		<c:out value="test"/>
	</p>
	-->
</body>
</html>