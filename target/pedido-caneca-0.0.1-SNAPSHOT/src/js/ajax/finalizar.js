

$("#finalizar").on('click', function() {
	var id = $('#clientId').val();
	var nome = $('#nome').val();
	var telefone = $('#telefone').val();

	if (telefone.length < 14) {
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
				
			}
		})
	}
});

