package hemantgtx950.com.oberero.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import hemantgtx950.com.oberero.R;
import hemantgtx950.com.oberero.Utility.Utils;

public class SignUpActivity extends AppCompatActivity {
    private TextView logInText;
    private  Context context;
    private EditText userET,localityET,phnET,addrET,passET;
    private Button signUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userET=(EditText)findViewById(R.id.user_name_sign_up);
        localityET=(EditText)findViewById(R.id.locality_sign_up);
        phnET=(EditText)findViewById(R.id.phone_no_sign_up);
        addrET=(EditText)findViewById(R.id.address_sign_up);
        passET=(EditText)findViewById(R.id.password_sign_up);
        signUpBtn=(Button)findViewById(R.id.sign_up_button);


        context=SignUpActivity.this;
        logInText = (TextView) findViewById(R.id.login_text);
        logInText.setPaintFlags(logInText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        logInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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

    public  void trySignUpUser( String username, String locatlity, String phn, String addr, String password){
        String url="http://obrero.herokuapp.com/api/adduser?" +
                "username="+username+"&locality="+locatlity+"&phoneno="+phn+"&address="+addr+"&password="+password;
        url=url.replaceAll(" ","%20");
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.get("uniqueid")!=null){
                                Intent i=new Intent(SignUpActivity.this,CategoryActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                            }
                        }catch (JSONException e){

                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);


    }
}
