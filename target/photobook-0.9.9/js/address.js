/********************************
 * Sipitanos Antonis			*
 *3272							*
 *Exercise 2					*
 *ADDRESS and Map AJAX REQUEST 	*
 ********************************/

var api = 'http://nominatim.openstreetmap.org/search?q=';
var api_end ='&format=json&addressdetails=1';
// 3 functions here as they are similar in what their purpose is 
var lat; 
var lon;
// Function to find if the address given was correct
function findLocation(){
	'use strict';
	// Check for empty address to show error
	if (document.getElementById('address').value === undefined ||document.getElementById('address').value === ''  ){
		console.log(document.getElementById('address').value);
			document.getElementById('addr_message').style.color = 'red';
			document.getElementById('addr_message').innerHTML = 'Address doesn\'t exist. Try again!';
	}
	else{	// if address is given then search for it with ajax request
		console.log(document.getElementById('address').value);
		var addr = document.getElementById('address').value;
		var addr_array = addr.split(" ");
		var address = addr_array[0];

		for (var i = 1; i < addr_array.length; i++) { /* Make it in a way that will be understood by the openstreetmaps */

    		address = address + '+'+ addr_array[i];
		}

		address = address +'+'+ document.getElementById('town_name').value;

		var url = api + address + api_end;

		var getloc = new XMLHttpRequest();

		getloc.onreadystatechange = function() {

			if (getloc.readyState === 4 && getloc.status === 200) {

				var json = JSON.parse(getloc.responseText);
				console.log(json[0]);

				if (json[0] === undefined){

					document.getElementById('addr_message').style.color = 'red';
					document.getElementById('addr_message').innerHTML = 'Address doesn\'t exist. Try again!';
				}
				else{

					lat = json[0].lat;
					lon = json[0].lon;

					document.getElementById('addr_message').style.color = '#4CAF50';
					document.getElementById('addr_message').innerHTML = 'Address found!';
				}
				// Reset the map
				document.getElementById('basicMap').innerHTML = "";
				document.getElementById('basicMap').style.border ="none";
			}
		};

		getloc.open('GET', url, true);

		getloc.send();
	}

}
// Function to show the map
function showMap() {
	'use strict';

    var map = new OpenLayers.Map("basicMap");
    var mapnik         = new OpenLayers.Layer.OSM();
    
    var fromProjection = new OpenLayers.Projection("EPSG:4326");   // Transform from WGS 1984
    var toProjection   = new OpenLayers.Projection("EPSG:900913"); // to Spherical Mercator Projection
    var position       = new OpenLayers.LonLat(lon,lat).transform( fromProjection, toProjection);
    var zoom           = 15; 
    // Add the marker
    var markers = new OpenLayers.Layer.Markers( "Markers" );

    // Draw the map
    map.addLayer(mapnik);
    map.addLayer(markers);
    markers.addMarker(new OpenLayers.Marker(position));
    map.setCenter(position, zoom);
    document.getElementById('basicMap').style.border ="thick solid green";
}

function autoDetectLocation(){
	'use strict';

  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(reverseFindLocation);
  } else { 
    document.getElementById("error_loc").innerHTML = "Geolocation is not supported by this browser.";
  }
}

// Auto detect Location function
function reverseFindLocation(position){
	'use strict';
	var rev = 'https://nominatim.openstreetmap.org/reverse?format=json&lat=';
	var mid = '&lon=';
	var end = '&zoom=18&addressdetails=1';
	var combined = rev + position.coords.latitude + mid + position.coords.longitude + end;
	console.log(combined);
	var autoloc = new XMLHttpRequest();
	autoloc.onreadystatechange = function() {

			if (autoloc.readyState === 4 && autoloc.status === 200) {

				var json = JSON.parse(autoloc.responseText);
				console.log(json);

				console.log(json.display_name);
                                locjson = JSON.parse(autoloc.responseText);
				
				// Reset the map
                                var check = document.getElementById('basicMap');
                                if (check){
                                    document.getElementById('basicMap').innerHTML = "";
                                    document.getElementById('basicMap').style.border ="none";

                                    document.getElementById('country').value = json.address.country;
                                    document.getElementById('town_name').value = json.address.city;
                                    if(json.address.house_number === undefined){
                                            document.getElementById('address').value = json.address.road ;
                                    }
                                    else{
                                            document.getElementById('address').value = json.address.road + ' '+  json.address.house_number ;
                                    }
                                }
			}
		};

		autoloc.open('GET', combined, true);

		autoloc.send();
}


function showPostMap(lat,lon){
    
    	'use strict';
         var check = document.getElementById('basicMap');
            if (check){
        document.getElementById('basicMap').innerHTML = "";
        document.getElementById('basicMap').style.border ="none";
    }

    var map = new OpenLayers.Map("basicMap");
    var mapnik         = new OpenLayers.Layer.OSM();
    
    var fromProjection = new OpenLayers.Projection("EPSG:4326");   // Transform from WGS 1984
    var toProjection   = new OpenLayers.Projection("EPSG:900913"); // to Spherical Mercator Projection
    var position       = new OpenLayers.LonLat(lon,lat).transform( fromProjection, toProjection);
    var zoom           = 15; 
    // Add the marker
    var markers = new OpenLayers.Layer.Markers( "Markers" );
   

    // Draw the map
    map.addLayer(mapnik);
    map.addLayer(markers);
    markers.addMarker(new OpenLayers.Marker(position));
    map.setCenter(position, zoom);
    document.getElementById('basicMap').style.border ="thick solid green";
    
    
}

function closeMap(){
             var check = document.getElementById('basicMap');
            if (check){
        document.getElementById('basicMap').innerHTML = "";
        document.getElementById('basicMap').style.border ="none";
    }
}