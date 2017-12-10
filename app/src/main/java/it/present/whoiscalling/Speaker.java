package it.present.whoiscalling;

/**
 * Created by Enes on 17.06.2017.
 */

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.DONUT)
public class Speaker implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;

    private boolean ready = false;

    private boolean allowed = false;

    public Speaker(Context context){
        tts = new TextToSpeech(context, this);
    }

    public boolean isAllowed(){
        return allowed;
    }

    public void allow(boolean allowed){
        this.allowed = allowed;
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            // Change this to match your
            // locale
            tts.setLanguage(Locale.getDefault());
            ready = true;
        }else{
            ready = false;
        }
    }
    public void speak(String text){

        // Speak only if the TTS is ready
        // and the user has allowed speech

        if(ready && allowed) {
            HashMap<String, String> hash = new HashMap<>();
            hash.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
                    String.valueOf(AudioManager.STREAM_MUSIC));
            tts.speak(text, TextToSpeech.QUEUE_ADD, hash);
        }
    }
    public void pause(int duration){
        tts.playSilence(duration, TextToSpeech.QUEUE_ADD, null);
    }
    public boolean isSpeaking(){
        return tts.isSpeaking();
    }
    public void destroy(){
        tts.shutdown();
    }
}
