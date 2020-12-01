var nameDiv = "#filediv2";
var count = 0;
var showPlusButton = true;

// ------------------ on change ------------------
$('#file1').on('change', function() {
	if (showPlusButton) {
		$('#plus').show();
		showPlusButton = false;
	}
	count++;
	checkFileType('file1', 1)
});

$('#file2').on('change', function() {
	checkFileType('file2', 2)

});

$('#file3').on('change', function() {
	checkFileType('file3', 3)
});


// ------------------- on click ---------------------

$('#plus').on("click", function() {
	$(nameDiv).show();
	count++;
	nameDiv = "#filediv3"
	if (count >= 3) {
		$('#plus').hide();
	}
})

// --------------------- remover -------------------
$('#remove1').on('click', function() {
	excluir(1)
});
$('#remove2').on('click', function() {
	excluir(2)

});
$('#remove3').on('click', function() {
	excluir(3)
});


function excluir(index) {
	let fileName = `#file${index}`
	let divName = `#div-preview${index}`
	$(fileName).val("");
	$(divName).hide();
}


 // --------------------- preview ---------------------
function preview(index) {
	var target = document.querySelector("#preview" + index);
	var file = document.querySelector("#file" + index).files[0];
	var reader = new FileReader();
	reader.onloadend = function() {
		target.src = reader.result;
	};
	if (file) {
		$('#div-preview' + index).show();
		target.style.width = '100%';
		target.style.height = '100%';
		target.style.borderRadius = '1em'; //
		$('.text-preview').text('Preview da sua foto')
		reader.readAsDataURL(file)
		//  hasChangedImage(true)
	}
}


function checkFileType(name, numero) {
	var file = document.querySelector("#" + name).files[0];
	$.ajax({
		url: 'order?action=check-file-type',
		type: 'POST',
		cache: false,
		data: {'file-type': file.type},
		success: function() {
			console.log("success");
			preview(numero)
		},
		error: function() {
			console.log("error");
			alertBootstrap("O tipo do arquivo não é compatível.", "alert alert-danger", "Ops...");
			document.getElementById(name).value = "";
			excluir(numero)
			return;
		}
	});
};