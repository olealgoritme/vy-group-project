#!/usr/bin/python3.7
import json
import googlemaps
from datetime import datetime

gmaps = googlemaps.Client(key='')

# Geocoding an address
#geocode_result = gmaps.geocode('1600 Amphitheatre Parkway, Mountain View, CA')

# Look up an address with reverse geocoding
#reverse_geocode_result = gmaps.reverse_geocode((40.714224, -73.961452))

# Request directions via public transit
now = datetime.now()
fromPlace="Oslo City"
toPlace="Bergen togstasjon"
directions_result = gmaps.directions(fromPlace,
                                     toPlace,
                                     mode="transit",
                                     departure_time=now)
# jsonPretty = json.loads(directions_result)
# jsonPretty = json.dumps(jsonPretty, indent=4, sort_keys=True)

#print(directions_result)
for leg in directions_result[0]['legs']:
   for step in leg['steps']:
        distance = step['distance']['text']
        time     = step['duration']['text']
        mode     = step['travel_mode']['text']
        print(distance+ " " + time + " with " + mode)
        
