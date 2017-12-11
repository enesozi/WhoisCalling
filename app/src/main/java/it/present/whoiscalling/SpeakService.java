package it.present.whoiscalling;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class SpeakService extends Service {

    private Handler handler = new Handler();
    private Handler handlerRing = new Handler();
    private Speaker speaker;
    private String name;
    private Context context;
    private int LONG_DURATION = 4000;
    private int MULTIPLIER = 175;
    private int currentVolume = 0;
    private AudioManager audioManager ;

    @Override
    public IBinder onBind(Intent intent)
    {
        name = intent.getStringExtra("name");
        Log.d("NAME",name);
        System.out.println("NAMEASD "+name);
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.name = intent.getStringExtra("name");
        return START_STICKY;
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        context =  getApplicationContext();
        speaker = new Speaker(context);
        audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        System.out.println("NAMEcreate "+name);
        startSpeaking();
    }

    Runnable ringRunnable = new Runnable() {
        @Override
        public void run() {
            //audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_RING,currentVolume,0);
            handlerRing.postDelayed(speakRunnable,LONG_DURATION);
        }
    };

    Runnable speakRunnable = new Runnable()
    {
        public void run() {
            //audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            audioManager.setStreamVolume(AudioManager.STREAM_RING,currentVolume/2,0);
            speaker.allow(true);
                speaker.speak(name);
                handler.postDelayed(ringRunnable,name.length()*MULTIPLIER);
        }
    };

    public void startSpeaking()
    {
        audioManager.setStreamVolume(audioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        ringRunnable.run();
    }

    private void stopSpeaker()
    {
        speaker.allow(false);
        speaker.destroy();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        handler.removeCallbacks(speakRunnable);
        stopSpeaker();
    }
}
