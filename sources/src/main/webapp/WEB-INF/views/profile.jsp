<!-- Vue qui permet d'afficher le compte d'un utilisateur.
	Cette vue est traitée par la servlet ProfileServlet.
	Elle contient les informations pour les kanbans donc l'utilisateur est gestionnaire,
	pour les kanbans où l'utilisateur est connecté et l'ensemble de ses taches dans les deux cas. -->
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="beans.Ressource"%>
<%@page import="beans.User"%>
<%@page import="beans.Demande"%>
<%@page import="dao.*"%>
<%@page import="beans.*"%>
<%@page import="java.util.*"%>
<%@ page import="dao.DemandeDAO" %>
<%@ page import ="java.util.List"%>
<%@ page import="beans.Demande" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Profile</title>
</head>
<body>
	<%
		User user = session.getAttribute("user") != null ? (User) session.getAttribute("user") : null;
	%>
	<h3><%=user.getUsername()%>, Vous êtes connecté(e) en tant que <%=user.getRole()%></h3>
	<%  if (!user.getRole().equals("admin")) {%>
   			<h2>Liste des Demandes :</h2><br>
	
			<%
			List<Demande> listDemandes = (List<Demande>) request.getAttribute("demandes");
			for (Demande demandeBuf : listDemandes) {
				if(!demandeBuf.getState().equals("valid")) {
			%>
			<form method="POST"> 
				<label>
				<%
				List<Ressource> listRessource = (List<Ressource>) request.getAttribute("ressource");
				%>
				<%
				for (Ressource ressource : listRessource) {
				
					if (ressource.getId() == demandeBuf.getIdSource()) {
						%>
						<%= ressource.getNom()%> : <%= ressource.getLocalisation()%> -> (<%= demandeBuf.getDescription()%>)
						<%
					}
				}
				%></label>
				<button type="submit" name="validerDemande" value="<%=demandeBuf.getIdRequest()%>"> Valider </button>
   			</form> 
			<br>
			<%
				}
			} 
			%>
    		<h2>Liste des ressources :</h2>
			<%
			List<Ressource> listRessource = (List<Ressource>) request.getAttribute("ressource");
			%>
			<%
			for (Ressource ressourceBuf : listRessource) {
			%>
				<form method="POST">  <label><%= ressourceBuf.getNom()%>:<%=ressourceBuf.getLocalisation() %>-> (<%=ressourceBuf.getDescription() %>)</label>
      				<button type="submit" name="suppRessource" value="<%=ressourceBuf.getId()%>"> Supprimer </button>  
      				<button type="submit" name="generateURL" value="<%=ressourceBuf.getId()%>"> Générer l'URL </button>
    			</form> 
    			<br>
			<%
			} 
			
			%>
			<form method="POST">  
	       		<input type="submit" name="CreationRessource" value="Créer une ressource"/>  
	     	</form> 
		<%} else { %>
			<h2>Liste des responsables de maintenance :</h2><br>
	
			<%
			List<User> listUser = (List<User>) request.getAttribute("Users");
			for (User userBuf : listUser) {
			%>
			<form method="POST"> 
				<label>Username : <%= userBuf.getUsername() %>  <br> mot de pass : <%= userBuf.getPassword()%></label><br>
				<button type="submit" name="suppUser" value="<%=userBuf.getId()%>"> Supprimer </button>
   			</form> 
			<br>
			<%
			} 
			%>
			 <form method="POST">  
       			<input type="submit" name="CreationUser" value="CreationUser"/>  
     		</form> 
		<%} %>
</body>
</html>