/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var container = document.getElementById('container');
/*
 * This function checks to see it there is a Session 
 */
var getLastSession = function(){
  
  var sess = new XMLHttpRequest();
  
  sess.onreadystatechange = function(){

        if(sess.readyState === 4 && sess.status === 200){
            console.log('Previous session active... fetching data');

            document.getElementById('container').innerHTML="";
            document.getElementById('container').innerHTML="<h1> Fetching your data... it will only take a moment!</h1>";
            json = JSON.parse(sess.responseText);
            console.log(json);
            username = json.username;
            password = json.password;
            /* Over here we load the prev Session */
            lSession();
        }
        else if(sess.readyState === 4 && sess.status === 404){
            console.log('NO previous Session Detected because the server returned 404');
        }

    };
    sess.open('POST','SignInSession');
    sess.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    sess.send();
    
};

/*
 * This function loads the previous Session
 */
var lSession = function(){

    var payload = 'userName=' + username + '&password=' + password;
         
    var loadSession = new XMLHttpRequest();

        loadSession.onreadystatechange = function(){
            if (loadSession.readyState === 4 && loadSession.status === 200){
                json = JSON.parse(loadSession.responseText);
                data = 'profile.html'; // The Variable data is from another js file.
                username = json.username;
                x(); // This function is in another js file.
            }
            else if(loadSession.readyState === 4 && loadSession.status === 404){

                alert('Something went wront.. please contact admin');
            }

        };
        loadSession.open('POST','signInServlet');
        loadSession.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        loadSession.send(payload);
};