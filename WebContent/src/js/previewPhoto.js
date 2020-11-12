
function checkFileType(file, e) {
	var file1 = document.querySelector("#files").files[0];
	var myFormData = new FormData();
	myFormData.append('file', file1);

	$.ajax({
		url: 'order?action=check-file-type',
		type: 'POST',
		cache: false,
		processData: false, // important
		contentType: false, // important
		data: myFormData,
		success: function() {
			console.log("success");

			$("<span class=\"pip\">" +
				"<img class=\"imageThumb img-fluid\" src=\"" + e.target.result + "\" title=\"" + file.name + "\"/>" +
				"<br/><span class=\"remove text-danger\"><i class=\"fas fa-times\"> <span class=\"items\" >Remover foto</span></i></span>" +
				"</span>").insertAfter("#files");
			$(".remove").click(function() {
				$(this).parent(".pip").remove();
			});

		},
		error: function() {
			console.log("error");
			alertBootstrap("O tipo do arquivo não é compatível.", "alert alert-danger", "Ops...");
			return;
		}
	});

};



$(document).ready(function() {
	if (window.File && window.FileList && window.FileReader) {
		$("#files").on("change", function(e) {
			var files = e.target.files,
				filesLength = files.length;
			for (var i = 0; i < filesLength; i++) {
				var f = files[i]
				var fileReader = new FileReader();

				if (!/\.(jpe?g|png|gif)$/i.test(f.name)) {
					return alertBootstrap("O tipo do arquivo não é compatível.", "alert alert-danger", "Ops...");
				} else {

					fileReader.onload = (function(e) {
						var file = e.target;
						checkFileType(file, e)
					});

					fileReader.readAsDataURL(f);
				}
			}
		});
	} else {
		alert("Your browser doesn't support to File API")
	}
});
