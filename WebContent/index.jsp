<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="src/js/fa.js"></script>

<title>Bem vindo</title>

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
			 <h5 class="card-title text-center mt-4">Faça o pedido de sua caneca aqui! <i class="fas fa-mug-hot"></i> </h5>
			 
			 <h6 class="card-title text-center mt-4">Seus dados de contato</h6>
			 
			 <form action="save-client" method="post" class="d-flex justify-content-center">
			 <div class="group">
			 	<div class="form-group">
			 	
				    <label for="formGroupExampleInput">Nome</label>
				    
				     <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend"><i class="far fa-user-circle"></i></span>
				        </div>
				    <input type="text" name="nome" class="form-control" id="nome" placeholder="Seu nome" required="required">
				    </div>
				 </div>
				 
				 <div class="form-group">
				   <label for="formGroupExampleInput">Telefone de contato</label>
				   
				    <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text"><i class="fab fa-whatsapp"></i></span>
				        </div>
				   <input name="telefone" type="text" class="telefone form-control" id="telefone"  inputmode="numeric" maxlength="12" placeholder="Whatsapp" required="required">
				   </div>
				 </div>
				 
			 	 <button type="submit" class="btn btn-success float-right">Próximo</button>
			 </div>
			 </form>
		</div>
	</div>
	
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script> 
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.min.js"></script>
<script src="src/js/jQueryMask.js"></script>
<script type="text/javascript">
if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
	$('.group').css('width', '80%');
}
</script>
</body>


</html>