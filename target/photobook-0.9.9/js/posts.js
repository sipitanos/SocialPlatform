/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var payload1;
var text;
var image;
var timestamp;
var location;
var locjson;
var comment;
var commentPayload;

/*
 * This function gets the image makes it as Base64 and lets me preview it
 */
var imm = function(){
    var file = document.getElementById('imgInp').files[0];
    var fr = new FileReader();

    fr.readAsDataURL(file);
    image = fr;

    fr.onload = function(e) {
      var img = document.getElementById('blah');
      img.src = this.result;
      image = this.result;
    };
};

/*
 *  Create the payload.
 */
var makePost = function(){
    text = document.getElementById('postText').value;
    // I have gotten the image from the prev function
    var currentdate = new Date(); 
    timestamp =   currentdate.getDate() + "/" + (currentdate.getMonth()+1)  + "/"  + currentdate.getFullYear() + " @ "   + currentdate.getHours() + ":"   + currentdate.getMinutes() + ":" + currentdate.getSeconds();
        
    console.log(timestamp);
    console.log(text);
    console.log('THE JSON WILL BE AFTER HERE');
    console.log(locjson);

    payload1 = "username=" + json.username + "&text=" + text + "&image=" + image+ "&timestamp=" + timestamp + "&lon=" + locjson.lon + "&lat=" + locjson.lat;
    console.log("Query will be = "+ payload1);
    var sendPost = new XMLHttpRequest();
    sendPost.onreadystatechange = function(){
        if (sendPost.readyState === 4 && sendPost.status === 200){
            
            console.log('post submitted successfully');
        }
        else if(sendPost.readyState === 4 && sendPost.status !== 200){
            
            console.log("ERROR with post : " + sendPost.status);
        }
    };
    sendPost.open('POST','newPostServlet');
    sendPost.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    sendPost.send(payload1);
};

var sendComment = function(id){
    
    text = document.getElementById('postComment').value;
    var currentdate = new Date(); 
    var timestamp1 =   currentdate.getDate() + "/" + (currentdate.getMonth()+1)  + "/" + currentdate.getFullYear() + " @ " + currentdate.getHours() + ":" + currentdate.getMinutes();
        
    commentPayload = "username=" + json.username + "&text=" + text + "&postID=" + id + "&timestamp=" + timestamp1;
    var postComment = new XMLHttpRequest();
    postComment.onreadystatechange = function(){
      if (postComment.readyState === 4 && postComment.status === 200){
          
        console.log('comment submitted successfully');
        fetchData();
      }
      else if(postComment.readyState === 4 && postComment.status !== 200){
          
        console.log("ERROR with comment : " + postComment.status);
      }
    };
    postComment.open('POST','commentPostServlet');
    postComment.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    postComment.send(commentPayload);
};

var deleteComment = function(id){
    
    var deleteCom = new XMLHttpRequest();
    deleteCom.onreadystatechange = function(){
        
      if (deleteCom.readyState === 4 && deleteCom.status === 200){
        console.log('comment deleted successfully');
        fetchData(); 
      }
      else if(deleteCom.readyState === 4 && deleteCom.status !== 200){
          
        console.log("ERROR with comment deletion: " + deleteCom.status);
      }
    };
    deleteCom.open('POST','deleteCommentServlet');
    deleteCom.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    deleteCom.send('id='+id);
};





