
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
