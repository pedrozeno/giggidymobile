package ncirl.project.giggidymobileapp.gigs.activities;


import ncirl.project.giggidymobileapp.utils.AppController;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.projectattempt.R;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import static ncirl.project.giggidymobileapp.gigs.activities.GigListActivity.EXTRA_ARTIST_INFO;
import static ncirl.project.giggidymobileapp.gigs.activities.GigListActivity.EXTRA_DATE_INFO;
import static ncirl.project.giggidymobileapp.gigs.activities.GigListActivity.EXTRA_VENUE_INFO;
import static ncirl.project.giggidymobileapp.gigs.activities.GigListActivity.EXTRA_ARTIST_IMAGE_INFO;
//import static ncirl.project.giggidymobileapp.gigs.activities.GigListActivity.EXTRA_GIG_ID_INFO;

public class GigDetailActivity extends Activity{
	

	private RequestQueue myQueue;
	private ImageLoader myImageLoader;

	TextView artistsName;
	TextView gigDate;
	Button venueName;
	Button attendButton;
	NetworkImageView artistImgUrl;
	String headliner;
	String date;
	String venue;
	String image;
	String gigId;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gig_detail);
		
		//Get data sent from GigListActivity
		Intent intent = getIntent();
		if (null != intent) {
			headliner = intent.getStringExtra(EXTRA_ARTIST_INFO);
			venue = intent.getStringExtra(EXTRA_VENUE_INFO);
			date = intent.getStringExtra(EXTRA_DATE_INFO);
			image = intent.getStringExtra(EXTRA_ARTIST_IMAGE_INFO);
			//gigId = intent.getStringExtra(EXTRA_GIG_ID_INFO);

			
			// Initialise RequestQueue and ImageLoader
			myQueue = AppController.getInstance().getRequestQueue();
			myImageLoader = AppController.getInstance().getImageLoader();
			
			onCreateParse();
		}
		
		
		artistsName = (TextView) findViewById(R.id.tvHeadliner);
		artistsName.setText(headliner);
		
		gigDate = (TextView) findViewById(R.id.tvGigDate1);
		gigDate.setText(date);
		
		venueName = (Button) findViewById(R.id.bttnVenueName);
		venueName.setText(venue);
		
		artistImgUrl = (NetworkImageView) findViewById(R.id.ivArtistImage);
		artistImgUrl.setImageUrl(image, myImageLoader);
		
		attendButton = (Button) findViewById(R.id.bttnAttend);
		
		attendButton.setOnClickListener(new OnClickListener(){
			
		

			@Override
			public void onClick(View v) {
				ParseObject attendee = new ParseObject("GigAttendees");
				attendee.put("gig_Id", gigId);
				attendee.put("artist", headliner);
				attendee.put("venue", venue);
				attendee.put("attendee", ParseUser.getCurrentUser());
				attendee.saveInBackground();
				
			}
			
			
		});

		
		
		
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gig_detail, menu);
		return true;
	}
	
	public void onCreateParse() {

		Parse.initialize(this, "gS91h8EixiaBiHbFk1up4z44VV2pjFBil7DsIUVr",
				"jwvWqF1C1cjNDp6qcU8922t1FuCngtFLO2o3mP3H");

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();

		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);

	}



}
