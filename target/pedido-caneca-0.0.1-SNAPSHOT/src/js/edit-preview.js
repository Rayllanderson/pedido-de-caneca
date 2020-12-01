var nameDiv = "#filediv2";
var count = 0; //contar quantos plus button tem. se for maior que 3, o plus button nao é mostrado mais
var showPlusButton = true;

// ------------------ on change ------------------
$('#file1').on('change', function() {
	if (showPlusButton) {
		$('#plus').show();
		count++;
		showPlusButton = false;
	}
	
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
	$('#id-' + index).val('null')
	hasChangedImage(true)
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
	}
}


function checkFileType(name, numero) {
	var file = document.querySelector("#" + name).files[0];
	hasChangedImage(true);
	$('#id-' + numero).val('null')
	$.ajax({
		url: 'order?action=check-file-type',
		type: 'POST',
		data: { 'file-type': file.type },
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


function setImage(imgs) {
	hasChangedImage(false);
	var img1 = null;
	var img2 = null;
	var img3 = null;
	if (imgs[0]) {
		img1 = imgs[0].base64;
		var id1 = imgs[0].id;
		setImageToDiv(img1, 1, id1);
	}

	if (imgs[1]) {
		img2 = imgs[1].base64;
		var id2 = imgs[1].id;
		setImageToDiv(img2, 2, id2);
	}

	if (imgs[2]) {
		img3 = imgs[2].base64;
		var id3 = imgs[2].id;
		setImageToDiv(img3, 3, id3);
	}
	
	if (img1) {
		$('#plus').show();
		showPlusButton = false;
	}
	if (img2) {
		nameDiv = "#filediv3";
	}

}

function setImageToDiv(img, index, id) {
	var target = document.querySelector("#preview" + index);
	if (img) {
		$('#id-' + index).val(id)
		$('#filediv' + index).show();
		$('#div-preview' + index).show();
		target.src = img;
		target.style.width = '100%';
		target.style.height = '100%';
		target.style.borderRadius = '1em'; //
		$('.text-preview').text('Preview da sua foto')
		count++;
	}
}