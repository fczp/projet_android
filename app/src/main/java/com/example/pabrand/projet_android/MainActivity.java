package com.example.pabrand.projet_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void consulter (View view) {
        Intent intent = new Intent(this, Consulter.class);
        startActivity(intent);
    }

    public void nouveau (View view) {
        Intent intent = new Intent(this, Creer_rdv.class);
        startActivity(intent);
    }

}
