package ncirl.project.giggidymobileapp.parse.logon;

import ncirl.project.giggidymobileapp.gigs.activities.GigListActivity;

import com.example.projectattempt.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.text.TextUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity {

	private EditText usernameEditTxt;
	private EditText password1EditText;
	private EditText password2EditText;
	private EditText emailEditText;
	private Button submitButtonEditText;

	private String mUsername;
	private String mPassword1;
	private String mPassword2;
	private String mEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		usernameEditTxt = (EditText) findViewById(R.id.suUsernameTxt);
		password1EditText = (EditText) findViewById(R.id.suPasswordTxt);
		password2EditText = (EditText) findViewById(R.id.suConfirmPasswordTxt);
		emailEditText = (EditText) findViewById(R.id.suEmailTxt);
		submitButtonEditText = (Button) findViewById(R.id.suSubmitButton);

		submitButtonEditText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				createAccount();
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

			private void createAccount() {

				mUsername = usernameEditTxt.getText().toString();
				mPassword1 = password1EditText.getText().toString();
				mPassword2 = password2EditText.getText().toString();
				mEmail = emailEditText.getText().toString();

				// Check first password
				if (TextUtils.isEmpty(mPassword1)) {

					Toast.makeText(getApplicationContext(),
							"Please enter a password", Toast.LENGTH_SHORT)
							.show();

				} else if (mPassword1.length() < 5) {

					Toast.makeText(
							getApplicationContext(),
							"Please enter a password with a minimum of 5 characters",
							Toast.LENGTH_SHORT).show();

				}
				// Check first confirm password
				if (TextUtils.isEmpty(mPassword2)) {

					Toast.makeText(getApplicationContext(),
							"Please enter a password", Toast.LENGTH_SHORT)
							.show();

				} else if (mPassword1 != null && !mPassword2.equals(mPassword1)) {

					Toast.makeText(getApplicationContext(),
							"Passwords don't match", Toast.LENGTH_SHORT).show();
				}
				// Check email
				if (TextUtils.isEmpty(mEmail)) {

					Toast.makeText(getApplicationContext(),
							"Please enter an email", Toast.LENGTH_SHORT).show();

				} else if (!mEmail.contains("@")) {

					Toast.makeText(getApplicationContext(), "Invalid email",
							Toast.LENGTH_SHORT).show();
				}

				if (TextUtils.isEmpty(mUsername)) {

					Toast.makeText(getApplicationContext(), "Enter a username",
							Toast.LENGTH_SHORT).show();

				}

				else {

					signUp(mUsername.toLowerCase(), mEmail, mPassword1);
				}

			}

			private void signUp(String lowerCase, String mEmail,
					String mPassword1) {

				ParseUser user = new ParseUser();
				user.setUsername(mUsername);
				user.setPassword(mPassword1);
				user.setEmail(mEmail);

				user.signUpInBackground(new SignUpCallback() {

					@Override
					public void done(ParseException e) {

						if (e == null) {
							Toast.makeText(getApplicationContext(),
									"Welcome to giigidy " + mUsername + "!",
									Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(SignUpActivity.this,
									GigListActivity.class);
							startActivity(intent);
						} else {

							displayAlertDialog(SignUpActivity.this, "Error",
									"Account already exists", false);
						}

					}

				});

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
		getMenuInflater().inflate(R.menu.sign_up, menu);
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
}
