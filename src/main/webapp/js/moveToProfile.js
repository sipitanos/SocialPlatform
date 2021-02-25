/* 
 * Sipitanos Antonis
 */
var username;
var password;
var json;
var container = document.getElementById('container');

/*
 * This function signs the user by taking his username and password
 */
var sign = function (){
    
    username = document.getElementById('user_name_signin').value;
    password = document.getElementById('password_signin').value;
    var passhash = CryptoJS.MD5(password + '224'); // 224 will be the salt.
    

    var payload = 'userName=' + username + '&password=' + passhash;
    
    if( username === null || username === undefined || username ==="" || password === null || password === undefined || password ===""){
        
        document.getElementById('signInMessage').innerHTML = "Empty inputs. check again";
    }
    else{
        
        var redirect = new XMLHttpRequest();
        
        redirect.onload = function(){
            
            if (this.readyState === 4 && this.status === 200) {
                /*
                 * If the authentication is successfull then log him in.
                 * by passing the profile.html value to the data and start the
                 * x function which will move him to the profile page
                 */
                json = JSON.parse(redirect.responseText);

                container.innerHTML = "";
                data = 'profile.html'; // The Variable data is from another js file.
                x(); // This function is in another js file.
                /*
                 * After loging him in populate the page with options button
                 * and with the user name tag  
                 */
                populatepage();
                
            }
            else if(this.readyState === 4 && this.status === 400) {
                
                document.getElementById('signInMessage').innerHTML = "Incorrect password/username combo";

            }
            else if (this.readyState === 4 && this.status !== 400 && this.status !==200){
                
                alert(this.status);
            }
        };
        
        redirect.open('POST', 'signInServlet');
        redirect.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        redirect.send(payload);
    }
};


/*
 * This function makes the user tag and options tag appear.
 * Might change them to make them empty containers and add those when a user 
 * has logged in if I got the time to do it.
 */
function populatepage(){
    
    document.getElementById('signedNamePlace').innerHTML = "User: ";
    document.getElementById('signedName').innerHTML = username;
    document.getElementById('firstbtn').style.visibility = "visible";
    document.getElementById('simplebtn').style.visibility = "visible";
}
/*
 * This function will log out the user.. flush the container and fetch the signin
 * data back to relogin
 */
var logOut = function (){
    username = null;
    password = null;
    
    var signOut = new XMLHttpRequest();
    signOut.onreadystatechange = function(){
            if (this.readyState === 4 && this.status === 200) {
                container.innerHTML = "";
                
                document.getElementById('signedNamePlace').innerHTML    = "";
                document.getElementById('signedName').innerHTML         = "";
                document.getElementById('firstbtn').style.visibility    = "hidden";
                document.getElementById('simplebtn').style.visibility   = "hidden";
                
                container.innerHTML = signOut.responseText;
               
            }
            else if(this.readyState === 4 && this.status !== 200) {
                
                alert(this.status);
            }
        };
        signOut.open('GET', 'signin.html');
        signOut.send();
        
    var deleteSession = new XMLHttpRequest();
    deleteSession.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            console.log('Session terminated successfully');
            
        }
        else if (this.readyState === 4 && this.statuss !== 200){
            alert('Could not terminate session: ' + this.status);
        }  
    };
    deleteSession.open('POST','deleteSessionServlet');
    deleteSession.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    deleteSession.send();
    
};

/*
 * This function will only work after the user has logged in 
 * and will delete him based on his unique username
 */
var deleteUservar = function(){
    
    var payload = "userName="+username;
    var deleteReq = new XMLHttpRequest();
    deleteReq.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 204){
            
            alert("User deleted Successfully");
            logOut();
        }
        else if(this.readyState === 4 && this.status !== 200) {
            
                alert('ERROR with code' + this.status);
        }
        
    };
    
    deleteReq.open('POST','deleteUserServlet');
    deleteReq.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    deleteReq.send(payload); 
};

/*
 *  Once the user has logged in this function will get all his data
 *  from the JSON response file
 */
