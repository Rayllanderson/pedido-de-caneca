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
