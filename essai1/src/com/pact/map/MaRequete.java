package com.pact.map;

import android.os.AsyncTask;
import android.util.Log;

public class MaRequete  extends AsyncTask<String, Void, String > {
		
	
		private String data2;
		@Override
		protected String doInBackground(String... url) {
			System.out.println("je suis la");
			String data = "";
			try {
				System.out.println("requete");
				HttpConnection http = new HttpConnection();
				data = http.readUrl(url[0]);
			} 
			catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			
			System.out.println("j'enregistre data");
			data2 = data;
			return data;
		}

		public String getdata(){
			
			System.out.println("je renvoie data");
			return data2;
		}

}
