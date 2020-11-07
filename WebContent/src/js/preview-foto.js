$(".alert").hide();

$('#file').on('change', () => {
	var target = document.querySelector("#preview");
    var file = document.querySelector("#file").files[0];
    var reader = new FileReader();
    reader.onloadend = function() {
        target.src = reader.result;
    };
    if (file) {
		$('#div-preview').show();
        target.style.width = '100%';
        target.style.height = '100%';
        target.style.borderRadius = '1em';//
        $('.text-preview').text('Preview da sua foto') //Não se preocupe se sua imagem parecer distorcida, este é apenas um preview. Ela chegará em qualidade original pra gente!'
        reader.readAsDataURL(file)
    } 
});

$('#excluir').on('click', () =>{
	document.getElementById("file").value = "";
	$('#exampleModalCenter').modal('hide');
	$('#div-preview').hide();
})

/*function LoadData(){
    $("#loading-image").show();
    $.ajax({
          url: yourURL,
          cache: false,
          success: function(html){
            $("Your div id").append(html);
          },
          complete: function(){
            $("#loading-image").hide();
          }
        });
}*/