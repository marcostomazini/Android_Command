package com.arquitetaweb.restaurantes;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arquitetaweb.command.R;
import com.arquitetaweb.restaurantes.adapter.DetailsAdapter;
import com.arquitetaweb.util.JSONParser;

public class DetailsActivity extends FragmentActivity {

	private static final String STATE_POSITION = "state:layout_id";

	private static final String SCHEME = "settings";
	private static final String AUTHORITY = "details";
	public static final Uri URI = new Uri.Builder().scheme(SCHEME)
			.authority(AUTHORITY).build();

	private ListView viewList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		new DetailsAdapter(this);

		setContentView(R.layout.details);

		final String idString = getIntent().getExtras().get("id").toString();
		//String descricao = getIntent().getExtras().get("description").toString();

		final TextView id = (TextView) findViewById(R.id.teste);
		id.setText(idString);

		//final TextView description = (TextView) findViewById(R.id.description);
		//description.setText(descricao);
		
		final Button btnLivre = (Button) findViewById(R.id.btnLivre);
		btnLivre.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Livre",
						Toast.LENGTH_SHORT).show();

				new Connection().execute("1", idString);
			}
		});

		final Button btnOcupada = (Button) findViewById(R.id.btnCcupada);
		btnOcupada.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Ocupada",
						Toast.LENGTH_SHORT).show();
				
				new Connection().execute("2" , idString);
			}
		});

		final Button btnOciosa = (Button) findViewById(R.id.btnOciosa);
		btnOciosa.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Ociosa",
						Toast.LENGTH_SHORT).show();
				new Connection().execute("7", idString);
			}
		});

	}

	private class Connection extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			final JSONParser jParser = new JSONParser();
			jParser.atualizaMesa(params[0], params[1]);

			return null;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		final int position = viewList.getSelectedItemPosition();
		if (position != ListView.INVALID_POSITION)
			outState.putInt(STATE_POSITION, position);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		onBackPressed();
		return true;
	}

	//
}
