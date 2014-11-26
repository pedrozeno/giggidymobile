package ncirl.project.giggidymobileapp.facebook;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.BaseAdapter;

public abstract class FriendListElement {
	
	private Drawable friendImg;
	private String text1;
	private String text2;
	
	private int requestCode;
	
	private BaseAdapter adapter;

	public FriendListElement(Drawable friendImg, String text1, String text2,
			int requestCode) {
		super();
		this.friendImg = friendImg;
		this.text1 = text1;
		this.text2 = text2;
		this.requestCode = requestCode;
	}
	
	public void setAdapter(BaseAdapter adapter) {
	    this.adapter = adapter;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
		if (adapter != null) {
		    adapter.notifyDataSetChanged();
		}
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
		if (adapter != null) {
		    adapter.notifyDataSetChanged();
		}
	}

	public Drawable getFriendImg() {
		return friendImg;
	}

	public int getRequestCode() {
		return requestCode;
	}
	
	protected abstract View.OnClickListener getOnClickListener();
	
	

}
