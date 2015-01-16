package org.odk.collect.android.adapters;

/**
 * Created by yinseng on 7/16/14.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


import org.odk.collect.android.model.ItemData;

import java.util.HashMap;
import java.util.Map;


public class KeyValueHashMapAdapter extends BaseAdapter implements SpinnerAdapter {

    private static String TAG = "KeyValueAdapter";

    private final Context _context;
    private final Map<String, ItemData> _data;
    private final String[] _keys;


    /**
     * The resource indicating what views to inflate to display the content of this
     * array adapter.
     */
    private int mResource;

    /**
     * The resource indicating what views to inflate to display the content of this
     * array adapter in a drop down widget.
     */
    private int mDropDownResource;
    private LayoutInflater mInflater;
    private Context mContext;


    public KeyValueHashMapAdapter(Context context, int resource, Map<String, ItemData> objects) {
        _context = context;
        _data = objects;

        //get positions
        int i = 0;
        _keys = new String[_data.size()];

        for (Map.Entry<String, ItemData> entry : _data.entrySet()) {
            _keys[i++] = entry.getKey();
        }
        init(context, resource);
    }

    private void init(Context context, int resource) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResource = mDropDownResource = resource;

    }


    public int getCount() {
        return _data.size();
    }


    public int getPositionFromKey(String searchKey) {
        for (int i = 0; i < _keys.length; i++) {
            if (_keys[i].equals(searchKey))
                return i;
        }
        return -1;
    }


    public Object getItem(int position) {
        return _data.get(_keys[position]);
    }


    public long getItemId(int position) {
        /*
        * I happened to be using long keys so I modified this function. you can leave it at:
        *  return position;
        */
        if (position >= _keys.length || position < 0) {
            return -1;
        }

        return _data.get(_keys[position]).getId();
    }


    public View getView(int position, View view, ViewGroup parent) {
//        //Set the text of the view to what you want it to display.
//        final String name = (_data.get(_keys[position])).getName();
//
//        final TextView text = new TextView(_context);
//        //text.setTextColor(Color.BLACK);
//        text.setText(name);
//
//        return text;
        return createViewFromResource(position, view, parent, mDropDownResource);
    }

    /**
     * <p>Sets the layout resource to create the drop down views.</p>
     *
     * @param resource the layout resource defining the drop down views
     * @see #getDropDownView(int, android.view.View, android.view.ViewGroup)
     */
    public void setDropDownViewResource(int resource) {
        this.mDropDownResource = resource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, mDropDownResource);
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent,
                                        int resource) {
        View view;
        TextView text;

        if (convertView == null) {
            view = mInflater.inflate(resource, parent, false);
        } else {
            view = convertView;
        }

        try {
            //  Otherwise, find the TextView field within the layout
            text = (TextView) view;
        } catch (ClassCastException e) {
            Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException(
                    "ArrayAdapter requires the resource ID to be a TextView", e);
        }

        //T item = getItem(position);
        //if (item instanceof CharSequence) {
        //text.setText((CharSequence)item);
        //} else {
        text.setText((_data.get(_keys[position])).getName());
        //}

        return view;
    }

}
