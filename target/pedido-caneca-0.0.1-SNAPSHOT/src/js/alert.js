$('.alert').hide();
function alertBootstrap(msg, classe, titulo) {
	$(".alert").show();

	const title = document.getElementById('titulo');

	if(titulo != undefined){	
		if(title != null){
		title.innerHTML = titulo
		}
	}else{
		title.innerHTML = ''
	}
	document.getElementById('alertMsg').innerHTML = msg;
	document.getElementById("success-alert").className = classe;
	$("#success-alert").fadeTo(2500, 500).slideUp(500, function() {
		$("#success-alert").slideUp(500);
	});
}

function alertModal(msg, classe) {
	$("#alertE").show();
	document.getElementById('alertMsgE').innerHTML = msg;
	document.getElementById("alertE").className = classe;
	$("#alertE").fadeTo(2700, 500).slideUp(500, function() {
		$("#alertE").slideUp(500);
	});
}