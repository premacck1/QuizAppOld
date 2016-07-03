package com.example.premankur.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Result extends Activity{
	    ProgressCircle progressCircle;
	    String correct =null;
	    String total = null;
	    TextView resultText ;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.resultpage);
	       /* ActionBar ab = getActionBar();
		    ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		    ab.setCustomView(R.layout.action_bar);
		    ab.setTitle("Result");*/
		    resultText = (TextView) this.findViewById(R.id.resultText);
	        Intent i = getIntent();
			correct = (i.getExtras().getString("correct"));
			total = (i.getExtras().getString("total"));
	        progressCircle = (ProgressCircle) findViewById(R.id.progress_circle);
	        progressCircle.setTextColor(Color.rgb(109, 221, 154));
	        progressCircle.setProgressColor(Color.rgb(61, 232, 32));
	        progressCircle.setIncompleteColor(Color.rgb(255, 13, 13));
	        animate();
	    }


	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.result, menu);
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
	    	if(id == R.id.home_page)
			{
				finish();
			}
	        return super.onOptionsItemSelected(item);
	    }

	    public void animate() {
	        float val = Float.parseFloat(correct)/Float.parseFloat(total);
	        if(val>=0.5){
	        	resultText.setTextColor(Color.rgb(0, 204,102));
	        }else{
	        	resultText.setTextColor(Color.rgb(255, 80, 80));
	        }
	        progressCircle.setProgress(val);
	        progressCircle.startAnimation();
	    }
	}
