function uploadFile() {

	var target = document.querySelector(".showImage")

	var file = document.querySelector("input[type=file]").files[0];

	var reader = new FileReader();

	reader.onloadend = function () {
		target.src = reader.result;
	};

	// Ajax
	$.ajax({
		method: "POST",
		url: "fileUpload",
		data: { fileUpload: reader.result }
	}).done(function (response) {
		alert("Success: " + response);
	}).fail(function (xhr, status, errorThrown) {
		alert("Error: " + xhr.responseText);
	});

	if (file) {
		reader.readAsDataURL(file);
	} else {
		target.src = "";
	}
}



