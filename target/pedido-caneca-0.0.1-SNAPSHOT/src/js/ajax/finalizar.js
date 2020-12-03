$(".alert").hide();
$('#alertE').hide();

const telefoneSize = 14;

$("#finalizar").on('click', function() {
	$(this).prop('disabled', true);
	$(this).html('Enviando...');
	var id = $('#clientId').val();
	var nome = $('#nome').val();
	var telefone = $('#telefone').val();

	if (telefone.length < telefoneSize) {
		$('#telefone').focus();
		alertModal('número inválido', 'alert alert-danger');
	} else {
		$.ajax({
			url: 'order?action=finish',
			type: 'POST',
			data: { 'id': id, 'telefone': telefone, 'nome': nome },
			success: function() {
				location.href = "sucess.jsp"
				console.log('Success')
			},
			error: function() {
				alertModal('Não é possível finalizar sem canecas no carrinho', 'alert alert-danger');
			}
		})
	}
});

