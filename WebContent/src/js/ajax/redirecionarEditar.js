
var id1 = null;
$('#edit-modal').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget)
	var id = button.data('id')
	id1 = id;
	$('#btn-edit').click();
});

$('#btn-edit').on('click', function() {
	$('#edit-modal').modal('hide')
	$.ajax({
		method: 'GET',
		url: 'order?action=edit',
		data: { id: id1 },
		success: function() {
			$('#edit-modal').modal('hide')
			window.location.href = "edit-order.jsp";
		}, error: function() {
			$('#edit-modal').modal('hide')
			alertBootstrap("Não foi possível realizar sua solicitação. Recarregue a página e tente novamente", "alert alert-danger", "Erro")
		}
	});
})