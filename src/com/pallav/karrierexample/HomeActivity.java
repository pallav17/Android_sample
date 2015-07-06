package com.pallav.karrierexample;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends Activity 
{
	Button btnSignIn,btnSignUp;
	LoginDataBaseAdapter loginDataBaseAdapter;
	 SessionManagement session;
	 public String userName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.main);
	     session = new SessionManagement(getApplicationContext());
	  
	     loginDataBaseAdapter=new LoginDataBaseAdapter(this);
	     loginDataBaseAdapter=loginDataBaseAdapter.open();
	     
	     
	     btnSignIn=(Button)findViewById(R.id.buttonSignIN);
	     btnSignUp=(Button)findViewById(R.id.buttonSignUP);
			
	     if(session.isLoggedIn())
	     {
	    	 Intent intent = new Intent(getApplicationContext(), Welcome.class);
	    	 intent.putExtra("Username", "temp");
	    	 startActivity(intent);
	     }
	     else if(session.isLoggedOff())
	     {
	    	
	     Intent intent1 = new Intent(getApplicationContext(), HomeActivity.class);
	      
	     
	     }
	     
	    // Set OnClick Listener on SignUp button 
	    btnSignUp.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			/// Create Intent for SignUpActivity  and Start The Activity
			Intent intentSignUP=new Intent(getApplicationContext(),SignUPActivity.class);
			startActivity(intentSignUP);
			}
		});
	}
	
	public void signIn(View V)
	   {
			final Dialog dialog = new Dialog(HomeActivity.this);
			dialog.setContentView(R.layout.login);
		    dialog.setTitle("Login");
	
		    
		    final  EditText editTextUserName=(EditText)dialog.findViewById(R.id.editTextUserNameToLogin);
		    final  EditText editTextPassword=(EditText)dialog.findViewById(R.id.editTextPasswordToLogin);
		    
			Button btnSignIn=(Button)dialog.findViewById(R.id.buttonSignIn);
				
			// Set On ClickListener
			btnSignIn.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// get The User name and Password
					 userName=editTextUserName.getText().toString();
					String password=editTextPassword.getText().toString();
					
					// fetch the Password form database for respective user name
					String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);
					
					// check if the Stored password matches with  Password entered by user
					if(password.equals(storedPassword))
					{
						
						session.createLoginSession(userName,password);
						//Toast.makeText(HomeActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
						Intent intentwelcome = new Intent(getApplicationContext(),Welcome.class);
						
						 userName =editTextUserName.getText().toString();
						intentwelcome.putExtra("Username", userName);
						
						startActivity(intentwelcome);
						
						//dialog.dismiss();
					}
					else
					{
						Toast.makeText(HomeActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
					}
				}
			});
			
			dialog.show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	    // Close The Database
		loginDataBaseAdapter.close();
	}
}
