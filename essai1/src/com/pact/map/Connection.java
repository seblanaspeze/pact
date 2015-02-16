package com.pact.map;

import java.util.ArrayList;



import com.google.android.gms.maps.model.LatLng;

public class Connection {

	//public ArrayList<LatLng> list;
	private String url;
	
	public Connection(ArrayList<LatLng> list) {
		// TODO Auto-generated constructor stub
		String waypoints = "MA&waypoints=";
		int s = list.size();
		String origin ="";
		String destination="";
		
		for(int i = 0; i<s-1; i++){
			if(i == 0){
				 origin = "origin="+ list.get(i).latitude+","+list.get(i).longitude+"&";
			}
			else if(i== s-2){
				destination = "MA&destination=" + list.get(i).latitude+","+list.get(i).longitude + "&";
			}
			else if (i == s-3){
				waypoints = waypoints + list.get(i).latitude+","+list.get(i).longitude + ",MA" ;
			}
			else{
				waypoints = waypoints + list.get(i).latitude+","+list.get(i).longitude + ",MA|";
			}
		}
		String key= "&key=AIzaSyDZqf_7vXpMUuFZANaSslul8DmS6ukmeJ0";
		String sensor = "sensor=false";
		String params = sensor + "&" + origin + destination + waypoints ;// + key;
		String output = "json";
		String mode = "&mode=walking";
		url = "https://maps.googleapis.com/maps/api/directions/"+ output + "?" + params + mode;
		//url = "https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal";//&key=AIzaSyDZqf_7vXpMUuFZANaSslul8DmS6ukmeJ0";
		System.out.println(url);
		
	
				

			
	}
	public String getURL(){
		return url;
	}
}
