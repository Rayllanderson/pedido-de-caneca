<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<meta name="viewport" content="width=device-width, initial-scale=0.95">
<title>Resumo do pedido</title>

<style type="text/css">


body {
	background-color: #f8f9fa;
}

img{
	height: 80px;
	width: 80px;
	border-radius: 1em;
	margin: 0 auto;
	display: flex;
}

.card{
	border-radius: 1em;
}

</style>
</head>
<body>

	<div class="mt-5"></div>

	<div class="container">
		<div class="card">
			<h5 class="card-title text-center mt-4">Resumo do pedido /Carrinho</h5>
			
			<div class="form-group mt-5 mb-1 p-3">
				<button class="btn btn-success float-right">Nova caneca</button>
			</div>
			<!--  INICIO TABELA  -->
			<div class="table-responsive-lg mb-5">
				<table class="table" id="tabela"
					style="border: 0; border-radius: 1rem;">

					<thead>
						<tr class="text-primary">
							<th scope="col" style="text-align: center">Foto</th>
							<th scope="col">Tema</th>
							<th scope="col">Editar</th>
							<th scope="col">Excluir</th>
						</tr>
					</thead>

					<tbody>
					<!--  	<c:forEach items="${canecas}" var="caneca"> -->
							<tr>
								<td data-label="Foto"> <img src="https://www.einerd.com.br/wp-content/uploads/2019/09/One-Piece-capa-890x466.png"> </td>
								
								<td data-label="Tema"> Natal </td>
								
								<!-- botao editar -->
								<td data-label="Editar"><button
										class="btn btn-outline-info" data-title="Editar">
										<svg width="1em" height="1em" viewBox="0 0 16 16"
											class="bi bi-pen-fill" fill="currentColor"
											xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd"
											d="M13.498.795l.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0
											1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 
											.131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.
											708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001z" /></svg>
									</button></td>


								<!-- botao excluir -->
								<td data-label="Excluir"><button
										class="btn btn-outline-danger" data-toggle="modal"
										data-target="#exampleModalCenter" data-id="${prod.id}">

										<svg width="1em" height="1em" viewBox="0 0 16 16"
											class="bi bi-x-circle-fill" fill="currentColor"
											xmlns="http://www.w3.org/2000/svg">
  											<path fill-rule="evenodd"
												d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM5.354 
												4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5
												.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 
												.708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 
												7.293 5.354 4.646z" />
												</svg>
									</button></td>
							</tr>
						<!-- </c:forEach>  -->
					
											<tr>
								<td data-label="Foto"> <img src="https://cdn.vox-cdn.com/thumbor/QmYUW4WDPUe5cakWg1doB00HdBk=/1400x1400/filters:format(jpeg)/cdn.vox-cdn.com/uploads/chorus_asset/file/19656977/5986912410_682fed19e2_b.jpg" > </td>
								
								<td data-label="Tema"> Natal </td>
								
								<!-- botao editar -->
								<td data-label="Editar"><button
										class="btn btn-outline-info" data-title="Editar">
										<svg width="1em" height="1em" viewBox="0 0 16 16"
											class="bi bi-pen-fill" fill="currentColor"
											xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd"
											d="M13.498.795l.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0
											1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 
											.131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.
											708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001z" /></svg>
									</button></td>


								<!-- botao excluir -->
								<td data-label="Excluir"><button
										class="btn btn-outline-danger" data-toggle="modal"
										data-target="#exampleModalCenter" data-id="${prod.id}">

										<svg width="1em" height="1em" viewBox="0 0 16 16"
											class="bi bi-x-circle-fill" fill="currentColor"
											xmlns="http://www.w3.org/2000/svg">
  											<path fill-rule="evenodd"
												d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM5.354 
												4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5
												.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 
												.708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 
												7.293 5.354 4.646z" />
												</svg>
									</button></td>
							</tr>
							<tr>
								<td data-label="Foto" > <img src="https://http2.mlstatic.com/D_NQ_NP_897401-MLB43964956346_112020-O.webp" > </td>
								
								<td data-label="Tema"> Natal </td>
								
								<!-- botao editar -->
								<td data-label="Editar"><button
										class="btn btn-outline-info" data-title="Editar">
										<svg width="1em" height="1em" viewBox="0 0 16 16"
											class="bi bi-pen-fill" fill="currentColor"
											xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd"
											d="M13.498.795l.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0
											1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 
											.131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.
											708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001z" /></svg>
									</button></td>


								<!-- botao excluir -->
								<td data-label="Excluir"><button
										class="btn btn-outline-danger" data-toggle="modal"
										data-target="#exampleModalCenter" data-id="${prod.id}">

										<svg width="1em" height="1em" viewBox="0 0 16 16"
											class="bi bi-x-circle-fill" fill="currentColor"
											xmlns="http://www.w3.org/2000/svg">
  											<path fill-rule="evenodd"
												d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM5.354 
												4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5
												.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 
												.708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 
												7.293 5.354 4.646z" />
												</svg>
									</button></td>
							</tr>
					</tbody>
				</table>
				<hr>
			</div>
			
			<div class="form-group p-3">
			<button class="btn btn-primary float-right">Finalizar pedido</button>
			</div>
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
	<script src="src/js/jQueryMask.js"></script>

</body>


</html>