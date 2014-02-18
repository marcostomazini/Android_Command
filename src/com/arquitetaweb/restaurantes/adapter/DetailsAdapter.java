package com.arquitetaweb.restaurantes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.arquitetaweb.command.R;

public class DetailsAdapter extends BaseAdapter {
  private final LayoutInflater mInflater;

  public DetailsAdapter(Context context) {
    mInflater = LayoutInflater.from(context);            
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = mInflater.inflate(R.layout.details, parent, false);    
    }
    return convertView;
  }

  @Override
public int getCount() {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public Object getItem(int position) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public long getItemId(int position) {
	// TODO Auto-generated method stub
	return 0;
}
}
