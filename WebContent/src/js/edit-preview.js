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
	var myFormData = new FormData();
	myFormData.append('file', file);
	hasChangedImage(true);
	$.ajax({
		url: 'order?action=check-file-type',
		type: 'POST',
		cache: false,
		processData: false, // important
		contentType: false, // important
		data: myFormData,
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


function setImage(imgs){
	hasChangedImage(false);
	var img1 = imgs[0];
	var img2 = imgs[1];
	var img3 = imgs[2];
	setImageToDiv(img1, 1);
	setImageToDiv(img2, 2);
	setImageToDiv(img3, 3);
	if(img1){
		$('#plus').show();		
		showPlusButton = false; 
	}
	if(img2){
		nameDiv = "#filediv3";
	}

}

function setImageToDiv(img, index){
		var target = document.querySelector("#preview" + index);
		if (img) {
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