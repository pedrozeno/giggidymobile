package ncirl.project.giggidymobileapp.facebook;

import com.example.projectattempt.R;
import com.example.projectattempt.R.layout;
import com.example.projectattempt.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class PlannerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_planner);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.planner, menu);
		return true;
	}

	
}
