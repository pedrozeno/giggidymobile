package ncirl.project.giggidymobileapp.facebook;

import java.util.Arrays;
import java.util.List;

import ncirl.project.giggidymobileapp.utils.AppController;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.projectattempt.HomeActivity;
import com.example.projectattempt.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class FacebookLoginActivity extends Activity {

	private Button loginButton;
	private Dialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		loginButton = (Button) findViewById(R.id.authButton);
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLoginButtonClicked();
			}
		});

		// Check if user is already logged on and connected to Facebook
		ParseUser user = ParseUser.getCurrentUser();
		if ((user != null) && ParseFacebookUtils.isLinked(user)) {
			// Go to the user info activity
			goToHomeScreen();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}

	private void onLoginButtonClicked() {
		progressDialog = ProgressDialog.show(this, "Please Wait",
				"Logging in...", true);

		// Basic Information that can be obtained from Facebook
		List<String> permissions = Arrays.asList("public_profile", "email",
				"user_friends");

		ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException err) {
				FacebookLoginActivity.this.progressDialog.dismiss();
				if (user == null) {
					Log.d(AppController.TAG,
							"The user must have cancelled before login completed!");
				} else if (user.isNew()) {
					Log.d(AppController.TAG,
							"New user. Go to user account activity");
					goToUserSetUp();
				} else {
					Log.d(AppController.TAG,
							"Existing user. Go straight to gig list activity");
					goToHomeScreen();
				}
			}

		});
	}

	private void goToHomeScreen() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}

	private void goToUserSetUp() {
		Intent intent = new Intent(this, UserProfileActivity.class);
		startActivity(intent);

	}

}
