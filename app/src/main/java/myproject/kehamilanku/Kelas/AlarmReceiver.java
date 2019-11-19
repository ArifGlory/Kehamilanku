package myproject.kehamilanku.Kelas;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import myproject.kehamilanku.R;
import myproject.kehamilanku.activity.HomeActivity;


/**
 * Created by Glory on 05/10/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    MediaPlayer mp;
    private NotificationManager mManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm aktif", Toast.LENGTH_LONG).show();
        mp = MediaPlayer.create(context, R.raw.loudalarm);
        mp.start();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.pregnant);
        builder.setContentTitle("Kehamilan sudah 3Bulan!");//"Notifications Example")
        builder.setContentText("Selamat kehamilan anda telah berjalan 3 bulan");//"This is a test notification");
        builder.setTicker("Yuk cek di Aplikasi Kehamilanku");
        builder.setSmallIcon(R.drawable.pregnant);

        Intent notificationIntent = new Intent(context, HomeActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
}
