package com.pact.map;

//import android.R;
//import gen.com.example.essai1.R;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
//import com.google.android.gms.R;

@SuppressLint("NewApi") 
public class MainActivity extends Activity {
	
	Button itineraire;
	Button valider;
	String data;
	int c = 0;
	ArrayList<LatLng> listepoint = new ArrayList<LatLng>();;
	static final LatLng TutorialsPoint = new LatLng(21 , 57);
    private GoogleMap googleMap;
    
    @SuppressLint("NewApi") 
    @Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		itineraire = (Button)findViewById(R.id.itineraire);
		valider = (Button)findViewById(R.id.valider);
	
		try {
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView)).getMap();
			}
			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			Marker TP = googleMap.addMarker(new MarkerOptions().position(TutorialsPoint).title("TutorialsPoint"));

		}
		
		catch (Exception e) {
        e.printStackTrace();
		}
		
		itineraire.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            	
            	System.out.println("Boujour");
            	itineraire.setText("Ajouter un point de passage");
            	valider.setVisibility(v.VISIBLE); 
            	
            	
            	googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {  
        			@Override  
        			public void onMapClick(LatLng point) { 
        				if( c == 0){
        					googleMap.addMarker(new MarkerOptions().position(point).title("depart"));
        				}
        				else {
        					googleMap.addMarker(new MarkerOptions().position(point).title("point de passage "+c+""));
        				}
        				listepoint.add(point);
        				
        				System.out.println("ici");
        				c = c+1;
        			}
        		});
            	
           
            }
		});
		
		valider.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				valider.setVisibility(v.INVISIBLE);
				itineraire.setText("Nouvel Itineraire");
				Connection connection = new Connection(listepoint);
				//System.out.println(listepoint.get(0));
				
				String url = connection.getURL();
				
				//MaRequete requete = new MaRequete();
				//requete.execute(url);
				
				
				//data = requete.getdata();
				//System.out.println(data);
				
				//Parseur parseur = new Parseur();
				//parseur.execute(data);
				//PolylineOptions poly = parseur.getpoly();
				
				//googleMap.addPolyline(poly);
				
				ReadTask downloadTask = new ReadTask();
				downloadTask.execute(url);
			}
			
		});
		
		
		
		

			
		
	}
    
    private class ReadTask extends AsyncTask<String, Void,String> {
		@Override
		protected String doInBackground(String... url) {
			String data = "";
			try {
				HttpConnection http = new HttpConnection();
				data = http.readUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			new ParserTask().execute(result);
		}
	}
    
    private class ParserTask extends AsyncTask<String, Void, ArrayList<ArrayList<HashMap<String,String>>>> {
    	
    	
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
    				System.out.println("derniere boucle for");
    				points.add(position);
    			}

    			polyLineOptions.addAll(points);
    			polyLineOptions.width(2);
    			polyLineOptions.color(Color.BLUE);
    		}
    		System.out.println("poly");
    		googleMap.addPolyline(polyLineOptions);
    	}
    	
    	

    }
}
