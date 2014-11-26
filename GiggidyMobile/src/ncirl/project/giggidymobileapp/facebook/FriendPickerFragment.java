package ncirl.project.giggidymobileapp.facebook;

import java.util.List;

import android.content.Context;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectattempt.R;

public class FriendPickerFragment extends ListFragment {
	
	
	
	private class ActionListAdapter extends ArrayAdapter<FriendListElement> {
	    private List<FriendListElement> friendElements;

	    public ActionListAdapter(Context context, int resourceId, 
	                             List<FriendListElement> listElements) {
	        super(context, resourceId, listElements);
	        this.friendElements = listElements;
	        // Set up as an observer for list item changes to
	        // refresh the view.
	        for (int i = 0; i < listElements.size(); i++) {
	            listElements.get(i).setAdapter(this);
	        }
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View view = convertView;
	        if (view == null) {
	            LayoutInflater inflater =
	                    (LayoutInflater) getActivity()
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            view = inflater.inflate(R.layout.friend_list_item, null);
	        }

	        FriendListElement friend = friendElements.get(position);
	        if (friend != null) {
	            view.setOnClickListener(friend.getOnClickListener());
	            ImageView icon = (ImageView) view.findViewById(R.id.icon);
	            TextView text1 = (TextView) view.findViewById(R.id.text1);
	            TextView text2 = (TextView) view.findViewById(R.id.text2);
	            if (icon != null) {
	                icon.setImageDrawable(friend.getFriendImg());
	            }
	            if (text1 != null) {
	                text1.setText(friend.getText1());
	            }
	            if (text2 != null) {
	                text2.setText(friend.getText2());
	            }
	        }
	        return view;
	    }

	}

}
