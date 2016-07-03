package com.example.premankur.myapplication;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class MainActivity extends Activity{

	private static final String TAG = "MyActivity";
	protected static ArrayList<QuestionBean> EXTRA_MESSAGE = null;
	protected static String domain = null;
	protected static String mode = null;
	protected static String strJson = null;
	String newJsonToWrite;	
	Bundle bundle = new Bundle();
	private boolean doubleBackToExitPressedOnce;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set the Action Bar to use tabs for navigation
/*        ActionBar ab = getActionBar();
	    ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	    ab.setCustomView(R.layout.action_bar);*/
//	    ab.setBackgroundDrawable(new ColorDrawable(Color.rgb(0,188,139)));
	    setTabs();
//		populateListView();
    	//read JSON
        if(isConnected()){
	        // call AsynTask to perform network operation on separate thread
	        new HttpAsyncTask().execute("http://probable-sprite-95723.appspot.com/json.json");
            
        }
	    try {
			String x = readFile();
			if(x!=null | !(x.equalsIgnoreCase(""))){
				strJson = x;	
			}
			else{
			    AssetManager assetManager = getResources().getAssets();
			    InputStream inputStream = null;
		
			        try {
			            inputStream = assetManager.open("json.txt");
			            strJson = getStringFromInputStream(inputStream);
			        } catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			System.out.println(x);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			 AssetManager assetManager = getResources().getAssets();
			    InputStream inputStream = null;
		
			        try {
			            inputStream = assetManager.open("json.txt");
			            strJson = getStringFromInputStream(inputStream);
			        } catch (Exception e1) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}

        Button java_button = (Button)this.findViewById(R.id.javaQuizButt);
        java_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                domain = "java";
                Dialog d = createDialog();
                d.show();
            }
        });

        Button iOS_button = (Button)this.findViewById(R.id.iOSQuizButt);
        iOS_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                domain = "iOS";
                Dialog d = createDialog();
                d.show();
            }
        });
        Button html_button = (Button)this.findViewById(R.id.htmlQuiz);
        html_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                domain = "html";
                Dialog d = createDialog();
                d.show();
            }
        });
    }

    //Create dialog to select mode - Begineer, Intermediate, Expert
    public Dialog createDialog() {
	    final String[] modeList = {"Begineer","Intermediate","Expert"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Please select a difficulty level")
               .setItems(modeList, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                   mode=modeList[which];
                   if(mode!=null){
                       EXTRA_MESSAGE =doInBackground(strJson,mode);
           			Intent intent = new Intent(getApplicationContext(), QuestionPage.class);
                   	intent.putExtra("ques",EXTRA_MESSAGE);
                   	intent.putExtra("domain", domain);
                   	intent.putExtra("mode", mode);
                   	startActivity(intent);
                   	};

               }
        });
        return builder.create();
    }

    //Write to JSON file in Internal Memory
    private void writeToFile(String data) {
        try {
        	BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new
                    File(getFilesDir()+File.separator+"json.txt")));
        	bufferedWriter.write(data);
        	bufferedWriter.close();
        }
        catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }

    }
    //Read from JSON file in Internal Memory
    String readFile() throws IOException {
    	BufferedReader bufferedReader = new BufferedReader(new FileReader(new
    			File(getFilesDir()+File.separator+"json.txt")));
    	String read;
    	StringBuilder builder = new StringBuilder("");

    	while((read = bufferedReader.readLine()) != null){
    		builder.append(read);
    	}
    	Log.d("Output", builder.toString());
    	bufferedReader.close();
		return  builder.toString();
    }

    /** Doing the parsing of xml data */
        public  ArrayList<QuestionBean> doInBackground(String strJson, String mode) {
        	JSONObject jObject = null;
            try{
                jObject = new JSONObject(strJson);
                CountryJSONPaser countryJsonParser = new CountryJSONPaser();
                countryJsonParser.parse(jObject,domain,mode);
            }catch(Exception e){
                Log.d("JSON Exception1",e.toString());
            }

            CountryJSONPaser countryJsonParser = new CountryJSONPaser();

            ArrayList<QuestionBean> domainList = null;

            try{
                /** Getting the parsed data as a List construct */
            	domainList = countryJsonParser.parse(jObject,domain,mode);
            }catch(Exception e){
                Log.d("Exception",e.toString());
            }

            return domainList;
        }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_act, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
	    if (doubleBackToExitPressedOnce) {
	        super.onBackPressed();
	        return;
	    }

	    this.doubleBackToExitPressedOnce = true;
	    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

	    new Handler().postDelayed(new Runnable() {

	        @Override
	        public void run() {
	            doubleBackToExitPressedOnce=false;
	        }
	    }, 2000);
	}

	// Read from Asset folder
	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	//Connect to internet to download new JSON file
    public static String GET(String urlStr){
        InputStream inputStream = null;
        String result = "";
        try {
            URL url = new URL(urlStr);
            // create HttpClient
			HttpURLConnection urlConnection  = (HttpURLConnection) url.openConnection();
            // receive response as inputStream
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            // convert inputstream to string
            if(inputStream != null)
                result = getStringFromInputStream(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected())
                return true;
            else
                return false;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            newJsonToWrite=(result);
            if(newJsonToWrite!=null){
	            writeToFile(newJsonToWrite);
	            strJson=newJsonToWrite;
            }
       }
    }
    public void setTabs() {
    	TabHost th=(TabHost)findViewById(R.id.tabhost);
        th.setup();
        TabSpec spec=th.newTabSpec("tag1");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Home");
        th.addTab(spec);
        spec=th.newTabSpec("tag2");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Favourites");
        th.addTab(spec);
//        th.setBackgroundColor(Color.rgb(0,188,139));
        AnimatedTabHostListener tablistener = new AnimatedTabHostListener(th);
       	th.setOnTabChangedListener(tablistener);

	}

