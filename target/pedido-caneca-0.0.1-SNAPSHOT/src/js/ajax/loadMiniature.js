$('.alert').hide();

function loadMiniature(){
	$.ajax({
     url: 'carrinho?action=load-miniature',
     type: 'GET',
     success:function(){
        // $('#img').load('src/jsp/miniature.jsp').html;
			$.get("carrinho", function(response) {  
				$("#start").html($(response).find("data").html());
			});
		console.log('Success')
     },
     error: function(){
     }
   });
}

loadMiniature();

