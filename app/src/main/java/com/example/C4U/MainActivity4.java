package com.example.C4U;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity4 extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        startService(new Intent(getBaseContext(), SensorService.class));
    }

    public void ocr()
    {
        Intent intent = new Intent(MainActivity4.this, OcrActivity.class);
        startActivity(intent);
    }

    public void moneyDetect()
    {
        Intent intent = new Intent(MainActivity4.this, MoneyDetectActivity.class);
        startActivity(intent);
    }

    public void geo()
    {
        Intent intent = new Intent(MainActivity4.this, GeoActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.param:
                Intent intent = new Intent(MainActivity4.this, Parametre.class);
                startActivity(intent);
                return true;
            case R.id.help:
                Toast.makeText(this,"Help",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
}