package ncirl.project.giggidymobileapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetDetector {
	
	private Context context;
	
	public InternetDetector(Context context){
		this.context = context;
		
	}
	
	public boolean isConnectedToInternet(){
		
		ConnectivityManager connector = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		if(connector !=null){
			
			NetworkInfo[] netInfo = connector.getAllNetworkInfo();
			  if (netInfo != null) 
				  for (int i = 0; i < netInfo.length; i++) 
					  if (netInfo[i].getState() == NetworkInfo.State.CONNECTED)
					  {
						  return true;
					  }

		  }
		
		return false;
	}

}
