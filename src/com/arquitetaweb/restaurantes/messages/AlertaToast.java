package com.arquitetaweb.restaurantes.messages;

import android.content.Context;
import android.widget.Toast;

public class AlertaToast {	
	public static void show(Context context, String message) {		
		Toast.makeText(context, message,
				Toast.LENGTH_LONG).show();	                    					
    }		
}
