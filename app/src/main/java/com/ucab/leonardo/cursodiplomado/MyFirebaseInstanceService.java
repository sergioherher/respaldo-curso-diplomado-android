package com.ucab.leonardo.cursodiplomado;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ucab.leonardo.cursodiplomado.actividades.MainActivity;

public class MyFirebaseInstanceService extends FirebaseMessagingService {
    private final String TAG = MyFirebaseInstanceService.class.getSimpleName();

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.w(TAG, "onNewToken: " + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "onMessageReceived: Mensaje con payload");

            String titulo = remoteMessage.getData().get("title");
            String contenido = remoteMessage.getData().get("message");

            // Convertir esto en metodo con el IDE
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, intent, PendingIntent.FLAG_ONE_SHOT);

            String idCanal = "1";
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this, idCanal)
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle(titulo)
                            .setContentText(contenido)
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);



            notificationManager.notify(0, builder.build());
        }

    }
}
