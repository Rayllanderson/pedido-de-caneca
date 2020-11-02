$('#foto').on('change', () => {
	var target = document.querySelector("#preview");
    var file = document.querySelector("#foto").files[0];
    var reader = new FileReader();
    reader.onloadend = function() {
        target.src = reader.result;
    };
    if (file) {
        target.style.width = '100%';
        target.style.height = '100%';
        target.style.borderRadius = '1em';//
        $('.text-preview').text('Preview da sua foto') //Não se preocupe se sua imagem parecer distorcida, este é apenas um preview. Ela chegará em qualidade original pra gente!'
        reader.readAsDataURL(file)
    } 
})