<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="src/css/alert.css">
<script src="src/js/fa.js"></script>

<title>Editando Caneca</title>

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
	
	a{
	cursor: pointer;}
	
.loader{
  position: fixed;
  left: 0px;
  top: 0px;
  width: 100%;
  height: 100%;
  z-index: 9999;
  background: url('src/img/loading page.gif') 
              50% 50% no-repeat #e93976;
}
#loading-img{
	background: url("src/img/loading page.gif");
}
	
</style>
</head>
<body>

<div class="loader" id="loading"></div>
		
		<!-- ALERT -->
			<div class="fixed-top">
				<div class="alert alert-success" id="success-alert">
				   <button type="button" class="close" onclick="$('.alert').hide();">x</button>
				   <h4 id="titulo"></h4> <p id="alertMsg"></p>
				</div>
			</div>
		
	<div class="mt-5"></div>
	<div class="container">
	
	
  <!--Grid row-->
  <div class="row">

    <!--Grid column-->
    <div class="col">

      <!-- Card -->
      <div class="card mb-3">
        <div class="card-body">
	
			 <h5 class="card-title text-center mt-4">Caneca x</h5>
			 
			 <form class="" action="order?action=next"
			 method="POST" id="order-form" enctype="multipart/form-data">		
			 <div class="group">
			 	<div class="form-group">
			 		<div style="display: none">
				 		<input type="text" id="id" name="id">
				 		<input name="hasChangedImage" id="hasChangedImage">
			 		</div>
				    <label for="validationCustom04">Tema</label>
				      <select class="custom-select" id="temas" name="tema-id" required>
				        <option selected disabled value="">Selecione o tema</option>
				       	<c:forEach items="${temas}" var="tema">
							<option id="tema-id"  value="${tema.id}"> 
								${tema.nome}
							</option>
				    	 </c:forEach>
				      </select>
				     <div class="invalid-feedback">
				        Por favor, selecione uma opção
				      </div>
				 </div>
				 
				 <div class="form-group" >
				  <label for="numberExample">Quantidade de canecas</label>
				  <input type="number" id="qtd" class="form-control" style="width: 80px; text-align: center" min="1" max="9999" value="1" name="quantidade" required>
				 
				</div>
				
				 <!-- input file  -->
		        <div id="files">
		            <div class="form-group" id="filediv">
						<input type="text" id="id-1">
		                <label style="display: block">Foto personalizada 1</label>
		                <label for="file1" class="btn btn-outline-primary">escolha a foto aqui</label>
		                <input id="file1" type="file" name="file1" accept="image/*" style="display: none">
		
		                <div class="form-group" id="div-preview1" style="display: none;">
		                    <h5 class="text-preview"></h5>
		                    <img class="img-fluid" id="preview1"></img>
		                    <div id="remove1">
		                        <a class="text-danger float-right mt-2 mb-4" id="remove-photo1"><i class="fas fa-times"> <span class="items" >Remover foto</span></i></a>
		                    </div>
		                </div>
		
		            </div>
		
		            <div class="form-group" id="filediv2" style="display: none;">
						<input type="text" id="id-2">
		                <label style="display: block">Foto personalizada 2</label>
		                <label for="file2" class="btn btn-outline-primary">escolha a foto aqui</label>
		                <input id="file2" type="file" name="file2" accept="image/*" style="display: none">
		
		                <div class="form-group" id="div-preview2" style="display: none;">
		                    <h5 class="text-preview"></h5>
		                    <img class="img-fluid" id="preview2"></img>
		                    <div id="remove2">
		                        <a class="text-danger float-right mt-2 mb-4" id="remove-photo2"><i class="fas fa-times"> <span class="items" >Remover foto</span></i></a>
		                    </div>
		                </div>
		            </div>
		
		            <div class="form-group" id="filediv3" style="display: none;">
						<input type="text" id="id-3">
		                <label style="display: block">Foto personalizada 3</label>
		                <label for="file3" class="btn btn-outline-primary">escolha a foto aqui</label>
		                <input id="file3" type="file" name="file3" accept="image/*" style="display: none">
		
		                <div class="form-group" id="div-preview3" style="display: none;">
		                    <h5 class="text-preview"></h5>
		                    <img class="img-fluid" id="preview3"></img>
		                    <div id="remove3">
		                        <a class="text-danger float-right mt-2 mb-4" id="remove-photo3"><i class="fas fa-times"> <span class="items" >Remover foto</span></i></a>
		                    </div>
		                </div>
		            </div>
		
		            <label id="plus" class="btn btn-outline-primary" style="display: none;"> + </label>
		        </div><!--  fim input -->
				 
				 
				 
				  <div class="form-group" id="div-preview" style="display: none;" >
					 <h5 class="text-preview"></h5>
					 <img class="preview-foto img-fluid" id="preview"></img>
					 <div id="remove" data-toggle="modal" data-target="#exampleModalCenter">
					 	<a class="text-danger float-right mt-2 mb-4" id="remove-photo"><i class="fas fa-times"> <span class="items" >Remover foto</span></i></a>
					 </div>
				 </div>
				 
				<div class="form-group">
				    <label for="exampleFormControlTextarea1">Adicione mais detalhes</label>
				    <textarea class="form-control" id="txt-area" name="descricao" rows="4" placeholder="Dê mais detalhes, diga pra gente como você quer a caneca (opcional)"></textarea>
				  </div>
				  <div class="col mb-4">
			 		 <button type="submit" id="btn-submit" class="btn btn-primary float-right">Próximo</button>
			 	 </div>
			 </div>
			 </form>
		</div>
	</div>
	</div>
	</div>
	</div>
	
	
	
	<!-- Modal confirmar remover foto -->
<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title text-danger" id="exampleModalLongTitle">Atenção <i class="fas fa-exclamation-triangle"></i></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
       	Você tem certeza que deseja remover a foto? Você ainda poderá escolher outra.
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
        <button type="button" class="btn btn-danger" id="excluir">Excluir</button>
      </div>
    </div>
  </div>
</div>
	
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script> 
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.min.js"></script>
<script type="text/javascript">
function hasChangedImage(value){
	$('#hasChangedImage').val(value)
}

</script>
<script src="src/js/alert.js"></script>
<script src="src/js/edit-preview.js"></script>

<script type="text/javascript">

$(".alert").hide();

if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
	$('.group').css('width', '80%');
}

</script>
<script src="src/js/edit-caneca.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $("#loading").hide();
});

//capturando os atributos
var canecaId = "${caneca.id}"
var tema = "${caneca.tema.id}"
var quantidade = "${caneca.quantidade}"
var descricao ="${caneca.descricao}";
var imgs = []

    <c:forEach items="${caneca.fotos}" var="imagem">
    imgs.push({ base64 : '<c:out value="${imagem.base64Html}" />',
  		id:  '<c:out value="${imagem.id}" />' });
    </c:forEach>

console.log(imgs)

//setando atributos
$('#id').val(canecaId)
$('#temas').val(tema);
$("#txt-area").val(descricao);
$("#qtd").val(quantidade == '' ? 1 : quantidade)
setImage(imgs);

</script>

</body>


</html>