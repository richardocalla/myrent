$( document ).ready(function() {
  // initialize the controls in the input data template and validate residence
  // type
  $('.ui.dropdown').dropdown();
  $('.ui.checkbox').checkbox();
  $('.ui.form')
      .form({
            residenceType: {
              identifier: 'residence.residenceType',
              rules: [{
                type: 'empty',
                prompt: 'Please select residence type'
              }]
            },
            rent: {
              identifier: 'residence.rent',
              rules: [{
                type: 'empty',
                prompt: 'Please select rent'
              }]
            }, numberBathrooms: {
              identifier: 'residence.numberBathrooms',
              rules: [{
                type: 'empty',
                prompt: 'Please select number of bathrooms'
              }]
            }, numberBedroom: {
              identifier: 'residence.numberBedrooms',
              rules: [{
                type: 'empty',
                prompt: 'Please select number of bedrooms'
              }]
            }, area: {
              identifier: 'residence.area',
              rules: [{
                type: 'empty',
                prompt: 'Please select area of residence'
              }]
            }
          },

          {
            onSuccess : function() {
              submitForm();
              return false;
            }
          }
      )
  function submitForm() {
    var formData = $('.ui.form.segment input').serialize();
    $.ajax({
      type: 'POST',
      url: '/inputdata/datacapture',
      data: formData,
      success: function(response) {
        console.log("notification: " + response.inputdata);
        $('#notification').html(response.inputdata);
      }
    });
  }
});