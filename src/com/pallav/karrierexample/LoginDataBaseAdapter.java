package com.pallav.karrierexample;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class LoginDataBaseAdapter 
{
	static final String DATABASE_NAME = "login.db";
	static final int DATABASE_VERSION = 1;
	public static final int NAME_COLUMN = 1;
	// TODO: Create public field for each column in your table.
	// SQL Statement to create a new database.
	static final String DATABASE_CREATE = "create table LOGIN " +
			"( ID integer primary key autoincrement, USERNAME  text unique,PASSWORD text, LATITUDE text, LONGITUDE text, ADDRESS text)";
	// Variable to hold the database instance
	public  SQLiteDatabase db;
	// Context of the application using the database.
	private final Context context;
	// Database open/upgrade helper
	private DataBaseHelper dbHelper;
	public String password;

	public String userName,address;

	
	


	public  LoginDataBaseAdapter(Context _context) 
	{
		context = _context;
		dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public  LoginDataBaseAdapter open() throws SQLException 
	{
		db = dbHelper.getWritableDatabase();
		return this;
	}
	public void close() 
	{
		db.close();
	}

	public  SQLiteDatabase getDatabaseInstance()
	{
		return db;
	}
	public boolean checkEvent(String userName) 
	{
		SQLiteDatabase db = this.getDatabaseInstance();
		Cursor cursor=db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);

		if(cursor.moveToFirst())

			return true; //row exists

		else 
			return false;

	}



	public void insertEntry(String userName,String password)
	{


		// Assign values for each row.
		if(checkEvent(userName)) 
		{ Toast.makeText(context,"Account already exists",Toast.LENGTH_LONG).show();
		}

		else
		{
			ContentValues newValues = new ContentValues();

			newValues.put("USERNAME", userName);
			newValues.put("PASSWORD",password);

			db.insert("LOGIN", null, newValues);
			Toast.makeText(context, "Account Successfully Created ", Toast.LENGTH_LONG).show();

		}
	
	}
	


	//}
	public void insertDetails(Double latitude,Double longitude, String address)
	{
		ContentValues newDetails = new ContentValues();
		newDetails.put("LONGITUDE",longitude);
		newDetails.put("ADDRESS",address);
		newDetails.put("LATITUDE", latitude);
		db.insert("LOGIN",null, newDetails);
		Toast.makeText(context, "Details inserted succesfully", Toast.LENGTH_LONG).show();


	}
	


	public String getSinlgeEntry(String userName)
	{
		Cursor cursor=db.query("LOGIN", null, "USERNAME ='"+userName+"'", null, null, null, null);
		String password = "";
		if(cursor.getCount() > 0) // UserName Not Exist
		{
			cursor.moveToFirst();
			password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
			return password;
		}
		return "NOT EXIST";	
	}
	public boolean  updateData(String userName,String password, Double latitude, Double longitude, String address)
	{
		db = dbHelper.getWritableDatabase();
		// Define the updated row content.
		ContentValues updatedValues = new ContentValues();
		// Assign values for each row.
		updatedValues.put("USERNAME", userName);
		updatedValues.put("PASSWORD",password);
		updatedValues.put("LATITUDE",latitude);
		updatedValues.put("LONGITUDE",longitude);
		updatedValues.put("ADDRESS",address);

		String where="USERNAME = ?";


		//int i = db.update("LOGIN",updatedValues, where, new String[]{userName});	
		Toast.makeText(context, "Account Successfully updated", Toast.LENGTH_LONG).show();

		//  return db.update("LOGIN",updatedValues, where, new String[]{userName})!= 0;

		return db.update("LOGIN",updatedValues, null,null)!= 0;
	}
	
	/* public class PostDataAsyncTask extends AsyncTask<String, String, String> {
		 
	        protected void onPreExecute() {
	            super.onPreExecute();
	            // do stuff before posting data
	        }
	 
	        @Override
	        protected String doInBackground(String... strings) {
	            try {
	 
	                // 1 = post text data, 2 = post file
	                int actionChoice = 2;
	                 
	                // post a text data
	                if(actionChoice==1){
	                    postText();
	                }
	                 
	                // post a file
	                else{
	                    postFile();
	                }
	                 
	            } catch (NullPointerException e) {
	                e.printStackTrace();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return null;
	        }
	 
	        protected void onPostExecute(String lenghtOfFile) {
	            // do stuff after posting data
	        }
	    }
	     
	    // this will post our text data
	    private void postText(){
	        try{
	            // url where the data will be posted
	            String postReceiverUrl = "http://192.168.0.102/post_data_receiver.php";
	            
	             
	            // HttpClient
	            HttpClient httpClient = new DefaultHttpClient();
	             
	            // post header
	            HttpPost httpPost = new HttpPost(postReceiverUrl);
	     
	            // add your data
	            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	            nameValuePairs.add(new BasicNameValuePair("Username", userName));
	            nameValuePairs.add(new BasicNameValuePair("password", password));
	            nameValuePairs.add(new BasicNameValuePair("latitude", lat));
	            nameValuePairs.add(new BasicNameValuePair("longitude", lon));	             
	            nameValuePairs.add(new BasicNameValuePair("address", address));
	     
	            // execute HTTP post request
	            HttpResponse response = httpClient.execute(httpPost);
	            HttpEntity resEntity = response.getEntity();
	             
	            if (resEntity != null) {
	                 
	                String responseStr = EntityUtils.toString(resEntity).trim();
	               
	                 
	                // you can add an if statement here and do other actions based on the response
	            }
	             
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	     
	   
	    private void postFile(){
	        try{
	             
	            // the file to be posted
	            String textFile = Environment.getExternalStorageDirectory() + "/sample.txt";
	           
	             
	            // the URL where the file will be posted
	            String postReceiverUrl = "http://yourdomain.com/post_data_receiver.php";
	            Log.v(null, "postURL: " + postReceiverUrl);
	             
	            // new HttpClient
	            HttpClient httpClient = new DefaultHttpClient();
	             
	            // post header
	            HttpPost httpPost = new HttpPost(postReceiverUrl);
	             
	            File file = new File(textFile);
	            FileBody fileBody = new FileBody(file);
	     
	            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	            reqEntity.addPart("file", fileBody);
	            httpPost.setEntity(reqEntity);
	             
	            // execute HTTP post request
	            HttpResponse response = httpClient.execute(httpPost);
	            HttpEntity resEntity = response.getEntity();
	     
	            if (resEntity != null) {
	                 
	                String responseStr = EntityUtils.toString(resEntity).trim();
	               
	                 
	                // you can add an if statement here and do other actions based on the response
	            }
	             
	        } catch (NullPointerException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }*/
}
	
	


