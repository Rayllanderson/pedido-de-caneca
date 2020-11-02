<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
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
			 <h5 class="card-title text-center mt-4">Caneca x</h5>
			 
			 <form action="form-servlet" method="post">
			 <div class="group">
			 	<div class="form-group">
				    <label for="validationCustom04">Tema</label>
				      <select class="custom-select" id="validationCustom04" required>
				        <option selected disabled value="">Selecione o tema</option>
				        <option>...</option>
				      </select>
				     <div class="invalid-feedback">
				        Por favor, selecione uma opção
				      </div>
				 </div>
				 
				 
				 <div class="form-group" >
				   <label style="display: block">Foto personalizada</label>
				   <label for="foto" class="btn btn-outline-info" >escolha a foto aqui</label>
				   <input type="file" id="foto" style="display: none;" accept="image/*">
				 </div>
				 
				  <div class="form-group" >
					 <h5 class="text-preview"></h5>
					 <img class="preview-foto img-fluid" id="preview"></img>
				 </div>
				 
				<div class="form-group">
				    <label for="exampleFormControlTextarea1">Adicione mais detalhes</label>
				    <textarea class="form-control" id="exampleFormControlTextarea1" rows="4" placeholder="Dê mais detalhes pra gente (opcional)"></textarea>
				  </div>
			 	 <button type="submit" class="btn btn-success mb-4 float-right">Próximo</button>
			 </div>
			 </form>
		</div>
	</div>
	
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script> 
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.min.js"></script>
<script src="src/js/jQueryMask.js"></script>
<script src="src/js/preview-foto.js"></script>
<script type="text/javascript">

if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
	$('.group').css('width', '80%');
}

</script>
</body>


</html>