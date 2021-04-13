package com.example.ocrcam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Parametre extends AppCompatActivity {
    private int chosenMethod;
    private SharedPreferences mPreferences;
    private final String sharedPrefFile = "com.c4u.appsharedprefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametre);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
    }

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButton:
                if (checked)
                    chosenMethod = 1;
                break;
            case R.id.radioButton2:
                if (checked)
                    chosenMethod = 2;
                break;
            case R.id.radioButton3:
                if (checked)
                    chosenMethod = 3;
                break;
            case R.id.radioButton4:
                if (checked)
                    chosenMethod = 4;
                break;
        }
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt("method", chosenMethod);
        preferencesEditor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}