<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
<link rel="stylesheet" href="src/css/alert.css">
<link rel="stylesheet" href="src/css/main.css">
<link rel="icon" href="src/img/icon.png">

<title>Caneca</title>

<style type="text/css">
	
		a{
		cursor: pointer;}

 		input[type="file"] {
            display: block;
        }
        
        
        .pip {
            display: inline-block;
            margin: 10px 10px 0 0;
        }
        
        .remove {
            display: block;
            text-align: center;
            cursor: pointer;
        }
        
        .remove:hover {
            background: white;
            color: black;
        }
        
        h5{
        	color: white;
        }

	
</style>
</head>
<body>
		
		<!-- ALERT -->
			<div class="fixed-top">
				<div class="alert alert-success" id="success-alert">
				   <button type="button" class="close" onclick="$('.alert').hide();">x</button>
				   <h4 id="titulo"></h4> <p id="alertMsg"></p>
				</div>
			</div>
		
	<section id="cover">
	<div class="container">
	
	
  <!--Grid row-->
  <div class="row">

    <!--Grid column-->
    <div class="col">

      <!-- Card -->
      <div class="card mb-3">
        <div class="card-body">
	
			 <h5 class="card-title text-center mt-4">Caneca Personalizada</h5>
			 
			 <form class="" action="order?action=next"
			 method="POST" id="order-form" enctype="multipart/form-data">		
			 <div class="group">
			 	<div class="form-group">
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
		                <label style="display: block">Foto personalizada 1</label>
		                <label for="file1" class="btn ">escolha a foto aqui</label>
		                <input id="file1" type="file" name="file1" accept="image/*" style="display: none">
		
		                <div class="form-group" id="div-preview1" style="display: none;">
		                    <h5 class="text-preview"></h5>
		                    <img class="img-fluid" id="preview1"></img>
		                    <div id="remove1">
		                        <a class="text-light float-right mt-2 mb-4" id="remove-photo1"><i class="fas fa-times"> <span class="items" >Remover foto</span></i></a>
		                    </div>
		                </div>
		
		            </div>
		
		            <div class="form-group" id="filediv2" style="display: none;">
		                <label style="display: block">Foto personalizada 2</label>
		                <label for="file2" class="btn ">escolha a foto aqui</label>
		                <input id="file2" type="file" name="file2" accept="image/*" style="display: none">
		
		                <div class="form-group" id="div-preview2" style="display: none;">
		                    <h5 class="text-preview"></h5>
		                    <img class="img-fluid" id="preview2"></img>
		                    <div id="remove2">
		                        <a class="text-light float-right mt-2 mb-4" id="remove-photo2"><i class="fas fa-times"> <span class="items" >Remover foto</span></i></a>
		                    </div>
		                </div>
		            </div>
		
		            <div class="form-group" id="filediv3" style="display: none;">
		                <label style="display: block">Foto personalizada 3</label>
		                <label for="file3" class="btn ">escolha a foto aqui</label>
		                <input id="file3" type="file" name="file3" accept="image/*" style="display: none">
		
		                <div class="form-group" id="div-preview3" style="display: none;">
		                    <h5 class="text-preview"></h5>
		                    <img class="img-fluid" id="preview3"></img>
		                    <div id="remove3">
		                        <a class="text-light float-right mt-2 mb-4" id="remove-photo3"><i class="fas fa-times"> <span class="items" >Remover foto</span></i></a>
		                    </div>
		                </div>
		            </div>
		
		            <label id="plus" class="btn" style="display: none;"> + </label>
		        </div><!--  fim input -->
		    
		    
				<div class="form-group">
				    <label for="exampleFormControlTextarea1">Adicione mais detalhes</label>
				    <textarea class="form-control" id="txt-area" name="descricao" rows="4" placeholder="Dê mais detalhes, diga pra gente como você quer a caneca (opcional)"></textarea>
				  </div>
				  <div class="col mb-4">
			 		 <button type="submit" id="btn-submit" class="btn float-right">Próximo</button>
			 	 </div>
			 </div>
			 </form>
		</div>
	</div>
	</div>
	</div>
	</div>
	
	</section>
	
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
<script src="src/js/alert.js"></script>

<!-- <script src="src/js/preview-foto.js"></script>
 -->
 <!-- 
<script src="src/js/previewPhoto.js"></script> -->
<script type="text/javascript">

$(".alert").hide();

if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
	$('.group').css('width', '80%');
}


$("#btn-submit").on('click', function () {
	if($("#temas").val() == null){
		 alertBootstrap("Você não selecionou nenhum tema", "alert alert-warning", "Ei!");
		$('#temas').focus();
	 }else{ 
		$('#btn-submit').prop('disabled', true);
		$("#btn-submit").html('Enviando...');
		$("#order-form").submit();
	 }
});
	    
</script>


</body>

<script src="src/js/preview.js"></script>

</html>