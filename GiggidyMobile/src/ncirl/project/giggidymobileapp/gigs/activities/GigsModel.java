package ncirl.project.giggidymobileapp.gigs.activities;

import java.util.ArrayList;

import android.text.TextUtils;

public class GigsModel {

	// From root JSON element
	private String gigId;
	private String gigDate;
	private String artistImgURL;
	private String gigVenue;
	private ArrayList<String> tags;
	

	// From artists array element
	private String gigHeadliner;
	private ArrayList<String> allArtists;
		
	
	
	public String getGigVenue() {
		return gigVenue;
	}
	public void setGigVenue(String gigVenue) {
		this.gigVenue = gigVenue;
	}
	public String getGigId() {
		return gigId;
	}
	public void setGigId(String gigId) {
		this.gigId = gigId;
	}
	public String getGigDate() {
		return gigDate;
	}
	public void setGigDate(String gigDate) {
		this.gigDate = gigDate;
	}
	public String getArtistImgURL() {
		return artistImgURL;
	}
	public void setArtistImgURL(String artistImgURL) {
		this.artistImgURL = artistImgURL;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public String getGigHeadliner() {
		return gigHeadliner;
	}
	public void setGigHeadliner(String gigHeadliner) {
		this.gigHeadliner = gigHeadliner;
	}
	public String getAllArtists() {
		return TextUtils.join(", ", allArtists);
	}
	public void setAllArtists(ArrayList<String> allArtists) {
		this.allArtists = allArtists;
	}

	

}