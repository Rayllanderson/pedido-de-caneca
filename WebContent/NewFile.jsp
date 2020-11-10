<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
				
				<form class="upload-box" action="email1" method="post">
				    <button class="btn btn-primary">Send</button>
				</form>


<!-- JQuery -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">

/*
$(document).ready(function (e) {
    $('#upload-form').on('submit',(function(e) {
        e.preventDefault();
        var formData = new FormData(this);
		var nome = $('#nome').val();
		
        $.ajax({
            type:'POST',
            url: $(this).attr('action'),
            data: {formData: formData,
            		nome: nome},
            cache:false,
            contentType: false,
            processData: false,
            success:function(data){
                console.log("success");
                console.log(data);
            },
            error: function(data){
                console.log("error");
                console.log(data);
            }
        });
    }));
 */   
    
    
    $("#file").on("change", function() {
    	 var file = document.querySelector("#file").files[0];
    	var myFormData = new FormData();
        myFormData.append('pictureFile', file);

        $.ajax({
          url: 'testea',
          type: 'POST',
          cache:false,
          processData: false, // important
          contentType: false, // important
          data: myFormData,
          success:function(data){
              console.log("successf");
              console.log(data);
          },
          error: function(data){
              console.log("error");
              console.log(data);
          }
        });

    });
</script>
</body>
</html>