package hemantgtx950.com.oberero.Api;

import android.content.Context;
import android.location.Address;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hemba on 5/6/2017.
 */

public class ApiUtil {
    private static final String APITAG="ApiHit";
    public static Boolean b;

    public static boolean tryLogIn(Context context, String workerName, String aadharNum, String locality,
                                   String phn, String password, String addr,String occupation){
        b=false;
        String url="http://obrero.herokuapp.com/api/addworker?workername="+workerName+"&aadharcardno="+aadharNum+"&age=22" +
                "&locality="+locality+"&phoneno="+phn+"&password="+password+"&address="+addr +
                "&occupation="+occupation;
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.get("workerid")!=null){
                                b=true;
                            }

                        }catch (JSONException e){

                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setTag(APITAG);
        queue.add(stringRequest);

        return b;
    }




    public static boolean trySignUpUser(Context context,String username,String locatlity,String phn,String addr,String password){
        b=false;
        String url="http://obrero.herokuapp.com/api/adduser?" +
                "username="+username+"&locality="+locatlity+"&phoneno="+phn+"&address="+addr+"&password="+password;
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.get("uniqueid")!=null){
                                b=true;
                            }

                        }catch (JSONException e){

                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setTag(APITAG);
        queue.add(stringRequest);

        return b;
    }
}
