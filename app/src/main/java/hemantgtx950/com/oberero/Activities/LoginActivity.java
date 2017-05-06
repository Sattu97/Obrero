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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import hemantgtx950.com.oberero.R;
import hemantgtx950.com.oberero.Utility.Utils;

public class LoginActivity extends AppCompatActivity {
    private TextView signUpText;
    private EditText phnET,passET;
    private Button loginBtn;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context=LoginActivity.this;
        phnET=(EditText)findViewById(R.id.user_phone_no_log_in);
        passET=(EditText)findViewById(R.id.password_log_in);
        loginBtn=(Button)findViewById(R.id.login_button);
        signUpText = (TextView) findViewById(R.id.sign_up_text);
        signUpText.setPaintFlags(signUpText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    public void tryLogin(String phn,String pass){
        String url="http://obrero.herokuapp.com/api/loginuser?phoneno="+phn+"&password="+pass;
        url=url.replaceAll(" ","%20");
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.get("_id")!=null){
                                Intent i=new Intent(LoginActivity.this,CategoryActivity.class);
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
