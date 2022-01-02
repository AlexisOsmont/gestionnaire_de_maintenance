<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/url.css" />
<title>url</title>
</head>
<body>
<img id='barcode' src='https://api.qrserver.com/v1/create-qr-code/?data="http://192.168.76.76:8080/gestionnaire_maintenance/accueil?id=<%= request.getAttribute("ressourceId") %>"&amp;size=75x75' width='75' height='75' />
<h1> Flashez-moi !</h1>
<p>Pour signaler un problème avec ce matériel.</p>
<a href="http://192.168.76.76:8080/gestionnaire_maintenance/accueil?id=<%= request.getAttribute("ressourceId") %>">gestionnaire_maintenance/accueil?id=<%= request.getAttribute("ressourceId") %></a>
</body>
<br>
<br>
<form>
  <input id="impression" name="impression" type="button" onclick="imprimer_page()" value="Imprimer l'étiquette" />
</form>
<script type="text/javascript">
function imprimer_page(){
  window.print();
}
</script>
</html>