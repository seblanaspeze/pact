package com.pact.map;

//import android.R;
//import gen.com.example.essai1.R;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

//import com.google.android.gms.R;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	Button itineraire;
	Button valider;
	//ProgressBar bar;
	String data;
	int c = 0;
	int d = 0;
	ArrayList<LatLng> listepoint = new ArrayList<LatLng>();;

	private GoogleMap googleMap;
	private LatLng myLocation;
	private Location location;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	//	bar = (ProgressBar) findViewById(R.id.bar);
		itineraire = (Button) findViewById(R.id.itineraire);
		valider = (Button) findViewById(R.id.valider);
		

		try {
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.mapView)).getMap();
			}
			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		googleMap.setMyLocationEnabled(true);
		location = googleMap.getMyLocation();

		if (location != null) {
			myLocation = new LatLng(location.getLatitude(), location.getLongitude());
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
		 }
		 
		// 48 2

		itineraire.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				if (c != 0 || d == 0){
					googleMap.clear();
					listepoint.clear();
				}
				

				//System.out.println("Boujour");
				itineraire.setText("		Annuler		");
				valider.setVisibility(View.VISIBLE);

				googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
							@Override
							public void onMapLongClick(LatLng point) {
								if (c == 0) {
									googleMap.addMarker(new MarkerOptions()
											.position(point).title("depart"));
								} else {
									googleMap.addMarker(new MarkerOptions()
											.position(point).title(
													"point de passage " + c
															+ ""));
								}
								listepoint.add(point);

								//System.out.println("ici");
								c = c + 1;
								d = d + 1;
								
							}
						});

			}
		});

		valider.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				valider.setVisibility(View.INVISIBLE);
				itineraire.setText("Nouvel Itineraire");
				String url = Connection.getURLConnection(listepoint);
				System.out.println(url);
				// MaRequete requete = new MaRequete();
				// requete.execute(url);

				// data = requete.getdata();
				// System.out.println(data);

				// Parseur parseur = new Parseur();
				// parseur.execute(data);
				// PolylineOptions poly = parseur.getpoly();

				// googleMap.addPolyline(poly);

				ReadTask downloadTask = new ReadTask();
				downloadTask.execute(url);

			}

		});

	}

	private class ReadTask extends AsyncTask<String, Integer, String> {
		
		/*@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Toast.makeText(getApplicationContext(), "D�but du traitement asynchrone", Toast.LENGTH_LONG).show();
		}
		
		@Override
		protected void onProgressUpdate(Integer... values){
			super.onProgressUpdate(values);
			bar.setVisibility(View.VISIBLE);
			// Mise � jour de la ProgressBar
			bar.setProgress(values[0]);
		}*/
		
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
			//bar.setVisibility(View.INVISIBLE);
			new ParserTask().execute(result);
		}
	}

	private class ParserTask
			extends
			AsyncTask<String, Void, ArrayList<ArrayList<HashMap<String, String>>>> {

		@Override
		protected ArrayList<ArrayList<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			ArrayList<ArrayList<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				PathJSONParser parser = new PathJSONParser();
				routes = parser.parse(jObject);
				//System.out.println("parseur");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		@Override
		protected void onPostExecute(
				ArrayList<ArrayList<HashMap<String, String>>> routes) {
			ArrayList<LatLng> points = null;
			PolylineOptions polyLineOptions = null;

			// traversing through routes
			for (int i = 0; i < routes.size(); i++) {
				points = new ArrayList<LatLng>();
				polyLineOptions = new PolylineOptions();
				ArrayList<HashMap<String, String>> path = routes.get(i);

				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble((String) point.get("lat"));
					double lng = Double.parseDouble((String) point.get("lng"));
					LatLng position = new LatLng(lat, lng);
					//System.out.println("derniere boucle for");
					points.add(position);
				}

				polyLineOptions.addAll(points);
				polyLineOptions.width(10);
				polyLineOptions.color(Color.BLUE);
			}
			Log.i("ParserTask", "Poly");
			googleMap.addPolyline(polyLineOptions);
			c = 0;
			d = 0;
			
		}

	}
}
