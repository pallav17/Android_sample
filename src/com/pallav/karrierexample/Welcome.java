package com.pallav.karrierexample;








import com.pallav.karrierexample.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;




public class Welcome extends Activity 
{

	Intent in;


	Intent logout;


	Cursor cursor;
	SessionManagement session;
	LoginDataBaseAdapter loginDataBaseAdapter;
	String us;

	SQLiteDatabase db; 
	String address;
	double lat;
	WebView webView1;
	double lon;
	GPS1 mGPSService;
	Button btnCheckin;
	Button btnLogout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);




		loginDataBaseAdapter=new LoginDataBaseAdapter(this);
		loginDataBaseAdapter=loginDataBaseAdapter.open();


		session = new SessionManagement(getApplicationContext());

		TextView tv1 =(TextView)findViewById(R.id.tv1);
		btnCheckin = (Button) findViewById(R.id.btnCheckin);

		btnLogout = (Button) findViewById(R.id.btnLogout);



		in = this.getIntent();
		final String name = in.getStringExtra("Username");

		tv1.setText("Welcome " +name);



		webView1 = (WebView)findViewById(R.id.webView1);





		mGPSService = new GPS1(getApplicationContext());
		mGPSService.getLocation();

		if (mGPSService.isLocationAvailable == false) {


			Toast.makeText(getApplicationContext(), "Your location is not available, please try again.", Toast.LENGTH_SHORT).show();
			return;


		} else {


			lat = mGPSService.getLatitude();
			lon = mGPSService.getLongitude();


			address = mGPSService.getLocationAddress();


		}



		mGPSService.closeGPS();



		startWebView("http://maps.google.com?q= "+ lat + "," + lon + " ");
		Toast.makeText(getApplicationContext(),address ,3000).show();
		
		
		

	}

	private void startWebView(String url) {



		webView1.setWebViewClient(new WebViewClient() {      
			ProgressDialog progressDialog;


			public boolean shouldOverrideUrlLoading(WebView view, String url) {              
				view.loadUrl(url);
				return true;
			

			}	
			public void onLoadResource (WebView view, String url) {
				
				
			}
			public void onPageFinished(WebView view, String url) {

			}

		}); 

		webView1.getSettings().setJavaScriptEnabled(true); 

		webView1.loadUrl(url);
		




		






		


		btnCheckin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				/*SessionManagement session = new SessionManagement(Welcome.this);
				String user = session.GetUserName();
				String pass = session.GetPassword();*/
				loginDataBaseAdapter.updateData(null,null, lat, lon, address);
			
			}
		});

		btnLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				//logout = new Intent (getApplicationContext(), HomeActivity.class);
				// startActivity(logout);

				session.logoutUser();	
			}
		});



	}

	@Override
	// Detect when the back button is pressed
	public void onBackPressed() {

		super.onBackPressed();
	}



	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		loginDataBaseAdapter.close();
	}
}
