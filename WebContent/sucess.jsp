<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<!-- Font Awesome -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
<link rel="stylesheet" href="src/css/alert.css">

<title>Insert title here</title>
</head>
<body>

	<!-- ALERT -->
	<div class="fixed-top">
		<div class="alert alert-success" id="success-alert">
			<button type="button" class="close" onclick="$('.alert').hide();">x</button>
			<h4 id="titulo"></h4>
			<p id="alertMsg"></p>
		</div>
	</div>

	<div class="mt-5"></div>
	<div class="container">
		
		<div class="card">
			<h1>Sucesso!</h1>
			<h2>Exemplo de pagina redireciada...</h2>
		</div>
		
	</div>





	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script
		src="//cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.min.js"></script>
	<script src="src/js/alert.js"></script>


</body>

</html>