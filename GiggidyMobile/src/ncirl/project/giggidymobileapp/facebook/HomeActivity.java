package ncirl.project.giggidymobileapp.facebook;

import com.example.projectattempt.R;
import com.example.projectattempt.R.id;
import com.example.projectattempt.R.layout;
import com.example.projectattempt.R.menu;
import com.parse.ParseUser;

import ncirl.project.giggidymobile.lastfm.GigListActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity {

	Button gigListButton;
	Button plannerButton;
	Button profileButton;
	Button logoutButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		gigListButton = (Button) findViewById(R.id.gigListButton);
		plannerButton = (Button) findViewById(R.id.plannerButton);
		profileButton = (Button) findViewById(R.id.profileButton);
		logoutButton = (Button) findViewById(R.id.logoutButton);

		gigListButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goToGigListScreen();

			}

		});

		plannerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goToPlannerScreen();

			}

		});

		profileButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goToProfileScreen();

			}

		});

		logoutButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onLogoutButtonClicked();

			}

		});

	}

	private void goToGigListScreen() {
		Intent intent = new Intent(this, GigListActivity.class);
		startActivity(intent);

	}

	private void goToPlannerScreen() {
		Intent intent = new Intent(this, PlannerActivity.class);
		startActivity(intent);

	}

	private void goToProfileScreen() {
		Intent intent = new Intent(this, UserProfileActivity.class);
		startActivity(intent);

	}

	private void onLogoutButtonClicked() {
		ParseUser.logOut();
		Intent intent = new Intent(this, FacebookLoginActivity.class);
		startActivity(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
