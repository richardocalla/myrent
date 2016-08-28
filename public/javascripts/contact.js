$('.ui.form').form({
  fields : {
    firstName : {
      identifier : 'firstName',
      rules : [ {
        type : 'empty',
        prompt : 'Please enter your first name'
      } ]
    },
    lastName : {
      identifier : 'lastName',
      rules : [ {
        type : 'empty',
        prompt : 'Please enter your last name'
      } ]
    },
    email : {
      identifier : 'email',
      rules : [ {
        type : 'empty',
        prompt : 'Please enter your email'
      } ]
    },
    messageTxt : {
      identifier : 'messageTxt',
      rules : [ {
        type : 'empty',
        prompt : 'Please enter your message'
      } ]
    }
  }
});