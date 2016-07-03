package com.example.premankur.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class QuestionPage extends Activity {

	int correct=0;
	int wrong=0;
	int notAnswered=0;
	int favStarClicked = 0;
	String selectedOption;
	String answer;
    int flag=0;
    Stack<QuestionBean> prevQues = new Stack<>();
    Stack<String>prevAns = new Stack<>();
    Boolean isPrevPressed = false;
    DatabaseHolder handler ;
    String domain;
    String mode;
//    Stack<QuestionBean> questionStack;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_screen);
        
        //Setting custom background color
        String[] coloursArray = getResources().getStringArray(R.array.colors);
        Random r = new Random();
        String cureentBackColour = coloursArray[r.nextInt(coloursArray.length)];
        ScrollView rl = (ScrollView)findViewById(R.id.questn_layout);
        rl.setBackgroundColor(Color.parseColor(cureentBackColour));
        
        
        final TextView quest = (TextView) findViewById(R.id.quetionView);
        final RadioButton opt1 = (RadioButton) findViewById(R.id.button1);
        final RadioButton opt2 = (RadioButton) findViewById(R.id.button2);
        final RadioButton opt3 = (RadioButton) findViewById(R.id.button3);
        final RadioButton opt4 = (RadioButton) findViewById(R.id.button4);
        final ImageView next = (ImageView) findViewById(R.id.nextBut);
        final ImageView prev = (ImageView) findViewById(R.id.prevBut);
//        final TextView quesNo = (TextView) findViewById(R.id.questnNo);
        final ImageView favStar = (ImageView) findViewById(R.id.favStar);
        
		favStar.setImageResource(R.drawable.unfilled_star);
        Intent i = getIntent();
		domain = i.getExtras().getString("domain");
		mode = i.getExtras().getString("mode");
		System.out.println(domain);
		/*ActionBar ab = getActionBar();
	    ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
	    ab.setCustomView(R.layout.action_bar);
	    ab.setTitle(domain+" Quiz");*/
		Bundle b = i.getExtras();
		final ArrayList<QuestionBean> questList = b.getParcelableArrayList("ques");
		if(flag==0)
		{
			prev.setVisibility(View.INVISIBLE);
		}
		else{
			prev.setVisibility(View.VISIBLE);
		}
		final QuestionBean questClass = questList.get(flag);
//		quesNo.setText("Question "+(Integer)(flag+1));
		quest.setText(questClass.getQuestion().trim());
		opt1.setText(questClass.getOption1().trim());
		opt2.setText(questClass.getOption2().trim());
		opt3.setText(questClass.getOption3().trim());
		opt4.setText(questClass.getOption4().trim());
		answer = questClass.getAnswer();
		opt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				opt1.setTextColor(Color.rgb(0, 226, 210));
				opt1.setTypeface(null, Typeface.BOLD);
				opt4.setTypeface(null, Typeface.NORMAL);
				opt2.setTypeface(null, Typeface.NORMAL);
				opt3.setTypeface(null, Typeface.NORMAL);
				opt2.setTextColor(Color.parseColor("#000000"));
				opt3.setTextColor(Color.parseColor("#000000"));
				opt4.setTextColor(Color.parseColor("#000000"));
				selectedOption=opt1.getText().toString();
			}
		});
		
		opt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				opt2.setTextColor(Color.rgb(0, 226, 210));
				opt2.setTypeface(null, Typeface.BOLD);
				opt1.setTypeface(null, Typeface.NORMAL);
				opt4.setTypeface(null, Typeface.NORMAL);
				opt3.setTypeface(null, Typeface.NORMAL);
				opt1.setTextColor(Color.parseColor("#000000"));
				opt3.setTextColor(Color.parseColor("#000000"));
				opt4.setTextColor(Color.parseColor("#000000"));
				selectedOption=opt2.getText().toString();
			}
		});
		
		opt3.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		opt3.setTextColor(Color.rgb(0, 226, 210));
		opt3.setTypeface(null, Typeface.BOLD);
		opt1.setTypeface(null, Typeface.NORMAL);
		opt2.setTypeface(null, Typeface.NORMAL);
		opt4.setTypeface(null, Typeface.NORMAL);
		opt2.setTextColor(Color.parseColor("#000000"));
		opt1.setTextColor(Color.parseColor("#000000"));
		opt4.setTextColor(Color.parseColor("#000000"));
		selectedOption=opt3.getText().toString();
			}
		});
		
		opt4.setOnClickListener(new OnClickListener() {	
	@Override
		public void onClick(View v) {
		// TODO Auto-generated method stub
		opt4.setTextColor(Color.rgb(0, 226, 210));
		opt4.setTypeface(null, Typeface.BOLD);
		opt1.setTypeface(null, Typeface.NORMAL);
		opt2.setTypeface(null, Typeface.NORMAL);
		opt3.setTypeface(null, Typeface.NORMAL);
		opt2.setTextColor(Color.parseColor("#000000"));
		opt3.setTextColor(Color.parseColor("#000000"));
		opt1.setTextColor(Color.parseColor("#000000"));
		selectedOption=opt4.getText().toString();
			}
		});
		
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//			Animation vanish =AnimationUtils.loadAnimation(QuestionPage.this,R.drawable.animate);
//			next.startAnimation(vanish);
				
			if(selectedOption!=null){			
				// TODO Auto-generated method stub
			if(selectedOption.equalsIgnoreCase(answer)&&!isPrevPressed)
			{
				correct++;
				isPrevPressed=false;
			}
			else if(selectedOption!=null)
			{
				wrong++;
			}
			else{
				notAnswered++;
			}
			flag++;
			if(flag<questList.size())
			{
				favStarClicked = 0;
				favStar.setImageResource(R.drawable.unfilled_star);
//				questionStack.push(questClass);
				prevAns.push(selectedOption);
				prevQues.push(questClass);
//				quesNo.setText("Question "+(Integer)(flag+1));
				QuestionBean questClassL = questList.get(flag);
				quest.setText(questClassL.getQuestion().trim());
				opt1.setText(questClassL.getOption1().trim());
				opt2.setText(questClassL.getOption2().trim());
				opt3.setText(questClassL.getOption3().trim());
				opt4.setText(questClassL.getOption4().trim());
				answer = questClassL.getAnswer();
				opt2.setTextColor(Color.parseColor("#000000"));
				opt3.setTextColor(Color.parseColor("#000000"));
				opt1.setTextColor(Color.parseColor("#000000"));
				opt4.setTextColor(Color.parseColor("#000000"));
				opt1.setTypeface(null, Typeface.NORMAL);
				opt2.setTypeface(null, Typeface.NORMAL);
				opt3.setTypeface(null, Typeface.NORMAL);
				opt4.setTypeface(null, Typeface.NORMAL);
//				next.setTextColor(Color.parseColor("#FFFFFF"));
				prev.setVisibility(View.VISIBLE);
				selectedOption=null;
			}
			else{
//				Toast.makeText(QuestionPage.this, "Correct Answers.. "+correct, Toast.LENGTH_SHORT).show();
				flag=0;
				Intent intent = new Intent(getApplicationContext(), Result.class);
               	intent.putExtra("correct", String.valueOf(correct));
               	intent.putExtra("total", String.valueOf(questList.size()));
               	startActivity(intent);
               	finish();
			}
			}
			else{
				Toast.makeText(QuestionPage.this, "Please select an answer.!", Toast.LENGTH_SHORT).show();
			}}
		});
		

		prev.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Animation vanish =AnimationUtils.loadAnimation(QuestionPage.this,R.drawable.animate);