//	public void populateListView() {
//		DatabaseHolder handler = new DatabaseHolder(getBaseContext());
//		handler.open();
//		Cursor c = handler.returnData();
//		if(c.moveToFirst())
//		{
//			do{
//		QuestionBean questn = new QuestionBean();
//		questn.setMode(mode)Question(c.getString(0));
////		email.setText(c.getString(1));
//			}
//			while(c.moveToNext());
//		}
//		handler.close();
//		ListView lv=(ListView) findViewById(R.id.listView1);
//		ArrayAdapter<QuestionBean> myAdapter = new ArrayAdapter<QuestionBean>(this,
//	          android.R.layout.simple_list_item_2, android.R.id.text1);
//		lv.setAdapter(myAdapter);
//
//	}
//    public boolean onTouchEvent(MotionEvent touchevent) {
//        float lastX = 0;
//		switch (touchevent.getAction()) {
//        // when user first touches the screen to swap
//        case MotionEvent.ACTION_DOWN: {
//            lastX = touchevent.getX();
//            break;
//        }
//        case MotionEvent.ACTION_UP: {
//            float currentX = touchevent.getX();
//
//            // if left to right swipe on screen
//            if (lastX < currentX) {
//
//                switchTabs(false);
//            }
//
//            // if right to left swipe on screen
//            if (lastX > currentX) {
//                switchTabs(true);
//            }
//
//            break;
//        }
//        }
//        return false;
//    }

//    public void switchTabs(boolean direction) {
//        TabHost tabHost = (TabHost)findViewById(R.id.tabhost);
//		if (direction) // true = move left
//        {
//            if (tabHost.getCurrentTab() == 0)
//                tabHost.setCurrentTab(tabHost.getTabWidget().getTabCount() - 1);
//            else
//                tabHost.setCurrentTab(tabHost.getCurrentTab() - 1);
//        } else
//        // move right
//        {
//            if (tabHost.getCurrentTab() != (tabHost.getTabWidget()
//                    .getTabCount() - 1))
//                tabHost.setCurrentTab(tabHost.getCurrentTab() + 1);
//            else
//                tabHost.setCurrentTab(0);
//        }
//    }

}
