function initialize() {
	const
	latlng = new google.maps.LatLng(52.254427,-7.185281);
	const
	mapOptions = {
		center : new google.maps.LatLng(latlng.lat(), latlng.lng()),
		mapTypeId : google.maps.MapTypeId.MAP,
		zoom : 16
	};

	map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);

	marker = new google.maps.Marker({
		map : map,
		draggable : true,
		position : latlng,
		title : "Drag and drop on your property!"
	});

	// To add the marker to the map, call setMap();
	marker.setMap(map);

	// marker listener populates hidden fields ondragend
	google.maps.event.addListener(marker, 'dragend', function() {
		const
		latLng = marker.getPosition();
		const
		latlong = latLng.lat().toString().substring(0, 10) + ','
				+ latLng.lng().toString().substring(0, 10);
		// publish lat long in geolocation control in html page
		$("#geolocation").val(latlong);
		// update the new marker position
		map.setCenter(latLng);
	});
}
google.maps.event.addDomListener(window, 'load', initialize);