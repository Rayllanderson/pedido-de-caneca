function loadMiniature(){
	$.ajax({
     url: 'carrinho?action=load-miniature',
     type: 'GET',
     success:function(){
        // $('#img').load('src/jsp/miniature.jsp').html;
			$.get("carrinho.jsp", function(responseXml) {          
				$("#start").html($(responseXml).find("data").html());
			});
		console.log('Success')
     },
     error: function(){
     }
   });
}

loadMiniature();

