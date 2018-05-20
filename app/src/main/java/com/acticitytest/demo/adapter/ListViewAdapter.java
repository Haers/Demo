package com.acticitytest.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.acticitytest.demo.R;

public class ListViewAdapter extends BaseAdapter {
    private final Context mContext;
    private final String[] mDataset;
    private final Boolean[] mCheck;
    public ListViewAdapter(Context context, String[] dataset, Boolean[] check) {
        mContext = context;
        mDataset = dataset;
        mCheck = check;
    }

    @Override
    public int getCount() {
        return mDataset.length;
    }

    @Override
    public String getItem(int position) {
        return mDataset[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.list_check);
            viewHolder.mTextView = convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        /*String[] values = mDataset[position].split(",");
        String countryName = values[0];
        int flagResId = mContext.getResources().getIdentifier(values[1],
                "drawable", mContext.getPackageName());
        viewHolder.mTextView.setText(countryName);
        viewHolder.mTextView.setCompoundDrawablesWithIntrinsicBounds(flagResId, 0, 0, 0);*/
        String value = mDataset[position];
        boolean isCaught = mCheck[position];
        viewHolder.mTextView.setText(value);
        if(isCaught)
            viewHolder.imageView.setImageResource(R.drawable.check);
        else
            viewHolder.imageView.setImageResource(R.drawable.question);
        return convertView;
    }

    private static class ViewHolder {
        private ImageView imageView;
        private TextView mTextView;
    }
}