package ncirl.project.giggidymobile.lastfm;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

public class LastFmHelper {


	private static String API_Key = "&api_key=91ef7088f6ad4ef274c2a9453165106d";
	

	public String getArtistInfo(String artist) throws UnsupportedEncodingException {

		String url = "http://ws.audioscrobbler.com/2.0/?method=artist.getinfo" +
				"&location=" + URLEncoder.encode(artist, "UTF-8") +
				"&api_key=" + API_Key +
				"&lang=" + Locale.getDefault().getLanguage() +
                "&format=json";
		
		
		return url;
	}
	
	public String getGigInfo(String location) throws UnsupportedEncodingException {

		String url = "http://ws.audioscrobbler.com/2.0/?method=geo.getevents" +
				"&location=" + URLEncoder.encode(location, "UTF-8") +
				"&api_key=" + API_Key +
				"&lang=" + Locale.getDefault().getLanguage() +
                "&format=json&limit=250";
		
		return url;
	}

}
