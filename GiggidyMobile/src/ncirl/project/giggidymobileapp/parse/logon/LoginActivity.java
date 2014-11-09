package ncirl.project.giggidymobileapp.parse.logon;

import ncirl.project.giggidymobileapp.gigs.activities.GigListActivity;
import ncirl.project.giggidymobileapp.utils.InternetDetector;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectattempt.R;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * @author paddybyrne x11101474
 *         https://github.com/zohaibbrohi/LoginUsingParseSdk example referenced
 *         for this activity
 * 
 */
public class LoginActivity extends Activity {

	Context context;
	Boolean hasInternet;
	InternetDetector id;

	Button logonBttn;
	Button signUpBttn;
	EditText usernameTxt;
	EditText passwordTxt;

	String username;
	String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		onCreateParse();

		logonBttn = (Button) findViewById(R.id.buttonlogon);
		signUpBttn = (Button) findViewById(R.id.buttonsignup);
		usernameTxt = (EditText) findViewById(R.id.usernametext);
		passwordTxt = (EditText) findViewById(R.id.passwordtext);

		// Clicks login Button
		logonBttn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				attemptSignIn();

				/*
				 * // Checks for Internet Access hasInternet =
				 * id.isConnectedToInternet();
				 * 
				 * // If Internet is present if (hasInternet) { attemptSignIn();
				 * 
				 * // If not present, display dialog. } else {
				 * displayAlertDialog(LoginActivity.this, "Connection Error",
				 * "No Internet connection found!", false);
				 * 
				 * }
				 */

			}

		});

		signUpBttn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Load SignUp page
				Intent intent = new Intent(LoginActivity.this,
						SignUpActivity.class);
				startActivity(intent);
			}

		});

	}

	private void attemptSignIn() {

		String username = usernameTxt.getText().toString();
		String password = passwordTxt.getText().toString();

		if (TextUtils.isEmpty(username)) {

			Toast.makeText(getApplicationContext(), "Must Enter Username",
					Toast.LENGTH_SHORT).show();

		} else if (TextUtils.isEmpty(password)) {

			Toast.makeText(getApplicationContext(), "Must Enter Password",
					Toast.LENGTH_SHORT).show();

		} else {

			login(username.toLowerCase(), password);

		}

	}

	private void login(String lowerCase, String password) {

		ParseUser.logInInBackground(lowerCase, password, new LogInCallback() {

			@Override
			public void done(ParseUser user, ParseException e) {

				if (e == null) {
					Toast.makeText(getApplicationContext(),
							"Welcome back " + username + "!",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(LoginActivity.this,
							GigListActivity.class);
					startActivity(intent);
				} else {

					displayAlertDialog(LoginActivity.this, "Login Error",
							"Invalid Username or Password", false);
				}

			}

		});

	}

	@SuppressWarnings("deprecation")
	public void displayAlertDialog(Context context, String title,
			String message, Boolean status) {
		AlertDialog alertBuilder = new AlertDialog.Builder(context).create();

		// Alert title
		alertBuilder.setTitle(title);

		// Alert message
		alertBuilder.setMessage(message);

		alertBuilder.setButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}

		});
		// Display dialog
		alertBuilder.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logon, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
