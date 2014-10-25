package ncirl.project.giggidymobile.gigs;


import java.util.ArrayList;

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

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.projectattempt.R;



public class MainActivity extends Activity {
	
	
	private String TAG = this.getClass().getSimpleName();
    private ListView lstView;
    private ArrayList<GigsModel> arrGigs ;
    private LayoutInflater lf;
    private VolleyAdapter va;
    private ProgressDialog pd;
    private RequestQueue myQueue;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lf = LayoutInflater.from(this);
        
        arrGigs = new ArrayList<GigsModel>();
        va = new VolleyAdapter();
        
        lstView = (ListView) findViewById(R.id.gigListView);
        lstView.setAdapter(va);

        myQueue = Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(myQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
        
        String url = "http://ws.audioscrobbler.com/2.0/?method=geo.getevents&limit=100&api_key=91ef7088f6ad4ef274c2a9453165106d&format=json&location=boston";
        
        pd = ProgressDialog.show(this,"Please Wait...","Please Wait...");
        
        JsonObjectRequest jr = new JsonObjectRequest(Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG,response.toString());
                parseJSON(response);
                va.notifyDataSetChanged();
                pd.dismiss();
          }
            
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG,error.getMessage());
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
                    GigsModel gn = new GigsModel();
                    gn.setGigTitle(item.optString("title"));
                    gn.setGigVenue(item.optString("startDate"));
                    
                    try {
						JSONArray imageUrls = events.getJSONArray("image");
						
						for(int j=0; j<imageUrls.length(); j++) {
							JSONObject imageObj = imageUrls.getJSONObject(j);
							
							gn.setArtistImg(imageObj.getString("#text"));
							if(imageObj.getString("size").equals("medium")) {
								break;
							}
						}
					} catch (Exception e) {
					
					}
                    
                    arrGigs.add(gn);
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
            return arrGigs.size();
        }

        @Override
        public Object getItem(int i) {
            return arrGigs.get(i);
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
               view = lf.inflate(R.layout.list_row,null);
               vh.gigTitle = (TextView) view.findViewById(R.id.gigTitle);
               vh.gigVenue = (TextView) view.findViewById(R.id.venueName);
               vh.gigDate = (TextView) view.findViewById(R.id.gigDate);
               vh.artistImg = (NetworkImageView) view.findViewById(R.id.artistImg);
               view.setTag(vh);
          }
            else{
               vh = (ViewHolder) view.getTag();
           }

            GigsModel nm = arrGigs.get(i);
            vh.gigTitle.setText(nm.getGigTitle());
            vh.gigVenue.setText(nm.getGigVenue());
            vh.gigDate.setText(nm.getGetId());
            vh.artistImg.setImageUrl(nm.getArtistImg(), mImageLoader);
            return view;
        }
        
        class  ViewHolder{
            TextView gigTitle;
             TextView gigVenue;
             TextView gigDate;
             NetworkImageView artistImg;

        }

    }

  
}
