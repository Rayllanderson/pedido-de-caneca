<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

<title>Insert title here</title>

<style type="text/css">
	
	.card{
		border-radius: 1em;
		
	}
	
	body{
		background-color: #f8f9fa;
	}
	
	.group{
		padding: 10px;
		width: 50%;
		margin: 0 auto;
	
	}
	


	
</style>
</head>
<body>
		
	<div class="mt-5"></div>
	
	<div class="container">
		<div class="card">
			 <h5 class="card-title text-center">Card title</h5>
			 
			 <div class="group">
			 	<div class="form-group">
				    <label for="formGroupExampleInput">Nome</label>
				    <input type="text" name="" class="form-control" id="nome" placeholder="Digite seu nome">
				 </div>
				 
				 <div class="form-group">
				   <label for="formGroupExampleInput">Telefone</label>
				   <input name="telefone" type="text" class="telefone form-control" id="telefone" style="width: 50%" inputmode="numeric" maxlength="12" placeholder="Whatsapp ou número do telefone">
				 </div>
				 
				 <div class="form-group">	
				  	<label for="formGroupExampleInput">Quantidade de Canecas</label>
					<input name="quantidade" type="number" class="form-control" id="quantidade" style="width: 50%" inputmode="numeric" maxlength="12" placeholder="Quantas canecas?">
				</div>
				 
			 	 <button type="submit" class="btn btn-primary">Próximo</button>
			 	
			 </div>
			 
		</div>
	</div>
	
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script> 
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.min.js"></script>
<script src="src/js/jQueryMask.js"></script>

</body>


</html>