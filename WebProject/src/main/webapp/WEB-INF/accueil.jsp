<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.test.bdd.Demandes" %>
<%@ page import ="java.util.List"%>
<%@ page import="com.test.beans.Demande" %>

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
	<%
	List<Demande> listDemandes = (List<Demande>) request.getAttribute("demandes");
	%>
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