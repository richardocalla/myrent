$(document).ready(function() {
  // Dynamically sets the state of the radio button pair
  var $isrented = $('#isrented').val();
  var $radios = $('input:radio[name=rented]');
  if ($isrented === 'yes') {
    $radios.filter('[value=yes]').prop('checked', true);
  } else {
    $radios.filter('[value=no]').prop('checked', true);
  }
});