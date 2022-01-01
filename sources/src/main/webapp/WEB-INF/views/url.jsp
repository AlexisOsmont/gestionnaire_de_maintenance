<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>url</title>
</head>
<body>
<a href="http://localhost:8080/gestionnaire_maintenance/accueil?id=<%= request.getAttribute("ressourceId") %>">http://localhost:8080/gestionnaire_maintenance/acceuil?id=<%= request.getAttribute("ressourceId") %></a>
</body>
</html>