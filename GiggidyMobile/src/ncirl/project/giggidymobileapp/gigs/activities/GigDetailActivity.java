package ncirl.project.giggidymobileapp.gigs.activities;


import com.example.projectattempt.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import static ncirl.project.giggidymobileapp.gigs.activities.GigListActivity.EXTRA_ARTIST_INFO;
import static ncirl.project.giggidymobileapp.gigs.activities.GigListActivity.EXTRA_DATE_INFO;
import static ncirl.project.giggidymobileapp.gigs.activities.GigListActivity.EXTRA_VENUE_INFO;

public class GigDetailActivity extends Activity{

	TextView artistsName;
	TextView gigDate;
	Button venueName;
	String headliner;
	String date;
	String venue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gig_detail);
		
		
		Intent intent = getIntent();
		if (null != intent) {
			headliner = intent.getStringExtra(EXTRA_ARTIST_INFO);
			date = intent.getStringExtra(EXTRA_DATE_INFO);
			venue = intent.getStringExtra(EXTRA_VENUE_INFO);
		}
		
		
		artistsName = (TextView) findViewById(R.id.tvHeadliner);
		artistsName.setText(headliner);
		
		gigDate = (TextView) findViewById(R.id.tvGigDate);
		gigDate.setText(date);
		
		venueName = (Button) findViewById(R.id.bttnVenue);
		venueName.setText(venue);
		
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gig_detail, menu);
		return true;
	}



}
