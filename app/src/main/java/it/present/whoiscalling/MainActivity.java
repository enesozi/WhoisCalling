package it.present.whoiscalling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity {
    private ToggleButton toggle;
    private CompoundButton.OnCheckedChangeListener toggleListener;
    private Preference preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggle = (ToggleButton)findViewById(R.id.toggleButton);
        preference = new Preference(getApplicationContext());
        toggle.setChecked(preference.getPreference());
        toggleListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                preference.setPreference(isChecked);
            }
        };
        toggle.setOnCheckedChangeListener(toggleListener);
        startService(new Intent(getApplicationContext(),ServiceReciever.class));
    }
}
