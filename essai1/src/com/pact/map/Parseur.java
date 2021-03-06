package com.pact.map;

import java.util.ArrayList;
import java.util.HashMap;


import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import android.graphics.Color;
import android.os.AsyncTask;

public class Parseur extends AsyncTask<String, Void, ArrayList<ArrayList<HashMap<String,String>>>> {
	
	private PolylineOptions poly;
	@Override
	protected ArrayList<ArrayList<HashMap<String,String>>> doInBackground(String... jsonData) {

		JSONObject jObject;
		ArrayList<ArrayList<HashMap<String,String>>> routes = null;

		try {
			jObject = new JSONObject(jsonData[0]);
			PathJSONParser parser = new PathJSONParser();
			routes = parser.parse(jObject);
			System.out.println("parseur");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return routes;
	}

	@Override
	protected void onPostExecute(ArrayList<ArrayList<HashMap<String,String>>> routes) {
		ArrayList<LatLng> points = null;
		PolylineOptions polyLineOptions = null;

		// traversing through routes
		for (int i = 0; i < routes.size(); i++) {
			points = new ArrayList<LatLng>();
			polyLineOptions = new PolylineOptions();
			ArrayList<HashMap<String,String>> path =  routes.get(i);

			for (int j = 0; j < path.size(); j++) {
				HashMap<String,String> point =  path.get(j);

				double lat = Double.parseDouble((String) point.get("lat"));
				double lng = Double.parseDouble((String) point.get("lng"));
				LatLng position = new LatLng(lat, lng);
				 
				points.add(position);
			}

			polyLineOptions.addAll(points);
			polyLineOptions = polyLineOptions.width(500);
			System.out.println(polyLineOptions.getWidth());
			polyLineOptions.color(Color.BLUE);
		}

		poly = polyLineOptions;
	}
	
	public PolylineOptions getpoly(){
		return poly;
	}

}
