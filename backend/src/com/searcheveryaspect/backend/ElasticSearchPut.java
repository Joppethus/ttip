package com.searcheveryaspect.backend;

import com.google.gson.Gson;

public class ElasticSearchPut {
	
	
	//ska h�r tillverka Json document och skicka in det i elastic search 
	public void putDocument(ESDocument doc) {
		
		
		Gson gson = new Gson();
		gson.toJson(doc);
		
	}

}
