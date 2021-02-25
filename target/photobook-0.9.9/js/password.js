/**************************
 *	Sipitanos Antonis *
 *	csd 3272          *
 *	Exercise 2        *
 **************************/
var check = function() {
	'use strict';
  if (document.getElementById('password').value === document.getElementById('password_confirmation').value) {

    document.getElementById('password_message').innerHTML = '';
  } 
  else {

    document.getElementById('password_message').innerHTML = 'Passwords do not match!';
  }
};