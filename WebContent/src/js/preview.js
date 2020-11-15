    var nameDiv = "#filediv2";
    var count = 0;
    var spanCount = $('.count');
    var i = 0;
    spanCount.text(i)
    var showPlusButton = true;
    $('#file1').on('change', function() {
        if (showPlusButton) {
            $('#plus').show();
            showPlusButton = false;
        }
        count++;
        i++
        spanCount.text(i)
        preview(1);
    });

    $('#file2').on('change', function() {
        preview(2);
        i++
        spanCount.text(i)

    });

    $('#file3').on('change', function() {
        preview(3);
        i++
        spanCount.text(i)
    });

    $('#plus').on("click", function() {
        $(nameDiv).show();
        count++;
        nameDiv = "#filediv3"
        if (count >= 3) {
            $('#plus').hide();
        }
    })

    $('#remove1').on('click', function() {
        excluir(1)
        i--;
        spanCount.text(i)
    });
    $('#remove2').on('click', function() {
        excluir(2)
        i--;
        spanCount.text(i)
    });
    $('#remove3').on('click', function() {
        excluir(3)
        i--;
        spanCount.text(i)
    });

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
                //  hasChangedImage(true)
        }
    }

    function excluir(index) {
        let fileName = `#file${index}`
        let divName = `#div-preview${index}`
        console.log(fileName)
        $(fileName).val("");
        $(divName).hide();
    }