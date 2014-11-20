package ncirl.project.giggidymobileapp.facebook;

import ncirl.project.giggidymobileapp.parse.LoginActivity;
import ncirl.project.giggidymobileapp.utils.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectattempt.R;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class UserProfileActivity extends Activity {
	
	private ProfilePictureView userProfileImage;
	private TextView userNameTextView;
	private TextView userEmailTextView;
	private TextView userGenderTextView;
	private Button logoutButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		
		userProfileImage = (ProfilePictureView) findViewById(R.id.profileImageView);
		userNameTextView = (TextView) findViewById(R.id.usernameTv);
		userEmailTextView = (TextView) findViewById(R.id.emailTv);
		userGenderTextView = (TextView) findViewById(R.id.genderTv);
		logoutButton = (Button) findViewById(R.id.logoutButton);
		
		logoutButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				logoutButtonClicked();
				
			}

			
			
			
		});
		
		// Fetch Facebook user info if the session is active
				Session session = ParseFacebookUtils.getSession();
				if (session != null && session.isOpened()) {
					makeMeRequest();
				}
			}


private void makeMeRequest() {
	Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
		new Request.GraphUserCallback() {
			@Override
			public void onCompleted(GraphUser user, Response response) {
				if (user != null) {
					// Create a JSON object to hold the profile info
					JSONObject userProfile = new JSONObject();
					try {
						// Populate the JSON object
						userProfile.put("facebookId", user.getId());
						userProfile.put("name", user.getName());
						
						if (user.getProperty("gender") != null) {
							userProfile.put("gender", (String) user.getProperty("gender"));
						}
						
						if (user.getProperty("email") != null) {
							userProfile.put("email", (String) user.getProperty("email"));
						}

						// Save the user profile info in a user property
						ParseUser currentUser = ParseUser.getCurrentUser();
						currentUser.put("profile", userProfile);
						currentUser.saveInBackground();

						// Show the user info
						updateUserProfile();
					} catch (JSONException e) {
						Log.d(AppController.TAG, "Error parsing returned user data. " + e);
					}

				} else if (response.getError() != null) {
					if ((response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY) || 
						(response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
						Log.d(AppController.TAG, "The facebook session was invalidated." + response.getError());
						logoutButtonClicked();
					} else {
						Log.d(AppController.TAG, 
							"Some other error: " + response.getError());
					}
				}
			}
		}
	);
	request.executeAsync();
}
	
	@Override
	public void onResume() {
		super.onResume();

		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			//If the user is logged in display info
			updateUserProfile();
		} else {
			//If the user is not logged in, go to activity view
			goToLogonActivity();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_account, menu);
		return true;
	}
	
	private void updateUserProfile() {
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser.has("profile")) {
			JSONObject userProfile = currentUser.getJSONObject("profile");
			try {
				
				if (userProfile.has("facebookId")) {
					userProfileImage.setProfileId(userProfile.getString("facebookId"));
				} else {
					// Show the default, blank user profile picture
					userProfileImage.setProfileId(null);
				}
				
				if (userProfile.has("name")) {
					userNameTextView.setText(userProfile.getString("name"));
				} else {
					userNameTextView.setText("");
				}
				
				if (userProfile.has("gender")) {
					userGenderTextView.setText(userProfile.getString("gender"));
				} else {
					userGenderTextView.setText("");
				}

				if (userProfile.has("email")) {
					userEmailTextView.setText(userProfile.getString("email"));
				} else {
					userEmailTextView.setText("");
				}
				
			} catch (JSONException e) {
				Log.d(AppController.TAG, "Error parsing saved user data.");
			}
		}
	}
	
	
	//Logout
	private void logoutButtonClicked() {
		ParseUser.logOut();
		goToLogonActivity();
	}

	//Go back to login activity
	private void goToLogonActivity() {
		Intent intent = new Intent(this, FacebookLoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//If activity is still running bring it to top of stack
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
		
	
}