//				prev.startAnimation(vanish);
				// TODO Auto-generated method stub
				if(prevQues.size()>0){
//				quesNo.setText("Question "+(Integer)(flag));
				favStarClicked = 0;
				favStar.setImageResource(R.drawable.unfilled_star);
				QuestionBean questClassL = prevQues.pop();
				quest.setText(questClassL.getQuestion());
				opt1.setText(questClassL.getOption1());
				opt2.setText(questClassL.getOption2());
				opt3.setText(questClassL.getOption3());
				opt4.setText(questClassL.getOption4());
				selectedOption=prevAns.pop();
				answer = questClassL.getAnswer();
				opt2.setTextColor(Color.parseColor("#000000"));
				opt3.setTextColor(Color.parseColor("#000000"));
				opt1.setTextColor(Color.parseColor("#000000"));
				opt4.setTextColor(Color.parseColor("#000000"));	
				if(selectedOption.equalsIgnoreCase(opt1.getText().toString())){
					opt1.setTextColor(Color.rgb(0, 226, 210));;
					opt1.setTypeface(null, Typeface.BOLD);
				}
				if(selectedOption.equalsIgnoreCase(opt2.getText().toString())){
					opt2.setTextColor(Color.rgb(0, 226, 210));
					opt2.setTypeface(null, Typeface.BOLD);
				}
				if(selectedOption.equalsIgnoreCase(opt3.getText().toString())){
					opt3.setTextColor(Color.rgb(0, 226, 210));
					opt3.setTypeface(null, Typeface.BOLD);
				}
				if(selectedOption.equalsIgnoreCase(opt4.getText().toString())){
					opt4.setTextColor(Color.rgb(0, 226, 210));
					opt4.setTypeface(null, Typeface.BOLD);
				}
				isPrevPressed = true;
				flag--;
//				prevAns.remove(prevQues.size()-1);
//				prevQues.remove(prevQues.size()-1);
			}
			else{
				Toast.makeText(QuestionPage.this, "No more prev Questions", Toast.LENGTH_SHORT).show();
			}}
		});
		
		favStar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(favStarClicked==0)
				{
				favStarClicked = 1;
				favStar.setImageResource(R.drawable.filled_star);
				handler = new DatabaseHolder(getBaseContext());
				handler.open();
				Long id  = handler.insertData(domain, mode,quest.getText().toString(),opt1.getText().toString(),opt2.getText().toString(),opt3.getText().toString(),opt4.getText().toString(),answer);
				System.out.println("SQL Exception "+id);				
				Toast.makeText(getBaseContext(), "Added to Fav", Toast.LENGTH_SHORT).show();
				handler.close();
				}
				else{
					favStarClicked = 0;
					favStar.setImageResource(R.drawable.unfilled_star);
					handler = new DatabaseHolder(getBaseContext());
					handler.open();
					Long id  = handler.deleteData(quest.getText().toString());
					System.out.println("SQL Exception "+id);				
					Toast.makeText(getBaseContext(), "Removed from Fav", Toast.LENGTH_SHORT).show();
					handler.close();
				}
			}
		});
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		  MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.main, menu);
		    return super.onCreateOptionsMenu(menu);
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
		if(id == R.id.back_home)
		{
			Dialog d = createDialog();
	        d.show();
		}
		return super.onOptionsItemSelected(item);
	}
	public Dialog createDialog() {
	   AlertDialog.Builder builder = new AlertDialog.Builder(QuestionPage.this);
       builder.setMessage("Exit the Quiz.?")
              .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int id) {
                      // FIRE ZE MISSILES!
                	  finish();
                  }
              })
              .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int id) {
                      // User cancelled the dialog
                  }
              });
       // Create the AlertDialog object and return it
       return builder.create();
	}
	@Override
	public void onBackPressed() {
		Dialog d = createDialog();
        d.show();
	} 
}
