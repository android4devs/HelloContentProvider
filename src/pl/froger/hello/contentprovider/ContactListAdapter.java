package pl.froger.hello.contentprovider;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ContactListAdapter extends SimpleCursorAdapter {
	private Context context;
	private Cursor c;
	private int displayNameColumnIndex;
	
	private OnClickListener onItemEditClickListener;
	private OnClickListener onItemDeleteClickListener;
	
	public ContactListAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
		super(context, layout, c, from, to);
		this.context = context;
		this.c = c;
		displayNameColumnIndex = c.getColumnIndex(Contacts.DISPLAY_NAME);
	}

	static class ViewHolder {
		TextView tvDisplayName;
		ImageButton ibEditContact;
		ImageButton ibDeleteContact;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		View rowView = convertView;
		if(rowView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = layoutInflater.inflate(R.layout.contact_row, null, true);
			viewHolder = new ViewHolder();
			viewHolder.tvDisplayName = (TextView) rowView.findViewById(R.id.tvDisplayName);
			viewHolder.ibEditContact = (ImageButton) rowView.findViewById(R.id.ibEdit);
			viewHolder.ibDeleteContact = (ImageButton) rowView.findViewById(R.id.ibDelete);
			viewHolder.ibEditContact.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if(onItemEditClickListener != null) onItemEditClickListener.onClick(v);
				}
			});
			viewHolder.ibDeleteContact.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if(onItemDeleteClickListener != null) onItemDeleteClickListener.onClick(v);
				}
			});
			rowView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) rowView.getTag();
		}
		c.moveToPosition(position);
		viewHolder.tvDisplayName.setText(c.getString(displayNameColumnIndex));
		viewHolder.ibEditContact.setTag(new Integer(position));
		viewHolder.ibDeleteContact.setTag(new Integer(position));
		return rowView;
	}
	
	public void setOnItemDeleteClickListener(
			OnClickListener onItemDeleteClickListner) {
		this.onItemDeleteClickListener = onItemDeleteClickListner;
	}
	
	public void setOnItemEditClickListener(
			OnClickListener onItemEditClickListener) {
		this.onItemEditClickListener = onItemEditClickListener;
	}
}
