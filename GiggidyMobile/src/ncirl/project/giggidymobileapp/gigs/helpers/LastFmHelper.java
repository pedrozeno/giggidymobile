package ncirl.project.giggidymobileapp.gigs.helpers;

public class LastFmHelper {

	private static String LastFM_root_url = "http://ws.audioscrobbler.com/2.0/?method=";
	private static String API_Key = "&api_key=91ef7088f6ad4ef274c2a9453165106d";

	public String getGigInfo(String location) {

		return LastFM_root_url + "geo.getevents&limit=100" + API_Key
				+ "&format=json&location=" + location;

	}

	public String getArtistInfo(String artist) {

		return LastFM_root_url + "artist.getinfo&artist=" + artist + API_Key
				+ "&format=json";

	}

}
