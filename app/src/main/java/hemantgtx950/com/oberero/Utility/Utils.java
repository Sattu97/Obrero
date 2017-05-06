package hemantgtx950.com.oberero.Utility;

import android.content.Context;
import android.widget.Toast;

import hemantgtx950.com.oberero.R;

/**
 * Created by hemba on 5/6/2017.
 */

public class Utils {
    public static void toastS(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
    public static int getId(String catName){
        if (catName.equals("Plumber")){
            return R.drawable.plumbering;
        }else if(catName.equals("Electrician")){
            return R.drawable.plug;
        }else{
            return R.drawable.saw;
        }
    }
}
