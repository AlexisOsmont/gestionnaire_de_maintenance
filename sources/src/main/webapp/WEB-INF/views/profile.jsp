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
	<!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/css/printKanbans.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css" />
	<script src="${pageContext.request.contextPath}/js/menu.js"></script>
    <script src="${pageContext.request.contextPath}/js/textareaParser.js"></script>
	<script>
		function validateAction(text, id) {
    		if (confirm(text)) {
      	 	 document.getElementById(id).submit();
    		}
		}
	</script>
	<style>
		select {
			height : 25px;
			width : 250px;
		}
	</style> -->
</head>
<body>
	<%
		User user = session.getAttribute("user") != null ? (User) session.getAttribute("user") : null;
	%>
	<ul class="ulMenu">
			<li id="page"><%=user.getUsername()%>, Vous êtes connecté(e) en tant que <%=user.getRole()%></li>
	</ul>
	<%  	if (user.getRole() != "admin") {%>
				<%
		List<Demande> listDemandes = (List<Demande>) request.getAttribute("demandes");
		%>
		<ul>
		<%
		for (Demande demandeBuf : listDemandes) {
		%>
			<li> <%= demandeBuf.getDescription() %></li>
		<%
		} 
		
		%>
		</ul>
		 <form method="POST">  
       		<input type="submit"/>  
     	</form> 
		<%} else { %>
		<%} %>
</body>
</html>