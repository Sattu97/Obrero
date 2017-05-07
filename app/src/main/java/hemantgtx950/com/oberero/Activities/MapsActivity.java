package hemantgtx950.com.oberero.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hemantgtx950.com.oberero.R;
import hemantgtx950.com.oberero.Utility.SharedPrefUtil;
import hemantgtx950.com.oberero.Utility.Utils;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private RequestQueue queue;
    private GoogleMap mMap;
    Context context;
    private String cat;
    private SharedPrefUtil sharedPrefUtil;

    @Override
    public boolean onSupportNavigateUp() {
        MapsActivity.this.finish();
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        context=MapsActivity.this;
        sharedPrefUtil=new SharedPrefUtil(context);

        queue=Volley.newRequestQueue(context);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Intent i=getIntent();
        cat=i.getStringExtra("category");
        //searchWorkers(cat,sharedPrefUtil.getUserDetails().getLocality());
        mMap = googleMap;
        Log.d("asd",cat+"-"+sharedPrefUtil.getUserDetails().getLocality());
        searchWorkers(cat,sharedPrefUtil.getUserDetails().getLocality());
        // Add a marker in Sydney and move the camera

      //  LatLng sydney = new LatLng(-34, 151);
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void searchWorkers(String category,String locality){
        String url="https://b69300c0.ngrok.io/api/searchworker?locality="+locality.toLowerCase()+"&occupation="+category.toLowerCase();
        final ArrayList<LatLng> latLngs=new ArrayList<>();
        url=url.replaceAll(" ","%20");
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jsonArray=new JSONArray(response);
                            Log.d("json",jsonArray.toString());
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject j=jsonArray.getJSONObject(i);
                                LatLng l=new LatLng(Double.parseDouble(j.getString("lat")),Double.parseDouble(j.getString("long")));
                                latLngs.add(l);
                            }
                            addMarkers(latLngs);
                        }catch (JSONException e){
                            Utils.toastS(MapsActivity.this,"error");
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.toastS(context,"Can't Connect to Servers!");

            }
        });

        queue.add(stringRequest);

    }
public void addMarkers(ArrayList<LatLng> e){
    for (int i=0;i<e.size();i++){
        mMap.addMarker(new MarkerOptions().position(e.get(i)).title(cat).icon(BitmapDescriptorFactory.fromResource(R.drawable.locationicon)));

    }
    LatLng current = new LatLng(28.535859, 77.345604);
    CameraPosition target = CameraPosition.builder().target(current).zoom((float) 17.3).bearing(87).tilt(20).build();
    mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(target));

}

    public void tryLogin(String phn,String pass){
        String message = "thier is a request for a work in your area";
        String url="https://c7fb27f9.ngrok.io/api/loginuser?phoneno="+phn+"&password="+pass;
        url=url.replaceAll(" ","%20");
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.get("_id")!=null){

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
