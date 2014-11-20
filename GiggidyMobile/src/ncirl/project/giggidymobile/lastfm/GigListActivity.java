package ncirl.project.giggidymobile.lastfm;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import ncirl.project.giggidymobileapp.utils.AppController;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.projectattempt.R;

public class GigListActivity extends Activity {

	public final static String EXTRA_ARTIST_INFO = "ncirl.project.giggidymobileapp.gigs.activities.ARTIST";
	public final static String EXTRA_ALL_ARTISTS_INFO = "ncirl.project.giggidymobileapp.gigs.activities.ALL_ARTIST";
	public final static String EXTRA_ARTIST_IMAGE_INFO = "ncirl.project.giggidymobileapp.gigs.activities.ARTIST_IMAGE";
	public final static String EXTRA_DATE_INFO = "ncirl.project.giggidymobileapp.gigs.activities.DATE";
	public final static String EXTRA_VENUE_INFO = "ncirl.project.giggidymobileapp.gigs.activities.VENUE";

	private String TAG = this.getClass().getSimpleName();
	private ListView lstView;
	private List<GigsModel> gigsArr = new ArrayList<GigsModel>();
	private ListViewAdapter adapter;
	private ProgressDialog pDialog;
	private RequestQueue myQueue;
	LastFmHelper lstHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gig_list);


		gigsArr = new ArrayList<GigsModel>();
		adapter = new ListViewAdapter(this, gigsArr);
		lstView = (ListView) findViewById(R.id.gigListView);
		lstView.setAdapter(adapter);

		lstHelper = new LastFmHelper();

		// Initialise RequestQueue and ImageLoader
		myQueue = AppController.getInstance().getRequestQueue();


		// Start Progress Dialog
		pDialog = ProgressDialog.show(this, "Synchronizing", "Please Wait...");

		// Volley JSONObject Request
		JsonObjectRequest request = null;
		try {
			request = new JsonObjectRequest(Method.GET,
					lstHelper.getGigInfo("dublin"), null,
					new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							Log.i(TAG, response.toString());
							parseJSON(response);
							adapter.notifyDataSetChanged();
							pDialog.dismiss();
						}

					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Log.i(TAG, error.getMessage());
						}
					});
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Add response to Volley RequestQueue
		myQueue.add(request);

		// Open New Page when clicking on item in list
		lstView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(GigListActivity.this,
						GigDetailActivity.class);
				GigsModel gm = (GigsModel) adapter.getItem(position);
				intent.putExtra(EXTRA_ARTIST_INFO, gm.getGigHeadliner()
						.toString());
				intent.putExtra(EXTRA_VENUE_INFO, gm.getGigVenue().toString());
				intent.putExtra(EXTRA_DATE_INFO, gm.getGigDate().toString());
				intent.putExtra(EXTRA_ARTIST_IMAGE_INFO, gm.getArtistImgURL()
						.toString());
				// intent.putExtra(EXTRA_GIG_ID_EXTRA,
				// gm.getGigId().toString());
				startActivity(intent);

			}

		});

	}

	// /Parse JSON Response
	@SuppressWarnings("unchecked")
	private void parseJSON(JSONObject json) {

		try {
			JSONObject events = json.getJSONObject("events");
			JSONArray event = events.getJSONArray("event");
			for (int i = 0; i < event.length(); i++) {

				JSONObject item = event.getJSONObject(i);
				GigsModel model = new GigsModel();
				model.setGigId(item.getString("id"));
				JSONObject artistObj = item.getJSONObject("artists");
				model.setGigHeadliner(artistObj.getString("headliner"));
				model.setGigDate(item.getString("startDate").substring(0, 16));
				JSONObject venueObj = item.getJSONObject("venue");
				model.setGigVenue(venueObj.getString("name"));

				JSONArray imageArr = item.getJSONArray("image");
				model.setArtistImgURL(imageArr.getJSONObject(
						imageArr.length() - 2).getString("#text"));

				gigsArr.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
