package com.pallav.karrierexample;




import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.pallav.karrierexample.*;
public class SessionManagement {
	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;

	// Context
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Sharedpref file name
	private static final String PREF_NAME = "pallavPref";

	// All Shared Preferences Keys
 public static final String IS_LOGIN = "IsLoggedIn";
 public static final String IS_LOGOFF = "IsLoggedOff";

	// User name (make variable public to access from outside)
	public static final String KEY_NAME = "username";

	// Email address (make variable public to access from outside)
	public static final String KEY_PASSWORD = "password";

	// Constructor
	public SessionManagement(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	/**
	 * Create login session
	 * */
	public void createLoginSession(String userName, String password)
	{
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);

		// Storing name in pref
		editor.putString(KEY_NAME,userName);

		// Storing email in pref
		editor.putString(KEY_PASSWORD, password);

		// commit changes
		editor.commit();
	} 
	
	public String GetUserName()
	{
		return pref.getString(KEY_NAME,"");
	}
	
	public String GetPassword()
	{
		return pref.getString(KEY_PASSWORD,"");
	}
	 public void checkLogin(){
	        // Check login status
	        if(!this.isLoggedIn()){
	            // user is not logged in redirect him to Login Activity
	            Intent i = new Intent(_context, HomeActivity.class);
	            // Closing all the Activities
	            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	             
	            // Add new Flag to start new Activity
	            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	             
	            // Staring Login Activity
	            _context.startActivity(i);
	        }
	 }
	        public boolean isLoggedIn(){
	            return pref.getBoolean(IS_LOGIN, false);
	        } 
	        
	        
	        public void logoutUser(){
	            // Clearing all data from Shared Preferences
	            editor.clear();
	            editor.commit();
	            if(!this.isLoggedOff()){
	             
	            // After logout redirect user to Loing Activity
	            	
	            Intent i = new Intent(_context, HomeActivity.class);
	            // Closing all the Activities
	            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	           
	            // Add new Flag to start new Activity
	            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	             
	            // Staring Login Activity
	           
	            _context.startActivity(i);
	            
	           
	            }
	        }
	        public boolean isLoggedOff(){
	            return pref.getBoolean(IS_LOGOFF, false);

}
}
