package com.arquitetaweb.util;

import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

public class Utils {
	
	private static String url = "";
	
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
    
    public static String isAndroidEmulator() {
        String model = Build.MODEL;
        Log.d(TAG, "model=" + model);
        String product = Build.PRODUCT;
        Log.d(TAG, "product=" + product);
        boolean isEmulator = false;
        if (product != null) {
            isEmulator = product.equals("sdk") || product.contains("_sdk") || product.contains("sdk_");            
        }
        if (isEmulator) {
        	url = "http://10.0.2.2:81";       
        } else {
        	url = "http://192.168.10.101:81";
        }
        Log.d(TAG, "isEmulator=" + isEmulator);
        return url;
    }
}