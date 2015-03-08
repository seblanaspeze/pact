package com.pact.map;

import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;

public class Retour {

	private ArrayList<Indication> retour;
	public Retour() {
		retour = new ArrayList<Indication>();
		// TODO Auto-generated constructor stub
	}

	public void addNew(LatLng latlng, String instruction){
		Indication indication = new Indication(latlng,instruction);
		retour.add(indication);
	}
	
	public Indication get(int i){
		return retour.get(i);
	}
	
	public int size(){
		return retour.size();
	}
}
