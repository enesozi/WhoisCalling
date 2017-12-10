package it.present.whoiscalling;

import android.*;
import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by Enes on 17.06.2017.
 */

public class ServiceReciever extends BroadcastReceiver {
    private Preference preference;

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    @Override
    public void onReceive(final Context context, Intent intent) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        preference = new Preference(context);

        switch (tm.getCallState()) {
            case TelephonyManager.CALL_STATE_RINGING:
                String phone = intent.getStringExtra("incoming_number");
                Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone));
                String projection[] = new String[]{ContactsContract.Data.DISPLAY_NAME};
                final Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
                //Uri sim = Uri.parse("content://icc/adn");
                //final Cursor cursorSim = context.getContentResolver().query(sim,projection,null,null,null);

                if(cursor.moveToFirst() && preference.getPreference()){
                    Intent iService = new Intent(context, SpeakService.class);
                    iService.putExtra("name",cursor.getString(0));
                    context.startService(iService);
                }else if(ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){
                    String phoneNumber = tm.getLine1Number();
                    if(phoneNumber != null && !phoneNumber.equals("")){
                        Intent iService = new Intent(context, SpeakService.class);
                        iService.putExtra("name",phoneNumber);
                        context.startService(iService);
                        Log.d("SIMNAME",phoneNumber);
                    }
                }
                break;
            default:
                Intent iService = new Intent(context, SpeakService.class);
                context.stopService(iService);
        }
    }
}

