package com.arquitetaweb.util;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arquitetaweb.command.R;
import com.arquitetaweb.restaurantes.fragment.CardapioFragment;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null) {
            vi = inflater.inflate(R.layout.list_row, null);

        TextView idItem = (TextView)vi.findViewById(R.id.idItem); // id
        TextView numero_mesa = (TextView)vi.findViewById(R.id.numero_mesa); // numero mesa
        TextView artist = (TextView)vi.findViewById(R.id.codigo_externo); // codigo externo
        TextView situacao = (TextView)vi.findViewById(R.id.situacao); // situacao
        
        
        HashMap<String, String> itens = new HashMap<String, String>();
        itens = data.get(position);
        
        // Setting all values in listview
        idItem.setText(itens.get(CardapioFragment.KEY_ID));
        numero_mesa.setText(itens.get(CardapioFragment.KEY_NUMEROMESA));
        artist.setText(itens.get(CardapioFragment.KEY_CODIGOEXTERNO));
        situacao.setText(itens.get(CardapioFragment.KEY_SITUACAO));
        

        String _situacao = situacao.getText().toString();
        if (_situacao.equals("2")) {
        	situacao.setTextColor(Color.parseColor("#bdbdbd"));
        }
        
        
        }
        return vi;
    }
}