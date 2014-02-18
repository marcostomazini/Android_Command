/*******************************************************************************
 * Copyright 2012 Steven Rudenko
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.arquitetaweb.restaurantes.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.arquitetaweb.command.R;
import com.arquitetaweb.restaurantes.DetailsActivity;
import com.arquitetaweb.util.JSONParser;
import com.arquitetaweb.util.LazyAdapter;
import com.arquitetaweb.util.Utils;

public class CardapioFragment extends Fragment {
	private FragmentActivity contexto;

	JSONArray json = null; 

	// // XML node keys
	public static final String KEY_ID = "Id";
	public static final String KEY_NUMEROMESA = "NumeroMesa";
	public static final String KEY_CODIGOEXTERNO = "CodigoExterno";
	public static final String KEY_SITUACAO = "Situacao";
	ArrayList<HashMap<String, String>> itemLista = new ArrayList<HashMap<String, String>>();

	GridView mesas;
	LazyAdapter adapter;

	Handler handler;
	ProgressDialog progressDialog;

	public static final String TAG = CardapioFragment.class.getSimpleName();
	private static final String CARDAPIO_SCHEME = "settings";
	private static final String CARDAPIO_AUTHORITY = "porcoes";
	public static final Uri CARDAPIO_URI = new Uri.Builder()
	.scheme(CARDAPIO_SCHEME).authority(CARDAPIO_AUTHORITY).build();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		contexto = this.getActivity();
		final View v = inflater.inflate(R.layout.list_itens, container, false);
		//final View v = inflater.inflate(R.layout.details, container, false);
		handler = new Handler();

		// carregarDados(v);
		carregarDadosJson(v);

		return v;
	}

	private JSONArray getData() {
		SharedPreferences s = PreferenceManager
				.getDefaultSharedPreferences(contexto);
		String jsonString = s.getString("mesasJson", null);

		try {
			StringBuilder builder = new StringBuilder();        	
			builder.append(jsonString);
			json = new JSONArray( builder.toString());
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		return json;
	}

	private void saveData(JSONArray jobj) {
		SharedPreferences s = PreferenceManager
				.getDefaultSharedPreferences(contexto);
		Editor edt = s.edit();

		edt.putString("mesasJson", jobj.toString());
		edt.commit();
	}
	
	private void carregarDadosJson(final View v) {
		final JSONParser jParser = new JSONParser();

		handler = new Handler();

		progressDialog = new ProgressDialog(contexto);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("carregando dados...");
		progressDialog.show();

		Thread thread = new Thread() {
			public void run() {
				if (Utils.isConnected(contexto)) {
					String url = Utils.isAndroidEmulator()+"/Api/SituacaoMesas";
					json = jParser.getJSONFromUrl(url);					
					saveData(json);
				} else {				
					json = getData();	
				}

				handler.post(new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i < json.length(); i++) {
							try {
								JSONObject c = json.getJSONObject(i);

								HashMap<String, String> map = new HashMap<String, String>();

								map.put(KEY_ID, c.getString(KEY_ID));
								map.put(KEY_NUMEROMESA, c.getString(KEY_NUMEROMESA));
								map.put(KEY_CODIGOEXTERNO, c.getString(KEY_CODIGOEXTERNO));
								map.put(KEY_SITUACAO, c.getString(KEY_SITUACAO));

								itemLista.add(map);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						mesas = (GridView) v.findViewById(R.id.list);

						adapter = new LazyAdapter(contexto, itemLista);
						mesas.setAdapter(adapter);

						// Click event for single list row
						mesas.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
																
								TextView idItem =(TextView) view.findViewById(R.id.idItem);
								//TextView descricaoItem =(TextView) view.findViewById(R.id.title);
								
								Bundle bun = new Bundle();
								
								bun.putString("id", (String)idItem.getText());
								//bun.putString("description", descricaoItem.getText().toString());								
								
								abrirDetalhes(view, bun);							
							}

							private void abrirDetalhes(View view, Bundle bun) {
								Intent intent = new Intent(view.getContext(), DetailsActivity.class);								
								intent.putExtras(bun);
								startActivity(intent);
							}
						});

						progressDialog.dismiss();
					}
				});
			}
		};
		thread.start();
	}
	 
}
