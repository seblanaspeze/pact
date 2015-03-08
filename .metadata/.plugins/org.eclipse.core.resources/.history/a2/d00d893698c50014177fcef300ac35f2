package com.pact.map;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HttpConnection {
	
	
	public String readUrl(String mapsApiDirectionsUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(mapsApiDirectionsUrl);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.connect();
			iStream = urlConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
				//System.out.println("je suis connecté");
			}
			data = sb.toString();
			br.close();
		} catch (Exception e) {
			Log.d("Exception while reading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		System.out.println(data);
		return data;
		
	}

}