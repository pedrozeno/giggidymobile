package ncirl.project.giggidymobileapp.parse.logon;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

import android.app.Application;

public class ParseHelper extends Application {
	
	 @Override
	    public void onCreate() {
	        super.onCreate();
	 
	        
	        Parse.initialize(this, "gS91h8EixiaBiHbFk1up4z44VV2pjFBil7DsIUVr", 
	        		"jwvWqF1C1cjNDp6qcU8922t1FuCngtFLO2o3mP3H");
	 
	        ParseUser.enableAutomaticUser();
	        ParseACL defaultACL = new ParseACL();
	 
	        
	        defaultACL.setPublicReadAccess(true);
	 
	        ParseACL.setDefaultACL(defaultACL, true);
	    }
	 
	}