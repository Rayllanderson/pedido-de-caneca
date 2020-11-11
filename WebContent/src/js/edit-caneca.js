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

var target = document.querySelector("#preview");
function setImage(image){
	if (image){
	hasChangedImage(false);
	target.src = image;
	$('#div-preview').show();
	$('.text-preview').text('Preview da sua foto') 
	target.style.width = '100%';
	target.style.height = '100%';
	target.style.borderRadius = '1em';//
	}
}
