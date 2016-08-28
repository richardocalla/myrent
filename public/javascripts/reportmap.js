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
      );
  let
      circle;
  function requestReport() {
    const center = circle.getCenter();
    const
        latcenter = center.lat().toString();
    const
        lngcenter = center.lng().toString();
    const
        radius = circle.getRadius().toString();
    $('#radius').val(radius);
    $('#latcenter').val(latcenter);
    $('#lngcenter').val(lngcenter);
  }

  function initialize() {
    const
        center = new google.maps.LatLng(53.347298, -6.268344);
    const
        initRadius = 10000;
    const
        mapProp = {
          center : center,
          zoom : 8,
          mapTypeId : google.maps.MapTypeId.ROADMAP
        };
    const
        mapDiv = document.getElementById('map_canvas');
    const
        map = new google.maps.Map(mapDiv, mapProp);
    mapDiv.style.width = '500px';
    mapDiv.style.height = '500px';

    circle = new google.maps.Circle({
      center : center,
      radius : initRadius,
      strokeColor : '#0000FF',
      strokeOpacity : 0.4,
      strokeWeight : 1,
      fillColor : '#0000FF',
      fillOpacity : 0.4,
      draggable : true
    });
    circle.setEditable(true);// allows radius be dragging anchor
    // point
    circle.setMap(map);
  }

  google.maps.event.addDomListener(window, 'load', initialize);
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