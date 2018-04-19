package com.ormediagroup.xproject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by YQ04 on 2018/4/19.
 */

public class asyncNetwork extends AsyncTask<String, Void, String> {
    public interface OnAsyncTaskCompleted{
        void onAsyncTaskCompleted(String response);
    }
  //  JSONObject jObj = null;
    String json = "";
    Context context;
    String url;
    private OnAsyncTaskCompleted listener;
    public asyncNetwork(OnAsyncTaskCompleted callback, Context c, String u) {
        context = c;
        url =u;
        listener = callback;

    }

    public asyncNetwork(Context c, String u) {
        context = c;
        url =u;
    }


    @Override
    protected String doInBackground(String... params) {
        // Log.e("ORM","context isn ull");
        InputStream myStream=getStream(url);
        String builder  ="";

        //Log.e("ORM","my stremam"+myStream.toString());
        if (myStream != null) {
            try {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(myStream));
                String newLine = System.getProperty("line.separator");
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append(newLine);
                }
                json=sb.toString();
                /*
                Intent i = new Intent("com.ormediagroup.TEST");
                i.putExtra("JSONDATA", json);
                LocalBroadcastManager.getInstance(context).sendBroadcast(i);
*/
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }
        } else {
            Log.e("ORM","ASyncNetwork doInBackground Exception ");
        }
        //Log.e("ORM","json string"+json.toString());
        return json;
    }


    private InputStream getStream(String u) {
        try {
            URL url = new URL(u);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestProperty("connection", "close");
            return urlConnection.getInputStream();
        } catch (Exception ex) {
            Log.e("ORM",ex.toString());
            return null;
        }
    }

    protected void onPostExecute(String result)
    {
        if (listener != null)
            listener.onAsyncTaskCompleted(result);
       // Log.e("ORM", "result post string" + result.toString());

    }

}