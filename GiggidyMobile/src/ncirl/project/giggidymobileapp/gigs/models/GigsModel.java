package ncirl.project.giggidymobileapp.gigs.models;

import com.android.volley.toolbox.ImageLoader;

public class GigsModel {
	
	private String gigTitle;
	private String gigVenue;
	private String gigDate;
	private String artistImgURL;
    private ImageLoader imageLoader;
	
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
		return gigTitle;
	}
	public void setGigTitle(String gigTitle) {
		this.gigTitle = gigTitle;
	}
	public String getGigVenue() {
		return gigVenue;
	}
	public void setGigVenue(String gigVenue) {
		this.gigVenue = gigVenue;
	}
	public String getGetId() {
		return gigDate;
	}
	public void setGetId(String getId) {
		this.gigDate = getId;
	}
	
	

}
