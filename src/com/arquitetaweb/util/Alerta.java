package com.arquitetaweb.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;

public class Alerta {	
	public Alerta(Context context, String title, String message) {
		Builder alert = new AlertDialog.Builder(context);
		alert.setTitle(!title.equals("") ? title : "Erro - ArquitetaWeb");
		alert.setMessage(message);
		alert.setPositiveButton("OK",null);
		alert.show();
    }		
}
