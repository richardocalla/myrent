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
        type : 'email',
        prompt : 'Please enter your email'
      } ]
    },
    password : {
      identifier : 'password',
      rules : [ {
        type : 'empty',
        prompt : 'Please enter your password'
      } ]
    },
    address1 : {
      identifier : 'address1',
      rules : [ {
        type : 'empty',
        prompt : 'Please enter the first line of your address'
      } ]
    },
    address2 : {
      identifier : 'address2',
      rules : [ {
        type : 'empty',
        prompt : 'Please enter the second line of your address'
      } ]
    },
    city : {
      identifier : 'city',
      rules : [ {
        type : 'empty',
        prompt : 'Please enter your city'
      } ]
    },
    county : {
      identifier : 'county',
      rules : [ {
        type : 'empty',
        prompt : 'Please enter your county'
      } ]
    },
    rent : {
        identifier : 'rent',
        rules : [ {
          type : 'integer',
          prompt : 'Please enter valid value for rent'
        } ]
      },
    conditions : {
      identifier : 'conditions',
      rules : [ {
        type : 'checked',
        prompt : 'You must agree to the terms and conditions'
      } ]
    }
  }
});