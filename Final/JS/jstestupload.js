var applyFiles = function() {
    if (this.files.length <= 0) {
      $('.choosen').html('No file choosen.');
      $('.guide').show();
    } else {
      $('.choosen').empty();
      $('.guide').hide();
      
      for (var i = 0; i < this.files.length; ++i) {
        $('.choosen').append($('<li>').html(this.files[i].name));
      }
    }
  }
  
  $('input[type="file"]').each(function() {
    applyFiles.call(this);
  }).change(function() {
    applyFiles.call(this);
  });