package ncirl.project.giggidymobileapp.gigs.activities;


import java.util.ArrayList;

import ncirl.project.giggidymobileapp.gigs.models.GigsModel;
import ncirl.project.giggidymobileapp.utils.VolleySingleton;
import ncirl.project.giggidymobileapp.gigs.helpers.LastFmHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.projectattempt.R;



public class GigListActivity extends Activity {
	
	public final static String EXTRA_ARTIST_INFO = "ncirl.project.giggidymobileapp.gigs.activities.ARTIST";
	public final static String EXTRA_DATE_INFO = "ncirl.project.giggidymobileapp.gigs.activities.DATE";
	public final static String EXTRA_VENUE_INFO = "ncirl.project.giggidymobileapp.gigs.activities.VENUE";
	
	
	private String TAG = this.getClass().getSimpleName();
    private ListView lstView;
    private ArrayList<GigsModel> gigsArr;
    private LayoutInflater inflator;
    private VolleyAdapter adapter;
    private ProgressDialog pDialog;
    private RequestQueue myQueue;
    private ImageLoader myImageLoader;
    LastFmHelper lstHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gig_list);
        inflator = LayoutInflater.from(this);
        
        gigsArr = new ArrayList<GigsModel>();
        adapter = new VolleyAdapter();
        lstView = (ListView) findViewById(R.id.gigListView);
        lstView.setAdapter(adapter);
        
        lstHelper = new LastFmHelper();

        //Initialise RequestQueue and ImageLoader
        myQueue = VolleySingleton.getInstance().getRequestQueue();
        myImageLoader = VolleySingleton.getInstance().getImageLoader(); 
       
        
        //Start Progress Dialog
        pDialog = ProgressDialog.show(this,"Synchronizing","Please Wait...");
        
        //Volley JSONObject Request
		JsonObjectRequest jr = new JsonObjectRequest(Method.GET,
				lstHelper.getGigInfo("london"), null,
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

		//Add response to Volley RequestQueue
		myQueue.add(jr);
		
		
		//Open New Page when clicking on item in list
		lstView.setOnItemClickListener(new OnItemClickListener(){



			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent = new Intent(GigListActivity.this, GigDetailActivity.class);
				GigsModel gm = (GigsModel) adapter.getItem(position);
				intent.putExtra(EXTRA_ARTIST_INFO, gm.getGigTitle().toString());
				intent.putExtra(EXTRA_DATE_INFO, gm.getGigDate().toString());
				intent.putExtra(EXTRA_VENUE_INFO, gm.getGigVenue().toString());
				
				startActivity(intent);
				
			}
			
			
		});

	}
    
    ///Parse JSON Response
    private void parseJSON(JSONObject json){
    	
        try{
            JSONObject events = json.getJSONObject("events");
            JSONArray event = events.getJSONArray("event");
            for(int i=0;i<event.length();i++){

                    JSONObject item = event.getJSONObject(i);
                    GigsModel gm = new GigsModel();
                    JSONObject artistArr = item.getJSONObject("artists");
                    gm.setGigTitle(artistArr.getString("headliner"));
                    gm.setGigVenue(item.getString("startDate"));
                    JSONObject venueObj = item.getJSONObject("venue");
					gm.setGigDate(venueObj.getString("name"));
					gm.setArtistImg("http://cnet4.cbsistatic.com/hub/i/2011/10/27/" +
							"a66dfbb7-fdc7-11e2-8c7c-d4ae52e62bcc/android-wallpaper5_2560x1600_1.jpg");
                  
					
					
					/*  try {
						JSONArray imageUrls = events.getJSONArray("image");
						
						for(int j=0; j<imageUrls.length(); j++) {
							JSONObject imageObj = imageUrls.getJSONObject(j);
							//gm.setArtistImg(imageObj.getString("#text"));
							
							if(imageObj.getString("size").equals("medium")) {
								break;
							}
						}
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(), "Cant find image", Toast.LENGTH_SHORT).show();
						 
					}*/
                    
                    gigsArr.add(gm);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    
    class VolleyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return gigsArr.size();
        }

        @Override
        public Object getItem(int i) {
            return gigsArr.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh ;
           if(view == null){
               vh = new ViewHolder();
               view = inflator.inflate(R.layout.list_row,null);
               vh.gigHeadliner = (TextView) view.findViewById(R.id.gigTitle);
               vh.gigVenue = (TextView) view.findViewById(R.id.venueName);
               vh.gigDate = (TextView) view.findViewById(R.id.gigDate);
               vh.artistImg = (NetworkImageView) view.findViewById(R.id.artistImg);
               view.setTag(vh);
          }
            else{
               vh = (ViewHolder) view.getTag();
           }

            GigsModel nm = gigsArr.get(i);
            vh.gigHeadliner.setText(nm.getGigTitle());
            vh.gigVenue.setText(nm.getGigVenue());
            vh.gigDate.setText(nm.getGigDate());
            vh.artistImg.setImageUrl(nm.getArtistImg(), myImageLoader);
            
            return view;
        }

        
    } 

  
}
