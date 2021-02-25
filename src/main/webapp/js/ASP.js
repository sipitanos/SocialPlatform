/* Sipitanos Antonis 3272
 * This js file is made for
 * the ajax requests of the ASP page
 * It dynamically loads the signin. signup
 * and profile pages of each client.
 */

var container = document.getElementById('container');
var data = 'signup.html';
var username;
var password;
/*
 * This Function gets called from js file signUpSend and ONLY when a user has
 * successfully created an account.
 * 
 * It is allso called from the HTML page when the user has successfully logged in 
 * 
 */
function redirectionFromSignUP(){
    
    username = document.getElementById('user_name').value;
    password = document.getElementById('password').value;
    data= 'profile.html';
    x();
}



var putData = function(){
    
    document.getElementById('namePlace').innerHTML      = username;
    document.getElementById('passwordPlace').innerHTML  = password;
};

/* 
 * This function is built to support some requests. 
 * Needs a better name I know. will change if I have time.
 */
var x = function(){
    
    var xhr2 = new XMLHttpRequest();

    xhr2.onreadystatechange = function(){

        container.innerHTML = "";

        if(xhr2.readyState === 4 && xhr2.status === 200){
            container.innerHTML= xhr2.responseText;
            if (data === 'profile.html'){
                document.getElementById('signedNamePlace').innerHTML = "User: ";
                document.getElementById('signedName').innerHTML = username;
                document.getElementById('firstbtn').style.visibility = "visible";
                document.getElementById('simplebtn').style.visibility = "visible";
                data='signup.html';
            }
        }
        else if(xhr2.readyState === 5 && xhr2.status !== 200){

             alert(xhr2.status);
        }

    };
    xhr2.open('GET',data);
    xhr2.send();
 };
