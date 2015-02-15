package com.pact.map;

//import android.R;
//import gen.com.example.essai1.R;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.R;

@SuppressLint("NewApi") 
public class MainActivity extends Activity {
	
	Button itineraire;
	Button valider;
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
			}
		});
		
	}

}
