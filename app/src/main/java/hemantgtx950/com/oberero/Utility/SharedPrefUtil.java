package hemantgtx950.com.oberero.Utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hemba on 5/7/2017.
 */

public class SharedPrefUtil implements SharedPrefConstantUtil {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SharedPrefUtil(Context context) {
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_FILENAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
    public void saveDetails(String username,String locality){
        editor.putString(USERNAME,username);
        editor.putString(LOCALITY,locality);
        editor.apply();
    }
    public UserDetails getUserDetails(){
        String username=sharedPreferences.getString(USERNAME,null);
        String local=sharedPreferences.getString(LOCALITY,null);
        UserDetails userDetails=new UserDetails(username,local);
        return userDetails;
    }




}
