package hemantgtx950.com.oberero.Services;

/**
 * Created by hemba on 3/1/2017.
 */

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hemantgtx950.com.oberero.Activities.MapsActivity;
import hemantgtx950.com.oberero.R;


/**
 * Created by hemba on 3/1/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
        }
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                long time = remoteMessage.getSentTime();


                handleDataMessage(json,time);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

    }
    // [END receive_message]

    private void handleDataMessage(JSONObject json,long time) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            Log.d("datasadsa",json.toString());
            JSONObject data = json.getJSONObject("data");


            Date date=new Date(time);
            String tDate=new SimpleDateFormat("EEE, MMM d, yyyy").format(date);
            String tDay=new SimpleDateFormat("d").format(date);
            String tMon=new SimpleDateFormat("MMM").format(date);
            String tTime=new SimpleDateFormat("h:mm a").format(date);
            Log.e(TAG, "Month: " + tMon);
            Log.e(TAG, "Day: " + tDay);
            Log.e(TAG, "tTime " + tTime);


            String title = data.getString("title").trim();
            String message = data.getString("message").trim();
            //boolean isBackground = data.getBoolean("is_background");
          //  String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
          //  JSONObject payload = data.getJSONObject("payload");
            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
           // Log.e(TAG, "isBackground: " + isBackground);
           // Log.e(TAG, "payload: " + payload.toString());
           // Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);
            int day=Integer.parseInt(tDay);

            if (!isAppIsInBackground(getApplicationContext())) {
                //foreground
                Intent resultIntent = new Intent(getApplicationContext(), MapsActivity.class);
                resultIntent.putExtra("message", message);
                PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 7190, resultIntent, PendingIntent.FLAG_ONE_SHOT);
                showNotification(getApplicationContext(), intent, message, title);
                //showNotification(getApplicationContext(),null,message,title);
/*                DbHelper dbHelper = new DbHelper(getApplicationContext());
                dbHelper.saveNotification(title, message, tTime, tDate);*/
            } else {
                Intent resultIntent = new Intent(getApplicationContext(), MapsActivity.class);
                resultIntent.putExtra("message", message);
                PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 7190, resultIntent, PendingIntent.FLAG_ONE_SHOT);
                showNotification(getApplicationContext(), intent, message, title);
/*                DbHelper dbHelper = new DbHelper(getApplicationContext());
                dbHelper.saveNotification(title, message, tTime, tDate);*/
            }
        }
        catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }



 /*   private void sendNotification(String messageBody) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
       *//* Intent intent = new Intent(this, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//**//* Request code *//**//*, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 *//**//* ID of notification *//**//*, notificationBuilder.build());
    }

   *//**//* private void saveNotifications(String message){
        SharedPreferences sharedPreferences = getSharedPreferences("notifications", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("number",0);
        int i = sharedPreferences.getInt("number",0)+1;
        editor.putInt("number",i);
        editor.putString("notification"+i,message);




    }*//*
    }*/

    private void showNotification(Context context,PendingIntent intent,String mssg,String head){

        Notification.Builder builder = new Notification.Builder(context);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(mssg);
        long pattern[] = {100,200,300,400};
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification = new Notification();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.
                    setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("Elements Culmyca").setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(head)
                    .setContentIntent(intent)
                    .setSound(defaultSoundUri)

                    .setVibrate(pattern)
                    .setContentText(mssg)
                    .build();
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(7190, notification);
    }




}

