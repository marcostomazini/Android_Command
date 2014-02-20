/*******************************************************************************
 * Copyright 2014 Marcos Tomazini
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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;

import com.arquitetaweb.command.R;
import com.arquitetaweb.restaurantes.DetailsActivity;
import com.arquitetaweb.util.JSONParser;
import com.arquitetaweb.util.LazyAdapter;
import com.arquitetaweb.util.Utils;

public class CardapioFragment extends Fragment {
	private FragmentActivity contexto;

	JSONArray json = null; 

	// JSON Keys
	public static final String KEY_ID = "Id";
	public static final String KEY_NUMEROMESA = "NumeroMesa";
	public static final String KEY_CODIGOEXTERNO = "CodigoExterno";
	public static final String KEY_SITUACAO = "Situacao";	

	GridView mesas;
	public static LazyAdapter adapter;

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
		final View view = inflater.inflate(R.layout.list_itens, container, false);
		
		handler = new Handler();
		carregarDadosJson(view);

		return view;
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

	private void carregarDadosJson(final View view) {
		final JSONParser jParser = new JSONParser();

		handler = new Handler();

		progressDialog = new ProgressDialog(contexto);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("atualizando mapa das mesas...");
		progressDialog.show();

		Thread thread = new Thread() {
			public void run() {
				if (Utils.isConnected(contexto)) {
					String url = Utils.isAndroidEmulator(contexto)+"/Api/SituacaoMesas";
					json = jParser.getJSONFromUrl(url);					
				} else {				
					json = getData();	
				}

				handler.post(new Runnable() {
					@Override
					public void run() {
						ArrayList<HashMap<String, String>> mesasLista = new ArrayList<HashMap<String, String>>();
						for (int i = 0; i < json.length(); i++) {
							try {
								JSONObject c = json.getJSONObject(i);

								HashMap<String, String> map = new HashMap<String, String>();

								map.put(KEY_ID, c.getString(KEY_ID));
								map.put(KEY_NUMEROMESA, c.getString(KEY_NUMEROMESA));
								map.put(KEY_CODIGOEXTERNO, c.getString(KEY_CODIGOEXTERNO));
								map.put(KEY_SITUACAO, c.getString(KEY_SITUACAO));

								mesasLista.add(map);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						mesas = (GridView) view.findViewById(R.id.list);
						adapter = new LazyAdapter(contexto, mesasLista);						
						mesas.setAdapter(adapter);						

						// Click event for single list row
						mesas.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
																
								TextView idItem =(TextView) view.findViewById(R.id.idItem);
								
								Bundle bun = new Bundle();								
								bun.putString("id", (String)idItem.getText());
								
								abrirDetalhes(view, bun);							
							}

							private void abrirDetalhes(View view, Bundle bun) {
								Intent intent = new Intent(view.getContext(), DetailsActivity.class);								
								intent.putExtras(bun);
								startActivityForResult(intent, 100);
							}												
						});
						progressDialog.dismiss();
					}
				});
			}
		};
		thread.start();
	}	 
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (Activity.RESULT_OK == resultCode) {
			carregarDadosJson(this.getView());		
		}
	}
}
