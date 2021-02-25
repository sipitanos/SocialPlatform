var faceAsk = function(){
  'use strict';
var x = document.getElementById('hiddenDiv');
    x.style.display = 'block';
};
var videoAsk = function(){
  'use strict';
var x1 = document.getElementById('innerDiv');
    x1.style.display = 'block';
    //faceRec.init();
};
/*
    Author: Panagiotis Papadakos papadako@csd.uoc.gr

    Try to read this file and understand what the code does...
    Then try to complete the missing functionality

    For the needs of the hy359 2020 course
    University of Crete

    At the end of the file there are some comments about our trips

*/

/*  face recognition that is based on faceplusplus service */
var faceRec = (function () {
  
  // Object that holds anything related with the facetPlusPlus REST API Service
  var faceAPI = {
    apiKey:  'l2jNgKbk1HXSR4vMzNygHXx2g8c_xT9c',
    apiSecret:  '2T6XdZt4EYw-I7OhmZ6g1wtECl81e_Ip',
    app: 'hy359',
    // Detect
    // https://console.faceplusplus.com/documents/5679127
    detect: 'https://api-us.faceplusplus.com/facepp/v3/detect',  // POST
    // Set User ID //'https://api-us.faceplusplus.com/facepp/v3/detect?api_key=l2jNgKbk1HXSR4vMzNygHXx2g8c_xT9c&api_secret=2T6XdZt4EYw-I7OhmZ6g1wtECl81e_Ip',
    // https://console.faceplusplus.com/documents/6329500
    setuserId: 'https://api-us.faceplusplus.com/facepp/v3/face/setuserid', // POST
    // Get User ID
    // https://console.faceplusplus.com/documents/6329496
    getDetail: 'https://api-us.faceplusplus.com/facepp/v3/face/getdetail', // POST
    // addFace
    // https://console.faceplusplus.com/documents/6329371
    addFace: 'https://api-us.faceplusplus.com/facepp/v3/faceset/addface', // POST
    // Search
    // https://console.faceplusplus.com/documents/5681455
    search: 'https://api-us.faceplusplus.com/facepp/v3/search', // POST
    // Create set of faces
    // https://console.faceplusplus.com/documents/6329329
    create: 'https://api-us.faceplusplus.com/facepp/v3/faceset/create', // POST
    // update
    // https://console.faceplusplus.com/documents/6329383
    update: 'https://api-us.faceplusplus.com/facepp/v3/faceset/update', // POST
    // removeface
    // https://console.faceplusplus.com/documents/6329376
    removeFace: 'https://api-us.faceplusplus.com/facepp/v3/faceset/removeface', // POST
  };

  // Object that holds anything related with the state of our app
  // Currently it only holds if the snap button has been pressed
  var state = {
    photoSnapped: false,
  };

  // function that returns a binary representation of the canvas
  function getImageAsBlobFromCanvas(canvas) {
  
    // function that converts the dataURL to a binary blob object
    function dataURLtoBlob(dataUrl) {
      
      // Decode the dataURL
      var binary = atob(dataUrl.split(',')[1]);

      // Create 8-bit unsigned array
      var array = [];
      for (var i = 0; i < binary.length; i++) {
        array.push(binary.charCodeAt(i));
      }

      // Return our Blob object
      return new Blob([new Uint8Array(array)], {
        type: 'image/jpg',
      });
    }

    var fullQuality = canvas.toDataURL('image/jpeg', 1.0);
    return dataURLtoBlob(fullQuality);

  }

  // function that returns a base64 representation of the canvas
  function getImageAsBase64FromCanvas(canvas) {
    
    // return only the base64 image not the header as reported in issue #2
    return canvas.toDataURL('image/jpeg', 1.0).split(',')[1];

  }

  // Function called when we upload an image
  function uploadImage(filename) {
    
    if (state.photoSnapped) {

      var canvas = document.getElementById('canvas');
      var image = getImageAsBlobFromCanvas(canvas);
      var image64 = getImageAsBase64FromCanvas(canvas);
      // ============================================

      // TODO!!! Well this is for you ... YES you!!!
      // Good luck!
      var url = window.location.pathname;
      filename = url.substring(url.lastIndexOf('/')+1);
      console.log(filename);
      // Create Form Data. Here you should put all data
      // requested by the face plus plus services and
      // pass it to ajaxRequest
      var data = new FormData();
      var api;
      if (filename == 'signup.html'){
        
        api = faceAPI.detect;
        console.log('SIGNUP');
        data.append("api_key", faceAPI.apiKey);
        data.append('api_secret', faceAPI.apiSecret);
        data.append('image_url', canvas);
        data.append('image_file', image);
        data.append('image_base64', image64);
      }
      else if (filename == 'index.html'){
        
        console.log('SIGNIN');
        api = faceAPI.search;
        data.append("api_key", faceAPI.apiKey);
        data.append('api_secret', faceAPI.apiSecret);
        data.append('outer_id', faceAPI.app);
        data.append('image_file', image);
      }
      // add also other query parameters based on the request
      // you have to send

      // You have to implement the ajaxRequest. Here you can
      // see an example of how you should call this
      // First argument: the HTTP method
      // Second argument: the URI where we are sending our request
      // Third argument: the data (the parameters of the request)
      // ajaxRequest function should be general and support all your ajax requests...
      // Think also about the handler
      var xhttp = new XMLHttpRequest();
      xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
          var json = JSON.parse(xhttp.responseText);
          
    
          if (filename == 'index.html'){

            console.log('SEARCH_FACE');
            document.getElementById('user_name').value = json.results[0].user_id;
            console.log(json);
          }
          else{

            console.log('DETECT_FACE');
            var inp = json.faces[0].face_token;
            setUserId(inp);
          }
           console.log('SUCCESSFULL DETECTION');
        }
      };

      xhttp.open('POST', api);
      xhttp.send(data);
    } 
    else {

      alert('No image has been taken!');
    }
  }
  // SETS THE USER ID 
  function setUserId(input){
    
    var data = new FormData();
    var api = faceAPI.setuserId;
    console.log('SETUSERID');
    data.append("api_key", faceAPI.apiKey);
    data.append('api_secret', faceAPI.apiSecret);
    data.append('face_token', input);
    data.append('user_id', document.getElementById('user_name').value);

    var xhttp1 = new XMLHttpRequest();
    xhttp1.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      var json1 = JSON.parse(xhttp1.responseText);
          
      console.log(json1); //this works
      console.log('SUCCESSFUL USER ID SET');
      addToFaceSet(input);
      }
    };

    xhttp1.open('POST', api);
    xhttp1.send(data);
  }
  // ADDS THE FACE TO THE FACESET
  function addToFaceSet(input){
    
    var data = new FormData();
    var api = faceAPI.addFace;
    console.log('ADD FACE');
    data.append("api_key", faceAPI.apiKey);
    data.append('api_secret', faceAPI.apiSecret);
    data.append('outer_id', faceAPI.app);
    data.append('face_tokens', input);

    var inToFS = new XMLHttpRequest();
    inToFS.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        var json2 = JSON.parse(inToFS.responseText);
          
        console.log(json2); //this works
        console.log('SUCCESSFUL FACE SET ADDITION');
      }
    };

    inToFS.open('POST', api);
    inToFS.send(data);
  }

  // this function is intended for the index.html that sees if there is a face
  function searchTheFaceSet(input){
    
    var data = new FormData();
    var api = faceAPI.search;
    console.log('FIND FACE');
    data.append("api_key", faceAPI.apiKey);
    data.append('api_secret', faceAPI.apiSecret);
    data.append('face_token', input);
    data.append('outer_id', faceAPI.app);
    var searchFS = new XMLHttpRequest();
    searchFS.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        var json3 = JSON.parse(searchFS.responseText);
        console.log('JSON PRINTS');
        console.log(json3); //this works
        console.log('WE DID IT ');
      }
    };

    searchFS.open('POST', api);
    searchFS.send(data);
  }
  // Function for initializing things (event handlers, etc...)
  function init() {

    // Put event listeners into place
    // Notice that in this specific case handlers are loaded on the onload event
    window.addEventListener('DOMContentLoaded', function () {
      // Grab elements, create settings, etc.
      var canvas = document.getElementById('canvas');
      var context = canvas.getContext('2d');
      var video = document.getElementById('video');
      var mediaConfig = {
        video: true,
      };
      var errBack = function (e) {
        console.log('An error has occurred!', e);
      };

      // Put video listeners into place
      if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
        navigator.mediaDevices.getUserMedia(mediaConfig).then(function (stream) {
          video.srcObject = stream; /// Check the code here even tho it is not needed to change
          video.onloadedmetadata = function (e) {
            console.log("can we try");
            video.play();
          };
        });
      }

      // Trigger photo take
      document.getElementById('snap').addEventListener('click', function () {
        context.drawImage(video, 0, 0, 256, 192);
        state.photoSnapped = true; // photo has been taken
      });

      // Trigger when upload button is pressed
      
      document.getElementById('upload').addEventListener('click', uploadImage);
    }, false);
  }
  // ============================================

  // !!!!!!!!!!! ================ TODO  ADD YOUR CODE HERE  ====================
  // From here on there is code that should not be given....

  // You have to implement the ajaxRequest function!!!!

  // !!!!!!!!!!! =========== END OF TODO  ===============================

  // Public API of function for facet recognition
  // You might need to add here other methods based on your implementation
  return {
    init: init,
  };

})();