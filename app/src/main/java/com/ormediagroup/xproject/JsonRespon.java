package com.ormediagroup.xproject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by YQ04 on 2018/4/19.
 */
public class JsonRespon {
    public interface onComplete {
        void onComplete(JSONObject json);
    }
    private Context context;
    private String url;
    private onComplete listener;

  //  private JSONObject jsonObj;
    public JsonRespon(Context c, String u, onComplete cb) {
        context = c;
        url =u;
        listener = cb;
        getJsonAsync();
    }
    public JsonRespon(Context c, String u) {
        context = c;
        url =u;
    }

    public String getJsonString() {
     String responseText="";
      try {
          responseText = new String();
          responseText = new asyncNetwork(this.context,url).execute().get();

      } catch (ExecutionException e){
          Log.e("ExecutionException", "ExecutionException " + e.toString());
      } catch (InterruptedException e) {
          e.printStackTrace();
      }
        return responseText ;
    }

    public void getJsonAsync() {

        asyncNetwork network = (asyncNetwork) new asyncNetwork(new asyncNetwork.OnAsyncTaskCompleted() {
            @Override
            public void onAsyncTaskCompleted(String response) {
                JSONObject jsonObj = new JSONObject();
                try {
                    jsonObj = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (context!=null) {
                        Toast.makeText(context, "未能連接網絡。請檢查你的網絡。", Toast.LENGTH_LONG).show();
                    }
                }
                listener.onComplete(jsonObj);
            }
        },this.context, url).execute();
    }

    public JSONObject getJsonObj(int i,String responseText){
        JSONObject c=null;
        if (responseText != null) {
            try {
                JSONObject jsonObj = new JSONObject(responseText);
                JSONArray unitPt = jsonObj.getJSONArray("data");
                c = unitPt.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    return c;
    }


}
