package com.example.premankur.myapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHolder {
	public static final String domain = "domain";
	public static final String mode = "mode";
	public static final String question = "question";
	public static final String opt1 = "opt1";
	public static final String opt2 = "opt2";
	public static final String opt3 = "opt3";
	public static final String opt4 = "opt4";
	public static final String answer = "answer";
	public static final String tableName = "favTable";
	public static final String databaseName = "quizDatabase";
	public static final int database_version = 1;
	public static final String Table_Create = "create table favTable (domain text not null, mode text not null,question text not null, opt1 text not null, opt2 text not null, opt3 text not null, opt4 text not null, answer text not null);";
	//public static final String Database_Create = ""

	DatabaseHelper dbhelper;
	Context ctx;
	SQLiteDatabase db;
	public DatabaseHolder(Context ctx)
	{
		this.ctx = ctx;
		dbhelper = new DatabaseHelper(ctx);
	}

	public DatabaseHolder open()
	{
		db  = dbhelper.getWritableDatabase();
		return this;
	}
	public void close() {
		dbhelper.close();

	}
	public long insertData(String domain_l, String mode_l, String question_l, String opt1_l, String opt2_l, String opt3_l, String opt4_l, String answer_l)
	{
		ContentValues content = new ContentValues();
		content.put(domain, domain_l);
		content.put(mode, mode_l);
		content.put(question, question_l);
		content.put(opt1, opt1_l);
		content.put(opt2, opt2_l);
		content.put(opt3, opt3_l);
		content.put(opt4, opt4_l);
		content.put(answer, answer_l);
		return db.insertOrThrow(tableName, null, content);

	}
	
	public long deleteData(String question_l)
	{
		return db.delete(tableName, "question='"+question_l+"'", null);
	}
	public Cursor returnData() {
		return db.query(tableName, new String[] {domain,mode,question,opt1,opt2,opt3,opt4,answer}, null, null, null, null, null);
		
	}
	public static class DatabaseHelper extends SQLiteOpenHelper
	{

		public DatabaseHelper(Context context) {
			super(context, domain, null, database_version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			try{

				db.execSQL(Table_Create);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}


		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS favTable");
			onCreate(db);
		}

	}

}
