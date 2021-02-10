$('.alert').hide();

var id1 = null;
$('#exampleModalCenter').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget)
	var id = button.data('id') // Extract info from data-* attributes
	id1 = id;
});

$('#btn-delete').on('click', function() {
	$(this).prop('disabled', true);
	$(this).html('Excluindo...');
	$.ajax({
		method: "GET",
		url: "carrinho?action=delete",
		data: { id1: id1 },
		success: function() {
			$('#exampleModalCenter').modal('hide')
			alertBootstrap("Caneca Excluída com Sucesso!", 'alert alert-success', "Sucesso")
			$.get("carrinho", function(responseXml) {
				$("#start").html($(responseXml).find("data").html());
				enableButton()
			});
		}, error: function() {
			$('#exampleModalCenter').modal('hide')
			alertBootstrap("Ocorreu um erro ao deletar", 'alert alert-danger', "Ops..")
			enableButton()
		}
	});
})

function enableButton() {
	$("#btn-delete").prop('disabled', false);
	$("#btn-delete").html('Excluir');
}