<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>url</title>
</head>
<body>
<img id='barcode' src='https://api.qrserver.com/v1/create-qr-code/?data="http://localhost:8080/gestionnaire_maintenance/accueil?id=<%= request.getAttribute("ressourceId") %>"&amp;size=75x75' width='75' height='75' /> 
<br>
<a href="http://192.168.76.76:8080/gestionnaire_maintenance/accueil?id=<%= request.getAttribute("ressourceId") %>">gestionnaire_maintenance/accueil?id=<%= request.getAttribute("ressourceId") %></a>
</body>
</html>