package com.arquitetaweb.util;

import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.arquitetaweb.command.R;

public class Utils {
	
	private static final String TAG = "Teste";

	public final static boolean isConnected( Context context )
	{   
	   final ConnectivityManager connectivityManager = 
	         (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );  
	   final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();    
	   return networkInfo != null && networkInfo.isConnected();
	}
	
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
    
    public static String isAndroidEmulator(Activity activity) {
        String model = Build.MODEL;
        Log.d(TAG, "model=" + model);
        String product = Build.PRODUCT;
        Log.d(TAG, "product=" + product);
        boolean isEmulator = false;
        if (product != null) {
            isEmulator = product.equals("sdk") || product.contains("_sdk") || product.contains("sdk_");            
        }
        if (!isEmulator) {        	
        	LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	View vi = inflater.inflate(R.layout.sandbox, null);
        	
        	EditText urlServico = (EditText)vi.findViewById(R.id.edtUrlServico);
        	EditText portaServico = (EditText)vi.findViewById(R.id.edtPortaServico);
        	        	        
        	return MontarUrl(urlServico, portaServico);
        }
        return "http://10.0.2.2:81";
    }

	private static String MontarUrl(EditText urlServico, EditText portaServico) {
		StringBuilder sb = new StringBuilder();
		sb.append("http://");
		sb.append(urlServico.getText());
		sb.append(":");
		sb.append(portaServico.getText());
		return sb.toString();
	}
}