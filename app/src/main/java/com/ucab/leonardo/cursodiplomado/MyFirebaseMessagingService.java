package com.ucab.leonardo.cursodiplomado;

import android.app.Notification;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    public final static String RESULT = "com.ucab.leonardo.cursodiplomado.RESULT";
    public final static String EXTRA_NOMBRE = "com.ucab.leonardo.cursodiplomado.EXTRA_NOMBRE";
    public final static String EXTRA_APELLIDO = "com.ucab.leonardo.cursodiplomado.EXTRA_APELLIDO";
    public final static String EXTRA_EMPRESA = "com.ucab.leonardo.cursodiplomado.EXTRA_EMPRESA";
    public final static String EXTRA_DIRECCION = "com.ucab.leonardo.cursodiplomado.EXTRA_DIRECCION";
    public final static String EXTRA_EDAD = "com.ucab.leonardo.cursodiplomado.EXTRA_EDAD";
    public final static String EXTRA_EMAIL = "com.ucab.leonardo.cursodiplomado.EXTRA_EMAIL";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            String title = remoteMessage.getData().get("title");
            String message = remoteMessage.getData().get("message");
//            String userId = remoteMessage.getData().get("user_id");
            String nombre = remoteMessage.getData().get("nombre");
            String apellido = remoteMessage.getData().get("apellido");
            String empresa = remoteMessage.getData().get("empresa");
            String direccion = remoteMessage.getData().get("direccion");
            String edad = remoteMessage.getData().get("edad");
            String email = remoteMessage.getData().get("email");

            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);

            Intent intent = new Intent(RESULT);
            intent.putExtra(EXTRA_NOMBRE, nombre);
            intent.putExtra(EXTRA_APELLIDO, apellido);
            intent.putExtra(EXTRA_EMPRESA, empresa);
            intent.putExtra(EXTRA_DIRECCION, direccion);
            intent.putExtra(EXTRA_EDAD, edad);
            intent.putExtra(EXTRA_EMAIL, email);
            broadcastManager.sendBroadcast(intent);

            Notification notification = new NotificationCompat.Builder(this, "micanal123")
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build();
            NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
            manager.notify(123, notification);
        }
    }
}
