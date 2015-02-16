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
		String key= "key=AIzaSyDORMusVOPdjXdJ-Sun-PFoYkt2ux4uZ9E";
		String sensor = "sensor=false";
		String params = sensor + "&" + origin + destination + waypoints + key;
		String output = "json";
		url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + params;
		System.out.println(url);
		
	
				

			
	}
	public String getURL(){
		return url;
	}
}
