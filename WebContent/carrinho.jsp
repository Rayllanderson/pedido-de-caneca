<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>


<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Resumo do pedido</title>

<link rel="stylesheet" href="src/css/alert.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
<!-- Bootstrap core CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<!-- Material Design Bootstrap -->
<!-- <link href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.19.1/css/mdb.min.css" rel="stylesheet"> -->

<style type="text/css">

	.card{
		 border-radius: 1em !important;
	}
	
 /*	.n-c{
		background-color: #28a745  !important;
	}
	
	 .f-p{
		background-color: #007bff  !important;
	}
	*/
	#img{
		 transition: transform .2s}
	
	#img:hover{
		 transform: scale(1.05);
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

<div class="container mt-5">
<!--Section: Block Content-->
<section>

  <!--Grid row-->
  <div class="row">

    <!--Grid column-->
    <div class="col-lg-8">

      <!-- Card -->
      <div class="card wish-list mb-3">
        <div class="card-body">
 <data id="start"> 
          <h5 class="mb-4">Carrinho (<span>${size}</span> ${txt})</h5>
	<c:forEach items="${canecas}" var="caneca">
          <div class="row mb-4" id="start">
            <div class="col-md-5 col-lg-3 col-xl-3">
              <div class="view zoom overlay z-depth-1 rounded mb-3 mb-md-0">
           	
                <c:if test="${!caneca.getFirstMiniature().isEmpty() && caneca.getFirstMiniature() != null}">
                     <img class="img-fluid w-100 img-thumbnail" id="img" src="${caneca.getFirstMiniature()}" />
                </c:if>
               
               <c:if test="${caneca.getFirstMiniature().isEmpty() || caneca.getFirstMiniature() == null}">
                     <img class="img-fluid w-100" id="img" src="src/img/loading miniature (3).gif" />
               </c:if>
             
              </div>
            </div>
            <div class="col-md-7 col-lg-9 col-xl-9">
              <div>
                <div class="d-flex justify-content-between">
                  <div>
                    <h5>Tema: ${caneca.tema}</h5>
                    <p class="mb-5 text-muted text-uppercase small">Quantidade: ${caneca.quantidade}</p>
                  </div>
                </div>
                <div class="d-flex justify-content-between align-items-center">
                  <div>
                  <div style="display: inline;" data-toggle="modal" data-target="#edit-modal" data-id="${caneca.id}">
                   <a href="#" id="edit" class="edit card-link-secondary small text-uppercase mr-3"><i
                        class="fas fa-edit mr-1"></i> Editar Caneca </a>
                   </div>
                  	<div style="display: inline;" data-toggle="modal" data-target="#exampleModalCenter" data-id="${caneca.id}" >
                   		 <a href="#!" type="button" class="card-link-secondary small text-uppercase"><i
                        class="fas fa-trash-alt mr-1"  ></i> Remover Caneca </a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <hr class="mb-4">
   </c:forEach>
 </data>        
          
          <p class="text-primary mb-0"><i class="fas fa-info-circle mr-1"></i> Não se preocupe se sua foto parecer borrada. Receberemos ela em qualidade original.</p>

        </div>
      </div>
      <!-- Card -->


    </div>
    <!--Grid column-->

    <!--Grid column-->
    <div class="bt-res col-lg-4">

      <!-- Card -->
      <div class="card mb-3">
        <div class="card-body">

          <h5 class="mb-3">The total amount of</h5>

          <ul class="list-group list-group-flush">
            <li class="list-group-item d-flex justify-content-between align-items-center px-0">
               <a  class="btn btn-success btn-block waves-effect waves-light" href="order.jsp">Nova Caneca</a>
            </li>
           
            <li class="list-group-item d-flex justify-content-between align-items-center border-0 px-0 "></li>
             
          </ul>
		<form action="email" method="post">
          <button type="submit" class="f-p btn btn-primary btn-block waves-effect waves-light">Finalizar Pedido</button>
		</form>
        </div>
      </div>
      <!-- Card -->

    </div>
    <!--Grid column-->

  </div>
  <!--Grid row-->

</section>
<!--Section: Block Content-->
</div>



	<!-- Modal confirmar remover caneca -->
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
       	Você tem certeza que deseja remover esta caneca?
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
        <button type="button" class="btn btn-danger" id="btn-delete">Excluir</button>
      </div>
    </div>
  </div>
</div>


	<!-- Modal confirmar remover caneca -->
<div class="modal" id="edit-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
    	<p class="p-2 mt-1">Redirecionando...</p>
       	<button id="btn-edit" style="display: none"></button>
     </div>
  </div>

</div>


<!-- JQuery -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!-- Bootstrap tooltips -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.4/umd/popper.min.js"></script>
<!-- Bootstrap core JavaScript -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.0/js/bootstrap.min.js"></script>
<!-- MDB core JavaScript -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.19.1/js/mdb.min.js"></script>
<script src="src/js/alert.js"></script>
<script src="src/js/ajax/loadMiniature.js"></script>
<script src="src/js/ajax/excluirCaneca.js"></script>
<script src="src/js/ajax/redirecionarEditar.js"></script>
</body>

</html>