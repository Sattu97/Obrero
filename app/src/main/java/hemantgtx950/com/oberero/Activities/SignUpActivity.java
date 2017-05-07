package hemantgtx950.com.oberero.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hemantgtx950.com.oberero.R;
import hemantgtx950.com.oberero.Utility.SharedPrefConstantUtil;
import hemantgtx950.com.oberero.Utility.SharedPrefUtil;
import hemantgtx950.com.oberero.Utility.UserDetails;
import hemantgtx950.com.oberero.Utility.Utils;

public class SignUpActivity extends AppCompatActivity {
    private TextView logInText;
    private ProgressDialog progressDialog;
    private  Context context;
    private EditText userET,localityET,phnET,addrET,passET;
    private Button signUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        context=SignUpActivity.this;
        SharedPrefUtil s=new SharedPrefUtil(context);
        UserDetails u=s.getUserDetails();
        if(u.getUsername()!=null){
            Intent i=new Intent(SignUpActivity.this,CategoryActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }


        userET=(EditText)findViewById(R.id.user_name_sign_up);
        localityET=(EditText)findViewById(R.id.locality_sign_up);
        phnET=(EditText)findViewById(R.id.phone_no_sign_up);
        addrET=(EditText)findViewById(R.id.address_sign_up);
        passET=(EditText)findViewById(R.id.password_sign_up);
        signUpBtn=(Button)findViewById(R.id.sign_up_button);

        progressDialog=new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Signing you in!!!");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        logInText = (TextView) findViewById(R.id.login_text);
        logInText.setPaintFlags(logInText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        logInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username,locality,phn,addr,pass;
                username=userET.getText().toString();
                locality=localityET.getText().toString();
                phn=phnET.getText().toString();
                addr=addrET.getText().toString();
                pass=passET.getText().toString();
                if((username!=null)&&(locality!=null)&&(phn!=null)&&(addr!=null)&&(pass!=null)){
                    trySignUpUser(username,locality,phn,addr,pass);
                }


            }
        });

    }

    public  void trySignUpUser(final String username, final String locatlity, final String phn, final String addr, final String password) {
        progressDialog.show();
        String address= "https://maps.googleapis.com/maps/api/geocode/json?address=" + addr + "&key=AIzaSyAt2hZn94aGAHnNn8X8c7mRgvAE2NPND0w";
        address = address.replaceAll(" ","%20");
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,address,
                new com.android.volley.Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray j=jsonObject.getJSONArray("results");
                            jsonObject=j.getJSONObject(0);
                            jsonObject=jsonObject.getJSONObject("geometry");
                            jsonObject=jsonObject.getJSONObject("location");
                            String lat=jsonObject.getString("lat");
                            String lng=jsonObject.getString("lng");
                            //jsonObject=j.getJSONObject(3);
                            Log.d("asd",lat+" "+lng);

                            trySignUp(username,locatlity,phn,addr,password,lat,lng);


                        }catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.cancel();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    progressDialog.cancel();
            }
        });
        requestQueue.add(stringRequest);

    }

    public void trySignUp(final String username, final String locatlity, String phn, String addr, String password, String lat, String lng){
        String url="https://b69300c0.ngrok.io/api/adduser?" +
                "username="+username+"&locality="+locatlity+"&phoneno="+phn+"&address="+addr+"&password="+password+"&lat="+lat+"&long="+lng;
        url=url.replaceAll(" ","%20");
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.get("uniqueid")!=null){
                                progressDialog.cancel();
                                SharedPrefUtil s=new SharedPrefUtil(context);
                                s.saveDetails(username,locatlity);
                                Intent i=new Intent(SignUpActivity.this,CategoryActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                            }
                        }catch (JSONException e){
                            progressDialog.cancel();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.toastS(context,"Can't Connect to Servers!");
                progressDialog.cancel();
            }
        });

        queue.add(stringRequest);
    }

}
