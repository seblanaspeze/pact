package com.pact.map;

import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;

public class Connection {

	// public ArrayList<LatLng> list;
	@SuppressWarnings("unused")
	private static final String API_KEY = "&key=AIzaSyDZqf_7vXpMUuFZANaSslul8DmS6ukmeJ0";
	public static final String WAYPOINTS_TAG = "&waypoints=";
	public static final String OUTPUT = "json";
	public static final String MODE = "mode=walking";
	public static final String SENSOR = "sensor=false";

	public static String getURLConnection(ArrayList<LatLng> list) {
		// TODO Auto-generated constructor stub
		String origin = "";
		String destination = "";
		String waypoints = WAYPOINTS_TAG;
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				origin = "origin=" + list.get(i).latitude + ","
						+ list.get(i).longitude;
			} else if (i == list.size() - 1) {
				destination = "&destination=" + list.get(i).latitude + ","
						+ list.get(i).longitude + "&";
			} else if (i == list.size() - 2) {
				waypoints = waypoints + list.get(i).latitude + ","
						+ list.get(i).longitude + "&";
			} else {
				waypoints = waypoints + list.get(i).latitude + ","
						+ list.get(i).longitude + "|";
			}
		}
		return ("https://maps.googleapis.com/maps/api/directions/" + OUTPUT
				+ "?" + SENSOR + "&" + origin + destination + waypoints + MODE);
	}
}
