package com.arquitetaweb.restaurantes.messages;

import com.arquitetaweb.command.R;
import android.content.Context;
import android.widget.Toast;

public class SucessoToast {	
	public static void show(Context context) {		
		Toast.makeText(context, R.string.msgSalvoSucesso,
				Toast.LENGTH_LONG).show();	                    					
    }		
}
