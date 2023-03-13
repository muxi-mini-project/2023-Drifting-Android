package android.bignerdranch.drifting.Mine.User;
/**
 * 用于调用通知提醒
 * 后台实时提醒需要服务器，暂时不写
 */

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.bignerdranch.drifting.Camera.Camera_;
import android.bignerdranch.drifting.R;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;


public class InformUtils extends AppCompatActivity {

    public static void BookInform(){

    }

    /**
     * 漂流相机通知
     * @param context
     * @param camera
     */
    public static void CameraInform(Context context , Camera_ camera){

        String channelId = "漂流相机";
        Long now = camera.getNowuser();
        Long max = camera.getMaxuser();
        String name = camera.getName();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Notification notification = new Notification.Builder(context,channelId)
                    .setContentTitle("漂流相机")
                    .setContentText("您参与的漂流相机项目:《"+name+"》又有新的成员加入啦("+now+"/"+max+")")
                    .setSmallIcon(R.drawable.logo)
                    .build();
            NotificationManager notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelId,"漂流相机", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("漂流相机通知");
            channel.enableVibration(true);
            channel.enableLights(true);
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(1, notification);
        }
    }

    public static void DrawInfrom(){

    }

    public static void NovelInform(){

    }
    /**
     * 打开通知权限
     *
     * @param context
     */
    public static void openNotificationSettingsForApp(Context context) {
        // Links to this app's notification settings.
        Intent intent = new Intent();
        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
        intent.putExtra("app_package", context.getPackageName());
        intent.putExtra("app_uid", context.getApplicationInfo().uid);
        // for Android 8 and above
        intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
        context.startActivity(intent);
    }


}
