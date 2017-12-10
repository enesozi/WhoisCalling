package it.present.whoiscalling;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Enes on 18.06.2017.
 */


public class Preference {
    private SharedPreferences sharedPreferences;
    public Preference(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void setPreference(Boolean checked){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Checked",checked);
        editor.commit();
    }
    public Boolean getPreference(){
        return sharedPreferences.getBoolean("Checked",false);
    }
}