var fetchData = function(){
    
    document.getElementById('nameSpan').innerHTML       = json.firstName;
    document.getElementById('lnameSpan').innerHTML      = json.lastName;
    document.getElementById('emailSpan').innerHTML      = json.email;
    document.getElementById('countrySpan').innerHTML    = json.country;
    document.getElementById('citySpan').innerHTML       = json.town;
    document.getElementById('occupationSpan').innerHTML = json.occupation;
    document.getElementById('interestSpan').innerHTML   = json.interests;
    document.getElementById('generalSpan').innerHTML    = json.info;
    document.getElementById('addrSpan').innerHTML       = json.address;
    document.getElementById('generalSpan1').innerHTML   = json.registeredSince;
    document.getElementById('DoBSpan').innerHTML        = json.birthDate;
    
    var getPosts = new XMLHttpRequest();
   
    getPosts.onreadystatechange = function(){
        
        if (this.readyState === 4 && this.status === 200){
           // console.log(getPosts.responseText);
            document.getElementById('showPosts').innerHTML = "";
            document.getElementById('showPosts').innerHTML = getPosts.responseText;
        }
        
    };
    getPosts.open('POST','getPostsServlet');
    getPosts.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    getPosts.send('username=' + json.username );
   
};

/*
 * This functions returns the whole database to the user.
 */
var seeAll = function(){
    
    var see = new XMLHttpRequest();
    
    see.onreadystatechange = function(){
        
        if (this.readyState === 4 && this.status === 200){
            
            document.getElementById('dataBase').innerHTML = see.responseText;
        }
        else if (this.readyState === 4 && this.status !==200){
            
            alert("Fetch All from DB error: " + this.status);
        }
        
    };
    
    see.open('POST','seeEverybodyServlet');
    see.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    see.send(); 
};

var updateData = function(){
  
    var update = new XMLHttpRequest();
    
    update.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200){
            document.getElementById("update-container").innerHTML = "";
            document.getElementById("update-container").innerHTML = update.responseText;
            insertPrevData();
        }
        else if (this.readyState === 4 && this.status !== 200){
            alert('Update Information Error');
        }
    };
    update.open('GET', 'dataUpdater.html');
    update.send();
};

var updateFromDataBase = function(){
    
    userName = json.username;
    email = document.getElementById('e_mail').value;
    password = document.getElementById('password').value;
    firstName = document.getElementById('first_name').value;
    lastName = document.getElementById('surname').value;
    occupation = document.getElementById('work_name').value;
    town = document.getElementById('town_name').value;
    interests = document.getElementById('interests').value;
    general = document.getElementById('general').value;
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
    
    var payload2= 'userName='+ userName +'&email='+ email +'&firstName='+ firstName +'&lastName='+ 
            lastName+'&password='+password+'&gender='+ gender +'&birthDate='+birthDate+'&country='+country+'&town='+town + '&address=' + address +
            '&occupation='+occupation+'&interests='+interests+'&general='+general;
    
    var updater = new XMLHttpRequest();
    
    updater.onreadystatechange = function(){
        if (updater.readyState === 4 && updater.status === 200){
            
            json = JSON.parse(updater.responseText); /* json is also declared in another js file */
            //console.log(json);
 
            //document.getElementById('signUpSendMessage').innerHTML = "Account Created Successfully! Redirecting...";
           // alert('USER UPDATED SUCCESSFULLY');
            document.getElementById('update-container').innerHTML = "<p> You can even update your data if you check the options! </p>";
            fetchData();
            //redirectionFromSignUP();    /* This function is written in another js file */
        }
        else if (updater.status===400){
            
            document.getElementById('signUpSendMessage').innerHTML = "Server ERROR 400. Plase check your inputs";
        }
        else if (updater.readyState === 4 && updater.status !== 200 && updater.status !== 400){
            
            alert('Request failed with error ' + updater.status );  
        }
    };
    updater.open('POST','updateDataServlet');
    updater.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    updater.send(payload2); 
    
};

//Inserts the prev info so the user can change them however he wants
var insertPrevData = function(){
    
    document.getElementById('e_mail').value = json.email;
    document.getElementById('password').value  = json.password;
    document.getElementById('password_confirmation').value  = json.password;
    document.getElementById('first_name').value       = json.firstName;
    document.getElementById('surname').value      = json.lastName;
    document.getElementById('country').value    = json.country;
    document.getElementById('town_name').value       = json.town;
    document.getElementById('work_name').value = json.occupation;
    document.getElementById('interests').innerHTML   = json.interests;
    document.getElementById('general').innerHTML    = json.info;
    document.getElementById('address').value       = json.address;
      
};