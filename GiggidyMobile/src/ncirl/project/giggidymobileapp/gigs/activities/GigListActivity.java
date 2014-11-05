package ncirl.project.giggidymobileapp.gigs.activities;


import java.util.ArrayList;

import ncirl.project.giggidymobileapp.gigs.models.GigsModel;
import ncirl.project.giggidymobileapp.utils.VolleySingleton;
import ncirl.project.giggidymobileapp.gigs.helpers.LastFmHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
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
	
	
	private String TAG = this.getClass().getSimpleName();
    private ListView lstView;
    private ArrayList<GigsModel> gigsArr ;
    private LayoutInflater inflator;
    private VolleyAdapter adapter;
    private ProgressDialog pDialog;
    private RequestQueue myQueue;
    private ImageLoader myImageLoader;
    LastFmHelper lstHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        pDialog = ProgressDialog.show(this,"Synchronizing\n","Please Wait...");
        
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

		myQueue.add(jr);

	}
    
    private void parseJSON(JSONObject json){
    	
        try{
            JSONObject events = json.getJSONObject("events");
            JSONArray event = events.getJSONArray("event");
            for(int i=0;i<event.length();i++){

                    JSONObject item = event.getJSONObject(i);
                    GigsModel gm = new GigsModel();
                    gm.setGigTitle(item.getString("title"));
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
               vh.gigTitle = (TextView) view.findViewById(R.id.gigTitle);
               vh.gigVenue = (TextView) view.findViewById(R.id.venueName);
               vh.gigDate = (TextView) view.findViewById(R.id.gigDate);
               vh.artistImg = (NetworkImageView) view.findViewById(R.id.artistImg);
               view.setTag(vh);
          }
            else{
               vh = (ViewHolder) view.getTag();
           }

            GigsModel nm = gigsArr.get(i);
            vh.gigTitle.setText(nm.getGigTitle());
            vh.gigVenue.setText(nm.getGigVenue());
            vh.gigDate.setText(nm.getGigDate());
            vh.artistImg.setImageUrl(nm.getArtistImg(), myImageLoader);
            
            return view;
        }
        
        private class ViewHolder{
        	
        	private TextView gigTitle;
        	private TextView gigVenue;
        	private TextView gigDate;
        	private NetworkImageView artistImg;
        }
        
    } 

  
}
