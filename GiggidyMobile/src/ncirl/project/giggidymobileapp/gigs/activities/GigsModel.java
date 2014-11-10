package ncirl.project.giggidymobileapp.gigs.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

public class GigsModel {
	
	//From root JSON element
	private String gigId;
	private String gigDate;
	private String artistImgURL;
	private ArrayList<String> tags;
	
	//From artists array element
	private String gigHeadliner;
	private ArrayList<String> allArtists;
	
	//From venue JSON JSON Object
	private String venueId;
	private String venueName;
	private String venuePhoneNumber;
	private String venueImg;
	
	//From location JSON Object element
	private String geoLat;
	private String geoLong;
	private String street;
	
	
	
	
    public String getGigId() {
		return gigId;
	}
	public void setGigId(String gigId) {
		this.gigId = gigId;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public String getAllArtists() {
		return TextUtils.join(", ", allArtists);
	}
	public void setAllArtists(ArrayList<String> allArtists) {
		this.allArtists = allArtists;
	}
	public String getVenueId() {
		return venueId;
	}
	public void setVenueId(String venueId) {
		this.venueId = venueId;
	}
	public String getVenuePhoneNumber() {
		return venuePhoneNumber;
	}
	public void setVenuePhoneNumber(String venuePhoneNumber) {
		this.venuePhoneNumber = venuePhoneNumber;
	}
	public String getVenueImg() {
		return venueImg;
	}
	public void setVenueImg(String venueImg) {
		this.venueImg = venueImg;
	}
	public String getGeoLat() {
		return geoLat;
	}
	public void setGeoLat(String geoLat) {
		this.geoLat = geoLat;
	}
	public String getGeoLong() {
		return geoLong;
	}
	public void setGeoLong(String geoLong) {
		this.geoLong = geoLong;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getGigDate() {
		return gigDate;
	}
	public void setGigDate(String gigDate) {
		this.gigDate = gigDate;
	}
	public String getArtistImg() {
		return artistImgURL;
	}
	public void setArtistImg(String artistImgURL) {
		this.artistImgURL = artistImgURL;
	
	}

	public String getGigTitle() {
		return gigHeadliner;
	}
	public void setGigTitle(String gigTitle) {
		this.gigHeadliner = gigTitle;
	}
	public String getGigVenue() {
		return venueName;
	}
	public void setGigVenue(String gigVenue) {
		this.venueName = gigVenue;
	}
	public String getGetId() {
		return gigDate;
	}
	public void setGetId(String getId) {
		this.gigDate = getId;
	}


}
