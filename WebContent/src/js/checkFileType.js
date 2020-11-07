
$("#file").on("change", function() {
	var file = document.querySelector("#file").files[0];
	var myFormData = new FormData();
    myFormData.append('pictureFile', file);

   $.ajax({
     url: 'order?action=check-file-type',
     type: 'POST',
     cache:false,
     processData: false, // important
     contentType: false, // important
     data: myFormData,
     success:function(){
         console.log("success");
     },
     error: function(){
         console.log("error");
         alertBootstrap("O tipo do arquivo não é compatível.", "alert alert-danger", "Ops...");
		 document.getElementById("file").value = "";
		 $('#div-preview').hide();
     }
   });

});


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