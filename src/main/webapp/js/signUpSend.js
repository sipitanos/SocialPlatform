/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var userName;
var firstName;
var lastName;
var email;
var password;
var birthDate;
var country;
var town;
var occupation;
var interests;
var general;
var gender;
var address;

/*
    Validates username Message
 */
function signUpUserNameMessageFun(){
    
    userName = document.getElementById('user_name').value;
    
    if (/^[0-9A-Za-z].{8,}$/.test(userName)){
        
        document.getElementById('signUpUserNameMessage').innerHTML="";
    }
    else{
        
        document.getElementById('signUpUserNameMessage').innerHTML="wrong username input, check again!";
    }
    
}
/*
    Validates email Message
 */
function signUpEmailFun(){
    
    email = document.getElementById('e_mail').value;
    
    if (/^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/.test(email)){
        
        document.getElementById('signUpEmailMessage').innerHTML="";
    }
    else{
        
        document.getElementById('signUpEmailMessage').innerHTML="wrong email input, check again!";
    }
}
/*
    Validates password Message
 */
function signUpPasswordFun(){
    
    password = document.getElementById('password').value;
    
    if (/^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[~!@#$%^&*]).{8,10}$/.test(password)){
        
        document.getElementById('signUpPasswordMessage').innerHTML="";
    }
    else{
        
        document.getElementById('signUpPasswordMessage').innerHTML="wrong password input, check again!";
    }
    
}
/*
    Validates First Name Message
 */
function signUpFirstNameFun(){
    
    firstName = document.getElementById('first_name').value;
    
    if (/^[A-Z]+[a-z]{2,14}$/.test(firstName)){
        
        document.getElementById('signUpFirstNameMessage').innerHTML="";
    }
    else{
        
        document.getElementById('signUpFirstNameMessage').innerHTML="wrong name input, check again!";
    }
}
/*
    Validates Last Name Message
 */
function signUpLastNameFun(){
    
    lastName = document.getElementById('surname').value;
    
    if (/^[A-Z]+[a-z]{2,14}$/.test(lastName)){
        
        document.getElementById('signUpLastNameMessage').innerHTML="";
    }
    else{
        
        document.getElementById('signUpLastNameMessage').innerHTML="wrong surname input, check again!";
    }
}
/*
    Validates Work Message
 */
function signUpWorkMessageFun(){
    
    occupation = document.getElementById('work_name').value;
        
    if (/^[A-Z]+[a-z]{2,19}$/.test(occupation)){
        
        document.getElementById('signupWorkMessage').innerHTML="";
    }
    else{
        
        document.getElementById('signupWorkMessage').innerHTML="wrong work input, check again!";
    }
}
/*
    Validates Town Message
 */
function signUpTownMessageFun(){
    
    town = document.getElementById('town_name').value;
        
    if (/^[A-Z]+[a-z]{2,19}$/.test(town)){
        
        document.getElementById('signupTownMessage').innerHTML="";
    }
    else{
        
        document.getElementById('signupTownMessage').innerHTML="wrong town input, check again!";
    }
}
/*
    Validates Interests Message
 */
function signUpInterestsMessageFun(){
    
    interests = document.getElementById('interests').value;
        
    if (interests.length <100){
        
        document.getElementById('signUpInterestsMessage').innerHTML="";
    }
    else{
        
        document.getElementById('signUpInterestsMessage').innerHTML="wrong Interests input, check again!";
    }
}
/*
    Validates General Message
 */
function signUpGeneralMessageFun(){
    
    general = document.getElementById('general').value;
        
    if (general.length <500){
        
        document.getElementById('signUpGeneralMessage').innerHTML="";
    }
    else{
        
        document.getElementById('signUpGeneralMessage').innerHTML="wrong General input, check again!";
    }
}

/* Ajax Request that sends the data
 * to the server 
 * and waits for a response if the 
 * user is created or someone exists
 * or if there was a problem
 * */
function redirectToProfile(){
    
    var container = document.getElementById('container');
    
    container.innerHTML = "";
    container.innerHTML = "ACCOUNT CREATED";
}

/*
 * This function will send the data to the servlet to connect with the DB
 * in order to create a new User if the data are correct and there is no such
 * user in the database already.
 */
function sendAjaxPOST(){
    
    document.getElementById('signUpSendMessage').innerHTML = "";
    document.getElementById('signUpSendMessage').innerHTML = "Creating new user.. please wait!";
    
    birthDate = document.getElementById('bdate').value;
    country   = document.getElementById('country').value;
    address   = document.getElementById('address').value;
    
    if (document.getElementById('male').checked) {
        
        gender = document.getElementById('male').value;
    }
    else if(document.getElementById('female').checked) {
        
        gender = document.getElementById('male').value;
    }
    else{
        gender ="n/a";
    }
    
    /*
     * Create a hash from the password and add the salt
     */
    password = CryptoJS.MD5(password + '224'); // 224 will be the salt.
    alert("hashed password will be = " + password);
    var payload = 'userName='+ userName +'&email='+ email +'&firstName='+ firstName +'&lastName='+ 
            lastName+'&password='+password+'&gender='+ gender +'&birthDate='+birthDate+'&country='+country+'&town='+town + '&address=' + address +
            '&occupation='+occupation+'&interests='+interests+'&general='+general;
    
    var request= new XMLHttpRequest();
    
    request.onreadystatechange = function(){
        if (request.readyState === 4 && request.status === 200){
            
            json = JSON.parse(request.responseText); /* json is also declared in another js file */
 
            document.getElementById('signUpSendMessage').innerHTML = "Account Created Successfully! Redirecting...";

            redirectionFromSignUP();    /* This function is written in another js file */
        }
        else if(request.status ===409){
            
            document.getElementById('signUpSendMessage').innerHTML = "User Already Exists";
        }
        else if (request.status===400){
            
            document.getElementById('signUpSendMessage').innerHTML = "Server ERROR 400. Plase check your inputs";
        }
        else if (request.readyState === 4 && request.status !== 200 && request.status !== 400){
            
            alert('Request failed with error ' + request.status );  
        }
    };
    
    request.open('POST','SignUpValidation');
    request.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    request.send(payload); 
}


