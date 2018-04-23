function uploadFile1() {

    var target1 = document.querySelector(".showImage1")

    var file1 = document.querySelector(".fileImage1").files[0];

    var reader1 = new FileReader();

    reader1.onloadend = function () {
        target1.src = reader1.result;

        // Ajax
        $.ajax({
            method: "POST",
            url: "fileUpload",

            data: { minato1: reader1.result }

        }).done(function (response) {

            alert("Success: " + response);

        }).fail(function (xhr, status, errorThrown) {

            alert("Error: " + xhr.responseText);
        });
    };


    if (file1) {
        reader1.readAsDataURL(file1);

    } else {
        target1.src = "";
    }
}

function uploadFile2() {

    var target2 = document.querySelector(".showImage2")

    var file2 = document.querySelector(".fileImage2").files[0];

    var reader2 = new FileReader();

    reader2.onloadend = function () {
        target2.src = reader2.result;

        // Ajax
        $.ajax({
            method: "POST",
            url: "fileUpload",
            data: { minato2: reader2.result }

        }).done(function (response) {

            alert("Success: " + response);

        }).fail(function (xhr, status, errorThrown) {

            alert("Error: " + xhr.responseText);
        });
    };


    if (file2) {
        reader2.readAsDataURL(file2);

    } else {
        target2.src = "";
    }
}

function uploadFile3() {

    var target3 = document.querySelector(".showImage3")

    var file3 = document.querySelector(".fileImage3").files[0];

    var reader3 = new FileReader();

    reader3.onloadend = function () {
        target3.src = reader3.result;

        // Ajax
        $.ajax({
            method: "POST",
            url: "fileUpload",
            data: { minato3: reader3.result }

        }).done(function (response) {

            alert("Success: " + response);

        }).fail(function (xhr, status, errorThrown) {

            alert("Error: " + xhr.responseText);
        });
    };


    if (file3) {
        reader3.readAsDataURL(file3);

    } else {
        target3.src = "";
    }
}