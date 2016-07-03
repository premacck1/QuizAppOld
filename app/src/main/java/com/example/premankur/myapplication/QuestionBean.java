package com.example.premankur.myapplication;


import android.os.Parcel;
import android.os.Parcelable;

public class QuestionBean implements Parcelable{

	String mode = "";
	String question = "";
	String Option1 = "";
	String Option2 = "";
	String Option3 = "";
	String Option4 = "";
	String answer = "";
	public QuestionBean()
	{
		
	}
	public QuestionBean(Parcel input)
	{
		mode = input.readString();
		question = input.readString();
		Option1 = input.readString();
		Option2 = input.readString();
		Option3 = input.readString();
		Option4 = input.readString();
		answer = input.readString();
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getOption1() {
		return Option1;
	}
	public void setOption1(String option1) {
		Option1 = option1;
	}
	public String getOption2() {
		return Option2;
	}
	public void setOption2(String option2) {
		Option2 = option2;
	}
	public String getOption3() {
		return Option3;
	}
	public void setOption3(String option3) {
		Option3 = option3;
	}
	public String getOption4() {
		return Option4;
	}
	public void setOption4(String option4) {
		Option4 = option4;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(mode);
		dest.writeString(question);
		dest.writeString(Option1);
		dest.writeString(Option2);
		dest.writeString(Option3);
		dest.writeString(Option4);
		dest.writeString(answer);
		
	}
	public static final Creator<QuestionBean> CREATOR
    = new Creator<QuestionBean>() {
public QuestionBean createFromParcel(Parcel in) {
    return new QuestionBean(in);
}

public QuestionBean[] newArray(int size) {
    return new QuestionBean[size];
}
}; 
}