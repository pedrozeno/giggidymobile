package ncirl.project.giggidymobileapp.utils;

/**
 * Rather than using the convenience RequestQueue method provided by Volley
 * I am instead creating a singleton class. This class will make sure only 
 * one instance of the Request Queue ever exists.
 *
 * @author Patrick Byrne
 * Referenced from http://developer.android.com/training/volley/requestqueue.html
 * @version 1.0
 */

import android.app.Application;
import android.text.TextUtils;
 
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
 
public class AppController extends Application {
 
    public static final String TAG = AppController.class.getSimpleName();
 
    private RequestQueue mQueue;
    private ImageLoader mImgLoader;
 
    private static AppController mInstance;
 
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
 
    public static synchronized AppController getInstance() {
        return mInstance;
    }
 
    public RequestQueue getRequestQueue() {
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(getApplicationContext());
        }
 
        return mQueue;
    }
 
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImgLoader == null) {
            mImgLoader = new ImageLoader(this.mQueue,
                    new LruBitmapCache());
        }
        return this.mImgLoader;
    }
 
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
 
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
 
    public void cancelPendingRequests(Object tag) {
        if (mQueue != null) {
            mQueue.cancelAll(tag);
        }
    }
}


