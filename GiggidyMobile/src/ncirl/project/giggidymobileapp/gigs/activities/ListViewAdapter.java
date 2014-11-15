package ncirl.project.giggidymobileapp.gigs.activities;

import java.util.ArrayList;
import java.util.List;

import ncirl.project.giggidymobileapp.utils.AppController;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.projectattempt.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
	
	private Activity activity;
	private List<GigsModel> list;
	
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	private static LayoutInflater inflater = null;
	
	public ListViewAdapter(Activity activity, List<GigsModel> list){
		this.activity = activity;
		this.list = list;
		
		inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
	}

	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ViewHolder vh;
		if (view == null) {
			vh = new ViewHolder();
			view = inflater.inflate(R.layout.list_row, viewGroup, false);
			vh.gigHeadliner = (TextView) view.findViewById(R.id.gigTitle);
			vh.gigVenue = (TextView) view.findViewById(R.id.venueName);
			vh.gigDate = (TextView) view.findViewById(R.id.gigDate);
			vh.artistImg = (NetworkImageView) view
					.findViewById(R.id.artistImg);
			view.setTag(vh);
		} else {
			vh = (ViewHolder) view.getTag();
		}

		GigsModel nm = list.get(i);
		vh.gigHeadliner.setText(nm.getGigHeadliner());
		vh.gigVenue.setText(nm.getGigVenue());
		vh.gigDate.setText(nm.getGigDate());
		vh.artistImg.setImageUrl(nm.getArtistImgURL(), imageLoader);

		return view;
	}

}


