package com.example.premankur.myapplication;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CountryJSONPaser {

	/** Receives a JSONObject and returns a list */
	public ArrayList<QuestionBean> parse(JSONObject jObject, String domain, String mode){

		JSONArray jDomainArray = null;
		JSONArray jModeArray = null;
		try {
			/** Retrieves all the elements in the 'domain' array */
			jDomainArray = jObject.getJSONArray(domain);
			int modeIndex = 0;
			if(mode.equalsIgnoreCase("Begineer")){
				modeIndex=0;
			}
			else if(mode.equalsIgnoreCase("Intermediate")){
				modeIndex=1;
			}
			else if (mode.equalsIgnoreCase("Expert")){
				modeIndex=2;
			}
			else{
				modeIndex=0;
			}
			JSONObject modejObj = jDomainArray.getJSONObject(modeIndex);
			jModeArray = modejObj.getJSONArray(mode);
					
		} catch (JSONException e) {
			e.printStackTrace();
		}
		/** Invoking getCountries with the array of json object
		 * where each json object represent a domain
		 */
		return getCountries(jModeArray,domain,mode);
	}
	
	private ArrayList<QuestionBean> getCountries(JSONArray jCountries,String d,String m){
		int countryCount = jCountries.length();
		ArrayList<QuestionBean> domainList = new ArrayList<QuestionBean>();
		QuestionBean country = null;

		/** Taking each country, parses and adds to list object */
		for(int i=0; i<countryCount;i++){
			try {
				/** Call getCountry with country JSON object to parse the country */
				country = getCountry((JSONObject)jCountries.get(i),d,m);
				domainList.add(country);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return domainList;
	}

	/** Parsing the Country JSON object */
	private QuestionBean getCountry(JSONObject jCountry, String d, String m){

		QuestionBean domain = new QuestionBean();
		String difficulty = "";
		String question="";
		String opt1 = "";
		String opt2 = "";
		String opt3 = "";
		String opt4 = "";
		String ans = "";

		try {
			difficulty =m;
			question = jCountry.getString("question");
			opt1 = jCountry.getString("opt1");
			opt2 = jCountry.getString("opt2");
			opt3 = jCountry.getString("opt3");
			opt4 = jCountry.getString("opt4");
			ans =  jCountry.getString("ans");
			
			domain.setQuestion(difficulty);
			domain.setQuestion(question);
			domain.setOption1(opt1);
			domain.setOption2(opt2);
			domain.setOption3(opt3);
			domain.setOption4(opt4);
			domain.setAnswer(ans);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return domain;
	}
}
