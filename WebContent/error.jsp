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
<link rel="stylesheet" href="src/css/main.css">
<link rel="icon" href="src/img/icon.png">
<style >
	h1, h2, h3, h4, p{
		color: white;
	}
	a{
		color:#FFB408
	}
	
	a:hover{
		color:#FBA408
	}
</style>

<title>Erro</title>
</head>
<body>

	<div class="mt-5 p-4 container" >
	
	<div style="font-size: 24px;" class="text-center mb-4">
		<span style="color: white"><i class="far fa-frown fa-3x"></i></span>
	</div>
	
	<div class="text-center">
		<h3>Ops... Algo deu errado, mas não se preocupe. </h3>
		<h3><a href="index.jsp" >Clique aqui para tentar novamente <i class="fas fa-mug-hot "></i></a></h3>
		
		<hr  class="mt-3"style="border-bottom: 1px solid white;">
		<p >Se o problema persistir por várias vezes consecutivas, você pode realizar seu pedido no nosso <a href="
		https://api.whatsapp.com/send?phone=NUMHERE&text=TEXTHERE">  WhatsApp <i class="fab fa-whatsapp"></i> </a> </p>
	</div>
	</div>
	
</body>

</html>